package Modelos;

import java.sql.Timestamp;

public class TcVouchers {

    long identificador;
    int idEmpresa, idUnidad, idSitio;
    String numero;
    int idProcesadora, idCaja;
    Timestamp fecha;
    long importe, importePagado;
    String vrTipoCarga, vrSituacion, usrCre;
    Timestamp fecCre;
    String usrMod;
    Timestamp fecMod;
    String nroTarjeta;
    Timestamp vctoTarjeta;
    String codigoAutorizacion, nroLote, nroCargo;
    String nroDocCliente, nombreCliente;
    Timestamp fecha_pago;
    String usrPago, numeroTicket;
    int idCajero, idTurno;
    long idControlCaja;
    String vrTipoTarjeta;
    long idCobro;
    int idMoneda, idTipoTarjeta, cantidadCuotas;
    int idPlanillaVoucher, idComercion, idEntidad;

    public TcVouchers() {
    }

    public TcVouchers(int identificador, int idEmpresa, int idUnidad, int idSitio, String numero, int idProcesadora, int idCaja, Timestamp fecha, long importe, long importePagado, String vrTipoCarga, String vrSituacion, String usrCre, Timestamp fecCre, String usrMod, Timestamp fecMod, String nroTarjeta, Timestamp vctoTarjeta, String codigoAutorizacion, String nroLote, String nroCargo, String nroDocCliente, String nombreCliente, Timestamp fecha_pago, String usrPago, String numeroTicket, int idCajero, int idTurno, int idControlCaja, String vrTipoTarjeta, int idCobro, int idMoneda, int idTipoTarjeta, int cantidadCuotas, int idPlanillaVoucher, int idComercion, int idEntidad) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idSitio = idSitio;
        this.numero = numero;
        this.idProcesadora = idProcesadora;
        this.idCaja = idCaja;
        this.fecha = fecha;
        this.importe = importe;
        this.importePagado = importePagado;
        this.vrTipoCarga = vrTipoCarga;
        this.vrSituacion = vrSituacion;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.nroTarjeta = nroTarjeta;
        this.vctoTarjeta = vctoTarjeta;
        this.codigoAutorizacion = codigoAutorizacion;
        this.nroLote = nroLote;
        this.nroCargo = nroCargo;
        this.nroDocCliente = nroDocCliente;
        this.nombreCliente = nombreCliente;
        this.fecha_pago = fecha_pago;
        this.usrPago = usrPago;
        this.numeroTicket = numeroTicket;
        this.idCajero = idCajero;
        this.idTurno = idTurno;
        this.idControlCaja = idControlCaja;
        this.vrTipoTarjeta = vrTipoTarjeta;
        this.idCobro = idCobro;
        this.idMoneda = idMoneda;
        this.idTipoTarjeta = idTipoTarjeta;
        this.cantidadCuotas = cantidadCuotas;
        this.idPlanillaVoucher = idPlanillaVoucher;
        this.idComercion = idComercion;
        this.idEntidad = idEntidad;
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

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getIdProcesadora() {
        return idProcesadora;
    }

    public void setIdProcesadora(int idProcesadora) {
        this.idProcesadora = idProcesadora;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public Timestamp getfecha() {
        return fecha;
    }

    public void setfecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public long getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(long importePagado) {
        this.importePagado = importePagado;
    }

    public String getVrTipoCarga() {
        return vrTipoCarga;
    }

    public void setVrTipoCarga(String vrTipoCarga) {
        this.vrTipoCarga = vrTipoCarga;
    }

    public String getVrSituacion() {
        return vrSituacion;
    }

    public void setVrSituacion(String vrSituacion) {
        this.vrSituacion = vrSituacion;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public Timestamp getVctoTarjeta() {
        return vctoTarjeta;
    }

    public void setVctoTarjeta(Timestamp vctoTarjeta) {
        this.vctoTarjeta = vctoTarjeta;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public String getNroLote() {
        return nroLote;
    }

    public void setNroLote(String nroLote) {
        this.nroLote = nroLote;
    }

    public String getNroCargo() {
        return nroCargo;
    }

    public void setNroCargo(String nroCargo) {
        this.nroCargo = nroCargo;
    }

    public String getNroDocCliente() {
        return nroDocCliente;
    }

    public void setNroDocCliente(String nroDocCliente) {
        this.nroDocCliente = nroDocCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Timestamp getfecha_pago() {
        return fecha_pago;
    }

    public void setfecha_pago(Timestamp fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getUsrPago() {
        return usrPago;
    }

    public void setUsrPago(String usrPago) {
        this.usrPago = usrPago;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public long getIdControlCaja() {
        return idControlCaja;
    }

    public void setIdControlCaja(long idControlCaja) {
        this.idControlCaja = idControlCaja;
    }

    public String getVrTipoTarjeta() {
        return vrTipoTarjeta;
    }

    public void setVrTipoTarjeta(String vrTipoTarjeta) {
        this.vrTipoTarjeta = vrTipoTarjeta;
    }

    public long getIdCobro() {
        return idCobro;
    }

    public void setIdCobro(long idCobro) {
        this.idCobro = idCobro;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getIdTipoTarjeta() {
        return idTipoTarjeta;
    }

    public void setIdTipoTarjeta(int idTipoTarjeta) {
        this.idTipoTarjeta = idTipoTarjeta;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public int getIdPlanillaVoucher() {
        return idPlanillaVoucher;
    }

    public void setIdPlanillaVoucher(int idPlanillaVoucher) {
        this.idPlanillaVoucher = idPlanillaVoucher;
    }

    public int getIdComercion() {
        return idComercion;
    }

    public void setIdComercion(int idComercion) {
        this.idComercion = idComercion;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

}
