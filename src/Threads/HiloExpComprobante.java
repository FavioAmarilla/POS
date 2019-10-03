package Threads;

import Controladores.ContExportarTransacciones;

public class HiloExpComprobante {

    ContExportarTransacciones transacciones = new ContExportarTransacciones();
    String condicion;

    public HiloExpComprobante() {

    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public void exportar() {
        transacciones.exportarComprobantes(condicion);
    }

}
