package Vistas;

import Utils.FormatosTicket;
import Controladores.ContParamAplicacion;
import Utils.Utilidades;
import Modelos.VtaComprobantes;
import Controladores.ContExportarTransacciones;
import Controladores.ContFndUsuarios;
import Controladores.ContTcCajas;
import static Vistas.FrmPos.identificador;
import static Vistas.FrmPos.lblComprobante;
import static Vistas.FrmPos.numeroComprobante;
import Controladores.ContVtaComprobantes;
import Controladores.ContVtaControlComprob;
import Utils.ControlImpresora;
import Utils.ControlMensajes;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrmAutorizacion extends javax.swing.JDialog {

    ContExportarTransacciones contExportarReferencias = new ContExportarTransacciones();
    ContVtaComprobantes contComprobante = new ContVtaComprobantes();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();

    VtaComprobantes dtsCp = new VtaComprobantes();

    ControlImpresora impresora = new ControlImpresora();
    FormatosTicket ticket = new FormatosTicket();

    public String formulario;
    int[] ultimoNro = contControlComprob.ultimoNro();

    public FrmAutorizacion() {
        initComponents();
        setModal(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLocationRelativeTo(null);
        txtCodigo.requestFocus();
    }

    private boolean autorizacion() {
        ContTcCajas contTcCajas = new ContTcCajas();

        return contTcCajas.getAutorizacionSupervidor(txtCodigo.getText());
        //return "1234567894321".equals(txtCodigo.getText()) || "2703863000528".equals(txtCodigo.getText());
    }

    public void cancelarComprobante() {
        System.out.println("<INICIO PROCESO DE CANCELACION DE COMPROBANTE>");
        dtsCp.setUsrAnulacion(ContFndUsuarios.USR_NOMBRE);
        dtsCp.setFeAnulacion(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsCp.setDescripcion("CANCELACION A PEDIDO DEL CLIENTE");
        dtsCp.setIdentificador(identificador);

        if (Utilidades.getPing()) {
            System.out.println("\tBASE DE DATOS SERVER");
            if (contComprobante.cancelarComp(dtsCp, 1)) {
                System.out.println("\tComprobante cancelado: " + numeroComprobante);
                if (contComprobante.ultNroUsado(ultimoNro[1], ultimoNro[0], 0)) {
                    System.out.println("\tUltimo numero usado actualizado");
                }
                if (Utilidades.getPing()) {
                    if (contComprobante.ultNroUsado(ultimoNro[1] + 1, ultimoNro[0], 1)) {
                        System.out.println("\tUltimo numero usado modificado");
                        contExportarReferencias.exportarComprobantes(" WHERE IDENTIFICADOR=" + identificador + "");
                    } else {
                        System.out.println("\tUltimo numero usado no modificado");
                    }
                }
            } else {
                System.out.println("\tComprobante no cancelado: " + numeroComprobante);
            }
        }

        System.out.println("\tBASE DE DATOS LOCAL");
        if (contComprobante.cancelarComp(dtsCp, 0)) {
            if (contComprobante.ultNroUsado(ultimoNro[1], ultimoNro[0], 0)) {
                System.out.println("\tComprobante cancelado: " + contComprobante.comprobante);
                System.out.println("\tUltimo numero usado actualizado");
                ControlMensajes.informacion("Comprobante cancelado: " + numeroComprobante, "Punto de ventas");

                cancelacionTicket();
                FrmPos.reiniciarValores();
                lblComprobante.setText(numero(String.valueOf(ultimoNro[1] + 1)));
            }

            if (Utilidades.getPing()) {
                System.out.println("\tBASE DE DATOS SERVER");
                if (contComprobante.ultNroUsado(ultimoNro[1] + 1, ultimoNro[0], 1)) {
                    System.out.println("\tUltimo numero usado modificado");
                } else {
                    System.out.println("\tUltimo numero usado no modificado");
                }
            }

        } else {
            ControlMensajes.error("Comprobante no cancelado: " + numeroComprobante, "Punto de ventas");
            System.out.println("\tComprobante no cancelado: " + numeroComprobante);
        }

        System.out.println("<FIN PROCESO DE CANCELACION DE COMPROBANTE>");
    }

    private String numero(String nro) {
        String ceros = "";
        int dif;
        dif = ContParamAplicacion.VTA_CTRL_COMP_LONG_NRO_COMPROB - nro.length();

        if (dif > 0) {
            for (int i = 0; i < dif; i++) {
                ceros = ceros + "0";
            }
        } else {
            ceros = "";
        }
        return ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-" + ceros + nro;
    }

    private void cancelacionTicket() {
        imprimir("\n");
        imprimir("\n");
        imprimir(ticket.separador());
        imprimir(ticket.centrar("--CANCELACION--"));
        imprimir(ticket.centrar("fecha: " + FrmPos.lblFecha.getText() + " " + FrmPos.lblHora.getText()));
        imprimir(ticket.separador());
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");
        imprimir("\n");

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
        }

        ControlImpresora.cortarPapel();
    }

    private void imprimir(String texto) {
        try {
            FileOutputStream os = new FileOutputStream(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
            PrintStream ps = new PrintStream(os);

            ps.print(texto);
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmPos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrmPos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::CODIGO DE AUTORIZACION::.");
        setIconImage(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Codigo de Autorizacion");

        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
        });
        btnAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAceptarKeyPressed(evt);
            }
        });

        txtCodigo.setBackground(new java.awt.Color(0, 9, 18));
        txtCodigo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(92, 92, 92))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (autorizacion()) {

                if (formulario == null) {
                    ControlMensajes.error("Parametro de formulario no enviado", "Autorizacion");
                }

                if (formulario.equals("cierre")) {
                    Thread h = new Thread(() -> {
                        FrmCierreCaja frm = new FrmCierreCaja();
                        frm.setLocationRelativeTo(null);
                        frm.setVisible(true);
                    });
                    h.start();
                    this.dispose();
                }

                if (formulario.equals("extraccion")) {
                    Thread h = new Thread(() -> {
                        FrmExtraccionEfectivo frm = new FrmExtraccionEfectivo();
                        frm.setLocationRelativeTo(null);
                        frm.setVisible(true);
                    });
                    h.start();
                    this.dispose();
                }

                if (formulario.equals("devolucion")) {
                    FrmPos.lblModoVenta.setForeground(Color.RED);
                    FrmPos.lblModoVenta.setText("MODO VENTA: Devolucion");
                    FrmPos.devolucion = true;
                    this.dispose();
                }

                if (formulario.equals("reimpresion")) {
                    Thread h = new Thread(() -> {
                        FrmReimpresion frm = new FrmReimpresion();
                        frm.setLocationRelativeTo(null);
                        frm.setVisible(true);
                    });
                    h.start();
                    this.dispose();
                }

                if (formulario.equals("cancelacion")) {
                    cancelarComprobante();
                    this.dispose();
                }

                if (formulario.equals("abrirGaveta")) {
                    ControlImpresora.abrirGaveta();
                    this.dispose();
                }

            } else {
                ControlMensajes.error("Acceso denegado", "Autorizacion");
                this.dispose();
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnCancelarKeyPressed(evt);
        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        if (evt.getClickCount() == 1) {
            if (autorizacion()) {
                if (formulario == null) {
                    ControlMensajes.error("Parametro de formulario no enviado", "Autorizacion");
                }

                if (formulario.equals("cierre")) {
                    FrmCierreCaja frm = new FrmCierreCaja();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                    this.dispose();
                }

                if (formulario.equals("extraccion")) {
                    FrmExtraccionEfectivo frm = new FrmExtraccionEfectivo();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                    this.dispose();
                }

                if (formulario.equals("devolucion")) {
                    FrmPos.lblModoVenta.setForeground(Color.RED);
                    FrmPos.lblModoVenta.setText("MODO VENTA: Devolucion");
                    FrmPos.devolucion = true;
                    this.dispose();
                }

                if (formulario.equals("reimpresion")) {
                    FrmReimpresion frm = new FrmReimpresion();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                    this.dispose();
                }

                if (formulario.equals("cancelacion")) {
                    cancelarComprobante();
                    this.dispose();
                }

                if (formulario.equals("abrirGaveta")) {
                    ControlImpresora.abrirGaveta();
                    this.dispose();
                }

            } else {
                ControlMensajes.error("Acceso denegado", "Autorizacion");
                this.dispose();
            }
        }
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        if (evt.getClickCount() == 1) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAceptarKeyPressed(evt);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnCancelarKeyPressed(evt);
        }

    }//GEN-LAST:event_txtCodigoKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAutorizacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAceptar;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField txtCodigo;
    // End of variables declaration//GEN-END:variables

}
