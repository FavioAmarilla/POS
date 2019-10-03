package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.TcCobrosCaja;
import Modelos.TcItemsCobro;
import Modelos.TcVouchers;
import Utils.FuncionesBd;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class ContTcCobrosCaja {

   
    String sql;

    public boolean insert(TcCobrosCaja dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_COBROS_CAJA(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_TIPO_COBRO, FECHA,"
                    + "NUMERO, ID_CONTROL, ID_SITIO, ID_CAJA, USR_CAJERO, USR_CRE, FEC_CRE, ID_MONEDA,"
                    + "ID_TURNO, IMPORTE_EFECTIVO, IMPORTE_DOCUM, IMPORTE_VUELTO, ID_CLIENTE,"
                    + "ID_COMPROBANTE, VERIFICADO, CONFIRMADO, REVERSADO, ID_CAJERO, MONTO_TOTAL, MONTO_TOTAL_REAL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdTipoCobro());
            pst.setTimestamp(5, FuncionesBd.sysdate());
            pst.setString(6, dts.getNumero());
            pst.setLong(7, dts.getIdControl());
            pst.setInt(8, dts.getIdSitio());
            pst.setInt(9, dts.getIdCaja());
            pst.setString(10, dts.getUsrCajero());
            pst.setString(11, dts.getUsrCre());
            pst.setTimestamp(12, FuncionesBd.sysdate());
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

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insert: " + e, ContTcCobrosCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean updImportes(TcCobrosCaja dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE TC_COBROS_CAJA SET IMPORTE_EFECTIVO= IMPORTE_EFECTIVO + " + dts.getImporteEfectivo() + ", "
                    + "IMPORTE_DOCUM= IMPORTE_DOCUM + " + dts.getImporteDocum() + ",IMPORTE_VUELTO=" + dts.getImporteVuelto() + " "
                    + "WHERE IDENTIFICADOR = " + dts.getIdentificador() + "";
            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("updImportes: " + e, ContTcCobrosCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public long identificadorVoucher() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long id = 0;
            sql = "SELECT SQ_TC_VOUCHERS.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getLong(1);
            } else {
                id = 0;
            }
            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("ERROR (identificador): " + ex, ContTcCobrosCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertVoucher(TcVouchers dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {

            sql = "INSERT INTO TC_VOUCHERS (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_SITIO, NUMERO, ID_PROCESADORA,"
                    + "ID_CAJA, FECHA, IMPORTE, VR_TIPO_CARGA, VR_SITUACION, USR_CRE, FEC_CRE, NRO_TARJETA,"
                    + "NOMBRE_CLIENTE, NUMERO_TICKET, IMPORTE_PAGADO, ID_CAJERO, ID_TURNO, ID_CONTROL_CAJA,"
                    + "VR_TIPO_TARJETA, ID_COBRO, ID_MONEDA, ID_TIPO_TARJETA) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdSitio());
            pst.setString(5, dts.getNumero());
            pst.setInt(6, dts.getIdProcesadora());
            pst.setInt(7, dts.getIdCaja());
            pst.setTimestamp(8, FuncionesBd.sysdate());
            pst.setLong(9, dts.getImporte());
            pst.setString(10, dts.getVrTipoCarga());
            pst.setString(11, dts.getVrSituacion());
            pst.setString(12, dts.getUsrCre());
            pst.setTimestamp(13, FuncionesBd.sysdate());
            pst.setString(14, dts.getNroTarjeta());
            pst.setString(15, dts.getNombreCliente());
            pst.setString(16, dts.getNumeroTicket());
            pst.setLong(17, dts.getImportePagado());
            pst.setInt(18, dts.getIdCajero());
            pst.setInt(19, dts.getIdTurno());
            pst.setLong(20, dts.getIdControlCaja());
            pst.setString(21, dts.getVrTipoTarjeta());
            pst.setLong(22, dts.getIdCobro());
            pst.setInt(23, dts.getIdMoneda());
            pst.setInt(24, dts.getIdTipoTarjeta());
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("insert: " + ex, ContTcCobrosCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //TC_ITEMS_COBRO
    public long idDetalleCobro() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long id = 0;
            sql = "SELECT SQ_ITEMS_COBRO.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getLong(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("idDetalleCobro: " + ex, ContTcCobrosCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insDetalle(TcItemsCobro dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_ITEMS_COBRO(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_COBRO, ID_COMPROBANTE, "
                    + "ID_FORMA_PAGO, ID_MONEDA, IMPORTE, IMPORTE_ORIGEN, USR_CRE, FEC_CRE, "
                    + "IMPORTE_ML, IMPORTE_ASIGNADO, TIPO_CAMBIO, ID_TIPO_DOCUM, FECHA_DOCUMENTO, "
                    + "ID_PROCESADORA, ID_TIPO_TARJETA, ID_MARCA_TARJETA, NRO_TARJETA, NRO_VOUCHER, "
                    + "NUMERO_ITEM, CANTIDAD_CUOTAS, IMPORTE_INICIAL, NOMBRE_CONSIGNADO)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setLong(4, dts.getIdCobro());
            pst.setLong(5, dts.getIdComprobante());
            pst.setInt(6, dts.getIdFormaPago());
            pst.setInt(7, dts.getIdMoneda());
            pst.setLong(8, dts.getImporte());
            pst.setLong(9, dts.getImporteOrigen());
            pst.setString(10, dts.getUsrCre());
            pst.setTimestamp(11, FuncionesBd.sysdate());
            pst.setLong(12, dts.getImporteMl());
            pst.setLong(13, dts.getImporteAsignado());
            pst.setInt(14, dts.getTipoCambio());
            pst.setInt(15, dts.getIdTipoDocumento());
            pst.setString(16, dts.getFecDocumento());
            pst.setInt(17, dts.getIdProcesadora());
            pst.setInt(18, dts.getIdTipoTarjeta());
            pst.setInt(19, dts.getIdMarcaTarjeta());
            pst.setString(20, dts.getNroTarjeta());
            pst.setString(21, dts.getNroVoucher());
            pst.setInt(22, dts.getNumeroItem());
            pst.setInt(23, dts.getCantidadCuotas());
            pst.setLong(24, dts.getImporteInicial());
            pst.setString(25, dts.getNombreConsignado());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insDetalle: " + e, ContTcCobrosCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean delDetalle(int id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "DELETE FROM TC_ITEMS_COBRO WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, id);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("delDetalle: " + e, ContTcCobrosCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public DefaultTableModel procesadoras() {
        ConexionJdbc jdbc = new ConexionJdbc();
        DefaultTableModel modelo;
        String[] titulos = {"IDENTIFICADOR", "DESCRIPCION"};
        String[] registro = new String[2];
        modelo = new DefaultTableModel(null, titulos);

        sql = "SELECT IDENTIFICADOR, DESCRIPCION FROM TC_PROC_TARJETAS WHERE ACTIVO='S' ";
        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("DESCRIPCION");
                modelo.addRow(registro);
            }

            pst.close();
            return modelo;
        } catch (SQLException ex) {
            ControlMensajes.error("procesadoras: " + ex, ContTcCobrosCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public DefaultTableModel tiposTarjeta() {
        ConexionJdbc jdbc = new ConexionJdbc();
        DefaultTableModel modelo;
        String[] titulos = {"IDENTIFICADOR", "DESCRIPCION"};
        String[] registro = new String[2];
        modelo = new DefaultTableModel(null, titulos);

        sql = "SELECT IDENTIFICADOR, DESCRIPCION FROM TC_TIPOS_TARJETAS WHERE ACTIVO='S' ";
        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("DESCRIPCION");
                modelo.addRow(registro);
            }

            pst.close();
            return modelo;
        } catch (SQLException ex) {
            ControlMensajes.error("tiposTarjeta: " + ex, ContTcCobrosCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public DefaultTableModel entEmisoras() {
        ConexionJdbc jdbc = new ConexionJdbc();
        DefaultTableModel modelo;
        String[] titulos = {"IDENTIFICADOR", "CODIGO", "DESCRIPCION"};
        String[] registro = new String[2];
        modelo = new DefaultTableModel(null, titulos);

        sql = "SELECT IDENTIFICADOR, CODIGO, DESCRIPCION FROM TC_ENT_EMISORAS";
        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("CODIGO");
                registro[2] = rs.getString("DESCRIPCION");
                modelo.addRow(registro);
            }

            pst.close();
            return modelo;
        } catch (SQLException ex) {
            ControlMensajes.error("tiposTarjeta: " + ex, ContTcCobrosCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }
}
