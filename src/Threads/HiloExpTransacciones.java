package Threads;

import Controladores.ContTcArqueoCaja;
import Controladores.ContTcControlCaja;
import Controladores.ContVtaClientes;
import Controladores.ContTcExtrEfectivoCaja;
import Utils.ControlMensajes;

public class HiloExpTransacciones extends Thread {

    HiloExpComprobante hiloFa;
   

    ContTcExtrEfectivoCaja extraccion = new ContTcExtrEfectivoCaja();
    ContTcControlCaja ctrlCaja = new ContTcControlCaja();
    ContTcArqueoCaja arqueo = new ContTcArqueoCaja();
    ContVtaClientes cliente = new ContVtaClientes();

    public HiloExpTransacciones() {
    }

    @Override
    public void run() {
        hiloFa = new HiloExpComprobante();
        hiloFa.setCondicion("WHERE FECHA_ENVIO_SRV IS NULL");
        hiloFa.exportar();
        
        if (arqueo.expArqueos("")) {
            if (ctrlCaja.expControles("")) {
                if (extraccion.expExtraccionEfectivo(ctrlCaja.idApertura())) {
                    if (cliente.Exportar()) {
                        System.out.println("\t---TRANSACCIONES EXPORTADAS---");
                    } else {
                        ControlMensajes.error("CLIENTES NO EXPORTADOS", "EXPORTAR REFERENCIAS");
                    }
                } else {
                    ControlMensajes.error("EXTRACCIONES DE CAJA NO EXPORTADAS", "EXPORTAR REFERENCIAS");
                }
            } else {
                ControlMensajes.error("CONTROLES DE CAJA NO EXPORTADOS", "EXPORTAR REFERENCIAS");
            }
        } else {
            ControlMensajes.error("ARQUEOS DE CAJA NO EXPORTADOS", "EXPORTAR REFERENCIAS");
        }
    }
}
