package Controladores;

import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.FndFormasPago;
import Utils.FuncionesBd;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContFndFormasPago {

    private final String tituloMsj = ContFndFormasPago.class.getName();

    public ArrayList<FndFormasPago> GetFormaPago(String sql) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ArrayList<FndFormasPago> lista = new ArrayList<>();

            if (rs.next()) {
                FndFormasPago dtsFormaPago = new FndFormasPago();
                dtsFormaPago.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsFormaPago.setAbreviatura(rs.getString("ABREVIATURA"));
                dtsFormaPago.setVrClase(rs.getString("VR_CLASE"));
                dtsFormaPago.setIdTipoDocum(rs.getInt("ID_TIPO_DOCUM"));

                lista.add(dtsFormaPago);
            }

            return lista;
        } catch (SQLException e) {
            ControlMensajes.error("GetFormaPago: " + e, tituloMsj);
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean importarFormasPago() {
        ConexionJdbc jdbc = new ConexionJdbc();
        FndFormasPago dts = new FndFormasPago();
        int filas = 0;
        try {

            PreparedStatement pst = jdbc.server().prepareStatement("SELECT * FROM FND_FORMAS_PAGO");
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("FND_FORMAS_PAGO");
            while (rs.next()) {
                dts.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setAbreviatura(rs.getString("ABREVIATURA"));
                dts.setNumero(rs.getString("NUMERO"));
                dts.setCantidadDias(rs.getInt("CANTIDAD_DIAS"));
                dts.setIdTipoDocum(rs.getInt("ID_TIPO_DOCUM"));
                dts.setPagoEfectivo(rs.getString("PAGO_EFECTIVO"));
                dts.setPagoCheque(rs.getString("PAGO_CHEQUE"));
                dts.setVrClase(rs.getString("VR_CLASE"));
                dts.setCodigoPos(rs.getString("CODIGO_POS"));
                dts.setIdMoneda(rs.getInt("ID_MONEDA"));
                dts.setMontoMaxVuelto(rs.getInt("MONTO_MAX_VUELTO"));
                dts.setMontoMinVuelto(rs.getInt("MONTO_MIN_VUELTO"));
                dts.setActivo(rs.getString("ACTIVO"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setCancelarDescProm(rs.getString("CANCELAR_DESC_PROM"));
                dts.setMontoMaxCobros(rs.getInt("MONTO_MAX_COBROS"));
                dts.setMontoMinCobros(rs.getInt("MONTO_MIN_COBROS"));
                dts.setEsPrincipal(rs.getString("ES_PRINCIPAL"));

                filas++;
                insertarFormasPago(dts);
            }

            System.out.println("\tFORMAS DE PAGO IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("importarFormasPago: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarFormasPago(FndFormasPago dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {

            PreparedStatement pst = jdbc.local().prepareStatement("INSERT INTO FND_FORMAS_PAGO("
                    + "IDENTIFICADOR, DESCRIPCION, USR_CRE, FEC_CRE, "
                    + "ABREVIATURA, NUMERO, CANTIDAD_DIAS, ID_TIPO_DOCUM, "
                    + "PAGO_EFECTIVO, PAGO_CHEQUE, VR_CLASE, CODIGO_POS, ID_MONEDA, "
                    + "MONTO_MAX_VUELTO, MONTO_MIN_VUELTO, ACTIVO, ID_EMPRESA, ID_UNIDAD, "
                    + "CANCELAR_DESC_PROM, MONTO_MAX_COBROS, MONTO_MIN_COBROS, ES_PRINCIPAL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getUsrCre());
            pst.setTimestamp(4, dts.getFecCre());
            pst.setString(5, dts.getAbreviatura());
            pst.setString(6, dts.getNumero());
            pst.setInt(7, dts.getCantidadDias());
            pst.setInt(8, dts.getIdTipoDocum());
            pst.setString(9, dts.getPagoEfectivo());
            pst.setString(10, dts.getPagoCheque());
            pst.setString(11, dts.getVrClase());
            pst.setString(12, dts.getCodigoPos());
            pst.setInt(13, dts.getIdMoneda());
            pst.setInt(14, dts.getMontoMaxVuelto());
            pst.setInt(15, dts.getMontoMinVuelto());
            pst.setString(16, dts.getActivo());
            pst.setInt(17, dts.getIdEmpresa());
            pst.setInt(18, dts.getIdUnidad());
            pst.setString(19, dts.getCancelarDescProm());
            pst.setInt(20, dts.getMontoMaxCobros());
            pst.setInt(21, dts.getMontoMinCobros());
            pst.setString(22, dts.getEsPrincipal());
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarFormasPago: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
