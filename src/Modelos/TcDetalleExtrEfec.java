package Modelos;

import java.sql.Timestamp;

public class TcDetalleExtrEfec {
    long identificador;
    int idEmpresa, idUnidad, idCaja;
    long idControl;
    int idCajero;
    String numero, fecfecha, usrCre, fecCre, usrMod, fecMod;
    String descripcion, nroDocBeneficiario, nombreBeneficiario, nroRefPago, vrTipoRetiro;
    int idRefPago;
    
    //TC_DETALLE_EXTR_EFEC
    long idExtraccion;
    int idDenominacion, cantidad;
    long importe;
    Timestamp fefecha, feCre, feMod;
    
    public TcDetalleExtrEfec() {
    }

    public TcDetalleExtrEfec(int identificador, int idEmpresa, int idUnidad, int idCaja, int idControl, int idCajero, String numero, String fecfecha, String usrCre, String fecCre, String usrMod, String fecMod, String descripcion, String nroDocBeneficiario, String nombreBeneficiario, String nroRefPago, String vrTipoRetiro, int idRefPago, int idExtraccion, int idDenominacion, int cantidad, long importe, Timestamp fefecha, Timestamp feCre, Timestamp feMod) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idCaja = idCaja;
        this.idControl = idControl;
        this.idCajero = idCajero;
        this.numero = numero;
        this.fecfecha = fecfecha;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.descripcion = descripcion;
        this.nroDocBeneficiario = nroDocBeneficiario;
        this.nombreBeneficiario = nombreBeneficiario;
        this.nroRefPago = nroRefPago;
        this.vrTipoRetiro = vrTipoRetiro;
        this.idRefPago = idRefPago;
        this.idExtraccion = idExtraccion;
        this.idDenominacion = idDenominacion;
        this.cantidad = cantidad;
        this.importe = importe;
        this.fefecha = fefecha;
        this.feCre = feCre;
        this.feMod = feMod;
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

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public long getIdControl() {
        return idControl;
    }

    public void setIdControl(long idControl) {
        this.idControl = idControl;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecfecha() {
        return fecfecha;
    }

    public void setFecfecha(String fecfecha) {
        this.fecfecha = fecfecha;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNroDocBeneficiario() {
        return nroDocBeneficiario;
    }

    public void setNroDocBeneficiario(String nroDocBeneficiario) {
        this.nroDocBeneficiario = nroDocBeneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getNroRefPago() {
        return nroRefPago;
    }

    public void setNroRefPago(String nroRefPago) {
        this.nroRefPago = nroRefPago;
    }

    public String getVrTipoRetiro() {
        return vrTipoRetiro;
    }

    public void setVrTipoRetiro(String vrTipoRetiro) {
        this.vrTipoRetiro = vrTipoRetiro;
    }

    public int getIdRefPago() {
        return idRefPago;
    }

    public void setIdRefPago(int idRefPago) {
        this.idRefPago = idRefPago;
    }

    public long getIdExtraccion() {
        return idExtraccion;
    }

    public void setIdExtraccion(long idExtraccion) {
        this.idExtraccion = idExtraccion;
    }

    public int getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public Timestamp getFefecha() {
        return fefecha;
    }

    public void setFefecha(Timestamp fefecha) {
        this.fefecha = fefecha;
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

    
}
