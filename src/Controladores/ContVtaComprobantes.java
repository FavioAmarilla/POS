package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.StkItemsMvStock;
import Modelos.VtaComprobantes;
import Modelos.VtaItemsComprob;
import Utils.FuncionesBd;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContVtaComprobantes {

    private String sql;
    public String comprobante;
    public long idComprobante;

    public long identificador() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long id = 0;
            sql = "SELECT SQ_COMP_CLT.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getLong(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("identificador: " + ex, ContVtaComprobantes.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean newVenta(VtaComprobantes dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_COMPROBANTES(IDENTIFICADOR, ID_TRANSACCION, NUMERO, FECHA, ESTADO,"
                    + "ID_SITIO, ID_MONEDA, USUARIO, USR_CRE, FEC_CRE, ID_CAJA, NRO_TICKET, NRO_REG_IMPOS,"
                    + "ID_REGISTRO_CTRL, ID_PUNTO_EMISION, ID_EMPRESA, ID_UNIDAD, ID_CONTROL_CAJA, "
                    + "MONTO_TOTAL, ID_TIPO_COMPROB, CANTIDAD_PAGOS, DIAS_POR_PAGO, "
                    + "ID_ALMACEN, ATRIBUTO1, ID_PROCESO_VENTA) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdTransaccion());
            pst.setString(3, dts.getNumero());
            pst.setTimestamp(4, FuncionesBd.sysdate());
            pst.setString(5, dts.getEstado());
            pst.setInt(6, dts.getIdSitio());
            pst.setInt(7, dts.getIdMoneda());
            pst.setString(8, dts.getUsuario());
            pst.setString(9, dts.getUsrCre());
            pst.setTimestamp(10, FuncionesBd.sysdate());
            pst.setInt(11, dts.getIdCaja());
            pst.setInt(12, dts.getNroTicket());
            pst.setString(13, dts.getNroRegImpos());
            pst.setInt(14, dts.getIdRegistroCtrl());
            pst.setInt(15, dts.getIdPuntoEmision());
            pst.setInt(16, dts.getIdEmpresa());
            pst.setInt(17, dts.getIdUnidad());
            pst.setLong(18, dts.getIdControlCaja());
            pst.setLong(19, dts.getMontoTotal());
            pst.setInt(20, dts.getIdTipoComprob());
            pst.setInt(21, dts.getCantidadPagos());
            pst.setInt(22, dts.getDiasPorPago());
            pst.setInt(23, dts.getIdAlmacen());
            pst.setString(24, dts.getAtributo1());
            pst.setInt(25, 63);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("newVenta: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean totales(VtaComprobantes dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_COMPROBANTES SET MONTO_TOTAL=?, MONTO_EXENTO=?, MONTO_GRAVADO=?, "
                    + "MONTO_GRAVADO5=?, MONTO_GRAVADO10=?, MONTO_IMPUESTO=?, MONTO_IMPUESTO5=?, "
                    + "MONTO_IMPUESTO10=?, MONTO_RETENCION=?, MONTO_PAGADO=?, DESCUENTO_347=?,"
                    + "MONTO_TOTAL_REAL=? WHERE IDENTIFICADOR=?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setLong(1, dts.getMontoTotal());
            pst.setDouble(2, dts.getMontoExento());
            pst.setDouble(3, dts.getMontoGravado());
            pst.setDouble(4, dts.getMontoGravado5());
            pst.setDouble(5, dts.getMontoGravado10());
            pst.setDouble(6, dts.getMontoImpuesto());
            pst.setDouble(7, dts.getMontoImpuesto5());
            pst.setDouble(8, dts.getMontoImpuesto10());
            pst.setDouble(9, dts.getMontoRetencion());
            pst.setDouble(10, dts.getMontoPagado());
            pst.setInt(11, dts.getDescuento347());
            pst.setLong(12, dts.getMontoTotalReal());
            pst.setLong(13, dts.getIdentificador());

            int x = pst.executeUpdate();
            pst.close();

            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("totales: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean emision(VtaComprobantes dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_COMPROBANTES SET USR_EMISION=?, FEC_EMISION=?, ID_CLIENTE=?, "
                    + "RAZON_SOCIAL=?,NUMERO_DOCUMENTO=?, ATRIBUTO2=?, DESCUENTO_347=? "
                    + "WHERE IDENTIFICADOR=?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setString(1, dts.getUsrCre());
            pst.setTimestamp(2, FuncionesBd.sysdate());
            pst.setLong(3, dts.getIdCliente());
            pst.setString(4, dts.getRazonSocial());
            pst.setString(5, dts.getNumeroDocumento());
            pst.setString(6, dts.getAtributo2());
            pst.setInt(7, dts.getDescuento347());
            pst.setLong(8, dts.getIdentificador());

            int x = pst.executeUpdate();
            pst.close();

            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("emision: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean ultNroUsado(int nro, int id, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_ASIGNACION_COMP SET ULT_NRO_USADO =? WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }
            pst.setInt(1, nro);
            pst.setInt(2, id);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("ultNroUsado: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean fechaEnvServer(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_COMPROBANTES SET FECHA_ENVIO_SRV = SYSDATE  WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);

            int x = pst.executeUpdate();
            System.out.println("\tComprobante actualizado en local: " + String.valueOf(id));

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("fechaEnvServer: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean movimStock(int idm, int id, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_COMPROBANTES SET ID_MOVIM_STOCK = ? "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, idm);
            pst.setInt(2, id);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("movimStock: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean itemStock(int idm, int id, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_ITEMS_COMPROB SET ID_ITEM_MV_STK = ? "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idm);
            pst.setInt(2, id);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("itemStock: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean enviadoServer(int id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT * FROM VTA_COMPROBANTE WHERE IDENTIFICADOR = ? AND FECHA_ENVIO_SRV IS NOT NULL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            ControlMensajes.error("enviadoServer: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean validarComprobanteEmitido(int idReg) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int cantidad = 0;

        try {
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT COUNT(IDENTIFICADOR) FROM VTA_COMPROBANTES");

            ResultSet rsv = pst.executeQuery();
            if (rsv.next()) {
                cantidad = rsv.getInt(1);
            }

            return cantidad > 0;
        } catch (SQLException ex) {
            ControlMensajes.error("validarComprobanteEmitido: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean comprobanteEmitido(int idReg) {
        ConexionJdbc jdbc = new ConexionJdbc();
        idComprobante = 0;
        comprobante = "0";
        String USR_EMISION = null;
        String USR_ANULACION = null;

        try {

            PreparedStatement pst = jdbc.local().prepareStatement("SELECT IDENTIFICADOR, NUMERO, COALESCE(USR_EMISION,''), "
                    + "COALESCE(USR_ANULACION,'') FROM VTA_COMPROBANTES WHERE NRO_TICKET IN (SELECT MAX(NRO_TICKET) "
                    + "FROM VTA_COMPROBANTES WHERE ID_REGISTRO_CTRL=? ) AND ID_REGISTRO_CTRL=?");
            pst.setInt(1, idReg);
            pst.setInt(2, idReg);

            ResultSet rsv = pst.executeQuery();
            if (rsv.next()) {
                idComprobante = rsv.getLong(1);
                comprobante = rsv.getString(2);
                USR_EMISION = rsv.getString(3);
                USR_ANULACION = rsv.getString(4);
            }

            return USR_EMISION != null || USR_ANULACION != null;
        } catch (SQLException ex) {
            ControlMensajes.error("valComprobantes: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean cancelarComp(VtaComprobantes dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_COMPROBANTES SET USR_ANULACION=?, FEC_ANULACION=?, DESCRIPCION=?, "
                    + "ID_SUPR_CANCEL=?, ID_MOVIM_STOCK=? WHERE IDENTIFICADOR=?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setString(1, dts.getUsrAnulacion());
            pst.setString(2, dts.getFeAnulacion());
            pst.setString(3, "CANCELACION A PEDIDO DEL CLIENTE");
            pst.setInt(4, 12);
            pst.setInt(5, 0);
            pst.setLong(6, dts.getIdentificador());
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("cancelarComp: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean stkComp(long idMv, long idVenta) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_COMPROBANTES SET ID_MOVIM_STOCK= ? WHERE IDENTIFICADOR= ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idMv);
            pst.setLong(2, idVenta);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("stkComp: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean valPuntoEmision(int idCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT 1 FROM VTA_PUNTOS_EMISION WHERE ID_CAJA = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idCaja);
            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            ControlMensajes.error("valPuntoEmision: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    //==========================VTA_ITEMS_COMPROBANTES==========================
    //==========================================================================
    public long identificadorItem() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long id = 0;
            sql = "SELECT SQ_ITEMS_CCLT.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getLong(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("identificadorItem: " + ex, ContVtaComprobantes.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean valProducto(String codigoBarras) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            boolean existe = false;
            sql = "SELECT 1 FROM PR_PRODUCTOS WHERE CODIGO_BARRAS = ?";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setString(1, codigoBarras);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                existe = true;
            }
            return existe;
        } catch (SQLException ex) {
            ControlMensajes.error("valProducto: " + ex, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean agregarItem(VtaItemsComprob dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_ITEMS_COMPROB(IDENTIFICADOR, ID_COMPROBANTE, ID_EMPRESA, ID_UNIDAD,"
                    + "NUMERO_ITEM, VR_TIPO_LINEA, IMPORTE_ITEM, USR_CRE, FEC_CRE, ID_PRODUCTO,"
                    + "ID_UNIDAD_MEDIDA, CANTIDAD, CANTIDAD_UB, COSTO_UNITARIO, PRECIO_UNITARIO,"
                    + "PRECIO_UNIT_NETO, PRECIO_UNITARIO_UB, IMPUESTO_UNITARIO, IMPORTE_EXENTO, "
                    + "IMPORTE_GRAVADO, IMPORTE_IMPUESTO, ID_TIPO_IMPUESTO, IMPUESTO_INCLUIDO, "
                    + "PORCENTAJE_IMP, ID_PROVEEDOR, ID_SITIO_PROV, ID_SUPR_DEVOL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setLong(2, dts.getIdComprobante());
            pst.setLong(3, dts.getIdEmpresa());
            pst.setLong(4, dts.getIdUnidad());
            pst.setLong(5, dts.getNumeroItem());
            pst.setString(6, dts.getVrTipoLinea());
            pst.setDouble(7, dts.getImporteItem());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, FuncionesBd.sysdate());
            pst.setInt(10, dts.getIdProducto());
            pst.setInt(11, dts.getIdUnidadMedida());
            pst.setDouble(12, dts.getCantidad());
            pst.setDouble(13, dts.getCantidadUb());
            pst.setDouble(14, dts.getCostoUnitario());
            pst.setDouble(15, dts.getPrecioUnitario());
            pst.setDouble(16, dts.getPrecioUnitNeto());
            pst.setDouble(17, dts.getPrecioUnitarioUb());
            pst.setDouble(18, dts.getImpuestoUnitario());
            pst.setDouble(19, dts.getImporteExento());
            pst.setDouble(20, dts.getImporteGravado());
            pst.setDouble(21, dts.getImporteImpuesto());
            pst.setInt(22, dts.getIdTipoImpuesto());
            pst.setString(23, dts.getImpuestoIncluido());
            pst.setInt(24, dts.getPorcImpuesto());
            pst.setInt(25, dts.getIdProveedor());
            pst.setInt(26, dts.getIdSitioProv());
            if (dts.getCantidad() > 0) {
                pst.setNull(27, java.sql.Types.INTEGER);
            } else {
                pst.setInt(27, ContFndUsuarios.USR_IDENTIFICADOR);
            }

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("agregarItem: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean stkCompItem(ArrayList<StkItemsMvStock> listaProductos) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_ITEMS_COMPROB SET ID_ITEM_MV_STK= ? WHERE IDENTIFICADOR= ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            for (int i = 0; i < listaProductos.size(); i++) {
                pst.setLong(1, listaProductos.get(i).getIdentificador());
                pst.setLong(2, listaProductos.get(i).getIdItemVenta());

                pst.addBatch();
            }

            int[] x = pst.executeBatch();

            System.out.println("\tItems de comprob actualizados: " + String.valueOf(x.length));

            pst.close();
            return x.length != 0;
        } catch (SQLException e) {
            ControlMensajes.error("stkCompItem: " + e, ContVtaComprobantes.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int idCategoria(String codigo) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int id = 0;
            sql = "SELECT IDENTIFICADOR FROM PR_CATEGORIAS_PROD WHERE CODIGO_REPORTE = " + codigo + "";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("idCategoria: " + ex, ContVtaComprobantes.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
