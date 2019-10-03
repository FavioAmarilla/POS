package Controladores;

import Modelos.FndMonedas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Utils.FuncionesBd;
import java.util.ArrayList;

public class ContFndMonedas {

    private final String tituloMsj = Controladores.ContFndMonedas.class.getName();
    private String sql = "";
    public int totalRegistros;

    public ArrayList<FndMonedas> getMoneda(String sql) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ArrayList<FndMonedas> lista = new ArrayList<>();

            if (rs.next()) {
                FndMonedas dtscontMonedas = new FndMonedas();
                dtscontMonedas.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtscontMonedas.setCodigo(rs.getString("CODIGO"));

                lista.add(dtscontMonedas);
            }

            return lista;
        } catch (SQLException e) {
            ControlMensajes.error("GetMoneda: " + e, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean importarMonedas() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndMonedas dtsMon = new FndMonedas();
        int filas = 0;
        sql = "SELECT * FROM FND_MONEDAS";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("FND_MONEDAS");

            while (rs.next()) {
                dtsMon.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsMon.setCodigo(rs.getString("CODIGO"));
                dtsMon.setDescripcion(rs.getString("DESCRIPCION"));
                dtsMon.setUsrCre(rs.getString("USR_CRE"));
                dtsMon.setFecCre(rs.getTimestamp("FEC_CRE"));
                dtsMon.setDecimales(rs.getInt("DECIMALES"));
                dtsMon.setIdEmpresa(rs.getString("ID_EMPRESA"));
                dtsMon.setIdUnnidad(rs.getString("ID_UNIDAD"));
                dtsMon.setMascara(rs.getString("MASCARA"));
                dtsMon.setEsPrincipal(rs.getString("ES_PRINCIPAL"));

                filas++;
                insertarcontMonedas(dtsMon);
            }

            System.out.println("\tMONEDAS IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("importarMonedas: " + e, ContTcCajas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarcontMonedas(FndMonedas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_MONEDAS(IDENTIFICADOR, CODIGO, DESCRIPCION, USR_CRE, "
                    + "FEC_CRE, DECIMALES,ID_EMPRESA, ID_UNIDAD, MASCARA, ES_PRINCIPAL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getCodigo());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getUsrCre());
            pst.setTimestamp(5, dts.getFecCre());
            pst.setInt(6, dts.getDecimales());
            pst.setString(7, dts.getIdEmpresa());
            pst.setString(8, dts.getIdUnnidad());
            pst.setString(9, dts.getMascara());
            pst.setString(10, dts.getEsPrincipal());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarMonedas: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
