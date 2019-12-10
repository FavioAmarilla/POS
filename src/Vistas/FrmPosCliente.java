package Vistas;

import Utils.ControlMensajes;
import Conexiones.ConexionRs;
import Controladores.ContParamAplicacion;
import Modelos.VtaComprobantes;
import Controladores.ContVtaClientes;
import Controladores.ContVtaComprobantes;
import Controladores.ContVtaParametros;
import Modelos.StkItemsMvStock;
import Modelos.StkParametros;
import Modelos.VtaParametros;
import Pos.POS;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FrmPosCliente extends javax.swing.JDialog implements Runnable {

    Thread hilo = new Thread(this);

    VtaComprobantes dtsComp = new VtaComprobantes();

    ContVtaClientes contCliente = new ContVtaClientes();
    ContVtaComprobantes contComprobante = new ContVtaComprobantes();
    StkParametros stkParametros = new StkParametros();
    ContVtaParametros contVtaParametros = new ContVtaParametros();
    VtaParametros vtaParametros = new VtaParametros();

    public static long idCliente = 0;
    public long idVenta = 0;
    public static String razonSocial = "";
    public static String atributo2 = "";
    public static String direccion = "";
    public static String telefono = "";
    public static String nroRuc = "";
    public String nroComp = "";

    public double montoGravado5;
    public double montoGravado10;
    public double montoImpuesto;
    public double montoImpuesto5;
    public double montoImpuesto10;

    public ArrayList<StkItemsMvStock> listaProductos = null;

    public FrmPosCliente() {
        initComponents();
        setModal(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);

        imagen();
    }

    public void setStkParametros(StkParametros value) {
        this.stkParametros = value;
    }

    public void setTablaProductos(ArrayList<StkItemsMvStock> values) {
        listaProductos = values;
    }

    public boolean getParametrosVenta() {
        vtaParametros = contVtaParametros.getParametrosVenta(ContParamAplicacion.SUC_IDENTIFICADOR);
       
        if (vtaParametros != null) {
            return true;
        } else {
            ControlMensajes.error("Parametros de stock no definidos", "Menu cajero");
            return false;
        }
    }

    private void imagen() {
        File archivo = new File(POS.IMG_LOGO_POS);
        if (archivo.exists()) {
            ImageIcon icon = new ImageIcon(POS.IMG_LOGO_POS);

            int scale = 3;
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            BufferedImage buffer = new BufferedImage(scale * width, scale * height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = buffer.createGraphics();
            graphics.scale(scale, scale);
            icon.paintIcon(null, graphics, 0, 0);
            graphics.dispose();

            logoEmpresaClientes.setHorizontalAlignment(JLabel.CENTER);
            logoEmpresaClientes.setVerticalAlignment(JLabel.CENTER);
            logoEmpresaClientes.setIcon(icon);
        } else {
            logoEmpresaClientes.setBackground(Color.GRAY);
        }
    }

    public void contCliente(String ruc) {
        String nombres = "";
        String apellidos = "";
        String razonJuridica = "";
        try {
            ConexionRs cnRs = new ConexionRs();

            String sql = "SELECT IDENTIFICADOR, COALESCE(NOMBRES,'-'), COALESCE(APELLIDOS,'-'), "
                    + "COALESCE(DIRECCION,'-'), COALESCE(TELEFONO,'-'),COALESCE(RAZON_SOCIAL,'-'), NUMERO_RUC "
                    + "FROM VTA_CLIENTES WHERE NUMERO_RUC LIKE '" + ruc + "%'";

            ResultSet rs = cnRs.consultar(sql);
            if (rs.next()) {
                idCliente = rs.getLong(1);
                nombres = rs.getString(2);
                apellidos = rs.getString(3);
                razonJuridica = rs.getString(6);

                if (nombres.equals("-") && apellidos.equals("-")) {
                    txtNombres.setText(razonJuridica);
                    txtApellidos.setText("");
                } else {
                    txtNombres.setText(nombres);
                    txtApellidos.setText(apellidos);
                }
                txtDireccion.setText(rs.getString(4));
                txtTelefono.setText(rs.getString(5));
                txtRuc.setText(rs.getString(7));
                atributo2 = null;
                txtNombres.requestFocus();
                cnRs.cerrarConexion();
            } else {
                sql = "SELECT NOMBRE_COMPLETO,RUC_NUEVO FROM CP_EQUIVALENCIA_RUC WHERE RUC_NUEVO LIKE '" + ruc + "%'";
                rs = cnRs.consultar(sql);
                if (rs.next()) {
                    idCliente = 1425;
                    txtNombres.setText(rs.getString(1));
                    txtRuc.setText(rs.getString(2));
                    atributo2 = null;
                    txtNombres.requestFocus();
                } else {
                    ControlMensajes.informacion("CLIENTE NO REGISTRADO", "PUNTO DE VENTAS");
                    System.out.println("Cliente no registrado: " + txtRuc.getText());

                    txtNombres.setText("");
                    txtApellidos.setText("");
                    txtDireccion.setText("");
                    txtTelefono.setText("");

                    txtNombres.requestFocus();
                }
                cnRs.cerrarConexion();
            }

        } catch (SQLException ex) {
            Logger.getLogger(FrmPosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nuevoCliente() {
        FrmPosClienteNuevo frm = new FrmPosClienteNuevo();
        frm.setVisible(true);
    }

    private void buscarCliente() {
        FrmVistaClientes frm = new FrmVistaClientes();
        frm.setVisible(true);
    }

    private boolean pago() {
        if (txtNombres.getText().isEmpty()) {
            ControlMensajes.error("DEBE INGRESAR CLIENTE", "PUNTO DE VENTAS");
            txtRuc.requestFocus();
            return false;
        }

        Thread hilo_pago = new Thread(() -> {
            razonSocial = txtNombres.getText() + " " + txtApellidos.getText();
            direccion = txtDireccion.getText();
            telefono = txtTelefono.getText();
            nroRuc = txtRuc.getText();

            System.out.println("\tBASE DE DATOS LOCAL");
            System.out.println("\tCliente asignado a comprobante: " + nroComp);
            System.out.println("\tNombre.: " + razonSocial);
            System.out.println("\tRuc....: " + nroRuc);

            FrmPosCobros.idVenta = idVenta;
            FrmPosCobros.nroComprobante = nroComp;
            FrmPosCobros frm = new FrmPosCobros();
            frm.setTablaProductos(listaProductos);
            frm.setStkParametros(stkParametros);
            frm.setRedondeoSedeco();
            frm.setVisible(true);
        });
        hilo_pago.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmPosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.dispose();
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        logoEmpresaClientes = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(".::DATOS DEL CLIENTE::.");
        setIconImage(null);
        setLocation(new java.awt.Point(0, 0));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("R.U.C.:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nombre/s:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Direccion:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Telefono:");

        txtRuc.setBackground(new java.awt.Color(0, 9, 18));
        txtRuc.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtRuc.setForeground(new java.awt.Color(255, 255, 255));
        txtRuc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucKeyPressed(evt);
            }
        });

        txtNombres.setBackground(new java.awt.Color(0, 9, 18));
        txtNombres.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtNombres.setForeground(new java.awt.Color(255, 255, 255));
        txtNombres.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombresKeyPressed(evt);
            }
        });

        txtDireccion.setBackground(new java.awt.Color(0, 9, 18));
        txtDireccion.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(255, 255, 255));
        txtDireccion.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
        });

        txtTelefono.setBackground(new java.awt.Color(0, 9, 18));
        txtTelefono.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefono.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
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

        logoEmpresaClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnAceptar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
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

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Apellidos/s:");

        txtApellidos.setBackground(new java.awt.Color(0, 9, 18));
        txtApellidos.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtApellidos.setForeground(new java.awt.Color(255, 255, 255));
        txtApellidos.setCaretColor(new java.awt.Color(255, 255, 255));
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtApellidosKeyPressed(evt);
            }
        });

        btnNuevo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevo.setText("Registrar (F8)");
        btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoMouseClicked(evt);
            }
        });
        btnNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnNuevoKeyPressed(evt);
            }
        });

        btnClientes.setText("...");
        btnClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClientesMouseClicked(evt);
            }
        });
        btnClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnClientesKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logoEmpresaClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(5, 5, 5)
                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logoEmpresaClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (txtRuc.getText().isEmpty()) {
                    txtNombres.requestFocus();
                } else {
                    contCliente(txtRuc.getText());
                }
                break;
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtRucKeyPressed

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                pago();
                break;
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        if (evt.getClickCount() == 1) {
            nuevoCliente();
        }
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        if (evt.getClickCount() == 1) {
            pago();
        }
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        if (evt.getClickCount() == 1) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnNuevoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnNuevoKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                nuevoCliente();
                break;
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnNuevoKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnClientesKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                buscarCliente();
                break;
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btnClientesKeyPressed

    private void btnClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseClicked
        if (evt.getClickCount() == 1) {
            buscarCliente();
        }
    }//GEN-LAST:event_btnClientesMouseClicked

    private void txtNombresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (txtNombres.getText().isEmpty()) {
                    if (getParametrosVenta()) {    
                        idCliente = vtaParametros.getId_cliente_pos();
                        txtRuc.setText("XXXXXX");
                        txtNombres.setText(vtaParametros.getNombre_clte_generico());
                        atributo2 = "CLIENTES NO IDENTIFICADOS";
                    }
                }
                txtApellidos.requestFocus();
                break;
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtNombresKeyPressed

    private void txtApellidosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                btnAceptarKeyPressed(evt);
                break;
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtApellidosKeyPressed

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F8:
                nuevoCliente();
                break;
            case KeyEvent.VK_F9:
                buscarCliente();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtTelefonoKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPosCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel logoEmpresaClientes;
    public static javax.swing.JTextField txtApellidos;
    public static javax.swing.JTextField txtDireccion;
    public static javax.swing.JTextField txtNombres;
    public static javax.swing.JTextField txtRuc;
    public static javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        pago();
    }

}
