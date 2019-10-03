
package Modelos;

import java.sql.Timestamp;

public class StkMovimStock {
    long identificador;
    int idEmpresa,idUnidad;
    String vrSituacion;
    int idSitio;
    String usuario,usrCre;
    String numero,vrOrigenTrans;
    int idTransaccion;
    long idComprobante;
    String nroComprobante,idCtaRef,nroReferencia, reversado;
    int idStock,idAlmacen,idProducto,idUndMedida;
    double existeStock;
    
    Timestamp fecha, fechaCre;
    String fefecha, feCre;
    
    public StkMovimStock() {
    }

    public StkMovimStock(int identificador, int idEmpresa, int idUnidad, String vrSituacion, int idSitio, String usuario, String usrCre, String numero, String vrOrigenTrans, int idTransaccion, int idComprobante, String nroComprobante, String idCtaRef, String nroReferencia, String reversado, int idStock, int idAlmacen, int idProducto, int idUndMedida, double existeStock, Timestamp fecha, Timestamp fechaCre, String fefecha, String feCre) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.vrSituacion = vrSituacion;
        this.idSitio = idSitio;
        this.usuario = usuario;
        this.usrCre = usrCre;
        this.numero = numero;
        this.vrOrigenTrans = vrOrigenTrans;
        this.idTransaccion = idTransaccion;
        this.idComprobante = idComprobante;
        this.nroComprobante = nroComprobante;
        this.idCtaRef = idCtaRef;
        this.nroReferencia = nroReferencia;
        this.reversado = reversado;
        this.idStock = idStock;
        this.idAlmacen = idAlmacen;
        this.idProducto = idProducto;
        this.idUndMedida = idUndMedida;
        this.existeStock = existeStock;
        this.fecha = fecha;
        this.fechaCre = fechaCre;
        this.fefecha = fefecha;
        this.feCre = feCre;
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

    public String getVrSituacion() {
        return vrSituacion;
    }

    public void setVrSituacion(String vrSituacion) {
        this.vrSituacion = vrSituacion;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVrOrigenTrans() {
        return vrOrigenTrans;
    }

    public void setVrOrigenTrans(String vrOrigenTrans) {
        this.vrOrigenTrans = vrOrigenTrans;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(String nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public String getIdCtaRef() {
        return idCtaRef;
    }

    public void setIdCtaRef(String idCtaRef) {
        this.idCtaRef = idCtaRef;
    }

    public String getNroReferencia() {
        return nroReferencia;
    }

    public void setNroReferencia(String nroReferencia) {
        this.nroReferencia = nroReferencia;
    }

    public String getReversado() {
        return reversado;
    }

    public void setReversado(String reversado) {
        this.reversado = reversado;
    }

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUndMedida() {
        return idUndMedida;
    }

    public void setIdUndMedida(int idUndMedida) {
        this.idUndMedida = idUndMedida;
    }

    public double getexisteStock() {
        return existeStock;
    }

    public void setexisteStock(double existeStock) {
        this.existeStock = existeStock;
    }

    public Timestamp getfecha() {
        return fecha;
    }

    public void setfecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getfechaCre() {
        return fechaCre;
    }

    public void setfechaCre(Timestamp fechaCre) {
        this.fechaCre = fechaCre;
    }

    public String getFefecha() {
        return fefecha;
    }

    public void setFefecha(String fefecha) {
        this.fefecha = fefecha;
    }

    public String getFeCre() {
        return feCre;
    }

    public void setFeCre(String feCre) {
        this.feCre = feCre;
    }

   
}
