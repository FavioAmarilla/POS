/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;

/**
 *
 * @author favio
 */
public class StkParametros implements Serializable {

    int identificador;
    int id_almaven_vtas;
    int id_trans_vtas;

    public StkParametros() {

    }

    public StkParametros(int identificador, int id_almaven_vtas, int id_trans_vtas) {
        this.identificador = identificador;
        this.id_almaven_vtas = id_almaven_vtas;
        this.id_trans_vtas = id_trans_vtas;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getId_almaven_vtas() {
        return id_almaven_vtas;
    }

    public void setId_almaven_vtas(int id_almaven_vtas) {
        this.id_almaven_vtas = id_almaven_vtas;
    }

    public int getId_trans_vtas() {
        return id_trans_vtas;
    }

    public void setId_trans_vtas(int id_trans_vtas) {
        this.id_trans_vtas = id_trans_vtas;
    }

}
