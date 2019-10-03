package Modelos;

import java.sql.Timestamp;

public class PsCajeros {
    int idPsCajeros;
    String usuarioCaj, abrirCaja, usuarioExterno;
    int codigoCaj, idCategoria, idProcesoVenta;
    String usrCre, usrMod;
    Timestamp fecCre, fecMod;
    
    public PsCajeros(){
    
    }

    public PsCajeros(int idPsCajeros, String usuarioCaj, String abrirCaja, String usuarioExterno, int codigoCaj, int idCategoria, int idProcesoVenta, String usrCre, String usrMod, Timestamp fecCre, Timestamp fecMod) {
        this.idPsCajeros = idPsCajeros;
        this.usuarioCaj = usuarioCaj;
        this.abrirCaja = abrirCaja;
        this.usuarioExterno = usuarioExterno;
        this.codigoCaj = codigoCaj;
        this.idCategoria = idCategoria;
        this.idProcesoVenta = idProcesoVenta;
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
    }

    public int getIdPsCajeros() {
        return idPsCajeros;
    }

    public void setIdPsCajeros(int idPsCajeros) {
        this.idPsCajeros = idPsCajeros;
    }

    public String getUsuarioCaj() {
        return usuarioCaj;
    }

    public void setUsuarioCaj(String usuarioCaj) {
        this.usuarioCaj = usuarioCaj;
    }

    public String getAbrirCaja() {
        return abrirCaja;
    }

    public void setAbrirCaja(String abrirCaja) {
        this.abrirCaja = abrirCaja;
    }

    public String getUsuarioExterno() {
        return usuarioExterno;
    }

    public void setUsuarioExterno(String usuarioExterno) {
        this.usuarioExterno = usuarioExterno;
    }

    public int getCodigoCaj() {
        return codigoCaj;
    }

    public void setCodigoCaj(int codigoCaj) {
        this.codigoCaj = codigoCaj;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProcesoVenta() {
        return idProcesoVenta;
    }

    public void setIdProcesoVenta(int idProcesoVenta) {
        this.idProcesoVenta = idProcesoVenta;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
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
    
    
}
