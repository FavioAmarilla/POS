package Modelos;

public class ParamUsuario {
    int identificador, idPerfil, idCajero;
    String nombre, nombreCompleto;
    String nombrePerfil;

    public ParamUsuario() {
    }

    public ParamUsuario(int identificador, int idPerfil, int idCajero, String nombre, String nombreCompleto, String nombrePerfil) {
        this.identificador = identificador;
        this.idPerfil = idPerfil;
        this.idCajero = idCajero;
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.nombrePerfil = nombrePerfil;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }
    
    
}
