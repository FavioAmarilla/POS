/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Conexiones.ConexionJdbc;
import Modelos.StkParametros;
import Utils.ControlMensajes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author favio
 */
public class ContStkParametros {

    String titulo = ContStkParametros.class.getName();

    public StkParametros getParametrosStock(int id_sitio) {
        ConexionJdbc cnn = new ConexionJdbc();

        try {

            PreparedStatement pst = cnn.local().prepareStatement("SELECT COALESCE(ID_ALMACEN_VTAS, 0) AS ID_ALMACEN_VTAS, COALESCE(ID_TRANS_VTAS, 0) AS ID_TRANS_VTAS FROM STK_PARAMETROS WHERE ID_SITIO=?");
            pst.setInt(1, id_sitio);

            ResultSet rs = pst.executeQuery();
            StkParametros stkParametros = null;
            if (rs.next()) {
                stkParametros = new StkParametros();
                stkParametros.setId_almaven_vtas(rs.getInt("ID_ALMACEN_VTAS"));
                stkParametros.setId_trans_vtas(rs.getInt("ID_TRANS_VTAS"));
            }

            pst.close();
            rs.close();
            return stkParametros;
        } catch (SQLException e) {
            ControlMensajes.error("Error: " + e, titulo);
            return null;
        } finally {
            cnn.cerrarConexion();
        }
    }

      
}
