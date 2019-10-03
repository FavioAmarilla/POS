package Controladores;

import Modelos.FndTurnosTrabajo;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContFndTurnosTrabajo {

   
    private String sql = "";

    //FND_TURNOS_TRABAJO
    public boolean impTurnosTrabajo() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndTurnosTrabajo dts = new FndTurnosTrabajo();
        int filas = 0;
        sql = "SELECT * FROM FND_TURNOS_TRABAJO";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            vaciar_tabla("FND_TURNOS_TRABAJO");
            while (rs.next()) {
                dts.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dts.setNombre(rs.getString("NOMBRE"));
                dts.setHoraInicio(rs.getTimestamp("HORA_INICIO"));
                dts.setHoraFin(rs.getTimestamp("HORA_FIN"));
                dts.setUsrCre(rs.getString("USR_CRE"));

                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setAbreviatura(rs.getString("ABREVIATURA"));
                dts.setDiaSemana(rs.getString("DIA_SEMANA"));
                dts.setDiaFeriado(rs.getString("DIA_FERIADO"));

                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));

                filas++;
                insrTurnosTrabajo(dts);
            }

            System.out.println("\tTURNOS DE TRABAJO IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("impTurnosTrabajo: " + e, ContFndTurnosTrabajo.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insrTurnosTrabajo(FndTurnosTrabajo dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_TURNOS_TRABAJO(IDENTIFICADOR, NOMBRE, HORA_INICIO, "
                    + "HORA_FIN, USR_CRE, FEC_CRE, DESCRIPCION, ABREVIATURA, "
                    + "DIA_SEMANA, DIA_FERIADO, ID_EMPRESA, ID_UNIDAD) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getNombre());
            pst.setTimestamp(3, dts.getHoraInicio());
            pst.setTimestamp(4, dts.getHoraFin());
            pst.setString(5, dts.getUsrCre());
            pst.setTimestamp(6, dts.getFecCre());
            pst.setString(7, dts.getDescripcion());
            pst.setString(8, dts.getAbreviatura());
            pst.setString(9, dts.getDiaSemana());
            pst.setString(10, dts.getDiaFeriado());
            pst.setInt(11, dts.getIdEmpresa());
            pst.setInt(12, dts.getIdUnidad());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insrTurnosTrabajo: " + e, ContFndTurnosTrabajo.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //TC_TURNOS_CAJA
    public boolean impTurnosCaja() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndTurnosTrabajo dts = new FndTurnosTrabajo();
        int filas = 0;
        sql = "SELECT * FROM TC_TURNOS_CAJA";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            vaciar_tabla("TC_TURNOS_CAJA");
            while (rs.next()) {
                dts.setIdCaja(rs.getInt("ID_CAJA"));
                dts.setIdTurno(rs.getInt("ID_TURNO"));
                dts.setfechaDesde(rs.getTimestamp("FECHA_DESDE"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));

                dts.setfechaHasta(rs.getTimestamp("FECHA_HASTA"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));

                filas++;
                insrTurnosCaja(dts);
            }

            System.out.println("\tURNOS DE CAJA IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("impTurnosCaja: " + e, ContParmEmpresas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insrTurnosCaja(FndTurnosTrabajo dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_TURNOS_CAJA(ID_CAJA, ID_TURNO, FECHA_DESDE, "
                    + "USR_CRE, FEC_CRE, FECHA_HASTA, "
                    + "ID_EMPRESA, ID_UNIDAD) "
                    + "VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdCaja());
            pst.setInt(2, dts.getIdTurno());
            pst.setTimestamp(3, dts.getfechaDesde());
            pst.setString(4, dts.getUsrCre());
            pst.setTimestamp(5, dts.getFecCre());
            pst.setTimestamp(6, dts.getfechaHasta());
            pst.setInt(7, dts.getIdEmpresa());
            pst.setInt(8, dts.getIdUnidad());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insrTurnosCaja: " + e, ContParmEmpresas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean vaciar_tabla(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "DELETE FROM " + tabla + "";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("vaciar_tabla " + e, this.getClass().getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }
}
