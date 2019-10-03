package Vistas;

import Conexiones.ConexionRs;
import Controladores.ContParamAplicacion;
import Controladores.ContReimpresionComprob;
import Controladores.ContVtaControlComprob;
import Utils.FormatosTicket;
import Utils.ControlImpresora;
import Utils.ControlMensajes;
import Utils.Utilidades;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class FrmReimpresion extends javax.swing.JDialog {

    ControlImpresora impresora = new ControlImpresora();
    DecimalFormat miles = new DecimalFormat("###,###,###,###");
    FormatosTicket ticket = new FormatosTicket();
    SimpleDateFormat formatfecha = new SimpleDateFormat("dd/MM/yyyy");
    ContReimpresionComprob func = new ContReimpresionComprob();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();

    //VARIABLES QUE SERAN UTILIZADAS EN EL FORMULARIO RUC
    String IDENTIFICADOR = "";
    String NUMERO_DOCUMENTO = "";
    String RAZON_SOCIAL = "";

    //VARIABLE QUE VALIDA LA EXISTENCIA DE UN COMPROBANTE
    boolean EXISTE = false;

    //VARIABLES DEL COMPROBANTE
    long DESCUENTO_347 = 0;

    String CONTENIDO_IMPRESION = "";

    public FrmReimpresion() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();
        txtId.requestFocus();
        setModal(true);
    }

    private void inicio() {
        txtId.setText(ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-");
    }

    //Carga un mensaje de que no existe la factura en la base de datos
    private void msjError() {
        EXISTE = false;
        ControlMensajes.error("Comprobante no Definido", "Reimpresion");
    }

    private void vistaPrevia() {
        txtdatos.setText("");
        txtdatos.setForeground(Color.black);
        try {
            String sql = "SELECT\n"
                    + "    fnd_parm_empresas.nombre,\n"
                    + "    fnd_parm_empresas.numero_documento,\n"
                    + "    fnd_parm_empresas.telefonos,\n"
                    + "    fnd_parm_empresas.direccion,\n"
                    + "    vta_control_comprob.numero,\n"
                    + "    vta_control_comprob.fecha_vencimiento,\n"
                    + "    fnd_sitios.descripcion,\n"
                    + "    tc_cajas.descripcion,\n"
                    + "    fnd_usuarios.nombre_completo,\n"
                    + "    SYSDATE,\n"
                    + "    vta_tipos_comprob.descripcion,\n"
                    + "    c.numero,\n"
                    + "    c.id_registro_ctrl,\n"
                    + "    c.monto_total,\n"
                    + "    c.razon_social,\n"
                    + "    c.numero_documento,\n"
                    + "    c.identificador\n"
                    + "FROM\n"
                    + "    vta_comprobantes c\n"
                    + "    INNER JOIN fnd_parm_empresas ON c.id_empresa = fnd_parm_empresas.identificador\n"
                    + "    INNER JOIN vta_control_comprob ON c.id_registro_ctrl = vta_control_comprob.identificador\n"
                    + "    INNER JOIN fnd_sitios ON c.id_sitio = fnd_sitios.identificador\n"
                    + "    INNER JOIN tc_cajas ON c.id_caja = tc_cajas.identificador\n"
                    + "    INNER JOIN fnd_usuarios ON c.usr_cre = fnd_usuarios.nombre\n"
                    + "    INNER JOIN vta_tipos_comprob ON c.id_tipo_comprob = vta_tipos_comprob.identificador\n"
                    + "WHERE\n"
                    + "    c.numero = '" + txtId.getText() + "'\n"
                    + "AND\n"
                    + "    c.id_registro_ctrl = " + contControlComprob.idRegCtrl() + ""
                    + "AND\n"
                    + "    c.fec_anulacion is null"
                    + "";
            ConexionRs cnRs = new ConexionRs();
            ResultSet rs = cnRs.consultar(sql);

            txtdatos.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
            txtdatos.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

            if (rs.next()) {
                EXISTE = true;
                RAZON_SOCIAL = rs.getString(15);
                NUMERO_DOCUMENTO = rs.getString(16);
                IDENTIFICADOR = rs.getString(17);

                txtdatos.append("TRADE GROUP S.A \n");
                txtdatos.append(rs.getString(1) + "\n");
                txtdatos.append("RUC.: " + rs.getString(2) + " TEL.:" + rs.getString(3) + "\n");
                txtdatos.append(rs.getString(4) + "\n");
                txtdatos.append("TIMBRADO NRO.:" + rs.getString(5) + " VTO.:" + formatfecha.format(rs.getDate(6)) + "\n");
                txtdatos.append("\n");
                txtdatos.append("LOCAL: " + rs.getString(7) + "\n");
                txtdatos.append("CAJA: " + rs.getString(8) + "  " + formatfecha.format(rs.getDate(10)) + "\n");
                txtdatos.append("CAJERO: " + rs.getString(9) + "\n");
                txtdatos.append(rs.getString(11) + ": " + rs.getString(12) + "\n");
                txtdatos.append("==================================\n");
                txtdatos.append("TOTAL MONTO: " + miles.format(rs.getLong(14)) + "\n");
                txtdatos.append("==================================\n");
                txtdatos.append("RAZON SOCIAL: " + RAZON_SOCIAL + "\n");
                txtdatos.append("RUC / CI....: " + NUMERO_DOCUMENTO + "\n");
            } else {
                msjError();
                txtId.setText("");
                txtId.setText(ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-");
            }

            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(FrmReimpresion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void encabezadoTicket() {
        //IMPRIMIR ENCABEZADO DE TICKET
        String sql = "SELECT\n"
                + "    fnd_parm_empresas.nombre,\n"
                + "    fnd_parm_empresas.numero_documento,\n"
                + "    fnd_parm_empresas.telefonos,\n"
                + "    fnd_sitios.direccion,\n"
                + "    vta_control_comprob.numero,\n"
                + "    vta_control_comprob.fecha_vencimiento,\n"
                + "    fnd_sitios.descripcion,\n"
                + "    tc_cajas.descripcion,\n"
                + "    fnd_usuarios.nombre_completo,\n"
                + "    c.fec_cre,\n"
                + "    vta_tipos_comprob.descripcion,\n"
                + "    c.numero,\n"
                + "    c.id_registro_ctrl,\n"
                + "    c.monto_total,\n"
                + "    c.razon_social,\n"
                + "    c.numero_documento,\n"
                + "    c.identificador,\n"
                + "    c.descuento_347\n"
                + "FROM\n"
                + "    vta_comprobantes c\n"
                + "    INNER JOIN fnd_parm_empresas ON c.id_empresa = fnd_parm_empresas.identificador\n"
                + "    INNER JOIN vta_control_comprob ON c.id_registro_ctrl = vta_control_comprob.identificador\n"
                + "    INNER JOIN fnd_sitios ON c.id_sitio = fnd_sitios.identificador\n"
                + "    INNER JOIN tc_cajas ON c.id_caja = tc_cajas.identificador\n"
                + "    INNER JOIN fnd_usuarios ON c.usr_cre = fnd_usuarios.nombre\n"
                + "    INNER JOIN vta_tipos_comprob ON c.id_tipo_comprob = vta_tipos_comprob.identificador\n"
                + "WHERE\n"
                + "        c.numero = '" + txtId.getText() + "'\n"
                + "    AND\n"
                + "        c.id_registro_ctrl = " + contControlComprob.idRegCtrl() + ""
                + "";
        ConexionRs cnRs = new ConexionRs();
        ResultSet rs = cnRs.consultar(sql);
        try {
            if (rs.next()) {
                EXISTE = true;
                RAZON_SOCIAL = rs.getString(15);
                NUMERO_DOCUMENTO = rs.getString(16);
                IDENTIFICADOR = rs.getString(17);
                DESCUENTO_347 = rs.getLong(18);

                CONTENIDO_IMPRESION = "";

                CONTENIDO_IMPRESION = "\n";
                CONTENIDO_IMPRESION += "\n";
                CONTENIDO_IMPRESION += "\n";
                CONTENIDO_IMPRESION += "\n";
                CONTENIDO_IMPRESION += ticket.centrar("TRADE GROUP S.A") + "\n";
                CONTENIDO_IMPRESION += ticket.centrar(rs.getString(1).toUpperCase()) + "\n";
                CONTENIDO_IMPRESION += ticket.centrar("RUC.: " + rs.getString(2) + "  TEL: " + rs.getString(3)) + "\n";
                CONTENIDO_IMPRESION += ticket.centrar(rs.getString(4).toUpperCase()) + "\n";
                CONTENIDO_IMPRESION += ticket.centrar("TIMBRADO NRO.: " + rs.getString(5) + " VTO.: " + formatfecha.format(rs.getDate(6))) + "\n";
                CONTENIDO_IMPRESION += "\n";
                CONTENIDO_IMPRESION += "LOCAL.: " + rs.getString(7).toUpperCase() + " \n";
                CONTENIDO_IMPRESION += "CAJA..: " + rs.getString(8).toUpperCase() + " " + formatfecha.format(rs.getDate(10)) + "\n";
                CONTENIDO_IMPRESION += "CAJERO: " + rs.getString(9).toUpperCase() + "\n";
                CONTENIDO_IMPRESION += rs.getString(11).toUpperCase() + ": " + rs.getString(12) + "\n";
                CONTENIDO_IMPRESION += "\n";
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("Articulo Cantidad Precio", "Total") + "\n";
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                rs.close();
                cnRs.cerrarConexion();

            } else {
                msjError();
                txtId.setText("");
                txtId.setText(ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-");
            }

        } catch (SQLException ex) {
            ControlMensajes.error("encabezadoTicket: " + ex, "Reimpresion");
        }

        cnRs.cerrarConexion();
        itemsTicket(IDENTIFICADOR);
    }

    private void itemsTicket(String idComprobante) {
        //IMPRIMIR ITEMS DE TICKET
        String cod = "";
        String des = "";
        String porc = "";
        double cant = 0;
        String precio = "";
        String total = "";
        String sql = "SELECT \n"
                + "PR_PRODUCTOS.CODIGO_BARRAS, \n"
                + "PR_PRODUCTOS.DESCRIPCION, \n"
                + "FND_TIPOS_IMPUESTO.VALOR,\n"
                + "I.CANTIDAD,\n"
                + "TO_CHAR(I.PRECIO_UNITARIO,'999,999,999,999') AS PRECIO, \n"
                + "TO_CHAR(I.CANTIDAD * I.PRECIO_UNITARIO,'999,999,999,999') AS TOTAL\n"
                + "FROM VTA_ITEMS_COMPROB  I\n"
                + "INNER JOIN PR_PRODUCTOS ON I.ID_PRODUCTO = PR_PRODUCTOS.IDENTIFICADOR\n"
                + "INNER JOIN FND_TIPOS_IMPUESTO ON I.ID_TIPO_IMPUESTO = FND_TIPOS_IMPUESTO.IDENTIFICADOR\n"
                + "INNER JOIN VTA_COMPROBANTES ON I.ID_COMPROBANTE = VTA_COMPROBANTES.IDENTIFICADOR\n"
                + "WHERE I.ID_COMPROBANTE = " + idComprobante + "";

        ConexionRs cnRs = new ConexionRs();
        ResultSet rs = cnRs.consultar(sql);
        try {
            while (rs.next()) {
                cod = rs.getString(1).replace(" ", "");
                des = rs.getString(2);
                porc = rs.getString(3).replace(" ", "");
                cant = rs.getDouble(4);
                precio = rs.getString(5).replace(" ", "");
                total = rs.getString(6).replace(" ", "");

                if (cant < 0) {
                    CONTENIDO_IMPRESION += ticket.centrar("--DEVOLUCION--") + "\n";
                }

                if (cant == 1) {
                    if (des.length() > 13) {
                        CONTENIDO_IMPRESION += ticket.dobleColumna(cod + " " + des.substring(0, 13) + " " + porc + "%", total) + "\n";
                    } else {
                        CONTENIDO_IMPRESION += ticket.dobleColumna(cod + " " + des + " " + porc + "%", total) + "\n";
                    }
                } else {
                    if (des.length() > 19) {
                        CONTENIDO_IMPRESION += ticket.dobleColumna(cod + " " + des.substring(0, 19), porc + "%") + "\n";
                        CONTENIDO_IMPRESION += ticket.item(String.valueOf(cant), precio, total) + "\n";
                    } else {
                        CONTENIDO_IMPRESION += ticket.dobleColumna(cod + " " + des, porc + "%") + "\n";
                        CONTENIDO_IMPRESION += ticket.item(String.valueOf(cant), precio, total) + "\n";
                    }
                }
            }

            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            ControlMensajes.error("itemsTicket: " + ex, "Reimpresion");
        }
        footerTicket(idComprobante);
    }

    private void footerTicket(String idComprobante) {
        long MONTO_GRAVADO = 0;
        long MONTO_GRAVADO5 = 0;
        long MONTO_GRAVADO10 = 0;

        long MONTO_IMPUESTO = 0;
        long MONTO_IMPUESTO5 = 0;
        long MONTO_IMPUESTO10 = 0;
        long TOTAL = 0;
        int CANTIDAD_ARTICULOS = 0;

        String NUMERO_DOCUMENTO_T = "";
        String RAZON_SOCIAL_T = "";

        //IMPRIMIR ITEMS DE TICKET
        String sql = "SELECT\n"
                + "V.MONTO_GRAVADO,\n"
                + "V.MONTO_GRAVADO5,\n"
                + "V.MONTO_GRAVADO10,\n"
                + "V.MONTO_IMPUESTO,\n"
                + "V.MONTO_IMPUESTO5,\n"
                + "V.MONTO_IMPUESTO10,\n"
                + "V.NUMERO_DOCUMENTO,\n"
                + "V.RAZON_SOCIAL,\n"
                + "(SELECT COUNT(*) FROM VTA_ITEMS_COMPROB WHERE ID_COMPROBANTE = " + idComprobante + ") AS ARTICULOS,\n"
                + "V.MONTO_TOTAL\n"
                + "FROM VTA_COMPROBANTES  V\n"
                + "WHERE V.IDENTIFICADOR = " + idComprobante + "";

        ConexionRs cnRs = new ConexionRs();
        ResultSet rs = cnRs.consultar(sql);
        try {
            if (rs.next()) {
                MONTO_GRAVADO = rs.getLong(1);
                MONTO_GRAVADO5 = rs.getLong(2);
                MONTO_GRAVADO10 = rs.getLong(3);

                MONTO_IMPUESTO = rs.getLong(4);
                MONTO_IMPUESTO5 = rs.getLong(5);
                MONTO_IMPUESTO10 = rs.getLong(6);

                NUMERO_DOCUMENTO_T = rs.getString(7);
                RAZON_SOCIAL_T = rs.getString(8);
                CANTIDAD_ARTICULOS = rs.getInt(9);
                TOTAL = rs.getLong(10);

                //DESCUENTO SEDECO
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("TOTAL:", miles.format(TOTAL + DESCUENTO_347)) + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("DESCUENTO SEDECO RES. 347:", miles.format(DESCUENTO_347)) + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("TOTAL A PAGAR:", miles.format(TOTAL)) + "\n";

                //GRAVADAS DEL COMPROBANTE
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("Total Gravadas:", miles.format(MONTO_GRAVADO)) + "\n";
                if (Math.round(MONTO_GRAVADO5) > 0) {
                    CONTENIDO_IMPRESION += ticket.dobleColumna("Total Gravadas 5%:", miles.format(Math.round(MONTO_GRAVADO5))) + "\n";
                }
                if (Math.round(MONTO_GRAVADO10) > 0) {
                    CONTENIDO_IMPRESION += ticket.dobleColumna("Total Gravadas 10%:", miles.format(Math.round(MONTO_GRAVADO10))) + "\n";
                }

                if (FrmPos.importeExento > 0) {
                    CONTENIDO_IMPRESION += ticket.dobleColumna("Total Exentas:", miles.format(Math.round(FrmPos.importeExento))) + "\n";
                }

                //LIQUIDACION DE IVA DEL COMPROBANTE
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                CONTENIDO_IMPRESION += "LIQUIDACION IVA \n";
                if (Math.round(MONTO_IMPUESTO5) > 0) {
                    CONTENIDO_IMPRESION += ticket.dobleColumna("Total I.V.A. 5%:", miles.format(Math.round(MONTO_IMPUESTO5))) + "\n";
                }
                if (Math.round(MONTO_IMPUESTO10) > 0) {
                    CONTENIDO_IMPRESION += ticket.dobleColumna("Total I.V.A. 10%:", miles.format(Math.round(MONTO_IMPUESTO10))) + "\n";
                }
                CONTENIDO_IMPRESION += ticket.dobleColumna("Total I.V.A.:", miles.format(MONTO_IMPUESTO)) + "\n";

                //CLIENTE DEL COMPROBANTE
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("C.I. / R.U.C.:", NUMERO_DOCUMENTO_T) + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("CLIENTE: ", RAZON_SOCIAL_T) + "\n";

                //TOTALES DEL COMPROBANTE
                CONTENIDO_IMPRESION += ticket.separador() + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("ARTICULOS:", String.valueOf(CANTIDAD_ARTICULOS)) + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("TOTAL:", miles.format(TOTAL + DESCUENTO_347)) + "\n";
                CONTENIDO_IMPRESION += ticket.dobleColumna("TOTAL A PAGAR:", miles.format(TOTAL)) + "\n";

                rs.close();
                cnRs.cerrarConexion();
            } else {
                msjError();
                txtId.setText("");
                txtId.setText(ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-");
            }

            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            ControlMensajes.error("footerTicket: " + ex, "Reimpresion");
        }
        pagosTicket(idComprobante);
    }

    private void pagosTicket(String idComprobante) {
        String sql = "SELECT \n"
                + "FND_FORMAS_PAGO.DESCRIPCION,\n"
                + "I.IMPORTE\n"
                + "FROM TC_ITEMS_COBRO I\n"
                + "INNER JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = I.ID_COBRO\n"
                + "INNER JOIN FND_FORMAS_PAGO ON FND_FORMAS_PAGO.IDENTIFICADOR = I.ID_FORMA_PAGO\n"
                + "WHERE TC_COBROS_CAJA.ID_COMPROBANTE = " + idComprobante + "";
        long impTotal = 0;
        long importe = 0;
        ConexionRs cnRs = new ConexionRs();
        ResultSet rs = cnRs.consultar(sql);
        try {

            //FORMAS DE PAGOS
            CONTENIDO_IMPRESION += ticket.separador() + "\n";
            CONTENIDO_IMPRESION += "FORMAS DE PAGO\n";
            while (rs.next()) {
                impTotal = rs.getLong(2);
                impTotal += importe;
                CONTENIDO_IMPRESION += ticket.dobleColumna(rs.getString(1) + ":", miles.format(importe)) + "\n";
            }

            CONTENIDO_IMPRESION += ticket.dobleColumna("VUELTO:", miles.format(impTotal)) + "\n";

            //FIN DEL COMPROBANTE
            CONTENIDO_IMPRESION += ticket.separador() + "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += ticket.centrar("--GRACIAS POR SU PREFERENCIA--") + "\n";
            CONTENIDO_IMPRESION += ticket.centrar("ORIGINAL: CLIENTE") + "\n";
            CONTENIDO_IMPRESION += ticket.centrar("DUPLICADO: ARCHIVO TRIBUTARIO") + "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";
            CONTENIDO_IMPRESION += "\n";

            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            ControlMensajes.error("pagosTicket: " + ex, "Reimpresion");
        }
        IDENTIFICADOR = "";
        RAZON_SOCIAL = "";
        NUMERO_DOCUMENTO = "";
        EXISTE = false;

        System.out.println(CONTENIDO_IMPRESION);
        if (ControlImpresora.imprimir(CONTENIDO_IMPRESION)) {
            try {
                txtId.setText("");
                txtdatos.setText("");
                txtId.requestFocus();
                txtId.setText(ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-");
                Thread.sleep(500);
                ControlImpresora.cortarPapel();
            } catch (InterruptedException ex) {
                Logger.getLogger(FrmReimpresion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void frmruc() {
        if (!Utilidades.getPing()) {
            ControlMensajes.error("SIN CONEXION A SERVIDOR", "MENU DEL CAJERO");
            return;
        }
        Thread h = new Thread(() -> {
            FrmReimpresionCliente frm = new FrmReimpresionCliente();
            frm.id = IDENTIFICADOR;
            FrmReimpresionCliente.lblNumero.setText(txtId.getText());
            FrmReimpresionCliente.txtRuc.setText(NUMERO_DOCUMENTO.toUpperCase());
            FrmReimpresionCliente.txtnombre.setText(RAZON_SOCIAL.toUpperCase());
            frm.setLocationRelativeTo(null);
            frm.setVisible(true);
        });
        h.start();
        this.dispose();
    }

    public void txtIdKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            vistaPrevia();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F9) {
            frmruc();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            encabezadoTicket();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtdatos = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        btnRuc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".::REIMPRESION DE FACTURAS::.");
        setIconImage(null);
        setResizable(false);

        txtdatos.setEditable(false);
        txtdatos.setColumns(20);
        txtdatos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtdatos.setLineWrap(true);
        txtdatos.setRows(5);
        txtdatos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdatosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtdatos);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Numero de Factura");

        txtId.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdKeyPressed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("CERRAR: Esc");

        btnImprimir.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnImprimir.setText("IMPRIMIR (F10)");
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImprimirMouseClicked(evt);
            }
        });
        btnImprimir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnImprimirKeyPressed(evt);
            }
        });

        btnRuc.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnRuc.setMnemonic('r');
        btnRuc.setText("MODIFICAR (F9)");
        btnRuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRucMouseClicked(evt);
            }
        });
        btnRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRucKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(txtId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    
    private void txtIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyPressed

    }//GEN-LAST:event_txtIdKeyPressed
*/
    private void txtdatosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdatosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_F9) {
            frmruc();
        }
    }//GEN-LAST:event_txtdatosKeyPressed

    private void btnImprimirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnImprimirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F9) {
            frmruc();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_F10) {
            encabezadoTicket();
        }
    }//GEN-LAST:event_btnImprimirKeyPressed

    private void btnRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRucKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_F9) {
            frmruc();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            encabezadoTicket();
        }
    }//GEN-LAST:event_btnRucKeyPressed

    private void btnImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseClicked
        if (evt.getClickCount() == 1) {
            if (impresora.habilitado()) {
                encabezadoTicket();
                txtId.setText("");
                txtdatos.setText("");
                txtId.requestFocus();
                txtId.setText(ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-");
            }
        }
    }//GEN-LAST:event_btnImprimirMouseClicked

    private void btnRucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRucMouseClicked
        if (evt.getClickCount() == 1) {
            frmruc();
        }
    }//GEN-LAST:event_btnRucMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReimpresion().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnImprimir;
    public static javax.swing.JButton btnRuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextField txtId;
    public static javax.swing.JTextArea txtdatos;
    // End of variables declaration//GEN-END:variables

}
