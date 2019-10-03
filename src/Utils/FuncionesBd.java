package Utils;

import Conexiones.ConexionJdbc;
import Controladores.ContTcControlCaja;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FuncionesBd {

    static String tituloMsj = ContTcControlCaja.class.getName();

    public FuncionesBd() {
    }

    public static Timestamp sysdate() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            Timestamp fecha = null;
            String sql = "SELECT SYSDATE FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fecha = rs.getTimestamp(1);
            }

            pst.close();
            return fecha;
        } catch (SQLException ex) {
            ControlMensajes.error("Sysdate: " + ex, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public static long Secuencia(String sql) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long id = 0;
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getLong(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("Secuencia: " + ex, tituloMsj);
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public static boolean truncate(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            PreparedStatement pst = jdbc.local().prepareStatement("TRUNCATE TABLE " + tabla);
            int x = pst.executeUpdate();

            pst.close();
            return x == 0;
        } catch (SQLException e) {
            ControlMensajes.error("truncate: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public static boolean getRegistros(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            String sql = "SELECT * FROM " + tabla + "";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            ControlMensajes.error("getRegistros: " + e, FuncionesBd.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }
}
