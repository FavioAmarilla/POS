package Vistas;

import Controladores.ContFndUsuarios;
import Controladores.ContParamAplicacion;
import Controladores.ContTcControlCaja;
import Controladores.ContVtaControlComprob;

import Utils.FuncionesBd;
import Utils.ControlImpresora;
import Utils.ControlMensajes;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class FrmCierreCaja extends javax.swing.JFrame {

    private final String titulo = "Cierre de caja";

    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    int[] ultimoNro = contControlComprob.ultimoNro();

    public FrmCierreCaja() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();
        txtComentario.requestFocus();
        this.setTitle(titulo);
        ControlImpresora.abrirGaveta();
    }

    private void inicio() {
        txtNroCaja.setVisible(false);
        jLabel2.setVisible(false);
        lblUsuario.setText(ContFndUsuarios.USR_NOMBRE_COMPLETO);
        lblCaja.setText(ContParamAplicacion.CAJA_DESCRIPCION);
        txtCaja.setText(ContParamAplicacion.CAJA_DESCRIPCION);
        txtNroCaja.setText(ContParamAplicacion.CAJA_NUMERO);
        txtUsuario.setText(ContFndUsuarios.USR_NOMBRE);
        txtFechaCierre.setText(String.valueOf(FuncionesBd.sysdate()));
        txtControlCaja.setText(String.valueOf(contControlCaja.idApertura()));
        txtSecuencia.setText(String.valueOf(contControlCaja.secuenciaControl() - 1));
        txtNroTransInicial.setText(String.valueOf(contControlComprob.nroInicial(contControlCaja.idApertura())));
        txtNroTransFinal.setText(String.valueOf(ultimoNro[1]));
    }

    private void declararEfectivo() {
        FrmDeclaracionEfec frm = new FrmDeclaracionEfec();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void btnSalir() {
        if (ControlMensajes.confirmacion("Deseas salir sin confirmar el cierre?", titulo)) {
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDecEfectivo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblCaja = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        txtControlCaja = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNroTransInicial = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNroCaja = new javax.swing.JTextField();
        txtNroTransFinal = new javax.swing.JTextField();
        txtCaja = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtFechaCierre = new javax.swing.JTextField();
        txtSecuencia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(".::CIERRE DE CAJAS::..");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnDecEfectivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDecEfectivo.setForeground(new java.awt.Color(0, 0, 0));
        btnDecEfectivo.setText("Dec. Efectivo");
        btnDecEfectivo.setPreferredSize(new java.awt.Dimension(68, 27));
        btnDecEfectivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDecEfectivoMouseClicked(evt);
            }
        });
        btnDecEfectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDecEfectivoKeyPressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblCaja.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCaja.setForeground(new java.awt.Color(204, 204, 0));
        lblCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja.setText("NUMERO DE CAJA");

        lblUsuario.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(204, 204, 0));
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("USUARIO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCaja)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnSalir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
        btnSalir.setText("Salir");
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

        txtControlCaja.setEditable(false);
        txtControlCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtControlCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtControlCaja.setForeground(java.awt.Color.white);
        txtControlCaja.setCaretColor(java.awt.Color.white);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Caja:");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Control Nro:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Caja Nro.:");

        txtNroTransInicial.setEditable(false);
        txtNroTransInicial.setBackground(new java.awt.Color(0, 9, 18));
        txtNroTransInicial.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroTransInicial.setForeground(java.awt.Color.white);
        txtNroTransInicial.setCaretColor(java.awt.Color.white);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Secuencia Nro:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Transaccion Inicial:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Fecha :");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("Transaccion Final:");

        txtNroCaja.setEditable(false);
        txtNroCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtNroCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroCaja.setForeground(java.awt.Color.white);
        txtNroCaja.setCaretColor(java.awt.Color.white);

        txtNroTransFinal.setEditable(false);
        txtNroTransFinal.setBackground(new java.awt.Color(0, 9, 18));
        txtNroTransFinal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroTransFinal.setForeground(java.awt.Color.white);
        txtNroTransFinal.setCaretColor(java.awt.Color.white);

        txtCaja.setEditable(false);
        txtCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtCaja.setForeground(java.awt.Color.white);
        txtCaja.setCaretColor(java.awt.Color.white);

        txtUsuario.setEditable(false);
        txtUsuario.setBackground(new java.awt.Color(0, 9, 18));
        txtUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtUsuario.setForeground(java.awt.Color.white);
        txtUsuario.setCaretColor(java.awt.Color.white);

        txtFechaCierre.setEditable(false);
        txtFechaCierre.setBackground(new java.awt.Color(0, 9, 18));
        txtFechaCierre.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtFechaCierre.setForeground(java.awt.Color.white);
        txtFechaCierre.setCaretColor(java.awt.Color.white);

        txtSecuencia.setEditable(false);
        txtSecuencia.setBackground(new java.awt.Color(0, 9, 18));
        txtSecuencia.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtSecuencia.setForeground(java.awt.Color.white);
        txtSecuencia.setCaretColor(java.awt.Color.white);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.white);
        jLabel9.setText("Comentario:");

        txtComentario.setBackground(new java.awt.Color(0, 9, 18));
        txtComentario.setColumns(20);
        txtComentario.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtComentario.setForeground(new java.awt.Color(255, 255, 255));
        txtComentario.setLineWrap(true);
        txtComentario.setRows(4);
        txtComentario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtComentarioKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtComentario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFechaCierre)
                    .addComponent(txtControlCaja)
                    .addComponent(txtSecuencia)
                    .addComponent(txtNroTransInicial)
                    .addComponent(txtNroTransFinal)
                    .addComponent(jScrollPane1)
                    .addComponent(txtNroCaja)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(btnDecEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtControlCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroTransInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroTransFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDecEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDecEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDecEfectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            declararEfectivo();
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnDecEfectivoKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSalir();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }

    }//GEN-LAST:event_btnSalirKeyPressed

    private void txtComentarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComentarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDecEfectivo.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        }
    }//GEN-LAST:event_txtComentarioKeyPressed

    private void btnDecEfectivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDecEfectivoMouseClicked
        if (evt.getClickCount() == 1) {
            declararEfectivo();
            this.dispose();
        }
    }//GEN-LAST:event_btnDecEfectivoMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        if (evt.getClickCount() == 1) {
            btnSalir();
        } else {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        btnSalir();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCierreCaja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDecEfectivo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblCaja;
    public static javax.swing.JLabel lblUsuario;
    public static javax.swing.JTextField txtCaja;
    public static javax.swing.JTextArea txtComentario;
    public static javax.swing.JTextField txtControlCaja;
    private javax.swing.JTextField txtFechaCierre;
    private javax.swing.JTextField txtNroCaja;
    private javax.swing.JTextField txtNroTransFinal;
    private javax.swing.JTextField txtNroTransInicial;
    private javax.swing.JTextField txtSecuencia;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
