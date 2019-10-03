package Controladores;

import Modelos.TcDetalleArqueo;
import Modelos.TcArqueoCaja;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Utils.FuncionesBd;
import Utils.Utilidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContTcArqueoCaja {
   
    ContTcDeclaracionEfec decEfect = new ContTcDeclaracionEfec();

    private String sql;
    long identificador;

    public long identificador() {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT SQ_ARQUEO_CAJA.NEXTVAL FROM DUAL";
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                identificador = rs.getLong(1);
            } else {
                identificador = 0;
            }

            pst.close();
            return identificador;
        } catch (SQLException ex) {
            ControlMensajes.error("identificador: " + ex, ContTcArqueoCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public long totalConceptos(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(SUM(MONTO_TOTAL),0) FROM VTA_COMPROBANTES "
                + "WHERE ID_CONTROL_CAJA = ? AND FEC_ANULACION IS NULL";
        long total = 0;

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                total = rs.getLong(1);
            }

            pst.close();
            return total;
        } catch (SQLException e) {
            ControlMensajes.error("totalConceptos: " + e, ContTcArqueoCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public long totalVouchers(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(SUM(IMPORTE),0) FROM TC_ITEMS_COBRO I\n"
                + "INNER JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = I.ID_COBRO\n"
                + "WHERE I.ID_FORMA_PAGO <> 9 AND TC_COBROS_CAJA.ID_CONTROL=?";
        long total = 0;

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                total = rs.getLong(1);
            }

            pst.close();
            return total;
        } catch (SQLException e) {
            ControlMensajes.error("totalVouchers: " + e, ContTcArqueoCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int nroControl() {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT MAX(NRO_CONTROL) FROM TC_ARQUEO_CAJA ";
        int nro = 0;

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nro = rs.getInt(1) + 1;
            }

            pst.close();
            return nro;
        } catch (SQLException e) {
            ControlMensajes.error("nroControl: " + e, ContTcArqueoCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int totalRedVuelto(long idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        sql = "SELECT COALESCE(SUM(IMPORTE_VUELTO), 0) FROM TC_COBROS_CAJA "
                + "WHERE ID_CONTROL = ?";
        int vuelto = 0;

        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idControlCaja);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                vuelto = rs.getInt(1);
            }

            pst.close();
            return vuelto;
        } catch (SQLException e) {
            ControlMensajes.error("totalRedVuelto: " + e, ContTcArqueoCaja.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean fechaEnvServer(long id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE TC_ARQUEO_CAJA SET FECHA_ENVIO_SRV = SYSDATE "
                    + "WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, id);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("fechaEnvServer: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert(TcArqueoCaja dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_ARQUEO_CAJA (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_CAJA, ID_CONTROL, "
                    + "FECHA, VR_TIPO_ARQUEO, USR_CRE, FEC_CRE, DESCRIPCION, CONFIRMADO, TOTAL_CONCEPTOS, "
                    + "TOTAL_VALORES, DIFERENCIA, ID_CAJERO, NRO_CONTROL, TOTAL_VUELTOS) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            pst.setString(6, Utilidades.getFecha("dd/MMM/yyyy"));
            pst.setString(7, dts.getVrTipoArqueo());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, FuncionesBd.sysdate());
            pst.setString(10, dts.getDescripcion());
            pst.setString(11, dts.getConfirmado());
            pst.setLong(12, dts.getTotalConceptos());
            pst.setLong(13, dts.getTotalValores());
            pst.setLong(14, dts.getDiferencia());
            pst.setInt(15, dts.getIdCajero());
            pst.setInt(16, dts.getNroControl());
            pst.setLong(17, dts.getTotalRedVueltos());

            int x = pst.executeUpdate();

            //pst.close();
            return x != 0;

        } catch (SQLException ex) {
            ControlMensajes.error("insert: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    public boolean arqueado(long identificador) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE TC_CONTROL_CAJA SET ARQUEADO = 'S' WHERE IDENTIFICADOR = ? ";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, identificador);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;

        } catch (SQLException ex) {
            ControlMensajes.error("arqueado: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //DETALLE DE ARQUEO
    public ResultSet conceptos(int idControlCaja) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT DESCRIPCION, IDENTIFICADOR,\n"
                    + "(\n"
                    + "SELECT COALESCE(SUM(IMPORTE),0) FROM TC_ITEMS_COBRO \n"
                    + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                    + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = ? \n"
                    + ") AS TOTAL,\n"
                    + "(\n"
                    + "SELECT COUNT(*) FROM TC_ITEMS_COBRO \n"
                    + "LEFT JOIN TC_COBROS_CAJA ON TC_COBROS_CAJA.IDENTIFICADOR = TC_ITEMS_COBRO.ID_COBRO\n"
                    + "WHERE ID_FORMA_PAGO = COALESCE(TCC.ID_FORMA_PAGO,9) AND TC_COBROS_CAJA.ID_CONTROL = ? \n"
                    + ") AS CANT_COMPROBANTE, TCC.ID_FORMA_PAGO\n"
                    + "FROM TC_CONCEPTOS_ARQUEO TCC";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idControlCaja);
            pst.setInt(2, idControlCaja);

            ResultSet rs = pst.executeQuery();

            return rs;
        } catch (SQLException e) {
            ControlMensajes.error("conceptos: " + e, ContTcArqueoCaja.class.getName());
            return null;
        }
    }

    public boolean insDetalleArqueo(TcDetalleArqueo dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_DETALLE_ARQUEO(ID_ARQUEO, ID_CONCEPTO, ID_EMPRESA, ID_UNIDAD,"
                    + "USR_CRE, FEC_CRE, IMPORTE_CALC, IMPORTE_DECL, CANTIDAD_COMP)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdArqueo());
            pst.setInt(2, dts.getIdConcepto());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getIdUnidad());
            pst.setString(5, dts.getUsrCre());
            pst.setTimestamp(6, FuncionesBd.sysdate());
            pst.setLong(7, dts.getImporteCalc());
            pst.setLong(8, dts.getImporteDecl());
            pst.setLong(9, dts.getCantidadComp());

            int x = pst.executeUpdate();

            //pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("(insDetalleArqueo: " + e, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }

    //=============================================
    //EXPORTAR ARQUEOS NO ENVIADOS A SERVER 
    //=============================================
    public boolean expArqueos(String condicion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int fila = 0;
            TcArqueoCaja dtsArqueo = new TcArqueoCaja();
            sql = "SELECT * FROM TC_ARQUEO_CAJA WHERE FECHA_ENVIO_SRV IS NULL " + condicion;

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dtsArqueo.setIdentificador(rs.getLong("IDENTIFICADOR"));
                dtsArqueo.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsArqueo.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsArqueo.setIdCaja(rs.getInt("ID_CAJA"));
                dtsArqueo.setIdControl(rs.getLong("ID_CONTROL"));
                dtsArqueo.setFefecha(rs.getDate("FECHA"));
                dtsArqueo.setVrTipoArqueo(rs.getString("VR_TIPO_ARQUEO"));
                dtsArqueo.setUsrCre(rs.getString("USR_CRE"));
                dtsArqueo.setFecCre(rs.getTimestamp("FEC_CRE"));
                dtsArqueo.setDescripcion(rs.getString("DESCRIPCION"));
                dtsArqueo.setConfirmado(rs.getString("CONFIRMADO"));
                dtsArqueo.setTotalConceptos(rs.getLong("TOTAL_CONCEPTOS"));
                dtsArqueo.setTotalValores(rs.getLong("TOTAL_VALORES"));
                dtsArqueo.setDiferencia(rs.getLong("DIFERENCIA"));
                dtsArqueo.setIdCajero(rs.getInt("ID_CAJERO"));
                dtsArqueo.setNroControl(rs.getInt("NRO_CONTROL"));
                dtsArqueo.setTotalRedVueltos(rs.getLong("TOTAL_RED_VUELTOS"));

                if (insertExpArqueo(dtsArqueo, 1)) {
                    if (expDetalleArqueos(dtsArqueo.getIdentificador())) {
                        if (decEfect.expDecEfectivo(dtsArqueo.getIdentificador())) {
                            if (fechaEnvServer(dtsArqueo.getIdentificador())) {
                                fila++;
                            }
                        } else {
                            System.out.println("Declaracion de efectivo no enviado");
                            return false;
                        }
                    } else {
                        System.out.println("Detalle de Arqueo nro (" + dtsArqueo.getIdentificador() + ") no fue enviado");
                        return false;
                    }
                } else {
                    System.out.println("Arqueo nro (" + dtsArqueo.getIdentificador() + ") no fue enviado");
                    return false;
                }
            }
            System.out.println("\tARQUEOS EXPORTADOS: " + fila);
            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("expArqueos: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertExpArqueo(TcArqueoCaja dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_ARQUEO_CAJA (IDENTIFICADOR, ID_EMPRESA, ID_UNIDAD, ID_CAJA, ID_CONTROL, "
                    + "FECHA, VR_TIPO_ARQUEO, USR_CRE, FEC_CRE, DESCRIPCION, "
                    + "CONFIRMADO, TOTAL_CONCEPTOS, TOTAL_VALORES, DIFERENCIA, ID_CAJERO, "
                    + "NRO_CONTROL, TOTAL_VUELTOS) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            pst.setDate(6, dts.getFefecha());
            pst.setString(7, dts.getVrTipoArqueo());
            pst.setString(8, dts.getUsrCre());
            pst.setTimestamp(9, dts.getFecCre());
            pst.setString(10, dts.getDescripcion());
            pst.setString(11, dts.getConfirmado());
            pst.setLong(12, dts.getTotalConceptos());
            pst.setLong(13, dts.getTotalValores());
            pst.setLong(14, dts.getDiferencia());
            pst.setInt(15, dts.getIdCajero());
            pst.setInt(16, dts.getNroControl());
            pst.setLong(17, dts.getTotalRedVueltos());

            int x = pst.executeUpdate();

            //pst.close();
            return x != 0;

        } catch (SQLException ex) {
            ControlMensajes.error("insertExpArqueo: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }
    
    public boolean expDetalleArqueos(long idArqueo) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {

            TcDetalleArqueo dts = new TcDetalleArqueo();
            sql = "SELECT * FROM TC_DETALLE_ARQUEO WHERE ID_ARQUEO = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setLong(1, idArqueo);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dts.setIdArqueo(rs.getLong("ID_ARQUEO"));
                dts.setIdConcepto(rs.getInt("ID_CONCEPTO"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFeCre(rs.getTimestamp("FEC_CRE"));
                dts.setImporteCalc(rs.getLong("IMPORTE_CALC"));
                dts.setImporteDecl(rs.getLong("IMPORTE_DECL"));
                dts.setCantidadComp(rs.getLong("CANTIDAD_COMP"));

                insertExpDetalleArqueos(dts, 1);
            }
            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("expDetalleArqueos: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertExpDetalleArqueos(TcDetalleArqueo dts, int con) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_DETALLE_ARQUEO(ID_ARQUEO, ID_CONCEPTO, ID_EMPRESA, ID_UNIDAD,"
                    + "USR_CRE, FEC_CRE, IMPORTE_CALC, IMPORTE_DECL, CANTIDAD_COMP)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = null;
            if (con == 0) {
                pst = jdbc.local().prepareStatement(sql);
            } else if (con == 1) {
                pst = jdbc.server().prepareStatement(sql);
            }

            pst.setLong(1, dts.getIdArqueo());
            pst.setInt(2, dts.getIdConcepto());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getIdUnidad());
            pst.setString(5, dts.getUsrCre());
            pst.setTimestamp(6, dts.getFeCre());
            pst.setLong(7, dts.getImporteCalc());
            pst.setLong(8, dts.getImporteDecl());
            pst.setLong(9, dts.getCantidadComp());

            int x = pst.executeUpdate();

            //pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("(insDetalleArqueo: " + e, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }

    }
    
    public boolean valDetalleArqueos(int idArqueo) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "SELECT * FROM TC_DETALLE_ARQUEO WHERE ID_ARQUEO = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, idArqueo);
            ResultSet rs = pst.executeQuery();

            pst.close();
            return rs.next();
        } catch (SQLException ex) {
            ControlMensajes.error("valDetalleArqueos: " + ex, ContTcArqueoCaja.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
