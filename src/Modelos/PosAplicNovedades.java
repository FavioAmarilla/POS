package Modelos;

import java.sql.Timestamp;

public class PosAplicNovedades {
    int identificador, idUnidad, ultimoIdNovedad;
    String nombreTabla, usrCre;
    Timestamp fecCre;

    public PosAplicNovedades() {
    }

    public PosAplicNovedades(int identificador, int idUnidad, int ultimoIdNovedad, String nombreTabla, String usrCre, Timestamp fecCre) {
        this.identificador = identificador;
        this.idUnidad = idUnidad;
        this.ultimoIdNovedad = ultimoIdNovedad;
        this.nombreTabla = nombreTabla;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
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

    public int getUltimoIdNovedad() {
        return ultimoIdNovedad;
    }

    public void setUltimoIdNovedad(int ultimoIdNovedad) {
        this.ultimoIdNovedad = ultimoIdNovedad;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
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
    
    
}
