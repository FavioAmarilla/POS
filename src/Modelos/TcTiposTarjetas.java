package Modelos;

import java.sql.Timestamp;

public class TcTiposTarjetas {
    String usrCre, usrMod, descripcion, codigo, codigoPos, activo;
    Timestamp fecCre, fecMod;
    int porcComision;
            
    //PROCESADORAS
    int idProcesadora;
    String codigoComercio, vrFrecuencia;
    String direccion, telefonos;
    int porcComisionTc, porcIvaComisTc;
    int porcRetenIvaTc, porcRetenRentaTc, porcComisionTd;
    int porcIvaComisTd, porcRetenIvaTd, porcRetenRentaTd;
    
    //TIPOS DE TARJETA
    int idTipoTarjeta, idMArca;
    String vrTipoTarjeta;
    int idEntidad, porcIvaComis, porcRetenIva, porcRetenRenta;

    public TcTiposTarjetas() {
    }

    public TcTiposTarjetas(String usrCre, String usrMod, String descripcion, String codigo, String codigoPos, String activo, Timestamp fecCre, Timestamp fecMod, int porcComision, int idProcesadora, String codigoComercio, String vrFrecuencia, String direccion, String telefonos, int porcComisionTc, int porcIvaComisTc, int porcRetenIvaTc, int porcRetenRentaTc, int porcComisionTd, int porcIvaComisTd, int porcRetenIvaTd, int porcRetenRentaTd, int idTipoTarjeta, int idMArca, String vrTipoTarjeta, int idEntidad, int porcIvaComis, int porcRetenIva, int porcRetenRenta) {
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.codigoPos = codigoPos;
        this.activo = activo;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
        this.porcComision = porcComision;
        this.idProcesadora = idProcesadora;
        this.codigoComercio = codigoComercio;
        this.vrFrecuencia = vrFrecuencia;
        this.direccion = direccion;
        this.telefonos = telefonos;
        this.porcComisionTc = porcComisionTc;
        this.porcIvaComisTc = porcIvaComisTc;
        this.porcRetenIvaTc = porcRetenIvaTc;
        this.porcRetenRentaTc = porcRetenRentaTc;
        this.porcComisionTd = porcComisionTd;
        this.porcIvaComisTd = porcIvaComisTd;
        this.porcRetenIvaTd = porcRetenIvaTd;
        this.porcRetenRentaTd = porcRetenRentaTd;
        this.idTipoTarjeta = idTipoTarjeta;
        this.idMArca = idMArca;
        this.vrTipoTarjeta = vrTipoTarjeta;
        this.idEntidad = idEntidad;
        this.porcIvaComis = porcIvaComis;
        this.porcRetenIva = porcRetenIva;
        this.porcRetenRenta = porcRetenRenta;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(String codigoPos) {
        this.codigoPos = codigoPos;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
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

    public int getPorcComision() {
        return porcComision;
    }

    public void setPorcComision(int porcComision) {
        this.porcComision = porcComision;
    }

    public int getIdProcesadora() {
        return idProcesadora;
    }

    public void setIdProcesadora(int idProcesadora) {
        this.idProcesadora = idProcesadora;
    }

    public String getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(String codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getVrFrecuencia() {
        return vrFrecuencia;
    }

    public void setVrFrecuencia(String vrFrecuencia) {
        this.vrFrecuencia = vrFrecuencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public int getPorcComisionTc() {
        return porcComisionTc;
    }

    public void setPorcComisionTc(int porcComisionTc) {
        this.porcComisionTc = porcComisionTc;
    }

    public int getPorcIvaComisTc() {
        return porcIvaComisTc;
    }

    public void setPorcIvaComisTc(int porcIvaComisTc) {
        this.porcIvaComisTc = porcIvaComisTc;
    }

    public int getPorcRetenIvaTc() {
        return porcRetenIvaTc;
    }

    public void setPorcRetenIvaTc(int porcRetenIvaTc) {
        this.porcRetenIvaTc = porcRetenIvaTc;
    }

    public int getPorcRetenRentaTc() {
        return porcRetenRentaTc;
    }

    public void setPorcRetenRentaTc(int porcRetenRentaTc) {
        this.porcRetenRentaTc = porcRetenRentaTc;
    }

    public int getPorcComisionTd() {
        return porcComisionTd;
    }

    public void setPorcComisionTd(int porcComisionTd) {
        this.porcComisionTd = porcComisionTd;
    }

    public int getPorcIvaComisTd() {
        return porcIvaComisTd;
    }

    public void setPorcIvaComisTd(int porcIvaComisTd) {
        this.porcIvaComisTd = porcIvaComisTd;
    }

    public int getPorcRetenIvaTd() {
        return porcRetenIvaTd;
    }

    public void setPorcRetenIvaTd(int porcRetenIvaTd) {
        this.porcRetenIvaTd = porcRetenIvaTd;
    }

    public int getPorcRetenRentaTd() {
        return porcRetenRentaTd;
    }

    public void setPorcRetenRentaTd(int porcRetenRentaTd) {
        this.porcRetenRentaTd = porcRetenRentaTd;
    }

    public int getIdTipoTarjeta() {
        return idTipoTarjeta;
    }

    public void setIdTipoTarjeta(int idTipoTarjeta) {
        this.idTipoTarjeta = idTipoTarjeta;
    }

    public int getIdMArca() {
        return idMArca;
    }

    public void setIdMArca(int idMArca) {
        this.idMArca = idMArca;
    }

    public String getVrTipoTarjeta() {
        return vrTipoTarjeta;
    }

    public void setVrTipoTarjeta(String vrTipoTarjeta) {
        this.vrTipoTarjeta = vrTipoTarjeta;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public int getPorcIvaComis() {
        return porcIvaComis;
    }

    public void setPorcIvaComis(int porcIvaComis) {
        this.porcIvaComis = porcIvaComis;
    }

    public int getPorcRetenIva() {
        return porcRetenIva;
    }

    public void setPorcRetenIva(int porcRetenIva) {
        this.porcRetenIva = porcRetenIva;
    }

    public int getPorcRetenRenta() {
        return porcRetenRenta;
    }

    public void setPorcRetenRenta(int porcRetenRenta) {
        this.porcRetenRenta = porcRetenRenta;
    }
    
    
}
