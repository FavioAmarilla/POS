/*
    Clase que contiene todos los campos a ser utilizados para las declaraciones de efectivo
*/
package Modelos;

import java.sql.Date;
import java.sql.Timestamp;

public class TcDeclaracionEfec {

    long idArqueo;
    int idDenominacion;
    int idEmpresa,idUnidad,cantidad;
    long importe;
    String usrCre,fecCre,usrMod,fecMod;
    
    Timestamp feCre, feMod;
    
    public TcDeclaracionEfec() {
    }

    public TcDeclaracionEfec(int idArqueo, int idDenominacion, int idEmpresa, int idUnidad, int cantidad, long importe, String usrCre, String fecCre, String usrMod, String fecMod, Timestamp feCre, Timestamp feMod) {
        this.idArqueo = idArqueo;
        this.idDenominacion = idDenominacion;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.cantidad = cantidad;
        this.importe = importe;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.feCre = feCre;
        this.feMod = feMod;
    }

    public long getIdArqueo() {
        return idArqueo;
    }

    public void setIdArqueo(long idArqueo) {
        this.idArqueo = idArqueo;
    }

    public int getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.idDenominacion = idDenominacion;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getFecCre() {
        return fecCre;
    }

    public void setFecCre(String fecCre) {
        this.fecCre = fecCre;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getFecMod() {
        return fecMod;
    }

    public void setFecMod(String fecMod) {
        this.fecMod = fecMod;
    }

    public Timestamp getFeCre() {
        return feCre;
    }

    public void setFeCre(Timestamp feCre) {
        this.feCre = feCre;
    }

    public Timestamp getFeMod() {
        return feMod;
    }

    public void setFeMod(Timestamp feMod) {
        this.feMod = feMod;
    }

   

    
}
