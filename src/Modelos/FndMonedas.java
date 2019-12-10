package Modelos;

import java.sql.Timestamp;

public class FndMonedas {

    int identificador;
    String codigo, descripcion, usrCre, idEmpresa, idUnnidad, mascara, esPrincipal;
    Timestamp fecCre;
    
    //DENOMINACIONES
    int idDenominacion, idMoneda, valor, decimales;

    public FndMonedas() {
    }

    public FndMonedas(int identificador, String codigo, String descripcion, String usrCre, String idEmpresa, String idUnnidad, String mascara, String esPrincipal, Timestamp fecCre, int idDenominacion, int idMoneda, int valor, int decimales) {
        this.identificador = identificador;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.usrCre = usrCre;
        this.idEmpresa = idEmpresa;
        this.idUnnidad = idUnnidad;
        this.mascara = mascara;
        this.esPrincipal = esPrincipal;
        this.fecCre = fecCre;
        this.idDenominacion = idDenominacion;
        this.idMoneda = idMoneda;
        this.valor = valor;
        this.decimales = decimales;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdUnnidad() {
        return idUnnidad;
    }

    public void setIdUnnidad(String idUnnidad) {
        this.idUnnidad = idUnnidad;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(String esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public int getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getDecimales() {
        return decimales;
    }

    public void setDecimales(int decimales) {
        this.decimales = decimales;
    }
    
   

}
