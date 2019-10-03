/*
    Clase que contiene todos los campos a ser utilizados para el arqueo de caja
*/
package Modelos;

import java.sql.Date;
import java.sql.Timestamp;

public class TcArqueoCaja {

    long identificador, idControl;
    int idEmpresa,idUnidad,idCaja;
    String vrTipoArqueo,usrCre,descripcion,confirmado;
    long totalConceptos,totalValores,diferencia, totalRedVueltos;
    int idCajero,nroControl;

    Timestamp fecCre, feEnvServ;
    String fecha;
    Date fefecha;
    
    public TcArqueoCaja() {
    }

    public TcArqueoCaja(int identificador, int idEmpresa, int idUnidad, int idCaja, int idControl, String vrTipoArqueo, String usrCre, String descripcion, String confirmado, long totalConceptos, long totalValores, long diferencia, long totalRedVueltos, int idCajero, int nroControl, Timestamp fecCre, Timestamp feEnvServ, String fecha, Date fefecha) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idCaja = idCaja;
        this.idControl = idControl;
        this.vrTipoArqueo = vrTipoArqueo;
        this.usrCre = usrCre;
        this.descripcion = descripcion;
        this.confirmado = confirmado;
        this.totalConceptos = totalConceptos;
        this.totalValores = totalValores;
        this.diferencia = diferencia;
        this.totalRedVueltos = totalRedVueltos;
        this.idCajero = idCajero;
        this.nroControl = nroControl;
        this.fecCre = fecCre;
        this.feEnvServ = feEnvServ;
        this.fecha = fecha;
        this.fefecha = fefecha;
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

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public long getIdControl() {
        return idControl;
    }

    public void setIdControl(long idControl) {
        this.idControl = idControl;
    }

    public String getVrTipoArqueo() {
        return vrTipoArqueo;
    }

    public void setVrTipoArqueo(String vrTipoArqueo) {
        this.vrTipoArqueo = vrTipoArqueo;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(String confirmado) {
        this.confirmado = confirmado;
    }

    public long getTotalConceptos() {
        return totalConceptos;
    }

    public void setTotalConceptos(long totalConceptos) {
        this.totalConceptos = totalConceptos;
    }

    public long getTotalValores() {
        return totalValores;
    }

    public void setTotalValores(long totalValores) {
        this.totalValores = totalValores;
    }

    public long getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(long diferencia) {
        this.diferencia = diferencia;
    }

    public long getTotalRedVueltos() {
        return totalRedVueltos;
    }

    public void setTotalRedVueltos(long totalRedVueltos) {
        this.totalRedVueltos = totalRedVueltos;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public int getNroControl() {
        return nroControl;
    }

    public void setNroControl(int nroControl) {
        this.nroControl = nroControl;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getFeEnvServ() {
        return feEnvServ;
    }

    public void setFeEnvServ(Timestamp feEnvServ) {
        this.feEnvServ = feEnvServ;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }

    public Date getFefecha() {
        return fefecha;
    }

    public void setFefecha(Date fefecha) {
        this.fefecha = fefecha;
    }

    
}
