package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.StkItemsMvStock;
import Modelos.StkMovimStock;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ContStkMovimStock {

    private String sql;

    public Timestamp sysdate() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            Timestamp fecha = null;
            sql = "SELECT SYSDATE FROM DUAL";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fecha = rs.getTimestamp(1);
            }

            pst.close();
            return fecha;
        } catch (SQLException ex) {
            ControlMensajes.error("sysdate: " + ex, ContTcCobrosCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public Date fecha() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            Date fecha = null;
            sql = "SELECT SYSDATE FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fecha = rs.getDate(1);
            }

            pst.close();
            return fecha;
        } catch (SQLException ex) {
            ControlMensajes.error("fecha: " + ex, ContTcCobrosCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public long idMovimiento(String server) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long idMov = 0;
            sql = "SELECT SQ_STK_MOVIM.NEXTVAL FROM DUAL";
            PreparedStatement pst = null;

            if (server.toUpperCase().equals("L")) {
                pst = jdbc.local().prepareStatement(sql);
            }
            if (server.toUpperCase().equals("S")) {
                pst = jdbc.server().prepareStatement(sql);
            }
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idMov = rs.getLong(1);
            }

            pst.close();
            return idMov;
        } catch (SQLException ex) {
            ControlMensajes.error("idMovimiento: " + ex, ContStkMovimStock.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean stk_movim_stock(StkMovimStock dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO STK_MOVIM_STOCK(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, FECHA, VR_SITUACION, ID_SITIO, "
                    + "USUARIO, USR_CRE, FEC_CRE, NUMERO, VR_ORIGEN_TRANS, ID_TRANSACCION, ID_COMPROBANTE, "
                    + "NRO_COMPROBANTE, ID_CTA_REF, NRO_CTA_REF, NRO_REFERENCIA, REVERSADO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setTimestamp(4, sysdate());
            pst.setString(5, dts.getVrSituacion());
            pst.setInt(6, dts.getIdSitio());
            pst.setString(7, dts.getUsuario());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, sysdate());
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
            System.out.println("\tMovimiento de stock generado: " + String.valueOf(x));

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("stk_movim_stock: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public long idMovItem(String server) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long id = 0;
            sql = "SELECT SQ_ITEMS_STKM.NEXTVAL FROM DUAL";

            PreparedStatement pst = null;
            if (server.toUpperCase().equals("L")) {
                pst = jdbc.local().prepareStatement(sql);
            }
            if (server.toUpperCase().equals("S")) {
                pst = jdbc.server().prepareStatement(sql);
            }

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("idMovItem: " + ex, ContStkMovimStock.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean stk_items_mv_stock(StkItemsMvStock dts, ArrayList<StkItemsMvStock> listaProductos) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int transaccion = 0;
        String vr_accion = "";

        try {
            sql = "INSERT INTO STK_ITEMS_MV_STOCK (IDENTIFICADOR, ID_MOVIMIENTO, ID_EMPRESA, ID_UNIDAD, "
                    + "ID_TRANSACCION, ID_ALMACEN,VR_ACCION, ID_PRODUCTO, ID_UND_MEDIDA, CANTIDAD, "
                    + "USR_CRE, FEC_CRE, CANTIDAD_UB, PRECIO_UNITARIO_UB, COSTO_UNITARIO_UB, PRECIO_UNITARIO, "
                    + "COSTO_UNITARIO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            for (int i = 0; i < listaProductos.size(); i++) {

                if (listaProductos.get(i).getCantidad() < 0) {
                    transaccion = 15;
                    vr_accion = "IN";
                } else {
                    transaccion = 19;
                    vr_accion = "EG";
                }

                pst.setLong(1, listaProductos.get(i).getIdentificador());
                pst.setLong(2, dts.getIdMovimiento());
                pst.setInt(3, dts.getIdEmpresa());
                pst.setInt(4, dts.getIdUnidad());
                pst.setInt(5, transaccion);
                pst.setInt(6, dts.getIdAlmacen());
                pst.setString(7, vr_accion);
                pst.setInt(8, listaProductos.get(i).getIdProducto());
                pst.setInt(9, listaProductos.get(i).getIdUndMedida());
                pst.setDouble(10, listaProductos.get(i).getCantidad());
                pst.setString(11, dts.getUsrCre());
                pst.setTimestamp(12, sysdate());
                pst.setDouble(13, listaProductos.get(i).getCantidad());
                pst.setDouble(14, listaProductos.get(i).getCostoUnitario());
                pst.setDouble(15, listaProductos.get(i).getPrecioUnitario());
                pst.setDouble(16, listaProductos.get(i).getPrecioUnitario());
                pst.setDouble(17, listaProductos.get(i).getCostoUnitario());

                pst.addBatch();
            }

            int[] x = pst.executeBatch();
            System.out.println("\tItems de mv registrados: " + String.valueOf(x.length));

            actualizarExistenciaStock(listaProductos);

            pst.close();
            return x.length != 0;
        } catch (SQLException e) {
            ControlMensajes.error("stk_items_mv_stock: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarExistenciaStock(ArrayList<StkItemsMvStock> listaProductos) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {

            PreparedStatement pst = null;
            int filas = 0;
            for (int i = 0; i < listaProductos.size(); i++) {

                if (listaProductos.get(i).getCantidad() < 0) {
                    sql = "UPDATE STK_STOCK_PROD SET EXISTENCIA = EXISTENCIA + ? WHERE ID_PRODUCTO = ? AND ID_ALMACEN = 507";
                } else {
                    sql = "UPDATE STK_STOCK_PROD SET EXISTENCIA = EXISTENCIA - ? WHERE ID_PRODUCTO = ? AND ID_ALMACEN = 507";
                }

                pst = jdbc.local().prepareStatement(sql);

                pst.setDouble(1, listaProductos.get(i).getCantidad());
                pst.setDouble(2, listaProductos.get(i).getIdProducto());

                filas += pst.executeUpdate();
            }

            System.out.println("\tExistencia de articulos actualizados: " + String.valueOf(filas));

            pst.close();
            return filas != 0;

        } catch (SQLException e) {
            ControlMensajes.error("actualizarExistenciaStock: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int idStock() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int id = 0;
            sql = "SELECT SQ_STOCK.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("idStock: " + ex, ContStkMovimStock.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarExistenciaStock(StkMovimStock dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO STK_STOCK_PROD (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_ALMACEN, "
                    + "ID_PRODUCTO, ID_UND_MEDIDA, EXISTENCIA, USR_CRE, FEC_CRE) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdStock());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdAlmacen());
            pst.setInt(5, dts.getIdProducto());
            pst.setInt(6, dts.getIdUndMedida());
            pst.setDouble(7, dts.getexisteStock());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, sysdate());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insertarExistenciaStock: " + e, ContStkMovimStock.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean fechaEnvServer(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE STK_MOVIM_STOCK SET FECHA_ENVIO_SRV = SYSDATE WHERE IDENTIFICADOR = " + id + "";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("fechaEnvServer: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
