package Modelos;

import java.sql.Timestamp;

public class VtaClientes {

    //VTA_CLIENTES
    long identificador;
    int idEmpresa, idUnidad;
    String codigo, activo;
    int idCategoria;
    String vrTipoPersona, usrCre, usrMod;
    String apellidos, nombres, razon_social, direccion, telefono, vrTipoDocumento, numeroDocumento, numeroRuc, nombreBarrio, nombreCiudad, envServer;

    Timestamp fecCre, fecMod, fechaInscClt;

    public VtaClientes() {
    }

    public VtaClientes(long identificador, int idEmpresa, int idUnidad, String codigo, String activo, int idCategoria, String vrTipoPersona, String usrCre, String usrMod, String apellidos, String nombres, String razon_social, String direccion, String telefono, String vrTipoDocumento, String numeroDocumento, String numeroRuc, String nombreBarrio, String nombreCiudad, String envServer, Timestamp fecCre, Timestamp fecMod, Timestamp fechaInscClt) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.codigo = codigo;
        this.activo = activo;
        this.idCategoria = idCategoria;
        this.vrTipoPersona = vrTipoPersona;
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.telefono = telefono;
        this.vrTipoDocumento = vrTipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroRuc = numeroRuc;
        this.nombreBarrio = nombreBarrio;
        this.nombreCiudad = nombreCiudad;
        this.envServer = envServer;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
        this.fechaInscClt = fechaInscClt;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getVrTipoPersona() {
        return vrTipoPersona;
    }

    public void setVrTipoPersona(String vrTipoPersona) {
        this.vrTipoPersona = vrTipoPersona;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getVrTipoDocumento() {
        return vrTipoDocumento;
    }

    public void setVrTipoDocumento(String vrTipoDocumento) {
        this.vrTipoDocumento = vrTipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNumeroRuc() {
        return numeroRuc;
    }

    public void setNumeroRuc(String numeroRuc) {
        this.numeroRuc = numeroRuc;
    }

    public String getNombreBarrio() {
        return nombreBarrio;
    }

    public void setNombreBarrio(String nombreBarrio) {
        this.nombreBarrio = nombreBarrio;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getEnvServer() {
        return envServer;
    }

    public void setEnvServer(String envServer) {
        this.envServer = envServer;
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

    public Timestamp getfechaInscClt() {
        return fechaInscClt;
    }

    public void setfechaInscClt(Timestamp fechaInscClt) {
        this.fechaInscClt = fechaInscClt;
    }

}
