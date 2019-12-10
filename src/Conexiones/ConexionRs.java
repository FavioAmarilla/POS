package Conexiones;

import Controladores.ContFndMonedas;
import Pos.POS;
import Utils.Utilidades;
import Utils.ControlMensajes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionRs {

    private final String tituloMsj = ConexionRs.class.getName();
    private String url = "";
    private Connection cnn = null;

     public Connection server() {
        if (Utilidades.getPing()) {
            url = "jdbc:oracle:thin:@" + POS.IP_SERVIDOR + ":1521:" + POS.DB_NAME + "";
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                cnn = DriverManager.getConnection(url, POS.DB_USER, POS.DB_PASS);
            } catch (ClassNotFoundException | SQLException ex) {
                ControlMensajes.error("Error(server): " + ex, tituloMsj);
            }
        }
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
            Logger.getLogger(ConexionJdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet consultar(String sql) {
        try {
            PreparedStatement pst = local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException ex) {
            ControlMensajes.error("consultar: " + ex, ConexionRs.class.getName());
            return null;
        }
    }
}
