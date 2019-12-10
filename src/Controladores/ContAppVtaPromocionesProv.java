package Controladores;

import Conexiones.ConexionJdbc;
import Modelos.AppItemsPromProv;
import Modelos.AppPromocionesProv;
import Utils.ControlMensajes;
import Utils.FuncionesBd;
import Utils.Utilidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContAppVtaPromocionesProv {

    String titulo = ContAppVtaPromocionesProv.class.getName();

    public boolean importarPromociones(int server) {
        try {
            String sql = "SELECT IDENTIFICADOR, ID_SITIO, ID_PROVEEDOR, TO_CHAR(FECHA_DESDE, 'dd/mm/yyyy') AS FECHA_DESDE, "
                    + "TO_CHAR(FECHA_HASTA, 'dd/mm/yyyy') AS FECHA_HASTA, NOMBRE, TO_CHAR(FEC_CRE, 'dd/mm/yyyy') AS FEC_CRE, "
                    + "USR_CRE, TO_CHAR(FEC_MOD, 'dd/mm/yyyy') AS FEC_MOD, USR_MOD "
                    + "FROM APP_PROMOCIONES_PROV "
                    + "WHERE ID_SITIO=? "
                    + "AND TRUNC(FECHA_DESDE) <= TO_DATE(?,'dd/MM/yyyy') "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?,'dd/MM/yyyy')";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setLong(1, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy"));
            pst.setString(3, Utilidades.getFecha("dd/MM/yyyy"));
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("APP_PROMOCIONES_PROV");

            while (rs.next()) {
                AppPromocionesProv appPromocionesProv = new AppPromocionesProv();
                appPromocionesProv.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));
                appPromocionesProv.setID_SITIO(rs.getInt("ID_SITIO"));
                appPromocionesProv.setID_PROVEEDOR(rs.getInt("ID_PROVEEDOR"));
                appPromocionesProv.setFECHA_DESDE(rs.getString("FECHA_DESDE"));
                appPromocionesProv.setFECHA_HASTA(rs.getString("FECHA_HASTA"));
                appPromocionesProv.setNOMBRE(rs.getString("NOMBRE"));
                appPromocionesProv.setUSR_CRE(rs.getString("USR_CRE"));
                appPromocionesProv.setFEC_CRE(rs.getString("FEC_CRE"));
                appPromocionesProv.setUSR_MOD(rs.getString("USR_MOD"));
                appPromocionesProv.setFEC_MOD(rs.getString("FEC_MOD"));

                if (!insertarPromocion(appPromocionesProv, 0)) {
                    ControlMensajes.error("Promocion no importada", titulo);
                    return false;
                }
                importarItemsPromocion(1, appPromocionesProv.getIDENTIFICADOR());
            }

            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("importarPromociones(): " + ex, titulo);
            return false;
        }
    }

    public boolean insertarPromocion(AppPromocionesProv appPromocionesProv, int server) {
        try {
            String sql = "INSERT INTO APP_PROMOCIONES_PROV (IDENTIFICADOR, ID_SITIO, ID_PROVEEDOR, "
                    + "FECHA_DESDE, FECHA_HASTA, NOMBRE, FEC_CRE, USR_CRE, FEC_MOD, USR_MOD) "
                    + "VALUES(?, ?, ?, TO_DATE(?,'dd/mm/yyyy'), TO_DATE(?,'dd/mm/yyyy'), ?, "
                    + "TO_DATE(?,'dd/mm/yyyy'), ?, TO_DATE(?,'dd/mm/yyyy'), ?)";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setInt(1, appPromocionesProv.getIDENTIFICADOR());
            pst.setInt(2, appPromocionesProv.getID_SITIO());
            pst.setInt(3, appPromocionesProv.getID_PROVEEDOR());
            pst.setString(4, appPromocionesProv.getFECHA_DESDE());
            pst.setString(5, appPromocionesProv.getFECHA_HASTA());
            pst.setString(6, appPromocionesProv.getNOMBRE());
            pst.setString(7, appPromocionesProv.getFEC_CRE());
            pst.setString(8, appPromocionesProv.getUSR_CRE());
            pst.setString(9, appPromocionesProv.getFEC_MOD());
            pst.setString(10, appPromocionesProv.getUSR_MOD());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("insertarPromocion(): " + ex, titulo);
            return false;
        }
    }

    public boolean importarItemsPromocion(int server, int idPromocion) {
        try {
            String sql = "SELECT IDENTIFICADOR, ID_PROMOCION, ID_PRODUCTO, PRECIO_VENTA, COSTO_UNITARIO, "
                    + "CANTIDAD, TO_CHAR(FEC_CRE, 'dd/mm/yyyy') AS FEC_CRE, USR_CRE, "
                    + "TO_CHAR(FEC_MOD, 'dd/mm/yyyy')AS FEC_MOD, USR_MOD "
                    + "FROM APP_ITEMS_PROM_PROV "
                    + "WHERE ID_PROMOCION=? ";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setInt(1, idPromocion);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("APP_ITEMS_PROM_PROV");

            while (rs.next()) {
                AppItemsPromProv appItemsPromProv = new AppItemsPromProv();
                appItemsPromProv.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));
                appItemsPromProv.setID_PROMOCION(rs.getInt("ID_PROMOCION"));
                appItemsPromProv.setID_PRODUCTO(rs.getInt("ID_PRODUCTO"));
                appItemsPromProv.setPRECIO_VENTA(rs.getInt("PRECIO_VENTA"));
                appItemsPromProv.setCOSTO_UNITARIO(rs.getInt("COSTO_UNITARIO"));
                appItemsPromProv.setCANTIDAD(rs.getInt("CANTIDAD"));
                appItemsPromProv.setFEC_CRE(rs.getString("FEC_CRE"));
                appItemsPromProv.setUSR_CRE(rs.getString("USR_CRE"));
                appItemsPromProv.setFEC_MOD(rs.getString("FEC_MOD"));
                appItemsPromProv.setUSR_MOD(rs.getString("USR_MOD"));

                if (!insertarItemPromocion(appItemsPromProv, 0)) {
                    ControlMensajes.error("Item de promocion no importada", titulo);
                    return false;
                }
            }

            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("importarItemsPromocion(): " + ex, titulo);
            return false;
        }
    }

    public boolean insertarItemPromocion(AppItemsPromProv appItemsPromProv, int server) {
        try {
            String sql = "INSERT INTO APP_ITEMS_PROM_PROV (IDENTIFICADOR, ID_PROMOCION, ID_PRODUCTO, PRECIO_VENTA, "
                    + "COSTO_UNITARIO, CANTIDAD, FEC_CRE, USR_CRE, FEC_MOD, USR_MOD) "
                    + "VALUES(?, ?, ?, ?, ?, ?, TO_DATE(?,'dd/mm/yyyy'), ?, TO_DATE(?,'dd/mm/yyyy'), ?)";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setInt(1, appItemsPromProv.getIDENTIFICADOR());
            pst.setInt(2, appItemsPromProv.getID_PROMOCION());
            pst.setInt(3, appItemsPromProv.getID_PRODUCTO());
            pst.setInt(4, appItemsPromProv.getPRECIO_VENTA());
            pst.setInt(5, appItemsPromProv.getCOSTO_UNITARIO());
            pst.setInt(6, appItemsPromProv.getCANTIDAD());
            pst.setString(7, appItemsPromProv.getFEC_CRE());
            pst.setString(8, appItemsPromProv.getUSR_CRE());
            pst.setString(9, appItemsPromProv.getFEC_MOD());
            pst.setString(10, appItemsPromProv.getUSR_MOD());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("insertarItemPromocion(): " + ex, titulo);
            return false;
        }
    }

    public boolean actualizarCantidad(int identificador, int cantidad, int server) {
        try {
            String sql = "UPDATE APP_ITEMS_PROM_PROV SET CANTIDAD=CANTIDAD+? WHERE IDENTIFICADOR=?";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setLong(1, cantidad);
            pst.setInt(2, identificador);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("ultimoNumero(): " + ex, titulo);
            return false;
        }
    }

    //obtener todas las promociones activas de la base de datos local
    public ArrayList<AppPromocionesProv> getPromocionesActivas() {
        try {
            String sql = "SELECT IDENTIFICADOR, ID_SITIO, ID_PROVEEDOR, TO_CHAR(FECHA_DESDE, 'dd/MM/yyyy') AS FECHA_DESDE, "
                    + "TO_CHAR(FECHA_HASTA, 'dd/MM/yyyy') AS FECHA_HASTA, NOMBRE, FEC_CRE, USR_CRE, FEC_MOD, USR_MOD "
                    + "FROM APP_PROMOCIONES_PROV "
                    + "WHERE ID_SITIO=? "
                    + "AND TRUNC(FECHA_DESDE) <= TO_DATE(?,'dd/MM/yyyy') "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?,'dd/MM/yyyy')";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = cnn.local().prepareStatement(sql);

            pst.setInt(1, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy"));
            pst.setString(3, Utilidades.getFecha("dd/MM/yyyy"));
            ResultSet rs = pst.executeQuery();

            AppPromocionesProv appPromocionesProv = new AppPromocionesProv();
            ArrayList<AppPromocionesProv> lista = new ArrayList<>();
            while (rs.next()) {
                appPromocionesProv.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));
                appPromocionesProv.setID_SITIO(rs.getInt("ID_SITIO"));
                appPromocionesProv.setID_PROVEEDOR(rs.getInt("ID_PROVEEDOR"));
                appPromocionesProv.setFECHA_DESDE(rs.getString("FECHA_DESDE"));
                appPromocionesProv.setFECHA_HASTA(rs.getString("FECHA_HASTA"));
                appPromocionesProv.setNOMBRE(rs.getString("NOMBRE"));
                appPromocionesProv.setUSR_CRE(rs.getString("USR_CRE"));
                appPromocionesProv.setFEC_CRE(rs.getString("FEC_CRE"));
                appPromocionesProv.setUSR_MOD(rs.getString("USR_MOD"));
                appPromocionesProv.setFEC_MOD(rs.getString("FEC_MOD"));

                lista.add(appPromocionesProv);
            }

            return lista;
        } catch (SQLException ex) {
            ControlMensajes.error("getPromocionesActivas(): " + ex, titulo);
            return null;
        }
    }

    //validar si producto esta en promocion
    public ArrayList<AppPromocionesProv> validarProductoPromo(int idProducto) {
        try {
            String sql = "SELECT PR.NOMBRE, TO_CHAR(FECHA_DESDE, 'dd/MM/yyyy') AS FECHA_DESDE, TO_CHAR(FECHA_HASTA, 'dd/MM/yyyy') AS FECHA_HASTA, "
                    + "APP_ITEMS_PROM_PROV.IDENTIFICADOR "
                    + "FROM APP_PROMOCIONES_PROV PR "
                    + "INNER JOIN APP_ITEMS_PROM_PROV ON APP_ITEMS_PROM_PROV.ID_PROMOCION = PR.IDENTIFICADOR "
                    + "WHERE PR.ID_SITIO=? "
                    + "AND TRUNC(PR.FECHA_DESDE) <= TO_DATE(?, 'dd/MM/yyyy') "
                    + "AND TRUNC(PR.FECHA_HASTA) >= TO_DATE(?, 'dd/MM/yyyy') "
                    + "AND APP_ITEMS_PROM_PROV.ID_PRODUCTO=?";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = cnn.local().prepareStatement(sql);

            pst.setInt(1, ContParamAplicacion.SUC_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy"));
            pst.setString(3, Utilidades.getFecha("dd/MM/yyyy"));
            pst.setInt(4, idProducto);
            ResultSet rs = pst.executeQuery();

            AppPromocionesProv appPromocionesProv = new AppPromocionesProv();
            ArrayList<AppPromocionesProv> lista = new ArrayList<>();
            while (rs.next()) {
                appPromocionesProv.setNOMBRE(rs.getString("NOMBRE"));
                appPromocionesProv.setFECHA_DESDE(rs.getString("FECHA_DESDE"));
                appPromocionesProv.setFECHA_HASTA(rs.getString("FECHA_HASTA"));
                appPromocionesProv.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));

                lista.add(appPromocionesProv);
            }

            return lista;
        } catch (SQLException ex) {
            ControlMensajes.error("getPromocionesActivas(): " + ex, titulo);
            return null;
        }
    }
}
