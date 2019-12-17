package Vistas;

import Controladores.ContFndUsuarios;
import Controladores.ContParamAplicacion;
import Pos.POS;
import Utils.ControlImpresora;
import Utils.ControlMensajes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FrmMenuAdministrador extends javax.swing.JFrame {

    ControlImpresora impresora = new ControlImpresora();
    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();

    public FrmMenuAdministrador() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();
    }

    private void inicio() {
        listaMenu.requestFocus();
        lblUsuario.setText(ContFndUsuarios.USR_NOMBRE_COMPLETO);
        lblCaja.setText(ContParamAplicacion.CAJA_DESCRIPCION);
        listaMenu.setSelectedIndex(0);

        imagen();
    }

    private void imagen() {
        File archivo = new File(POS.IMG_LOGO_MENU);
        if (archivo.exists()) {
            ImageIcon icon = new ImageIcon(POS.IMG_LOGO_MENU);

            int scale = 3;
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            BufferedImage buffer = new BufferedImage(scale * width, scale * height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = buffer.createGraphics();
            graphics.scale(scale, scale);
            icon.paintIcon(null, graphics, 0, 0);
            graphics.dispose();

            logoEmpresaMenu.setHorizontalAlignment(JLabel.CENTER);
            logoEmpresaMenu.setVerticalAlignment(JLabel.CENTER);
            logoEmpresaMenu.setIcon(icon);
        } else {
            logoEmpresaMenu.setBackground(Color.GRAY);
        }
    }

//    private boolean paramtrosApp() {
//        if (!contParamAplicacion.getParamPuntoEmision()) {
//            listaMenu.requestFocus();
//            return false;
//        }
//
//        if (!contParamAplicacion.getParamComprobante()) {
//            listaMenu.requestFocus();
//            return false;
//        }
//
//        if (!contParamAplicacion.getTurnosCaja()) {
//            listaMenu.requestFocus();
//            return false;
//        }
//        return true;
//    }
    private void menu() {
        switch (String.valueOf(listaMenu.getSelectedIndex() + 1)) {
            case "1":
                //if (paramtrosApp()) {
                impParametrosPos();
                //}
                break;

            case "2":
                //if (paramtrosApp()) {
                expRefencias();
                // }
                break;

            case "3":
                if (!impresora.habilitado() || !impresora.habilitado() || !impresora.habilitado()) {
                    ControlMensajes.error("GAVETA NO DISPONIBLE", "MENU DEL CAJERO");
                    return;
                }
                FrmAutorizacion frm = new FrmAutorizacion();
                frm.setLocationRelativeTo(null);
                frm.formulario = "abrirGaveta";
                frm.setVisible(true);
                break;

            case "4":
                login();
                this.dispose();
                break;

            default:
                ControlMensajes.error("Opcion Desconocida", "Menu Cajero");
                break;
        }
    }

    private void cerrar() {
        this.dispose();
    }

    private void login() {
        FrmLogin frm = new FrmLogin();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void impParametrosPos() {
        FrmImportarParametros frm = new FrmImportarParametros();
        frm.formulario = "menuAdmin";
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void expRefencias() {
        FrmExportarTransacciones frm = new FrmExportarTransacciones();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblCaja = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaMenu = new javax.swing.JList<>();
        logoEmpresaMenu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::MENU DE ADMINISTRADOR::.");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        lblUsuario.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(204, 204, 0));
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("USUARIO");

        lblCaja.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCaja.setForeground(new java.awt.Color(204, 204, 0));
        lblCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja.setText("NUMERO DE CAJA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCaja)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        listaMenu.setBackground(java.awt.Color.darkGray);
        listaMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0)));
        listaMenu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        listaMenu.setForeground(new java.awt.Color(255, 255, 255));
        listaMenu.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "- Importar Parametros", "- Exportar Referencias", "- Abrir Gaveta", "- Cerrar Sesion" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaMenu.setSelectionBackground(new java.awt.Color(0, 0, 0));
        listaMenu.setSelectionForeground(new java.awt.Color(204, 204, 0));
        listaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaMenuMouseClicked(evt);
            }
        });
        listaMenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listaMenuKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(listaMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logoEmpresaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoEmpresaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(124, 124, 124)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        login();
        cerrar();
    }//GEN-LAST:event_formWindowClosing

    private void listaMenuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listaMenuKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            menu();
        }
    }//GEN-LAST:event_listaMenuKeyPressed

    private void listaMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMenuMouseClicked
        if (evt.getClickCount() == 1) {
            menu();
        }
    }//GEN-LAST:event_listaMenuMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblCaja;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JList<String> listaMenu;
    private javax.swing.JLabel logoEmpresaMenu;
    // End of variables declaration//GEN-END:variables

}
