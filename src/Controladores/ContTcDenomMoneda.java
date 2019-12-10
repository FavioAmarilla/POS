package Controladores;

import Conexiones.ConexionJdbc;
import Modelos.FndMonedas;
import Utils.FuncionesBd;
import Utils.ControlMensajes;
import Vistas.FrmLogin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

public class ContTcDenomMoneda {

    private final String tituloMsj = ContTcDenomMoneda.class.getName();
    DecimalFormat miles = new DecimalFormat("###,###.##");

    public DefaultTableModel getDenominaciones() {
        ConexionJdbc jdbc = new ConexionJdbc();
        DefaultTableModel modelo;
        String[] titulos = {"IDENTIFICADOR", "ID_MONEDA", "DESCRIPCION", "VALOR", "CANTIDAD", "TOTAL"};
        String[] registro = new String[6];
        modelo = new DefaultTableModel(null, titulos);

        try {
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT * FROM TC_DENOM_MONEDA "
                    + "WHERE ID_MONEDA = ? ORDER BY VALOR DESC");
            pst.setInt(1, ContParamAplicacion.UND_ID_MONEDA);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("IDENTIFICADOR");
                registro[1] = rs.getString("ID_MONEDA");
                registro[2] = rs.getString("DESCRIPCION");
                registro[3] = miles.format(rs.getLong("VALOR"));
                registro[4] = "0";
                registro[5] = "0";
                modelo.addRow(registro);
            }
            pst.close();
            return modelo;
        } catch (SQLException ex) {
            ControlMensajes.error("getDenominacion " + ex, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean importarDenominaciones() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndMonedas dtsMon = new FndMonedas();
        int filas = 0;

        try {
            PreparedStatement pst = jdbc.server().prepareStatement("SELECT * FROM TC_DENOM_MONEDA");
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("TC_DENOM_MONEDA");

            while (rs.next()) {
                dtsMon.setIdDenominacion(rs.getInt("IDENTIFICADOR"));
                dtsMon.setIdMoneda(rs.getInt("ID_MONEDA"));
                dtsMon.setDescripcion(rs.getString("DESCRIPCION"));
                dtsMon.setValor(rs.getInt("VALOR"));
                dtsMon.setUsrCre(rs.getString("USR_CRE"));
                dtsMon.setFecCre(rs.getTimestamp("FEC_CRE"));

                filas++;
                insertarDenominaciones(dtsMon);
            }

            System.out.println("\tDENOMINACIONES IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("importarDenominaciones: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarDenominaciones(FndMonedas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {;

            PreparedStatement pst = jdbc.local().prepareStatement("INSERT INTO TC_DENOM_MONEDA("
                    + "IDENTIFICADOR, ID_MONEDA, DESCRIPCION, VALOR, "
                    + "USR_CRE, FEC_CRE) VALUES(?,?,?,?,?,?)");
            pst.setInt(1, dts.getIdDenominacion());
            pst.setInt(2, dts.getIdMoneda());
            pst.setString(3, dts.getDescripcion());
            pst.setInt(4, dts.getValor());
            pst.setString(5, dts.getUsrCre());
            pst.setTimestamp(6, dts.getFecCre());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarDenominaciones: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
