package Controladores;

import Conexiones.ConexionJdbc;
import Modelos.ReimpresionComprob;
import Utils.ControlMensajes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContReimpresionComprob {

    String query = "";

    public ReimpresionComprob getCpEquivalenciaRuc(String ruc, String condicion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        ReimpresionComprob dts = null;
        try {
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT RUC_NUEVO, NOMBRE_COMPLETO FROM CP_EQUIVALENCIA_RUC WHERE RUC_NUEVO = ?");
            pst.setString(1, ruc);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dts = new ReimpresionComprob(1425, rs.getString("RUC_NUEVO"), rs.getString("NOMBRE_COMPLETO"), condicion);
            }

            pst.close();
            return dts;
        } catch (SQLException ex) {
            ControlMensajes.error("ERROR (cliente): " + ex, "FUNC. MODIF. RUC");
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public ReimpresionComprob getVtaClientes(String ruc, String condicion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        ReimpresionComprob dts = null;
        try {
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT IDENTIFICADOR, NOMBRES || ' ' || APELLIDOS AS CLIENTE, NUMERO_RUC "
                    + "FROM VTA_CLIENTES WHERE NUMERO_RUC = ?");
            pst.setString(1, ruc);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dts = new ReimpresionComprob(rs.getInt("IDENTIFICADOR"), rs.getString("NUMERO_RUC"), rs.getString("CLIENTE"), condicion);
            }
            pst.close();
            return dts;
        } catch (SQLException ex) {
            ControlMensajes.error("ERROR (vtaClientes): " + ex, "FUNC. MODIF. RUC");
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean modificar(ReimpresionComprob dts, String conexion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        PreparedStatement pst = null;

        try {
            if (conexion.equals("L")) {
                pst = jdbc.local().prepareStatement("UPDATE vta_comprobantes SET id_cliente=?, numero_documento=?, "
                        + "razon_social=? WHERE identificador=?");
            }
            if (conexion.equals("S")) {
                pst = jdbc.server().prepareStatement("UPDATE vta_comprobantes SET id_cliente=?, numero_documento=?, "
                        + "razon_social=? WHERE identificador=?");
            }
            pst.setInt(1, dts.getId_cliente());
            pst.setString(2, dts.getRuc());
            pst.setString(3, dts.getNombre());
            pst.setString(4, dts.getCondicion());
            int n = pst.executeUpdate();

            pst.close();
            return n != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("ERROR (modificar): " + ex, "FUNC. MODIF. RUC");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }
}
