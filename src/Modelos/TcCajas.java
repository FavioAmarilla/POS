package Modelos;

import java.sql.Timestamp;

public class TcCajas {
        int identificador,idUnidad,idEmpresa,numero;
        String descripcion;
        String vrTipoCaja;
        int idSitio;
        String equipo,userCre,userMod;
        int idPuntoEmision;
        String vrTipoImpresora,impCintaAudit,usarCortePapel;
        String usarRegistroControl,vrPuertoImpresion,usarGaveta;
        String puertoImpresion,activo;
        //VTA_PUNTOS_EMISION
        int idPunto;
        String codigo, emitirNc;
        int idCaja;
        Timestamp fecCre, fecMod;
 
    public TcCajas() {
    }

    public TcCajas(int identificador, int idUnidad, int idEmpresa, int numero, String descripcion, String vrTipoCaja, int idSitio, String equipo, String userCre, String userMod, int idPuntoEmision, String vrTipoImpresora, String impCintaAudit, String usarCortePapel, String usarRegistroControl, String vrPuertoImpresion, String usarGaveta, String puertoImpresion, String activo, int idPunto, String codigo, String emitirNc, int idCaja, Timestamp fecCre, Timestamp fecMod) {
        this.identificador = identificador;
        this.idUnidad = idUnidad;
        this.idEmpresa = idEmpresa;
        this.numero = numero;
        this.descripcion = descripcion;
        this.vrTipoCaja = vrTipoCaja;
        this.idSitio = idSitio;
        this.equipo = equipo;
        this.userCre = userCre;
        this.userMod = userMod;
        this.idPuntoEmision = idPuntoEmision;
        this.vrTipoImpresora = vrTipoImpresora;
        this.impCintaAudit = impCintaAudit;
        this.usarCortePapel = usarCortePapel;
        this.usarRegistroControl = usarRegistroControl;
        this.vrPuertoImpresion = vrPuertoImpresion;
        this.usarGaveta = usarGaveta;
        this.puertoImpresion = puertoImpresion;
        this.activo = activo;
        this.idPunto = idPunto;
        this.codigo = codigo;
        this.emitirNc = emitirNc;
        this.idCaja = idCaja;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVrTipoCaja() {
        return vrTipoCaja;
    }

    public void setVrTipoCaja(String vrTipoCaja) {
        this.vrTipoCaja = vrTipoCaja;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getUserCre() {
        return userCre;
    }

    public void setUserCre(String userCre) {
        this.userCre = userCre;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    public int getIdPuntoEmision() {
        return idPuntoEmision;
    }

    public void setIdPuntoEmision(int idPuntoEmision) {
        this.idPuntoEmision = idPuntoEmision;
    }

    public String getVrTipoImpresora() {
        return vrTipoImpresora;
    }

    public void setVrTipoImpresora(String vrTipoImpresora) {
        this.vrTipoImpresora = vrTipoImpresora;
    }

    public String getImpCintaAudit() {
        return impCintaAudit;
    }

    public void setImpCintaAudit(String impCintaAudit) {
        this.impCintaAudit = impCintaAudit;
    }

    public String getUsarCortePapel() {
        return usarCortePapel;
    }

    public void setUsarCortePapel(String usarCortePapel) {
        this.usarCortePapel = usarCortePapel;
    }

    public String getUsarRegistroControl() {
        return usarRegistroControl;
    }

    public void setUsarRegistroControl(String usarRegistroControl) {
        this.usarRegistroControl = usarRegistroControl;
    }

    public String getVrPuertoImpresion() {
        return vrPuertoImpresion;
    }

    public void setVrPuertoImpresion(String vrPuertoImpresion) {
        this.vrPuertoImpresion = vrPuertoImpresion;
    }

    public String getUsarGaveta() {
        return usarGaveta;
    }

    public void setUsarGaveta(String usarGaveta) {
        this.usarGaveta = usarGaveta;
    }

    public String getPuertoImpresion() {
        return puertoImpresion;
    }

    public void setPuertoImpresion(String puertoImpresion) {
        this.puertoImpresion = puertoImpresion;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmitirNc() {
        return emitirNc;
    }

    public void setEmitirNc(String emitirNc) {
        this.emitirNc = emitirNc;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

   
}
