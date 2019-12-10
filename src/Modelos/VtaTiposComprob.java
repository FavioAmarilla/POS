package Modelos;

import java.sql.Timestamp;

public class VtaTiposComprob {

    int identificador;
    String descripcion, vrDebitoCre, usrCre;
    Timestamp fecCre;
    String abreviatura, vrClaseComp;
    int lineasLimite, idProgEmision;
    String vrGrupoImp, codigoPos, activo, vrTipoNumeracion;
    String idEmpresa, idUnidad;

    public VtaTiposComprob() {
    }

    public VtaTiposComprob(int identificador, String descripcion, String vrDebitoCre, String usrCre, Timestamp fecCre, String abreviatura, String vrClaseComp, int lineasLimite, int idProgEmision, String vrGrupoImp, String codigoPos, String activo, String vrTipoNumeracion, String idEmpresa, String idUnidad) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.vrDebitoCre = vrDebitoCre;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.abreviatura = abreviatura;
        this.vrClaseComp = vrClaseComp;
        this.lineasLimite = lineasLimite;
        this.idProgEmision = idProgEmision;
        this.vrGrupoImp = vrGrupoImp;
        this.codigoPos = codigoPos;
        this.activo = activo;
        this.vrTipoNumeracion = vrTipoNumeracion;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
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

    public String getVrDebitoCre() {
        return vrDebitoCre;
    }

    public void setVrDebitoCre(String vrDebitoCre) {
        this.vrDebitoCre = vrDebitoCre;
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

    public String getVrClaseComp() {
        return vrClaseComp;
    }

    public void setVrClaseComp(String vrClaseComp) {
        this.vrClaseComp = vrClaseComp;
    }

    public int getLineasLimite() {
        return lineasLimite;
    }

    public void setLineasLimite(int lineasLimite) {
        this.lineasLimite = lineasLimite;
    }

    public int getIdProgEmision() {
        return idProgEmision;
    }

    public void setIdProgEmision(int idProgEmision) {
        this.idProgEmision = idProgEmision;
    }

    public String getVrGrupoImp() {
        return vrGrupoImp;
    }

    public void setVrGrupoImp(String vrGrupoImp) {
        this.vrGrupoImp = vrGrupoImp;
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

    public String getVrTipoNumeracion() {
        return vrTipoNumeracion;
    }

    public void setVrTipoNumeracion(String vrTipoNumeracion) {
        this.vrTipoNumeracion = vrTipoNumeracion;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }

}
