package Threads;

import Controladores.ContFndUsuarios;
import Controladores.ContParamAplicacion;
import Utils.Utilidades;
import Modelos.StkItemsMvStock;
import Modelos.StkMovimStock;
import Vistas.FrmPosCobros;
import Controladores.ContStkMovimStock;
import Controladores.ContVtaComprobantes;
import Modelos.StkParametros;
import Utils.ControlMensajes;
import java.util.ArrayList;

public class HiloMovimStock extends Thread {

    StkItemsMvStock dtsIt = new StkItemsMvStock();
    StkMovimStock dtsMv = new StkMovimStock();
    ContStkMovimStock funcMv = new ContStkMovimStock();
    ContVtaComprobantes venta = new ContVtaComprobantes();
    StkParametros stkParametros = new StkParametros();

    public ArrayList<StkItemsMvStock> listaProductos = null;

    public HiloMovimStock() {

    }

    public void setTablaProductos(ArrayList<StkItemsMvStock> values) {
        this.listaProductos = values;
    }
    
    public void setStkParametros(StkParametros value) {
        this.stkParametros = value;
    }

    //movimiento de stock
    private long idMovimiento;

    private boolean mvStock() {
        System.out.println("stkParametros.getId_trans_vtas(): " + stkParametros.getId_trans_vtas());
        System.out.println("stkParametros.getId_almaven_vtas(): " + stkParametros.getId_almaven_vtas());
        dtsMv.setIdentificador(idMovimiento);
        dtsMv.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsMv.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsMv.setVrSituacion("00");
        dtsMv.setIdSitio(ContParamAplicacion.SUC_IDENTIFICADOR);
        dtsMv.setUsuario(ContFndUsuarios.USR_NOMBRE);
        dtsMv.setUsrCre(ContFndUsuarios.USR_NOMBRE);
        dtsMv.setNumero(FrmPosCobros.nroComprobante);
        dtsMv.setVrOrigenTrans("VE");
        dtsMv.setIdTransaccion(stkParametros.getId_trans_vtas());
        dtsMv.setIdComprobante(FrmPosCobros.idVenta);
        dtsMv.setNroComprobante(FrmPosCobros.nroComprobante);
        dtsMv.setIdCtaRef("0");
        dtsMv.setNroReferencia(FrmPosCobros.nroComprobante);
        dtsMv.setReversado("N");

        return funcMv.stk_movim_stock(dtsMv);
    }

    //items de movimiento
    private boolean itemStock() {
        boolean enviado = false;

        try {
            dtsIt.setIdMovimiento(idMovimiento);
            dtsIt.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
            dtsIt.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
            dtsIt.setIdAlmacen(stkParametros.getId_almaven_vtas());
            dtsIt.setUsrCre(ContFndUsuarios.USR_NOMBRE);
            dtsIt.setFecCre(funcMv.sysdate());
            dtsIt.setFecha(funcMv.fecha());

            enviado = funcMv.stk_items_mv_stock(dtsIt, listaProductos);

            return enviado;
        } catch (NumberFormatException e) {
            ControlMensajes.informacion("ERROR EN THREAD DE MOVIMIENTO DE STOCK: " + e.getMessage(), "ERROR THREAD");
            return false;
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("<INICIO DE PROCESO DE MV. STOCK>");

            idMovimiento = funcMv.idMovimiento("L");
            if (mvStock()) {
                venta.stkComp(idMovimiento, FrmPosCobros.idVenta);
                if (itemStock()) {
                    System.out.println("<FIN DE PROCESO DE MV. STOCK>");

                    if (Utilidades.getPing()) {
                        System.out.println("<INICIO EXPORTAR TRANSACCION>");
                        HiloExpComprobante hFactura = new HiloExpComprobante();
                        hFactura.setCondicion("WHERE IDENTIFICADOR = " + FrmPosCobros.idVenta + "");
                        hFactura.exportar();
                        System.out.println("</FIN EXPORTAR TRANSACCION>");
                    }

                } else {
                    System.out.println("\tItems de movimiento no registrados");
                }
            } else {
                System.out.println("\tMovimiento de stock no registrado");
            }

        } catch (Exception e) {
            ControlMensajes.informacion("ERROR EN THREAD(RUN) DE MOVIMIENTO DE STOCK: " + e.getMessage(), "ERROR THREAD");
        }

    }
}
