package Modelos;

public class AppItemsPromProv {

    int IDENTIFICADOR;
    int ID_PROMOCION;
    int ID_PRODUCTO;
    int PRECIO_VENTA;
    int COSTO_UNITARIO;
    int CANTIDAD;
    String FEC_CRE;
    String USR_CRE;
    String FEC_MOD;
    String USR_MOD;

    public AppItemsPromProv() {

    }

    public AppItemsPromProv(int IDENTIFICADOR, int ID_PROMOCION, int ID_PRODUCTO, int PRECIO_VENTA, int COSTO_UNITARIO, int CANTIDAD, String FEC_CRE, String USR_CRE, String FEC_MOD, String USR_MOD) {
        this.IDENTIFICADOR = IDENTIFICADOR;
        this.ID_PROMOCION = ID_PROMOCION;
        this.ID_PRODUCTO = ID_PRODUCTO;
        this.PRECIO_VENTA = PRECIO_VENTA;
        this.COSTO_UNITARIO = COSTO_UNITARIO;
        this.CANTIDAD = CANTIDAD;
        this.FEC_CRE = FEC_CRE;
        this.USR_CRE = USR_CRE;
        this.FEC_MOD = FEC_MOD;
        this.USR_MOD = USR_MOD;
    }

    public int getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }

    public void setIDENTIFICADOR(int IDENTIFICADOR) {
        this.IDENTIFICADOR = IDENTIFICADOR;
    }

    public int getID_PROMOCION() {
        return ID_PROMOCION;
    }

    public void setID_PROMOCION(int ID_PROMOCION) {
        this.ID_PROMOCION = ID_PROMOCION;
    }

    public int getID_PRODUCTO() {
        return ID_PRODUCTO;
    }

    public void setID_PRODUCTO(int ID_PRODUCTO) {
        this.ID_PRODUCTO = ID_PRODUCTO;
    }

    public int getPRECIO_VENTA() {
        return PRECIO_VENTA;
    }

    public void setPRECIO_VENTA(int PRECIO_VENTA) {
        this.PRECIO_VENTA = PRECIO_VENTA;
    }

    public int getCOSTO_UNITARIO() {
        return COSTO_UNITARIO;
    }

    public void setCOSTO_UNITARIO(int COSTO_UNITARIO) {
        this.COSTO_UNITARIO = COSTO_UNITARIO;
    }

    public int getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(int CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public String getFEC_CRE() {
        return FEC_CRE;
    }

    public void setFEC_CRE(String FEC_CRE) {
        this.FEC_CRE = FEC_CRE;
    }

    public String getUSR_CRE() {
        return USR_CRE;
    }

    public void setUSR_CRE(String USR_CRE) {
        this.USR_CRE = USR_CRE;
    }

    public String getFEC_MOD() {
        return FEC_MOD;
    }

    public void setFEC_MOD(String FEC_MOD) {
        this.FEC_MOD = FEC_MOD;
    }

    public String getUSR_MOD() {
        return USR_MOD;
    }

    public void setUSR_MOD(String USR_MOD) {
        this.USR_MOD = USR_MOD;
    }

}
