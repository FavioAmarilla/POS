package Controladores;

import Modelos.TcDeclaracionEfec;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Vistas.FrmLogin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContTcDeclaracionEfec {

   
    TcDeclaracionEfec dtsDecEfect = new TcDeclaracionEfec();

    private String sql;

    public Timestamp sysdate() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            Timestamp fecha;
            sql = "SELECT SYSDATE FROM DUAL";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fecha = rs.getTimestamp(1);
            } else {
                fecha = null;
            }
            pst.close();
            return fecha;
        } catch (SQLException ex) {
            ControlMensajes.error("sysdate: " + ex, ContTcControlCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int idControlCaja(String fecha) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int id = 0;

        sql = "SELECT IDENTIFICADOR FROM TC_CONTROL_CAJA WHERE "
                + "ID_EMPRESA=" + ContParamAplicacion.EMP_IDENTIFICADOR + " AND "
                + "ID_UNIDAD=" + ContParamAplicacion.UND_IDENTIFICADOR + " AND "
                + "ID_CAJA=" + ContParamAplicacion.CAJA_IDENTIFICADOR + " AND "
                + "TRUNC(FECHA_APERT) <= TO_DATE('" + fecha + "','dd/mm/yyyy') AND "
                + "CERRADO = 'S' ";

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                id = 0;
            }

            pst.close();
            return id;
        } catch (SQLException e) {
            ControlMensajes.error("idControlCaja: " + e, ContTcDeclaracionEfec.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert(TcDeclaracionEfec dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_DECLARACION_EFEC (ID_ARQUEO, ID_DENOMINACION, ID_EMPRESA, ID_UNIDAD, "
                    + "CANTIDAD, IMPORTE, USR_CRE, FEC_CRE)"
                    + "VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement pst = null;

            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdArqueo());
            pst.setInt(2, dts.getIdDenominacion());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getIdUnidad());
            pst.setInt(5, dts.getCantidad());
            pst.setLong(6, dts.getImporte());
            pst.setString(7, dts.getUsrCre());
            pst.setTimestamp(8,sysdate());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException ex) {
            ControlMensajes.error("insert: " + ex, ContTcDeclaracionEfec.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //=============================================
    //EXPORTAR DECLARACIONES DE EFECTIVO NO ENVIADOS A SERVER 
    //=============================================
    public boolean expDecEfectivo(long idArqueo) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int fila = 0;
            sql = "SELECT * FROM TC_DECLARACION_EFEC WHERE ID_ARQUEO =" + idArqueo + "";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dtsDecEfect.setIdArqueo(rs.getLong("ID_ARQUEO"));
                dtsDecEfect.setIdDenominacion(rs.getInt("ID_DENOMINACION"));
                dtsDecEfect.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsDecEfect.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsDecEfect.setCantidad(rs.getInt("CANTIDAD"));
                dtsDecEfect.setImporte(rs.getInt("IMPORTE"));
                dtsDecEfect.setUsrCre(rs.getString("USR_CRE"));
                dtsDecEfect.setFeCre(rs.getTimestamp("FEC_CRE"));

                if (insert(dtsDecEfect, 1)) { //SE ENVIA EL ARQUEO AL SERVER
                    fila++;
                } else {
                    System.out.println("ARQUEO NRO (" + dtsDecEfect.getIdArqueo() + ") NO FUE ENVIADO A SERVER");
                    System.out.println("----------------------------------------");
                }
            }

            System.out.println("\tDECLARACION DE ARQUEO NRO (" + dtsDecEfect.getIdArqueo() + ") EXPORTADOS: " + fila);
            pst.close();
            return true;

        } catch (SQLException ex) {
            ControlMensajes.error("expDecEfectivo: " + ex, ContTcDeclaracionEfec.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
