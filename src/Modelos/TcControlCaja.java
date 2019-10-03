/*
    Clase que contiene todos los campos a ser utilizados para los controles de cajas
*/
package Modelos;

import java.sql.Timestamp;

public class TcControlCaja {
    long identificador;
    int idEmpresa, idUnidad, idCaja, idTurno;
    String usrApertura;
    String usrCre;
    int idMoneda, idCajero;
    long totalDebitos, TotalCreditos, total, totalExento;
    long montoEfectivo;
    String arqueado, cerrado;
    int nroInicial, nroFinal;
    long totalGrabado, totalImpuesto, totalGrabado5, totalImpuesto5;
    long totalGrabado10, totalImpuesto10, totalDescuento, totalAnulado;
    long totalCancelado, cantCancelado, cantDevolucion, cantDescuentos;
    int cantAnulado, secuencia, idRegistroCtrl;
    String usrMod,descripcion;
    
    Timestamp fecApertura, fecCre, fecMod, fecEnvSrv, fechaCierre;
    String feApertura, feCre, feMod, feEnvSrv, fehaCierre;
    
    public TcControlCaja() {
    }

    public TcControlCaja(long identificador, int idEmpresa, int idUnidad, int idCaja, int idTurno, String usrApertura, String usrCre, int idMoneda, int idCajero, long totalDebitos, long TotalCreditos, long total, long totalExento, long montoEfectivo, String arqueado, String cerrado, int nroInicial, int nroFinal, long totalGrabado, long totalImpuesto, long totalGrabado5, long totalImpuesto5, long totalGrabado10, long totalImpuesto10, long totalDescuento, long totalAnulado, long totalCancelado, long cantCancelado, long cantDevolucion, long cantDescuentos, int cantAnulado, int secuencia, int idRegistroCtrl, String usrMod, String descripcion, Timestamp fecApertura, Timestamp fecCre, Timestamp fecMod, Timestamp fecEnvSrv, Timestamp fechaCierre, String feApertura, String feCre, String feMod, String feEnvSrv, String fehaCierre) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idCaja = idCaja;
        this.idTurno = idTurno;
        this.usrApertura = usrApertura;
        this.usrCre = usrCre;
        this.idMoneda = idMoneda;
        this.idCajero = idCajero;
        this.totalDebitos = totalDebitos;
        this.TotalCreditos = TotalCreditos;
        this.total = total;
        this.totalExento = totalExento;
        this.montoEfectivo = montoEfectivo;
        this.arqueado = arqueado;
        this.cerrado = cerrado;
        this.nroInicial = nroInicial;
        this.nroFinal = nroFinal;
        this.totalGrabado = totalGrabado;
        this.totalImpuesto = totalImpuesto;
        this.totalGrabado5 = totalGrabado5;
        this.totalImpuesto5 = totalImpuesto5;
        this.totalGrabado10 = totalGrabado10;
        this.totalImpuesto10 = totalImpuesto10;
        this.totalDescuento = totalDescuento;
        this.totalAnulado = totalAnulado;
        this.totalCancelado = totalCancelado;
        this.cantCancelado = cantCancelado;
        this.cantDevolucion = cantDevolucion;
        this.cantDescuentos = cantDescuentos;
        this.cantAnulado = cantAnulado;
        this.secuencia = secuencia;
        this.idRegistroCtrl = idRegistroCtrl;
        this.usrMod = usrMod;
        this.descripcion = descripcion;
        this.fecApertura = fecApertura;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
        this.fecEnvSrv = fecEnvSrv;
        this.fechaCierre = fechaCierre;
        this.feApertura = feApertura;
        this.feCre = feCre;
        this.feMod = feMod;
        this.feEnvSrv = feEnvSrv;
        this.fehaCierre = fehaCierre;
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

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getUsrApertura() {
        return usrApertura;
    }

    public void setUsrApertura(String usrApertura) {
        this.usrApertura = usrApertura;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public long getTotalDebitos() {
        return totalDebitos;
    }

    public void setTotalDebitos(long totalDebitos) {
        this.totalDebitos = totalDebitos;
    }

    public long getTotalCreditos() {
        return TotalCreditos;
    }

    public void setTotalCreditos(long TotalCreditos) {
        this.TotalCreditos = TotalCreditos;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(long totalExento) {
        this.totalExento = totalExento;
    }

    public long getMontoEfectivo() {
        return montoEfectivo;
    }

    public void setMontoEfectivo(long montoEfectivo) {
        this.montoEfectivo = montoEfectivo;
    }

    public String getArqueado() {
        return arqueado;
    }

    public void setArqueado(String arqueado) {
        this.arqueado = arqueado;
    }

    public String getCerrado() {
        return cerrado;
    }

    public void setCerrado(String cerrado) {
        this.cerrado = cerrado;
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

    public long getTotalGrabado() {
        return totalGrabado;
    }

    public void setTotalGrabado(long totalGrabado) {
        this.totalGrabado = totalGrabado;
    }

    public long getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(long totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public long getTotalGrabado5() {
        return totalGrabado5;
    }

    public void setTotalGrabado5(long totalGrabado5) {
        this.totalGrabado5 = totalGrabado5;
    }

    public long getTotalImpuesto5() {
        return totalImpuesto5;
    }

    public void setTotalImpuesto5(long totalImpuesto5) {
        this.totalImpuesto5 = totalImpuesto5;
    }

    public long getTotalGrabado10() {
        return totalGrabado10;
    }

    public void setTotalGrabado10(long totalGrabado10) {
        this.totalGrabado10 = totalGrabado10;
    }

    public long getTotalImpuesto10() {
        return totalImpuesto10;
    }

    public void setTotalImpuesto10(long totalImpuesto10) {
        this.totalImpuesto10 = totalImpuesto10;
    }

    public long getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(long totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public long getTotalAnulado() {
        return totalAnulado;
    }

    public void setTotalAnulado(long totalAnulado) {
        this.totalAnulado = totalAnulado;
    }

    public long getTotalCancelado() {
        return totalCancelado;
    }

    public void setTotalCancelado(long totalCancelado) {
        this.totalCancelado = totalCancelado;
    }

    public long getCantCancelado() {
        return cantCancelado;
    }

    public void setCantCancelado(long cantCancelado) {
        this.cantCancelado = cantCancelado;
    }

    public long getCantDevolucion() {
        return cantDevolucion;
    }

    public void setCantDevolucion(long cantDevolucion) {
        this.cantDevolucion = cantDevolucion;
    }

    public long getCantDescuentos() {
        return cantDescuentos;
    }

    public void setCantDescuentos(long cantDescuentos) {
        this.cantDescuentos = cantDescuentos;
    }

    public int getCantAnulado() {
        return cantAnulado;
    }

    public void setCantAnulado(int cantAnulado) {
        this.cantAnulado = cantAnulado;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public int getIdRegistroCtrl() {
        return idRegistroCtrl;
    }

    public void setIdRegistroCtrl(int idRegistroCtrl) {
        this.idRegistroCtrl = idRegistroCtrl;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecApertura() {
        return fecApertura;
    }

    public void setFecApertura(Timestamp fecApertura) {
        this.fecApertura = fecApertura;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public Timestamp getFecEnvSrv() {
        return fecEnvSrv;
    }

    public void setFecEnvSrv(Timestamp fecEnvSrv) {
        this.fecEnvSrv = fecEnvSrv;
    }

    public Timestamp getfechaCierre() {
        return fechaCierre;
    }

    public void setfechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getFeApertura() {
        return feApertura;
    }

    public void setFeApertura(String feApertura) {
        this.feApertura = feApertura;
    }

    public String getFeCre() {
        return feCre;
    }

    public void setFeCre(String feCre) {
        this.feCre = feCre;
    }

    public String getFeMod() {
        return feMod;
    }

    public void setFeMod(String feMod) {
        this.feMod = feMod;
    }

    public String getFeEnvSrv() {
        return feEnvSrv;
    }

    public void setFeEnvSrv(String feEnvSrv) {
        this.feEnvSrv = feEnvSrv;
    }

    public String getFehaCierre() {
        return fehaCierre;
    }

    public void setFehaCierre(String fehaCierre) {
        this.fehaCierre = fehaCierre;
    }

   
    
}
