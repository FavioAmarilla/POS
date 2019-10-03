package Modelos;

import java.sql.Timestamp;

public class FndSitios {
    int identificador;
    String descripcion;
    int idEmpresa, idUnidad;
    String codigo, usrCre;
    Timestamp fecCre;
    String direccion;
    int idPais;
    int idCiudad;
    String codigocontrol;
    int idUbicacion;
    String codigoFact;
    String codUnificacion;
    String itemSistema;

    public FndSitios() {
    }

    public FndSitios(int identificador, String descripcion, int idEmpresa, int idUnidad, String codigo, String usrCre, Timestamp fecCre, String direccion, int idPais, int idCiudad, String codigocontrol, int idUbicacion, String codigoFact, String codUnificacion, String itemSistema) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.codigo = codigo;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.direccion = direccion;
        this.idPais = idPais;
        this.idCiudad = idCiudad;
        this.codigocontrol = codigocontrol;
        this.idUbicacion = idUbicacion;
        this.codigoFact = codigoFact;
        this.codUnificacion = codUnificacion;
        this.itemSistema = itemSistema;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getCodigocontrol() {
        return codigocontrol;
    }

    public void setCodigocontrol(String codigocontrol) {
        this.codigocontrol = codigocontrol;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getCodigoFact() {
        return codigoFact;
    }

    public void setCodigoFact(String codigoFact) {
        this.codigoFact = codigoFact;
    }

    public String getCodUnificacion() {
        return codUnificacion;
    }

    public void setCodUnificacion(String codUnificacion) {
        this.codUnificacion = codUnificacion;
    }

    public String getItemSistema() {
        return itemSistema;
    }

    public void setItemSistema(String itemSistema) {
        this.itemSistema = itemSistema;
    }
    
    
}
