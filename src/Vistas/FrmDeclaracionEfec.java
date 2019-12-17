package Vistas;

import Modelos.TcDeclaracionEfec;
import Modelos.TcControlCaja;
import Modelos.TcDetalleArqueo;
import Modelos.TcArqueoCaja;
import Utils.FormatosTicket;
import Utils.ControlMensajes;
import Utils.Utilidades;
import Controladores.ContFndMonedas;
import Utils.RenderTablaEfectivo;
import Conexiones.ConexionRs;
import Controladores.ContExportarTransacciones;
import Controladores.ContFndUsuarios;
import Controladores.ContTcArqueoCaja;
import Controladores.ContTcControlCaja;
import Controladores.ContImpresionCierreCaja;
import Controladores.ContParamAplicacion;
import Controladores.ContTcDeclaracionEfec;
import Controladores.ContTcDenomMoneda;
import Controladores.ContTcExtrEfectivoCaja;
import Controladores.ContVtaClientes;
import Controladores.ContVtaComprobantes;
import Controladores.ContVtaControlComprob;
import Utils.ControlImpresora;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public final class FrmDeclaracionEfec extends javax.swing.JFrame {

    private final String titulo = "Dec. de Efectivo";

    ContTcDenomMoneda contDenomMoneda = new ContTcDenomMoneda();
    ContTcDeclaracionEfec contDeclaracionfec = new ContTcDeclaracionEfec();
    ContTcArqueoCaja contArqueoCaja = new ContTcArqueoCaja();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContVtaComprobantes contComprobante = new ContVtaComprobantes();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    ContImpresionCierreCaja contImpresionCierreCaja = new ContImpresionCierreCaja();
    ContFndMonedas contcontMonedas = new ContFndMonedas();

    String[] denominacion = new String[4];
    int contador = 0;
    int[] ultimoNro = contControlComprob.ultimoNro();

    TcDeclaracionEfec dtsDecEfect = new TcDeclaracionEfec();
    TcArqueoCaja dtsArqueo = new TcArqueoCaja();
    TcDetalleArqueo dtsDetAr = new TcDetalleArqueo();
    TcControlCaja dtsCtrl = new TcControlCaja();

    ControlImpresora impresora = new ControlImpresora();
    DefaultTableModel tabla = new DefaultTableModel();
    FormatosTicket ticket = new FormatosTicket();
    DecimalFormat miles = new DecimalFormat("###,###.##");

    long idArqueo = contArqueoCaja.identificador();
    long idControlCaja = Long.parseLong(FrmCierreCaja.txtControlCaja.getText());
    int TOTAL_VALORES_ARQUEO = 0;

    //VARIABLE A UTILIZAR PARA LA IMPRESION DEL REPORTE DE CIERRE
    String contenidoDeImpresion = "";

    public FrmDeclaracionEfec() {
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
        lblControlCaja.setText("CONTROL DE CAJA.: " + idControlCaja);
        lblArqueo.setText("ARQUEO NRO.: " + idArqueo);
        lblUsuario.setText("USUARIO: " + ContFndUsuarios.USR_NOMBRE_COMPLETO);
        lblCja.setText("CAJA NRO.: " + ContParamAplicacion.CAJA_NUMERO);
        tablaEfectivo.getTableHeader().setReorderingAllowed(false);
    }

    private void registrosTabla() {
        try {
            tabla = contDenomMoneda.getDenominaciones();
            tablaEfectivo.setModel(tabla);
        } catch (Exception e) {
            ControlMensajes.error("registrosTabla: " + e, titulo);
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

    public static long totalValores() {
        long total = 0;
        for (int i = 0; i < tablaEfectivo.getRowCount(); i++) {
            total += Integer.parseInt(tablaEfectivo.getValueAt(i, 5).toString().replace(".", ""));
        }
        return total;
    }

    private boolean contArqueoCajaCaja(int serv) {
        dtsArqueo.setIdentificador(idArqueo);
        dtsArqueo.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsArqueo.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsArqueo.setIdCaja(ContParamAplicacion.CAJA_IDENTIFICADOR);
        dtsArqueo.setIdControl(idControlCaja);
        dtsArqueo.setVrTipoArqueo("DE");
        dtsArqueo.setUsrCre(ContFndUsuarios.USR_NOMBRE);
        dtsArqueo.setConfirmado("N");
        dtsArqueo.setTotalConceptos(contArqueoCaja.totalConceptos(idControlCaja));
        dtsArqueo.setTotalValores(totalValores() + contArqueoCaja.totalVouchers(idControlCaja));
        dtsArqueo.setDiferencia(dtsArqueo.getTotalValores() - dtsArqueo.getTotalConceptos());
        dtsArqueo.setIdCajero(ContFndUsuarios.USR_ID_CAJERO);
        dtsArqueo.setNroControl(contArqueoCaja.nroControl());
        dtsArqueo.setTotalRedVueltos(contArqueoCaja.totalRedVuelto(idControlCaja));

        return contArqueoCaja.insert(dtsArqueo, serv);
    }

    private boolean detalleArqueo(int serv) {
        ConexionRs cnRs = new ConexionRs();
        String sql = "SELECT DESCRIPCION, IDENTIFICADOR,\n"
                + "(\n"
                + "SELECT COALESCE(SUM(IMPORTE),0) FROM TC_ITEMS_COBRO \n"
                + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = " + idControlCaja + " \n"
                + ") AS TOTAL,\n"
                + "(\n"
                + "SELECT COUNT(*) FROM TC_ITEMS_COBRO \n"
                + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = " + idControlCaja + " \n"
                + ") AS CANT_COMPROBANTE, TCC.ID_FORMA_PAGO\n"
                + "FROM TC_CONCEPTOS_ARQUEO TCC";
        try {
            boolean enviado = false;
            ResultSet rs = cnRs.consultar(sql);

            while (rs.next()) {
                dtsDetAr.setIdArqueo(idArqueo);
                dtsDetAr.setIdConcepto(rs.getInt(2));
                dtsDetAr.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
                dtsDetAr.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
                dtsDetAr.setUsrCre(ContFndUsuarios.USR_NOMBRE);
                if (rs.getString(5) == null) {
                    dtsDetAr.setImporteCalc(contImpresionCierreCaja.ventaPos(idControlCaja, " AND FEC_ANULACION IS NULL"));
                    dtsDetAr.setImporteDecl(contImpresionCierreCaja.ventaPos(idControlCaja, " AND FEC_ANULACION IS NULL"));
                } else {
                    dtsDetAr.setImporteCalc(rs.getLong(3));
                    dtsDetAr.setImporteDecl(rs.getLong(3));
                }
                dtsDetAr.setCantidadComp(rs.getInt(4));

                enviado = contArqueoCaja.insDetalleArqueo(dtsDetAr, serv);
            }

            rs.close();
            cnRs.cerrarConexion();
            return enviado;
        } catch (SQLException ex) {
            ControlMensajes.error("detalleArqueo: " + ex, titulo);
            return false;
        }
    }

    private boolean contDeclaracionfec(int serv) {
        boolean insertado = false;

        for (int i = 0; i < tablaEfectivo.getRowCount(); i++) {
            dtsDecEfect.setIdArqueo(idArqueo);
            dtsDecEfect.setIdDenominacion(Integer.parseInt(tablaEfectivo.getValueAt(i, 0).toString()));
            dtsDecEfect.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
            dtsDecEfect.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
            dtsDecEfect.setCantidad(Integer.parseInt(tablaEfectivo.getValueAt(i, 4).toString()));
            dtsDecEfect.setImporte(Integer.parseInt(tablaEfectivo.getValueAt(i, 5).toString().replace(".", "")));
            dtsDecEfect.setUsrCre(ContFndUsuarios.USR_NOMBRE);
            dtsDecEfect.setFecCre(Utilidades.getFecha("dd/MMM/yyyy"));

            insertado = contDeclaracionfec.insert(dtsDecEfect, serv);
        }
        return insertado;
    }

    private boolean exportarTransacciones() {
        //exportar controles de caja
        ContTcControlCaja ctrlCaja = new ContTcControlCaja();
        if (ctrlCaja.expControles("") == false) {
            ControlMensajes.error("Control de caja no exportado", titulo);
            return false;
        }

        //exportar arqueo de caja
        ContTcArqueoCaja arqueo = new ContTcArqueoCaja();
        if (arqueo.expArqueos("") == false) {
            ControlMensajes.error("Arqueo de caja no exportado", titulo);
            return false;
        }

        //exportar extracciones de caja
        ContTcExtrEfectivoCaja extraccion = new ContTcExtrEfectivoCaja();
        if (extraccion.expExtraccionEfectivo(ctrlCaja.idApertura()) == false) {
            ControlMensajes.error("Extracciones de caja no exportado", titulo);
            return false;
        }

        //exportar nuevos clientes
        ContVtaClientes cliente = new ContVtaClientes();
        if (cliente.Exportar() == false) {
            ControlMensajes.error("clientes no exportado", titulo);
            return false;
        }

        //exportar comprobantes
        ContExportarTransacciones transacciones = new ContExportarTransacciones();
        if (transacciones.exportarComprobantes("WHERE FECHA_ENVIO_SRV IS NULL AND ID_CONTROL_CAJA=" + String.valueOf(idControlCaja)) == false) {
            ControlMensajes.error("Comprobantes no exportados", titulo);
            return false;
        }

        //exportar ultimo numero de comprobante
        if (contComprobante.ultNroUsado(ultimoNro[1], ultimoNro[0], 1) == false) {
            ControlMensajes.error("Ultimo numero usado no modificado", titulo);
            System.out.println("\tUltimo numero usado no modificado");
            return false;
        }
        return true;
    }

    private void btnConf() {
        if (!impresora.habilitado() || !impresora.habilitado() || !impresora.habilitado()) {
            ControlMensajes.error("Impresora no disponible", titulo);
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmDeclaracionEfec.class.getName()).log(Level.SEVERE, null, ex);
        }
        idControlCaja = contControlCaja.idApertura();

        System.out.println("\tBASE DE DATOS LOCAL");
        if (contControlCaja.cerrarCaja(0, idControlCaja)) {
            if (contArqueoCajaCaja(0)) {
//                try {
                if (detalleArqueo(0)) {
                    if (contDeclaracionfec(0)) {
                        contArqueoCaja.arqueado(idControlCaja);
                    } else {
                        System.out.println("\tDeclaracion de efectivo no realizado");
                        ControlMensajes.informacion("Declaracion de efectivo no realizado", titulo);
                        return;
                    }
                } else {
                    ControlMensajes.informacion("Detalles de Arqueo no Registrados", titulo);
                    System.out.println("\tDetalles de Arqueo no Registrados");
                    return;
                }

                //una vez realizado todas las transacciones en la base de datos local se procede a exportarlas al servidor
                if (Utilidades.getPing()) {
                    if (exportarTransacciones() == false) {
                        System.out.println("Transacciones no exportadas correctamente");
                    }
                } else {
                    ControlMensajes.informacion("Transacciones no exportadas, sin conexion a servidor", titulo);
                }

                System.out.println("\n");
                System.out.println("\n");
//                    impResumenFinanciero();
//                    contenidoDeImpresion += ticket.separadorDoble() + "\n";
//                    impResumenPorCajeros();
//                    contenidoDeImpresion += ticket.separadorDoble() + "\n";
//                    impResumenContable();
//                    contenidoDeImpresion += ticket.separadorDoble() + "\n";
                impArqueoCaja();
                contenidoDeImpresion += ticket.separadorDoble() + "\n";

                ControlImpresora.imprimir(contenidoDeImpresion);

//                } catch (SQLException ex) {
//                    Logger.getLogger(FrmDeclaracionEfec.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
                }

                ControlImpresora.cortarPapel();
                btnSalir.requestFocus();
            } else {
                System.out.println("\tArqueo de caja no realizado");
            }
        } else {
            ControlMensajes.informacion("Cierre de caja no realizado", titulo);
            System.out.println("\tCierre de caja no realizado");
        }

        ControlMensajes.informacion("Cierre de caja realizado", titulo);
        this.dispose();
    }

    private String cabezeraImpresion(String titulo) {
        return "\n"
                + ticket.centrar(ContParamAplicacion.EMP_NOMBRE.toUpperCase()) + "\n"
                + ticket.centrar("RUC: " + ContParamAplicacion.EMP_NUMERO_DOCUMENTO + "  TEL:" + ContParamAplicacion.EMP_TELEFONOS) + "\n"
                + ticket.centrar(ContParamAplicacion.SUC_DIRECCION.toUpperCase()) + "\n"
                + ticket.centrar("----I.V.A. INCLUIDO----") + "\n"
                + ticket.centrar("TIMBRADO: " + ContParamAplicacion.VTA_CTRL_NUMERO) + "\n"
                + "\n"
                + ticket.centrar("--" + titulo + "--") + "\n"
                + "\n"
                + "LOCAL : " + ContParamAplicacion.SUC_DESCRIPCION + " - " + ContParamAplicacion.EMP_CIUDAD + "\n"
                + "CAJA: " + ContParamAplicacion.CAJA_NUMERO + "  " + Utilidades.getFecha("dd/MM/yyyy HH:mm:ss") + "\n"
                + "CAJERO: " + ContFndUsuarios.USR_NOMBRE_COMPLETO + "\n"
                + "\n"
                + ticket.separadorDoble() + "\n";
    }

    private void impResumenFinanciero() throws SQLException {
        ResultSet rs = null;

        String[] dtsTotales = new String[4];
        dtsTotales = contImpresionCierreCaja.totalesResumenFinanciero(idControlCaja);
        String[] dtsCant = new String[3];
        dtsCant = contImpresionCierreCaja.cantResumenFinanciero(idControlCaja);

        long totalBruto = contImpresionCierreCaja.ventaPos(idControlCaja, "");
        long cancelado = Long.parseLong(dtsTotales[0]);
        long anulado = Long.parseLong(dtsTotales[1]);
        long descuento = Long.parseLong(dtsTotales[2]);

        contenidoDeImpresion = "";
        contenidoDeImpresion
                = cabezeraImpresion("RESUMEN FINANCIERO")
                + ticket.dobleColumna("Total Bruto..:     ", miles.format(totalBruto + anulado)) + "\n"
                + ticket.dobleColumna("Total Cancel.:     " + dtsCant[0], miles.format(cancelado)) + "\n"
                + ticket.dobleColumna("Total Anul...:     " + dtsCant[1], miles.format(anulado)) + "\n"
                + ticket.dobleColumna("Total Desctos:     " + dtsCant[2], miles.format(descuento)) + "\n"
                + ticket.separador() + "\n"
                + ticket.dobleColumna("Sub Total..........:", miles.format(totalBruto - cancelado)) + "\n"
                + "\n"
                + ticket.separador() + "\n"
                + ticket.dobleColumna("Medio de Pago      Cant.", "Importe") + "\n"
                + ticket.separador() + "\n";

        long totalRendir = 0;
        long cantMediosPago = 0;
        long totalMedioPago = 0;
        long vuelto = 0;

        rs = contImpresionCierreCaja.mediosPagoResFinanciero(idControlCaja);
        while (rs.next()) {
            cantMediosPago = rs.getLong(2);
            totalMedioPago = rs.getLong(3);
            if (rs.getInt(2) > 0) {
                contenidoDeImpresion += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), String.valueOf(cantMediosPago)), miles.format(totalMedioPago)) + "\n";
                totalRendir += totalMedioPago;
            }
        }

        rs = contImpresionCierreCaja.vuelto(idControlCaja);
        while (rs.next()) {
            if (rs.getInt(2) > 0) {
                contenidoDeImpresion += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), rs.getString(2)), miles.format(rs.getLong(3))) + "\n";
                vuelto = rs.getInt(3);
            }
        }

        rs = contImpresionCierreCaja.artTotales(idControlCaja);
        while (rs.next()) {
            contenidoDeImpresion
                    += ticket.separador() + "\n"
                    + ticket.dobleColumna("Total a Rendir.....:", String.valueOf(miles.format(totalRendir - vuelto))) + "\n"
                    + "\n"
                    + ticket.separador() + "\n"
                    + ticket.centrar("--Monedas Extranjeras Pasan a GS.--") + "\n"
                    + ticket.dobleColumna("Clientes...........:", contImpresionCierreCaja.clientes(idControlCaja)) + "\n"
                    + ticket.dobleColumna("Articulos..........:", String.valueOf(rs.getInt(1))) + "\n"
                    + ticket.dobleColumna("Total Gravado......:", String.valueOf(miles.format(rs.getLong(2)))) + "\n"
                    + ticket.dobleColumna("Total Impuesto.....:", String.valueOf(miles.format(rs.getLong(3)))) + "\n"
                    + ticket.dobleColumna("Total Exento.......:", String.valueOf(miles.format(rs.getLong(4)))) + "\n";
        }

        rs = contImpresionCierreCaja.nroTickets(idControlCaja);
        while (rs.next()) {
            contenidoDeImpresion
                    += ticket.dobleColumna("Ticket Inicial.....:", rs.getString(1)) + "\n"
                    + ticket.dobleColumna("Ticket Final.......:", rs.getString(2)) + "\n"
                    + "\n"
                    + "\n";
        }
    }

    private void impResumenPorCajeros() {
        try {
            long totalRendir = 0;
            long vuelto = 0;
            ResultSet rs = null;
            contenidoDeImpresion
                    += cabezeraImpresion("RESUMEN POR CAJEROS")
                    + "Cajero: " + ContFndUsuarios.USR_NOMBRE_COMPLETO + "\n"
                    + "Caja...: " + ContParamAplicacion.CAJA_NUMERO + " " + Utilidades.getFecha("dd/MM/yyyy HH:mm:ss") + "\n"
                    + ticket.dobleColumna("Forma de Pago      Cant.", "Importe") + "\n"
                    + ticket.separador() + "\n";
            rs = contImpresionCierreCaja.mediosPagoResFinanciero(idControlCaja);
            while (rs.next()) {
                if (rs.getInt(2) > 0) {
                    contenidoDeImpresion += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), rs.getString(2)), miles.format(rs.getLong(3))) + "\n";
                    totalRendir += rs.getLong(3);
                }
            }

            rs = contImpresionCierreCaja.vuelto(idControlCaja);
            while (rs.next()) {
                if (rs.getInt(2) > 0) {
                    contenidoDeImpresion += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), rs.getString(2)), miles.format(rs.getLong(3))) + "\n";
                    vuelto = rs.getLong(3);
                }
            }

            contenidoDeImpresion
                    += ticket.separador() + "\n"
                    + ticket.dobleColumna(ticket.mediosPago("Total Cajero.:", contImpresionCierreCaja.totalComprobantes(idControlCaja)), String.valueOf(miles.format(totalRendir - vuelto))) + "\n"
                    + ticket.dobleColumna("Total Venta..:", String.valueOf(miles.format(totalRendir - vuelto))) + "\n"
                    + "\n"
                    + "\n";

        } catch (SQLException ex) {
            Logger.getLogger(FrmDeclaracionEfec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void impResumenContable() {
        try {
            ResultSet rs = null;
            contenidoDeImpresion += cabezeraImpresion("RESUMEN CONTABLE");

            rs = contImpresionCierreCaja.nroTickets(idControlCaja);
            while (rs.next()) {
                contenidoDeImpresion
                        += ticket.dobleColumna("Ticket Inicial.:", rs.getString(1)) + "\n"
                        + ticket.dobleColumna("Ticket Final...:", rs.getString(2)) + "\n";
            }

            rs = contImpresionCierreCaja.totalesResumenContable(idControlCaja);
            while (rs.next()) {
                contenidoDeImpresion
                        += ticket.dobleColumna("Total Exento...:", miles.format(rs.getLong(1))) + "\n"
                        + ticket.dobleColumna("Total Grav. 5%.:", miles.format((int) rs.getLong(2) * 1.05)) + "\n"
                        + ticket.dobleColumna("Total Grav. 10%:", miles.format((int) rs.getLong(3) * 1.1)) + "\n"
                        + ticket.dobleColumna("Total Operac...:", miles.format((int) rs.getLong(2) * 1.05 + (int) rs.getLong(3) * 1.1)) + "\n"
                        + ticket.dobleColumna("Total Venta....:", miles.format((int) rs.getLong(2) * 1.05 + (int) rs.getLong(3) * 1.1)) + "\n"
                        + "\n" + "\n";
            }

        } catch (SQLException ex) {
            Logger.getLogger(FrmDeclaracionEfec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void impArqueoCaja() {
        try {
            //cabezera
            ResultSet rs = null;
            int totalExtraccion = 0;
            contenidoDeImpresion = cabezeraImpresion("ARQUEO DE CAJA");

            //totales del comprobante
            String[] dtsTotales = new String[4];
            dtsTotales = contImpresionCierreCaja.totalesResumenFinanciero(idControlCaja);
            String[] dtsCant = new String[3];
            dtsCant = contImpresionCierreCaja.cantResumenFinanciero(idControlCaja);

            long cancelado = Long.parseLong(dtsTotales[0]);
            long anulado = Long.parseLong(dtsTotales[1]);

            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += ticket.dobleColumna("Concepto       Cantidad", "Importe") + "\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += ticket.dobleColumna("Total Cancel.:     " + dtsCant[0], miles.format(cancelado)) + "\n";
            contenidoDeImpresion += ticket.dobleColumna("Total Anul...:     " + dtsCant[1], miles.format(anulado)) + "\n";

            //valores de arqueo
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += ticket.dobleColumna("Concepto       Cantidad", "Importe") + "\n";
            contenidoDeImpresion += ticket.separador() + "\n";;
            contenidoDeImpresion += "Grupo: CONCEPTOS DE ARQUEO \n";
            rs = contImpresionCierreCaja.conceptosArqueo(idControlCaja);
            while (rs.next()) {
                if (rs.getString(4) == null && !rs.getString(2).equals("0")) {
                    contenidoDeImpresion += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), rs.getString(2)), miles.format(contImpresionCierreCaja.ventaPos(idControlCaja, " AND FEC_ANULACION IS NULL"))) + "\n";
                }
            }

            contenidoDeImpresion
                    += " \nGrupo: VALORES DE ARQUEO \n";
            rs = contImpresionCierreCaja.conceptosArqueo(idControlCaja);
            long valores = 0;
            while (rs.next()) {
                if (rs.getString(4) != null && !rs.getString(2).equals("0")) {
                    valores = rs.getLong(3);
                    contenidoDeImpresion
                            += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), rs.getString(2)), miles.format(valores)) + "\n";
                    TOTAL_VALORES_ARQUEO += valores;
                }
            }

            //DECLARACION DE EFECTIVO
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\nDECLARACION DE EFECTIVO\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += ticket.dobleColumna("Valor          Cantidad", "Importe") + "\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            for (int i = 0; i < tablaEfectivo.getRowCount(); i++) {
                if (!tablaEfectivo.getValueAt(i, 4).toString().equals("0")) {
                    contenidoDeImpresion
                            += ticket.dobleColumna(ticket.mediosPago(miles.format(Integer.parseInt(tablaEfectivo.getValueAt(i, 3).toString().replace(".", ""))),
                                    tablaEfectivo.getValueAt(i, 4).toString()),
                                    miles.format(Integer.parseInt(tablaEfectivo.getValueAt(i, 5).toString().replace(".", "")))) + "\n";
                }
            }
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += ticket.dobleColumna("Total Efectivo...:", miles.format(totalValores())) + "\n";

            //EXTRACIONES
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\nEXTRACCION DE EFECTIVO\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += ticket.dobleColumna("Valor          Cantidad", "Importe") + "\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            rs = contImpresionCierreCaja.totalExtracciones(idControlCaja);
            while (rs.next()) {
                if (!rs.getString(3).equals("0")) {
                    contenidoDeImpresion += ticket.dobleColumna(ticket.mediosPago(rs.getString(1), rs.getString(3)), miles.format(rs.getLong(4))) + "\n";
                    totalExtraccion += rs.getInt(4);
                }
            }
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += ticket.dobleColumna("Total Extraccion.:", miles.format(totalExtraccion)) + "\n";

            long totalEfectivo = totalValores();
            long totalEntrega = totalValores() + contArqueoCaja.totalVouchers(idControlCaja);
            long totalSobreTicket = contArqueoCaja.totalConceptos(idControlCaja);
            long totalDiferencia = (totalValores() + TOTAL_VALORES_ARQUEO) - totalSobreTicket;

            contenidoDeImpresion += ticket.dobleColumna("Total Efectivo...:", miles.format(totalEfectivo)) + "\n";//EFECTIVO DECLARADO
            contenidoDeImpresion += ticket.dobleColumna("Total Entrega....:", miles.format(totalEntrega)) + "\n";//EFECTIVO DECLARADO + VOUCHERS ENTREGADOS
            contenidoDeImpresion += ticket.dobleColumna("Total s/Tickets..:", miles.format(totalSobreTicket)) + "\n";//TOTAL DE TODAS LAS VENTAS 
            contenidoDeImpresion += ticket.dobleColumna("Total Diferencia.:", miles.format(totalDiferencia)) + "\n";//TOTAL S/TICKET - TOTAL ENTREGA
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += "Recibido por \n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += ticket.separador() + "\n";
            contenidoDeImpresion += "Verificado por \n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";
            contenidoDeImpresion += "\n";

            imprimirTerminal(contenidoDeImpresion);

        } catch (SQLException ex) {
            Logger.getLogger(FrmDeclaracionEfec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void imprimirTerminal(String texto) {
        System.out.println(texto);
    }

    private void salir() {
        if (ControlMensajes.confirmacion("Deseas salir sin confirmar el cierre?", titulo)) {
            this.dispose();
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
        jPanel2 = new javax.swing.JPanel();
        lblControlCaja = new javax.swing.JLabel();
        lblArqueo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblCja = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(".::Declaracion de efectivo::.");
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

        btnConfirmar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseClicked(evt);
            }
        });
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        btnConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConfirmarKeyPressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblControlCaja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblControlCaja.setForeground(new java.awt.Color(204, 204, 0));
        lblControlCaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblControlCaja.setText("CONTROL DE CAJA NRO.");

        lblArqueo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblArqueo.setForeground(new java.awt.Color(204, 204, 0));
        lblArqueo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblArqueo.setText("ARQUEO NRO.:");

        lblUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(204, 204, 0));
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsuario.setText("USUARIO:");

        lblCja.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCja.setForeground(new java.awt.Color(204, 204, 0));
        lblCja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCja.setText("CAJA NRO.:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblControlCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCja, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCja, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblControlCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lblArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(scrEfectivo)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(scrEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            if (!impresora.habilitado() || !impresora.habilitado() || !impresora.habilitado()) {
                ControlMensajes.error("Impresora no disponible", titulo);
                btnConfirmar.requestFocus();
                return;
            }
            if (totalValores() > 0) {
                btnConf();
                System.out.println("<FIN PROCESO DE CIERRE DE CAJA>");
            } else {
                ControlMensajes.informacion("Debe realizar declaracion de efectivo", titulo);
                tablaEfectivo.requestFocus();
            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            salir();
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        salir();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salir();
    }//GEN-LAST:event_formWindowClosing

    private void tablaEfectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaEfectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            btnSalir.requestFocus();
            btnConfirmarKeyPressed(evt);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            salir();
        }
    }//GEN-LAST:event_tablaEfectivoKeyPressed

    private void btnConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConfirmarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            if (!impresora.habilitado() || !impresora.habilitado() || !impresora.habilitado()) {
                ControlMensajes.error("Impresora no disponible", titulo);
                return;
            }

            if (totalValores() > 0) {
                System.out.println("<INICIO PROCESO DE CIERRE DE CAJA>");
                btnConf();
                System.out.println("<FIN PROCESO DE CIERRE DE CAJA>");
            } else {
                ControlMensajes.informacion("Debe realizar declaracion de efectivo", titulo);
                tablaEfectivo.requestFocus();
            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            salir();
        }
    }//GEN-LAST:event_btnConfirmarKeyPressed

    private void btnConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseClicked
        if (evt.getClickCount() == 1) {
            if (!impresora.habilitado() || !impresora.habilitado() || !impresora.habilitado()) {
                ControlMensajes.error("Impresora no disponible", titulo);
                btnConfirmar.requestFocus();
                return;
            }
            
            if (totalValores() > 0) {
                System.out.println("<INICIO PROCESO DE CIERRE DE CAJA>");
                btnConf();
                System.out.println("<FIN PROCESO DE CIERRE DE CAJA>");
            } else {
                ControlMensajes.informacion("Debe realizar declaracion de efectivo", titulo);
                tablaEfectivo.requestFocus();
            }
        }
    }//GEN-LAST:event_btnConfirmarMouseClicked

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDeclaracionEfec().setVisible(true);
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
        // Se trata el evento UPDATE, correspondiente al cambio de valor
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblArqueo;
    private javax.swing.JLabel lblCja;
    private javax.swing.JLabel lblControlCaja;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JScrollPane scrEfectivo;
    public static javax.swing.JTable tablaEfectivo;
    // End of variables declaration//GEN-END:variables

}
