package Vistas;

import Controladores.ContAppVtaPromociones;
import java.awt.event.KeyEvent;
import Utils.FormatosTicket;
import Utils.ControlMensajes;
import Utils.Utilidades;
import Controladores.ContTcControlCaja;
import Modelos.VtaComprobantes;
import Controladores.ContExportarTransacciones;
import Controladores.ContFndUsuarios;
import Controladores.ContParamAplicacion;
import static Vistas.FrmLogin.idTipoComprob;
import static Vistas.FrmLogin.tipoComprob;
import Controladores.ContVtaClientes;
import Controladores.ContVtaComprobantes;
import Controladores.ContPrProductos;
import Controladores.ContStkParametros;
import Controladores.ContVtaControlComprob;
import Controladores.ContVtaParametros;
import Modelos.StkParametros;
import Modelos.VtaParametros;
import Pos.POS;
import Utils.ControlImpresora;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FrmMenuCajero extends javax.swing.JFrame {

    ControlImpresora impresora = new ControlImpresora();
    FormatosTicket ticket = new FormatosTicket();

    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    ContPrProductos contProducto = new ContPrProductos();
    ContExportarTransacciones contExportarReferencias = new ContExportarTransacciones();
    ContVtaClientes contCliente = new ContVtaClientes();
    ContVtaComprobantes contVentas = new ContVtaComprobantes();
    ContStkParametros contStkParametros = new ContStkParametros();
    StkParametros stkParametros = new StkParametros();
    ContVtaParametros contVtaParametros = new ContVtaParametros();
    VtaParametros vtaParametros = new VtaParametros();

    VtaComprobantes dtsComp = new VtaComprobantes();

    int segundos;
    Thread hiloLabel;

    public FrmMenuCajero() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        listaMenu.requestFocus();
        lblUsuario.setText(ContFndUsuarios.USR_NOMBRE_COMPLETO);
        lblCaja.setText(ContParamAplicacion.CAJA_DESCRIPCION);
        listaMenu.setSelectedIndex(0);

        imagen();
    }

    private boolean getParametrosStock() {
        stkParametros = contStkParametros.getParametrosStock(ContParamAplicacion.SUC_IDENTIFICADOR);

        if (stkParametros != null) {
            return true;
        } else {
            ControlMensajes.error("Parametros de stock no definidos", "Menu cajero");
            return false;
        }
    }

    public boolean getParametrosVenta() {
        vtaParametros = contVtaParametros.getParametrosVenta(ContParamAplicacion.SUC_IDENTIFICADOR);

        if (vtaParametros != null) {
            return true;
        } else {
            ControlMensajes.error("Parametros de venta no definidos", "Menu cajero");
            return false;
        }
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

    public void cancelarComprobante() {
        if (!impresora.habilitado()) {
            ControlMensajes.error("Impresora no disponible", "MENU DEL CAJERO");
        }

        System.out.println("<INICIO PROCESO DE CANCELACION DE COMPROBANTE>");
        if (ControlMensajes.confirmacion("EL ULTIMO COMPROBANTE EMITIDO TIENE ERRORES: " + contVentas.comprobante + "\n DESEAS ANULAR", "Menu cajero")) {
            dtsComp.setUsrAnulacion(ContFndUsuarios.USR_NOMBRE);
            dtsComp.setFeAnulacion(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsComp.setDescripcion("CANCELACION A PEDIDO DEL CLIENTE");
            dtsComp.setIdentificador(contVentas.idComprobante);

            System.out.println("\tBASE DE DATOS LOCAL");
            if (contVentas.cancelarComp(dtsComp, 0)) {
                System.out.println("\tComprobante cancelado: " + contVentas.comprobante);
                ControlMensajes.informacion("COMPROBANTE CANCELADO, COMPROBANTE NRO.: " + contVentas.comprobante, "Menu cajero");
                cancelacionTicket();
                pos();
                FrmPos.reiniciarValores();
                this.dispose();
            } else {
                ControlMensajes.informacion("COMPROBANTE NO CANCELADO, COMPROBANTE NRO.: " + contVentas.comprobante, "Menu cajero");
                System.out.println("\tComprobante no cancelado: " + contVentas.comprobante);
            }

        } else {
            ControlMensajes.error("COMPROBANTE NO CANCELADO, COMPROBANTE NRO.: " + contVentas.comprobante, "Menu cajero");
            System.out.println("\tComprobante no cancelado: " + contVentas.comprobante);
            listaMenu.requestFocus();
        }
        System.out.println("<FIN PROCESO DE CANCELACION DE COMPROBANTE>");
    }

    private void autorizacion(String form) {
        FrmAutorizacion frm = new FrmAutorizacion();
        frm.formulario = form;
        frm.setVisible(true);
    }

    private boolean validacionePOS() {

        if (!contParamAplicacion.getParamPuntoEmision()) {
            listaMenu.requestFocus();
            return false;
        }

        if (!contParamAplicacion.getParamComprobante()) {
            listaMenu.requestFocus();
            return false;
        }

        if (!contParamAplicacion.getTurnosCaja()) {
            listaMenu.requestFocus();
            return false;
        }

        if (!contParamAplicacion.getLimiteComp()) {
            listaMenu.requestFocus();
            return false;
        }
        if (!contControlComprob.validarTimbrado(Utilidades.getFecha("dd/MM/yyyy"))) {
            ControlMensajes.error("Timbrado no definido", "Menu cajero");
            listaMenu.requestFocus();
            return false;
        }
        if (!contControlComprob.validarPeriodoGestion(Utilidades.getFecha("dd/MM/yyyy"))) {
            ControlMensajes.error("Periodo administrativo no definido", "Menu cajero");
            listaMenu.requestFocus();
            return false;
        }
        if (contControlCaja.valCierre()) {
            ControlMensajes.error("Existen controles anteriores que no han sido cerrados", "Menu cajero");
            listaMenu.requestFocus();
            return false;
        }
        return true;
    }

    private void menu() {
        switch (String.valueOf(listaMenu.getSelectedIndex() + 1)) {
            case "1":
                if (impresora.habilitado()) {
                    if (validacionePOS()) {
                        btnApertura();
                    }
                } else {
                    ControlMensajes.error("Gaveta no disponible", "MENU DEL CAJERO");
                }
                break;

            case "2":
                if (impresora.habilitado()) {
                    if (validacionePOS()) {
                        if (getParametrosStock()) {
                            if (Utilidades.getPing()) {
                                ContAppVtaPromociones contAppVtaPromociones = new ContAppVtaPromociones();
                                contAppVtaPromociones.importarPromociones(1);
                            }
                            btnPos();
                        }
                    }
                } else {
                    ControlMensajes.error("Impresora no disponible", "MENU DEL CAJERO");
                }
                break;

            case "3":
                if (impresora.habilitado()) {
                    if (validacionePOS()) {
                        btnCierre();
                    }
                } else {
                    ControlMensajes.error("Impresora no disponible", "MENU DEL CAJERO");
                }
                break;

            case "4":
                if (impresora.habilitado()) {
                    if (validacionePOS()) {
                        btnExtraccion();
                    }
                } else {
                    ControlMensajes.error("Impresora no disponible", "MENU DEL CAJERO");
                }
                break;

            case "5":
                if (!impresora.habilitado()) {
                    ControlMensajes.error("Impresora no disponible", "MENU DEL CAJERO");
                    return;
                }
                if (!Utilidades.getPing()) {
                    ControlMensajes.error("Sin conexion a servidor", "MENU DEL CAJERO");
                    return;
                }

                Thread h = new Thread(() -> {
                    FrmAutorizacion form = new FrmAutorizacion();
                    form.setLocationRelativeTo(null);
                    form.formulario = "reimpresion";
                    form.setVisible(true);
                });
                h.start();

                break;

            case "6":
                if (impresora.habilitado()) {
                    FrmAutorizacion frm = new FrmAutorizacion();
                    frm.setLocationRelativeTo(null);
                    frm.formulario = "abrirGaveta";
                    frm.setVisible(true);
                } else {
                    ControlMensajes.error("Gaveta no disponible", "MENU DEL CAJERO");
                }
                break;

            case "7":
                login();
                break;

        }
    }

    private void cancelacionTicket() {
        String lineaImpresion = "";

        lineaImpresion = "\n";
        lineaImpresion += "\n";
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += ticket.centrar("CANCELACION") + "\n";
        lineaImpresion += ticket.centrar("Fecha: " + Utilidades.getFecha("dd/MM/yyyy hh:mm:ss")) + "\n";
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";

        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmPos.class.getName()).log(Level.SEVERE, null, ex);
        }

        ControlImpresora.imprimir(lineaImpresion);
        ControlImpresora.cortarPapel();
    }

    private void login() {
        FrmLogin frm = new FrmLogin();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);

        this.dispose();
    }

    private void pos() {
        ContVtaControlComprob contControlComprobCp = new ContVtaControlComprob();
        String[] tiposComprob = new String[2];
        tiposComprob = contControlComprobCp.tipoFactura();

        idTipoComprob = Integer.parseInt(tiposComprob[0]);
        tipoComprob = tiposComprob[1];

        FrmPos frm = new FrmPos();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        frm.setExtendedState(MAXIMIZED_BOTH);
    }

    private void btnApertura() {
        if (contControlCaja.valApertura()) {
            ControlMensajes.error("Ya existe un control de caja abierto para la fecha: " + Utilidades.getFecha("dd/MMM/yyyy"), "Menu cajero");
            listaMenu.requestFocus();
            return;
        }

        int[] ultimoNro = contControlComprob.ultimoNro();
        if (contControlComprob.existeComprobante(ultimoNro[1] + 1, ultimoNro[0])) {
            ControlMensajes.error("EL TICKET NRO.: --" + String.valueOf(ultimoNro[1] + 1) + "-- YA EXISTE", "Menu cajero");
            listaMenu.requestFocus();
            return;
        }

        FrmAperturaCaja frm = new FrmAperturaCaja();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        this.dispose();
    }

    private boolean btnPos() {
        if (!contControlCaja.valApertura()) {
            ControlMensajes.error("No se determino control de caja para la fecha: " + Utilidades.getFecha("dd/MM/yyyy"), "Menu cajero");
            listaMenu.requestFocus();
            return false;
        }
        int[] ultimoNro = contControlComprob.ultimoNro();
        int[] timbrado = contControlComprob.timbrado();
        if (contControlComprob.existeComprobante(ultimoNro[1] + 1, ultimoNro[0])) {
            ControlMensajes.error("El ticket numero --" + String.valueOf(ultimoNro[1] + 1) + "-- ya existe", "Menu cajero");
            return false;
        }

        if (ultimoNro[1] + 1 > ContParamAplicacion.VTA_ASIG_COMP_NUMERO_HASTA) {
            ControlMensajes.error("Numero maximo de comprobantes emitidos", "Menu cajero");
            return false;
        }
        if (Utilidades.getPing()) {
            contProducto.novedades("PR_PRODUCTOS");
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrmMenuCajero.class.getName()).log(Level.SEVERE, null, ex);
            }

            contProducto.novedades("PR_UM_PRODUCTO");
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrmMenuCajero.class.getName()).log(Level.SEVERE, null, ex);
            }

            contProducto.importarPreciosAplicar();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrmMenuCajero.class.getName()).log(Level.SEVERE, null, ex);
            }

            contCliente.novedades();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrmMenuCajero.class.getName()).log(Level.SEVERE, null, ex);
            }
            contCliente.Exportar();
        }
        //si existen ocmprobantes valida el ultimo que se emitio para ver si no tuvo errores
        //si no existen comprobantes no se valida nada y se abre el punto de ventas
        if (contVentas.validarComprobanteEmitido(timbrado[0])) {
            if (contVentas.comprobanteEmitido(timbrado[0])) {
                pos();
                this.dispose();
            } else {
                cancelarComprobante();
            }
        } else {
            pos();
            this.dispose();
        }

        return true;
    }

    private void btnCierre() {
        if (!contControlCaja.valApertura()) {
            ControlMensajes.error("No se determino control de caja para la fecha: " + Utilidades.getFecha("dd/MM/yyyy"), "Menu cajero");
            listaMenu.requestFocus();
            return;
        }
        autorizacion("cierre");
    }

    private void btnExtraccion() {
        if (!contControlCaja.valApertura()) {
            ControlMensajes.error("No se determino control de caja para la fecha: " + Utilidades.getFecha("dd/MM/yyyy"), "Menu cajero");
            listaMenu.requestFocus();
            return;
        }
        autorizacion("extraccion");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblCaja = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaMenu = new javax.swing.JList<>();
        logoEmpresaMenu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::MENU CAJERO::.");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblCaja.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCaja.setForeground(new java.awt.Color(204, 204, 0));
        lblCaja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCaja.setText("NUMERO DE CAJA");

        lblUsuario.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(204, 204, 0));
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("USUARIO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
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
            String[] strings = { "- Apertura Caja", "- Punto de Ventas", "- Cierre de Caja", "- Extraccion de Efectivo", "- Reimpresion de Ticket", "- Abrir Gaveta", "- Cerrar Sesion" };
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logoEmpresaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                        .addGap(12, 12, 12))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoEmpresaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        login();
    }//GEN-LAST:event_formWindowClosing

    private void listaMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMenuMouseClicked
        if (evt.getClickCount() == 1) {
            menu();
        }
    }//GEN-LAST:event_listaMenuMouseClicked

    private void listaMenuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listaMenuKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            menu();
        }
    }//GEN-LAST:event_listaMenuKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuCajero().setVisible(true);
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
