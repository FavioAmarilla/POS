package Vistas;

import Modelos.ReimpresionComprob;
import Controladores.ContReimpresionComprob;
import Utils.ControlMensajes;
import Utils.Utilidades;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class FrmReimpresionCliente extends javax.swing.JDialog {

    ContReimpresionComprob reimpresion = new ContReimpresionComprob();
    ReimpresionComprob dts = new ReimpresionComprob();

    public String id;

    public FrmReimpresionCliente() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        lblNumero.setText(id);
        txtRuc.requestFocus();
        setModal(true);
    }

    private boolean modificar() {
        cliente(txtRuc.getText(), "NO");

        if (!Utilidades.getPing()) {
            ControlMensajes.error("Sin conexion a servidor", "Reimpresion");
            return false;
        }
        if (txtRuc.getText().isEmpty()) {
            ControlMensajes.error("Debe ingresar Nro. Ruc", "Reimpresion");
            return false;
        }
        if (txtnombre.getText().isEmpty()) {
            ControlMensajes.error("Debe ingresar Nombre del Cliente", "Reimpresion");
            return false;
        }

        System.out.println("\tID_CLIENTE: " + dts.getId_cliente());
        System.out.println("\tNOMBRE: " + dts.getNombre());
        System.out.println("\tRUC: " + dts.getRuc());
        System.out.println("\tCONDICION: " + dts.getCondicion());

        if (!reimpresion.modificar(dts, "L")) {
            ControlMensajes.error("Comprobante no modificado (local)", "Reimpresion");
            return false;
        }
        if (!reimpresion.modificar(dts, "S")) {
            ControlMensajes.error("Comprobante no modificado (server)", "Reimpresion");
            return false;
        }

        ControlMensajes.informacion("Comprobante modificado", "Reimpresion");
        System.out.print("Comprobante modificado");

        return true;
    }

    private void cliente(String ruc, String mensaje) {
        dts = reimpresion.getCpEquivalenciaRuc(ruc, String.valueOf(id));
        if (dts == null) {
            dts = reimpresion.getVtaClientes(ruc, String.valueOf(id));
            if (dts == null) {
                if (mensaje.equals("SI")) {
                    ControlMensajes.error("Cliente no Definido", "Reimpresion");
                }

                dts = new ReimpresionComprob();
                dts.setId_cliente(0);
                dts.setRuc(txtRuc.getText());
                dts.setNombre(txtnombre.getText());
                dts.setCondicion(id);

                txtRuc.requestFocus();
                return;
            }
        }
        txtRuc.setText(dts.getRuc());
        txtnombre.setText(dts.getNombre());
        txtnombre.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblNumero = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::MODIFICAR RUC::.");
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setForeground(new java.awt.Color(204, 204, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Factura Nº: ");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lblNumero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNumero.setForeground(new java.awt.Color(204, 204, 0));
        lblNumero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumero.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
            .addComponent(lblNumero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtRuc.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtRuc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("R.U.C");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("NOMBRE");

        txtnombre.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtnombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
        });
        btnModificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificarKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ESC:  Salir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRuc, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(txtnombre, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
            .addGroup(layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(btnModificar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (modificar()) {
                Thread h = new Thread(() -> {
                    FrmReimpresion frm = new FrmReimpresion();
                    FrmReimpresion.txtId.setText(lblNumero.getText());
                    frm.txtIdKeyPressed(evt);
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                    frm.toFront();
                });
                h.start();
                this.dispose();
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnModificarKeyPressed

    private void txtRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtRuc.getText().isEmpty() || txtRuc.getText().equals("XXXXXX")) {
                txtRuc.setText("XXXXXX");
                txtnombre.setText("SIN NOMBRE");

                dts.setId_cliente(1425);
                dts.setRuc("XXXXXX");
                dts.setNombre("SIN NOMBRE");
                dts.setCondicion(id);

                txtnombre.requestFocus();
            } else {
                cliente(txtRuc.getText(), "SI");
            }
        }
    }//GEN-LAST:event_txtRucKeyPressed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtnombre.getText().isEmpty()) {
                btnModificar.requestFocus();
            }
        }
    }//GEN-LAST:event_txtnombreKeyPressed

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        if (modificar()) {
            Thread h = new Thread(() -> {
                FrmReimpresion frm = new FrmReimpresion();
                FrmReimpresion.txtId.setText(lblNumero.getText());
                frm.setLocationRelativeTo(null);
                frm.setVisible(true);
                frm.toFront();
            });
            h.start();
            this.dispose();
        }
    }//GEN-LAST:event_btnModificarMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReimpresionCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JLabel lblNumero;
    public static javax.swing.JTextField txtRuc;
    public static javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables

}
