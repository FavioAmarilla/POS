package Modelos;

import java.sql.Timestamp;

public class FndFormasPago {
    int identificador;
    String descripcion, usrCre;
    Timestamp fecCre;
    String abreviatura, numero;
    int cantidadDias, idTipoDocum, idTipoComp;
    String generarVuelto;
    int idCtaContableCp, idCtaContableVt;
    String pagoEfectivo, pagoCheque, vrClase, codigoPos;
    int idMoneda;
    String requiereEntBco, requiereEntOc, requiereEmpl;
    int montoMaxVuelto, montoMinVuelto;
    String activo;
    int idEmpresa, idUnidad;
    String cancelarDescProm, codigoUnificacion, itemSistema;
    int montoMaxCobros, montoMinCobros;
    String codigoSistema, esPrincipal;

    public FndFormasPago() {
    }

    public FndFormasPago(int identificador, String descripcion, String usrCre, Timestamp fecCre, String abreviatura, String numero, int cantidadDias, int idTipoDocum, int idTipoComp, String generarVuelto, int idCtaContableCp, int idCtaContableVt, String pagoEfectivo, String pagoCheque, String vrClase, String codigoPos, int idMoneda, String requiereEntBco, String requiereEntOc, String requiereEmpl, int montoMaxVuelto, int montoMinVuelto, String activo, int idEmpresa, int idUnidad, String cancelarDescProm, String codigoUnificacion, String itemSistema, int montoMaxCobros, int montoMinCobros, String codigoSistema, String esPrincipal) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.abreviatura = abreviatura;
        this.numero = numero;
        this.cantidadDias = cantidadDias;
        this.idTipoDocum = idTipoDocum;
        this.idTipoComp = idTipoComp;
        this.generarVuelto = generarVuelto;
        this.idCtaContableCp = idCtaContableCp;
        this.idCtaContableVt = idCtaContableVt;
        this.pagoEfectivo = pagoEfectivo;
        this.pagoCheque = pagoCheque;
        this.vrClase = vrClase;
        this.codigoPos = codigoPos;
        this.idMoneda = idMoneda;
        this.requiereEntBco = requiereEntBco;
        this.requiereEntOc = requiereEntOc;
        this.requiereEmpl = requiereEmpl;
        this.montoMaxVuelto = montoMaxVuelto;
        this.montoMinVuelto = montoMinVuelto;
        this.activo = activo;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.cancelarDescProm = cancelarDescProm;
        this.codigoUnificacion = codigoUnificacion;
        this.itemSistema = itemSistema;
        this.montoMaxCobros = montoMaxCobros;
        this.montoMinCobros = montoMinCobros;
        this.codigoSistema = codigoSistema;
        this.esPrincipal = esPrincipal;
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

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public int getIdTipoDocum() {
        return idTipoDocum;
    }

    public void setIdTipoDocum(int idTipoDocum) {
        this.idTipoDocum = idTipoDocum;
    }

    public int getIdTipoComp() {
        return idTipoComp;
    }

    public void setIdTipoComp(int idTipoComp) {
        this.idTipoComp = idTipoComp;
    }

    public String getGenerarVuelto() {
        return generarVuelto;
    }

    public void setGenerarVuelto(String generarVuelto) {
        this.generarVuelto = generarVuelto;
    }

    public int getIdCtaContableCp() {
        return idCtaContableCp;
    }

    public void setIdCtaContableCp(int idCtaContableCp) {
        this.idCtaContableCp = idCtaContableCp;
    }

    public int getIdCtaContableVt() {
        return idCtaContableVt;
    }

    public void setIdCtaContableVt(int idCtaContableVt) {
        this.idCtaContableVt = idCtaContableVt;
    }

    public String getPagoEfectivo() {
        return pagoEfectivo;
    }

    public void setPagoEfectivo(String pagoEfectivo) {
        this.pagoEfectivo = pagoEfectivo;
    }

    public String getPagoCheque() {
        return pagoCheque;
    }

    public void setPagoCheque(String pagoCheque) {
        this.pagoCheque = pagoCheque;
    }

    public String getVrClase() {
        return vrClase;
    }

    public void setVrClase(String vrClase) {
        this.vrClase = vrClase;
    }

    public String getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(String codigoPos) {
        this.codigoPos = codigoPos;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getRequiereEntBco() {
        return requiereEntBco;
    }

    public void setRequiereEntBco(String requiereEntBco) {
        this.requiereEntBco = requiereEntBco;
    }

    public String getRequiereEntOc() {
        return requiereEntOc;
    }

    public void setRequiereEntOc(String requiereEntOc) {
        this.requiereEntOc = requiereEntOc;
    }

    public String getRequiereEmpl() {
        return requiereEmpl;
    }

    public void setRequiereEmpl(String requiereEmpl) {
        this.requiereEmpl = requiereEmpl;
    }

    public int getMontoMaxVuelto() {
        return montoMaxVuelto;
    }

    public void setMontoMaxVuelto(int montoMaxVuelto) {
        this.montoMaxVuelto = montoMaxVuelto;
    }

    public int getMontoMinVuelto() {
        return montoMinVuelto;
    }

    public void setMontoMinVuelto(int montoMinVuelto) {
        this.montoMinVuelto = montoMinVuelto;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
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

    public String getCancelarDescProm() {
        return cancelarDescProm;
    }

    public void setCancelarDescProm(String cancelarDescProm) {
        this.cancelarDescProm = cancelarDescProm;
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

    public int getMontoMaxCobros() {
        return montoMaxCobros;
    }

    public void setMontoMaxCobros(int montoMaxCobros) {
        this.montoMaxCobros = montoMaxCobros;
    }

    public int getMontoMinCobros() {
        return montoMinCobros;
    }

    public void setMontoMinCobros(int montoMinCobros) {
        this.montoMinCobros = montoMinCobros;
    }

    public String getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(String codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public String getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(String esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

   
    
}
