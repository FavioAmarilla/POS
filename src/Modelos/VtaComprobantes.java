package Modelos;

import java.sql.Timestamp;

public class VtaComprobantes {

    long identificador;
    int idTransaccion;
    String numero, estado;
    int idSitio, idMoneda, idTipoComprob;
    long montoTotal;
    String usuario, usrCre;
    long idCliente;
    int idSitioCliente, idVendedor;
    String nroReferencia;
    double montoExento, montoGravado, montoGravado5, montoGravado10;
    double montoDescuento, montoImpuesto, montoImpuesto5, montoImpuesto10;
    double montoRetencion, montoPagado;
    int idFormaPago, diasPorPago, tipoCambio, porcDescuento, cantidadPagos;
    String usrEmision, usrAnulacion, usrMod;
    String razonSocial, vrTipoDocumento, numeroDocumento, direccion, telefonos;
    double montoUtilizado, montoEntInic;
    String descripcion;
    int idTipoCompra, idCaja, nroTicket;
    String nroRegImpos, idReferencia;
    int porcEntinic, idRegistroCtrl, idPuntoEmision;
    int idAlmacen, idPedido, idEmpresa, idUnidad;
    String atributo1, atributo2, atributo3, atributo4, atributo5;
    long idMovimStock;
    int idMoviStkRev, idProcesoVenta, idTipoVenta, idProyecto;
    int idPAciente, idEmpSeguro, idTipoCtaRef;
    String nroCtaRef;
    int tasaIntMora, idCompRetencion, idTipoRetencion;
    long idControlCaja;
    int tasaRetencion, idSuprCancel, idListaPecios;
    String tansferidoSc;
    int idFactura, idComprobOrigen, idPlanArqueos;
    int cantCupones, cantPuntos, idCtaContable;
    String programa, apellidos, nombres;
    long montoTotalReal, montoRedondeo, montoTotalMl, montoAplicaciones;
    int descuento_347;
    Timestamp fecha, fechaVencimiento, fecEmision, fecAnulacion, fechaContable;
    Timestamp fecMod, fechaUltimoPago, fecTransferSc, fecCre, fechaEnvSrv;

    String fefecha, feVencimiento, feEmision, feAnulacion, fehaContable;
    String feMod, feUltimoPago, feTransferSc, feCre, feEnvSrv;

    public VtaComprobantes() {
    }

    public VtaComprobantes(int identificador, int idTransaccion, String numero, String estado, int idSitio, int idMoneda, int idTipoComprob, long montoTotal, String usuario, String usrCre, long idCliente, int idSitioCliente, int idVendedor, String nroReferencia, double montoExento, double montoGravado, double montoGravado5, double montoGravado10, double montoDescuento, double montoImpuesto, double montoImpuesto5, double montoImpuesto10, double montoRetencion, double montoPagado, int idFormaPago, int diasPorPago, int tipoCambio, int porcDescuento, int cantidadPagos, String usrEmision, String usrAnulacion, String usrMod, String razonSocial, String vrTipoDocumento, String numeroDocumento, String direccion, String telefonos, double montoUtilizado, double montoEntInic, String descripcion, int idTipoCompra, int idCaja, int nroTicket, String nroRegImpos, String idReferencia, int porcEntinic, int idRegistroCtrl, int idPuntoEmision, int idAlmacen, int idPedido, int idEmpresa, int idUnidad, String atributo1, String atributo2, String atributo3, String atributo4, String atributo5, int idMovimStock, int idMoviStkRev, int idProcesoVenta, int idTipoVenta, int idProyecto, int idPAciente, int idEmpSeguro, int idTipoCtaRef, String nroCtaRef, int tasaIntMora, int idCompRetencion, int idControlCaja, int idTipoRetencion, int tasaRetencion, int idSuprCancel, int idListaPecios, String tansferidoSc, int idFactura, int idComprobOrigen, int idPlanArqueos, int cantCupones, int cantPuntos, int idCtaContable, String programa, String apellidos, String nombres, long montoTotalReal, long montoRedondeo, long montoTotalMl, long montoAplicaciones, Timestamp fecha, Timestamp fechaVencimiento, Timestamp fecEmision, Timestamp fecAnulacion, Timestamp fechaContable, Timestamp fecMod, Timestamp fechaUltimoPago, Timestamp fecTransferSc, Timestamp fecCre, Timestamp fechaEnvSrv, String fefecha, String feVencimiento, String feEmision, String feAnulacion, String fehaContable, String feMod, String feUltimoPago, String feTransferSc, String feCre, String feEnvSrv, int descuento_347) {
        this.identificador = identificador;
        this.idTransaccion = idTransaccion;
        this.numero = numero;
        this.estado = estado;
        this.idSitio = idSitio;
        this.idMoneda = idMoneda;
        this.idTipoComprob = idTipoComprob;
        this.montoTotal = montoTotal;
        this.usuario = usuario;
        this.usrCre = usrCre;
        this.idCliente = idCliente;
        this.idSitioCliente = idSitioCliente;
        this.idVendedor = idVendedor;
        this.nroReferencia = nroReferencia;
        this.montoExento = montoExento;
        this.montoGravado = montoGravado;
        this.montoGravado5 = montoGravado5;
        this.montoGravado10 = montoGravado10;
        this.montoDescuento = montoDescuento;
        this.montoImpuesto = montoImpuesto;
        this.montoImpuesto5 = montoImpuesto5;
        this.montoImpuesto10 = montoImpuesto10;
        this.montoRetencion = montoRetencion;
        this.montoPagado = montoPagado;
        this.idFormaPago = idFormaPago;
        this.diasPorPago = diasPorPago;
        this.tipoCambio = tipoCambio;
        this.porcDescuento = porcDescuento;
        this.cantidadPagos = cantidadPagos;
        this.usrEmision = usrEmision;
        this.usrAnulacion = usrAnulacion;
        this.usrMod = usrMod;
        this.razonSocial = razonSocial;
        this.vrTipoDocumento = vrTipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.direccion = direccion;
        this.telefonos = telefonos;
        this.montoUtilizado = montoUtilizado;
        this.montoEntInic = montoEntInic;
        this.descripcion = descripcion;
        this.idTipoCompra = idTipoCompra;
        this.idCaja = idCaja;
        this.nroTicket = nroTicket;
        this.nroRegImpos = nroRegImpos;
        this.idReferencia = idReferencia;
        this.porcEntinic = porcEntinic;
        this.idRegistroCtrl = idRegistroCtrl;
        this.idPuntoEmision = idPuntoEmision;
        this.idAlmacen = idAlmacen;
        this.idPedido = idPedido;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.atributo1 = atributo1;
        this.atributo2 = atributo2;
        this.atributo3 = atributo3;
        this.atributo4 = atributo4;
        this.atributo5 = atributo5;
        this.idMovimStock = idMovimStock;
        this.idMoviStkRev = idMoviStkRev;
        this.idProcesoVenta = idProcesoVenta;
        this.idTipoVenta = idTipoVenta;
        this.idProyecto = idProyecto;
        this.idPAciente = idPAciente;
        this.idEmpSeguro = idEmpSeguro;
        this.idTipoCtaRef = idTipoCtaRef;
        this.nroCtaRef = nroCtaRef;
        this.tasaIntMora = tasaIntMora;
        this.idCompRetencion = idCompRetencion;
        this.idControlCaja = idControlCaja;
        this.idTipoRetencion = idTipoRetencion;
        this.tasaRetencion = tasaRetencion;
        this.idSuprCancel = idSuprCancel;
        this.idListaPecios = idListaPecios;
        this.tansferidoSc = tansferidoSc;
        this.idFactura = idFactura;
        this.idComprobOrigen = idComprobOrigen;
        this.idPlanArqueos = idPlanArqueos;
        this.cantCupones = cantCupones;
        this.cantPuntos = cantPuntos;
        this.idCtaContable = idCtaContable;
        this.programa = programa;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.montoTotalReal = montoTotalReal;
        this.montoRedondeo = montoRedondeo;
        this.montoTotalMl = montoTotalMl;
        this.montoAplicaciones = montoAplicaciones;
        this.fecha = fecha;
        this.fechaVencimiento = fechaVencimiento;
        this.fecEmision = fecEmision;
        this.fecAnulacion = fecAnulacion;
        this.fechaContable = fechaContable;
        this.fecMod = fecMod;
        this.fechaUltimoPago = fechaUltimoPago;
        this.fecTransferSc = fecTransferSc;
        this.fecCre = fecCre;
        this.fechaEnvSrv = fechaEnvSrv;
        this.fefecha = fefecha;
        this.feVencimiento = feVencimiento;
        this.feEmision = feEmision;
        this.feAnulacion = feAnulacion;
        this.fehaContable = fehaContable;
        this.feMod = feMod;
        this.feUltimoPago = feUltimoPago;
        this.feTransferSc = feTransferSc;
        this.feCre = feCre;
        this.feEnvSrv = feEnvSrv;
        this.descuento_347 = descuento_347;
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getIdTipoComprob() {
        return idTipoComprob;
    }

    public void setIdTipoComprob(int idTipoComprob) {
        this.idTipoComprob = idTipoComprob;
    }

    public long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(long montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSitioCliente() {
        return idSitioCliente;
    }

    public void setIdSitioCliente(int idSitioCliente) {
        this.idSitioCliente = idSitioCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNroReferencia() {
        return nroReferencia;
    }

    public void setNroReferencia(String nroReferencia) {
        this.nroReferencia = nroReferencia;
    }

    public double getMontoExento() {
        return montoExento;
    }

    public void setMontoExento(double montoExento) {
        this.montoExento = montoExento;
    }

    public double getMontoGravado() {
        return montoGravado;
    }

    public void setMontoGravado(double montoGravado) {
        this.montoGravado = montoGravado;
    }

    public double getMontoGravado5() {
        return montoGravado5;
    }

    public void setMontoGravado5(double montoGravado5) {
        this.montoGravado5 = montoGravado5;
    }

    public double getMontoGravado10() {
        return montoGravado10;
    }

    public void setMontoGravado10(double montoGravado10) {
        this.montoGravado10 = montoGravado10;
    }

    public double getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public double getMontoImpuesto() {
        return montoImpuesto;
    }

    public void setMontoImpuesto(double montoImpuesto) {
        this.montoImpuesto = montoImpuesto;
    }

    public double getMontoImpuesto5() {
        return montoImpuesto5;
    }

    public void setMontoImpuesto5(double montoImpuesto5) {
        this.montoImpuesto5 = montoImpuesto5;
    }

    public double getMontoImpuesto10() {
        return montoImpuesto10;
    }

    public void setMontoImpuesto10(double montoImpuesto10) {
        this.montoImpuesto10 = montoImpuesto10;
    }

    public double getMontoRetencion() {
        return montoRetencion;
    }

    public void setMontoRetencion(double montoRetencion) {
        this.montoRetencion = montoRetencion;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public int getDiasPorPago() {
        return diasPorPago;
    }

    public void setDiasPorPago(int diasPorPago) {
        this.diasPorPago = diasPorPago;
    }

    public int getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(int tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public int getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(int porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public int getCantidadPagos() {
        return cantidadPagos;
    }

    public void setCantidadPagos(int cantidadPagos) {
        this.cantidadPagos = cantidadPagos;
    }

    public String getUsrEmision() {
        return usrEmision;
    }

    public void setUsrEmision(String usrEmision) {
        this.usrEmision = usrEmision;
    }

    public String getUsrAnulacion() {
        return usrAnulacion;
    }

    public void setUsrAnulacion(String usrAnulacion) {
        this.usrAnulacion = usrAnulacion;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getVrTipoDocumento() {
        return vrTipoDocumento;
    }

    public void setVrTipoDocumento(String vrTipoDocumento) {
        this.vrTipoDocumento = vrTipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public double getMontoUtilizado() {
        return montoUtilizado;
    }

    public void setMontoUtilizado(double montoUtilizado) {
        this.montoUtilizado = montoUtilizado;
    }

    public double getMontoEntInic() {
        return montoEntInic;
    }

    public void setMontoEntInic(double montoEntInic) {
        this.montoEntInic = montoEntInic;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipoCompra() {
        return idTipoCompra;
    }

    public void setIdTipoCompra(int idTipoCompra) {
        this.idTipoCompra = idTipoCompra;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getNroTicket() {
        return nroTicket;
    }

    public void setNroTicket(int nroTicket) {
        this.nroTicket = nroTicket;
    }

    public String getNroRegImpos() {
        return nroRegImpos;
    }

    public void setNroRegImpos(String nroRegImpos) {
        this.nroRegImpos = nroRegImpos;
    }

    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    public int getPorcEntinic() {
        return porcEntinic;
    }

    public void setPorcEntinic(int porcEntinic) {
        this.porcEntinic = porcEntinic;
    }

    public int getIdRegistroCtrl() {
        return idRegistroCtrl;
    }

    public void setIdRegistroCtrl(int idRegistroCtrl) {
        this.idRegistroCtrl = idRegistroCtrl;
    }

    public int getIdPuntoEmision() {
        return idPuntoEmision;
    }

    public void setIdPuntoEmision(int idPuntoEmision) {
        this.idPuntoEmision = idPuntoEmision;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getAtributo1() {
        return atributo1;
    }

    public void setAtributo1(String atributo1) {
        this.atributo1 = atributo1;
    }

    public String getAtributo2() {
        return atributo2;
    }

    public void setAtributo2(String atributo2) {
        this.atributo2 = atributo2;
    }

    public String getAtributo3() {
        return atributo3;
    }

    public void setAtributo3(String atributo3) {
        this.atributo3 = atributo3;
    }

    public String getAtributo4() {
        return atributo4;
    }

    public void setAtributo4(String atributo4) {
        this.atributo4 = atributo4;
    }

    public String getAtributo5() {
        return atributo5;
    }

    public void setAtributo5(String atributo5) {
        this.atributo5 = atributo5;
    }

    public long getIdMovimStock() {
        return idMovimStock;
    }

    public void setIdMovimStock(long idMovimStock) {
        this.idMovimStock = idMovimStock;
    }

    public int getIdMoviStkRev() {
        return idMoviStkRev;
    }

    public void setIdMoviStkRev(int idMoviStkRev) {
        this.idMoviStkRev = idMoviStkRev;
    }

    public int getIdProcesoVenta() {
        return idProcesoVenta;
    }

    public void setIdProcesoVenta(int idProcesoVenta) {
        this.idProcesoVenta = idProcesoVenta;
    }

    public int getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(int idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdPAciente() {
        return idPAciente;
    }

    public void setIdPAciente(int idPAciente) {
        this.idPAciente = idPAciente;
    }

    public int getIdEmpSeguro() {
        return idEmpSeguro;
    }

    public void setIdEmpSeguro(int idEmpSeguro) {
        this.idEmpSeguro = idEmpSeguro;
    }

    public int getIdTipoCtaRef() {
        return idTipoCtaRef;
    }

    public void setIdTipoCtaRef(int idTipoCtaRef) {
        this.idTipoCtaRef = idTipoCtaRef;
    }

    public String getNroCtaRef() {
        return nroCtaRef;
    }

    public void setNroCtaRef(String nroCtaRef) {
        this.nroCtaRef = nroCtaRef;
    }

    public int getTasaIntMora() {
        return tasaIntMora;
    }

    public void setTasaIntMora(int tasaIntMora) {
        this.tasaIntMora = tasaIntMora;
    }

    public int getIdCompRetencion() {
        return idCompRetencion;
    }

    public void setIdCompRetencion(int idCompRetencion) {
        this.idCompRetencion = idCompRetencion;
    }

    public long getIdControlCaja() {
        return idControlCaja;
    }

    public void setIdControlCaja(long idControlCaja) {
        this.idControlCaja = idControlCaja;
    }

    public int getIdTipoRetencion() {
        return idTipoRetencion;
    }

    public void setIdTipoRetencion(int idTipoRetencion) {
        this.idTipoRetencion = idTipoRetencion;
    }

    public int getTasaRetencion() {
        return tasaRetencion;
    }

    public void setTasaRetencion(int tasaRetencion) {
        this.tasaRetencion = tasaRetencion;
    }

    public int getIdSuprCancel() {
        return idSuprCancel;
    }

    public void setIdSuprCancel(int idSuprCancel) {
        this.idSuprCancel = idSuprCancel;
    }

    public int getIdListaPecios() {
        return idListaPecios;
    }

    public void setIdListaPecios(int idListaPecios) {
        this.idListaPecios = idListaPecios;
    }

    public String getTansferidoSc() {
        return tansferidoSc;
    }

    public void setTansferidoSc(String tansferidoSc) {
        this.tansferidoSc = tansferidoSc;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdComprobOrigen() {
        return idComprobOrigen;
    }

    public void setIdComprobOrigen(int idComprobOrigen) {
        this.idComprobOrigen = idComprobOrigen;
    }

    public int getIdPlanArqueos() {
        return idPlanArqueos;
    }

    public void setIdPlanArqueos(int idPlanArqueos) {
        this.idPlanArqueos = idPlanArqueos;
    }

    public int getCantCupones() {
        return cantCupones;
    }

    public void setCantCupones(int cantCupones) {
        this.cantCupones = cantCupones;
    }

    public int getCantPuntos() {
        return cantPuntos;
    }

    public void setCantPuntos(int cantPuntos) {
        this.cantPuntos = cantPuntos;
    }

    public int getIdCtaContable() {
        return idCtaContable;
    }

    public void setIdCtaContable(int idCtaContable) {
        this.idCtaContable = idCtaContable;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public long getMontoTotalReal() {
        return montoTotalReal;
    }

    public void setMontoTotalReal(long montoTotalReal) {
        this.montoTotalReal = montoTotalReal;
    }

    public long getMontoRedondeo() {
        return montoRedondeo;
    }

    public void setMontoRedondeo(long montoRedondeo) {
        this.montoRedondeo = montoRedondeo;
    }

    public long getMontoTotalMl() {
        return montoTotalMl;
    }

    public void setMontoTotalMl(long montoTotalMl) {
        this.montoTotalMl = montoTotalMl;
    }

    public long getMontoAplicaciones() {
        return montoAplicaciones;
    }

    public void setMontoAplicaciones(long montoAplicaciones) {
        this.montoAplicaciones = montoAplicaciones;
    }

    public Timestamp getfecha() {
        return fecha;
    }

    public void setfecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getfechaVencimiento() {
        return fechaVencimiento;
    }

    public void setfechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Timestamp getFecEmision() {
        return fecEmision;
    }

    public void setFecEmision(Timestamp fecEmision) {
        this.fecEmision = fecEmision;
    }

    public Timestamp getFecAnulacion() {
        return fecAnulacion;
    }

    public void setFecAnulacion(Timestamp fecAnulacion) {
        this.fecAnulacion = fecAnulacion;
    }

    public Timestamp getfechaContable() {
        return fechaContable;
    }

    public void setfechaContable(Timestamp fechaContable) {
        this.fechaContable = fechaContable;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public Timestamp getfechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setfechaUltimoPago(Timestamp fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public Timestamp getFecTransferSc() {
        return fecTransferSc;
    }

    public void setFecTransferSc(Timestamp fecTransferSc) {
        this.fecTransferSc = fecTransferSc;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getfechaEnvSrv() {
        return fechaEnvSrv;
    }

    public void setfechaEnvSrv(Timestamp fechaEnvSrv) {
        this.fechaEnvSrv = fechaEnvSrv;
    }

    public String getFefecha() {
        return fefecha;
    }

    public void setFefecha(String fefecha) {
        this.fefecha = fefecha;
    }

    public String getFeVencimiento() {
        return feVencimiento;
    }

    public void setFeVencimiento(String feVencimiento) {
        this.feVencimiento = feVencimiento;
    }

    public String getFeEmision() {
        return feEmision;
    }

    public void setFeEmision(String feEmision) {
        this.feEmision = feEmision;
    }

    public String getFeAnulacion() {
        return feAnulacion;
    }

    public void setFeAnulacion(String feAnulacion) {
        this.feAnulacion = feAnulacion;
    }

    public String getFehaContable() {
        return fehaContable;
    }

    public void setFehaContable(String fehaContable) {
        this.fehaContable = fehaContable;
    }

    public String getFeMod() {
        return feMod;
    }

    public void setFeMod(String feMod) {
        this.feMod = feMod;
    }

    public String getFeUltimoPago() {
        return feUltimoPago;
    }

    public void setFeUltimoPago(String feUltimoPago) {
        this.feUltimoPago = feUltimoPago;
    }

    public String getFeTransferSc() {
        return feTransferSc;
    }

    public void setFeTransferSc(String feTransferSc) {
        this.feTransferSc = feTransferSc;
    }

    public String getFeCre() {
        return feCre;
    }

    public void setFeCre(String feCre) {
        this.feCre = feCre;
    }

    public String getFeEnvSrv() {
        return feEnvSrv;
    }

    public void setFeEnvSrv(String feEnvSrv) {
        this.feEnvSrv = feEnvSrv;
    }

    public int getDescuento347() {
        return descuento_347;
    }

    public void setDescuento347(int descuento_347) {
        this.descuento_347 = descuento_347;
    }

}
