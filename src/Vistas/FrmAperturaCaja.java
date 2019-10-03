package Vistas;

import Modelos.TcControlCaja;
import Atxy2k.CustomTextField.RestrictedTextField;
import Controladores.ContFndUsuarios;
import Controladores.ContTcControlCaja;
import Utils.ControlMensajes;
import Controladores.ContParamAplicacion;
import Controladores.ContVtaControlComprob;
import Utils.ControlImpresora;
import Utils.Utilidades;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class FrmAperturaCaja extends javax.swing.JFrame {

    private final String titulo = "Apertura de caja";
    RestrictedTextField restricted;

    TcControlCaja dts = new TcControlCaja();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    int secuenciaCtrl = 0;
    int[] ultimoNro = contControlComprob.ultimoNro();

    public FrmAperturaCaja() {
        initComponents();
        this.setTitle(titulo);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();

        restricted = new RestrictedTextField(txtMontoEfectivo);
        restricted.setOnlyNums(true);
        restricted.setOnlyText(false);

        ControlImpresora.abrirGaveta();
        this.setTitle(titulo);
    }

    private void inicio() {
        txtNroCaja.setVisible(false);
        txtTurno.setText(ContParamAplicacion.TCJ_NOMBRE);
        lblUsuario.setText(ContFndUsuarios.USR_NOMBRE_COMPLETO);
        lblCaja.setText(ContParamAplicacion.CAJA_DESCRIPCION);
        txtIdentificador.setText(String.valueOf(contControlCaja.identificador()));
        txtCaja.setText(ContParamAplicacion.CAJA_DESCRIPCION);
        txtNroCaja.setText(ContParamAplicacion.CAJA_NUMERO);
        txtUsuario.setText(ContFndUsuarios.USR_NOMBRE);
        txtControlComp.setText(String.valueOf(ultimoNro[0]));
        txtSecuencia.setText(String.valueOf(contControlCaja.secuenciaControl()));
        txtNroTransInicial.setText(String.valueOf(ultimoNro[1] + 1));
        txtFechaApertura.setText(String.valueOf(contControlCaja.sysdate()));
        txtMontoEfectivo.requestFocus();
    }

    private void menuCajero() {
        FrmMenuCajero frm = new FrmMenuCajero();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void abrirCaja() {
        dts.setIdentificador(Long.parseLong(txtIdentificador.getText()));
        dts.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dts.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dts.setIdCaja(ContParamAplicacion.CAJA_IDENTIFICADOR);
        dts.setIdTurno(ContParamAplicacion.TCJ_IDENTIFICADOR);
        dts.setFecApertura(contControlCaja.sysdate());
        dts.setUsrApertura(ContFndUsuarios.USR_NOMBRE);
        dts.setIdMoneda(ContParamAplicacion.UND_ID_MONEDA);
        dts.setIdCajero(ContFndUsuarios.USR_ID_CAJERO);
        if (txtMontoEfectivo.getText().isEmpty()) {
            dts.setMontoEfectivo(0);
        } else {
            dts.setMontoEfectivo(Long.valueOf(txtMontoEfectivo.getText()));
        }
        dts.setArqueado("N");
        dts.setCerrado("N");
        dts.setNroInicial(Integer.parseInt(txtNroTransInicial.getText()));
        dts.setSecuencia(secuenciaCtrl);
        dts.setDescripcion(txtComentario.getText());

        if (Utilidades.getPing()) {
            contControlCaja.abrirCaja(dts, "S");
        }
        
        
        System.out.println("\tBASE DE DATOS LOCAL");
        if (contControlCaja.abrirCaja(dts, "L")) {
            ControlMensajes.informacion("Caja aperturada", titulo);
            System.out.println("\tApertura de caja realizada");
            btnSalir();
        } else {
            ControlMensajes.error("Caja no aperturada", titulo);
            System.out.println("\tApertura de caja no realizada");
        }

    }

    private void btnSalir() {
        menuCajero();
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNroCaja = new javax.swing.JTextField();
        txtCaja = new javax.swing.JTextField();
        txtFechaApertura = new javax.swing.JTextField();
        txtSecuencia = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblCaja = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtControlComp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNroTransInicial = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNroTransFinal = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtMontoEfectivo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtIdentificador = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        txtTurno = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::APERTURA DE CAJA::.");
        setBackground(java.awt.Color.darkGray);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Secuencia:");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Fecha Apertura:");

        txtNroCaja.setEditable(false);
        txtNroCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtNroCaja.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtNroCaja.setForeground(java.awt.Color.white);
        txtNroCaja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNroCaja.setCaretColor(java.awt.Color.white);

        txtCaja.setEditable(false);
        txtCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtCaja.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtCaja.setForeground(java.awt.Color.white);
        txtCaja.setCaretColor(java.awt.Color.white);

        txtFechaApertura.setEditable(false);
        txtFechaApertura.setBackground(new java.awt.Color(0, 9, 18));
        txtFechaApertura.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtFechaApertura.setForeground(java.awt.Color.white);
        txtFechaApertura.setCaretColor(java.awt.Color.white);

        txtSecuencia.setEditable(false);
        txtSecuencia.setBackground(new java.awt.Color(0, 9, 18));
        txtSecuencia.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtSecuencia.setForeground(java.awt.Color.white);
        txtSecuencia.setCaretColor(java.awt.Color.white);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

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
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        txtControlComp.setEditable(false);
        txtControlComp.setBackground(new java.awt.Color(0, 9, 18));
        txtControlComp.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtControlComp.setForeground(java.awt.Color.white);
        txtControlComp.setCaretColor(java.awt.Color.white);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Caja:");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Control Nro.:");

        txtNroTransInicial.setEditable(false);
        txtNroTransInicial.setBackground(new java.awt.Color(0, 9, 18));
        txtNroTransInicial.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtNroTransInicial.setForeground(java.awt.Color.white);
        txtNroTransInicial.setCaretColor(java.awt.Color.white);

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Transaccion Inicial:");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel8.setForeground(java.awt.Color.white);
        jLabel8.setText("Transaccion Final:");

        txtNroTransFinal.setEditable(false);
        txtNroTransFinal.setBackground(new java.awt.Color(0, 9, 18));
        txtNroTransFinal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtNroTransFinal.setForeground(java.awt.Color.white);
        txtNroTransFinal.setCaretColor(java.awt.Color.white);

        txtUsuario.setEditable(false);
        txtUsuario.setBackground(new java.awt.Color(0, 9, 18));
        txtUsuario.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtUsuario.setForeground(java.awt.Color.white);
        txtUsuario.setCaretColor(java.awt.Color.white);

        btnAceptar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(0, 0, 0));
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

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel9.setForeground(java.awt.Color.white);
        jLabel9.setText("Monto Efectivo");

        txtMontoEfectivo.setBackground(new java.awt.Color(0, 9, 18));
        txtMontoEfectivo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtMontoEfectivo.setForeground(java.awt.Color.white);
        txtMontoEfectivo.setCaretColor(java.awt.Color.white);
        txtMontoEfectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMontoEfectivoKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("Apertura Nro:");

        txtIdentificador.setEditable(false);
        txtIdentificador.setBackground(new java.awt.Color(0, 9, 18));
        txtIdentificador.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtIdentificador.setForeground(java.awt.Color.white);
        txtIdentificador.setCaretColor(java.awt.Color.white);

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("Comentario:");

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

        txtTurno.setEditable(false);
        txtTurno.setBackground(new java.awt.Color(0, 9, 18));
        txtTurno.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtTurno.setForeground(java.awt.Color.white);
        txtTurno.setCaretColor(java.awt.Color.white);

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("Turno:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(213, 213, 213)
                                        .addComponent(jLabel1))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTurno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUsuario))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNroTransFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNroTransInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtControlComp, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtMontoEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtNroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtIdentificador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(153, 153, 153))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaApertura, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtControlComp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSecuencia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroTransInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroTransFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMontoEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("<INICIO PROCESO DE APERTURA DE CAJA>");
            secuenciaCtrl = contControlCaja.secuenciaControl();
            abrirCaja();
            System.out.println("<FIN PROCESO DE APERTURA DE CAJA>");
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }

        btnAceptar.setEnabled(false);
        btnSalir.requestFocus();
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSalir();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }


    }//GEN-LAST:event_btnSalirKeyPressed

    private void txtMontoEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoEfectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtComentario.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_txtMontoEfectivoKeyPressed

    private void txtComentarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComentarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAceptar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_txtComentarioKeyPressed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        if (evt.getClickCount() == 1) {
            System.out.println("<INICIO PROCESO DE APERTURA DE CAJA>");
            secuenciaCtrl = contControlCaja.secuenciaControl();
            abrirCaja();
            System.out.println("<FIN PROCESO DE APERTURA DE CAJA>");
        }
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        btnSalir();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        btnSalir();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAperturaCaja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JTextField txtCaja;
    public static javax.swing.JTextArea txtComentario;
    private javax.swing.JTextField txtControlComp;
    private javax.swing.JTextField txtFechaApertura;
    private javax.swing.JTextField txtIdentificador;
    private javax.swing.JTextField txtMontoEfectivo;
    private javax.swing.JTextField txtNroCaja;
    private javax.swing.JTextField txtNroTransFinal;
    private javax.swing.JTextField txtNroTransInicial;
    private javax.swing.JTextField txtSecuencia;
    private javax.swing.JTextField txtTurno;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
