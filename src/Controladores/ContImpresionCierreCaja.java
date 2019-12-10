package Controladores;

import Utils.FormatosTicket;
import Conexiones.ConexionJdbc;
import Vistas.FrmLogin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContImpresionCierreCaja {

    String sql = "";

    SimpleDateFormat formato;
    FormatosTicket ticket = new FormatosTicket();

    public String[] totalesResumenFinanciero(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            String[] datos = new String[4];
            sql = "SELECT \n"
                    + "COALESCE(SUM(TOTAL_CANCELADO),0) AS TOTAL_CANCELACION, "
                    + "COALESCE(SUM(TOTAL_ANULADO),0) AS TOTAL_ANULACION, "
                    + "COALESCE(SUM(TOTAL_DESCUENTO),0) AS TOTAL_DESCUENTOS, "
                    + "COALESCE(SUM(TOTAL),0) AS TOTAL_BRUTO "
                    + "FROM TC_CONTROL_CAJA "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
            }

            pst.close();
            return datos;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public String[] cantResumenFinanciero(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            String[] datos = new String[3];
            sql = "SELECT "
                    + "COALESCE(CANT_CANCELADO,0) AS TOTAL_CANCELACION, "
                    + "COALESCE(CANT_ANULADO,0) AS TOTAL_ANULACION, "
                    + "COALESCE(CANT_DESCTOS,0) AS TOTAL_DESCUENTOS "
                    + "FROM TC_CONTROL_CAJA "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
            }

            pst.close();
            return datos;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public ResultSet mediosPagoResFinanciero(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT \n"
                    + "COALESCE(FND_FORMAS_PAGO.ABREVIATURA,'-'),\n"
                    + "(\n"
                    + "SELECT COUNT(*) FROM TC_ITEMS_COBRO \n"
                    + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                    + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = ?\n"
                    + ") AS CANTIDAD,\n"
                    + "(\n"
                    + "SELECT COALESCE(SUM(IMPORTE),0) FROM TC_ITEMS_COBRO \n"
                    + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                    + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = ?\n"
                    + ") , FND_MONEDAS.CODIGO \n"
                    + "FROM TC_CONCEPTOS_ARQUEO TCC\n"
                    + "LEFT JOIN FND_FORMAS_PAGO ON FND_FORMAS_PAGO.IDENTIFICADOR = COALESCE(TCC.ID_FORMA_PAGO,9)\n"
                    + "LEFT JOIN FND_MONEDAS ON FND_MONEDAS.IDENTIFICADOR = FND_FORMAS_PAGO.ID_MONEDA";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            pst.setLong(2, idControlCaja);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String totalComprobantes(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            String cant = "";
            sql = "SELECT COUNT(*) FROM VTA_COMPROBANTES WHERE ID_CONTROL_CAJA = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                cant = rs.getString(1);
            }

            pst.close();
            return cant;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public ResultSet vuelto(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT 'VUELTO' ,COUNT(IMPORTE_VUELTO), COALESCE(SUM(IMPORTE_VUELTO),0), FND_MONEDAS.CODIGO FROM TC_COBROS_CAJA \n"
                    + "LEFT JOIN FND_MONEDAS ON FND_MONEDAS.IDENTIFICADOR = TC_COBROS_CAJA.ID_MONEDA\n"
                    + "WHERE ID_CONTROL = ? AND IMPORTE_VUELTO > 0 \n"
                    + "GROUP BY FND_MONEDAS.CODIGO";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String clientes(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            String cant = "";
            sql = "SELECT COALESCE(COUNT(*),0) FROM VTA_COMPROBANTES "
                    + "WHERE ID_CONTROL_CAJA = ? AND ID_CLIENTE <> 1425";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                cant = rs.getString(1);
            }

            pst.close();
            return cant;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public ResultSet artTotales(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT \n"
                    + "COALESCE(COUNT(VTA.IDENTIFICADOR),0) AS ARTICULOS,\n"
                    + "COALESCE(SUM(VTA.MONTO_GRAVADO),0) AS TOTAL_GRAVADO,\n"
                    + "COALESCE(SUM(VTA.MONTO_IMPUESTO),0) AS TOTAL_IMPUESTO,\n"
                    + "COALESCE(SUM(VTA.MONTO_EXENTO),0) AS TOTAL_EXENTO\n"
                    + "FROM VTA_COMPROBANTES VTA "
                    + "WHERE VTA.ID_CONTROL_CAJA = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet nroTickets(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT NRO_INICIAL, NRO_FINAL FROM TC_CONTROL_CAJA WHERE IDENTIFICADOR = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet totalesResumenContable(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT\n"
                    + "COALESCE(SUM(MONTO_EXENTO),0) ,\n"
                    + "COALESCE(SUM(MONTO_GRAVADO5),0) ,\n"
                    + "COALESCE(SUM(MONTO_GRAVADO10),0)\n"
                    + "FROM VTA_COMPROBANTES\n"
                    + "WHERE ID_CONTROL_CAJA = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet conceptosArqueo(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT SUBSTR(TCC.DESCRIPCION,1,14),\n"
                    + "(\n"
                    + "SELECT COUNT(*) FROM TC_ITEMS_COBRO \n"
                    + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                    + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = ?\n"
                    + ") AS CANT_COMPROBANTE,\n"
                    + "(\n"
                    + "SELECT COALESCE(SUM(IMPORTE),0) FROM TC_ITEMS_COBRO \n"
                    + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                    + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = ?\n"
                    + ") AS TOTAL,\n"
                    + "TCC.ID_FORMA_PAGO\n"
                    + "FROM TC_CONCEPTOS_ARQUEO TCC\n"
                    + "LEFT JOIN FND_FORMAS_PAGO ON FND_FORMAS_PAGO.IDENTIFICADOR = TCC.ID_FORMA_PAGO";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            pst.setLong(2, idControlCaja);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet totalExtracciones(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT \n"
                    + "TC_DENOM_MONEDA.DESCRIPCION, TC_DETALLE_EXTR_EFEC.ID_DENOMINACION, "
                    + "SUM(TC_DETALLE_EXTR_EFEC.CANTIDAD) AS CANTIDAD, "
                    + "SUM(TC_DETALLE_EXTR_EFEC.IMPORTE) AS IMPORTE "
                    + "FROM TC_EXTR_EFECTIVO_CAJA EXT "
                    + "LEFT JOIN TC_DETALLE_EXTR_EFEC ON TC_DETALLE_EXTR_EFEC.ID_EXTRACCION = EXT.IDENTIFICADOR "
                    + "LEFT JOIN TC_DENOM_MONEDA ON TC_DENOM_MONEDA.IDENTIFICADOR = TC_DETALLE_EXTR_EFEC.ID_DENOMINACION "
                    + "WHERE EXT.ID_CONTROL = ? AND TC_DENOM_MONEDA.ID_MONEDA = ? "
                    + "GROUP BY TC_DENOM_MONEDA.DESCRIPCION, TC_DETALLE_EXTR_EFEC.ID_DENOMINACION, "
                    + "TC_DETALLE_EXTR_EFEC.CANTIDAD,TC_DETALLE_EXTR_EFEC.IMPORTE, TC_DENOM_MONEDA.VALOR "
                    + "ORDER BY TC_DENOM_MONEDA.VALOR DESC ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            pst.setInt(2, ContParamAplicacion.UND_ID_MONEDA);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int ventaPos(long idControlCaja, String condicion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int total = 0;
            sql = "SELECT COALESCE(SUM(MONTO_TOTAL),0) FROM VTA_COMPROBANTES WHERE ID_CONTROL_CAJA= ? " + condicion;

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

            pst.close();
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(ContImpresionCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
