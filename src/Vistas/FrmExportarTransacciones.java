package Vistas;

import Controladores.ContExportarTransacciones;
import Utils.ControlMensajes;
import Utils.Utilidades;
import Controladores.ContTcArqueoCaja;
import Controladores.ContTcControlCaja;
import Controladores.ContVtaClientes;
import Controladores.ContTcExtrEfectivoCaja;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public final class FrmExportarTransacciones extends javax.swing.JDialog {

    private final String titulo = "Exportar Transacciones";

    Thread hiloStk;

    ContExportarTransacciones transacciones = new ContExportarTransacciones();
    ContTcExtrEfectivoCaja contExtrEfectivo = new ContTcExtrEfectivoCaja();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContTcArqueoCaja contArqueoCaja = new ContTcArqueoCaja();
    ContExportarTransacciones contExportarRef = new ContExportarTransacciones();
    ContVtaClientes contCliente = new ContVtaClientes();

    public FrmExportarTransacciones() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        setModal(true);
        setMaskFormat();
        this.setTitle(titulo);
    }

    void setMaskFormat() {
        try {
            MaskFormatter maskFechaDesde = new MaskFormatter("##/##/####");
            maskFechaDesde.setPlaceholderCharacter('_');
            maskFechaDesde.install(txtFechaDesde);

            MaskFormatter maskFechaHasta = new MaskFormatter("##/##/####");
            maskFechaHasta.setPlaceholderCharacter('_');
            maskFechaHasta.install(txtFechaHasta);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean opciones(int opc) {
        if (!Utilidades.getPing()) {
            ControlMensajes.error("Sin conexion a servidor", titulo);
            return false;
        }

        if (txtFechaDesde.getText().isEmpty()) {
            ControlMensajes.informacion("Debe ingresar fecha desde", titulo);
            return false;
        }

        if (txtFechaHasta.getText().isEmpty()) {
            ControlMensajes.informacion("Debe ingresar fecha hasta", titulo);
            return false;
        }

        switch (opc) {
            case 0:

                if (!transacciones.exportarComprobantes("WHERE FECHA_ENVIO_SRV IS NULL AND TRUNC(FECHA) >= TO_DATE('" + txtFechaDesde.getText() + "','dd/mm/yyyy') AND TRUNC(FECHA) <= TO_DATE('" + txtFechaHasta.getText() + "','dd/mm/yyyy')")) {
                    ControlMensajes.error("Comprobantes no exportados", titulo);
                    return false;
                }

                if (!contArqueoCaja.expArqueos("AND TRUNC(FECHA) >= TO_DATE('" + txtFechaDesde.getText() + "','dd/mm/yyyy') AND TRUNC(FECHA) <= TO_DATE('" + txtFechaHasta.getText() + "','dd/mm/yyyy')")) {
                    ControlMensajes.error("Arqueos de caja no exportados", titulo);
                    return false;
                }

                if (!contControlCaja.expControles("AND TRUNC(FEC_CRE) >= TO_DATE('" + txtFechaDesde.getText() + "','dd/mm/yyyy') AND TRUNC(FEC_CRE) <= TO_DATE('" + txtFechaHasta.getText() + "','dd/mm/yyyy')")) {
                    ControlMensajes.error("Controles de caja no exportados", titulo);
                    return false;
                }

                if (!contExtrEfectivo.expExtraccionEfectivo(contControlCaja.idApertura())) {
                    ControlMensajes.error("Extracciones no exportadas", titulo);
                    return false;
                }

                if (!contCliente.Exportar()) {
                    ControlMensajes.error("Clientes no exportados", titulo);
                    return false;
                }
                ControlMensajes.informacion("Transacciones enviadas: " + ContExportarTransacciones.transaccionesEnviadas, titulo);

                break;

            case 1:
                if (!contArqueoCaja.expArqueos("AND TRUNC(FECHA) >= TO_DATE('" + txtFechaDesde.getText() + "','dd/mm/yyyy') AND TRUNC(FECHA) <= TO_DATE('" + txtFechaHasta.getText() + "','dd/mm/yyyy')")) {
                    ControlMensajes.error("Arqueos de caja no exportados", titulo);
                }
                ControlMensajes.informacion("Arqueos de Caja exportados: ", titulo);
                break;

            case 2:
                if (!contControlCaja.expControles("AND TRUNC(FEC_CRE) >= TO_DATE('" + txtFechaDesde.getText() + "','dd/mm/yyyy') AND TRUNC(FEC_CRE) <= TO_DATE('" + txtFechaHasta.getText() + "','dd/mm/yyyy')")) {
                    ControlMensajes.error("Controles de cajas no exportados", titulo);
                }
                ControlMensajes.informacion("Controles de caja exportados: ", titulo);
                break;

            case 3:
                if (!contExportarRef.exportarComprobantes("WHERE FECHA_ENVIO_SRV IS NULL AND TRUNC(FECHA) >= TO_DATE('" + txtFechaDesde.getText() + "','dd/mm/yyyy') AND TRUNC(FECHA) <= TO_DATE('" + txtFechaHasta.getText() + "','dd/mm/yyyy')")) {
                    ControlMensajes.error("Comprobantes no exportados", titulo);
                    return false;
                }
                ControlMensajes.informacion("Comprobantes exportados: ", titulo);
                break;

            case 4:
                if (!contCliente.Exportar()) {
                    ControlMensajes.error("Clientes no exportados", titulo);
                    return false;
                }
                ControlMensajes.informacion("Clientes exportados ", titulo);
                break;
        }
        btnSalir.requestFocus();
        return true;
    }

    private void btnExpParametros() {
        System.out.println("<INICIO PROCESO DE EXPORTACION>");
        opciones(cboOpciones.getSelectedIndex());
        btnSalir.requestFocus();
        System.out.println("<FIN PROCESO DE EXPORTACION>");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExportar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        cboOpciones = new javax.swing.JComboBox<>();
        txtFechaDesde = new javax.swing.JFormattedTextField();
        txtFechaHasta = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::EXPORTAR TRANSACCIONES::.");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnExportar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnExportar.setText("EXPORTAR");
        btnExportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportarMouseClicked(evt);
            }
        });
        btnExportar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExportarKeyPressed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        btnSalir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalirKeyPressed(evt);
            }
        });

        cboOpciones.setBackground(java.awt.Color.gray);
        cboOpciones.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        cboOpciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas las Transacciones", "Arqueos de Caja", "Controles de Caja", "Comprobantes", "Clientes" }));
        cboOpciones.setToolTipText("");
        cboOpciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboOpcionesKeyPressed(evt);
            }
        });

        txtFechaDesde.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtFechaDesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaDesdeKeyPressed(evt);
            }
        });

        txtFechaHasta.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtFechaHasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaHastaKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Fecha desde");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Fecha Hasta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnExportar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cboOpciones, 0, 288, Short.MAX_VALUE)
                        .addComponent(txtFechaDesde)
                        .addComponent(txtFechaHasta)))
                .addGap(89, 89, 89))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txtFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(cboOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExportarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnExpParametros();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnExportarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void cboOpcionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboOpcionesKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnExportar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_cboOpcionesKeyPressed

    private void btnExportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarMouseClicked
        if (evt.getClickCount() == 1) {
            btnExpParametros();
        }
    }//GEN-LAST:event_btnExportarMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        if (evt.getClickCount() == 1) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void txtFechaDesdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaDesdeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && !txtFechaDesde.getText().equals("__/__/____")) {
            txtFechaHasta.requestFocus();
        }
    }//GEN-LAST:event_txtFechaDesdeKeyPressed

    private void txtFechaHastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaHastaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && !txtFechaHasta.getText().equals("__/__/____")) {
            cboOpciones.requestFocus();
        }
    }//GEN-LAST:event_txtFechaHastaKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmExportarTransacciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboOpciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JFormattedTextField txtFechaDesde;
    private javax.swing.JFormattedTextField txtFechaHasta;
    // End of variables declaration//GEN-END:variables

}
