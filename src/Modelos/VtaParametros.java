/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author favio
 */
public class VtaParametros {
    int identificador;
    long id_cliente_pos;
    String nombre_clte_generico;
    
    public VtaParametros(){
        
    }

    public VtaParametros(int identificador, long id_cliente_pos, String nombre_clte_generico) {
        this.identificador = identificador;
        this.id_cliente_pos = id_cliente_pos;
        this.nombre_clte_generico = nombre_clte_generico;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public long getId_cliente_pos() {
        return id_cliente_pos;
    }

    public void setId_cliente_pos(int id_cliente_pos) {
        this.id_cliente_pos = id_cliente_pos;
    }

    public String getNombre_clte_generico() {
        return nombre_clte_generico;
    }

    public void setNombre_clte_generico(String nombre_clte_generico) {
        this.nombre_clte_generico = nombre_clte_generico;
    }
    
    
    
}
