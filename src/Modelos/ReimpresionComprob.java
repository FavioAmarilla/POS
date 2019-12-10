package Modelos;

public class ReimpresionComprob {
    int id_cliente;
    String ruc;
    String nombre;
    String condicion;

    public ReimpresionComprob(){
    }

    public ReimpresionComprob(int id_cliente, String ruc, String nombre, String condicion) {
        this.id_cliente = id_cliente;
        this.ruc = ruc;
        this.nombre = nombre;
        this.condicion = condicion;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

   
    
    
}
