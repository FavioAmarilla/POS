package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.TcCobrosCaja;
import Modelos.TcItemsCobro;
import Modelos.StkItemsMvStock;
import Modelos.StkMovimStock;
import Modelos.TcVouchers;
import Modelos.VtaComprobantes;
import Modelos.VtaItemsComprob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ContExportarTransacciones {

    private final String tituloMsj = ContExportarTransacciones.class.getName();
    SimpleDateFormat formatoFec;

    ContVtaComprobantes ventas = new ContVtaComprobantes();
    ContStkMovimStock movim = new ContStkMovimStock();
    StkMovimStock dtsMv = new StkMovimStock();

    public static int transaccionesEnviadas = 0;
    private String sql;
    int idVenta;
    int idItemVenta;
    int idMovim;
    int idItemMovim;

    public boolean exportarComprobantes(String condicion) {
        transaccionesEnviadas = 0;
        ConexionJdbc jdbc = new ConexionJdbc();
        ContStkMovimStock cont_movim = new ContStkMovimStock();
        long id_movimiento_server = cont_movim.idMovimiento("S");

        try {
            sql = "SELECT * FROM VTA_COMPROBANTES " + condicion;

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                VtaComprobantes dts = new VtaComprobantes();
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdTransaccion(rs.getInt("ID_TRANSACCION"));
                dts.setNumero(rs.getString("NUMERO"));
                dts.setfecha(rs.getTimestamp("FECHA"));
                dts.setEstado(rs.getString("ESTADO"));
                dts.setIdSitio(rs.getInt("ID_SITIO"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setMontoTotal(rs.getLong("MONTO_TOTAL"));
                dts.setIdTipoComprob(rs.getInt("ID_TIPO_COMPROB"));
                dts.setUsuario(rs.getString("USUARIO"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setIdCliente(rs.getLong("ID_CLIENTE"));
                dts.setMontoExento(rs.getLong("MONTO_EXENTO"));
                dts.setMontoGravado(rs.getLong("MONTO_GRAVADO"));
                dts.setMontoGravado5(rs.getLong("MONTO_GRAVADO5"));
                dts.setMontoGravado10(rs.getLong("MONTO_GRAVADO10"));
                dts.setMontoImpuesto(rs.getLong("MONTO_IMPUESTO"));
                dts.setMontoImpuesto5(rs.getLong("MONTO_IMPUESTO5"));
                dts.setMontoImpuesto10(rs.getLong("MONTO_IMPUESTO10"));
                dts.setMontoPagado(rs.getLong("MONTO_PAGADO"));
                dts.setIdFormaPago(rs.getInt("ID_FORMA_PAGO"));
                dts.setCantidadPagos(rs.getInt("CANTIDAD_PAGOS"));
                dts.setDiasPorPago(rs.getInt("DIAS_POR_PAGO"));
                dts.setUsrEmision(rs.getString("USR_EMISION"));
                dts.setFecEmision(rs.getTimestamp("FEC_EMISION"));
                dts.setUsrAnulacion(rs.getString("USR_ANULACION"));
                dts.setFecAnulacion(rs.getTimestamp("FEC_ANULACION"));
                dts.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                dts.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
                dts.setIdCaja(rs.getInt("ID_CAJA"));
                dts.setNroTicket(rs.getInt("NRO_TICKET"));
                dts.setNroRegImpos(rs.getString("NRO_REG_IMPOS"));
                dts.setIdRegistroCtrl(rs.getInt("ID_REGISTRO_CTRL"));
                dts.setIdPuntoEmision(rs.getInt("ID_PUNTO_EMISION"));
                dts.setIdAlmacen(rs.getInt("ID_ALMACEN"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setAtributo1(rs.getString("ATRIBUTO1"));
                dts.setAtributo2(rs.getString("ATRIBUTO2"));
                dts.setIdMovimStock(id_movimiento_server);
                dts.setIdProcesoVenta(rs.getInt("ID_PROCESO_VENTA"));
                dts.setIdControlCaja(rs.getLong("ID_CONTROL_CAJA"));
                dts.setMontoTotalReal(rs.getInt("MONTO_TOTAL_REAL"));
                dts.setMontoRedondeo(rs.getInt("MONTO_REDONDEO"));
                dts.setDescuento347(rs.getInt("DESCUENTO_347"));
                dts.setMontoTotalReal(rs.getInt("MONTO_TOTAL_REAL"));

                if (insertarCombrobante(dts)) {
                    if (exportarItemsComprobante(dts.getIdentificador())) {
                        if (ventas.fechaEnvServer(dts.getIdentificador())) {
                            if (exportarCobrosCaja(dts.getIdentificador())) {
                                if (exportarMovimStock("WHERE ID_COMPROBANTE = " + dts.getIdentificador() + "", id_movimiento_server)) {
                                    transaccionesEnviadas++;
                                } else {
                                    System.out.println("\tMv. de Stock de comprobante nro (" + dts.getNumero() + ") no exportado");
                                    return false;
                                }
                            } else {
                                System.out.println("\tCobro de comprobante nro (" + dts.getNumero() + ") no exportado");
                                return false;
                            }
                        } else {
                            System.out.println("\tFecha Env. Ser. no actualizado, comp. nro (" + dts.getNumero() + ") no exportado");
                            return false;
                        }
                    } else {
                        System.out.println("\tItems de comprobante nro (" + dts.getNumero() + ") no exportado");
                        return false;
                    }
                } else {
                    System.out.println("\tComprobante nro (" + dts.getNumero() + ") no exportado");
                    return false;
                }
            }

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("exportarComprobantes: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarCombrobante(VtaComprobantes dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_COMPROBANTES(IDENTIFICADOR, ID_TRANSACCION, NUMERO, FECHA, ESTADO,"
                    + "ID_SITIO, ID_MONEDA, USUARIO, USR_CRE, FEC_CRE, ID_CLIENTE, "
                    + "MONTO_EXENTO, MONTO_GRAVADO, MONTO_GRAVADO5, MONTO_GRAVADO10, "
                    + "MONTO_IMPUESTO, MONTO_IMPUESTO5, MONTO_IMPUESTO10, MONTO_PAGADO, "
                    + "RAZON_SOCIAL, NUMERO_DOCUMENTO, ID_CAJA, NRO_TICKET, NRO_REG_IMPOS,"
                    + "ID_REGISTRO_CTRL, ID_PUNTO_EMISION, ID_EMPRESA, ID_UNIDAD, ID_MOVIM_STOCK,"
                    + "ID_CONTROL_CAJA, MONTO_TOTAL, ID_ALMACEN, ID_TIPO_COMPROB, CANTIDAD_PAGOS, "
                    + "DIAS_POR_PAGO, USR_EMISION, FEC_EMISION, USR_ANULACION, FEC_ANULACION, "
                    + "ATRIBUTO1, ATRIBUTO2, DESCUENTO_347, MONTO_TOTAL_REAL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdTransaccion());
            pst.setString(3, dts.getNumero());
            pst.setTimestamp(4, dts.getfecha());
            pst.setString(5, dts.getEstado());
            pst.setInt(6, dts.getIdSitio());
            pst.setInt(7, dts.getIdMoneda());
            pst.setString(8, dts.getUsuario());
            pst.setString(9, dts.getUsrCre());
            pst.setTimestamp(10, dts.getFecCre());
            pst.setLong(11, dts.getIdCliente());
            pst.setDouble(12, dts.getMontoExento());
            pst.setDouble(13, dts.getMontoGravado());
            pst.setDouble(14, dts.getMontoGravado5());
            pst.setDouble(15, dts.getMontoGravado10());
            pst.setDouble(16, dts.getMontoImpuesto());
            pst.setDouble(17, dts.getMontoImpuesto5());
            pst.setDouble(18, dts.getMontoImpuesto10());
            pst.setDouble(19, dts.getMontoPagado());
            pst.setString(20, dts.getRazonSocial());
            pst.setString(21, dts.getNumeroDocumento());
            pst.setInt(22, dts.getIdCaja());
            pst.setInt(23, dts.getNroTicket());
            pst.setString(24, dts.getNroRegImpos());
            pst.setInt(25, dts.getIdRegistroCtrl());
            pst.setInt(26, dts.getIdPuntoEmision());
            pst.setInt(27, dts.getIdEmpresa());
            pst.setInt(28, dts.getIdUnidad());
            pst.setLong(29, dts.getIdMovimStock());
            pst.setLong(30, dts.getIdControlCaja());
            pst.setLong(31, dts.getMontoTotal());
            pst.setInt(32, dts.getIdAlmacen());
            pst.setInt(33, dts.getIdTipoComprob());
            pst.setInt(34, dts.getCantidadPagos());
            pst.setInt(35, dts.getDiasPorPago());
            pst.setString(36, dts.getUsrEmision());
            pst.setTimestamp(37, dts.getFecEmision());
            pst.setString(38, dts.getUsrAnulacion());
            pst.setTimestamp(39, dts.getFecAnulacion());
            pst.setString(40, dts.getAtributo1());
            pst.setString(41, dts.getAtributo2());
            pst.setLong(42, dts.getDescuento347());
            pst.setLong(43, dts.getMontoTotalReal());

            int x = pst.executeUpdate();
            System.out.println("\tComprobantes exportados: " + String.valueOf(x));

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insertarCombrobante: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean exportarItemsComprobante(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        ArrayList<VtaItemsComprob> listaItems = new ArrayList<>();

        try {
            sql = "SELECT IDENTIFICADOR,ID_COMPROBANTE, ID_EMPRESA, ID_UNIDAD,NUMERO_ITEM,"
                    + "VR_TIPO_LINEA,IMPORTE_ITEM,USR_CRE,FEC_CRE,ID_PRODUCTO,ID_UNIDAD_MEDIDA,"
                    + "CANTIDAD,CANTIDAD_UB,COSTO_UNITARIO,PRECIO_UNITARIO,PRECIO_UNIT_NETO,"
                    + "PRECIO_UNITARIO_UB, IMPUESTO_UNITARIO,IMPORTE_EXENTO,IMPORTE_GRAVADO,"
                    + "IMPORTE_IMPUESTO,ID_TIPO_IMPUESTO,IMPUESTO_INCLUIDO,PORCENTAJE_IMP,"
                    + "ID_PROVEEDOR,ID_SITIO_PROV,COALESCE(ID_ITEM_MV_STK,0) AS ID_ITEM_MV_STK "
                    + "FROM VTA_ITEMS_COMPROB WHERE ID_COMPROBANTE = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                VtaItemsComprob dts = new VtaItemsComprob();
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdComprobante(rs.getLong("ID_COMPROBANTE"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setNumeroItem(rs.getInt("NUMERO_ITEM"));
                dts.setVrTipoLinea(rs.getString("VR_TIPO_LINEA"));
                dts.setImporteItem(rs.getLong("IMPORTE_ITEM"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setIdProducto(rs.getInt("ID_PRODUCTO"));
                dts.setIdUnidadMedida(rs.getInt("ID_UNIDAD_MEDIDA"));
                dts.setCantidad(rs.getDouble("CANTIDAD"));
                dts.setCantidadUb(rs.getDouble("CANTIDAD_UB"));
                dts.setCostoUnitario(rs.getDouble("COSTO_UNITARIO"));
                dts.setPrecioUnitario(rs.getDouble("PRECIO_UNITARIO"));
                dts.setPrecioUnitNeto(rs.getDouble("PRECIO_UNIT_NETO"));
                dts.setPrecioUnitarioUb(rs.getDouble("PRECIO_UNITARIO_UB"));
                dts.setImpuestoUnitario(rs.getDouble("IMPUESTO_UNITARIO"));
                dts.setImporteExento(rs.getDouble("IMPORTE_EXENTO"));
                dts.setImporteGravado(rs.getDouble("IMPORTE_GRAVADO"));
                dts.setImporteImpuesto(rs.getDouble("IMPORTE_IMPUESTO"));
                dts.setIdTipoImpuesto(rs.getInt("ID_TIPO_IMPUESTO"));
                dts.setImpuestoIncluido(rs.getString("IMPUESTO_INCLUIDO"));
                dts.setPorcImpuesto(rs.getInt("PORCENTAJE_IMP"));
                dts.setIdProveedor(rs.getInt("ID_PROVEEDOR"));
                dts.setIdSitioProv(rs.getInt("ID_SITIO_PROV"));
                dts.setIdMovStkItem(rs.getInt("ID_ITEM_MV_STK"));

                listaItems.add(dts);
            }

            pst.close();
            return insertarItemsComprobante(listaItems);
        } catch (SQLException ex) {
            ControlMensajes.error("exportarItemsComprobante: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean insertarItemsComprobante(ArrayList<VtaItemsComprob> listaItems) {
        //se retoorna true en caso de que la lista no contenga items(comprobantes anulados)
        if (listaItems.size() <= 0) {
            return true;
        }

        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_ITEMS_COMPROB(IDENTIFICADOR, ID_COMPROBANTE, ID_EMPRESA, ID_UNIDAD,"
                    + "NUMERO_ITEM, VR_TIPO_LINEA, IMPORTE_ITEM, USR_CRE, FEC_CRE, ID_PRODUCTO,"
                    + "ID_UNIDAD_MEDIDA, CANTIDAD, CANTIDAD_UB, COSTO_UNITARIO, PRECIO_UNITARIO,"
                    + "PRECIO_UNIT_NETO, PRECIO_UNITARIO_UB, IMPUESTO_UNITARIO,IMPORTE_EXENTO, "
                    + "IMPORTE_GRAVADO, IMPORTE_IMPUESTO, ID_TIPO_IMPUESTO, IMPUESTO_INCLUIDO, "
                    + "PORCENTAJE_IMP, ID_PROVEEDOR, ID_SITIO_PROV, ID_ITEM_MV_STK) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            for (int i = 0; i < listaItems.size(); i++) {
                pst.setLong(1, listaItems.get(i).getIdentificador());
                pst.setLong(2, listaItems.get(i).getIdComprobante());
                pst.setLong(3, listaItems.get(i).getIdEmpresa());
                pst.setLong(4, listaItems.get(i).getIdUnidad());
                pst.setLong(5, listaItems.get(i).getNumeroItem());
                pst.setString(6, listaItems.get(i).getVrTipoLinea());
                pst.setDouble(7, listaItems.get(i).getImporteItem());
                pst.setString(8, listaItems.get(i).getUsrCre());
                pst.setTimestamp(9, listaItems.get(i).getFecCre());
                pst.setInt(10, listaItems.get(i).getIdProducto());
                pst.setInt(11, listaItems.get(i).getIdUnidadMedida());
                pst.setDouble(12, listaItems.get(i).getCantidad());
                pst.setDouble(13, listaItems.get(i).getCantidadUb());
                pst.setDouble(14, listaItems.get(i).getCostoUnitario());
                pst.setDouble(15, listaItems.get(i).getPrecioUnitario());
                pst.setDouble(16, listaItems.get(i).getPrecioUnitNeto());
                pst.setDouble(17, listaItems.get(i).getPrecioUnitarioUb());
                pst.setDouble(18, listaItems.get(i).getImpuestoUnitario());
                pst.setDouble(19, listaItems.get(i).getImporteExento());
                pst.setDouble(20, listaItems.get(i).getImporteGravado());
                pst.setDouble(21, listaItems.get(i).getImporteImpuesto());
                pst.setInt(22, listaItems.get(i).getIdTipoImpuesto());
                pst.setString(23, listaItems.get(i).getImpuestoIncluido());
                pst.setInt(24, listaItems.get(i).getPorcImpuesto());
                pst.setInt(25, listaItems.get(i).getIdProveedor());
                pst.setInt(26, listaItems.get(i).getIdSitioProv());
                pst.setInt(27, listaItems.get(i).getIdMovStkItem());

                pst.addBatch();
            }

            int[] x = pst.executeBatch();
            System.out.println("\tItems de comprobantes exportados: " + String.valueOf(x.length));

            pst.close();
            return x.length != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarItemsComprobante: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean exportarMovimStock(String condicion, long id_movimiento_server) {
        ConexionJdbc jdbc = new ConexionJdbc();

        try {
            sql = "SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, FECHA, VR_SITUACION, ID_SITIO, USUARIO, USR_CRE, "
                    + "FEC_CRE, NUMERO, VR_ORIGEN_TRANS, ID_TRANSACCION, ID_COMPROBANTE, NRO_COMPROBANTE, ID_CTA_REF,"
                    + "NRO_CTA_REF, REVERSADO "
                    + "FROM STK_MOVIM_STOCK " + condicion;

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dtsMv.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dtsMv.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsMv.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsMv.setfecha(rs.getTimestamp("FECHA"));
                dtsMv.setVrSituacion(rs.getString("VR_SITUACION"));
                dtsMv.setIdSitio(rs.getInt("ID_SITIO"));
                dtsMv.setUsuario(rs.getString("USUARIO"));
                dtsMv.setUsrCre(rs.getString("USR_CRE"));
                dtsMv.setfechaCre(rs.getTimestamp("FEC_CRE"));
                dtsMv.setNumero(rs.getString("NUMERO"));
                dtsMv.setVrOrigenTrans(rs.getString("VR_ORIGEN_TRANS"));
                dtsMv.setIdTransaccion(rs.getInt("ID_TRANSACCION"));
                dtsMv.setIdComprobante(rs.getLong("ID_COMPROBANTE"));
                dtsMv.setNroComprobante(rs.getString("NRO_COMPROBANTE"));
                dtsMv.setIdCtaRef(rs.getString("ID_CTA_REF"));
                dtsMv.setNroReferencia(rs.getString("NRO_CTA_REF"));
                dtsMv.setReversado(rs.getString("REVERSADO"));

                if (insertarMovimStock(dtsMv, id_movimiento_server)) {
                    if (exportarItemMovim(dtsMv.getIdentificador(), id_movimiento_server)) {
                        if (!movim.fechaEnvServer(dtsMv.getIdentificador())) {
                            return false;
                        }
                    } else {
                        System.out.println("\tItems de Movim. nro (" + dtsMv.getIdentificador() + ") no exportado");
                        return false;
                    }
                } else {
                    System.out.println("\tMovimiento nro (" + dtsMv.getIdentificador() + ") no exportado");
                    return false;
                }
            }

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("exportarMovimStock: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean insertarMovimStock(StkMovimStock dts, long id_movimiento) {
        ConexionJdbc jdbc = new ConexionJdbc();

        try {
            sql = "INSERT INTO STK_MOVIM_STOCK(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, FECHA, VR_SITUACION, ID_SITIO, "
                    + "USUARIO, USR_CRE, FEC_CRE, NUMERO, VR_ORIGEN_TRANS, ID_TRANSACCION, ID_COMPROBANTE, "
                    + "NRO_COMPROBANTE, ID_CTA_REF, NRO_CTA_REF, NRO_REFERENCIA, REVERSADO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            pst.setLong(1, id_movimiento);
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setTimestamp(4, dts.getfecha());
            pst.setString(5, dts.getVrSituacion());
            pst.setInt(6, dts.getIdSitio());
            pst.setString(7, dts.getUsuario());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, dts.getfechaCre());
            pst.setString(10, dts.getNumero());
            pst.setString(11, dts.getVrOrigenTrans());
            pst.setInt(12, dts.getIdTransaccion());
            pst.setLong(13, dts.getIdComprobante());
            pst.setString(14, dts.getNroComprobante());
            pst.setString(15, dts.getIdCtaRef());
            pst.setString(16, dts.getNroReferencia());
            pst.setString(17, dts.getNroReferencia());
            pst.setString(18, dts.getReversado());

            int x = pst.executeUpdate();
            System.out.println("\tMovimientos de stock exportados: " + String.valueOf(x));

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insertarMovimStock: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean exportarItemMovim(long id, long id_movimiento) {
        ConexionJdbc jdbc = new ConexionJdbc();
        ArrayList<StkItemsMvStock> listaItems = new ArrayList<>();

        try {
            sql = "SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_TRANSACCION, ID_ALMACEN, VR_ACCION, ID_PRODUCTO, "
                    + "ID_UND_MEDIDA, CANTIDAD, USR_CRE, FEC_CRE, CANTIDAD_UB, PRECIO_UNITARIO_UB, PRECIO_UNITARIO "
                    + "FROM STK_ITEMS_MV_STOCK WHERE ID_MOVIMIENTO= ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                StkItemsMvStock dts = new StkItemsMvStock();
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdMovimiento(id_movimiento);
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setIdTransaccion(rs.getInt("ID_TRANSACCION"));
                dts.setIdAlmacen(rs.getInt("ID_ALMACEN"));
                dts.setVrAccion(rs.getString("VR_ACCION"));
                dts.setIdProducto(rs.getInt("ID_PRODUCTO"));
                dts.setIdUndMedida(rs.getInt("ID_UND_MEDIDA"));
                dts.setCantidad(rs.getDouble("CANTIDAD"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setCantidadUb(rs.getDouble("CANTIDAD_UB"));
                dts.setPrecioUnitUb(rs.getInt("PRECIO_UNITARIO_UB"));
                dts.setPrecioUnitario(rs.getInt("PRECIO_UNITARIO"));

                listaItems.add(dts);
            }

            pst.close();
            return stk_items_mv_stock(listaItems);
        } catch (SQLException ex) {
            ControlMensajes.error("exportarItemMovim: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean stk_items_mv_stock(ArrayList<StkItemsMvStock> listaItems) {
        if (listaItems.size() <= 0) {
            return true;
        }

        ConexionJdbc jdbc = new ConexionJdbc();
        ContStkMovimStock cont_movim = new ContStkMovimStock();

        try {
            sql = "INSERT INTO STK_ITEMS_MV_STOCK (IDENTIFICADOR, ID_MOVIMIENTO, ID_EMPRESA, ID_UNIDAD, "
                    + "ID_TRANSACCION, ID_ALMACEN,VR_ACCION, ID_PRODUCTO, ID_UND_MEDIDA, CANTIDAD, "
                    + "USR_CRE, FEC_CRE, CANTIDAD_UB, PRECIO_UNITARIO_UB, COSTO_UNITARIO_UB, PRECIO_UNITARIO, "
                    + "COSTO_UNITARIO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            for (int i = 0; i < listaItems.size(); i++) {
                pst.setLong(1, cont_movim.idMovItem("S"));
                pst.setLong(2, listaItems.get(i).getIdMovimiento());
                pst.setInt(3, listaItems.get(i).getIdEmpresa());
                pst.setInt(4, listaItems.get(i).getIdUnidad());
                pst.setInt(5, listaItems.get(i).getIdTransaccion());
                pst.setInt(6, listaItems.get(i).getIdAlmacen());
                pst.setString(7, listaItems.get(i).getVrAccion());
                pst.setInt(8, listaItems.get(i).getIdProducto());
                pst.setInt(9, listaItems.get(i).getIdUndMedida());
                pst.setDouble(10, listaItems.get(i).getCantidad());
                pst.setString(11, listaItems.get(i).getUsrCre());
                pst.setTimestamp(12, listaItems.get(i).getFecCre());
                pst.setDouble(13, listaItems.get(i).getCantidad());
                pst.setDouble(14, listaItems.get(i).getPrecioUnitUb());
                pst.setDouble(15, listaItems.get(i).getPrecioUnitario());
                pst.setDouble(16, listaItems.get(i).getPrecioUnitario());
                pst.setDouble(17, listaItems.get(i).getPrecioUnitUb());

                pst.addBatch();

                actualizarExistenciaStock(listaItems.get(i).getCantidad(), listaItems.get(i).getIdProducto(), listaItems.get(i).getIdAlmacen());
            }

            int[] x = pst.executeBatch();
            System.out.println("\tItems de mv exportados: " + String.valueOf(x.length));

            pst.close();
            return x.length != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarItemsComprobanteMovim: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarExistenciaStock(double existencia, int id_producto, int idAlmacen) {
        ConexionJdbc jdbc = new ConexionJdbc();

        try {
            PreparedStatement pst = null;

            if (existencia < 0) {
                sql = "UPDATE STK_STOCK_PROD SET EXISTENCIA = EXISTENCIA + ? WHERE ID_PRODUCTO = ? AND ID_ALMACEN = ?";
            } else {
                sql = "UPDATE STK_STOCK_PROD SET EXISTENCIA = EXISTENCIA - ? WHERE ID_PRODUCTO = ? AND ID_ALMACEN = ?";
            }

            pst = jdbc.server().prepareStatement(sql);

            pst.setDouble(1, existencia);
            pst.setDouble(2, id_producto);
            pst.setInt(3, idAlmacen);

            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("actualizarExistenciaStock: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean exportarCobrosCaja(long idComp) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            TcCobrosCaja dts = new TcCobrosCaja();
            sql = "SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_TIPO_COBRO, FECHA, NUMERO, ID_CONTROL, ID_SITIO, ID_CAJA, USR_CAJERO,"
                    + "USR_CRE, FEC_CRE, ID_MONEDA, ID_TURNO, TIPO_CAMBIO, IMPORTE_EFECTIVO, IMPORTE_DOCUM, IMPORTE_VUELTO, ID_CLIENTE,"
                    + "ID_COMPROBANTE, VERIFICADO, CONFIRMADO, REVERSADO, ID_CAJERO, MONTO_TOTAL, MONTO_TOTAL_REAL "
                    + "FROM TC_COBROS_CAJA WHERE ID_COMPROBANTE = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idComp);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setIdTipoCobro(rs.getInt("ID_TIPO_COBRO"));
                dts.setfecha(rs.getTimestamp("FECHA"));
                dts.setNumero(rs.getString("NUMERO"));
                dts.setIdControl(rs.getLong("ID_CONTROL"));
                dts.setIdSitio(rs.getInt("ID_SITIO"));
                dts.setIdCaja(rs.getInt("ID_CAJA"));
                dts.setUsrCajero(rs.getString("USR_CAJERO"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setIdTurno(rs.getInt("ID_TURNO"));
                dts.setTipoCambio(rs.getInt("TIPO_CAMBIO"));
                dts.setImporteEfectivo(rs.getInt("IMPORTE_EFECTIVO"));
                dts.setImporteDocum(rs.getInt("IMPORTE_DOCUM"));
                dts.setImporteVuelto(rs.getInt("IMPORTE_VUELTO"));
                dts.setIdCliente(rs.getLong("ID_CLIENTE"));
                dts.setIdComprobante(rs.getLong("ID_COMPROBANTE"));
                dts.setVerificado(rs.getString("VERIFICADO"));
                dts.setConfirmado(rs.getString("CONFIRMADO"));
                dts.setReversado(rs.getString("REVERSADO"));
                dts.setIdCajero(rs.getInt("ID_CAJERO"));
                dts.setMontoTotal(rs.getLong("MONTO_TOTAL"));
                dts.setMontoTotalReal(rs.getLong("MONTO_TOTAL_REAL"));

                if (insertarCobrosCaja(dts)) {
                    if (exportarDetallesCobro(dts.getIdentificador())) {
                        if (!exportarVouchers(dts.getIdentificador())) {
                            System.out.println("\tVoucher de Cobro (" + dts.getIdentificador() + ") no exportado");
                            return false;
                        }
                    } else {
                        System.out.println("\tDetalle de Cobro nro (" + dts.getIdentificador() + ") no exportado");
                        return false;
                    }
                } else {
                    System.out.println("\tCobro nro (" + dts.getIdentificador() + ") no exportado");
                    return false;
                }
            }
            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("exportarCobrosCaja: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean insertarCobrosCaja(TcCobrosCaja dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_COBROS_CAJA(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_TIPO_COBRO, FECHA,"
                    + "NUMERO, ID_CONTROL, ID_SITIO, ID_CAJA, USR_CAJERO, USR_CRE, FEC_CRE, ID_MONEDA,"
                    + "ID_TURNO, IMPORTE_EFECTIVO, IMPORTE_DOCUM, IMPORTE_VUELTO, ID_CLIENTE,"
                    + "ID_COMPROBANTE, VERIFICADO, CONFIRMADO, REVERSADO, ID_CAJERO, MONTO_TOTAL, MONTO_TOTAL_REAL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdTipoCobro());
            pst.setTimestamp(5, dts.getfecha());
            pst.setString(6, dts.getNumero());
            pst.setLong(7, dts.getIdControl());
            pst.setInt(8, dts.getIdSitio());
            pst.setInt(9, dts.getIdCaja());
            pst.setString(10, dts.getUsrCajero());
            pst.setString(11, dts.getUsrCre());
            pst.setTimestamp(12, dts.getFecCre());
            pst.setInt(13, dts.getIdMoneda());
            pst.setInt(14, dts.getIdTurno());
            pst.setLong(15, dts.getImporteEfectivo());
            pst.setLong(16, dts.getImporteDocum());
            pst.setLong(17, dts.getImporteVuelto());
            pst.setLong(18, dts.getIdCliente());
            pst.setLong(19, dts.getIdComprobante());
            pst.setString(20, dts.getVerificado());
            pst.setString(21, dts.getConfirmado());
            pst.setString(22, dts.getReversado());
            pst.setInt(23, dts.getIdCajero());
            pst.setLong(24, dts.getMontoTotal());
            pst.setLong(25, dts.getMontoTotalReal());

            int x = pst.executeUpdate();
            System.out.println("\tCobros exportados: " + String.valueOf(x));

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insertarCobrosCaja: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean exportarDetallesCobro(long idCobro) {
        ConexionJdbc jdbc = new ConexionJdbc();
        ArrayList<TcItemsCobro> listaItems = new ArrayList<>();

        try {
            sql = "SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_COBRO, ID_COMPROBANTE, ID_FORMA_PAGO, "
                    + "ID_MONEDA, IMPORTE, IMPORTE_ORIGEN, USR_CRE, FEC_CRE, IMPORTE_ML, IMPORTE_ASIGNADO, "
                    + "TIPO_CAMBIO, ID_TIPO_DOCUM, NRO_DOCUMENTO, IMPORTE_DOCUMENTO, FECHA_DOCUMENTO,"
                    + "ID_PROCESADORA, ID_TIPO_TARJETA, ID_MARCA_TARJETA, NRO_TARJETA, NOMBRE_CONSIGNADO,"
                    + "NUMERO_ITEM, CANTIDAD_CUOTAS, IMPORTE_INICIAL "
                    + "FROM TC_ITEMS_COBRO WHERE ID_COBRO = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idCobro);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                TcItemsCobro dts = new TcItemsCobro();
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setIdCobro(rs.getLong("ID_COBRO"));
                dts.setIdComprobante(rs.getLong("ID_COMPROBANTE"));
                dts.setIdFormaPago(rs.getInt("ID_FORMA_PAGO"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setImporte(rs.getLong("IMPORTE"));
                dts.setImporteOrigen(rs.getLong("IMPORTE_ORIGEN"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFeCre(rs.getTimestamp("FEC_CRE"));
                dts.setImporteMl(rs.getLong("IMPORTE_ML"));
                dts.setImporteAsignado(rs.getLong("IMPORTE_ASIGNADO"));
                dts.setTipoCambio(rs.getInt("TIPO_CAMBIO"));
                dts.setIdTipoDocumento(rs.getInt("ID_TIPO_DOCUM"));
                dts.setNroDocumento(rs.getString("NRO_DOCUMENTO"));
                dts.setImporteDocumento(rs.getLong("IMPORTE_DOCUMENTO"));
                dts.setFecDocumento(rs.getString("FECHA_DOCUMENTO"));
                dts.setIdProcesadora(rs.getInt("ID_PROCESADORA"));
                dts.setIdTipoTarjeta(rs.getInt("ID_TIPO_TARJETA"));
                dts.setIdMarcaTarjeta(rs.getInt("ID_MARCA_TARJETA"));
                dts.setNroTarjeta(rs.getString("NRO_TARJETA"));
                dts.setNombreConsignado(rs.getString("NOMBRE_CONSIGNADO"));
                dts.setNumeroItem(rs.getInt("NUMERO_ITEM"));
                dts.setCantidadCuotas(rs.getInt("CANTIDAD_CUOTAS"));
                dts.setImporteInicial(rs.getLong("IMPORTE_INICIAL"));

                listaItems.add(dts);
            }

            pst.close();
            return insertarDetallesCobro(listaItems);
        } catch (SQLException ex) {
            ControlMensajes.error("exportarDetallesCobro: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean insertarDetallesCobro(ArrayList<TcItemsCobro> listaItems) {
        if (listaItems.size() <= 0) {
            return true;
        }

        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_ITEMS_COBRO(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_COBRO, ID_COMPROBANTE, "
                    + "ID_FORMA_PAGO, ID_MONEDA, IMPORTE, IMPORTE_ORIGEN, USR_CRE, FEC_CRE, "
                    + "IMPORTE_ML, IMPORTE_ASIGNADO, TIPO_CAMBIO, ID_TIPO_DOCUM, FECHA_DOCUMENTO, "
                    + "ID_PROCESADORA, ID_TIPO_TARJETA, ID_MARCA_TARJETA, NRO_TARJETA, NRO_VOUCHER, "
                    + "NUMERO_ITEM, CANTIDAD_CUOTAS, IMPORTE_INICIAL, NOMBRE_CONSIGNADO)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            for (int i = 0; i < listaItems.size(); i++) {
                pst.setLong(1, listaItems.get(i).getIdentificador());
                pst.setInt(2, listaItems.get(i).getIdEmpresa());
                pst.setInt(3, listaItems.get(i).getIdUnidad());
                pst.setLong(4, listaItems.get(i).getIdCobro());
                pst.setLong(5, listaItems.get(i).getIdComprobante());
                pst.setInt(6, listaItems.get(i).getIdFormaPago());
                pst.setInt(7, listaItems.get(i).getIdMoneda());
                pst.setLong(8, listaItems.get(i).getImporte());
                pst.setLong(9, listaItems.get(i).getImporteOrigen());
                pst.setString(10, listaItems.get(i).getUsrCre());
                pst.setTimestamp(11, listaItems.get(i).getFeCre());
                pst.setLong(12, listaItems.get(i).getImporteMl());
                pst.setLong(13, listaItems.get(i).getImporteAsignado());
                pst.setInt(14, listaItems.get(i).getTipoCambio());
                pst.setInt(15, listaItems.get(i).getIdTipoDocumento());
                pst.setTimestamp(16, listaItems.get(i).getFeDocumento());
                pst.setInt(17, listaItems.get(i).getIdProcesadora());
                pst.setInt(18, listaItems.get(i).getIdTipoTarjeta());
                pst.setInt(19, listaItems.get(i).getIdMarcaTarjeta());
                pst.setString(20, listaItems.get(i).getNroTarjeta());
                pst.setString(21, listaItems.get(i).getNroVoucher());
                pst.setInt(22, listaItems.get(i).getNumeroItem());
                pst.setInt(23, listaItems.get(i).getCantidadCuotas());
                pst.setLong(24, listaItems.get(i).getImporteInicial());
                pst.setString(25, listaItems.get(i).getNombreConsignado());

                pst.addBatch();
            }

            int[] x = pst.executeBatch();
            System.out.println("\tItems de cobros exportados: " + String.valueOf(x.length));

            pst.close();
            return x.length != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarDetallesCobro: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean exportarVouchers(long idCobro) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            TcVouchers dts = new TcVouchers();
            sql = "SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_SITIO, NUMERO, ID_PROCESADORA, ID_CAJA,"
                    + "FECHA, IMPORTE, VR_TIPO_cARGA, VR_SITUACION, USR_CRE, FEC_CRE, NRO_TARJETA, NRO_DOC_CLIENTE,"
                    + "NOMBRE_CLIENTE, NUMERO_TICKET, IMPORTE_PAGADO, ID_CAJERO, ID_TURNO, ID_CONTROL_CAJA,"
                    + "VR_TIPO_TARJETA, ID_COBRO, ID_MONEDA, ID_TIPO_TARJETA, CANTIDAD_CUOTAS "
                    + "FROM TC_VOUCHERS WHERE ID_COBRO = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idCobro);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setIdSitio(rs.getInt("ID_SITIO"));
                dts.setNumero(rs.getString("NUMERO"));
                dts.setIdProcesadora(rs.getInt("ID_PROCESADORA"));
                dts.setIdCaja(rs.getInt("ID_CAJA"));
                dts.setfecha(rs.getTimestamp("FECHA"));
                dts.setImporte(rs.getLong("IMPORTE"));
                dts.setVrTipoCarga(rs.getString("VR_TIPO_CARGA"));
                dts.setVrSituacion(rs.getString("VR_SITUACION"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setNroTarjeta(rs.getString("NRO_TARJETA"));
                dts.setNroDocCliente(rs.getString("NRO_DOC_CLIENTE"));
                dts.setNombreCliente(rs.getString("NOMBRE_CLIENTE"));
                dts.setNumeroTicket(rs.getString("NUMERO_TICKET"));
                dts.setImportePagado(rs.getLong("IMPORTE_PAGADO"));
                dts.setIdCajero(rs.getInt("ID_CAJERO"));
                dts.setIdTurno(rs.getInt("ID_TURNO"));
                dts.setIdControlCaja(rs.getLong("ID_CONTROL_CAJA"));
                dts.setVrTipoTarjeta(rs.getString("VR_TIPO_TARJETA"));
                dts.setIdCobro(rs.getLong("ID_COBRO"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setIdTipoTarjeta(rs.getInt("ID_TIPO_TARJETA"));
                dts.setCantidadCuotas(rs.getInt("CANTIDAD_CUOTAS"));

                if (!insertarVouchers(dts)) {
                    System.out.println("\tVoucher nro (" + dts.getNumero() + ") no exportado");
                    return false;
                } else {
                    System.out.println("\tVoucher nro (" + dts.getNumero() + ") no exportado");
                }
            }
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("exportarVouchers: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarVouchers(TcVouchers dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_VOUCHERS(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, "
                    + "ID_SITIO, NUMERO, ID_PROCESADORA, ID_CAJA, FECHA, IMPORTE, "
                    + "VR_TIPO_CARGA, VR_SITUACION, USR_CRE, FEC_CRE, NRO_TARJETA, "
                    + "NRO_DOC_CLIENTE, NOMBRE_CLIENTE, NUMERO_TICKET, "
                    + "IMPORTE_PAGADO, ID_CAJERO, ID_TURNO, ID_CONTROL_CAJA, "
                    + "VR_TIPO_TARJETA, ID_COBRO, ID_MONEDA, ID_TIPO_TARJETA) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdSitio());
            pst.setString(5, dts.getNumero());
            pst.setInt(6, dts.getIdProcesadora());
            pst.setInt(7, dts.getIdCaja());
            pst.setTimestamp(8, dts.getfecha());
            pst.setLong(9, dts.getImporte());
            pst.setString(10, dts.getVrTipoCarga());
            pst.setString(11, dts.getVrSituacion());
            pst.setString(12, dts.getUsrCre());
            pst.setTimestamp(13, dts.getFecCre());
            pst.setString(14, dts.getNroTarjeta());
            pst.setString(15, dts.getNroDocCliente());
            pst.setString(16, dts.getNombreCliente());
            pst.setString(17, dts.getNumeroTicket());
            pst.setLong(18, dts.getImportePagado());
            pst.setInt(19, dts.getIdCajero());
            pst.setInt(20, dts.getIdTurno());
            pst.setLong(21, dts.getIdControlCaja());
            pst.setString(22, dts.getVrTipoTarjeta());
            pst.setLong(23, dts.getIdCobro());
            pst.setLong(24, dts.getIdMoneda());
            pst.setLong(25, dts.getIdTipoTarjeta());

            int x = pst.executeUpdate();
            System.out.println("Vouchers exportados: " + String.valueOf(x));

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarVouchers: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
