package Vistas;

import Conexiones.ConexionRs;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmVistaFormasPago extends javax.swing.JDialog {

    ConexionRs cnRs = new ConexionRs();

    public FrmVistaFormasPago() {
        initComponents();
        setModal(true);
        tablaPagos.getTableHeader().setReorderingAllowed(false);
        addcontMonedas();
        formatoColumnas();
    }

    private void addcontMonedas() {
        try {
            ResultSet rs;
            rs = cnRs.consultar("SELECT IDENTIFICADOR, DESCRIPCION, ABREVIATURA, ID_TIPO_DOCUM, VR_CLASE FROM FND_FORMAS_PAGO WHERE ACTIVO='S' ORDER BY NUMERO ASC");

            DefaultTableModel modelo;
            String[] titulos = {"IDENTIFICADOR", "CODIGO", "DESCRIPCION", "TIPO DOC.", "VR_CLASE"};
            String[] registro = new String[5];
            modelo = new DefaultTableModel(null, titulos);

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("DESCRIPCION");
                registro[2] = rs.getString("ABREVIATURA");
                registro[3] = rs.getString("ID_TIPO_DOCUM");
                registro[4] = rs.getString("VR_CLASE");
                modelo.addRow(registro);
            }

            tablaPagos.setModel(modelo);
            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(FrmVistaFormasPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void formatoColumnas() {
        tablaPagos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaPagos.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaPagos.getColumnModel().getColumn(0).setMaxWidth(0);

        tablaPagos.getColumnModel().getColumn(2).setMinWidth(0);
        tablaPagos.getColumnModel().getColumn(2).setPreferredWidth(0);
        tablaPagos.getColumnModel().getColumn(2).setMaxWidth(0);

        tablaPagos.getColumnModel().getColumn(3).setMinWidth(0);
        tablaPagos.getColumnModel().getColumn(3).setPreferredWidth(0);
        tablaPagos.getColumnModel().getColumn(3).setMaxWidth(0);

        tablaPagos.getColumnModel().getColumn(4).setMinWidth(0);
        tablaPagos.getColumnModel().getColumn(4).setPreferredWidth(0);
        tablaPagos.getColumnModel().getColumn(4).setMaxWidth(0);
    }

    private void mandarDatos(int id, String desc, int tipoDoc, String vr_forma_pago) {
        FrmPosCobros.idFormaPago = id;
        FrmPosCobros.txtFormaPago.setText(desc);
        FrmPosCobros.idTipoDocum = tipoDoc;
        FrmPosCobros.vr_forma_pago = vr_forma_pago;

        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPagos = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::FORMAS DE PAGO::.");
        setAlwaysOnTop(true);
        setIconImage(null);
        setResizable(false);

        tablaPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDENTIFICADOR", "DESCRIPCION", "ABREVIATURA"
            }
        ));
        tablaPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaPagosMousePressed(evt);
            }
        });
        tablaPagos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaPagosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPagos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaPagosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaPagosKeyPressed
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                int fila = tablaPagos.getSelectedRow();

                int id = Integer.parseInt(tablaPagos.getValueAt(fila, 0).toString());
                String desc = tablaPagos.getValueAt(fila, 2).toString();
                int tipoDoc = Integer.parseInt(tablaPagos.getValueAt(fila, 3).toString());
                String vr_forma_pago = tablaPagos.getValueAt(fila, 4).toString();
                mandarDatos(id, desc, tipoDoc, vr_forma_pago);
            } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.dispose();
            }
        } catch (NumberFormatException ex) {

        }
    }//GEN-LAST:event_tablaPagosKeyPressed

    private void tablaPagosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPagosMousePressed
        if (evt.getClickCount() == 2) {
            int fila = tablaPagos.rowAtPoint(evt.getPoint());

            int id = Integer.parseInt(tablaPagos.getValueAt(fila, 0).toString());
            String desc = tablaPagos.getValueAt(fila, 1).toString();
            int tipoDoc = Integer.parseInt(tablaPagos.getValueAt(fila, 3).toString());
            String vr_forma_pago = tablaPagos.getValueAt(fila, 4).toString();
            mandarDatos(id, desc, tipoDoc, vr_forma_pago);
        }
    }//GEN-LAST:event_tablaPagosMousePressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVistaFormasPago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPagos;
    // End of variables declaration//GEN-END:variables

}
