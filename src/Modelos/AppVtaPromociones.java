package Modelos;

public class AppVtaPromociones {

    int IDENTIFICADOR;
    int ID_SITIO;
    int ID_CAJA;
    int ULT_NUMERO;
    String FECHA_DESDE;
    String FECHA_HASTA;
    String NOMBRE;
    String DESCRIPCION;
    String USR_CRE;
    String FEC_CRE;
    String USR_MOD;
    String FEC_MOD;
    int MONTO;

    public AppVtaPromociones() {

    }

    public AppVtaPromociones(int IDENTIFICADOR, int ID_SITIO, int ID_CAJA, int ULT_NUMERO, String FECHA_DESDE, String FECHA_HASTA, String NOMBRE, String DESCRIPCION, String USR_CRE, String FEC_CRE, String USR_MOD, String FEC_MOD, int MONTO) {
        this.IDENTIFICADOR = IDENTIFICADOR;
        this.ID_SITIO = ID_SITIO;
        this.ID_CAJA = ID_CAJA;
        this.ULT_NUMERO = ULT_NUMERO;
        this.FECHA_DESDE = FECHA_DESDE;
        this.FECHA_HASTA = FECHA_HASTA;
        this.NOMBRE = NOMBRE;
        this.DESCRIPCION = DESCRIPCION;
        this.USR_CRE = USR_CRE;
        this.FEC_CRE = FEC_CRE;
        this.USR_MOD = USR_MOD;
        this.FEC_MOD = FEC_MOD;
        this.MONTO = MONTO;
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

    public int getID_CAJA() {
        return ID_CAJA;
    }

    public void setID_CAJA(int ID_CAJA) {
        this.ID_CAJA = ID_CAJA;
    }

    public int getULT_NUMERO() {
        return ULT_NUMERO;
    }

    public void setULT_NUMERO(int ULT_NUMERO) {
        this.ULT_NUMERO = ULT_NUMERO;
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

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getUSR_CRE() {
        return USR_CRE;
    }

    public void setUSR_CRE(String USR_CRE) {
        this.USR_CRE = USR_CRE;
    }

    public String getFEC_CRE() {
        return FEC_CRE;
    }

    public void setFEC_CRE(String FEC_CRE) {
        this.FEC_CRE = FEC_CRE;
    }

    public String getUSR_MOD() {
        return USR_MOD;
    }

    public void setUSR_MOD(String USR_MOD) {
        this.USR_MOD = USR_MOD;
    }

    public String getFEC_MOD() {
        return FEC_MOD;
    }

    public void setFEC_MOD(String FEC_MOD) {
        this.FEC_MOD = FEC_MOD;
    }

    public int getMONTO() {
        return MONTO;
    }

    public void setMONTO(int MONTO) {
        this.MONTO = MONTO;
    }

}
