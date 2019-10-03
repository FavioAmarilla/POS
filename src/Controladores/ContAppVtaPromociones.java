package Controladores;

import Conexiones.ConexionJdbc;
import Modelos.AppVtaPromociones;
import Utils.ControlMensajes;
import Utils.FuncionesBd;
import Utils.Utilidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContAppVtaPromociones {

    String titulo = ContAppVtaPromociones.class.getName();

    public boolean importarPromociones(int server) {
        try {
            String sql = "SELECT IDENTIFICADOR, ID_SITIO, ID_CAJA, COALESCE(ULT_NUMERO, 0) AS ULT_NUMERO, TO_CHAR(FECHA_DESDE,'dd/mm/yyyy') AS FECHA_DESDE, "
                    + "TO_CHAR(FECHA_HASTA, 'dd/mm/yyyy') AS FECHA_HASTA, NOMBRE, DESCRIPCION, USR_CRE, TO_CHAR(FEC_CRE,'dd/mm/yyyy') AS FEC_CRE, MONTO "
                    + "FROM APP_VTA_PROMOCIONES "
                    + "WHERE ID_CAJA=? "
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
            pst.setLong(1, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy"));
            pst.setString(3, Utilidades.getFecha("dd/MM/yyyy"));
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("APP_VTA_PROMOCIONES");

            while (rs.next()) {
                AppVtaPromociones appVtaPromociones = new AppVtaPromociones();
                appVtaPromociones.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));
                appVtaPromociones.setID_SITIO(rs.getInt("ID_SITIO"));
                appVtaPromociones.setID_CAJA(rs.getInt("ID_CAJA"));
                appVtaPromociones.setULT_NUMERO(rs.getInt("ULT_NUMERO"));
                appVtaPromociones.setFECHA_DESDE(rs.getString("FECHA_DESDE"));
                appVtaPromociones.setFECHA_HASTA(rs.getString("FECHA_HASTA"));
                appVtaPromociones.setNOMBRE(rs.getString("NOMBRE"));
                appVtaPromociones.setDESCRIPCION(rs.getString("DESCRIPCION"));
                appVtaPromociones.setUSR_CRE(rs.getString("USR_CRE"));
                appVtaPromociones.setFEC_CRE(rs.getString("FEC_CRE"));
                appVtaPromociones.setMONTO(rs.getInt("MONTO"));

                if (eliminarPromocion(appVtaPromociones.getIDENTIFICADOR(), 0)) {
                    if (!insertarPromocion(appVtaPromociones, 0)) {
                        ControlMensajes.error("Promocion no registrada en local", titulo);
                        return false;
                    }
                } else {
                    ControlMensajes.error("Promocion no eliminada en local", titulo);
                    return false;
                }
            }

            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("importarPromociones(): " + ex, titulo);
            return false;
        }
    }

    public boolean insertarPromocion(AppVtaPromociones appVtaPromociones, int server) {
        try {
            String sql = "INSERT INTO APP_VTA_PROMOCIONES (IDENTIFICADOR, ID_SITIO, ID_CAJA, "
                    + "ULT_NUMERO, FECHA_DESDE, FECHA_HASTA, NOMBRE, DESCRIPCION, USR_CRE, FEC_CRE, MONTO) "
                    + "VALUES(?, ?, ?, ?, TO_DATE(?,'dd/mm/yyyy'), TO_DATE(?,'dd/mm/yyyy'), ?, ?, ?, TO_DATE(?,'dd/mm/yyyy'), ?)";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setInt(1, appVtaPromociones.getIDENTIFICADOR());
            pst.setInt(2, appVtaPromociones.getID_SITIO());
            pst.setInt(3, appVtaPromociones.getID_CAJA());
            pst.setInt(4, appVtaPromociones.getULT_NUMERO());
            pst.setString(5, appVtaPromociones.getFECHA_DESDE());
            pst.setString(6, appVtaPromociones.getFECHA_HASTA());
            pst.setString(7, appVtaPromociones.getNOMBRE());
            pst.setString(8, appVtaPromociones.getDESCRIPCION());
            pst.setString(9, appVtaPromociones.getUSR_CRE());
            pst.setString(10, appVtaPromociones.getFEC_CRE());
            pst.setInt(11, appVtaPromociones.getMONTO());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("insertarPromocion(): " + ex, titulo);
            return false;
        }
    }

    public boolean actualizarUltimoNumero(int identificador, int numero, int server) {
        try {
            String sql = "UPDATE APP_VTA_PROMOCIONES SET ULT_NUMERO=? WHERE IDENTIFICADOR=?  AND ID_CAJA=?";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setLong(1, numero);
            pst.setInt(2, identificador);
            pst.setLong(3, ContParamAplicacion.CAJA_IDENTIFICADOR);

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("ultimoNumero(): " + ex, titulo);
            return false;
        }
    }

    public boolean eliminarPromocion(int identificador, int server) {
        try {
            String sql = "DELETE FROM APP_VTA_PROMOCIONES WHERE IDENTIFICADOR=? AND ID_CAJA=?";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = null;
            if (server == 0) {
                pst = cnn.local().prepareStatement(sql);
            }
            if (server == 1) {
                pst = cnn.server().prepareStatement(sql);
            }
            pst.setLong(1, identificador);
            pst.setLong(2, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.executeUpdate();

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("eliminarPromocion(): " + ex, titulo);
            return false;
        }
    }

    //obtener todas las promociones activas de la base de datos local
    public ArrayList<AppVtaPromociones> getPromocionesActivas() {
        try {
            String sql = "SELECT IDENTIFICADOR, ID_SITIO, ID_CAJA, COALESCE(ULT_NUMERO, 0) AS ULT_NUMERO, TO_CHAR(FECHA_DESDE,'dd/mm/yyyy') AS FECHA_DESDE, "
                    + "TO_CHAR(FECHA_HASTA, 'dd/mm/yyyy') AS FECHA_HASTA, NOMBRE, DESCRIPCION, USR_CRE, TO_CHAR(FEC_CRE,'dd/mm/yyyy') AS FEC_CRE, MONTO "
                    + "FROM APP_VTA_PROMOCIONES "
                    + "WHERE ID_CAJA=? "
                    + "AND TRUNC(FECHA_DESDE) <= TO_DATE(?,'dd/MM/yyyy') "
                    + "AND TRUNC(FECHA_HASTA) >= TO_DATE(?,'dd/MM/yyyy')";

            ConexionJdbc cnn = new ConexionJdbc();
            PreparedStatement pst = cnn.local().prepareStatement(sql);

            pst.setInt(1, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy"));
            pst.setString(3, Utilidades.getFecha("dd/MM/yyyy"));
            ResultSet rs = pst.executeQuery();

            AppVtaPromociones appVtaPromociones = new AppVtaPromociones();
            ArrayList<AppVtaPromociones> lista = new ArrayList<>();
            while (rs.next()) {
                appVtaPromociones.setIDENTIFICADOR(rs.getInt("IDENTIFICADOR"));
                appVtaPromociones.setID_SITIO(rs.getInt("ID_SITIO"));
                appVtaPromociones.setID_CAJA(rs.getInt("ID_CAJA"));
                appVtaPromociones.setULT_NUMERO(rs.getInt("ULT_NUMERO"));
                appVtaPromociones.setFECHA_DESDE(rs.getString("FECHA_DESDE"));
                appVtaPromociones.setFECHA_HASTA(rs.getString("FECHA_HASTA"));
                appVtaPromociones.setNOMBRE(rs.getString("NOMBRE"));
                appVtaPromociones.setDESCRIPCION(rs.getString("DESCRIPCION"));
                appVtaPromociones.setUSR_CRE(rs.getString("USR_CRE"));
                appVtaPromociones.setFEC_CRE(rs.getString("FEC_CRE"));
                appVtaPromociones.setMONTO(rs.getInt("MONTO"));

                lista.add(appVtaPromociones);
            }

            return lista;
        } catch (SQLException ex) {
            ControlMensajes.error("getPromocionesActivas(): " + ex, titulo);
            return null;
        }
    }

}
