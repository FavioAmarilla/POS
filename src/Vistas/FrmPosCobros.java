package Vistas;

import Atxy2k.CustomTextField.RestrictedTextField;
import Utils.ControlMensajes;
import Utils.Utilidades;
import Controladores.ContParamAplicacion;
import Utils.FormatosTicket;
import Controladores.ContFndMonedas;
import Conexiones.ConexionRs;
import Controladores.ContAppVtaPromociones;
import Controladores.ContAppVtaPromocionesProv;
import Controladores.ContTcControlCaja;
import Controladores.ContFndFormasPago;
import Controladores.ContFndUsuarios;
import Modelos.TcCobrosCaja;
import Modelos.TcItemsCobro;
import Modelos.TcVouchers;
import Modelos.VtaComprobantes;
import Controladores.ContTcCobrosCaja;
import Controladores.ContVtaComprobantes;
import Controladores.ContVtaControlComprob;
import Modelos.AppItemsPromProv;
import Modelos.AppPromocionesProv;
import Modelos.AppVtaPromociones;
import Modelos.FndFormasPago;
import Modelos.FndMonedas;
import Modelos.StkItemsMvStock;
import Modelos.StkParametros;
import Threads.HiloMovimStock;
import Utils.FuncionesBd;
import Utils.ControlImpresora;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmPosCobros extends javax.swing.JDialog {

    ConexionRs cnRs = new ConexionRs();

    ContTcCobrosCaja contCobrosCaja = new ContTcCobrosCaja();
    ContVtaComprobantes contComprobante = new ContVtaComprobantes();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();

    ControlImpresora impresora = new ControlImpresora();
    HiloMovimStock hStock;

    TcCobrosCaja dtsCob = new TcCobrosCaja();
    TcVouchers dtsVou = new TcVouchers();
    VtaComprobantes dtsComp = new VtaComprobantes();
    StkParametros stkParametros = new StkParametros();

    DefaultTableModel modelo = new DefaultTableModel();
    DecimalFormat miles = new DecimalFormat("###,###,###,###");
    FormatosTicket ticket = new FormatosTicket();
    RestrictedTextField restricted;
    public ArrayList<StkItemsMvStock> listaProductos = null;
    public ArrayList<AppVtaPromociones> listaPromocionesMonto = null;
    public ArrayList<AppPromocionesProv> listaPromocionesProv = null;
    public ArrayList<AppItemsPromProv> listaItemsPromoProv = null;

    private final String tituloMsj = "Detalles de Cobro";
    boolean cobrando = false;
    public static long idCobro;
    public static long idVenta;
    public static int idMoneda;
    public static int idFormaPago;
    public static String vr_forma_pago = "";
    public static String nroComprobante;
    public static long idVoucher;
    public static int idProcesadora;
    public static int idTipoTarjeta;
    public static int idEntidad;
    public static int idTipoDocum;
    public static String vrTipoTarjeta;

    long idDetalleCobro;
    long importe = 0;
    long totalRecibido = 0;
    long saldoPagar = 0;
    long totalVenta = 0;
    long vuelto = 0;
    boolean esTarjeta;
    boolean esOrden;
    int indiceTabla = 0;
    int[] ultimoNro = contControlComprob.ultimoNro();
    int IMPORTE_DOCUMENTO = 0;
    String formaPago = "";
    int importePago = 0;
    long total_a_pagar = 0;
    int descto_sedeco = 0;

    public double montoGravado5;
    public double montoGravado10;
    public double montoImpuesto;
    public double montoImpuesto5;
    public double montoImpuesto10;

    public FrmPosCobros() {
        initComponents();
        setModal(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        inicio();
    }

    public void setTablaProductos(ArrayList<StkItemsMvStock> values) {
        listaProductos = values;
    }

    private void inicio() {
        tablaPagos.getTableHeader().setReorderingAllowed(false);

        restricted = new RestrictedTextField(txtImporte);
        restricted.setOnlyNums(true);
        restricted.setOnlyText(false);

        restricted = new RestrictedTextField(txtCuotas);
        restricted.setOnlyNums(true);
        restricted.setOnlyText(false);

        titulosTabla();
        panelDetalle.setVisible(false);
        txtTotalRecibido.setText("0");
        txtTotalRecibido.setText("0");
        txtSaldoPagar.setText(String.valueOf(FrmPos.totalMonto));
        txtSaldoPagar.setText(miles.format(FrmPos.totalMonto));
        txtVuelto.setText(String.valueOf(vuelto));
        txtTotalVenta.setText(miles.format(FrmPos.totalMonto));
        saldoPagar = FrmPos.importeItem;
        txtVuelto.setText("0");
        txtFormaPago.requestFocus();
        cobrando = false;
    }

    public void setStkParametros(StkParametros value) {
        this.stkParametros = value;
    }

    private void getVistas(String vista) {
        if (vista.equals("FP")) {
            if (FuncionesBd.getRegistros("FND_FORMAS_PAGO")) {
                FrmVistaFormasPago frm = new FrmVistaFormasPago();
                frm.setVisible(true);
                txtFormaPago.requestFocus();
            } else {
                ControlMensajes.error("Formas de pago no definidos", tituloMsj);
            }
        }

        if (vista.equals("MN")) {
            if (FuncionesBd.getRegistros("FND_MONEDAS")) {
                FrmVistaMonedas frm = new FrmVistaMonedas();
                frm.setVisible(true);
            } else {
                ControlMensajes.error("Monedas no definidas", tituloMsj);
            }
        }

        if (vista.equals("PR")) {
            if (FuncionesBd.getRegistros("TC_PROC_TARJETAS")) {
                FrmVistaProcesadoras frm = new FrmVistaProcesadoras();
                frm.setVisible(true);
            } else {
                ControlMensajes.error("Procesadoras de tarjetas no definidas", tituloMsj);
            }
        }

        if (vista.equals("EN")) {
            if (FuncionesBd.getRegistros("TC_ENT_EMISORAS")) {
                FrmVistaEntidadesEmisoras frm = new FrmVistaEntidadesEmisoras();
                frm.setVisible(true);
            } else {
                ControlMensajes.error("Entidades emisoras no definidas", tituloMsj);
            }
        }

        if (vista.equals("TT")) {
            if (FuncionesBd.getRegistros("TC_TIPOS_TARJETAS")) {
                FrmVistaTiposTarjeta frm = new FrmVistaTiposTarjeta();
                frm.setVisible(true);
            } else {
                ControlMensajes.error("Tipos de tarjetas no definidos", tituloMsj);
            }
        }
    }

    private void getFormaPago(String sql) {
        ContFndFormasPago contFormaPago = new ContFndFormasPago();
        ArrayList<FndFormasPago> lista = new ArrayList<>();
        lista = contFormaPago.GetFormaPago(sql);

        if (lista.size() > 0) {
            idFormaPago = lista.get(0).getIdentificador();
            txtFormaPago.setText(lista.get(0).getAbreviatura());
            idTipoDocum = lista.get(0).getIdTipoDocum();
            vr_forma_pago = lista.get(0).getVrClase();
        } else {
            ControlMensajes.error("Forma de Pago no definida", tituloMsj);
        }
    }

    private void getMoneda(String sql) {
        ContFndMonedas contMoneda = new ContFndMonedas();
        ArrayList<FndMonedas> lista = new ArrayList<>();
        lista = contMoneda.getMoneda(sql);

        if (lista.size() > 0) {
            idMoneda = lista.get(0).getIdentificador();
            txtMoneda.setText(lista.get(0).getCodigo());
        } else {
            ControlMensajes.error("Moneda no definida", tituloMsj);
        }
    }

    private void vuelto() {
        Thread hilo = new Thread(() -> {
            FrmPosVuelto frm = new FrmPosVuelto();
            FrmPosVuelto.lblVuelto.setText(miles.format(vuelto));
            frm.setVisible(true);
            FrmPosVuelto.lblVuelto.requestFocus();
        });
        hilo.start();
    }

    private void titulosTabla() {
        modelo.addColumn("FORMAS DE PAGO");
        modelo.addColumn("MONEDA");
        modelo.addColumn("IMPORTE");
        modelo.addColumn("ID_FORMA");
        modelo.addColumn("IDENTIFICADOR");
        tablaPagos.setModel(modelo);

        tablaPagos.getColumnModel().getColumn(3).setMaxWidth(0);
        tablaPagos.getColumnModel().getColumn(3).setMinWidth(0);
        tablaPagos.getColumnModel().getColumn(3).setPreferredWidth(0);

        tablaPagos.getColumnModel().getColumn(4).setMaxWidth(0);
        tablaPagos.getColumnModel().getColumn(4).setMinWidth(0);
        tablaPagos.getColumnModel().getColumn(4).setPreferredWidth(0);
    }

    private void registrosTabla() {
        String[] registros = new String[5];

        registros[0] = txtFormaPago.getText();
        registros[1] = txtMoneda.getText();
        registros[2] = txtImporte.getText();
        registros[3] = String.valueOf(idFormaPago);
        registros[4] = String.valueOf(idDetalleCobro);

        modelo.addRow(registros);
        tablaPagos.setModel(modelo);
    }

    private void limpiar() {
        txtFormaPago.setText("");
        txtMoneda.setText("");
        txtImporte.setText("");

        panelDetalle.setVisible(false);
        txtNroTarjeta.setText("");
        txtProcesadora.setText("");
        txtTipoTarjeta.setText("");
        txtCuotas.setText("");

        idFormaPago = 0;
        idMoneda = 0;
        idProcesadora = 0;
        idTipoTarjeta = 0;
        idTipoDocum = 0;

        esTarjeta = false;
        esOrden = false;

        txtFormaPago.requestFocus();
    }

    private boolean cobro_caja() {
        idCobro = FuncionesBd.Secuencia("SELECT SQ_COBROS_CAJA.NEXTVAL FROM DUAL");

        dtsCob.setIdentificador(idCobro);
        dtsCob.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsCob.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsCob.setIdTipoCobro(600);
        dtsCob.setFefecha(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsCob.setNumero(FrmPos.numeroComprobante);
        dtsCob.setIdControl(contControlCaja.idApertura());
        dtsCob.setIdSitio(ContParamAplicacion.SUC_IDENTIFICADOR);
        dtsCob.setIdCaja(ContParamAplicacion.CAJA_IDENTIFICADOR);
        dtsCob.setUsrCajero(ContFndUsuarios.USR_NOMBRE);
        dtsCob.setUsrCre(ContFndUsuarios.USR_NOMBRE);
        dtsCob.setFeCre(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsCob.setIdMoneda(idMoneda);
        dtsCob.setIdTurno(1);
        if (esTarjeta || esOrden) {
            dtsCob.setImporteEfectivo(0);
            dtsCob.setImporteDocum(totalRecibido);
        } else {
            dtsCob.setImporteEfectivo(totalRecibido);
            dtsCob.setImporteDocum(0);
        }
        dtsCob.setImporteVuelto(vuelto);
        dtsCob.setIdCliente(FrmPosCliente.idCliente);
        dtsCob.setIdComprobante(idVenta);
        dtsCob.setVerificado("N");
        dtsCob.setConfirmado("N");
        dtsCob.setReversado("N");
        dtsCob.setIdCajero(ContFndUsuarios.USR_ID_CAJERO);
        dtsCob.setMontoTotal(totalVenta);
        dtsCob.setMontoTotalReal(totalVenta);

        return contCobrosCaja.insert(dtsCob);
    }

    private boolean detalle_cobro() {
        TcItemsCobro dtsDetCob = new TcItemsCobro();

        dtsDetCob.setIdentificador(idDetalleCobro);
        dtsDetCob.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsDetCob.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsDetCob.setIdCobro(idCobro);
        dtsDetCob.setIdComprobante(idVenta);
        dtsDetCob.setIdFormaPago(idFormaPago);
        dtsDetCob.setIdMoneda(idMoneda);
        dtsDetCob.setImporte(importe);
        dtsDetCob.setImporteOrigen(importe);
        dtsDetCob.setUsrCre(ContFndUsuarios.USR_NOMBRE.toUpperCase());
        dtsDetCob.setFecCre(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsDetCob.setImporteMl(importe);
        dtsDetCob.setImporteAsignado(importe);
        dtsDetCob.setTipoCambio(1);
        dtsDetCob.setIdTipoDocumento(idTipoDocum);

        if (esTarjeta) {
            dtsDetCob.setFecDocumento(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsDetCob.setIdProcesadora(idProcesadora);
            dtsDetCob.setIdTipoTarjeta(idTipoTarjeta);
            dtsDetCob.setNroTarjeta(txtNroTarjeta.getText());
            dtsDetCob.setNombreConsignado(FrmPosCliente.razonSocial);
            dtsDetCob.setCantidadCuotas(Integer.parseInt(txtCuotas.getText()));
        }

        if (esOrden) {
            dtsDetCob.setFecDocumento(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsDetCob.setIdEntidad(idEntidad);
            dtsDetCob.setNombreConsignado(txtTipoTarjeta.getText().toUpperCase());
            dtsDetCob.setNroDocumento(txtNroTarjeta.getText());
        }

        dtsDetCob.setNumeroItem(tablaPagos.getRowCount() + 1);
        dtsDetCob.setImporteInicial(totalVenta);

        return contCobrosCaja.insDetalle(dtsDetCob);
    }

    private boolean eliminarPago(int id) {
        return contCobrosCaja.delDetalle(id);
    }

    private boolean updImportes(int serv) {
        if (idFormaPago == 9) {
            dtsCob.setIdentificador(idCobro);
            dtsCob.setImporteEfectivo(importe);
            dtsCob.setImporteDocum(IMPORTE_DOCUMENTO);
            dtsCob.setImporteVuelto(Long.valueOf(String.valueOf(vuelto)));
        } else {
            IMPORTE_DOCUMENTO += importe;
            dtsCob.setIdentificador(idCobro);
            dtsCob.setImporteEfectivo(0);
            dtsCob.setImporteDocum(IMPORTE_DOCUMENTO);
            dtsCob.setImporteVuelto(Long.valueOf(String.valueOf(vuelto)));
        }

        return contCobrosCaja.updImportes(dtsCob, serv);
    }

    private boolean vouchers(int serv) {
        dtsVou.setIdentificador(idVoucher);
        dtsVou.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsVou.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsVou.setIdSitio(ContParamAplicacion.SUC_IDENTIFICADOR);
        dtsVou.setNumero(String.valueOf(FrmPos.nroTicket));
        dtsVou.setIdProcesadora(idProcesadora);
        dtsVou.setIdCaja(ContParamAplicacion.CAJA_IDENTIFICADOR);
        dtsVou.setImporte(importe);
        dtsVou.setVrTipoCarga("CA");
        dtsVou.setVrSituacion("IN");
        dtsVou.setUsrCre(ContFndUsuarios.USR_NOMBRE);
        dtsVou.setNroTarjeta(txtNroTarjeta.getText());
        dtsVou.setNombreCliente(FrmPosCliente.razonSocial);
        dtsVou.setNumeroTicket(nroComprobante);
        dtsVou.setImportePagado(importe);
        dtsVou.setIdCajero(ContFndUsuarios.USR_ID_CAJERO);
        dtsVou.setIdTurno(1);
        dtsVou.setIdControlCaja(contControlCaja.idApertura());
        dtsVou.setVrTipoTarjeta(vrTipoTarjeta);
        dtsVou.setIdCobro(idCobro);
        dtsVou.setIdMoneda(idMoneda);
        dtsVou.setIdTipoTarjeta(idTipoTarjeta);

        return contCobrosCaja.insertVoucher(dtsVou, serv);
    }

    private void vtaEmision() {
        Thread hilo = new Thread(() -> {
            dtsComp.setIdCliente(FrmPosCliente.idCliente);
            dtsComp.setRazonSocial(FrmPosCliente.razonSocial);
            dtsComp.setNumeroDocumento(FrmPosCliente.nroRuc);
            dtsComp.setAtributo2(FrmPosCliente.atributo2);
            dtsComp.setIdentificador(idVenta);
            dtsComp.setUsrCre(ContFndUsuarios.USR_NOMBRE);
            dtsComp.setIdentificador(idVenta);
            dtsComp.setDescuento347(descto_sedeco);

            if (!contComprobante.emision(dtsComp, 0)) {
                System.out.println("\tBASE DE DATOS LOCAL");
                System.out.println("\tUsuario de emision no actualizado");
            }
        });
        hilo.start();
    }

    private boolean validacionesCobro() {
        if (idFormaPago <= 0) {
            ControlMensajes.error("Debe ingresar forma de pago", tituloMsj);
            txtFormaPago.requestFocus();
            return false;
        }
        if (txtImporte.getText().isEmpty()) {
            ControlMensajes.error("Debe ingresar importe de pago", tituloMsj);
            txtImporte.requestFocus();
            return false;
        }
        if (!txtImporte.getText().replace(".", "").matches("[0-9]*")) {
            ControlMensajes.error("Importe de pago no debe contener caracteres", tituloMsj);
            txtImporte.setText("");
            txtImporte.requestFocus();
            return false;
        }
        if (Integer.parseInt(txtImporte.getText().replace(".", "")) <= 0) {
            ControlMensajes.error("Debe ingresar importe de pago", tituloMsj);
            txtImporte.requestFocus();
            return false;
        }
        if (idMoneda <= 0) {
            ControlMensajes.error("Debe ingresar moneda de pago", tituloMsj);
            txtMoneda.requestFocus();
            return false;
        }

        if (esTarjeta) {
            if (txtNroTarjeta.getText().isEmpty()) {
                ControlMensajes.error("Debe ingresar nro de boleta", tituloMsj);
                txtNroTarjeta.requestFocus();
                return false;
            }
            if (idProcesadora <= 0) {
                ControlMensajes.error("Debe ingresar procesadora de tarjeta", tituloMsj);
                txtProcesadora.requestFocus();
                return false;
            }
            if (idTipoTarjeta <= 0) {
                ControlMensajes.error("Debe ingresar tipo de tarjeta", tituloMsj);
                txtTipoTarjeta.requestFocus();
                return false;
            }

            if (txtCuotas.getText().isEmpty()) {
                ControlMensajes.error("Debe ingresar forma de pago", tituloMsj);
                txtCuotas.requestFocus();
                return false;
            }
            if (!txtCuotas.getText().matches("[0-9]*")) {
                ControlMensajes.error("Cuota de pagos no debe contener caracteres", tituloMsj);
                txtCuotas.setText("");
                txtCuotas.requestFocus();
                return false;
            }
            if (Integer.parseInt(txtCuotas.getText()) <= 0) {
                ControlMensajes.error("Debe ingresar cuota de pago", tituloMsj);
                txtCuotas.requestFocus();
                return false;
            }
        }

        if (esOrden) {
            if (txtNroTarjeta.getText().isEmpty()) {
                ControlMensajes.error("Debe ingresar numero de orden", tituloMsj);
                txtNroTarjeta.requestFocus();
                return false;
            }
            if (txtTipoTarjeta.getText().isEmpty()) {
                ControlMensajes.error("Debe ingresar beneficiario de orden", tituloMsj);
                txtTipoTarjeta.requestFocus();
                return false;
            }
        }

        return true;
    }

    private boolean txtImporte() {

        if (!validacionesCobro()) {
            return false;
        }

        totalVenta = Long.valueOf(txtVuelto.getText().replaceAll("\\.", ""));
        importe = Long.valueOf(txtImporte.getText().replaceAll("\\.", ""));
        saldoPagar = Long.valueOf(txtSaldoPagar.getText().replaceAll("\\.", ""));
        totalRecibido = Long.valueOf(txtTotalRecibido.getText().replaceAll("\\.", ""));
        idDetalleCobro = contCobrosCaja.idDetalleCobro();

        if (importe >= saldoPagar) {
            totalRecibido = totalRecibido + importe;
            vuelto = importe - saldoPagar;
            saldoPagar = 0;
        } else {
            totalRecibido = totalRecibido + importe;
            vuelto = vuelto + 0;
            saldoPagar = saldoPagar - importe;
        }

        txtTotalRecibido.setText(miles.format(totalRecibido));
        txtSaldoPagar.setText(miles.format(saldoPagar));
        txtVuelto.setText(String.valueOf(vuelto));

        if (cobrando) {
            if (esTarjeta) {
                System.out.println("\tBASE DE DATOS LOCAL");
                if (vouchers(0)) {
                    System.out.println("\tCobro Voucher registrado, comprobante nro: " + nroComprobante);
                    System.out.println("\tNumero Tarjeta:" + txtNroTarjeta.getText());
                    System.out.println("\tCuotas.......:" + txtCuotas.getText());
                } else {
                    System.out.println("\tCobro Voucher no registrado, comprobante nro: " + nroComprobante);
                    System.out.println("\tNumero Tarjeta:" + txtNroTarjeta.getText());
                    System.out.println("\tCuotas.......:" + txtCuotas.getText());
                }
                panelDetalle.setVisible(false);
                esTarjeta = true;
            } else {
                esTarjeta = false;
            }

            System.out.println("\tBASE DE DATOS LOCAL");
            if (detalle_cobro()) {
                System.out.println("\tDetalle de cobro registrado");
                registrosTabla();
            } else {
                System.out.println("\tDetalle de cobro no registrado");
            }
            if (updImportes(0)) {
                System.out.println("\tImportes Actualizados");
                if (saldoPagar <= 50) {

                    hStock = new HiloMovimStock();
                    hStock.setTablaProductos(listaProductos);
                    hStock.setStkParametros(stkParametros);
                    hStock.start();

                    vtaEmision();
                    vuelto();

                    ControlImpresora.abrirGaveta();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    footerTicket();

                    btnSalir.requestFocus();
                    cobrando = false;
                    limpiar();

                } else {
                    txtFormaPago.requestFocus();
                }
            } else {
                System.out.println("\tImportes no Actualizados");
            }

        } else {
            if (esTarjeta) {
                System.out.println("\tBASE DE DATOS LOCAL");
                if (vouchers(0)) {
                    System.out.println("\tCobro Voucher registrado, comprobante nro: " + nroComprobante);
                    System.out.println("\tNumero Tarjeta:" + txtNroTarjeta.getText());
                    System.out.println("\tCuotas.......:" + txtCuotas.getText());
                } else {
                    System.out.println("\tCobro Voucher no registrado, comprobante nro: " + nroComprobante);
                    System.out.println("\tNumero Tarjeta:" + txtNroTarjeta.getText());
                    System.out.println("\tCuotas.......:" + txtCuotas.getText());
                }
                panelDetalle.setVisible(false);
                esTarjeta = true;
            } else {
                esTarjeta = false;
            }

            System.out.println("\tBASE DE DATOS LOCAL");
            if (cobro_caja()) {
                cobrando = true;
                System.out.println("\tCobro registrado, comprobante nro: " + nroComprobante);
                System.out.println("\tForma de Pago:" + txtFormaPago.getText());
                System.out.println("\tMoneda.......:" + txtMoneda.getText());
                System.out.println("\tImporte......:" + txtImporte.getText());
                if (detalle_cobro()) {
                    System.out.println("\tDetalle de cobro registrado");
                    registrosTabla();
                } else {
                    System.out.println("\tDetalle de cobro no registrado");
                }

                if (saldoPagar <= 50) {
                    hStock = new HiloMovimStock();
                    hStock.setTablaProductos(listaProductos);
                    hStock.setStkParametros(stkParametros);
                    hStock.start();

                    vtaEmision();
                    vuelto();

                    ControlImpresora.abrirGaveta();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    footerTicket();

                    btnSalir.requestFocus();
                    cobrando = false;
                    limpiar();

                } else {
                    txtFormaPago.requestFocus();
                }
            } else {
                ControlMensajes.error("Cobro no registrado", tituloMsj);
                System.out.println("\tCobro no registrado, comprobante nro: " + nroComprobante);
                System.out.println("\tDetalle de cobro no registrado");
                System.out.println("\tComprobante registrado con errores");
                System.out.println("<FIN DE PROCESO DE FACTURACION>");
                txtFormaPago.requestFocus();
            }
        }
        idFormaPago = 0;
        idMoneda = 0;
        esTarjeta = false;
        idProcesadora = 0;
        idTipoTarjeta = 0;
        esOrden = false;
        return true;

    }

    private String numero(String nro) {
        String ceros = "";
        int dif;
        dif = ContParamAplicacion.VTA_CTRL_COMP_LONG_NRO_COMPROB - nro.length();

        if (dif > 0) {
            for (int i = 0; i < dif; i++) {
                ceros = ceros + "0";
            }
        } else {
            ceros = "";
        }
        return ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-" + ceros + nro;
    }

    private void footerTicket() {
        String lineaImpresion = "";

        //TOTALES A PAGAR DEL COMPROBANTE
        lineaImpresion = ticket.separador() + "\n";
        lineaImpresion += ticket.dobleColumna("TOTAL:", miles.format(FrmPos.totalMonto)) + "\n";
        lineaImpresion += ticket.dobleColumna("DESCUENTO SEDECO RES. 347:", miles.format(descto_sedeco)) + "\n";
        lineaImpresion += ticket.dobleColumna("SUB TOTAL A PAGAR:", miles.format(total_a_pagar)) + "\n";

        //GRAVADAS DEL COMPROBANTE
        lineaImpresion += ticket.separador() + "\n";
        if (Math.round(FrmPos.montoGravado5) > 0) {
            lineaImpresion += ticket.dobleColumna("TOTAL GRAVADAS 5%:", miles.format(Math.round(FrmPos.montoGravado5 + FrmPos.montoImpuesto5))) + "\n";
        }
        if (Math.round(FrmPos.montoGravado10) > 0) {
            lineaImpresion += ticket.dobleColumna("TOTAL GRAVADAS 10%:", miles.format(Math.round(FrmPos.montoGravado10 + FrmPos.montoImpuesto10))) + "\n";
        }
        if (FrmPos.importeExento > 0) {
            lineaImpresion += ticket.dobleColumna("TOTAL EXENTAS:", miles.format(Math.round(FrmPos.importeExento))) + "\n";
        }
        lineaImpresion += ticket.dobleColumna("TOTAL GRAVADAS:", miles.format(Math.round(FrmPos.montoGravado5 + FrmPos.montoImpuesto5) + Math.round(FrmPos.montoGravado10 + FrmPos.montoImpuesto10))) + "\n";

        //LIQUIDACION DEL IVA DEL COMPROBANTE
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += "LIQUIDACION IVA \n";
        if (Math.round(FrmPos.montoImpuesto5) > 0) {
            lineaImpresion += ticket.dobleColumna("TOTAL I.V.A. 5%:", miles.format(Math.round(FrmPos.montoImpuesto5))) + "\n";
        }
        if (Math.round(FrmPos.montoImpuesto10) > 0) {
            lineaImpresion += ticket.dobleColumna("TOTAL I.V.A. 10%:", miles.format(Math.round(FrmPos.montoImpuesto10))) + "\n";
        }
        int totalIva = (int) (Math.round(FrmPos.montoImpuesto10) + Math.round(FrmPos.montoImpuesto5));
        lineaImpresion += ticket.dobleColumna("TOTAL I.V.A.:", miles.format(totalIva)) + "\n";

        //CLIENTE DEL COMPROBANTE
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += ticket.dobleColumna("C.I. / R.U.C.:", FrmPosCliente.nroRuc) + "\n";
        lineaImpresion += ticket.dobleColumna("CLIENTE: ", FrmPosCliente.razonSocial) + "\n";

        //TOTALES DEL COMPROBANTE
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += ticket.dobleColumna("ARTICULOS:", String.valueOf(FrmPos.totalItems)) + "\n";
        lineaImpresion += ticket.dobleColumna("TOTAL:", miles.format(FrmPos.totalMonto)) + "\n";
        lineaImpresion += ticket.dobleColumna("TOTAL A PAGAR:", miles.format(total_a_pagar)) + "\n";

        //FORMAS DE PAGO DEL COMPROBANTE
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += "FORMAS DE PAGOS \n";
        int impTotal;
        for (int i = 0; i < tablaPagos.getRowCount(); i++) {
            impTotal = Integer.parseInt(tablaPagos.getValueAt(i, 2).toString().replace(".", ""));
            lineaImpresion += ticket.dobleColumna(tablaPagos.getValueAt(i, 0).toString() + ":", miles.format(impTotal)) + "\n";
        }
        lineaImpresion += ticket.dobleColumna("VUELTO:", miles.format(vuelto)) + "\n";

        //FIN DEL COMPROBANTE
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += ticket.centrar("--GRACIAS POR SU PREFERENCIA--") + "\n";
        lineaImpresion += ticket.centrar("ORIGINAL: CLIENTE") + "\n";
        lineaImpresion += ticket.centrar("DUPLICADO: ARCHIVO TRIBUTARIO") + "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
        }

        ControlImpresora.imprimir(lineaImpresion);
        ControlImpresora.cortarPapel();

        //obtener promociones por monto
        getPromocionesMonto();

        //obtener promociones por proveedor
        getPromocionesProveedor();

        System.out.println("Limpiando controles de punto de venta..............");
        FrmPos.reiniciarValores();
        FrmPos.txtCodigoBarras.requestFocus();

        this.dispose();
    }

    private void btnSalir() {
        if (tablaPagos.getRowCount() > 0) {
            ControlMensajes.error("Debe finalizar cobro del comprobante", tituloMsj);
            txtFormaPago.requestFocus();
        } else {
            this.dispose();
        }
    }

    private void setLabels() {
        txtNroTarjeta.setText("");
        txtProcesadora.setText("");
        txtTipoTarjeta.setText("");
        txtCuotas.setText("");

        if (esTarjeta) {
            lbl1.setText("Nro. Boleta:");
            lbl2.setText("Procesadora:");
            lbl3.setText("Tipo Tarjeta:");
            lbl4.setText("Cant. Cuotas:");
            txtCuotas.setVisible(true);
            lbl4.setVisible(true);
            btnTipo.setVisible(true);
        }
        if (esOrden) {
            lbl1.setText("Nro. Orden:");
            lbl2.setText("Ent. Emisora:");
            lbl3.setText("Beneficiario:");
            lbl4.setVisible(false);
            txtCuotas.setVisible(false);
            btnTipo.setVisible(false);

        }
    }

    public void setRedondeoSedeco() {
        int multiplo = 50;
        long total_vta = FrmPos.totalMonto;

        total_a_pagar = multiplo * (Math.round(total_vta / multiplo));
        descto_sedeco = (int) (total_vta - total_a_pagar);

        txtSaldoPagar.setText(miles.format(total_a_pagar));
        txtDescuento.setText(miles.format(descto_sedeco));

    }

    void getPromocionesMonto() {
        String lineaImpresion = "";

        ContAppVtaPromociones contAppVtaPromociones = new ContAppVtaPromociones();
        listaPromocionesMonto = contAppVtaPromociones.getPromocionesActivas();

        if (listaPromocionesMonto != null) {
            if (listaPromocionesMonto.size() > 0) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrmPosCobros.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i <= listaPromocionesMonto.size() - 1; i++) {
                    if (FrmPos.totalMonto >= listaPromocionesMonto.get(i).getMONTO()) {
                        lineaImpresion += ticket.separadorDoble() + "\n";
                        lineaImpresion += ticket.centrar(listaPromocionesMonto.get(i).getNOMBRE()) + "\n";
                        lineaImpresion += ticket.centrar(listaPromocionesMonto.get(i).getFECHA_DESDE() + " - " + listaPromocionesMonto.get(i).getFECHA_HASTA()) + "\n";
                        lineaImpresion += "CUPON NRO. : " + String.valueOf(listaPromocionesMonto.get(i).getULT_NUMERO() + 1) + "    " + ContParamAplicacion.CAJA_DESCRIPCION + "\n";
                        lineaImpresion += ticket.lineaEscritura("NOMBRE/S   :") + "\n";
                        lineaImpresion += ticket.lineaEscritura("APELLIDO/S :") + "\n";
                        lineaImpresion += ticket.lineaEscritura("C.I.       :") + "\n";
                        lineaImpresion += ticket.lineaEscritura("TELEFONO   :") + "\n";
                        lineaImpresion += "\n";
                        lineaImpresion += "\n";
                        lineaImpresion += "\n";
                        lineaImpresion += "\n";
                        lineaImpresion += "\n";
                        lineaImpresion += "\n";
                        lineaImpresion += "\n";
                        ControlImpresora.imprimir(lineaImpresion);
                        ControlImpresora.cortarPapel();

                        if (!contAppVtaPromociones.actualizarUltimoNumero(listaPromocionesMonto.get(i).getIDENTIFICADOR(), listaPromocionesMonto.get(i).getULT_NUMERO() + 1, 0)) {
                            ControlMensajes.error("Ultimo numero de cupon no actualizado - local", "Cobro");
                        }
                        if (Utilidades.getPing()) {
                            if (!contAppVtaPromociones.actualizarUltimoNumero(listaPromocionesMonto.get(i).getIDENTIFICADOR(), listaPromocionesMonto.get(i).getULT_NUMERO() + 1, 1)) {
                                ControlMensajes.error("Ultimo numero de cupon no actualizado - server", tituloMsj);
                            }
                        }
                    }
                }
            }
        }
    }

    void getPromocionesProveedor() {
        String lineaImpresion = "";
        ContAppVtaPromocionesProv contAppVtaPromocionesProv = new ContAppVtaPromocionesProv();

        //recorrer productos
        for (int i = 0; i < listaProductos.size(); i++) {

            //validar si esta en promocion
            listaPromocionesProv = contAppVtaPromocionesProv.validarProductoPromo(listaProductos.get(i).getIdProducto());
            if (listaPromocionesProv != null) {
                if (listaPromocionesProv.size() > 0) {

                    //recoorer en caso de que traiga el producto en varias promociones
                    for (int j = 0; j < listaPromocionesProv.size(); j++) {

                        //imprimir de acuerdo a la cantidad de productos comprados
                        for (int k = 1; k <= (int) listaProductos.get(i).getCantidad(); k++) {
                            lineaImpresion += ticket.separadorDoble() + "\n";
                            lineaImpresion += ticket.centrar(listaPromocionesProv.get(j).getNOMBRE()) + "\n";
                            lineaImpresion += ticket.centrar(listaPromocionesProv.get(j).getFECHA_DESDE() + " - " + listaPromocionesProv.get(j).getFECHA_HASTA()) + "\n";
                            lineaImpresion += "COMPROBANTE NRO. : " + FrmPos.numeroComprobante + "\n";
                            lineaImpresion += ticket.lineaEscritura("NOMBRE/S   :") + "\n";
                            lineaImpresion += ticket.lineaEscritura("APELLIDO/S :") + "\n";
                            lineaImpresion += ticket.lineaEscritura("C.I.       :") + "\n";
                            lineaImpresion += ticket.lineaEscritura("TELEFONO   :") + "\n";
                            lineaImpresion += "\n";
                            lineaImpresion += "\n";
                            lineaImpresion += "\n";
                            lineaImpresion += "\n";
                            lineaImpresion += "\n";
                            lineaImpresion += "\n";
                            lineaImpresion += "\n";
                            ControlImpresora.imprimir(lineaImpresion);
                            ControlImpresora.cortarPapel();
                        }

                        if (!contAppVtaPromocionesProv.actualizarCantidad(listaPromocionesProv.get(j).getIDENTIFICADOR(), (int) listaProductos.get(i).getCantidad(), 0)) {
                            ControlMensajes.error("Ultimo numero de cupon no actualizado - local", "Cobro");
                        }
                        if (Utilidades.getPing()) {
                            if (!contAppVtaPromocionesProv.actualizarCantidad(listaPromocionesProv.get(j).getIDENTIFICADOR(), (int) listaProductos.get(i).getCantidad(), 1)) {
                                ControlMensajes.error("Ultimo numero de cupon no actualizado - server", tituloMsj);
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPagos = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFormaPago = new javax.swing.JTextField();
        txtMoneda = new javax.swing.JTextField();
        btnFormaPago = new javax.swing.JButton();
        btnMoneda = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panelDetalle = new javax.swing.JPanel();
        lbl1 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        txtNroTarjeta = new javax.swing.JTextField();
        txtProcesadora = new javax.swing.JTextField();
        txtTipoTarjeta = new javax.swing.JTextField();
        txtCuotas = new javax.swing.JTextField();
        btnTipo = new javax.swing.JButton();
        btnProcesadora = new javax.swing.JButton();
        pnlTotales = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl7 = new javax.swing.JLabel();
        txtSaldoPagar = new javax.swing.JTextField();
        txtTotalVenta = new javax.swing.JTextField();
        txtVuelto = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTotalRecibido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        txtImporte = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(".::DETALLES DE COBRO::.");
        setIconImage(null);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        tablaPagos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tablaPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaPagos.setRowHeight(27);
        tablaPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPagosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPagos);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Forma de Pago:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Moneda:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Importe:");

        txtFormaPago.setEditable(false);
        txtFormaPago.setBackground(new java.awt.Color(0, 9, 18));
        txtFormaPago.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtFormaPago.setForeground(new java.awt.Color(255, 255, 255));
        txtFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormaPagoKeyPressed(evt);
            }
        });

        txtMoneda.setEditable(false);
        txtMoneda.setBackground(new java.awt.Color(0, 9, 18));
        txtMoneda.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtMoneda.setForeground(new java.awt.Color(255, 255, 255));
        txtMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMonedaKeyPressed(evt);
            }
        });

        btnFormaPago.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnFormaPago.setForeground(new java.awt.Color(0, 0, 0));
        btnFormaPago.setText("...");
        btnFormaPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFormaPagoMouseClicked(evt);
            }
        });
        btnFormaPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnFormaPagoKeyPressed(evt);
            }
        });

        btnMoneda.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnMoneda.setForeground(new java.awt.Color(0, 0, 0));
        btnMoneda.setText("...");
        btnMoneda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMonedaMouseClicked(evt);
            }
        });
        btnMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnMonedaKeyPressed(evt);
            }
        });

        btnAceptar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
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

        btnCancelar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
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

        panelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalles de Tarjeta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 11))); // NOI18N
        panelDetalle.setForeground(new java.awt.Color(102, 102, 102));

        lbl1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl1.setText("nro tarjeta/orden");

        lbl2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl2.setText("procesadora/ entidad");

        lbl4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl4.setText("Cuota:");

        lbl3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl3.setText("Tipo/ beneficiario");

        txtNroTarjeta.setBackground(new java.awt.Color(0, 9, 18));
        txtNroTarjeta.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNroTarjeta.setForeground(new java.awt.Color(255, 255, 255));
        txtNroTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNroTarjetaKeyPressed(evt);
            }
        });

        txtProcesadora.setEditable(false);
        txtProcesadora.setBackground(new java.awt.Color(0, 9, 18));
        txtProcesadora.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtProcesadora.setForeground(new java.awt.Color(255, 255, 255));
        txtProcesadora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProcesadoraKeyPressed(evt);
            }
        });

        txtTipoTarjeta.setBackground(new java.awt.Color(0, 9, 18));
        txtTipoTarjeta.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtTipoTarjeta.setForeground(new java.awt.Color(255, 255, 255));
        txtTipoTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTipoTarjetaKeyPressed(evt);
            }
        });

        txtCuotas.setBackground(new java.awt.Color(0, 9, 18));
        txtCuotas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtCuotas.setForeground(new java.awt.Color(255, 255, 255));
        txtCuotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCuotasKeyPressed(evt);
            }
        });

        btnTipo.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btnTipo.setForeground(new java.awt.Color(0, 0, 0));
        btnTipo.setText("...");
        btnTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTipoMouseClicked(evt);
            }
        });
        btnTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTipoKeyPressed(evt);
            }
        });

        btnProcesadora.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btnProcesadora.setForeground(new java.awt.Color(0, 0, 0));
        btnProcesadora.setText("...");
        btnProcesadora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProcesadoraMouseClicked(evt);
            }
        });
        btnProcesadora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnProcesadoraKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDetalleLayout = new javax.swing.GroupLayout(panelDetalle);
        panelDetalle.setLayout(panelDetalleLayout);
        panelDetalleLayout.setHorizontalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetalleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTipoTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(txtProcesadora))
                        .addGap(1, 1, 1)
                        .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnProcesadora, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNroTarjeta)
                    .addComponent(txtCuotas))
                .addContainerGap())
        );
        panelDetalleLayout.setVerticalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl1))
                .addGap(12, 12, 12)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl2)
                    .addComponent(txtProcesadora, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcesadora))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTipo)
                    .addComponent(lbl3))
                .addGap(7, 7, 7)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lbl4))
                    .addComponent(txtCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pnlTotales.setBackground(java.awt.Color.darkGray);
        pnlTotales.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Total Venta:");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Total Vuelto:");

        lbl7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl7.setForeground(new java.awt.Color(255, 255, 255));
        lbl7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl7.setText("Saldo a Pagar:");

        txtSaldoPagar.setEditable(false);
        txtSaldoPagar.setBackground(new java.awt.Color(0, 9, 18));
        txtSaldoPagar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtSaldoPagar.setForeground(new java.awt.Color(204, 204, 0));
        txtSaldoPagar.setText("123456789");
        txtSaldoPagar.setBorder(null);

        txtTotalVenta.setEditable(false);
        txtTotalVenta.setBackground(new java.awt.Color(0, 9, 18));
        txtTotalVenta.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTotalVenta.setForeground(new java.awt.Color(204, 204, 0));
        txtTotalVenta.setText("123456789");
        txtTotalVenta.setBorder(null);

        txtVuelto.setEditable(false);
        txtVuelto.setBackground(new java.awt.Color(0, 9, 18));
        txtVuelto.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtVuelto.setForeground(new java.awt.Color(204, 204, 0));
        txtVuelto.setText("123456789");
        txtVuelto.setBorder(null);

        txtDescuento.setEditable(false);
        txtDescuento.setBackground(new java.awt.Color(0, 9, 18));
        txtDescuento.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtDescuento.setForeground(new java.awt.Color(204, 204, 0));
        txtDescuento.setText("123456789");
        txtDescuento.setBorder(null);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Descto:");

        txtTotalRecibido.setEditable(false);
        txtTotalRecibido.setBackground(new java.awt.Color(0, 9, 18));
        txtTotalRecibido.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTotalRecibido.setForeground(new java.awt.Color(204, 204, 0));
        txtTotalRecibido.setText("123456789");
        txtTotalRecibido.setBorder(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Recibido:");

        javax.swing.GroupLayout pnlTotalesLayout = new javax.swing.GroupLayout(pnlTotales);
        pnlTotales.setLayout(pnlTotalesLayout);
        pnlTotalesLayout.setHorizontalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalesLayout.createSequentialGroup()
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVuelto))
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                                .addComponent(lbl7)
                                .addGap(10, 10, 10)
                                .addComponent(txtSaldoPagar))
                            .addGroup(pnlTotalesLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtTotalVenta))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtDescuento))))
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtTotalRecibido, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlTotalesLayout.setVerticalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTotalRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl7)
                    .addComponent(txtSaldoPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        btnEliminar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        btnEliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEliminarKeyPressed(evt);
            }
        });

        txtImporte.setBackground(new java.awt.Color(0, 9, 18));
        txtImporte.setForeground(new java.awt.Color(204, 204, 0));
        txtImporte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtImporte.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtImporteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlTotales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(txtFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(btnFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtImporte)
                                            .addComponent(txtMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panelDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(3, 3, 3))))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel6))
                            .addComponent(txtFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(btnFormaPago)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMoneda)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel7))
                            .addComponent(txtMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFormaPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormaPagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F9) {
            getVistas("FP");
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFormaPago.getText().isEmpty()) {
                getFormaPago("select IDENTIFICADOR, DESCRIPCION, ABREVIATURA, ID_TIPO_DOCUM, VR_CLASE from FND_FORMAS_PAGO where ES_PRINCIPAL='S'");
            }

            if (idFormaPago == 10) {
                esTarjeta = true;
                esOrden = false;
            }

            if (idFormaPago == 1843) {
                esOrden = true;
                esTarjeta = false;
            }

            if (idFormaPago == 1863) {
                esOrden = true;
                esTarjeta = false;
            }

            txtImporte.requestFocus();

        }

        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }

        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            getVistas("FP");
        }
    }//GEN-LAST:event_txtFormaPagoKeyPressed

    private void txtMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonedaKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F9:
                getVistas("MN");
                break;

            case KeyEvent.VK_ENTER:
                if (txtMoneda.getText().isEmpty()) {
                    getMoneda("SELECT IDENTIFICADOR, CODIGO, DESCRIPCION FROM FND_MONEDAS WHERE ES_PRINCIPAL='S'");
                }

                if (esTarjeta) {
                    panelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles de Tarjeta"));
                    idVoucher = contCobrosCaja.identificadorVoucher();
                    panelDetalle.setVisible(true);
                    setLabels();
                    txtNroTarjeta.requestFocus();
                }

                if (esOrden) {
                    panelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordenes de Compra / Vales"));
                    panelDetalle.setVisible(true);
                    setLabels();
                    txtNroTarjeta.requestFocus();
                }

                if (esOrden == false && esTarjeta == false) {
                    btnAceptar.requestFocus();
                }
                break;

            case KeyEvent.VK_ESCAPE:
                btnSalir();
                break;

            case KeyEvent.VK_SPACE:
                getVistas("MN");
                break;

        }
    }//GEN-LAST:event_txtMonedaKeyPressed

    private void txtNroTarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroTarjetaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtNroTarjeta.getText().isEmpty()) {
                txtProcesadora.requestFocus();
            }
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_txtNroTarjetaKeyPressed

    private void txtProcesadoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcesadoraKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (vr_forma_pago.equals("TC")) {
                    if (!txtProcesadora.getText().isEmpty()) {
                        txtTipoTarjeta.requestFocus();
                    }
                } else {
                    txtTipoTarjeta.requestFocus();
                }
                break;

            case KeyEvent.VK_F9:
                if (esTarjeta) {
                    getVistas("PR");
                }
                if (esOrden) {
                    getVistas("EN");
                }
                break;

            case KeyEvent.VK_SPACE:
                if (esTarjeta) {
                    getVistas("PR");
                }
                if (esOrden) {
                    getVistas("EN");
                }
                break;

            case KeyEvent.VK_ESCAPE:
                btnSalir();
                break;
        }
    }//GEN-LAST:event_txtProcesadoraKeyPressed

    private void txtTipoTarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoTarjetaKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (!txtTipoTarjeta.getText().isEmpty()) {
                    if (vr_forma_pago.equals("TC")) {
                        txtCuotas.requestFocus();
                    } else {
                        btnAceptar.requestFocus();
                    }
                }
                break;

            case KeyEvent.VK_F9:
                if (esTarjeta) {
                    getVistas("TT");
                }
                break;

            case KeyEvent.VK_SPACE:
                if (esTarjeta) {
                    getVistas("TT");
                }
                break;

            case KeyEvent.VK_ESCAPE:
                btnSalir();
                break;

        }
    }//GEN-LAST:event_txtTipoTarjetaKeyPressed

    private void txtCuotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCuotasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCuotas.getText().isEmpty()) {
                txtCuotas.setText("1");
            }
            btnAceptarKeyPressed(evt);
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_txtCuotasKeyPressed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        if (evt.getClickCount() == 1) {
            if (impresora.habilitado()) {
                if (txtImporte()) {
                    FrmPos.lblComprobante.setText(numero(String.valueOf(ultimoNro[1] + 1)));
                    limpiar();
                }
            } else {
                ControlMensajes.error("Impresora no disponible", tituloMsj);
                btnAceptar.requestFocus();
            }

        }
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        btnSalir();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSalir();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            limpiar();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAceptarKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (impresora.habilitado()) {
                if (txtImporte()) {
                    FrmPos.lblComprobante.setText(numero(String.valueOf(ultimoNro[1] + 1)));
                    limpiar();
                }
            } else {
                ControlMensajes.error("Impresora no disponible", tituloMsj);
                btnAceptar.requestFocus();
                return;
            }
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnAceptarKeyPressed

    private void btnFormaPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFormaPagoMouseClicked
        if (evt.getClickCount() == 1) {
            getVistas("FP");
            txtFormaPago.requestFocus();
        }
    }//GEN-LAST:event_btnFormaPagoMouseClicked

    private void btnMonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMonedaMouseClicked
        if (evt.getClickCount() == 1) {
            getVistas("MN");
            txtMoneda.requestFocus();
        }
    }//GEN-LAST:event_btnMonedaMouseClicked

    private void btnProcesadoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProcesadoraMouseClicked
        if (evt.getClickCount() == 1) {
            if (esTarjeta) {
                getVistas("PR");
                txtProcesadora.requestFocus();
            }
            if (esOrden) {
                getVistas("EN");
                txtProcesadora.requestFocus();
            }
        }
    }//GEN-LAST:event_btnProcesadoraMouseClicked

    private void btnTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTipoMouseClicked
        if (evt.getClickCount() == 1) {
            getVistas("TT");
            txtTipoTarjeta.requestFocus();
        }
    }//GEN-LAST:event_btnTipoMouseClicked

    private void btnEliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEliminarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (ControlMensajes.confirmacion("¿Deseas eliminar el cobro?", tituloMsj)) {

                int recibido = Integer.parseInt(txtTotalRecibido.getText().replaceAll("\\.", ""));
                int saldo = Integer.parseInt(txtSaldoPagar.getText().replaceAll("\\.", ""));

                txtTotalRecibido.setText(String.valueOf(Integer.parseInt(tablaPagos.getValueAt(indiceTabla, 2).toString().replaceAll("\\.", "")) - recibido));
                txtSaldoPagar.setText(String.valueOf(Integer.parseInt(tablaPagos.getValueAt(indiceTabla, 2).toString().replaceAll("\\.", "")) + saldo));

                if (eliminarPago(Integer.parseInt(tablaPagos.getValueAt(indiceTabla, 4).toString()))) {
                    ControlMensajes.informacion("Cobro eliminado", tituloMsj);
                    modelo.removeRow(indiceTabla);
                } else {
                    ControlMensajes.error("Cobro no eliminado", tituloMsj);
                }
                txtFormaPago.requestFocus();
            } else {
                txtFormaPago.requestFocus();
            }
        } else if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnEliminarKeyPressed

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        if (evt.getClickCount() == 1) {
            if (ControlMensajes.confirmacion("¿Deseas eliiminar el cobro?", tituloMsj)) {

                int recibido = Integer.parseInt(txtTotalRecibido.getText().replaceAll("\\.", ""));
                int saldo = Integer.parseInt(txtSaldoPagar.getText().replaceAll("\\.", ""));

                txtTotalRecibido.setText(String.valueOf(Integer.parseInt(tablaPagos.getValueAt(indiceTabla, 2).toString().replaceAll("\\.", "")) - recibido));
                txtSaldoPagar.setText(String.valueOf(Integer.parseInt(tablaPagos.getValueAt(indiceTabla, 2).toString().replaceAll("\\.", "")) + saldo));

                if (eliminarPago(Integer.parseInt(tablaPagos.getValueAt(indiceTabla, 4).toString()))) {
                    ControlMensajes.informacion("Cobro eliminado", tituloMsj);
                    modelo.removeRow(indiceTabla);
                } else {
                    ControlMensajes.error("Cobro no eliminado", tituloMsj);
                }
                txtFormaPago.requestFocus();
            } else {
                txtFormaPago.requestFocus();
            }
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void tablaPagosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPagosMouseClicked
        int fila = tablaPagos.rowAtPoint(evt.getPoint());
        if (fila != 0) {
            indiceTabla = fila;
            btnEliminar.setEnabled(true);
        }
    }//GEN-LAST:event_tablaPagosMouseClicked

    private void btnFormaPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFormaPagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFormaPago.getText().isEmpty()) {
                getVistas("FP");
            } else {
                txtFormaPagoKeyPressed(evt);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnFormaPagoKeyPressed

    private void btnMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnMonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFormaPago.getText().isEmpty()) {
                getVistas("MN");
            } else {
                txtMonedaKeyPressed(evt);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnMonedaKeyPressed

    private void btnProcesadoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnProcesadoraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtProcesadora.getText().isEmpty()) {
                getVistas("PR");
            } else {
                txtProcesadoraKeyPressed(evt);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnProcesadoraKeyPressed

    private void btnTipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTipoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtTipoTarjeta.getText().isEmpty()) {
                getVistas("TT");
            } else {
                txtTipoTarjetaKeyPressed(evt);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_btnTipoKeyPressed

    private void txtImporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtImporte.getText().isEmpty()) {
                txtMoneda.requestFocus();
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnSalir();
        }
    }//GEN-LAST:event_txtImporteKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        btnSalir();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPosCobros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFormaPago;
    private javax.swing.JButton btnMoneda;
    private javax.swing.JButton btnProcesadora;
    public static javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl7;
    private javax.swing.JPanel panelDetalle;
    private javax.swing.JPanel pnlTotales;
    private javax.swing.JTable tablaPagos;
    public static javax.swing.JTextField txtCuotas;
    private javax.swing.JTextField txtDescuento;
    public static javax.swing.JTextField txtFormaPago;
    private javax.swing.JFormattedTextField txtImporte;
    public static javax.swing.JTextField txtMoneda;
    private javax.swing.JTextField txtNroTarjeta;
    public static javax.swing.JTextField txtProcesadora;
    private javax.swing.JTextField txtSaldoPagar;
    public static javax.swing.JTextField txtTipoTarjeta;
    private javax.swing.JTextField txtTotalRecibido;
    private javax.swing.JTextField txtTotalVenta;
    private javax.swing.JTextField txtVuelto;
    // End of variables declaration//GEN-END:variables

}
