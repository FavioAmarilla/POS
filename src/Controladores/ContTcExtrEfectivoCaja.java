package Controladores;


import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.TcDetalleExtrEfec;
import Vistas.FrmLogin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContTcExtrEfectivoCaja {

   
    TcDetalleExtrEfec dtsExtEfect = new TcDetalleExtrEfec();
    private String sql;

   public static Timestamp sysdate() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            Timestamp fecha = null;
            String sql = "SELECT SYSDATE FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fecha = rs.getTimestamp(1);
            }

            pst.close();
            return fecha;
        } catch (SQLException ex) {
            ControlMensajes.error("sysdate: " + ex, ContTcCobrosCaja.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int identificador() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int id = 0;
            sql = "SELECT SQ_TC_EXTR_EFECTIVO_CAJA.NEXTVAL FROM DUAL";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            pst.close();
            return id;
        } catch (SQLException ex) {
            ControlMensajes.error("identificador: " + ex, ContTcExtrEfectivoCaja.class.getName());
            return 0;
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
            ControlMensajes.error("idControlCaja: " + e, ContTcExtrEfectivoCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert(TcDetalleExtrEfec dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_EXTR_EFECTIVO_CAJA (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, "
                    + "ID_CAJA, ID_CONTROL, ID_CAJERO, NUMERO, FECHA, USR_CRE, FEC_CRE, DESCRIPCION,"
                    + "NRO_DOC_BENEFICIARIO, NOMBRE_BENEFICIARIO, NRO_REF_PAGO, VR_TIPO_RETIRO, ID_REF_PAGO)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdEmpresa());
            pst.setInt(3, dts.getIdUnidad());
            pst.setInt(4, dts.getIdCaja());
            pst.setLong(5, dts.getIdControl());
            pst.setInt(6, dts.getIdCajero());
            pst.setString(7, dts.getNumero());
            pst.setTimestamp(8,sysdate());
            pst.setString(9, dts.getUsrCre());
            pst.setTimestamp(10,sysdate());
            pst.setString(11, dts.getDescripcion());
            pst.setString(12, dts.getNroDocBeneficiario());
            pst.setString(13, dts.getNombreBeneficiario());
            pst.setString(14, dts.getNroRefPago());
            pst.setString(15, dts.getVrTipoRetiro());
            pst.setInt(16, dts.getIdRefPago());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;

        } catch (SQLException ex) {
            ControlMensajes.error("insert: " + ex, ContTcExtrEfectivoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean detalleExtraccion(TcDetalleExtrEfec dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_DETALLE_EXTR_EFEC (ID_EXTRACCION, ID_DENOMINACION, ID_EMPRESA, ID_UNIDAD, "
                    + "CANTIDAD, IMPORTE, USR_CRE, FEC_CRE)"
                    + "VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdExtraccion());
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
            ControlMensajes.error("detalleExtraccion: " + ex, ContTcDeclaracionEfec.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //======================================================= 
    //EXPORTAR EXGTRACCIONES DE EFECTIVO NO ENVIADOS A SERVER 
    //=======================================================
    public boolean expExtraccionEfectivo(long idControl) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int fila = 0;
            sql = "SELECT * FROM TC_EXTR_EFECTIVO_CAJA WHERE ID_CONTROL = ?";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControl);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dtsExtEfect.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dtsExtEfect.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsExtEfect.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsExtEfect.setIdCaja(rs.getInt("ID_CAJA"));
                dtsExtEfect.setIdControl(rs.getLong("ID_CONTROL"));
                dtsExtEfect.setIdCajero(rs.getInt("ID_CAJERO"));
                dtsExtEfect.setNumero(rs.getString("NUMERO"));
                dtsExtEfect.setFefecha(rs.getTimestamp("FECHA"));
                dtsExtEfect.setUsrCre(rs.getString("USR_CRE"));
                dtsExtEfect.setFeCre(rs.getTimestamp("FEC_CRE"));
                dtsExtEfect.setDescripcion(rs.getString("DESCRIPCION"));
                dtsExtEfect.setNroDocBeneficiario(rs.getString("NRO_DOC_BENEFICIARIO"));
                dtsExtEfect.setNombreBeneficiario(rs.getString("NOMBRE_BENEFICIARIO"));

                if (insert(dtsExtEfect, 1)) {
                    if (expDetExtraccionEfectivo(dtsExtEfect.getIdentificador())) {
                        fila++;
                    } else {
                        System.out.println("\tDetalle de Ext. de Efec. nro (" + dtsExtEfect.getIdentificador() + ") no exportado");
                    }
                } else {
                    System.out.println("\tExtracion de Efectivo nro (" + dtsExtEfect.getIdentificador() + ") no exportado");
                }
                fila++;
            }

            System.out.println("\tEXTRACCIONES DE EFECTIVO EXPORTADOS: " + fila);
            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("expExtraccionEfectivo: " + ex, ContTcExtrEfectivoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean expDetExtraccionEfectivo(long idExtraccion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {

            sql = "SELECT * FROM TC_DETALLE_EXTR_EFEC WHERE ID_EXTRACCION = ?";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idExtraccion);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dtsExtEfect.setIdExtraccion(rs.getLong("ID_EXTRACCION"));
                dtsExtEfect.setIdDenominacion(rs.getInt("ID_DENOMINACION"));
                dtsExtEfect.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsExtEfect.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsExtEfect.setCantidad(rs.getInt("CANTIDAD"));
                dtsExtEfect.setImporte(rs.getInt("IMPORTE"));
                dtsExtEfect.setUsrCre(rs.getString("USR_CRE"));
                dtsExtEfect.setFeCre(rs.getTimestamp("FEC_CRE"));

                detalleExtraccion(dtsExtEfect, 1);
            }

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("expDetExtraccionEfectivo: " + ex, ContTcExtrEfectivoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
