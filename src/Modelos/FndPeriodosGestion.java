package Modelos;

import java.sql.Timestamp;

public class FndPeriodosGestion {
    int identificador, idEmpresa;
    String codigo, nombre;
    int anho,trimestre;
    String fechaDesde, fechaHasta; 
    String vrSituacion, usrCre, fecCre, fechaApert, usrApert;
    String fechaCierre, usrCierre,usrMod,fecMod;
    
    Timestamp feDesde, feHasta,feCre, feApert,feCierre, feMod;

    public FndPeriodosGestion() {
    }

    public FndPeriodosGestion(int identificador, int idEmpresa, String codigo, String nombre, int anho, int trimestre, String fechaDesde, String fechaHasta, String vrSituacion, String usrCre, String fecCre, String fechaApert, String usrApert, String fechaCierre, String usrCierre, String usrMod, String fecMod, Timestamp feDesde, Timestamp feHasta, Timestamp feCre, Timestamp feApert, Timestamp feCierre, Timestamp feMod) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.codigo = codigo;
        this.nombre = nombre;
        this.anho = anho;
        this.trimestre = trimestre;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.vrSituacion = vrSituacion;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.fechaApert = fechaApert;
        this.usrApert = usrApert;
        this.fechaCierre = fechaCierre;
        this.usrCierre = usrCierre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.feDesde = feDesde;
        this.feHasta = feHasta;
        this.feCre = feCre;
        this.feApert = feApert;
        this.feCierre = feCierre;
        this.feMod = feMod;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public String getfechaDesde() {
        return fechaDesde;
    }

    public void setfechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getfechaHasta() {
        return fechaHasta;
    }

    public void setfechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getVrSituacion() {
        return vrSituacion;
    }

    public void setVrSituacion(String vrSituacion) {
        this.vrSituacion = vrSituacion;
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

    public String getfechaApert() {
        return fechaApert;
    }

    public void setfechaApert(String fechaApert) {
        this.fechaApert = fechaApert;
    }

    public String getUsrApert() {
        return usrApert;
    }

    public void setUsrApert(String usrApert) {
        this.usrApert = usrApert;
    }

    public String getfechaCierre() {
        return fechaCierre;
    }

    public void setfechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getUsrCierre() {
        return usrCierre;
    }

    public void setUsrCierre(String usrCierre) {
        this.usrCierre = usrCierre;
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

    public Timestamp getFeDesde() {
        return feDesde;
    }

    public void setFeDesde(Timestamp feDesde) {
        this.feDesde = feDesde;
    }

    public Timestamp getFeHasta() {
        return feHasta;
    }

    public void setFeHasta(Timestamp feHasta) {
        this.feHasta = feHasta;
    }

    public Timestamp getFeCre() {
        return feCre;
    }

    public void setFeCre(Timestamp feCre) {
        this.feCre = feCre;
    }

    public Timestamp getFeApert() {
        return feApert;
    }

    public void setFeApert(Timestamp feApert) {
        this.feApert = feApert;
    }

    public Timestamp getFeCierre() {
        return feCierre;
    }

    public void setFeCierre(Timestamp feCierre) {
        this.feCierre = feCierre;
    }

    public Timestamp getFeMod() {
        return feMod;
    }

    public void setFeMod(Timestamp feMod) {
        this.feMod = feMod;
    }
    
    
}
