package Modelos;

import java.sql.Timestamp;

public class FndUsuarios {
    //fnd_usuarios
    int idUsuarios, idEmpresa;
    String nombre, nombreCompleto;
    int idPerfil;
    String activo, abrirSesion;
    int sesionesConc;
    String claveAcceso;
    int idSitio;
    String usrCre, usrMod;
    Timestamp fecCre, fecMod;
    int idUnidad, diasCambioClv;
    String autCambioUnd, itemSistema;
    
    //ps_cajeros
    int idPsCajeros;
    String usuarioCaj, abrirCaja, usuarioExterno, codigoAutoriz;
    int codigoCaj, idCategoria, idProcesoVenta;
    
    //fnd_perfiles
    int idenPerfil;
    String nombrePerfil;
    String superUsuario;
    String descripcion;

    public FndUsuarios() {
    }

    public FndUsuarios(int idUsuarios, int idEmpresa, String nombre, String nombreCompleto, int idPerfil, String activo, String abrirSesion, int sesionesConc, String claveAcceso, int idSitio, String usrCre, String usrMod, Timestamp fecCre, Timestamp fecMod, int idUnidad, int diasCambioClv, String autCambioUnd, String itemSistema, int idPsCajeros, String usuarioCaj, String abrirCaja, String usuarioExterno, String codigoAutoriz, int codigoCaj, int idCategoria, int idProcesoVenta, int idenPerfil, String nombrePerfil, String superUsuario, String descripcion) {
        this.idUsuarios = idUsuarios;
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.idPerfil = idPerfil;
        this.activo = activo;
        this.abrirSesion = abrirSesion;
        this.sesionesConc = sesionesConc;
        this.claveAcceso = claveAcceso;
        this.idSitio = idSitio;
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
        this.idUnidad = idUnidad;
        this.diasCambioClv = diasCambioClv;
        this.autCambioUnd = autCambioUnd;
        this.itemSistema = itemSistema;
        this.idPsCajeros = idPsCajeros;
        this.usuarioCaj = usuarioCaj;
        this.abrirCaja = abrirCaja;
        this.usuarioExterno = usuarioExterno;
        this.codigoAutoriz = codigoAutoriz;
        this.codigoCaj = codigoCaj;
        this.idCategoria = idCategoria;
        this.idProcesoVenta = idProcesoVenta;
        this.idenPerfil = idenPerfil;
        this.nombrePerfil = nombrePerfil;
        this.superUsuario = superUsuario;
        this.descripcion = descripcion;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getAbrirSesion() {
        return abrirSesion;
    }

    public void setAbrirSesion(String abrirSesion) {
        this.abrirSesion = abrirSesion;
    }

    public int getSesionesConc() {
        return sesionesConc;
    }

    public void setSesionesConc(int sesionesConc) {
        this.sesionesConc = sesionesConc;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
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

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getDiasCambioClv() {
        return diasCambioClv;
    }

    public void setDiasCambioClv(int diasCambioClv) {
        this.diasCambioClv = diasCambioClv;
    }

    public String getAutCambioUnd() {
        return autCambioUnd;
    }

    public void setAutCambioUnd(String autCambioUnd) {
        this.autCambioUnd = autCambioUnd;
    }

    public String getItemSistema() {
        return itemSistema;
    }

    public void setItemSistema(String itemSistema) {
        this.itemSistema = itemSistema;
    }

    public int getIdPsCajeros() {
        return idPsCajeros;
    }

    public void setIdPsCajeros(int idPsCajeros) {
        this.idPsCajeros = idPsCajeros;
    }

    public String getUsuarioCaj() {
        return usuarioCaj;
    }

    public void setUsuarioCaj(String usuarioCaj) {
        this.usuarioCaj = usuarioCaj;
    }

    public String getAbrirCaja() {
        return abrirCaja;
    }

    public void setAbrirCaja(String abrirCaja) {
        this.abrirCaja = abrirCaja;
    }

    public String getUsuarioExterno() {
        return usuarioExterno;
    }

    public void setUsuarioExterno(String usuarioExterno) {
        this.usuarioExterno = usuarioExterno;
    }

    public String getCodigoAutoriz() {
        return codigoAutoriz;
    }

    public void setCodigoAutoriz(String codigoAutoriz) {
        this.codigoAutoriz = codigoAutoriz;
    }

    public int getCodigoCaj() {
        return codigoCaj;
    }

    public void setCodigoCaj(int codigoCaj) {
        this.codigoCaj = codigoCaj;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProcesoVenta() {
        return idProcesoVenta;
    }

    public void setIdProcesoVenta(int idProcesoVenta) {
        this.idProcesoVenta = idProcesoVenta;
    }

    public int getIdenPerfil() {
        return idenPerfil;
    }

    public void setIdenPerfil(int idenPerfil) {
        this.idenPerfil = idenPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getSuperUsuario() {
        return superUsuario;
    }

    public void setSuperUsuario(String superUsuario) {
        this.superUsuario = superUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
   
}
