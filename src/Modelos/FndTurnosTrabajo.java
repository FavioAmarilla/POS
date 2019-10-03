package Modelos;

import java.sql.Timestamp;

public class FndTurnosTrabajo {
    int identificador;
    String nombre;
    Timestamp horaInicio, horaFin;
    String usrCre;
    Timestamp fecCre;
    String descripcion, abreviatura, diaSemana, diaFeriado;
    int idEmpresa, idUnidad;
    
    //TURNOS_CAJA
    int idCaja, idTurno;
    Timestamp fechaDesde, fechaHasta;
    
    public FndTurnosTrabajo() {
    }

    public FndTurnosTrabajo(int identificador, String nombre, Timestamp horaInicio, Timestamp horaFin, String usrCre, Timestamp fecCre, String descripcion, String abreviatura, String diaSemana, String diaFeriado, int idEmpresa, int idUnidad, int idCaja, int idTurno, Timestamp fechaDesde, Timestamp fechaHasta) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.descripcion = descripcion;
        this.abreviatura = abreviatura;
        this.diaSemana = diaSemana;
        this.diaFeriado = diaFeriado;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idCaja = idCaja;
        this.idTurno = idTurno;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
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

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getDiaFeriado() {
        return diaFeriado;
    }

    public void setDiaFeriado(String diaFeriado) {
        this.diaFeriado = diaFeriado;
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

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
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

    
   
    
    
}
