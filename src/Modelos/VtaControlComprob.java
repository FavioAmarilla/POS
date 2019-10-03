package Modelos;

import java.sql.Timestamp;


public class VtaControlComprob {

    int identificador;
    int idEmpresa;
    int idSitio;
    int numero;
    Timestamp fechaDesde;
    Timestamp fechaHasta;
    String activo;
    int longNroComprob;
    String usrCre;
    Timestamp fecCre;
    int idUnidad;
    String vrUso;
    int nroInicial;
    int nroFinal;
    int codigoPos;
    Timestamp fechaVenc;
    int idRegistroCtrl;
    
    public VtaControlComprob() {
    }

    public VtaControlComprob(int identificador, int idEmpresa, int idSitio, int numero, Timestamp fechaDesde, Timestamp fechaHasta, String activo, int longNroComprob, String usrCre, Timestamp fecCre, int idUnidad, String vrUso, int nroInicial, int nroFinal, int codigoPos, Timestamp fechaVenc, int idRegistroCtrl) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idSitio = idSitio;
        this.numero = numero;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.activo = activo;
        this.longNroComprob = longNroComprob;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.idUnidad = idUnidad;
        this.vrUso = vrUso;
        this.nroInicial = nroInicial;
        this.nroFinal = nroFinal;
        this.codigoPos = codigoPos;
        this.fechaVenc = fechaVenc;
        this.idRegistroCtrl = idRegistroCtrl;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Timestamp getfechaDesde() {
        return fechaDesde;
    }

    public void setfechaDesde(Timestamp fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Timestamp getfechaHasta() {
        return fechaHasta;
    }

    public void setfechaHasta(Timestamp fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public int getLongNroComprob() {
        return longNroComprob;
    }

    public void setLongNroComprob(int longNroComprob) {
        this.longNroComprob = longNroComprob;
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

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getVrUso() {
        return vrUso;
    }

    public void setVrUso(String vrUso) {
        this.vrUso = vrUso;
    }

    public int getNroInicial() {
        return nroInicial;
    }

    public void setNroInicial(int nroInicial) {
        this.nroInicial = nroInicial;
    }

    public int getNroFinal() {
        return nroFinal;
    }

    public void setNroFinal(int nroFinal) {
        this.nroFinal = nroFinal;
    }

    public int getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(int codigoPos) {
        this.codigoPos = codigoPos;
    }

    public Timestamp getfechaVenc() {
        return fechaVenc;
    }

    public void setfechaVenc(Timestamp fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public int getIdRegistroCtrl() {
        return idRegistroCtrl;
    }

    public void setIdRegistroCtrl(int idRegistroCtrl) {
        this.idRegistroCtrl = idRegistroCtrl;
    }

   

   
}
