package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContPosAplicNovedades {

   
    String sql = "";

    public boolean getAplicNovedades() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT NOMBRE_TABLA FROM POS_APLIC_NOVEDADES";
            String tabla = "";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                tabla = rs.getString(1);
                if (update_aplic_novedades(tabla)) {
                    System.out.println("\tAPLIC. NOVEDADES (" + tabla + ") ACTUALIZADO");
                } else {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("getAplicNovedades:" + e.getMessage(), ContPosAplicNovedades.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int getIdMAx(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT MAX(IDENTIFICADOR) FROM POS_REGISTRO_NOVEDADES WHERE NOMBRE_TABLA='" + tabla + "'";

            int id = 0;
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
        } catch (SQLException e) {
            ControlMensajes.error("getIdMAx: " + e.getMessage(), ContPosAplicNovedades.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    private boolean update_aplic_novedades(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE POS_APLIC_NOVEDADES SET ULTIMO_ID_NOVEDAD=" + getIdMAx(tabla) + " WHERE NOMBRE_TABLA='" + tabla + "'";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            int x = pst.executeUpdate();

            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("update_aplic_novedades: " + e.getMessage(), ContPosAplicNovedades.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
