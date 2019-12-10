package Modelos;

import java.sql.Timestamp;

public class FndPerfiles {
    int idenPerfil;
    String nombrePerfil;
    String superUsuario;
    String descripcion;
    String usrCre, usrMod;
    Timestamp fecCre, fecMod;
    
    public FndPerfiles(){
    
    }

    public FndPerfiles(int idenPerfil, String nombrePerfil, String superUsuario, String descripcion, String usrCre, String usrMod, Timestamp fecCre, Timestamp fecMod) {
        this.idenPerfil = idenPerfil;
        this.nombrePerfil = nombrePerfil;
        this.superUsuario = superUsuario;
        this.descripcion = descripcion;
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
    }

    public int getIdenPerfil() {
        return idenPerfil;
    }

    public void setIdenPerfil(int idenPerfil) {
        this.idenPerfil = idenPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getSuperUsuario() {
        return superUsuario;
    }

    public void setSuperUsuario(String superUsuario) {
        this.superUsuario = superUsuario;
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
