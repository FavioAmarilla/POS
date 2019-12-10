package Controladores;

import Modelos.VtaControlComprob;
import Modelos.VtaAsignacionComp;
import Modelos.VtaTiposComprob;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Utils.FuncionesBd;
import Utils.Utilidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContVtaControlComprob {

    private final String tituloMsj = ContVtaControlComprob.class.getName();
    private String sql = "";
    int identificador;

    VtaAsignacionComp dtsAsignacionComp = new VtaAsignacionComp();
    VtaControlComprob dtsControlComprob = new VtaControlComprob();

    ContTcCajas contCaja = new ContTcCajas();

    public boolean importarControlComprob() {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT IDENTIFICADOR, ID_EMPRESA, ID_SITIO, NUMERO, FECHA_DESDE, "
                + "FECHA_HASTA , ACTIVO, LONG_NRO_COMPROB, ID_UNIDAD, VR_USO, "
                + "NRO_INICIAL, NRO_FINAL, CODIGO_POS, FECHA_VENCIMIENTO, USR_CRE "
                + "FROM VTA_CONTROL_COMPROB "
                + "WHERE ID_EMPRESA = ? "
                + "AND ID_SITIO = ? "
                + "AND ID_UNIDAD = ? "
                + "AND FECHA_DESDE <= ? "
                + "AND FECHA_HASTA >= ? "
                + "AND VR_USO = 'AI'"
                + "AND ACTIVO = 'S' ";
        try {

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MMM/yyyy"));
            pst.setString(5, Utilidades.getFecha("dd/MMM/yyyy"));

            ResultSet rs = pst.executeQuery();

            int filas = 0;

            FuncionesBd.truncate("VTA_CONTROL_COMPROB");
            while (rs.next()) {
                dtsControlComprob.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsControlComprob.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsControlComprob.setIdSitio(rs.getInt("ID_SITIO"));
                dtsControlComprob.setNumero(rs.getInt("NUMERO"));
                dtsControlComprob.setfechaDesde(rs.getTimestamp("FECHA_DESDE"));
                dtsControlComprob.setfechaHasta(rs.getTimestamp("FECHA_HASTA"));
                dtsControlComprob.setActivo(rs.getString("ACTIVO"));
                dtsControlComprob.setLongNroComprob(rs.getInt("LONG_NRO_COMPROB"));
                dtsControlComprob.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsControlComprob.setVrUso(rs.getString("VR_USO"));
                dtsControlComprob.setNroInicial(rs.getInt("NRO_INICIAL"));
                dtsControlComprob.setNroFinal(rs.getInt("NRO_FINAL"));
                dtsControlComprob.setCodigoPos(rs.getInt("CODIGO_POS"));
                dtsControlComprob.setfechaVenc(rs.getTimestamp("FECHA_VENCIMIENTO"));
                dtsControlComprob.setUsrCre(rs.getString("USR_CRE"));
                dtsControlComprob.setFecCre(FuncionesBd.sysdate());

                filas++;
                insertarControlComprob(dtsControlComprob);
            }

            System.out.println("\tCONTROL DE COMPROB. IMPORTADOS: " + filas);
            pst.close();
            return true;

        } catch (SQLException e) {
            ControlMensajes.error("ERROR (importarControlComprob): " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarControlComprob(VtaControlComprob dtsControlComprob) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_CONTROL_COMPROB(IDENTIFICADOR, ID_EMPRESA, ID_SITIO, "
                    + "NUMERO, FECHA_DESDE, FECHA_HASTA, ACTIVO, LONG_NRO_COMPROB, "
                    + "ID_UNIDAD,VR_USO, NRO_INICIAL, NRO_FINAL, CODIGO_POS, "
                    + "FECHA_VENCIMIENTO, USR_CRE, FEC_CRE) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dtsControlComprob.getIdentificador());
            pst.setInt(2, dtsControlComprob.getIdEmpresa());
            pst.setInt(3, dtsControlComprob.getIdSitio());
            pst.setInt(4, dtsControlComprob.getNumero());
            pst.setTimestamp(5, dtsControlComprob.getfechaDesde());
            pst.setTimestamp(6, dtsControlComprob.getfechaHasta());
            pst.setString(7, dtsControlComprob.getActivo());
            pst.setInt(8, dtsControlComprob.getLongNroComprob());
            pst.setInt(9, dtsControlComprob.getIdUnidad());
            pst.setString(10, dtsControlComprob.getVrUso());
            pst.setInt(11, dtsControlComprob.getNroInicial());
            pst.setInt(12, dtsControlComprob.getNroFinal());
            pst.setInt(13, dtsControlComprob.getCodigoPos());
            pst.setTimestamp(14, dtsControlComprob.getfechaVenc());
            pst.setString(15, dtsControlComprob.getUsrCre());
            pst.setTimestamp(16, dtsControlComprob.getFecCre());

            int x = pst.executeUpdate();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarControlComprob: " + e, ContVtaControlComprob.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public int idRegCtrl() {
        ConexionJdbc jdbc = new ConexionJdbc();
        int idReg = 0;
        try {
            sql = "SELECT IDENTIFICADOR FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD = ? "
                    + "AND TRUNC(FECHA_DESDE) <= TO_DATE(?, 'dd/MM/yyyy') "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?, 'dd/MM/yyyy') "
                    + "AND VR_USO = 'AI'"
                    + "AND ACTIVO = 'S' ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MMM/yyyy"));
            pst.setString(5, Utilidades.getFecha("dd/MMM/yyyy"));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idReg = rs.getInt(1);
            }

            return idReg;
        } catch (SQLException e) {
            ControlMensajes.error("idRegCtrl: " + e, ContVtaControlComprob.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean existeAsignacionComp() {
        ConexionJdbc jdbc = new ConexionJdbc();
        boolean existe = false;
        sql = "SELECT IDENTIFICADOR, ULT_NRO_USADO  FROM VTA_ASIGNACION_COMP "
                + "WHERE ID_REGISTRO_CTRL= ? AND ID_PUNTO_EMISION = ? ";

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idRegCtrl());
            pst.setInt(2, ContParamAplicacion.PEM_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                existe = true;
            }

            pst.close();
            return existe;
        } catch (SQLException e) {
            ControlMensajes.error("existeAsignacionComp: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean exportarAsignacionComp() {
        if (!existeAsignacionComp()) {
            return true;
        }
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT IDENTIFICADOR, ULT_NRO_USADO  FROM VTA_ASIGNACION_COMP "
                + "WHERE ID_REGISTRO_CTRL= ? AND ID_PUNTO_EMISION = ? ";

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idRegCtrl());
            pst.setInt(2, ContParamAplicacion.PEM_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dtsAsignacionComp.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsAsignacionComp.setUltNroUsado(rs.getInt("ULT_NRO_USADO"));
                if (!actualizarAsignacionComp(dtsAsignacionComp)) {
                    ControlMensajes.error("Ultimo Numero Usano no actualizado en servidor", tituloMsj);
                    return false;
                }
            }

            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR(importarAsignacionComprob) \n " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarAsignacionComp(VtaAsignacionComp dtsAsignacionComp) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE VTA_ASIGNACION_COMP SET ULT_NRO_USADO=? WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            pst.setInt(1, dtsAsignacionComp.getUltNroUsado());
            pst.setInt(2, dtsAsignacionComp.getIdentificador());
            int x = pst.executeUpdate();

            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("actualizarAsignacionComp: " + e, tituloMsj);
            return false;
        }

    }

    public boolean importarAsignacionComp() {
        if (!exportarAsignacionComp()) {
            return false;
        }

        ConexionJdbc jdbc = new ConexionJdbc();
        int filas = 0;
        sql = "SELECT * FROM VTA_ASIGNACION_COMP WHERE ID_REGISTRO_CTRL= ? AND ID_PUNTO_EMISION = ? ";

        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, idRegCtrl());
            pst.setInt(2, ContParamAplicacion.PEM_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                FuncionesBd.truncate("VTA_ASIGNACION_COMP");

                dtsAsignacionComp.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsAsignacionComp.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsAsignacionComp.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsAsignacionComp.setfecha(rs.getTimestamp("FECHA"));
                dtsAsignacionComp.setActivo(rs.getString("ACTIVO"));
                dtsAsignacionComp.setUsrCre(rs.getString("USR_CRE"));
                dtsAsignacionComp.setFecCre(FuncionesBd.sysdate());
                dtsAsignacionComp.setNroDesde(rs.getInt("NUMERO_DESDE"));
                dtsAsignacionComp.setNroHasta(rs.getInt("NUMERO_HASTA"));
                dtsAsignacionComp.setIdSitio(rs.getInt("ID_SITIO"));
                dtsAsignacionComp.setIdPuntoEmision(rs.getInt("ID_PUNTO_EMISION"));
                dtsAsignacionComp.setIdRegistroCtrol(rs.getInt("ID_REGISTRO_CTRL"));
                dtsAsignacionComp.setCodigoPos(rs.getInt("CODIGO_POS"));
                dtsAsignacionComp.setUltNroUsado(rs.getInt("ULT_NRO_USADO"));

                if (!insertarAsignacionComp(dtsAsignacionComp)) {
                    ControlMensajes.error("Asignacion de comprobante no Registrado", tituloMsj);
                    return false;
                }

                filas++;
                System.out.println("\tASIG. DE COMPROBANTES IMPORTADOS: " + filas);

            }
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR(importarAsignacionComprob) \n " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarAsignacionComp(VtaAsignacionComp dtsAsignacionComp) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_ASIGNACION_COMP(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, FECHA, "
                    + "ACTIVO, USR_CRE, FEC_CRE, NUMERO_DESDE, NUMERO_HASTA, ID_SITIO, "
                    + "ID_PUNTO_EMISION, ULT_NRO_USADO,ID_REGISTRO_CTRL, CODIGO_POS) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dtsAsignacionComp.getIdentificador());
            pst.setInt(2, dtsAsignacionComp.getIdEmpresa());
            pst.setInt(3, dtsAsignacionComp.getIdUnidad());
            pst.setTimestamp(4, dtsAsignacionComp.getfecha());
            pst.setString(5, dtsAsignacionComp.getActivo());
            pst.setString(6, dtsAsignacionComp.getUsrCre());
            pst.setTimestamp(7, dtsAsignacionComp.getFecCre());
            pst.setInt(8, dtsAsignacionComp.getNroDesde());
            pst.setInt(9, dtsAsignacionComp.getNroHasta());
            pst.setInt(10, dtsAsignacionComp.getIdSitio());
            pst.setInt(11, dtsAsignacionComp.getIdPuntoEmision());
            pst.setInt(12, dtsAsignacionComp.getUltNroUsado());
            pst.setInt(13, dtsAsignacionComp.getIdRegistroCtrol());
            pst.setInt(14, dtsAsignacionComp.getCodigoPos());

            int x = pst.executeUpdate();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR(insertarAsignacionComp) \n " + e, tituloMsj);
            return false;
        }

    }

    public boolean importarTiposComprob() {
        ConexionJdbc jdbc = new ConexionJdbc();
        VtaTiposComprob tipo = new VtaTiposComprob();
        int filas = 0;

        sql = "SELECT * FROM VTA_TIPOS_COMPROB "
                + "WHERE ID_EMPRESA = ? "
                + "AND ID_UNIDAD= ? "
                + "AND CODIGO_POS = '1' ";

        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.UND_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("VTA_TIPOS_COMPROB");

            while (rs.next()) {
                tipo.setIdentificador(rs.getInt("IDENTIFICADOR"));
                tipo.setDescripcion(rs.getString("DESCRIPCION"));
                tipo.setVrDebitoCre(rs.getString("VR_DEBITO_CRED"));
                tipo.setUsrCre(rs.getString("USR_CRE"));
                tipo.setFecCre(rs.getTimestamp("FEC_CRE"));
                tipo.setAbreviatura(rs.getString("ABREVIATURA"));
                tipo.setLineasLimite(rs.getInt("LIMITE_LINEAS"));
                tipo.setVrClaseComp(rs.getString("VR_CLASE_COMP"));
                tipo.setIdProgEmision(rs.getInt("ID_PROG_EMISION"));
                tipo.setVrGrupoImp(rs.getString("VR_GRUPO_IMP"));
                tipo.setCodigoPos(rs.getString("CODIGO_POS"));
                tipo.setActivo(rs.getString("ACTIVO"));
                tipo.setVrTipoNumeracion(rs.getString("VR_TIPO_NUMERACION"));
                tipo.setIdEmpresa(rs.getString("ID_EMPRESA"));
                tipo.setIdUnidad(rs.getString("ID_UNIDAD"));

                filas++;
                insertarTiposComprob(tipo);
            }

            System.out.println("\tTIPOS DE COMPROBANTES IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR(importarTiposComprob) \n " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarTiposComprob(VtaTiposComprob dtsAsignacionComp) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_TIPOS_COMPROB(IDENTIFICADOR, DESCRIPCION, VR_DEBITO_CRED,"
                    + "USR_CRE, FEC_CRE, ABREVIATURA, LIMITE_LINEAS, VR_CLASE_COMP, "
                    + "ID_PROG_EMISION, VR_GRUPO_IMP, CODIGO_POS, ACTIVO, VR_TIPO_NUMERACION, "
                    + "ID_EMPRESA, ID_UNIDAD) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dtsAsignacionComp.getIdentificador());
            pst.setString(2, dtsAsignacionComp.getDescripcion());
            pst.setString(3, dtsAsignacionComp.getVrDebitoCre());
            pst.setString(4, dtsAsignacionComp.getUsrCre());
            pst.setTimestamp(5, dtsAsignacionComp.getFecCre());
            pst.setString(6, dtsAsignacionComp.getAbreviatura());
            pst.setInt(7, dtsAsignacionComp.getLineasLimite());
            pst.setString(8, dtsAsignacionComp.getVrClaseComp());
            pst.setInt(9, dtsAsignacionComp.getIdProgEmision());
            pst.setString(10, dtsAsignacionComp.getVrGrupoImp());
            pst.setString(11, dtsAsignacionComp.getCodigoPos());
            pst.setString(12, dtsAsignacionComp.getActivo());
            pst.setString(13, dtsAsignacionComp.getVrTipoNumeracion());
            pst.setString(14, dtsAsignacionComp.getIdEmpresa());
            pst.setString(15, dtsAsignacionComp.getIdUnidad());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarTiposComprob: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public int[] timbrado() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int[] datos = new int[2];

            PreparedStatement pst = jdbc.local().prepareStatement("SELECT IDENTIFICADOR, NUMERO "
                    + "FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD = ? "
                    + "AND FECHA_DESDE <= ? "
                    + "AND FECHA_HASTA >= ? "
                    + "AND VR_USO = 'AI'"
                    + "AND ACTIVO = 'S' ");
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MMM/yyyy"));
            pst.setString(5, Utilidades.getFecha("dd/MMM/yyyy"));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                datos[0] = rs.getInt(1);
                datos[1] = rs.getInt(2);
            }

            pst.close();
            return datos;
        } catch (SQLException ex) {
            ControlMensajes.error("timbrado: " + ex, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean validarPeriodoGestion(String fecha) {
        ConexionJdbc jdbc = new ConexionJdbc();
        boolean registro = false;
        try {

            PreparedStatement pst = jdbc.local().prepareStatement("SELECT IDENTIFICADOR FROM FND_PERIODOS_GESTION "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND TRUNC(FECHA_DESDE) <= TO_DATE(?, 'dd/mm/yyyy') "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?, 'dd/mm/yyyy') "
                    + "AND VR_SITUACION = 'AB'");
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setString(2, fecha);
            pst.setString(3, fecha);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                registro = true;
            }

            pst.close();
            return registro;
        } catch (SQLException e) {
            ControlMensajes.error("validarPeriodoGestion: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean validarTimbrado(String fecha) {
        ConexionJdbc jdbc = new ConexionJdbc();
        boolean registro = false;

        try {

            PreparedStatement pst = jdbc.local().prepareStatement("SELECT IDENTIFICADOR FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD = ? "
                    + " AND TRUNC(FECHA_DESDE) <= TO_DATE(?, 'dd/mm/yyyy') "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?, 'dd/mm/yyyy') "
                    + "AND VR_USO = 'AI' "
                    + "AND ACTIVO = 'S' ");
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, fecha);
            pst.setString(5, fecha);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                registro = true;
            }

            pst.close();
            return registro;
        } catch (SQLException e) {
            ControlMensajes.error("valTimbrado: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean existeComprobante(int numero, int idReg) {
        ConexionJdbc jdbc = new ConexionJdbc();
        boolean registro = false;

        try {

            PreparedStatement pst = jdbc.local().prepareStatement("SELECT 1 FROM VTA_COMPROBANTES "
                    + "WHERE NRO_TICKET = ? AND ID_REGISTRO_CTRL = ?");
            pst.setInt(1, numero);
            pst.setInt(2, idReg);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                registro = true;
            }
            pst.close();
            return registro;
        } catch (SQLException e) {
            ControlMensajes.error("valNumero: " + e, tituloMsj);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int nroInicialCtrl() {
        ConexionJdbc jdbc = new ConexionJdbc();
        int nroFinal = 0;
        try {
            sql = "SELECT COALESCE(NRO_INICIAL, 1) FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD = ? "
                    + " AND FECHA_DESDE <= ? "
                    + "AND FECHA_HASTA >= ? "
                    + "AND VR_USO = 'AI'"
                    + "AND ACTIVO = 'S' ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MMM/yyyy"));
            pst.setString(5, Utilidades.getFecha("dd/MMM/yyyy"));

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nroFinal = rs.getInt(1);
            }

            pst.close();
            return nroFinal;
        } catch (SQLException e) {
            ControlMensajes.error("nroInicialCtrl: " + e, tituloMsj);
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int[] ultimoNro() {
        ConexionJdbc jdbc = new ConexionJdbc();
        int[] datos = new int[2];
        try {
            sql = "SELECT IDENTIFICADOR, ULT_NRO_USADO FROM VTA_ASIGNACION_COMP "
                    + "WHERE ID_PUNTO_EMISION = ? "
                    + "AND ID_REGISTRO_CTRL = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.PEM_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.VTA_CTRL_COMP_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                datos[0] = rs.getInt(1);
                datos[1] = rs.getInt(2);
            }

            pst.close();
            return datos;
        } catch (SQLException e) {
            ControlMensajes.error("nroFinal: " + e, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int nroInicial(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int nroInicial = 0;
        try {
            sql = "SELECT COALESCE(NRO_INICIAL, 0) FROM TC_CONTROL_CAJA WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nroInicial = rs.getInt(1);
            }

            pst.close();
            return nroInicial;
        } catch (SQLException e) {
            ControlMensajes.error("nroInicial: " + e, tituloMsj);
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public String codigoPos() {
        ConexionJdbc jdbc = new ConexionJdbc();
        String codigo = "";
        try {
            sql = "SELECT COALESCE(CODIGO_POS, '') FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD = ? "
                    + " AND FECHA_DESDE <= ? "
                    + "AND FECHA_HASTA >= ? "
                    + "AND VR_USO = 'AI'"
                    + "AND ACTIVO = 'S' ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MMM/yyyy"));
            pst.setString(5, Utilidades.getFecha("dd/MMM/yyyy"));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                codigo = rs.getString(1);
            }

            pst.close();
            return codigo;
        } catch (SQLException e) {
            ControlMensajes.error("codigoPos: " + e, ContParamAplicacion.class.getName());
            return "0";
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public String[] tipoFactura() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT COALESCE(IDENTIFICADOR, 0), COALESCE(DESCRIPCION, '') FROM VTA_TIPOS_COMPROB "
                    + "WHERE CODIGO_POS = ?";
            String[] datos = new String[2];

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setString(1, codigoPos());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
            }

            pst.close();
            return datos;
        } catch (SQLException e) {
            ControlMensajes.error("tipoFactura: " + e, ContParamAplicacion.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
