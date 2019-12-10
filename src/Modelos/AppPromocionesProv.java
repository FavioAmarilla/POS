package Modelos;

public class AppPromocionesProv {

    int IDENTIFICADOR;
    int ID_SITIO;
    int ID_PROVEEDOR;
    String FECHA_DESDE;
    String FECHA_HASTA;
    String NOMBRE;
    String FEC_CRE;
    String USR_CRE;
    String FEC_MOD;
    String USR_MOD;

    public AppPromocionesProv() {

    }

    public AppPromocionesProv(int IDENTIFICADOR, int ID_SITIO, int ID_PROVEEDOR, String FECHA_DESDE, String FECHA_HASTA, String NOMBRE, String FEC_CRE, String USR_CRE, String FEC_MOD, String USR_MOD) {
        this.IDENTIFICADOR = IDENTIFICADOR;
        this.ID_SITIO = ID_SITIO;
        this.ID_PROVEEDOR = ID_PROVEEDOR;
        this.FECHA_DESDE = FECHA_DESDE;
        this.FECHA_HASTA = FECHA_HASTA;
        this.NOMBRE = NOMBRE;
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

    public int getID_SITIO() {
        return ID_SITIO;
    }

    public void setID_SITIO(int ID_SITIO) {
        this.ID_SITIO = ID_SITIO;
    }

    public int getID_PROVEEDOR() {
        return ID_PROVEEDOR;
    }

    public void setID_PROVEEDOR(int ID_PROVEEDOR) {
        this.ID_PROVEEDOR = ID_PROVEEDOR;
    }

    public String getFECHA_DESDE() {
        return FECHA_DESDE;
    }

    public void setFECHA_DESDE(String FECHA_DESDE) {
        this.FECHA_DESDE = FECHA_DESDE;
    }

    public String getFECHA_HASTA() {
        return FECHA_HASTA;
    }

    public void setFECHA_HASTA(String FECHA_HASTA) {
        this.FECHA_HASTA = FECHA_HASTA;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
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
