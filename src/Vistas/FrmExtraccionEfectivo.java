package Vistas;

import Controladores.ContFndUsuarios;
import Controladores.ContParamAplicacion;
import Controladores.ContTcControlCaja;
import Modelos.TcDetalleExtrEfec;
import Controladores.ContTcExtrEfectivoCaja;
import Utils.FuncionesBd;
import Utils.ControlMensajes;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class FrmExtraccionEfectivo extends javax.swing.JFrame {

    private final String titulo = "Extracciones";

    TcDetalleExtrEfec dtsExtEfect = new TcDetalleExtrEfec();

    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();
    ContTcExtrEfectivoCaja contExtrEfectivo = new ContTcExtrEfectivoCaja();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();

    public FrmExtraccionEfectivo() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();
        this.setTitle(titulo);
    }

    private void inicio() {
        txtCaja.setText(String.valueOf(ContParamAplicacion.CAJA_DESCRIPCION));
        txtNroCaja.setText(ContParamAplicacion.CAJA_NUMERO);
        txtNroControl.setText(String.valueOf(contControlCaja.idApertura()));
        txtIdTurno.setText(String.valueOf(ContParamAplicacion.TCJ_IDENTIFICADOR));
        txtTurno.setText(ContParamAplicacion.TCJ_NOMBRE);
        txtNroExtraccion.setText(String.valueOf(contExtrEfectivo.identificador()));
        txtFecha.setText(String.valueOf(FuncionesBd.sysdate()));

        lblDatos.setText(ContFndUsuarios.USR_NOMBRE_COMPLETO + "-" + ContParamAplicacion.CAJA_DESCRIPCION);
        cboTipoExtraccion.requestFocus();
    }

    private void declararEfectivo() {
        if (!txtNroDocBeneficiario.getText().isEmpty()) {
            ControlMensajes.error("Debe ingresar nro. de documento del beneficiario", titulo);
            return;
        }
        if (!txtNombreBeneficiario.getText().isEmpty()) {
            ControlMensajes.error("Debe ingresar nombre del beneficiario", titulo);
            return;
        }
        
        FrmExtraccionEfectivoDetalle frm = new FrmExtraccionEfectivoDetalle();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);

        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblDatos = new javax.swing.JLabel();
        btnDecEfectivo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        txtCaja = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNroCaja = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTurno = new javax.swing.JTextField();
        txtIdTurno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNroControl = new javax.swing.JTextField();
        txtNroExtraccion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cboTipoExtraccion = new javax.swing.JComboBox<>();
        txtNombreBeneficiario = new javax.swing.JTextField();
        txtNroDocBeneficiario = new javax.swing.JTextField();
        txtNroReferencia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::EXTRACCION DE EFECTIVO::.");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblDatos.setBackground(new java.awt.Color(0, 0, 0));
        lblDatos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblDatos.setForeground(new java.awt.Color(204, 204, 0));
        lblDatos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDatos.setText("USUARIO - NRO. CAJA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblDatos)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        btnDecEfectivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDecEfectivo.setForeground(new java.awt.Color(0, 0, 0));
        btnDecEfectivo.setText("Dec. Efectivo");
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

        btnSalir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
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

        txtCaja.setEditable(false);
        txtCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtCaja.setForeground(java.awt.Color.white);
        txtCaja.setCaretColor(java.awt.Color.white);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setText("Caja:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Nro. Caja:");

        txtNroCaja.setEditable(false);
        txtNroCaja.setBackground(new java.awt.Color(0, 9, 18));
        txtNroCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroCaja.setForeground(java.awt.Color.white);
        txtNroCaja.setCaretColor(java.awt.Color.white);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Turno:");

        txtTurno.setEditable(false);
        txtTurno.setBackground(new java.awt.Color(0, 9, 18));
        txtTurno.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtTurno.setForeground(java.awt.Color.white);
        txtTurno.setCaretColor(java.awt.Color.white);

        txtIdTurno.setEditable(false);
        txtIdTurno.setBackground(new java.awt.Color(0, 9, 18));
        txtIdTurno.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtIdTurno.setForeground(java.awt.Color.white);
        txtIdTurno.setCaretColor(java.awt.Color.white);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Nro. Control:");

        txtNroControl.setEditable(false);
        txtNroControl.setBackground(new java.awt.Color(0, 9, 18));
        txtNroControl.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroControl.setForeground(java.awt.Color.white);
        txtNroControl.setCaretColor(java.awt.Color.white);

        txtNroExtraccion.setEditable(false);
        txtNroExtraccion.setBackground(new java.awt.Color(0, 9, 18));
        txtNroExtraccion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroExtraccion.setForeground(java.awt.Color.white);
        txtNroExtraccion.setCaretColor(java.awt.Color.white);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setText("Nro. Extraccion:");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(java.awt.Color.white);
        jLabel10.setText("Comentario:");

        txtComentario.setBackground(new java.awt.Color(0, 9, 18));
        txtComentario.setColumns(20);
        txtComentario.setRows(5);
        txtComentario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtComentarioKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtComentario);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(java.awt.Color.white);
        jLabel11.setText("Tipo Extraccion:");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(java.awt.Color.white);
        jLabel12.setText("Nombre Beneficiario:");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(java.awt.Color.white);
        jLabel13.setText("Nro. Doc. Beneficiario:");

        cboTipoExtraccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cboTipoExtraccion.setForeground(new java.awt.Color(255, 255, 255));
        cboTipoExtraccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NORMAL", "PAGOS VARIOS", "DEVOLUCION EFECTIVO" }));
        cboTipoExtraccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboTipoExtraccionKeyPressed(evt);
            }
        });

        txtNombreBeneficiario.setBackground(new java.awt.Color(0, 9, 18));
        txtNombreBeneficiario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNombreBeneficiario.setForeground(java.awt.Color.white);
        txtNombreBeneficiario.setCaretColor(java.awt.Color.white);
        txtNombreBeneficiario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreBeneficiarioKeyPressed(evt);
            }
        });

        txtNroDocBeneficiario.setBackground(new java.awt.Color(0, 9, 18));
        txtNroDocBeneficiario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroDocBeneficiario.setForeground(java.awt.Color.white);
        txtNroDocBeneficiario.setCaretColor(java.awt.Color.white);
        txtNroDocBeneficiario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroDocBeneficiarioKeyPressed(evt);
            }
        });

        txtNroReferencia.setBackground(new java.awt.Color(0, 9, 18));
        txtNroReferencia.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroReferencia.setForeground(java.awt.Color.white);
        txtNroReferencia.setCaretColor(java.awt.Color.white);
        txtNroReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroReferenciaKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(java.awt.Color.white);
        jLabel14.setText("Nro. Referencia:");

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(0, 9, 18));
        txtFecha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtFecha.setForeground(java.awt.Color.white);
        txtFecha.setCaretColor(java.awt.Color.white);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(btnDecEfectivo)
                                .addGap(191, 191, 191)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCaja)
                                            .addComponent(txtNroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNroControl, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboTipoExtraccion, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtNroExtraccion, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtNroDocBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNroReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 94, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtIdTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroControl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroExtraccion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboTipoExtraccion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroDocBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNombreBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNroReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDecEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDecEfectivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDecEfectivoMouseClicked
        if (evt.getClickCount() == 1) {
            declararEfectivo();
        }
    }//GEN-LAST:event_btnDecEfectivoMouseClicked

    private void btnDecEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDecEfectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            declararEfectivo();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnDecEfectivoKeyPressed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        if (evt.getClickCount() == 1) {
            this.dispose();
        } else {

        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void cboTipoExtraccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboTipoExtraccionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNroDocBeneficiario.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_cboTipoExtraccionKeyPressed

    private void txtNroDocBeneficiarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroDocBeneficiarioKeyPressed
        txtNroDocBeneficiario.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_TAB:
                if (!txtNroDocBeneficiario.getText().isEmpty()) {
                    txtNombreBeneficiario.requestFocus();
                } else {
                    ControlMensajes.error("Debe ingresar nro. de doc. del beneficiario", titulo);
                }
                break;
            case KeyEvent.VK_ENTER:
                if (!txtNroDocBeneficiario.getText().isEmpty()) {
                    txtNombreBeneficiario.requestFocus();
                } else {
                    ControlMensajes.error("Debe ingresar nro. de doc. del beneficiario", titulo);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtNroDocBeneficiarioKeyPressed

    private void txtNombreBeneficiarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreBeneficiarioKeyPressed
        txtNombreBeneficiario.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, java.util.Collections.EMPTY_SET);
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (!txtNombreBeneficiario.getText().isEmpty()) {
                    txtNroReferencia.requestFocus();
                } else {
                    ControlMensajes.error("Debe ingresar nombre del beneficiario", titulo);
                }
                break;
            case KeyEvent.VK_TAB:
                if (!txtNombreBeneficiario.getText().isEmpty()) {
                    txtNroReferencia.requestFocus();
                } else {
                    ControlMensajes.error("Debe ingresar nombre del beneficiario", titulo);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtNombreBeneficiarioKeyPressed

    private void txtNroReferenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroReferenciaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtComentario.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_txtNroReferenciaKeyPressed

    private void txtComentarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComentarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDecEfectivo.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_txtComentarioKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmExtraccionEfectivo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDecEfectivo;
    private javax.swing.JButton btnSalir;
    public static javax.swing.JComboBox<String> cboTipoExtraccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblDatos;
    public static javax.swing.JTextField txtCaja;
    private javax.swing.JTextArea txtComentario;
    public static javax.swing.JTextField txtFecha;
    public static javax.swing.JTextField txtIdTurno;
    public static javax.swing.JTextField txtNombreBeneficiario;
    public static javax.swing.JTextField txtNroCaja;
    public static javax.swing.JTextField txtNroControl;
    public static javax.swing.JTextField txtNroDocBeneficiario;
    public static javax.swing.JTextField txtNroExtraccion;
    public static javax.swing.JTextField txtNroReferencia;
    public static javax.swing.JTextField txtTurno;
    // End of variables declaration//GEN-END:variables

}
