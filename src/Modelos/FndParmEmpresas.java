package Modelos;

import java.sql.Timestamp;

public class FndParmEmpresas {

    int identificador, idMoneda, idTipoImpuesto;
    String nombre, usrCre;
    Timestamp fecCre;
    String agenteRetencion,autoImpresor,nombreAlternativo;
    String direccion,telefonos,nroDocumento;
    String ciudad,pais,exportarTrn,nombreDbLink;
    int idUoCentral;
    String email,paginaWeb,plataforma,comandoImp;
    String itemSistema,diaCierrePeriodo;

    public FndParmEmpresas() {
    }

    public FndParmEmpresas(int identificador, String nombre, int idMoneda, int idTipoImpuesto, String usrCre, Timestamp fecCre, String agenteRetencion, String autoImpresor, String nombreAlternativo, String direccion, String telefonos, String nroDocumento, String ciudad, String pais, String exportarTrn, String nombreDbLink, int idUoCentral, String email, String paginaWeb, String plataforma, String comandoImp, String itemSistema, String diaCierrePeriodo) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.idMoneda = idMoneda;
        this.idTipoImpuesto = idTipoImpuesto;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.agenteRetencion = agenteRetencion;
        this.autoImpresor = autoImpresor;
        this.nombreAlternativo = nombreAlternativo;
        this.direccion = direccion;
        this.telefonos = telefonos;
        this.nroDocumento = nroDocumento;
        this.ciudad = ciudad;
        this.pais = pais;
        this.exportarTrn = exportarTrn;
        this.nombreDbLink = nombreDbLink;
        this.idUoCentral = idUoCentral;
        this.email = email;
        this.paginaWeb = paginaWeb;
        this.plataforma = plataforma;
        this.comandoImp = comandoImp;
        this.itemSistema = itemSistema;
        this.diaCierrePeriodo = diaCierrePeriodo;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getIdTipoImpuesto() {
        return idTipoImpuesto;
    }

    public void setIdTipoImpuesto(int idTipoImpuesto) {
        this.idTipoImpuesto = idTipoImpuesto;
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

    public String getAgenteRetencion() {
        return agenteRetencion;
    }

    public void setAgenteRetencion(String agenteRetencion) {
        this.agenteRetencion = agenteRetencion;
    }

    public String getAutoImpresor() {
        return autoImpresor;
    }

    public void setAutoImpresor(String autoImpresor) {
        this.autoImpresor = autoImpresor;
    }

    public String getNombreAlternativo() {
        return nombreAlternativo;
    }

    public void setNombreAlternativo(String nombreAlternativo) {
        this.nombreAlternativo = nombreAlternativo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getExportarTrn() {
        return exportarTrn;
    }

    public void setExportarTrn(String exportarTrn) {
        this.exportarTrn = exportarTrn;
    }

    public String getNombreDbLink() {
        return nombreDbLink;
    }

    public void setNombreDbLink(String nombreDbLink) {
        this.nombreDbLink = nombreDbLink;
    }

    public int getIdUoCentral() {
        return idUoCentral;
    }

    public void setIdUoCentral(int idUoCentral) {
        this.idUoCentral = idUoCentral;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getComandoImp() {
        return comandoImp;
    }

    public void setComandoImp(String comandoImp) {
        this.comandoImp = comandoImp;
    }

    public String getItemSistema() {
        return itemSistema;
    }

    public void setItemSistema(String itemSistema) {
        this.itemSistema = itemSistema;
    }

    public String getDiaCierrePeriodo() {
        return diaCierrePeriodo;
    }

    public void setDiaCierrePeriodo(String diaCierrePeriodo) {
        this.diaCierrePeriodo = diaCierrePeriodo;
    }

}
