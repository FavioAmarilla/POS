package Vistas;

import Controladores.ContFndUsuarios;
import Controladores.ContParamAplicacion;
import java.awt.event.KeyEvent;
import Controladores.ContTcControlCaja;
import Controladores.ContPrProductos;
import Controladores.ContTcCajas;
import Controladores.ContVtaControlComprob;
import Pos.POS;
import Utils.ControlMensajes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FrmLogin extends javax.swing.JFrame {

    private final String titulo = "Acceso al sistema";

    public static int idTipoComprob;
    public static String tipoComprob;

    ContFndUsuarios contUsuario = new ContFndUsuarios();
    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();
    ContTcCajas contCaja = new ContTcCajas();
    ContPrProductos contProducto = new ContPrProductos();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();

    public FrmLogin() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        parametrosApp();
        imagen();
    }

    private void imagen() {
        File archivo = new File(POS.IMG_LOGO_LOGIN);
        if (archivo.exists()) {
            ImageIcon image = new ImageIcon(POS.IMG_LOGO_LOGIN);

            int scale = 3;
            int width = image.getIconWidth();
            int height = image.getIconHeight();

            BufferedImage buffer = new BufferedImage(scale * width, scale * height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = buffer.createGraphics();
            graphics.scale(scale, scale);
            image.paintIcon(null, graphics, 0, 0);
            graphics.dispose();

            lblImagen.setHorizontalAlignment(JLabel.CENTER);
            lblImagen.setVerticalAlignment(JLabel.CENTER);
            lblImagen.setIcon(image);
        } else {
            lblImagen.setBackground(Color.GRAY);
        }
    }

    private void login(String user, String pass) {
        if (contParamAplicacion.cantUsers()) {
            if (contUsuario.login(user, pass)) {
                menu();
                this.dispose();
            } else {
                if (user.equals("USRINSTALL") && pass.equals("USRINSTALL")) {
                    ContParamAplicacion.EMP_IDENTIFICADOR = 1;
                    ContParamAplicacion.SUC_IDENTIFICADOR = 1;
                    ContParamAplicacion.UND_IDENTIFICADOR = 1;
                    ContFndUsuarios.USR_NOMBRE = "USRINSTALL";

                    FrmImportarParametros frm = new FrmImportarParametros();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);

                } else {
                    ControlMensajes.error("Credenciales invalidas", titulo);
                    cancelar();
                    txtUser.requestFocus();
                }
            }
        } else {
            if (contUsuario.login(user, pass)) {
                menu();
                this.dispose();
            } else {
                if (user.equals("USRINSTALL") && pass.equals("USRINSTALL")) {
                    ContParamAplicacion.EMP_IDENTIFICADOR = 1;
                    ContParamAplicacion.SUC_IDENTIFICADOR = 1;
                    ContParamAplicacion.UND_IDENTIFICADOR = 1;
                    ContFndUsuarios.USR_NOMBRE = "USRINSTALL";

                    FrmImportarParametros frm = new FrmImportarParametros();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                    this.dispose();
                } else {
                    ControlMensajes.informacion("Debe ingresar como usuario de instalacion \n para importar los parametros", titulo);
                    cancelar();
                    txtUser.requestFocus();
                }
            }

        }
    }

    private void parametrosApp() {
        contParamAplicacion.getParamCaja();
        contParamAplicacion.getParamPuntoEmision();
        contParamAplicacion.getParamEmpresa();
        contParamAplicacion.getParamSucursal();
        contParamAplicacion.getParamUnidad();

        lblEmpresa.setText(ContParamAplicacion.EMP_NOMBRE + " - " + ContParamAplicacion.CAJA_DESCRIPCION);
        lblCaja.setText("PUNTO DE VENTAS v" + POS.VERSION_SOFTWARE);
    }

    private void menu() {
        switch (ContFndUsuarios.USR_ID_PERFIL) {
            case 1:
                menuAdministrador();
                this.dispose();
                break;
            case 7:
                if (!contControlCaja.valApertura()) {
                    if (contControlCaja.userApertura(contControlCaja.idApertura()).equals(txtUser.getText())) {
                        menuCajero();
                        this.dispose();
                    } else {
                        if (contControlCaja.userApertura(contControlCaja.idApertura()).equals("")) {
                            menuCajero();
                            this.dispose();
                        } else {
                            ControlMensajes.error("Debe ingresar con el usuario aperturado", titulo);
                            txtUser.requestFocus();
                        }
                    }
                } else {
                    menuCajero();
                    this.dispose();
                }

                break;

            default:
                ControlMensajes.error("El usuario no tiene permiso de acceso", titulo);
                txtUser.setText("");
                txtPass.setText("");
                txtUser.requestFocus();
                break;
        }
    }

    private void menuCajero() {
        FrmMenuCajero frm = new FrmMenuCajero();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void menuAdministrador() {
        FrmMenuAdministrador frm = new FrmMenuAdministrador();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void cancelar() {
        System.exit(0);
    }

    private void cerrar() {
        if (ControlMensajes.confirmacion("Deseas sair del sistema", titulo)) {
            System.exit(0);
        } else {
            txtUser.requestFocus();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        lblCaja = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::ACCESO AL SISTEMA::.");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Clave de Acceso....");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 310, -1));

        txtUser.setBackground(new java.awt.Color(0, 9, 18));
        txtUser.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtUser.setForeground(new java.awt.Color(255, 255, 255));
        txtUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUser.setCaretColor(new java.awt.Color(255, 255, 255));
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });
        getContentPane().add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 310, 35));

        txtPass.setBackground(new java.awt.Color(0, 9, 18));
        txtPass.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtPass.setForeground(new java.awt.Color(255, 255, 255));
        txtPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPass.setCaretColor(new java.awt.Color(255, 255, 255));
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassKeyPressed(evt);
            }
        });
        getContentPane().add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 310, 35));

        btnAceptar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(0, 0, 0));
        btnAceptar.setText("ACEPTAR");
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
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 154, 40));

        btnCancelar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("CANCELAR");
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
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 150, 40));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Nombre de Usuario....");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 310, -1));

        lblImagen.setBackground(java.awt.Color.darkGray);
        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setOpaque(true);
        getContentPane().add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 150, 140));

        lblCaja.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCaja.setForeground(new java.awt.Color(255, 255, 255));
        lblCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja.setText("PUNTO DE VENTAS");
        lblCaja.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(lblCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 520, -1));

        lblEmpresa.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpresa.setText("NOMBRE DE EMPRESA");
        lblEmpresa.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(lblEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 520, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login(txtUser.getText(), txtPass.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cancelar();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        txtUser.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                txtPass.requestFocus();
                break;
            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
            case KeyEvent.VK_TAB:
                txtPass.requestFocus();
                break;

        }
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAceptar.requestFocus();
            btnAceptarKeyPressed(evt);
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrar();
        }
    }//GEN-LAST:event_txtPassKeyPressed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        if (evt.getClickCount() == 1) {
            login(txtUser.getText(), txtPass.getText());
        }
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        if (evt.getClickCount() == 1) {
            cancelar();
        }
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblCaja;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

}
