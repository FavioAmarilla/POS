package Controladores;

import Vistas.FrmCierreCaja;
import Modelos.TcControlCaja;
import Utils.Utilidades;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Vistas.FrmLogin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContTcControlCaja {

    ContVtaControlComprob control = new ContVtaControlComprob();
    private String sql;
    int[] timbrado = control.timbrado();
    int[] ultimoNro = control.ultimoNro();

    TcControlCaja dtsCtrl = new TcControlCaja();

    //====================================
    //FUNCIONES PARA LA APERTURA DE CAJA
    //====================================
    public Timestamp sysdate() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            Timestamp fecha = null;
            sql = "SELECT SYSDATE FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fecha = rs.getTimestamp(1);
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

    public long identificador() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            long identificador = 0;
            sql = "SELECT SQ_CTRL_CAJA.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                identificador = rs.getLong(1);
            }

            pst.close();
            return identificador;
        } catch (SQLException ex) {
            ControlMensajes.error("identificador: " + ex, ContTcControlCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int secuenciaControl() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int secuencia = 1;
            sql = "SELECT MAX(SECUENCIA) FROM TC_CONTROL_CAJA WHERE ID_REGISTRO_CTRL = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, timbrado[0]);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                secuencia = rs.getInt(1) + 1;
            }

            pst.close();
            return secuencia;
        } catch (SQLException ex) {
            ControlMensajes.error("secuenciaControl: " + ex, ContTcControlCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean abrirCaja(TcControlCaja dts, String conexion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        PreparedStatement pst = null;
        try {
            sql = "INSERT INTO TC_CONTROL_CAJA(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_CAJA, ID_TURNO, "
                    + "FECHA_APERT, USR_APERTURA, USR_CRE, FEC_CRE, ID_MONEDA, ID_CAJERO, "
                    + "MONTO_EFECTIVO, ARQUEADO, CERRADO, "
                    + "NRO_INICIAL, SECUENCIA, ID_REGISTRO_CTRL,DESCRIPCION) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            if (conexion.equals("S")) {
                pst = jdbc.server().prepareStatement(sql);
            }
            if (conexion.equals("L")) {
                pst = jdbc.local().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdCaja());
            pst.setInt(5, dts.getIdTurno());
            pst.setTimestamp(6, sysdate());
            pst.setString(7, dts.getUsrApertura());
            pst.setString(8, dts.getUsrApertura());
            pst.setTimestamp(9, sysdate());
            pst.setInt(10, dts.getIdMoneda());
            pst.setInt(11, dts.getIdCajero());
            pst.setLong(12, dts.getMontoEfectivo());
            pst.setString(13, dts.getArqueado());
            pst.setString(14, dts.getCerrado());
            pst.setInt(15, dts.getNroInicial());
            pst.setInt(16, dts.getSecuencia());
            pst.setInt(17, timbrado[0]);
            pst.setString(18, dts.getDescripcion());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("abrirCaja: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean fechaEnvServer(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE TC_CONTROL_CAJA SET FECHA_ENVIO_SRV = SYSDATE "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("fechaEnvServer: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean valApertura() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            boolean registro = false;
            sql = "SELECT IDENTIFICADOR FROM TC_CONTROL_CAJA "
                    + "WHERE ID_EMPRESA= ? AND ID_UNIDAD= ? "
                    + "AND ID_CAJA= ? AND USR_CRE= ? "
                    + "AND TRUNC(FECHA_APERT) = TO_DATE(?, 'dd/MM/yyyy') "
                    + "AND CERRADO = 'N' ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(4, ContFndUsuarios.USR_NOMBRE);
            pst.setString(5, Utilidades.getFecha("dd/MM/yyyy"));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                registro = true;
            }

            pst.close();
            return registro; //SI RETORNA TRUE EXISTEN CONTROLES ABIERTOS
        } catch (SQLException ex) {
            ControlMensajes.error("valApertura: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean valCierre() {
        ConexionJdbc jdbc = new ConexionJdbc();
        boolean registro = false;
        try {
            sql = "SELECT 1 FROM TC_CONTROL_CAJA WHERE "
                    + "ID_EMPRESA= ? "
                    + "AND ID_UNIDAD= ? "
                    + "AND ID_CAJA= ? "
                    + "AND USR_CRE= ? "
                    + "AND TRUNC(FECHA_APERT) < TO_DATE(?, 'dd/MM/yyyy') "
                    + "AND COALESCE(CERRADO,'N') = 'N' ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(4, ContFndUsuarios.USR_NOMBRE);
            pst.setString(5, Utilidades.getFecha("dd/MM/yyyy"));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                registro = true;
            }

            pst.close();
            return registro; //SI RETORNA TRUE EXISTEN CONTROLES ANTERIORES ABIERTOS
        } catch (SQLException ex) {
            ControlMensajes.error("valCierre: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public String userApertura(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            String usr = "";
            sql = "SELECT COALESCE(USR_CRE, '') FROM TC_CONTROL_CAJA WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                usr = rs.getString(1);
            }

            pst.close();
            return usr;
        } catch (SQLException ex) {
            ControlMensajes.error("userApertura: " + ex, ContTcControlCaja.class.getName());
            return "";
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //====================================
    //FUNCIONES PARA EL CIEERE DE CAJA
    //====================================
    public int idControlComprob() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int idRegistro = 0;
            sql = "SELECT IDENTIFICADOR FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ACTIVO = 'S' "
                    + "AND ID_UNIDAD = ? "
                    + "AND CODIGO_POS = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, String.valueOf(ContParamAplicacion.UND_ID_MONEDA));

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idRegistro = rs.getInt(1);
            }

            pst.close();
            return idRegistro;
        } catch (SQLException ex) {
            ControlMensajes.error("idControlComprob: " + ex, ContTcControlCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int nroInicial(int id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int nroInicial = 0;
        try {
            sql = "SELECT COALESCE(NRO_INICIAL, 0) FROM TC_CONTROL_CAJA "
                    + "WHERE IDENTIFICADOR = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nroInicial = rs.getInt(1);
            }

            pst.close();
            return nroInicial;
        } catch (SQLException e) {
            ControlMensajes.error("nroInicial: " + e, ContTcControlCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public long idApertura() {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(IDENTIFICADOR, 0) FROM TC_CONTROL_CAJA WHERE "
                + "ID_EMPRESA= ? "
                + "AND ID_UNIDAD= ? "
                + "AND ID_CAJA= ? "
                + "AND TRUNC(FECHA_APERT) <= TO_DATE(?,'dd/mm/yyyy') "
                + "AND CERRADO = 'N' ";
        long id = 0;

        try {

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MMM/yyyy"));

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            }

            pst.close();
            return id;
        } catch (SQLException e) {
            ControlMensajes.error("idApertura: " + e, ContTcControlCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean cerrarCaja(int con, long idApertura) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            //LLENAMOS LOS DATOS PARA EL INSERT
            dtsCtrl.setUsrMod(ContFndUsuarios.USR_NOMBRE);
            dtsCtrl.setArqueado("N");
            dtsCtrl.setCerrado("S");
            dtsCtrl.setDescripcion(FrmCierreCaja.txtComentario.getText());
            dtsCtrl.setNroFinal(ultimoNro[1]);

            totales(idApertura);
            cancelados(idApertura);
            anulaciones(idApertura);

            //SE REALIZA EL INSERT A LA BD
            sql = "UPDATE TC_CONTROL_CAJA SET USR_MOD=?, FEC_MOD=?, FECHA_CIERRE=?, ARQUEADO=?, CERRADO=?, "
                    + "NRO_FINAL=?, TOTAL=?, TOTAL_EXENTO=?, TOTAL_GRAVADO=?, TOTAL_IMPUESTO=?, "
                    + "TOTAL_GRAVADO5=?, TOTAL_IMPUESTO5=?, TOTAL_GRAVADO10=?, TOTAL_IMPUESTO10=?,"
                    + "TOTAL_CANCELADO=?, CANT_CANCELADO=?, CANT_DEVOL=?, CANT_CANCEL=?, DESCRIPCION=?, "
                    + "TOTAL_ANULADO=?, CANT_ANULADO=? "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = null;
            switch (con) {
                case 0:
                    pst = jdbc.local().prepareStatement(sql);
                    break;
                case 1:
                    pst = jdbc.server().prepareStatement(sql);
                    break;
                default:
                    return false;
            }

            pst.setString(1, dtsCtrl.getUsrMod());
            pst.setTimestamp(2, sysdate());
            pst.setTimestamp(3, sysdate());
            pst.setString(4, dtsCtrl.getArqueado());
            pst.setString(5, dtsCtrl.getCerrado());
            pst.setInt(6, dtsCtrl.getNroFinal());
            pst.setLong(7, dtsCtrl.getTotal());
            pst.setLong(8, dtsCtrl.getTotalExento());
            pst.setLong(9, dtsCtrl.getTotalGrabado());
            pst.setLong(10, dtsCtrl.getTotalImpuesto());
            pst.setLong(11, dtsCtrl.getTotalGrabado5());
            pst.setLong(12, dtsCtrl.getTotalImpuesto5());
            pst.setLong(13, dtsCtrl.getTotalGrabado10());
            pst.setLong(14, dtsCtrl.getTotalImpuesto10());
            pst.setLong(15, dtsCtrl.getTotalCancelado());
            pst.setLong(16, dtsCtrl.getCantCancelado());
            pst.setLong(17, dtsCtrl.getCantDevolucion());
            pst.setLong(18, dtsCtrl.getCantCancelado());
            pst.setString(19, dtsCtrl.getDescripcion());
            pst.setLong(20, dtsCtrl.getTotalAnulado());
            pst.setInt(21, dtsCtrl.getCantAnulado());
            pst.setLong(22, idApertura);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("cerrarCaja: " + e, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //=============================================
    //EXPORTAR CONTROLES NO ENVIADOS A SERVER 
    //=============================================
    public boolean expControles(String condicion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int filas = 0;
        try {
            TcControlCaja dts = new TcControlCaja();
            sql = "SELECT * FROM TC_CONTROL_CAJA "
                    + "WHERE FECHA_ENVIO_SRV IS NULL "
                    + "AND CERRADO='S' AND ARQUEADO='S' "
                    + condicion
                    + "ORDER BY IDENTIFICADOR DESC";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dts.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setIdCaja(rs.getInt("ID_CAJA"));
                dts.setIdTurno(rs.getInt("ID_TURNO"));
                dts.setFecApertura(rs.getTimestamp("FECHA_APERT"));
                dts.setUsrApertura(rs.getString("USR_APERTURA"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setUsrMod(rs.getString("USR_CRE"));
                dts.setFecMod(rs.getTimestamp("FEC_MOD"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setIdCajero(rs.getInt("ID_CAJERO"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setMontoEfectivo(rs.getInt("MONTO_EFECTIVO"));
                dts.setTotalDebitos(rs.getInt("TOTAL_DEBITOS"));
                dts.setTotalCreditos(rs.getInt("TOTAL_CREDITOS"));
                dts.setfechaCierre(rs.getTimestamp("FECHA_CIERRE"));
                dts.setArqueado(rs.getString("ARQUEADO"));
                dts.setCerrado(rs.getString("CERRADO"));
                dts.setNroInicial(rs.getInt("NRO_INICIAL"));
                dts.setNroFinal(rs.getInt("NRO_FINAL"));
                dts.setTotal(rs.getInt("TOTAL"));
                dts.setTotalExento(rs.getInt("TOTAL_EXENTO"));
                dts.setTotalGrabado(rs.getInt("TOTAL_GRAVADO"));
                dts.setTotalImpuesto(rs.getInt("TOTAL_IMPUESTO"));
                dts.setTotalGrabado5(rs.getInt("TOTAL_GRAVADO5"));
                dts.setTotalImpuesto5(rs.getInt("TOTAL_IMPUESTO5"));
                dts.setTotalGrabado10(rs.getInt("TOTAL_GRAVADO10"));
                dts.setTotalImpuesto10(rs.getInt("TOTAL_IMPUESTO10"));
                dts.setTotalDescuento(rs.getInt("TOTAL_DESCUENTO"));
                dts.setTotalAnulado(rs.getInt("TOTAL_ANULADO"));
                dts.setTotalCancelado(rs.getInt("TOTAL_CANCELADO"));
                dts.setCantCancelado(rs.getInt("CANT_CANCELADO"));
                dts.setCantDevolucion(rs.getInt("CANT_DEVOL"));
                dts.setCantDescuentos(rs.getInt("CANT_DESCTOS"));
                dts.setCantAnulado(rs.getInt("CANT_ANULADO"));
                dts.setSecuencia(rs.getInt("SECUENCIA"));
                dts.setFecEnvSrv(sysdate());
                dts.setIdRegistroCtrl(rs.getInt("ID_REGISTRO_CTRL"));

                deleteControl(dts.getIdentificador()); //ELIMINAR EL CONTROL SI EXISTE EN SERVIDOR PARA ENVIAR EL CONTROL YA CERRADO

                if (insertServer(dts)) { //SE ENVIA EL CONTROL AL SERVER
                    fechaEnvServer(dts.getIdentificador()); //ACTUALIZAMOS EL CAMPO DE fecha DE ENV. AL SERVER
                    filas++;
                }
            }
            System.out.println("\tCONTROL DE CAJAS ENVIADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("expControles: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertServer(TcControlCaja dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_CONTROL_CAJA(IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_CAJA,"
                    + "ID_TURNO, FECHA_APERT, USR_APERTURA, USR_CRE, FEC_CRE, USR_MOD, FEC_MOD,"
                    + "ID_CAJERO, DESCRIPCION, MONTO_EFECTIVO, TOTAL_DEBITOS, TOTAL_CREDITOS,"
                    + "FECHA_CIERRE, ARQUEADO, CERRADO, NRO_INICIAL, NRO_FINAL, TOTAL, TOTAL_EXENTO,"
                    + "TOTAL_GRAVADO, TOTAL_IMPUESTO, TOTAL_GRAVADO5, TOTAL_IMPUESTO5, TOTAL_GRAVADO10,"
                    + "TOTAL_IMPUESTO10, TOTAL_ANULADO, TOTAL_CANCELADO, CANT_CANCELADO,"
                    + "CANT_DEVOL, CANT_ANULADO, SECUENCIA, CANT_CANCEL, FECHA_ENVIO_SRV, "
                    + "ID_REGISTRO_CTRL, ID_MONEDA) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdCaja());
            pst.setInt(5, dts.getIdTurno());
            pst.setTimestamp(6, dts.getFecApertura());//
            pst.setString(7, dts.getUsrApertura());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, dts.getFecCre());//
            pst.setString(10, dts.getUsrMod());
            pst.setTimestamp(11, dts.getFecMod());//
            pst.setInt(12, dts.getIdCajero());
            pst.setString(13, dts.getDescripcion());
            pst.setLong(14, dts.getMontoEfectivo());
            pst.setLong(15, dts.getTotalDebitos());
            pst.setLong(16, dts.getTotalCreditos());
            pst.setTimestamp(17, dts.getfechaCierre());//
            pst.setString(18, dts.getArqueado());
            pst.setString(19, dts.getCerrado());
            pst.setInt(20, dts.getNroInicial());
            pst.setInt(21, dts.getNroFinal());
            pst.setLong(22, dts.getTotal());
            pst.setLong(23, dts.getTotalExento());
            pst.setLong(24, dts.getTotalGrabado());
            pst.setLong(25, dts.getTotalImpuesto());
            pst.setLong(26, dts.getTotalGrabado5());
            pst.setLong(27, dts.getTotalImpuesto5());
            pst.setLong(28, dts.getTotalGrabado10());
            pst.setLong(29, dts.getTotalImpuesto10());
            pst.setLong(30, dts.getTotalAnulado());
            pst.setLong(31, dts.getTotalCancelado());
            pst.setLong(32, dts.getCantCancelado());
            pst.setLong(33, dts.getCantDevolucion());
            pst.setInt(34, dts.getCantAnulado());
            pst.setInt(35, dts.getSecuencia());
            pst.setLong(36, dts.getCantCancelado());
            pst.setTimestamp(37, dts.getFecEnvSrv());
            pst.setInt(38, dts.getIdRegistroCtrl());
            pst.setInt(39, dts.getIdMoneda());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("insertServer: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean valControl(int idControl) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT 1 FROM TC_CONTROL_CAJA WHERE IENTIFICADOR = ? "
                    + "AND FECHA_ENVIO_SRV IS NOT NULL";
            boolean registro = false;

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idControl);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                registro = true;
            }

            pst.close();
            return registro; //SI DEVUELVE TRUE EL CONTROL DE CAJA FUE ENVIADO AL SERVER 

        } catch (SQLException ex) {
            ControlMensajes.error("enviadoServer: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean deleteControl(long idControl) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "DELETE FROM TC_CONTROL_CAJA WHERE IDENTIFICADOR = ? ";

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setLong(1, idControl);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("deleteControl: " + e, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //=========================================
    //FUNCIONES PARA CONSULTAR SI 
    //EL CONTROL DE CAJA FUE ENVIADO AL SERVER
    //=========================================
    public boolean enviadoServer(String fecha) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT * FROM TC_CONTROL_CAJA WHERE "
                    + "ID_EMPRESA= ? "
                    + "AND ID_UNIDAD= ? "
                    + "AND ID_CAJA= ? "
                    + "AND TRUNC(FECHA_APERT) = TO_DATE(?,'dd/mm/yyyy') "
                    + "AND CERRADO = 'N' "
                    + "AND FECHA_ENVIO_SRV IS NOT NULL";
            boolean registro = false;

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(4, fecha);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                registro = true;
            }
            pst.close();
            return registro; //SI DEVUELVE TRUE EL CONTROL DE CAJA FUE ENVIADO AL SERVER 

        } catch (SQLException ex) {
            ControlMensajes.error("enviadoServer: " + ex, ContTcControlCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //=========================================
    //FUNCIONES PARA CALCULAR
    //LOS TOTALES DEL CIERRE DE CAJA
    //=========================================
    public void totales(long idControl) {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(SUM(MONTO_TOTAL),0) AS TOTAL, COALESCE(SUM(MONTO_EXENTO),0) AS TOTAL_EXENTO, "
                + "COALESCE(SUM(MONTO_GRAVADO),0) AS TOTAL_GRAVADO,  COALESCE(SUM(MONTO_IMPUESTO),0) AS TOTAL_IMPUESTO, "
                + " COALESCE(SUM(MONTO_GRAVADO5),0) AS TOTAL_GRAVADO5,  COALESCE(SUM(MONTO_IMPUESTO5),0) AS TOTAL_IMPUESTO5, "
                + " COALESCE(SUM(MONTO_GRAVADO10),0) AS TOTAL_GRAVADO10,  COALESCE(SUM(MONTO_IMPUESTO10),0) AS TOTAL_IMPUESTO10 "
                + "FROM VTA_COMPROBANTES "
                + "WHERE ID_CONTROL_CAJA = ? "
                + "AND FEC_ANULACION IS NULL "
                + "AND ID_SUPR_CANCEL IS NULL";

        try {

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControl);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                dtsCtrl.setTotal(rs.getLong("TOTAL"));
                dtsCtrl.setTotalExento(rs.getLong("TOTAL_EXENTO"));
                dtsCtrl.setTotalGrabado(rs.getLong("TOTAL_GRAVADO"));
                dtsCtrl.setTotalImpuesto(rs.getLong("TOTAL_IMPUESTO"));
                dtsCtrl.setTotalGrabado5(rs.getLong("TOTAL_GRAVADO5"));
                dtsCtrl.setTotalImpuesto5(rs.getLong("TOTAL_IMPUESTO5"));
                dtsCtrl.setTotalGrabado10(rs.getLong("TOTAL_GRAVADO10"));
                dtsCtrl.setTotalImpuesto10(rs.getLong("TOTAL_IMPUESTO10"));
            }
            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("totales: " + e, ContTcControlCaja.class.getName());
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public void cancelados(long idControl) {  //SE REFIERE A LOS COMPROBANTES CANCELADOS
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(SUM(MONTO_TOTAL),0),  COALESCE(COUNT(*),0)  "
                + "FROM VTA_COMPROBANTES "
                + "WHERE ID_CONTROL_CAJA = ? "
                + "AND USR_ANULACION IS NOT NULL";

        try {

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControl);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                dtsCtrl.setTotalCancelado(rs.getLong(1));
                dtsCtrl.setCantCancelado(rs.getLong(2));
            }

            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("cancelados: " + e, ContTcControlCaja.class.getName());

        } finally {
            jdbc.cerrarConexion();
        }
    }

    public void anulaciones(long idControl) { //LAS ANULACIONES SE REFIEREN A LOS ITEMS DEVUELTOS
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(SUM(VTA_ITEMS_COMPROB.IMPORTE_ITEM),0) AS TOTAL_ANULACION, "
                + "COALESCE(COUNT(VTA_ITEMS_COMPROB.IMPORTE_GRAVADO),0) AS CANT_ANULACION "
                + "FROM VTA_COMPROBANTES VTA "
                + "LEFT JOIN VTA_ITEMS_COMPROB ON VTA.IDENTIFICADOR = VTA_ITEMS_COMPROB.ID_COMPROBANTE "
                + "WHERE ID_CONTROL_CAJA = ? AND VTA_ITEMS_COMPROB.CANTIDAD < 0";

        try {

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControl);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                dtsCtrl.setTotalAnulado(rs.getLong(1) * -1);
                dtsCtrl.setCantAnulado(rs.getInt(2));
            }

            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("anulaciones: " + e, ContTcControlCaja.class.getName());
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
