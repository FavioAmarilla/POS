package Conexiones;

import Pos.POS;
import Utils.ControlMensajes;
import Utils.Utilidades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionJdbc {

    private final String tituloMsj = ConexionJdbc.class.getName();
    String url = "";
    Connection cnn = null;

    public Connection server() {
//        if (Utilidades.getPing()) {
            url = "jdbc:oracle:thin:@" + POS.IP_SERVIDOR + ":1521:" + POS.DB_NAME + "";
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                cnn = DriverManager.getConnection(url, POS.DB_USER, POS.DB_PASS);
            } catch (ClassNotFoundException | SQLException ex) {
                ControlMensajes.error("Error(server): " + ex, tituloMsj);
            }
//        } else {
//                ControlMensajes.error("Sin conexion a servidor(ping)", tituloMsj);
//        }
        return cnn;
    }

    public Connection local() {
        url = "jdbc:oracle:thin:@" + POS.IP_LOCAL + ":1521:" + POS.DB_NAME + "";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            cnn = DriverManager.getConnection(url, POS.DB_USER, POS.DB_PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            ControlMensajes.error("Error(local): " + ex, tituloMsj);
        }
        return cnn;
    }

    public void cerrarConexion() {
        try {
            if (cnn != null) {
                cnn.close();
            }
        } catch (SQLException ex) {
            ControlMensajes.error("Error(cerrarConexion)" + ex, tituloMsj);
        }
    }

}
