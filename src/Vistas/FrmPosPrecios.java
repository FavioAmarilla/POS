package Vistas;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Utils.ControlMensajes;
import Conexiones.ConexionRs;
import Controladores.ContPrProductos;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FrmPosPrecios extends javax.swing.JDialog {

    ContPrProductos contProducto = new ContPrProductos();
    ConexionRs cnRs = new ConexionRs();
    DecimalFormat miles = new DecimalFormat("###,###.##");

    DefaultTableModel modelo = new DefaultTableModel();
    String[] filas = new String[3];

    public FrmPosPrecios() {
        initComponents();
        setModal(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        txtCodigo.requestFocus();

        rdoCodBarras.setSelected(true);

        if (rdoCodBarras.isSelected()) {
            lblTitulo.setText("Codigo de Barras...");
        }
        if (rdoDescripcion.isSelected()) {
            lblTitulo.setText("Descripcion...");
        }

        txtCodigo.requestFocus();
    }

    private void leerCodBarras(String codigo) {

        int codTipoUnid = Integer.parseInt(codigo.substring(0, 3));
        String codigoPr = codigo.substring(0, 7);
        double codCantidad = Double.valueOf(codigo.substring(7, 12));

        switch (codTipoUnid) {
            case 200:
                producto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigoPr) + "' AND ACTIVO='S'", codCantidad / 1000, "Kg.");
                break;
            case 240:
                producto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigoPr) + "' AND ACTIVO='S'", codCantidad, "Und.");
                System.out.println("--------------------");
                System.out.println(codigoBarras(codigoPr) + "   " + codCantidad);
                break;
        }

    }

    private String codigoBarras(String codigo) {
        int longitud = codigo.length();
        for (int i = 0; i < 13 - longitud; i++) {
            codigo += "0";
        }
        return codigo;
    }

    private void producto(String condicion, double cantidad, String undMedida) {

        int precio = 0;
        String sql = "SELECT PR.IDENTIFICADOR, PR.CODIGO_BARRAS, PR.DESCRIPCION, "
                + "PR.ID_UNIDAD_MEDIDA, "
                + "PR.PRECIO_VENTA * " + cantidad + " AS PRECIO_UNITARIO, "
                + "PR.ID_TIPO_IMPUESTO AS ID_TIPO_IMPUESTO, "
                + "FND_TIPOS_IMPUESTO.VALOR AS PORCENTAJE_IMP, "
                + "PR.ID_PROVEEDOR AS ID_PROVEEDOR,  "
                + "PR.ID_SITIO_PROV AS ID_SITIO_PROV,"
                + "PR.PRECIO_VENTA AS PRECIO_UNITARIO "
                + "FROM PR_PRODUCTOS PR "
                + "LEFT JOIN FND_TIPOS_IMPUESTO ON FND_TIPOS_IMPUESTO.IDENTIFICADOR = PR.ID_TIPO_IMPUESTO "
                + condicion.toUpperCase();

        try {
            ResultSet rs = null;
            rs = cnRs.consultar(sql);
            if (rs.next()) {
                pesables();
                this.dispose();
                FrmPosPreciosPesables.lblCodigo.setText(txtCodigo.getText());
                FrmPosPreciosPesables.lblDescripcion.setText(rs.getString(3));
                precio = rs.getInt(10);
                FrmPosPreciosPesables.lblPrecioUnitario.setText(miles.format(precio) + "  Gs.");
                FrmPosPreciosPesables.lblPrecio.setText(miles.format(precio) + "  Gs.");
                FrmPosPreciosPesables.lblCantidad.setText("X    " + String.valueOf(cantidad) + "  " + undMedida);
                FrmPosPreciosPesables.lblTotal.setText(miles.format(precio * cantidad) + "  Gs.");
            }
            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            ControlMensajes.error("producto: " + ex, "CONSULTA DE PRECIOS");
        }

    }

    private void registrosTabla(String codigo) {
        try {
            String condicion = "";

            if (rdoCodBarras.isSelected()) {
                condicion = "WHERE CODIGO_BARRAS = '" + codigo + "' AND ACTIVO='S'";
            }
            if (rdoDescripcion.isSelected()) {
                condicion = "WHERE DESCRIPCION LIKE '%" + codigo.toUpperCase() + "%' AND ACTIVO='S' AND BUSCAR_EN_CAJA = 'S'";
            }

            modelo = contProducto.precios(condicion);
            tablaProductos.setModel(modelo);

            tablaProductos.setAutoResizeMode(0);

            tablaProductos.getColumnModel().getColumn(0).setMaxWidth(120);
            tablaProductos.getColumnModel().getColumn(0).setMinWidth(120);
            tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(120);

            tablaProductos.getColumnModel().getColumn(1).setMaxWidth(245);
            tablaProductos.getColumnModel().getColumn(1).setMinWidth(245);
            tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(245);

            tablaProductos.getColumnModel().getColumn(2).setMaxWidth(90);
            tablaProductos.getColumnModel().getColumn(2).setMinWidth(90);
            tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(90);

            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setForeground(Color.RED);
            Font font = new Font("Dailog", Font.BOLD, 18);
            tcr.setFont(font);

            tablaProductos.getColumnModel().getColumn(2).setCellRenderer(tcr);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "registrosTabla: " + e, "CONSULTA DE PRECIOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesables() {
        FrmPosPreciosPesables frm = new FrmPosPreciosPesables();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        frm.toFront();
    }

    private void cerrar() {
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        txtCodigo = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        rdoCodBarras = new javax.swing.JRadioButton();
        rdoDescripcion = new javax.swing.JRadioButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::CONSULTA DE PRECIOS::.");
        setAlwaysOnTop(true);
        setIconImage(null);
        setResizable(false);

        txtCodigo.setBackground(new java.awt.Color(0, 9, 18));
        txtCodigo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(204, 204, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo.setText("Codigo de Barras");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaProductos.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        tablaProductos.setRowHeight(23);
        tablaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProductosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        buttonGroup1.add(rdoCodBarras);
        rdoCodBarras.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        rdoCodBarras.setForeground(new java.awt.Color(255, 255, 255));
        rdoCodBarras.setText("Cod. Barras (F1)");
        rdoCodBarras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoCodBarrasMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoDescripcion);
        rdoDescripcion.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        rdoDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        rdoDescripcion.setText("Descripcion (F2)");
        rdoDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDescripcionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rdoCodBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdoDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdoCodBarras)
                        .addGap(0, 0, 0)
                        .addComponent(rdoDescripcion)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        if (rdoDescripcion.isSelected()) {
            registrosTabla(txtCodigo.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (txtCodigo.getText().length() < 13) {
                    String ceros = "";
                    for (int i = 0; i < 13 - txtCodigo.getText().length(); i++) {
                        ceros += "0";
                    }
                    txtCodigo.setText(ceros + txtCodigo.getText());
                }
                if (txtCodigo.getText().substring(0, 3).equals("240") || txtCodigo.getText().substring(0, 3).equals("200")) {
                    leerCodBarras(txtCodigo.getText());
                } else {
                    registrosTabla(txtCodigo.getText().toUpperCase());
                    tablaProductos.requestFocus();
                }
                break;

            case KeyEvent.VK_F1:
                rdoCodBarras.setSelected(true);
                lblTitulo.setText("Codigo de Barras...");
                txtCodigo.setText("");
                txtCodigo.requestFocus();
                break;

            case KeyEvent.VK_F2:
                rdoDescripcion.setSelected(true);
                lblTitulo.setText("Descripcion...");
                txtCodigo.setText("");
                txtCodigo.requestFocus();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void tablaProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductosKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
            case KeyEvent.VK_ENTER:
                int fila = tablaProductos.getSelectedRow();
                String codigo = tablaProductos.getValueAt(fila, 0).toString();
                FrmPos.txtCodigoBarras.setText(codigo);

                this.dispose();
                break;
            case KeyEvent.VK_F1:
                rdoCodBarras.setSelected(true);
                lblTitulo.setText("Codigo de Barras...");
                txtCodigo.setText("");
                txtCodigo.requestFocus();
                break;
            case KeyEvent.VK_F2:
                rdoDescripcion.setSelected(true);
                lblTitulo.setText("Descripcion...");
                txtCodigo.setText("");
                txtCodigo.requestFocus();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_tablaProductosKeyPressed

    private void rdoCodBarrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoCodBarrasMouseClicked
        if (evt.getClickCount() == 1) {
            lblTitulo.setText("Codigo de Barras...");
            txtCodigo.setText("");
        }
    }//GEN-LAST:event_rdoCodBarrasMouseClicked

    private void rdoDescripcionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDescripcionMouseClicked
        if (evt.getClickCount() == 1) {
            lblTitulo.setText("Descripcion...");
            txtCodigo.setText("");
        }
    }//GEN-LAST:event_rdoDescripcionMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPosPrecios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JRadioButton rdoCodBarras;
    private javax.swing.JRadioButton rdoDescripcion;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables

}
