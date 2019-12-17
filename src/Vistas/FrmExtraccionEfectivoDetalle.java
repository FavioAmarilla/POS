package Vistas;

import Controladores.ContFndMonedas;
import Controladores.ContFndUsuarios;
import Controladores.ContImpresionCierreCaja;
import Controladores.ContParamAplicacion;
import Utils.FormatosTicket;
import Utils.Utilidades;
import Controladores.ContTcDenomMoneda;
import Utils.RenderTablaEfectivo;
import Modelos.TcDetalleExtrEfec;
import Controladores.ContTcExtrEfectivoCaja;
import Utils.ControlImpresora;
import Utils.ControlMensajes;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public final class FrmExtraccionEfectivoDetalle extends javax.swing.JFrame {

    private final String titulo = "Detalle de Extraccion";
    
    ControlImpresora impresora = new ControlImpresora();
    ContTcDenomMoneda contDenomMoneda = new ContTcDenomMoneda();
    ContFndMonedas contMonedas = new ContFndMonedas();
    ContTcExtrEfectivoCaja contExtrEfectivo = new ContTcExtrEfectivoCaja();
    ContImpresionCierreCaja contImpCierre = new ContImpresionCierreCaja();

    Connection cnn = null;
    DefaultTableModel tabla = new DefaultTableModel();
    FormatosTicket ticket = new FormatosTicket();
    DecimalFormat miles = new DecimalFormat("###,###.##");

    Thread hilo;
    String[] denominacion = new String[4];
    int contador = 0;
    int idExtraccion;
    int idControlCaja;

    TcDetalleExtrEfec dtsExtEfect = new TcDetalleExtrEfec();

    public FrmExtraccionEfectivoDetalle() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();
        this.setTitle(titulo);
    }

    private void inicio() {
        registrosTabla();
        ocultarColumnas();
        setCellRender(tablaEfectivo);
        tablaEfectivo.requestFocus();
        listenerTabla(tablaEfectivo);
        tablaEfectivo.getTableHeader().setReorderingAllowed(false);
        lblExtaccion.setText(FrmExtraccionEfectivo.txtNroExtraccion.getText());
        lblBeneficiario.setText(FrmExtraccionEfectivo.txtNombreBeneficiario.getText().toUpperCase());
    }

    private void registrosTabla() {
        try {
            tabla = contDenomMoneda.getDenominaciones();
            tablaEfectivo.setModel(tabla);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR(registrosTabla): " + e, titulo, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ocultarColumnas() {
        tablaEfectivo.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaEfectivo.getColumnModel().getColumn(0).setMinWidth(0);
        tablaEfectivo.getColumnModel().getColumn(0).setPreferredWidth(0);

        tablaEfectivo.getColumnModel().getColumn(1).setMaxWidth(0);
        tablaEfectivo.getColumnModel().getColumn(1).setMinWidth(0);
        tablaEfectivo.getColumnModel().getColumn(1).setPreferredWidth(0);

    }

    private void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new RenderTablaEfectivo());
        }
    }

    public static int totalValores() {
        int total = 0;
        for (int i = 0; i < tablaEfectivo.getRowCount(); i++) {
            total += Integer.parseInt(tablaEfectivo.getValueAt(i, 5).toString().replace(".", ""));
        }
        return total;
    }

    private boolean contExtrEfectivo(int serv) {
        dtsExtEfect.setIdentificador(Integer.parseInt(FrmExtraccionEfectivo.txtNroExtraccion.getText()));
        dtsExtEfect.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsExtEfect.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsExtEfect.setIdCaja(ContParamAplicacion.CAJA_IDENTIFICADOR);
        dtsExtEfect.setIdControl(Integer.parseInt(FrmExtraccionEfectivo.txtNroControl.getText()));
        dtsExtEfect.setNumero(FrmExtraccionEfectivo.txtNroExtraccion.getText());
        dtsExtEfect.setFecfecha(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsExtEfect.setUsrCre(ContFndUsuarios.USR_NOMBRE);
        dtsExtEfect.setFecCre(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsExtEfect.setDescripcion("");
        dtsExtEfect.setNroDocBeneficiario(FrmExtraccionEfectivo.txtNroDocBeneficiario.getText());
        dtsExtEfect.setNombreBeneficiario(FrmExtraccionEfectivo.txtNombreBeneficiario.getText());
        dtsExtEfect.setNroRefPago(FrmExtraccionEfectivo.txtNroReferencia.getText());
        dtsExtEfect.setVrTipoRetiro("");
        dtsExtEfect.setIdRefPago(0);

        return contExtrEfectivo.insert(dtsExtEfect, serv);
    }

    private boolean decEfectivo(int serv) {
        boolean insertado = false;

        for (int i = 0; i < tablaEfectivo.getRowCount(); i++) {
            dtsExtEfect.setIdExtraccion(Integer.parseInt(FrmExtraccionEfectivo.txtNroExtraccion.getText()));
            dtsExtEfect.setIdDenominacion(Integer.parseInt(tablaEfectivo.getValueAt(i, 0).toString().replace(".", "")));
            dtsExtEfect.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
            dtsExtEfect.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
            dtsExtEfect.setCantidad(Integer.parseInt(tablaEfectivo.getValueAt(i, 4).toString()));
            dtsExtEfect.setImporte(Integer.parseInt(tablaEfectivo.getValueAt(i, 5).toString().replace(".", "")));
            dtsExtEfect.setUsrCre(ContFndUsuarios.USR_NOMBRE);
            dtsExtEfect.setFecCre(Utilidades.getFecha("dd/MMM/yyyy"));

            insertado = contExtrEfectivo.detalleExtraccion(dtsExtEfect, serv);
        }
        return insertado;
    }

    private void btnConfirmar() {
        if (impresora.habilitado() && impresora.habilitado() && impresora.habilitado()) {
            if (totalValores() > 0) {
                System.out.println("<INICIO PROCESO DE EXTRACCION DE EFECTIVO>");
                ControlImpresora.abrirGaveta();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrmExtraccionEfectivoDetalle.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("\tBASE DE DATOS SERVER");
                if (Utilidades.getPing()) {
                    if (contExtrEfectivo(1)) {
                        System.out.println("\tExtraccion registrada");
                        if (decEfectivo(1)) {
                            System.out.println("\tEfectivos Registrados");
                        }
                    }
                }
                System.out.println("\tBASE DE DATOS LOCAL");
                if (contExtrEfectivo(0)) {
                    System.out.println("\tExtraccion registrada");
                    if (decEfectivo(0)) {
                        ControlMensajes.informacion("Extraccion realizada", titulo);
                        imprimir();
                        imprimirTerminal();
                        imprimir();

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ControlImpresora.cortarPapel();
                        this.dispose();
                        System.out.println("\tExtraccion de efectivo registrado");
                    } else {
                        System.out.println("\tExtraccion de efectivo no registrado");
                        ControlMensajes.error("Efectivo no registrado", titulo);
                    }
                } else {
                    System.out.println("\tExtraccion no registrada");
                    ControlMensajes.error("Extraccion no registrada", titulo);
                }
                System.out.println("<FIN PROCESO DE EXTRACCION DE EFECTIVO>");
            } else {
                ControlMensajes.error("Los valores deben ser mayor a cero(0)", titulo);
                tablaEfectivo.requestFocus();
            }
        } else {
            ControlMensajes.error("Impresora no disponible", "MENU DEL CAJERO");
            btnConfirmar.requestFocus();
        }
    }

    private String ticketExtraccion() {
        String texto = "";
        int valor = 0;
        int cant = 0;
        int subTotal = 0;
        int totalExtraccion = 0;

        texto += ticket.centrar(ContParamAplicacion.EMP_NOMBRE)
                + ticket.centrar(titulo)
                + "\n"
                + "\n"
                + "Extraccion Nro.: " + lblExtaccion.getText() + "\n"
                + "fecha: " + Utilidades.getFecha("dd/MM/yyyy hh:mm:ss") + "\n"
                + "Caja Nro.: " + ContParamAplicacion.CAJA_NUMERO + "\n"
                + "Cajero: " + ContFndUsuarios.USR_NOMBRE_COMPLETO.toUpperCase()
                + "\n"
                + "\n"
                + ticket.separador()
                + ticket.dobleColumna("Valor          Cantidad", "Importe")
                + ticket.separador();

        for (int i = 0; i < tablaEfectivo.getRowCount(); i++) {
            valor = Integer.parseInt(tablaEfectivo.getValueAt(i, 3).toString().replace(".", ""));
            cant = Integer.parseInt(tablaEfectivo.getValueAt(i, 4).toString());
            subTotal = Integer.parseInt(tablaEfectivo.getValueAt(i, 5).toString().replace(".", ""));
            if (cant > 0) {
                texto += ticket.dobleColumna(ticket.mediosPago(miles.format(valor), String.valueOf(cant)), miles.format(subTotal));
                totalExtraccion += subTotal;
            }

        }
        texto += ticket.separador()
                + ticket.dobleColumna("Total Extraccion:", miles.format(totalExtraccion))
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + ticket.separador()
                + "Recibido por \n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n";

        return texto;
    }

    private void imprimirTerminal() {
        System.out.println(ticketExtraccion());
    }

    private void imprimir() {
        try {
            FileOutputStream os = new FileOutputStream(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
            PrintStream ps = new PrintStream(os);

            ps.print(ticketExtraccion());
            os.close();
            ps.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmPos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrmPos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrEfectivo = new javax.swing.JScrollPane();
        tablaEfectivo = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex != 0 && colIndex != 1 && colIndex != 2 && colIndex != 3 && colIndex != 5;
            }

            public Class getColumnClass(int columna) {
                if (columna == 4) return Integer.class;
                return Object.class;
            }
        };
        btnSalir = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblExtaccion = new javax.swing.JLabel();
        lblBeneficiario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::DETALLE DE EXTRACCION::.");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tablaEfectivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tablaEfectivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDENTIFICADOR", "ID MONEDA", "DENOMINACION", "VALOR", "CANTIDAD", "TOTAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Long.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEfectivo.setRowHeight(35);
        tablaEfectivo.setSurrendersFocusOnKeystroke(true);
        tablaEfectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaEfectivoKeyPressed(evt);
            }
        });
        scrEfectivo.setViewportView(tablaEfectivo);

        btnSalir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
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

        btnConfirmar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnConfirmar.setText("CONFIRMAR");
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseClicked(evt);
            }
        });
        btnConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConfirmarKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Beneficiario:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Extraccion Nro.:");

        lblExtaccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblExtaccion.setForeground(new java.awt.Color(204, 204, 0));
        lblExtaccion.setText("jLabel3");

        lblBeneficiario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblBeneficiario.setForeground(new java.awt.Color(204, 204, 0));
        lblBeneficiario.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExtaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBeneficiario, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblExtaccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblBeneficiario))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrEfectivo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scrEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConfirmarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConfirmar();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }

    }//GEN-LAST:event_btnConfirmarKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void btnConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseClicked
        if (evt.getClickCount() == 1) {
            btnConfirmar();
        }
    }//GEN-LAST:event_btnConfirmarMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        if (evt.getClickCount() == 1) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void tablaEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaEfectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            btnSalir.requestFocus();
            btnConfirmar();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_tablaEfectivoKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmExtraccionEfectivoDetalle().setVisible(true);
            }
        });
    }

    private void listenerTabla(JTable tabla) {
        tabla.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent evento) {
                totalEfectivo(evento);
            }
        });
    }

    long total = 0;

    protected void totalEfectivo(TableModelEvent evento) {
        // Solo se trata el evento UPDATE, correspondiente al cambio de valor
        // de una celda.
        if (evento.getType() == TableModelEvent.UPDATE) {
            // Se obtiene el modelo de la tabla y la fila/columna que han cambiado.
            TableModel modelo = ((TableModel) (evento.getSource()));
            int fila = evento.getFirstRow();
            int columna = evento.getColumn();
            // Los cambios en la ultima fila y columna se ignoran.
            // Este return es necesario porque cuando nuestro codigo modifique
            // los valores de las sumas en esta fila y columna, saltara nuevamente
            // el evento, metiendonos en un bucle recursivo de llamadas a este
            // metodo.
            if (columna == 5) {
                return;
            }

            try {
                // Se actualiza la suma en la ultima columna de la fila que ha
                // cambiado.
                long valor = Integer.parseInt(modelo.getValueAt(fila, 3).toString().replace(".", ""));
                long cantidad = Long.valueOf(modelo.getValueAt(fila, 4).toString());
                total += valor * cantidad;
                long totalEfec = valor * cantidad;
                tablaEfectivo.setValueAt(miles.format(totalEfec), fila, 5);
            } catch (NullPointerException e) {
                tablaEfectivo.setValueAt(0, fila, 4);
                tablaEfectivo.setValueAt(0, fila, 5);
            }

        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBeneficiario;
    private javax.swing.JLabel lblExtaccion;
    private javax.swing.JScrollPane scrEfectivo;
    public static javax.swing.JTable tablaEfectivo;
    // End of variables declaration//GEN-END:variables

}
