package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.VtaClientes;
import Utils.FuncionesBd;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class ContVtaClientes {

    String tituloMsj = ContVtaClientes.class.getName();
    SimpleDateFormat formato;
    private String sql;
    long contador = 0;

    public int ultNovedad() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int ultNovedad = 0;
            sql = "SELECT ULTIMO_ID_NOVEDAD FROM POS_APLIC_NOVEDADES WHERE NOMBRE_TABLA = 'VTA_CLIENTES'";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                ultNovedad = rs.getInt(1);
            }

            pst.close();
            return ultNovedad;
        } catch (SQLException ex) {
            ControlMensajes.error("ultNovedad: " + ex, tituloMsj);
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean novedades() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int insert = 0;
            long idCliente = 0;
            int idNovedad = 0;
            String accion = "";

            PreparedStatement pst = jdbc.server().prepareStatement("SELECT IDENTIFICADOR, IDENTIFICADOR_1, "
                    + "TIPO_OPERACION FROM POS_REGISTRO_NOVEDADES "
                    + "WHERE NOMBRE_TABLA = 'VTA_CLIENTES' AND IDENTIFICADOR > ? ORDER BY IDENTIFICADOR ASC");
            pst.setInt(1, ultNovedad());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                idNovedad = rs.getInt(1);
                idCliente = rs.getLong(2);
                accion = rs.getString(3);

                if (!Importar(idCliente, idNovedad, accion)) {
                    return false;
                }
                insert++;
            }

            System.out.println("\tCLIENTES IMPORTADOS: " + insert);

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("novedades (clientes): " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean Importar(long idCliente, int ultIdNovedad, String accion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            VtaClientes dts = new VtaClientes();

            PreparedStatement pst = jdbc.server().prepareStatement("SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, CODIGO, ACTIVO, ID_CATEGORIA, "
                    + "VR_TIPO_PERSONA, USR_CRE, FEC_CRE, COALESCE(APELLIDOS, ' ') AS APELLIDOS, COALESCE(NOMBRES, ' ') AS NOMBRES, NUMERO_RUC,"
                    + "USR_MOD, FEC_MOD, RAZON_SOCIAL, DIRECCION, TELEFONO, VR_TIPO_DOCUM, NUMERO_DOCUMENTO, NOMBRE_BARRIO, NOMBRE_CIUDAD, FECHA_INSCR_CLF"
                    + " FROM VTA_CLIENTES WHERE IDENTIFICADOR = ?");
            pst.setLong(1, idCliente);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setCodigo(rs.getString("CODIGO"));
                dts.setActivo(rs.getString("ACTIVO"));
                dts.setIdCategoria(rs.getInt("ID_CATEGORIA"));
                dts.setVrTipoPersona(rs.getString("VR_TIPO_PERSONA"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setApellidos(rs.getString("APELLIDOS"));
                dts.setNombres(rs.getString("NOMBRES"));
                dts.setRazon_social(rs.getString("RAZON_SOCIAL"));
                dts.setDireccion(rs.getString("DIRECCION"));
                dts.setTelefono(rs.getString("TELEFONO"));
                dts.setVrTipoDocumento(rs.getString("VR_TIPO_DOCUM"));
                dts.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
                dts.setNumeroRuc(rs.getString("NUMERO_RUC"));
                dts.setNombreBarrio(rs.getString("NOMBRE_BARRIO"));
                dts.setNombreCiudad(rs.getString("NOMBRE_CIUDAD"));
                dts.setfechaInscClt(rs.getTimestamp("FECHA_INSCR_CLF"));

                if (accion.equals("I")) {
                    if (!insert(dts, "L")) {
                        System.out.println("\tCliente no insertado");
                    }
                    if (!actualizarNovedades(ultIdNovedad)) {
                        System.out.println("\t" + contador + "- ULTIMO ID NO ACTUALIZADO (VTA_CLIENTES)");
                    }

                    if (!enviado(dts.getIdentificador())) {
                        System.out.println("\tCLIENTE NO ACTUALIZADO: Exportar");
                    }
                    contador++;
                    System.out.println("\t" + contador + "- ULTIMO ID ACTUALIZADO (VTA_CLIENTES)");
                }

                if (accion.equals("U")) {
                    if (!update(dts)) {
                        System.out.println("\tCliente no actualizado");
                    }
                    if (!actualizarNovedades(ultIdNovedad)) {
                        System.out.println("\t" + contador + "- ULTIMO ID NO ACTUALIZADO (VTA_CLIENTES)");
                    }
                    contador++;
                    System.out.println("\t" + contador + "- ULTIMO ID ACTUALIZADO (VTA_CLIENTES)");
                }
            }

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("Importar: " + ex, ContExportarTransacciones.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean Exportar() {
        ConexionJdbc jdbc = new ConexionJdbc();
        int enviados = 0;
        try {
            VtaClientes dts = new VtaClientes();
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, CODIGO, ACTIVO, ID_CATEGORIA, "
                    + "VR_TIPO_PERSONA, USR_CRE, FEC_CRE, COALESCE(APELLIDOS, '') AS APELLIDOS, COALESCE(NOMBRES, '') AS NOMBRES, NUMERO_RUC,"
                    + "USR_MOD, FEC_MOD, RAZON_SOCIAL "
                    + "FROM VTA_CLIENTES WHERE ENV_SERVER  <> 'S'");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dts.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setCodigo(rs.getString("CODIGO"));
                dts.setActivo(rs.getString("ACTIVO"));
                dts.setIdCategoria(rs.getInt("ID_CATEGORIA"));
                dts.setVrTipoPersona(rs.getString("VR_TIPO_PERSONA"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setApellidos(rs.getString("APELLIDOS"));
                dts.setNombres(rs.getString("NOMBRES"));
                dts.setNumeroRuc(rs.getString("NUMERO_RUC"));
                dts.setEnvServer("S");
                dts.setUsrMod(rs.getString("USR_MOD"));
                dts.setFecMod(rs.getTimestamp("FEC_MOD"));
                dts.setRazon_social(rs.getString("RAZON_SOCIAL"));

                if (!insert(dts, "S")) {
                    System.out.println("\tCLIENTE NO EXPORTADO: Exportar");
                    return false;
                }

                if (!enviado(dts.getIdentificador())) {
                    System.out.println("\tCLIENTE NO ACTUALIZADO: Exportar");
                    return false;
                }
                enviados++;
                System.out.println("\t CLIENTE ENVIADO AL SERVIDOR: " + dts.getNombres());
            }
            System.out.println("\tCLIENTES EXPORTADOS: " + enviados);
            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("Exportar: " + ex, ContExportarTransacciones.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert(VtaClientes dts, String conexion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        PreparedStatement pst = null;
        try {
            if (conexion.equals("L")) {
                pst = jdbc.local().prepareStatement("INSERT INTO VTA_CLIENTES (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, "
                        + "CODIGO, ACTIVO, ID_CATEGORIA, VR_TIPO_PERSONA, USR_CRE, FEC_CRE, "
                        + "USR_MOD, FEC_MOD, APELLIDOS, NOMBRES, RAZON_SOCIAL, DIRECCION, "
                        + "TELEFONO, VR_TIPO_DOCUM, NUMERO_DOCUMENTO, NUMERO_RUC, "
                        + "NOMBRE_BARRIO, NOMBRE_CIUDAD, FECHA_INSCR_CLF, ENV_SERVER) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            }
            if (conexion.equals("S")) {
                pst = jdbc.server().prepareStatement("INSERT INTO VTA_CLIENTES (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, "
                        + "CODIGO, ACTIVO, ID_CATEGORIA, VR_TIPO_PERSONA, USR_CRE, FEC_CRE, "
                        + "USR_MOD, FEC_MOD, APELLIDOS, NOMBRES, RAZON_SOCIAL, DIRECCION, "
                        + "TELEFONO, VR_TIPO_DOCUM, NUMERO_DOCUMENTO, NUMERO_RUC, "
                        + "NOMBRE_BARRIO, NOMBRE_CIUDAD, FECHA_INSCR_CLF, ENV_SERVER) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            }

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setString(4, dts.getCodigo());
            pst.setString(5, dts.getActivo());
            pst.setInt(6, dts.getIdCategoria());
            pst.setString(7, dts.getVrTipoPersona());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, FuncionesBd.sysdate());
            pst.setString(10, dts.getUsrMod());
            pst.setTimestamp(11, FuncionesBd.sysdate());
            pst.setString(12, dts.getApellidos());
            pst.setString(13, dts.getNombres());
            pst.setString(14, dts.getRazon_social());
            pst.setString(15, dts.getDireccion());
            pst.setString(16, dts.getTelefono());
            pst.setString(17, dts.getVrTipoDocumento());
            pst.setString(18, dts.getNumeroRuc());
            pst.setString(19, dts.getNumeroRuc());
            pst.setString(20, dts.getNombreBarrio());
            pst.setString(21, dts.getNombreCiudad());
            pst.setTimestamp(22, dts.getfechaInscClt());
            pst.setString(23, dts.getEnvServer());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("insert: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean update(VtaClientes dts) {

        ConexionJdbc jdbc = new ConexionJdbc();
        try {

            PreparedStatement pst = jdbc.local().prepareStatement("UPDATE VTA_CLIENTES SET ID_EMPRESA=?, ID_UNIDAD=?, "
                    + "CODIGO=?, ACTIVO=?, ID_CATEGORIA=?, VR_TIPO_PERSONA=?, "
                    + "USR_MOD=?, FEC_MOD=?, APELLIDOS=?, NOMBRES=?, RAZON_SOCIAL=?, DIRECCION=?, "
                    + "TELEFONO=?, VR_TIPO_DOCUM=?, NUMERO_DOCUMENTO=?, NUMERO_RUC=?, "
                    + "NOMBRE_BARRIO=?, NOMBRE_CIUDAD=?, FECHA_INSCR_CLF=?, ENV_SERVER=? "
                    + "WHERE IDENTIFICADOR = ?");

            pst.setInt(1, dts.getIdEmpresa());
            pst.setInt(2, dts.getIdUnidad());
            pst.setString(3, dts.getCodigo());
            pst.setString(4, dts.getActivo());
            pst.setInt(5, dts.getIdCategoria());
            pst.setString(6, dts.getVrTipoPersona());
            pst.setString(7, dts.getUsrMod());
            pst.setTimestamp(8, FuncionesBd.sysdate());
            pst.setString(9, dts.getApellidos());
            pst.setString(10, dts.getNombres());
            pst.setString(11, dts.getRazon_social());
            pst.setString(12, dts.getDireccion());
            pst.setString(13, dts.getTelefono());
            pst.setString(14, dts.getVrTipoDocumento());
            pst.setString(15, dts.getNumeroDocumento());
            pst.setString(16, dts.getNumeroRuc());
            pst.setString(17, dts.getNombreBarrio());
            pst.setString(18, dts.getNombreCiudad());
            pst.setTimestamp(19, dts.getfechaInscClt());
            pst.setString(20, "S");
            pst.setLong(21, dts.getIdentificador());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("update: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

//    public boolean existeCliente(int idCliente, String conexion) {
//        ConexionJdbc jdbc = new ConexionJdbc();
//        PreparedStatement pst = null;
//        boolean existe = false;
//        try {
//            if (conexion.equals("L")) {
//                pst = jdbc.local().prepareStatement("SELECT 1 FROM VTA_CLIENTES WHERE IDENTIFICADOR = ?");
//
//            }
//            if (conexion.equals("S")) {
//                pst = jdbc.server().prepareStatement("SELECT 1 FROM VTA_CLIENTES WHERE IDENTIFICADOR = ?");
//
//            }
//            pst.setInt(1, idCliente);
//
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                existe = true;
//            }
//
//            pst.close();
//            return existe;
//        } catch (SQLException ex) {
//            ControlMensajes.error("existeCliente: " + ex, tituloMsj);
//            return false;
//        } finally {
//            jdbc.cerrarConexion();
//        }
//    }
    public boolean enviado(long idCliente) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            PreparedStatement pst = jdbc.local().prepareStatement("UPDATE VTA_CLIENTES SET ENV_SERVER='S' "
                    + "WHERE IDENTIFICADOR = ?");
            pst.setLong(1, idCliente);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("enviado: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarNovedades(int numero) {
        ConexionJdbc jdbc = new ConexionJdbc();
        PreparedStatement pst = null;
        try {
            sql = "UPDATE POS_APLIC_NOVEDADES SET ULTIMO_ID_NOVEDAD = ? WHERE NOMBRE_TABLA = 'VTA_CLIENTES'";

            pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, numero);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("actualizarNovedades: " + ex, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public ResultSet equivalrenciaRuc(String ruc) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT NOMBRE_COMPLETO,RUC_NUEVO FROM CP_EQUIVALENCIA_RUC "
                    + "WHERE RUC_NUEVO LIKE '" + ruc + "%'";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rsc = pst.executeQuery();

            return rsc;
        } catch (SQLException ex) {
            ControlMensajes.error("ERROR (equivalrenciaRuc): " + ex, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public ResultSet vtaClientes(String ruc) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, NOMBRES, APELLIDOS, "
                    + "COALESCE(DIRECCION,'-'), COALESCE(TELEFONO,'-'), NUMERO_RUC "
                    + "FROM VTA_CLIENTES WHERE NUMERO_RUC LIKE '" + ruc + "%'";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rse = pst.executeQuery();

            return rse;

        } catch (SQLException ex) {
            ControlMensajes.error("ERROR (vtaClientes): " + ex, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public DefaultTableModel vistaCliente(String nombre) {
        ConexionJdbc jdbc = new ConexionJdbc();
        DefaultTableModel modelo;
        String[] titulos = {"NRO. RUC", "NOMBRE Y APELLIDO",};
        String[] registro = new String[6];
        modelo = new DefaultTableModel(null, titulos);

        sql = "SELECT NUMERO_RUC, NOMBRES || ', ' || APELLIDOS "
                + "FROM VTA_CLIENTES WHERE NOMBRES || ' ' || APELLIDOS LIKE '%" + nombre + "%' "
                + "AND ACTIVO='S'";

        try {

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString(1);
                registro[1] = rs.getString(2);
                modelo.addRow(registro);
            }

            pst.close();
            return modelo;
        } catch (SQLException ex) {
            ControlMensajes.error("select: " + ex, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
