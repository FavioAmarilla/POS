package Modelos;

import java.sql.Timestamp;

public class VtaAsignacionComp {
    int identificador;
    int idEmpresa;
    int idUnidad;
    Timestamp fecha;
    String activo;
    String usrCre;
    Timestamp fecCre;
    int nroDesde;
    int nroHasta;
    int idSitio;
    int idPuntoEmision;
    int ultNroUsado;
    int idRegistroCtrol;
    int codigoPos;

    public VtaAsignacionComp() {
    }

    public VtaAsignacionComp(int identificador, int idEmpresa, int idUnidad, Timestamp fecha, String activo, String usrCre, Timestamp fecCre, int nroDesde, int nroHasta, int idSitio, int idPuntoEmision, int ultNroUsado, int idRegistroCtrol, int codigoPos) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.fecha = fecha;
        this.activo = activo;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.nroDesde = nroDesde;
        this.nroHasta = nroHasta;
        this.idSitio = idSitio;
        this.idPuntoEmision = idPuntoEmision;
        this.ultNroUsado = ultNroUsado;
        this.idRegistroCtrol = idRegistroCtrol;
        this.codigoPos = codigoPos;
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

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Timestamp getfecha() {
        return fecha;
    }

    public void setfecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
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

    public int getNroDesde() {
        return nroDesde;
    }

    public void setNroDesde(int nroDesde) {
        this.nroDesde = nroDesde;
    }

    public int getNroHasta() {
        return nroHasta;
    }

    public void setNroHasta(int nroHasta) {
        this.nroHasta = nroHasta;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public int getIdPuntoEmision() {
        return idPuntoEmision;
    }

    public void setIdPuntoEmision(int idPuntoEmision) {
        this.idPuntoEmision = idPuntoEmision;
    }

    public int getUltNroUsado() {
        return ultNroUsado;
    }

    public void setUltNroUsado(int ultNroUsado) {
        this.ultNroUsado = ultNroUsado;
    }

    public int getIdRegistroCtrol() {
        return idRegistroCtrol;
    }

    public void setIdRegistroCtrol(int idRegistroCtrol) {
        this.idRegistroCtrol = idRegistroCtrol;
    }

    public int getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(int codigoPos) {
        this.codigoPos = codigoPos;
    }

    
    
}
