package Modelos;

import java.sql.Timestamp;

public class TcCobrosCaja {

    long identificador;
    int idEmpresa, idUnidad, idTipoCobro;
    String numero;
    long idControl;
    int idSitio, idCaja;
    String usrCajero, usrCre;
    int idMoneda, idTurno, tipoCambio;
    long importeEfectivo, importeDocum, importeVuelto, importeConfirm;
    long idCliente;
    int idPaciente, idCtaRef;
    long idComprobante;
    String nroCtaRef, verificado, confirmado;
    String usrConfirm, reversado, usrReversion;
    int idMovReversion;
    String usrMod, comentarios, numeroRecibo;
    int idCajero;
    String atributo1, atributo2, atributo3, atributo4, atributo5, vrGenCompRetencion;
    long importeRedondeo, impoteVueltoRed, importeComplVuelto, montoTotal, montoTotalReal;

    Timestamp fecha, fecCre, fecConfirm, fecReversion, fecMod;
    String fefecha, feCre, feConfirm, feReversion, feMod;

    public TcCobrosCaja() {
    }

    public TcCobrosCaja(int identificador, int idEmpresa, int idUnidad, int idTipoCobro, String numero, int idControl, int idSitio, int idCaja, String usrCajero, String usrCre, int idMoneda, int idTurno, int tipoCambio, long importeEfectivo, long importeDocum, long importeVuelto, long importeConfirm, long idCliente, int idPaciente, int idCtaRef, int idComprobante, String nroCtaRef, String verificado, String confirmado, String usrConfirm, String reversado, String usrReversion, int idMovReversion, String usrMod, String comentarios, String numeroRecibo, int idCajero, String atributo1, String atributo2, String atributo3, String atributo4, String atributo5, String vrGenCompRetencion, long importeRedondeo, long impoteVueltoRed, long importeComplVuelto, long montoTotal, long montoTotalReal, Timestamp fecha, Timestamp fecCre, Timestamp fecConfirm, Timestamp fecReversion, Timestamp fecMod, String fefecha, String feCre, String feConfirm, String feReversion, String feMod) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idTipoCobro = idTipoCobro;
        this.numero = numero;
        this.idControl = idControl;
        this.idSitio = idSitio;
        this.idCaja = idCaja;
        this.usrCajero = usrCajero;
        this.usrCre = usrCre;
        this.idMoneda = idMoneda;
        this.idTurno = idTurno;
        this.tipoCambio = tipoCambio;
        this.importeEfectivo = importeEfectivo;
        this.importeDocum = importeDocum;
        this.importeVuelto = importeVuelto;
        this.importeConfirm = importeConfirm;
        this.idCliente = idCliente;
        this.idPaciente = idPaciente;
        this.idCtaRef = idCtaRef;
        this.idComprobante = idComprobante;
        this.nroCtaRef = nroCtaRef;
        this.verificado = verificado;
        this.confirmado = confirmado;
        this.usrConfirm = usrConfirm;
        this.reversado = reversado;
        this.usrReversion = usrReversion;
        this.idMovReversion = idMovReversion;
        this.usrMod = usrMod;
        this.comentarios = comentarios;
        this.numeroRecibo = numeroRecibo;
        this.idCajero = idCajero;
        this.atributo1 = atributo1;
        this.atributo2 = atributo2;
        this.atributo3 = atributo3;
        this.atributo4 = atributo4;
        this.atributo5 = atributo5;
        this.vrGenCompRetencion = vrGenCompRetencion;
        this.importeRedondeo = importeRedondeo;
        this.impoteVueltoRed = impoteVueltoRed;
        this.importeComplVuelto = importeComplVuelto;
        this.montoTotal = montoTotal;
        this.montoTotalReal = montoTotalReal;
        this.fecha = fecha;
        this.fecCre = fecCre;
        this.fecConfirm = fecConfirm;
        this.fecReversion = fecReversion;
        this.fecMod = fecMod;
        this.fefecha = fefecha;
        this.feCre = feCre;
        this.feConfirm = feConfirm;
        this.feReversion = feReversion;
        this.feMod = feMod;
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

    public int getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(int idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public long getIdControl() {
        return idControl;
    }

    public void setIdControl(long idControl) {
        this.idControl = idControl;
    }

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public String getUsrCajero() {
        return usrCajero;
    }

    public void setUsrCajero(String usrCajero) {
        this.usrCajero = usrCajero;
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

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public int getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(int tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public long getImporteEfectivo() {
        return importeEfectivo;
    }

    public void setImporteEfectivo(long importeEfectivo) {
        this.importeEfectivo = importeEfectivo;
    }

    public long getImporteDocum() {
        return importeDocum;
    }

    public void setImporteDocum(long importeDocum) {
        this.importeDocum = importeDocum;
    }

    public long getImporteVuelto() {
        return importeVuelto;
    }

    public void setImporteVuelto(long importeVuelto) {
        this.importeVuelto = importeVuelto;
    }

    public long getImporteConfirm() {
        return importeConfirm;
    }

    public void setImporteConfirm(long importeConfirm) {
        this.importeConfirm = importeConfirm;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdCtaRef() {
        return idCtaRef;
    }

    public void setIdCtaRef(int idCtaRef) {
        this.idCtaRef = idCtaRef;
    }

    public long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getNroCtaRef() {
        return nroCtaRef;
    }

    public void setNroCtaRef(String nroCtaRef) {
        this.nroCtaRef = nroCtaRef;
    }

    public String getVerificado() {
        return verificado;
    }

    public void setVerificado(String verificado) {
        this.verificado = verificado;
    }

    public String getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(String confirmado) {
        this.confirmado = confirmado;
    }

    public String getUsrConfirm() {
        return usrConfirm;
    }

    public void setUsrConfirm(String usrConfirm) {
        this.usrConfirm = usrConfirm;
    }

    public String getReversado() {
        return reversado;
    }

    public void setReversado(String reversado) {
        this.reversado = reversado;
    }

    public String getUsrReversion() {
        return usrReversion;
    }

    public void setUsrReversion(String usrReversion) {
        this.usrReversion = usrReversion;
    }

    public int getIdMovReversion() {
        return idMovReversion;
    }

    public void setIdMovReversion(int idMovReversion) {
        this.idMovReversion = idMovReversion;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getAtributo1() {
        return atributo1;
    }

    public void setAtributo1(String atributo1) {
        this.atributo1 = atributo1;
    }

    public String getAtributo2() {
        return atributo2;
    }

    public void setAtributo2(String atributo2) {
        this.atributo2 = atributo2;
    }

    public String getAtributo3() {
        return atributo3;
    }

    public void setAtributo3(String atributo3) {
        this.atributo3 = atributo3;
    }

    public String getAtributo4() {
        return atributo4;
    }

    public void setAtributo4(String atributo4) {
        this.atributo4 = atributo4;
    }

    public String getAtributo5() {
        return atributo5;
    }

    public void setAtributo5(String atributo5) {
        this.atributo5 = atributo5;
    }

    public String getVrGenCompRetencion() {
        return vrGenCompRetencion;
    }

    public void setVrGenCompRetencion(String vrGenCompRetencion) {
        this.vrGenCompRetencion = vrGenCompRetencion;
    }

    public long getImporteRedondeo() {
        return importeRedondeo;
    }

    public void setImporteRedondeo(long importeRedondeo) {
        this.importeRedondeo = importeRedondeo;
    }

    public long getImpoteVueltoRed() {
        return impoteVueltoRed;
    }

    public void setImpoteVueltoRed(long impoteVueltoRed) {
        this.impoteVueltoRed = impoteVueltoRed;
    }

    public long getImporteComplVuelto() {
        return importeComplVuelto;
    }

    public void setImporteComplVuelto(long importeComplVuelto) {
        this.importeComplVuelto = importeComplVuelto;
    }

    public long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(long montoTotal) {
        this.montoTotal = montoTotal;
    }

    public long getMontoTotalReal() {
        return montoTotalReal;
    }

    public void setMontoTotalReal(long montoTotalReal) {
        this.montoTotalReal = montoTotalReal;
    }

    public Timestamp getfecha() {
        return fecha;
    }

    public void setfecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getFecConfirm() {
        return fecConfirm;
    }

    public void setFecConfirm(Timestamp fecConfirm) {
        this.fecConfirm = fecConfirm;
    }

    public Timestamp getFecReversion() {
        return fecReversion;
    }

    public void setFecReversion(Timestamp fecReversion) {
        this.fecReversion = fecReversion;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public String getFefecha() {
        return fefecha;
    }

    public void setFefecha(String fefecha) {
        this.fefecha = fefecha;
    }

    public String getFeCre() {
        return feCre;
    }

    public void setFeCre(String feCre) {
        this.feCre = feCre;
    }

    public String getFeConfirm() {
        return feConfirm;
    }

    public void setFeConfirm(String feConfirm) {
        this.feConfirm = feConfirm;
    }

    public String getFeReversion() {
        return feReversion;
    }

    public void setFeReversion(String feReversion) {
        this.feReversion = feReversion;
    }

    public String getFeMod() {
        return feMod;
    }

    public void setFeMod(String feMod) {
        this.feMod = feMod;
    }

}
