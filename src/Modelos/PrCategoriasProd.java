package Modelos;

import java.sql.Timestamp;

public class PrCategoriasProd {
    int identificador;
    String descripcion, usrCre, usrMod, abreviatura;
    Timestamp fecCre, fecMod;
    String codigoReporte, vrFormaAdmin, calcDigVerif;
    
    public PrCategoriasProd(){
    
    }

    public PrCategoriasProd(int identificador, String descripcion, String usrCre, String usrMod, String abreviatura, Timestamp fecCre, Timestamp fecMod, String codigoReporte, String vrFormaAdmin, String calcDigVerif) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.abreviatura = abreviatura;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
        this.codigoReporte = codigoReporte;
        this.vrFormaAdmin = vrFormaAdmin;
        this.calcDigVerif = calcDigVerif;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
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

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    public String getCodigoReporte() {
        return codigoReporte;
    }

    public void setCodigoReporte(String codigoReporte) {
        this.codigoReporte = codigoReporte;
    }

    public String getVrFormaAdmin() {
        return vrFormaAdmin;
    }

    public void setVrFormaAdmin(String vrFormaAdmin) {
        this.vrFormaAdmin = vrFormaAdmin;
    }

    public String getCalcDigVerif() {
        return calcDigVerif;
    }

    public void setCalcDigVerif(String calcDigVerif) {
        this.calcDigVerif = calcDigVerif;
    }
    
    
}
