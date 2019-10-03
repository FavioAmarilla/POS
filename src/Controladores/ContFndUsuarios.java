package Controladores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.FndUsuarios;
import Utils.FuncionesBd;

public class ContFndUsuarios {

    private String sql = "";

    public static int USR_IDENTIFICADOR;
    public static String USR_NOMBRE_COMPLETO;
    public static int USR_ID_PERFIL;
    public static String USR_PERFIL;
    public static String USR_NOMBRE;
    public static int USR_ID_CAJERO;

    public boolean login(String user, String clave) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int totalUsuarios = 0;
        try {
            sql = "SELECT U.IDENTIFICADOR, U.NOMBRE_COMPLETO, FND_PERFILES.IDENTIFICADOR AS ID_PERFIL, "
                    + "FND_PERFILES.NOMBRE AS PERFIL, U.NOMBRE, PS_CAJEROS.IDENTIFICADOR AS ID_CAJERO "
                    + "FROM FND_USUARIOS U "
                    + "LEFT JOIN FND_PERFILES ON U.ID_PERFIL = FND_PERFILES.IDENTIFICADOR "
                    + "LEFT JOIN PS_CAJEROS ON U.NOMBRE = PS_CAJEROS.USUARIO "
                    + "WHERE UPPER(U.NOMBRE) = ? AND UPPER(U.CLAVE_ACCESO) = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setString(1, user.toUpperCase());
            pst.setString(2, clave.toUpperCase());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                USR_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                USR_NOMBRE_COMPLETO = rs.getString("NOMBRE_COMPLETO");
                USR_ID_PERFIL = rs.getInt("ID_PERFIL");
                USR_PERFIL = rs.getString("PERFIL");
                USR_NOMBRE = rs.getString("NOMBRE");
                USR_ID_CAJERO = rs.getInt("ID_CAJERO");
                totalUsuarios++;
            }

            return totalUsuarios > 0;
        } catch (SQLException e) {
            ControlMensajes.error("login: " + e, ContFndUsuarios.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    public boolean impUsuarios() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndUsuarios dtsUsr = new FndUsuarios();
        int filas = 0;
        sql = "SELECT * FROM FND_USUARIOS";
        try {

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("FND_USUARIOS");
            while (rs.next()) {
                dtsUsr.setIdUsuarios(rs.getInt("IDENTIFICADOR"));
                dtsUsr.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsUsr.setNombre(rs.getString("NOMBRE"));
                dtsUsr.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                dtsUsr.setIdPerfil(rs.getInt("ID_PERFIL"));
                dtsUsr.setActivo(rs.getString("ACTIVO"));
                dtsUsr.setAbrirSesion(rs.getString("ABRIR_SESION"));
                dtsUsr.setSesionesConc(rs.getInt("SESIONES_CONC"));
                dtsUsr.setClaveAcceso(rs.getString("CLAVE_ACCESO"));
                dtsUsr.setIdSitio(rs.getInt("ID_SITIO"));
                dtsUsr.setUsrCre(rs.getString("USR_CRE"));
                dtsUsr.setFecCre(rs.getTimestamp("FEC_CRE"));
                dtsUsr.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsUsr.setDiasCambioClv(rs.getInt("DIAS_CAMBIO_CLV"));
                dtsUsr.setAutCambioUnd(rs.getString("AUT_CAMBIO_UND"));

                filas++;
                insertUsuarios(dtsUsr);
            }

            System.out.println("\tUSUARIOS IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("impUsuarios: " + e, ContFndUsuarios.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertUsuarios(FndUsuarios dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_USUARIOS(IDENTIFICADOR, ID_EMPRESA, NOMBRE, NOMBRE_COMPLETO, "
                    + "ID_PERFIL, ACTIVO, ABRIR_SESION, SESIONES_CONC, CLAVE_ACCESO, ID_SITIO, "
                    + "USR_CRE, FEC_CRE, ID_UNIDAD, DIAS_CAMBIO_CLV, AUT_CAMBIO_UND) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdUsuarios());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setString(3, dts.getNombre());
            pst.setString(4, dts.getNombreCompleto());
            pst.setInt(5, dts.getIdPerfil());
            pst.setString(6, dts.getActivo());
            pst.setString(7, dts.getAbrirSesion());
            pst.setInt(8, dts.getSesionesConc());
            pst.setString(9, dts.getClaveAcceso());
            pst.setInt(10, dts.getIdSitio());
            pst.setString(11, dts.getUsrCre());
            pst.setTimestamp(12, dts.getFecCre());
            pst.setInt(13, dts.getIdUnidad());
            pst.setInt(14, dts.getDiasCambioClv());
            pst.setString(15, dts.getAutCambioUnd());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertUsuarios: " + e, ContFndUsuarios.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    public boolean impPerfiles() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndUsuarios dtsUsr = new FndUsuarios();
        int filas = 0;
        sql = "SELECT * FROM FND_PERFILES";
        try {

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("FND_PERFILES");

            while (rs.next()) {
                dtsUsr.setIdPerfil(rs.getInt("IDENTIFICADOR"));
                dtsUsr.setNombrePerfil(rs.getString("NOMBRE"));
                dtsUsr.setSuperUsuario(rs.getString("SUPER_USUARIO"));
                dtsUsr.setUsrCre(rs.getString("USR_CRE"));
                dtsUsr.setFecCre(rs.getTimestamp("FEC_CRE"));

                filas++;
                insertPerfiles(dtsUsr);
            }

            System.out.println("\tPERFILES IMPORTADAS: " + filas);
            pst.close();
            return true;

        } catch (SQLException e) {
            ControlMensajes.error("impPerfiles: " + e, ContFndUsuarios.class
                    .getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertPerfiles(FndUsuarios dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_PERFILES(IDENTIFICADOR, NOMBRE, SUPER_USUARIO, USR_CRE, FEC_CRE) "
                    + "VALUES(?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdPerfil());
            pst.setString(2, dts.getNombrePerfil());
            pst.setString(3, dts.getSuperUsuario());
            pst.setString(4, dts.getUsrCre());
            pst.setTimestamp(5, dts.getFecCre());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insertPerfiles: " + e, ContFndUsuarios.class
                    .getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    public boolean impCajeros() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndUsuarios dtsUsr = new FndUsuarios();
        int filas = 0;
        sql = "SELECT * FROM PS_CAJEROS";
        try {

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("PS_CAJEROS");
            while (rs.next()) {
                dtsUsr.setIdPsCajeros(rs.getInt("IDENTIFICADOR"));
                dtsUsr.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsUsr.setUsuarioCaj(rs.getString("USUARIO"));
                dtsUsr.setNombreCompleto(rs.getString("NOMBRE_COMPLETO"));
                dtsUsr.setAbrirCaja(rs.getString("ABRIR_CAJA"));
                dtsUsr.setUsrCre(rs.getString("USR_CRE"));
                dtsUsr.setFecCre(rs.getTimestamp("FEC_CRE"));
                dtsUsr.setIdSitio(rs.getInt("ID_SITIO"));
                dtsUsr.setIdCategoria(rs.getInt("ID_CATEGORIA"));
                dtsUsr.setActivo(rs.getString("ACTIVO"));
                dtsUsr.setIdProcesoVenta(rs.getInt("ID_PROCESO_VENTA"));
                dtsUsr.setUsuarioExterno(rs.getString("USUARIO_EXTERNO"));
                dtsUsr.setCodigoAutoriz(rs.getString("CODIGO_AUTORIZ"));

                filas++;
                insertCajeros(dtsUsr);
            }

            System.out.println("\tCAJEROS IMPORTADAS: " + filas);
            pst.close();
            return true;

        } catch (SQLException e) {
            ControlMensajes.error("impCajeros: " + e, ContFndUsuarios.class
                    .getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertCajeros(FndUsuarios dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO PS_CAJEROS(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, USUARIO,"
                    + "CODIGO, NOMBRE_COMPLETO, ABRIR_CAJA, USR_CRE, FEC_CRE, ID_SITIO,"
                    + "ID_CATEGORIA, ACTIVO, ID_PROCESO_VENTA, USUARIO_EXTERNO, CODIGO_AUTORIZ) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdPsCajeros());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setString(4, dts.getUsuarioCaj());
            pst.setInt(5, dts.getCodigoCaj());
            pst.setString(6, dts.getNombreCompleto());
            pst.setString(7, dts.getAbrirCaja());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, dts.getFecCre());
            pst.setInt(10, dts.getIdSitio());
            pst.setInt(11, dts.getIdCategoria());
            pst.setString(12, dts.getActivo());
            pst.setInt(13, dts.getIdProcesoVenta());
            pst.setString(14, dts.getUsuarioExterno());
            pst.setString(15, dts.getCodigoAutoriz());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException e) {
            ControlMensajes.error("insertCajeros: " + e, ContFndUsuarios.class
                    .getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
