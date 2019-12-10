package Modelos;

import java.sql.Timestamp;

public class TcItemsCobro {

    long identificador, idComprobante;
    int idEmpresa, idUnidad;
    long idCobro;
    int idFormaPago, idMoneda;
    String usrCre, fecCre, usrMod, fecMod;
    long importeConfirm, importeOrig_conf, importeMl, importeAsignado, importe, importeOrigen;
    int tipoCambio, idTipoDocumento;
    String nroDocumento;
    long importeDocumento, importeInicial, importeDescuento;
    String fecDocumento, fecVcto;
    int idEntidad, idProcesadora, idTipoTarjeta, idMarcaTarjeta;
    String nroTarjeta, nroCuenta, nroVoucher, codAutorizacion, nombreConsignado;
    int idComprobPago, idDocumPago;
    String comentarios;
    int numeroItem, cantidadCuotas, idEmpleado, porcDescuento;
    String codigoReferencia, fecAplicacion;
    int tipoCambioMonCobro, tipoCambioRevaluo;

    Timestamp feCre, feMod, feDocumento, feVto, feAplicacion;

    public TcItemsCobro() {
    }

    public TcItemsCobro(int identificador, int idEmpresa, int idUnidad, int idCobro, int idComprobante, int idFormaPago, int idMoneda, String usrCre, String fecCre, String usrMod, String fecMod, long importeConfirm, long importeOrig_conf, long importeMl, long importeAsignado, long importe, long importeOrigen, int tipoCambio, int idTipoDocumento, String nroDocumento, long importeDocumento, long importeInicial, long importeDescuento, String fecDocumento, String fecVcto, int idEntidad, int idProcesadora, int idTipoTarjeta, int idMarcaTarjeta, String nroTarjeta, String nroCuenta, String nroVoucher, String codAutorizacion, String nombreConsignado, int idComprobPago, int idDocumPago, String comentarios, int numeroItem, int cantidadCuotas, int idEmpleado, int porcDescuento, String codigoReferencia, String fecAplicacion, int tipoCambioMonCobro, int tipoCambioRevaluo, Timestamp feCre, Timestamp feMod, Timestamp feDocumento, Timestamp feVto, Timestamp feAplicacion) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idCobro = idCobro;
        this.idComprobante = idComprobante;
        this.idFormaPago = idFormaPago;
        this.idMoneda = idMoneda;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.importeConfirm = importeConfirm;
        this.importeOrig_conf = importeOrig_conf;
        this.importeMl = importeMl;
        this.importeAsignado = importeAsignado;
        this.importe = importe;
        this.importeOrigen = importeOrigen;
        this.tipoCambio = tipoCambio;
        this.idTipoDocumento = idTipoDocumento;
        this.nroDocumento = nroDocumento;
        this.importeDocumento = importeDocumento;
        this.importeInicial = importeInicial;
        this.importeDescuento = importeDescuento;
        this.fecDocumento = fecDocumento;
        this.fecVcto = fecVcto;
        this.idEntidad = idEntidad;
        this.idProcesadora = idProcesadora;
        this.idTipoTarjeta = idTipoTarjeta;
        this.idMarcaTarjeta = idMarcaTarjeta;
        this.nroTarjeta = nroTarjeta;
        this.nroCuenta = nroCuenta;
        this.nroVoucher = nroVoucher;
        this.codAutorizacion = codAutorizacion;
        this.nombreConsignado = nombreConsignado;
        this.idComprobPago = idComprobPago;
        this.idDocumPago = idDocumPago;
        this.comentarios = comentarios;
        this.numeroItem = numeroItem;
        this.cantidadCuotas = cantidadCuotas;
        this.idEmpleado = idEmpleado;
        this.porcDescuento = porcDescuento;
        this.codigoReferencia = codigoReferencia;
        this.fecAplicacion = fecAplicacion;
        this.tipoCambioMonCobro = tipoCambioMonCobro;
        this.tipoCambioRevaluo = tipoCambioRevaluo;
        this.feCre = feCre;
        this.feMod = feMod;
        this.feDocumento = feDocumento;
        this.feVto = feVto;
        this.feAplicacion = feAplicacion;
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
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

    public long getIdCobro() {
        return idCobro;
    }

    public void setIdCobro(long idCobro) {
        this.idCobro = idCobro;
    }

    public long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getFecCre() {
        return fecCre;
    }

    public void setFecCre(String fecCre) {
        this.fecCre = fecCre;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getFecMod() {
        return fecMod;
    }

    public void setFecMod(String fecMod) {
        this.fecMod = fecMod;
    }

    public long getImporteConfirm() {
        return importeConfirm;
    }

    public void setImporteConfirm(long importeConfirm) {
        this.importeConfirm = importeConfirm;
    }

    public long getImporteOrig_conf() {
        return importeOrig_conf;
    }

    public void setImporteOrig_conf(long importeOrig_conf) {
        this.importeOrig_conf = importeOrig_conf;
    }

    public long getImporteMl() {
        return importeMl;
    }

    public void setImporteMl(long importeMl) {
        this.importeMl = importeMl;
    }

    public long getImporteAsignado() {
        return importeAsignado;
    }

    public void setImporteAsignado(long importeAsignado) {
        this.importeAsignado = importeAsignado;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public long getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(long importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public int getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(int tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public long getImporteDocumento() {
        return importeDocumento;
    }

    public void setImporteDocumento(long importeDocumento) {
        this.importeDocumento = importeDocumento;
    }

    public long getImporteInicial() {
        return importeInicial;
    }

    public void setImporteInicial(long importeInicial) {
        this.importeInicial = importeInicial;
    }

    public long getImporteDescuento() {
        return importeDescuento;
    }

    public void setImporteDescuento(long importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public String getFecDocumento() {
        return fecDocumento;
    }

    public void setFecDocumento(String fecDocumento) {
        this.fecDocumento = fecDocumento;
    }

    public String getFecVcto() {
        return fecVcto;
    }

    public void setFecVcto(String fecVcto) {
        this.fecVcto = fecVcto;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public int getIdProcesadora() {
        return idProcesadora;
    }

    public void setIdProcesadora(int idProcesadora) {
        this.idProcesadora = idProcesadora;
    }

    public int getIdTipoTarjeta() {
        return idTipoTarjeta;
    }

    public void setIdTipoTarjeta(int idTipoTarjeta) {
        this.idTipoTarjeta = idTipoTarjeta;
    }

    public int getIdMarcaTarjeta() {
        return idMarcaTarjeta;
    }

    public void setIdMarcaTarjeta(int idMarcaTarjeta) {
        this.idMarcaTarjeta = idMarcaTarjeta;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getNroVoucher() {
        return nroVoucher;
    }

    public void setNroVoucher(String nroVoucher) {
        this.nroVoucher = nroVoucher;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public String getNombreConsignado() {
        return nombreConsignado;
    }

    public void setNombreConsignado(String nombreConsignado) {
        this.nombreConsignado = nombreConsignado;
    }

    public int getIdComprobPago() {
        return idComprobPago;
    }

    public void setIdComprobPago(int idComprobPago) {
        this.idComprobPago = idComprobPago;
    }

    public int getIdDocumPago() {
        return idDocumPago;
    }

    public void setIdDocumPago(int idDocumPago) {
        this.idDocumPago = idDocumPago;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(int numeroItem) {
        this.numeroItem = numeroItem;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(int porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public String getFecAplicacion() {
        return fecAplicacion;
    }

    public void setFecAplicacion(String fecAplicacion) {
        this.fecAplicacion = fecAplicacion;
    }

    public int getTipoCambioMonCobro() {
        return tipoCambioMonCobro;
    }

    public void setTipoCambioMonCobro(int tipoCambioMonCobro) {
        this.tipoCambioMonCobro = tipoCambioMonCobro;
    }

    public int getTipoCambioRevaluo() {
        return tipoCambioRevaluo;
    }

    public void setTipoCambioRevaluo(int tipoCambioRevaluo) {
        this.tipoCambioRevaluo = tipoCambioRevaluo;
    }

    public Timestamp getFeCre() {
        return feCre;
    }

    public void setFeCre(Timestamp feCre) {
        this.feCre = feCre;
    }

    public Timestamp getFeMod() {
        return feMod;
    }

    public void setFeMod(Timestamp feMod) {
        this.feMod = feMod;
    }

    public Timestamp getFeDocumento() {
        return feDocumento;
    }

    public void setFeDocumento(Timestamp feDocumento) {
        this.feDocumento = feDocumento;
    }

    public Timestamp getFeVto() {
        return feVto;
    }

    public void setFeVto(Timestamp feVto) {
        this.feVto = feVto;
    }

    public Timestamp getFeAplicacion() {
        return feAplicacion;
    }

    public void setFeAplicacion(Timestamp feAplicacion) {
        this.feAplicacion = feAplicacion;
    }

}
