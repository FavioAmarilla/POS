package Controladores;

import Utils.ControlMensajes;
import Utils.Utilidades;
import Conexiones.ConexionJdbc;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContParamAplicacion {

    private String sql;

    public static int CAJA_IDENTIFICADOR;
    public static String CAJA_NUMERO;
    public static String CAJA_DESCRIPCION;
    public static String CAJA_USAR_CORTE_PAPEL;
    public static String CAJA_USAR_GAVETA;
    public static String CAJA_PUERTO_IMPRESION;
    public static String CAJA_VR_PUERTO_IMPR;

    public static int PEM_IDENTIFICADOR;
    public static String PEM_CODIGO;

    public static int EMP_IDENTIFICADOR;
    public static String EMP_NOMBRE;
    public static String EMP_TELEFONOS;
    public static String EMP_NUMERO_DOCUMENTO;
    public static String EMP_CIUDAD;
    public static String EMP_PAIS;

    public static int SUC_IDENTIFICADOR;
    public static String SUC_DESCRIPCION;
    public static String SUC_DIRECCION;
    public static String SUC_CODIGO_CTRL_COMP;

    public static int UND_IDENTIFICADOR;
    public static int UND_ID_MONEDA;
    public static String UND_DESCRIPCION;
    public static String UND_DIRECCION;
    public static String UND_TELEFONOS;
    public static String UND_NUMERO_DOCUMENTO;
    public static String UND_CIUDAD;
    public static String UND_PAIS;

    public static int TCJ_IDENTIFICADOR;
    public static String TCJ_NOMBRE;

    public static int VTA_CTRL_COMP_IDENTIFICADOR;
    public static int VTA_CTRL_COMP_LONG_NRO_COMPROB;
    public static String VTA_CTRL_NUMERO;
    public static Date VTA_CTRL_FECHA_VENCIMIENTO;

    public static int VTA_ASIG_COMP_NUMERO_HASTA;

    public void getParamCaja() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, NUMERO, DESCRIPCION, USAR_CORTE_PAPEL, "
                    + "USA_GAVETA, PUERTO_IMPRESION, ID_EMPRESA, "
                    + "ID_SITIO, ID_UNIDAD, VR_PUERTO_IMPR "
                    + "FROM TC_CAJAS WHERE EQUIPO=? AND ACTIVO='S'";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setString(1, Utilidades.getHostname().toUpperCase());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                CAJA_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                CAJA_NUMERO = rs.getString("NUMERO");
                CAJA_DESCRIPCION = rs.getString("DESCRIPCION");
                CAJA_USAR_CORTE_PAPEL = rs.getString("USAR_CORTE_PAPEL");
                CAJA_USAR_GAVETA = rs.getString("USA_GAVETA");
                CAJA_PUERTO_IMPRESION = rs.getString("PUERTO_IMPRESION");
                EMP_IDENTIFICADOR = rs.getInt("ID_EMPRESA");
                SUC_IDENTIFICADOR = rs.getInt("ID_SITIO");
                UND_IDENTIFICADOR = rs.getInt("ID_UNIDAD");
                CAJA_VR_PUERTO_IMPR = rs.getString("VR_PUERTO_IMPR");
            } else {
                ControlMensajes.error("Parametros de caja no definidos", "ACCESO AL SISTEMA");
                //System.exit(0);
            }

            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("getParamCaja: " + e, ContParamAplicacion.class.getName());
            //System.exit(0);
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean getParamPuntoEmision() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, CODIGO FROM VTA_PUNTOS_EMISION WHERE ID_CAJA=? AND ACTIVO='S'";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, CAJA_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                PEM_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                PEM_CODIGO = rs.getString("CODIGO");
            } else {
                ControlMensajes.error("Punto de emision no definido", "ACCESO AL SISTEMA");
                //System.exit(0);
            }

            pst.close();
            return PEM_IDENTIFICADOR > 0;
        } catch (SQLException e) {
            ControlMensajes.error("getParamCaja: " + e, ContParamAplicacion.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public void getParamEmpresa() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, NOMBRE, DIRECCION, TELEFONOS, NUMERO_DOCUMENTO, CIUDAD, PAIS "
                    + "FROM FND_PARM_EMPRESAS WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, EMP_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                EMP_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                EMP_NOMBRE = rs.getString("NOMBRE");
                EMP_TELEFONOS = rs.getString("TELEFONOS");
                EMP_NUMERO_DOCUMENTO = rs.getString("NUMERO_DOCUMENTO");
                EMP_CIUDAD = rs.getString("CIUDAD");
                EMP_PAIS = rs.getString("PAIS");
            } else {
                ControlMensajes.error("Parametros de empresa no definido", "ACCESO AL SISTEMA");
                //System.exit(0);
            }

            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("getParamEmpresa: " + e, ContParamAplicacion.class.getName());
            //System.exit(0);
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public void getParamSucursal() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, DESCRIPCION, DIRECCION, CODIGO_CTRL_COMP "
                    + "FROM FND_SITIOS WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, SUC_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                SUC_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                SUC_DESCRIPCION = rs.getString("DESCRIPCION");
                SUC_DIRECCION = rs.getString("DIRECCION");
                SUC_CODIGO_CTRL_COMP = rs.getString("CODIGO_CTRL_COMP");
            } else {
                ControlMensajes.error("Parametros de sucursal no definidos", "ACCESO AL SISTEMA");
                //System.exit(0);
            }

            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("getParamSucursal: " + e, ContParamAplicacion.class.getName());
            //System.exit(0);
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public void getParamUnidad() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, ID_MONEDA, DESCRIPCION, DIRECCION, TELEFONOS, NUMERO_DOCUMENTO, CIUDAD, PAIS "
                    + "FROM CO_UNIDADES_OPERAT WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, UND_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                UND_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                UND_ID_MONEDA = rs.getInt("ID_MONEDA");
                UND_DESCRIPCION = rs.getString("DESCRIPCION");
                UND_DIRECCION = rs.getString("DIRECCION");
                UND_TELEFONOS = rs.getString("TELEFONOS");
                UND_NUMERO_DOCUMENTO = rs.getString("NUMERO_DOCUMENTO");
                UND_CIUDAD = rs.getString("CIUDAD");
                UND_PAIS = rs.getString("PAIS");
            } else {
                ControlMensajes.error("Parametros de unidad operativa no definidos", "ACCESO AL SISTEMA");
                //System.exit(0);
            }

            pst.close();
        } catch (SQLException e) {
            ControlMensajes.error("getParamUnidad: " + e, ContParamAplicacion.class.getName());
            //System.exit(0);
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean getParamComprobante() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT IDENTIFICADOR, LONG_NRO_COMPROB, NUMERO, FECHA_VENCIMIENTO FROM VTA_CONTROL_COMPROB "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD = ? "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?, 'dd/mm/yyyy') "
                    + "AND VR_USO = 'AI' "
                    + "AND ACTIVO = 'S' ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setString(4, Utilidades.getFecha("dd/MM/yyyy"));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                VTA_CTRL_COMP_IDENTIFICADOR = rs.getInt("IDENTIFICADOR");
                VTA_CTRL_COMP_LONG_NRO_COMPROB = rs.getInt("LONG_NRO_COMPROB");
                VTA_CTRL_NUMERO = rs.getString("NUMERO");
                VTA_CTRL_FECHA_VENCIMIENTO = rs.getDate("FECHA_VENCIMIENTO");
            } else {
                ControlMensajes.error("Parametros del comprobante no definidos", "ACCESO AL SISTEMA");          
            }

            pst.close();
            return VTA_CTRL_COMP_IDENTIFICADOR > 0;
        } catch (SQLException e) {
            ControlMensajes.error("getParamComprobante: " + e, ContParamAplicacion.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean getLimiteComp() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT NUMERO_HASTA FROM VTA_ASIGNACION_COMP "
                    + "WHERE ID_EMPRESA = ? "
                    + "AND ID_SITIO = ? "
                    + "AND ID_UNIDAD= ? "
                    + "AND ID_REGISTRO_CTRL= ? "
                    + "AND ID_PUNTO_EMISION = ? "
                    + "AND CODIGO_POS = 1 ";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.EMP_IDENTIFICADOR);
            pst.setInt(2, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setInt(3, ContParamAplicacion.UND_IDENTIFICADOR);
            pst.setInt(4, VTA_CTRL_COMP_IDENTIFICADOR);
            pst.setInt(5, ContParamAplicacion.PEM_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                VTA_ASIG_COMP_NUMERO_HASTA = rs.getInt(1);
            } else {
                ControlMensajes.error("Limite de emision de comprobantes no definido", "ACCESO AL SISTEMA");
            }

            pst.close();
            return VTA_ASIG_COMP_NUMERO_HASTA > 0;
        } catch (SQLException e) {
            ControlMensajes.error("getLimiteComp: " + e, ContParamAplicacion.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean getTurnosCaja() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT COALESCE(CJ.ID_TURNO,0) AS ID_TURNO, COALESCE(FND_TURNOS_TRABAJO.NOMBRE, '') AS NOMBRE "
                    + "FROM TC_TURNOS_CAJA CJ "
                    + "LEFT JOIN FND_TURNOS_TRABAJO ON FND_TURNOS_TRABAJO.IDENTIFICADOR = CJ.ID_TURNO "
                    + "WHERE CJ.ID_CAJA = ?";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, CAJA_IDENTIFICADOR);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                TCJ_IDENTIFICADOR = rs.getInt("ID_TURNO");
                TCJ_NOMBRE = rs.getString("NOMBRE");
            } else {
                ControlMensajes.error("Turnos de cajas no definidos", "ACCESO AL SISTEMA");
            }

            pst.close();
            return TCJ_IDENTIFICADOR > 0;
        } catch (SQLException e) {
            ControlMensajes.error("getTurnosCaja: " + e, ContParamAplicacion.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean cantUsers() {
        ConexionJdbc jdbc = new ConexionJdbc();
        int idReg = 0;
        try {
            sql = "SELECT COUNT(*) FROM FND_USUARIOS";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idReg = rs.getInt(1);
            }

            pst.close();
            return idReg != 0;

        } catch (SQLException e) {
            ControlMensajes.error("cantUsers: " + e, ContParamAplicacion.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
