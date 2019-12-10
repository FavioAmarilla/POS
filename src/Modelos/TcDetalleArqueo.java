/*
    Clase que contiene todos los campos a ser utilizados para los detalles del arqueo
*/
package Modelos;

import java.sql.Timestamp;

public class TcDetalleArqueo {
    long idArqueo;
    int idConcepto, idEmpresa, idUnidad;
    String usrCre, fecCre, usrMod, fecMod;
    long importeCalc, importeDecl, cantidadComp;

    Timestamp feCre, feMod;
            
    public TcDetalleArqueo() {
    }

    public TcDetalleArqueo(int idArqueo, int idConcepto, int idEmpresa, int idUnidad, String usrCre, String fecCre, String usrMod, String fecMod, long importeCalc, long importeDecl, long cantidadComp, Timestamp feCre, Timestamp feMod) {
        this.idArqueo = idArqueo;
        this.idConcepto = idConcepto;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.importeCalc = importeCalc;
        this.importeDecl = importeDecl;
        this.cantidadComp = cantidadComp;
        this.feCre = feCre;
        this.feMod = feMod;
    }

    public long getIdArqueo() {
        return idArqueo;
    }

    public void setIdArqueo(long idArqueo) {
        this.idArqueo = idArqueo;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
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

    public long getImporteCalc() {
        return importeCalc;
    }

    public void setImporteCalc(long importeCalc) {
        this.importeCalc = importeCalc;
    }

    public long getImporteDecl() {
        return importeDecl;
    }

    public void setImporteDecl(long importeDecl) {
        this.importeDecl = importeDecl;
    }

    public long getCantidadComp() {
        return cantidadComp;
    }

    public void setCantidadComp(long cantidadComp) {
        this.cantidadComp = cantidadComp;
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
