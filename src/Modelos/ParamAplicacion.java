package Modelos;

import java.sql.Date;

public class ParamAplicacion {

    int idCaja, nroCaja;
    String cajaNombre;
    
    int idEmpresa;
    String empresa;
    int idSitio;
    String sucursal, sucEmision, cajaEmision;
    int idUnidad;
    String unidadNombre, puertoImpresion;
    int idPuntoEmision;
    String telefono, direccion, ciudad, pais;
    int idMoneda;
    String ruc, timbrado;
    Date vencTimbrado;
    
    public ParamAplicacion() {
    }

    public ParamAplicacion(int idCaja, int nroCaja, String cajaNombre, int idEmpresa, String empresa, int idSitio, String sucursal, String sucEmision, String cajaEmision, int idUnidad, String unidadNombre, String puertoImpresion, int idPuntoEmision, String telefono, String direccion, String ciudad, String pais, int idMoneda, String ruc, String timbrado, Date vencTimbrado) {
        this.idCaja = idCaja;
        this.nroCaja = nroCaja;
        this.cajaNombre = cajaNombre;
        this.idEmpresa = idEmpresa;
        this.empresa = empresa;
        this.idSitio = idSitio;
        this.sucursal = sucursal;
        this.sucEmision = sucEmision;
        this.cajaEmision = cajaEmision;
        this.idUnidad = idUnidad;
        this.unidadNombre = unidadNombre;
        this.puertoImpresion = puertoImpresion;
        this.idPuntoEmision = idPuntoEmision;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.idMoneda = idMoneda;
        this.ruc = ruc;
        this.timbrado = timbrado;
        this.vencTimbrado = vencTimbrado;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getNroCaja() {
        return nroCaja;
    }

    public void setNroCaja(int nroCaja) {
        this.nroCaja = nroCaja;
    }

    public String getCajaNombre() {
        return cajaNombre;
    }

    public void setCajaNombre(String cajaNombre) {
        this.cajaNombre = cajaNombre;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getSucEmision() {
        return sucEmision;
    }

    public void setSucEmision(String sucEmision) {
        this.sucEmision = sucEmision;
    }

    public String getCajaEmision() {
        return cajaEmision;
    }

    public void setCajaEmision(String cajaEmision) {
        this.cajaEmision = cajaEmision;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getUnidadNombre() {
        return unidadNombre;
    }

    public void setUnidadNombre(String unidadNombre) {
        this.unidadNombre = unidadNombre;
    }

    public String getPuertoImpresion() {
        return puertoImpresion;
    }

    public void setPuertoImpresion(String puertoImpresion) {
        this.puertoImpresion = puertoImpresion;
    }

    public int getIdPuntoEmision() {
        return idPuntoEmision;
    }

    public void setIdPuntoEmision(int idPuntoEmision) {
        this.idPuntoEmision = idPuntoEmision;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(String timbrado) {
        this.timbrado = timbrado;
    }

    public Date getVencTimbrado() {
        return vencTimbrado;
    }

    public void setVencTimbrado(Date vencTimbrado) {
        this.vencTimbrado = vencTimbrado;
    }

   

}
