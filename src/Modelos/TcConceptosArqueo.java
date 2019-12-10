package Modelos;

import java.sql.Timestamp;

public class TcConceptosArqueo {
    int identificador;
    String descripcion, vrTipoConcepto, vrUso, vrSigno, usrCre;
    Timestamp fecCre;
    String abreviatura, numero;
    String idFormaPago, idTransaccion;
    String requiereAut, codigoPos, activo;
    int idTipoCobro, idEmpresa, idUnidad;
    String valorDeposit, codigoUnificacion, itemSistema;
    String codigoReporte, vrTipoImporteCobro;
    int idCtaContable;

    public TcConceptosArqueo() {
    }

    public TcConceptosArqueo(int identificador, String descripcion, String vrTipoConcepto, String vrUso, String vrSigno, String usrCre, Timestamp fecCre, String abreviatura, String numero, String idFormaPago, String idTransaccion, String requiereAut, String codigoPos, String activo, int idTipoCobro, int idEmpresa, int idUnidad, String valorDeposit, String codigoUnificacion, String itemSistema, String codigoReporte, String vrTipoImporteCobro, int idCtaContable) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.vrTipoConcepto = vrTipoConcepto;
        this.vrUso = vrUso;
        this.vrSigno = vrSigno;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.abreviatura = abreviatura;
        this.numero = numero;
        this.idFormaPago = idFormaPago;
        this.idTransaccion = idTransaccion;
        this.requiereAut = requiereAut;
        this.codigoPos = codigoPos;
        this.activo = activo;
        this.idTipoCobro = idTipoCobro;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.valorDeposit = valorDeposit;
        this.codigoUnificacion = codigoUnificacion;
        this.itemSistema = itemSistema;
        this.codigoReporte = codigoReporte;
        this.vrTipoImporteCobro = vrTipoImporteCobro;
        this.idCtaContable = idCtaContable;
    }

    
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVrTipoConcepto() {
        return vrTipoConcepto;
    }

    public void setVrTipoConcepto(String vrTipoConcepto) {
        this.vrTipoConcepto = vrTipoConcepto;
    }

    public String getVrUso() {
        return vrUso;
    }

    public void setVrUso(String vrUso) {
        this.vrUso = vrUso;
    }

    public String getVrSigno() {
        return vrSigno;
    }

    public void setVrSigno(String vrSigno) {
        this.vrSigno = vrSigno;
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(String idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getRequiereAut() {
        return requiereAut;
    }

    public void setRequiereAut(String requiereAut) {
        this.requiereAut = requiereAut;
    }

    public String getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(String codigoPos) {
        this.codigoPos = codigoPos;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public int getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(int idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
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

    public String getValorDeposit() {
        return valorDeposit;
    }

    public void setValorDeposit(String valorDeposit) {
        this.valorDeposit = valorDeposit;
    }

    public String getCodigoUnificacion() {
        return codigoUnificacion;
    }

    public void setCodigoUnificacion(String codigoUnificacion) {
        this.codigoUnificacion = codigoUnificacion;
    }

    public String getItemSistema() {
        return itemSistema;
    }

    public void setItemSistema(String itemSistema) {
        this.itemSistema = itemSistema;
    }

    public String getCodigoReporte() {
        return codigoReporte;
    }

    public void setCodigoReporte(String codigoReporte) {
        this.codigoReporte = codigoReporte;
    }

    public String getVrTipoImporteCobro() {
        return vrTipoImporteCobro;
    }

    public void setVrTipoImporteCobro(String vrTipoImporteCobro) {
        this.vrTipoImporteCobro = vrTipoImporteCobro;
    }

    public int getIdCtaContable() {
        return idCtaContable;
    }

    public void setIdCtaContable(int idCtaContable) {
        this.idCtaContable = idCtaContable;
    }
    
    
    
}
