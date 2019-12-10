
package Modelos;

import java.sql.Timestamp;


public class FndUnidadesOperat {

int IDENTIFICADOR;
  int ID_EMPRESA;
  String DESCRIPCION;
  String NUM_ASNTOS_MANUAL;
  String USR_CRE;
  Timestamp FEC_CRE;
  String ABREVIATURA;
  int ID_SEC_ASIENTOS;
  int ID_NUM_ASIENTOS;
  int ID_NUM_CONTROL;
  int ID_SEC_ITEMS_AST;
 String USR_MOD;
  Timestamp FEC_MOD;
  String DIRECCION;
  String TELEFONOS;
  String NUMERO_DOCUMENTO;
  String CIUDAD;
  String PAIS;
  int  NUMERO_ORDEN;
  String ES_PRINCIPAL;
  int ID_GRUPO_LISTA;
  int ID_MONEDA;
  String SEPARADOR_MILES;

    public FndUnidadesOperat() {
    }

    public FndUnidadesOperat(int IDENTIFICADOR, int ID_EMPRESA, String DESCRIPCION, String NUM_ASNTOS_MANUAL, String USR_CRE, Timestamp FEC_CRE, String ABREVIATURA, int ID_SEC_ASIENTOS, int ID_NUM_ASIENTOS, int ID_NUM_CONTROL, int ID_SEC_ITEMS_AST, String USR_MOD, Timestamp FEC_MOD, String DIRECCION, String TELEFONOS, String NUMERO_DOCUMENTO, String CIUDAD, String PAIS, int NUMERO_ORDEN, String ES_PRINCIPAL, int ID_GRUPO_LISTA, int ID_MONEDA, String SEPARADOR_MILES) {
        this.IDENTIFICADOR = IDENTIFICADOR;
        this.ID_EMPRESA = ID_EMPRESA;
        this.DESCRIPCION = DESCRIPCION;
        this.NUM_ASNTOS_MANUAL = NUM_ASNTOS_MANUAL;
        this.USR_CRE = USR_CRE;
        this.FEC_CRE = FEC_CRE;
        this.ABREVIATURA = ABREVIATURA;
        this.ID_SEC_ASIENTOS = ID_SEC_ASIENTOS;
        this.ID_NUM_ASIENTOS = ID_NUM_ASIENTOS;
        this.ID_NUM_CONTROL = ID_NUM_CONTROL;
        this.ID_SEC_ITEMS_AST = ID_SEC_ITEMS_AST;
        this.USR_MOD = USR_MOD;
        this.FEC_MOD = FEC_MOD;
        this.DIRECCION = DIRECCION;
        this.TELEFONOS = TELEFONOS;
        this.NUMERO_DOCUMENTO = NUMERO_DOCUMENTO;
        this.CIUDAD = CIUDAD;
        this.PAIS = PAIS;
        this.NUMERO_ORDEN = NUMERO_ORDEN;
        this.ES_PRINCIPAL = ES_PRINCIPAL;
        this.ID_GRUPO_LISTA = ID_GRUPO_LISTA;
        this.ID_MONEDA = ID_MONEDA;
        this.SEPARADOR_MILES = SEPARADOR_MILES;
    }

    public int getIDENTIFICADOR() {
        return IDENTIFICADOR;
    }

    public void setIDENTIFICADOR(int IDENTIFICADOR) {
        this.IDENTIFICADOR = IDENTIFICADOR;
    }

    public int getID_EMPRESA() {
        return ID_EMPRESA;
    }

    public void setID_EMPRESA(int ID_EMPRESA) {
        this.ID_EMPRESA = ID_EMPRESA;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getNUM_ASNTOS_MANUAL() {
        return NUM_ASNTOS_MANUAL;
    }

    public void setNUM_ASNTOS_MANUAL(String NUM_ASNTOS_MANUAL) {
        this.NUM_ASNTOS_MANUAL = NUM_ASNTOS_MANUAL;
    }

    public String getUSR_CRE() {
        return USR_CRE;
    }

    public void setUSR_CRE(String USR_CRE) {
        this.USR_CRE = USR_CRE;
    }

    public Timestamp getFEC_CRE() {
        return FEC_CRE;
    }

    public void setFEC_CRE(Timestamp FEC_CRE) {
        this.FEC_CRE = FEC_CRE;
    }

    public String getABREVIATURA() {
        return ABREVIATURA;
    }

    public void setABREVIATURA(String ABREVIATURA) {
        this.ABREVIATURA = ABREVIATURA;
    }

    public int getID_SEC_ASIENTOS() {
        return ID_SEC_ASIENTOS;
    }

    public void setID_SEC_ASIENTOS(int ID_SEC_ASIENTOS) {
        this.ID_SEC_ASIENTOS = ID_SEC_ASIENTOS;
    }

    public int getID_NUM_ASIENTOS() {
        return ID_NUM_ASIENTOS;
    }

    public void setID_NUM_ASIENTOS(int ID_NUM_ASIENTOS) {
        this.ID_NUM_ASIENTOS = ID_NUM_ASIENTOS;
    }

    public int getID_NUM_CONTROL() {
        return ID_NUM_CONTROL;
    }

    public void setID_NUM_CONTROL(int ID_NUM_CONTROL) {
        this.ID_NUM_CONTROL = ID_NUM_CONTROL;
    }

    public int getID_SEC_ITEMS_AST() {
        return ID_SEC_ITEMS_AST;
    }

    public void setID_SEC_ITEMS_AST(int ID_SEC_ITEMS_AST) {
        this.ID_SEC_ITEMS_AST = ID_SEC_ITEMS_AST;
    }

    public String getUSR_MOD() {
        return USR_MOD;
    }

    public void setUSR_MOD(String USR_MOD) {
        this.USR_MOD = USR_MOD;
    }

    public Timestamp getFEC_MOD() {
        return FEC_MOD;
    }

    public void setFEC_MOD(Timestamp FEC_MOD) {
        this.FEC_MOD = FEC_MOD;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getTELEFONOS() {
        return TELEFONOS;
    }

    public void setTELEFONOS(String TELEFONOS) {
        this.TELEFONOS = TELEFONOS;
    }

    public String getNUMERO_DOCUMENTO() {
        return NUMERO_DOCUMENTO;
    }

    public void setNUMERO_DOCUMENTO(String NUMERO_DOCUMENTO) {
        this.NUMERO_DOCUMENTO = NUMERO_DOCUMENTO;
    }

    public String getCIUDAD() {
        return CIUDAD;
    }

    public void setCIUDAD(String CIUDAD) {
        this.CIUDAD = CIUDAD;
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }

    public int getNUMERO_ORDEN() {
        return NUMERO_ORDEN;
    }

    public void setNUMERO_ORDEN(int NUMERO_ORDEN) {
        this.NUMERO_ORDEN = NUMERO_ORDEN;
    }

    public String getES_PRINCIPAL() {
        return ES_PRINCIPAL;
    }

    public void setES_PRINCIPAL(String ES_PRINCIPAL) {
        this.ES_PRINCIPAL = ES_PRINCIPAL;
    }

    public int getID_GRUPO_LISTA() {
        return ID_GRUPO_LISTA;
    }

    public void setID_GRUPO_LISTA(int ID_GRUPO_LISTA) {
        this.ID_GRUPO_LISTA = ID_GRUPO_LISTA;
    }

    public int getID_MONEDA() {
        return ID_MONEDA;
    }

    public void setID_MONEDA(int ID_MONEDA) {
        this.ID_MONEDA = ID_MONEDA;
    }

    public String getSEPARADOR_MILES() {
        return SEPARADOR_MILES;
    }

    public void setSEPARADOR_MILES(String SEPARADOR_MILES) {
        this.SEPARADOR_MILES = SEPARADOR_MILES;
    }

    
}
