package Controladores;

import Modelos.FndUnidadesOperat;
import Modelos.FndPeriodosGestion;
import Modelos.FndParmEmpresas;
import Modelos.FndSitios;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Utils.FuncionesBd;
import Utils.Utilidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ContParmEmpresas {

    private String sql = "";

    //FND_PARM_EMPRESA
    public boolean imp_param_empresa() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndParmEmpresas dts = new FndParmEmpresas();
        int filas = 0;
        sql = "SELECT * FROM FND_PARM_EMPRESAS";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            vaciar_tabla("FND_PARM_EMPRESAS");

            while (rs.next()) {
                dts.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dts.setNombre(rs.getString("NOMBRE"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setIdTipoImpuesto(rs.getInt("ID_TIPO_IMPUESTO"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(FuncionesBd.sysdate());
                dts.setAgenteRetencion(rs.getString("AGENTE_RETENCION"));
                dts.setAutoImpresor(rs.getString("AUTO_IMPRESOR"));
                dts.setNombreAlternativo(rs.getString("NOMBRE_ALTERNAT"));
                dts.setDireccion(rs.getString("DIRECCION"));
                dts.setTelefonos(rs.getString("TELEFONOS"));
                dts.setNroDocumento(rs.getString("NUMERO_DOCUMENTO"));
                dts.setCiudad(rs.getString("CIUDAD"));
                dts.setPais(rs.getString("PAIS"));
                dts.setExportarTrn(rs.getString("EXPORTAR_TRN"));
                dts.setNombreDbLink(rs.getString("NOMBRE_DBLINK"));
                dts.setIdUoCentral(rs.getInt("ID_UO_CENTRAL"));
                dts.setEmail(rs.getString("E_MAIL"));
                dts.setPaginaWeb(rs.getString("PAGINA_WEB"));
                dts.setPlataforma(rs.getString("VR_PLATAFORMA"));
                dts.setComandoImp(rs.getString("COMANDO_IMPR"));
                dts.setItemSistema(rs.getString("ITEM_SISTEMA"));
                dts.setDiaCierrePeriodo(rs.getString("DIA_CIERRE_PERIODO"));

                filas++;
                insert_param_empresa(dts);
            }
            System.out.println("\tPARAM. EMPRESA IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR (imp_param_empresa) " + e, "FUNC_IMP_PARAM_EMPRESAS");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean insert_param_empresa(FndParmEmpresas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_PARM_EMPRESAS (IDENTIFICADOR, NOMBRE, ID_MONEDA, ID_TIPO_IMPUESTO, "
                    + "USR_CRE, FEC_CRE, AGENTE_RETENCION, AUTO_IMPRESOR, NOMBRE_ALTERNAT, DIRECCION, "
                    + "TELEFONOS, NUMERO_DOCUMENTO, CIUDAD, PAIS, EXPORTAR_TRN, NOMBRE_DBLINK, "
                    + "ID_UO_CENTRAL, E_MAIL, PAGINA_WEB, VR_PLATAFORMA, COMANDO_IMPR, ITEM_SISTEMA, DIA_CIERRE_PERIODO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getNombre());
            pst.setInt(3, dts.getIdMoneda());
            pst.setInt(4, dts.getIdTipoImpuesto());
            pst.setString(5, dts.getUsrCre());
            pst.setTimestamp(6, dts.getFecCre());
            pst.setString(7, dts.getAgenteRetencion());
            pst.setString(8, dts.getAutoImpresor());
            pst.setString(9, dts.getNombreAlternativo());
            pst.setString(10, dts.getDireccion());
            pst.setString(11, dts.getTelefonos());
            pst.setString(12, dts.getNroDocumento());
            pst.setString(13, dts.getCiudad());
            pst.setString(14, dts.getPais());
            pst.setString(15, dts.getExportarTrn());
            pst.setString(16, dts.getNombreDbLink());
            pst.setInt(17, dts.getIdUoCentral());
            pst.setString(18, dts.getEmail());
            pst.setString(19, dts.getPaginaWeb());
            pst.setString(20, dts.getPlataforma());
            pst.setString(21, dts.getComandoImp());
            pst.setString(22, dts.getItemSistema());
            pst.setString(23, dts.getDiaCierrePeriodo());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR (insert_param_empresa) " + e, "FUNC_IMP_PARAM_EMPRESAS");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //FND_SITIOS
    FndSitios dtsSitios = new FndSitios();

    public boolean imp_sitios() {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT * FROM FND_SITIOS";
        try {

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            int filas = 0;

            vaciar_tabla("FND_SITIOS");
            while (rs.next()) {
                dtsSitios.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsSitios.setDescripcion(rs.getString("DESCRIPCION"));
                dtsSitios.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsSitios.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsSitios.setCodigo(rs.getString("CODIGO"));
                dtsSitios.setUsrCre(rs.getString("USR_CRE"));
                dtsSitios.setFecCre(FuncionesBd.sysdate());
                dtsSitios.setDireccion(rs.getString("DIRECCION"));
                dtsSitios.setIdPais(rs.getInt("ID_PAIS"));
                dtsSitios.setIdCiudad(rs.getInt("ID_CIUDAD"));
                dtsSitios.setCodigocontrol(rs.getString("CODIGO_CTRL_COMP"));
                dtsSitios.setIdUbicacion(rs.getInt("ID_UBICACION_G"));
                dtsSitios.setCodigoFact(rs.getString("CODIGO_FACT"));
                dtsSitios.setCodUnificacion(rs.getString("CODIGO_UNIFICACION"));
                dtsSitios.setItemSistema(rs.getString("ITEM_SISTEMA"));

                filas++;
                insert_sitios(dtsSitios);
            }
            System.out.println("\tSUCURSALES IMPORTADAS: " + filas);
            pst.close();
            return true;

        } catch (SQLException e) {
            ControlMensajes.error("ERROR (imp_sitios) " + e, this.getClass().getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean insert_sitios(FndSitios dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_SITIOS (IDENTIFICADOR, DESCRIPCION, ID_EMPRESA, ID_UNIDAD, CODIGO,"
                    + "USR_CRE, FEC_CRE, DIRECCION, ID_PAIS, ID_CIUDAD, CODIGO_CTRL_COMP,"
                    + "ID_UBICACION_G, CODIGO_FACT, CODIGO_UNIFICACION, ITEM_SISTEMA) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getDescripcion());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getIdUnidad());
            pst.setString(5, dts.getCodigo());
            pst.setString(6, dts.getUsrCre());
            pst.setTimestamp(7, dts.getFecCre());
            pst.setString(8, dts.getDireccion());
            pst.setInt(9, dts.getIdPais());
            pst.setInt(10, dts.getIdCiudad());
            pst.setString(11, dts.getCodigocontrol());
            pst.setInt(12, dts.getIdUbicacion());
            pst.setString(13, dts.getCodigoFact());
            pst.setString(14, dts.getCodUnificacion());
            pst.setString(15, dts.getItemSistema());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR (insert_sitios) " + e, this.getClass().getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //PERIODOS DE GESTION ADMINISTRATIVOS
    public boolean impPeriodos() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            FndPeriodosGestion dtsT = new FndPeriodosGestion();

            //IMPORTAR SOLO EL PERIODO ACTUAL VIGENTE
//            sql = "SELECT * FROM FND_PERIODOS_GESTION "
//                    + "WHERE ID_EMPRESA = ? "
//                    + "AND FECHA_DESDE <= ? "
//                    + "AND FECHA_HASTA >= ? "
//                    + "AND VR_SITUACION = 'AB'";

            //IMPORTAR EL PERIODO ACTUAL VIGENTE Y LOS SIGUIENTES
            sql = "SELECT * FROM FND_PERIODOS_GESTION "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?, 'dd/mm/yyyy') "
                    + "AND VR_SITUACION = 'AB'";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy"));

            ResultSet rs = pst.executeQuery();
            int filas = 0;
            vaciar_tabla("FND_PERIODOS_GESTION");
            while (rs.next()) {
                dtsT.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsT.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsT.setCodigo(rs.getString("CODIGO"));
                dtsT.setNombre(rs.getString("NOMBRE"));
                dtsT.setAnho(rs.getInt("ANHO"));
                dtsT.setTrimestre(rs.getInt("TRIMESTRE"));
                dtsT.setFeDesde(rs.getTimestamp("FECHA_DESDE"));
                dtsT.setFeHasta(rs.getTimestamp("FECHA_HASTA"));
                dtsT.setVrSituacion(rs.getString("VR_SITUACION"));
                dtsT.setUsrCre(rs.getString("USR_CRE"));
                dtsT.setFeCre(rs.getTimestamp("FEC_CRE"));
                dtsT.setFeApert(rs.getTimestamp("FECHA_APERT"));
                dtsT.setUsrApert(rs.getString("USR_APERT"));
                dtsT.setFeCierre(rs.getTimestamp("FECHA_CIERRE"));
                dtsT.setUsrCierre(rs.getString("USR_CIERRE"));

                filas++;
                insertPeriodos(dtsT);
            }
            System.out.println("\tPERIODOS ADMIN. IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR (impPeriodos): " + e, ContParmEmpresas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertPeriodos(FndPeriodosGestion dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO FND_PERIODOS_GESTION (IDENTIFICADOR, ID_EMPRESA, CODIGO, NOMBRE, "
                    + "ANHO, TRIMESTRE, FECHA_DESDE, FECHA_HASTA, VR_SITUACION, USR_CRE, "
                    + "FEC_CRE, FECHA_APERT, USR_APERT, FECHA_CIERRE, USR_CIERRE) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setString(3, dts.getCodigo());
            pst.setString(4, dts.getNombre());
            pst.setInt(5, dts.getAnho());
            pst.setInt(6, dts.getTrimestre());
            pst.setTimestamp(7, dts.getFeDesde());
            pst.setTimestamp(8, dts.getFeHasta());
            pst.setString(9, dts.getVrSituacion());
            pst.setString(10, dts.getUsrCre());
            pst.setTimestamp(11, dts.getFeCre());
            pst.setTimestamp(12, dts.getFeApert());
            pst.setString(13, dts.getUsrApert());
            pst.setTimestamp(14, dts.getFeCierre());
            pst.setString(15, dts.getUsrCierre());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR (insertPeriodos): " + e, ContParmEmpresas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //UNIDADES OPERATIVAS
    public boolean imp_unidades_operativas() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndUnidadesOperat dts_unidades_operativas = new FndUnidadesOperat();

        try {
            int filas = 0;
            sql = "SELECT  * from CO_UNIDADES_OPERAT";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dts_unidades_operativas.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));
                dts_unidades_operativas.setID_EMPRESA(rs.getInt("ID_EMPRESA"));
                dts_unidades_operativas.setDESCRIPCION(rs.getString("DESCRIPCION"));
                dts_unidades_operativas.setNUM_ASNTOS_MANUAL(rs.getString("NUM_ASNTOS_MANUAL"));
                dts_unidades_operativas.setUSR_CRE(rs.getString("USR_CRE"));
                dts_unidades_operativas.setFEC_CRE(rs.getTimestamp("FEC_CRE"));
                dts_unidades_operativas.setABREVIATURA(rs.getString("ABREVIATURA"));
                dts_unidades_operativas.setID_SEC_ASIENTOS(rs.getInt("ID_SEC_ASIENTOS"));
                dts_unidades_operativas.setID_NUM_ASIENTOS(rs.getInt("ID_NUM_ASIENTOS"));
                dts_unidades_operativas.setID_NUM_CONTROL(rs.getInt("ID_NUM_CONTROL"));
                dts_unidades_operativas.setID_SEC_ITEMS_AST(rs.getInt("ID_SEC_ITEMS_AST"));
                dts_unidades_operativas.setUSR_MOD(rs.getString("USR_MOD"));
                dts_unidades_operativas.setFEC_MOD(rs.getTimestamp("FEC_MOD"));
                dts_unidades_operativas.setDIRECCION(rs.getString("DIRECCION"));
                dts_unidades_operativas.setTELEFONOS(rs.getString("TELEFONOS"));
                dts_unidades_operativas.setNUMERO_DOCUMENTO(rs.getString("NUMERO_DOCUMENTO"));
                dts_unidades_operativas.setCIUDAD(rs.getString("CIUDAD"));
                dts_unidades_operativas.setPAIS(rs.getString("PAIS"));
                dts_unidades_operativas.setNUMERO_ORDEN(rs.getInt("NUMERO_ORDEN"));
                dts_unidades_operativas.setES_PRINCIPAL(rs.getString("ES_PRINCIPAL"));
                dts_unidades_operativas.setID_GRUPO_LISTA(rs.getInt("ID_GRUPO_LISTA"));
                dts_unidades_operativas.setID_MONEDA(rs.getInt("ID_MONEDA"));
                dts_unidades_operativas.setSEPARADOR_MILES(rs.getString("SEPARADOR_MILES"));

                filas++;
                insert_unidades_operativas(dts_unidades_operativas);

            }
            System.out.println("\tUNIDADES OPERAT. IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContParmEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert_unidades_operativas(FndUnidadesOperat dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO CO_UNIDADES_OPERAT (IDENTIFICADOR, ID_EMPRESA, DESCRIPCION,"
                    + "NUM_ASNTOS_MANUAL, USR_CRE, FEC_CRE, ABREVIATURA, ID_SEC_ASIENTOS, "
                    + "ID_NUM_ASIENTOS, ID_NUM_CONTROL, ID_SEC_ITEMS_AST, USR_MOD, FEC_MOD,"
                    + "DIRECCION, TELEFONOS, NUMERO_DOCUMENTO, CIUDAD, PAIS, NUMERO_ORDEN,"
                    + "ES_PRINCIPAL, ID_GRUPO_LISTA, ID_MONEDA, SEPARADOR_MILES)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIDENTIFICADOR());
            pst.setInt(2, dts.getID_EMPRESA());
            pst.setString(3, dts.getDESCRIPCION());
            pst.setString(4, dts.getNUM_ASNTOS_MANUAL());
            pst.setString(5, dts.getUSR_CRE());
            pst.setTimestamp(6, dts.getFEC_CRE());
            pst.setString(7, dts.getABREVIATURA());
            pst.setInt(8, dts.getID_SEC_ASIENTOS());
            pst.setInt(9, dts.getID_NUM_ASIENTOS());
            pst.setInt(10, dts.getID_NUM_CONTROL());
            pst.setInt(11, dts.getID_SEC_ITEMS_AST());
            pst.setString(12, dts.getUSR_MOD());
            pst.setTimestamp(13, dts.getFEC_MOD());
            pst.setString(14, dts.getDIRECCION());
            pst.setString(15, dts.getTELEFONOS());
            pst.setString(16, dts.getNUMERO_DOCUMENTO());
            pst.setString(17, dts.getCIUDAD());
            pst.setString(18, dts.getPAIS());
            pst.setInt(19, dts.getNUMERO_ORDEN());
            pst.setString(20, dts.getES_PRINCIPAL());
            pst.setInt(21, dts.getID_GRUPO_LISTA());
            pst.setInt(22, dts.getID_MONEDA());
            pst.setString(23, dts.getSEPARADOR_MILES());

            int x = pst.executeUpdate();
            pst.close();
            
            return x != 0;
        } catch (SQLException ex) {
            Logger.getLogger(ContParmEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //POS_APLIC_NOVEDADES
    public boolean imp_aplic_novedades() {
        if (!valPosAplicNovedades()) {
            insert_aplic_novedades();
        }
        return true;
    }

    //INSERTAR REGISTROS POS_APLIC_NOVEDADES EN CASO DE QUE ESTE VACIO
    public boolean insert_aplic_novedades() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int x = 0;
            String[] tablas = {"PR_PRODUCTOS", "PR_UM_PRODUCTO", "VTA_CLIENTES"};

            sql = "INSERT INTO POS_APLIC_NOVEDADES (ID_CAJA, ID_UNIDAD, "
                    + "NOMBRE_TABLA, ULTIMO_ID_NOVEDAD, USR_CRE, FEC_CRE) "
                    + "VALUES(?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            for (int i = 0; i < 3; i++) {

                pst.setInt(1, ContParamAplicacion.CAJA_IDENTIFICADOR);
                pst.setInt(2, ContParamAplicacion.UND_IDENTIFICADOR);
                pst.setString(3, tablas[i]);
                pst.setInt(4, 0);
                pst.setString(5, ContFndUsuarios.USR_NOMBRE);
                pst.setTimestamp(6, FuncionesBd.sysdate());
                x = pst.executeUpdate();

            }
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("ERROR (insert_aplic_novedades) " + e, "FUNC_IMP_PARAM_EMPRESAS");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //Valida la existeStock de registros en base de datos local
    public boolean valPosAplicNovedades() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT * FROM POS_APLIC_NOVEDADES";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            ControlMensajes.error("vistas: " + e, ContParamAplicacion.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //SI EXISTEN REGISTROS PRIMERO VACIAR LA TABLA
    public boolean vaciar_tabla(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "DELETE FROM " + tabla + "";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("vaciar_tabla: " + e, this.getClass().getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
