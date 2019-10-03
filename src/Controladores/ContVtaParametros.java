/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Conexiones.ConexionJdbc;
import Modelos.VtaParametros;
import Utils.ControlMensajes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author favio
 */
public class ContVtaParametros {

    String titulo = ContVtaParametros.class.getName();

    public VtaParametros getParametrosVenta(int id_sitio) {
        ConexionJdbc cnn = new ConexionJdbc();

        try {

            PreparedStatement pst = cnn.local().prepareStatement("SELECT ID_CLIENTE_POS, NOMBRE_CLTE_GENERICO FROM VTA_PARAMETROS WHERE ID_SITIO=?");
            pst.setInt(1, id_sitio);

            ResultSet rs = pst.executeQuery();
            VtaParametros vtaParametros = null;
            if (rs.next()) {
                vtaParametros = new VtaParametros();
                vtaParametros.setId_cliente_pos(rs.getInt("ID_CLIENTE_POS"));
                vtaParametros.setNombre_clte_generico(rs.getString("NOMBRE_CLTE_GENERICO"));
            }

            pst.close();
            rs.close();
            return vtaParametros;
        } catch (SQLException e) {
            ControlMensajes.error("Error: " + e, titulo);
            return null;
        } finally {
            cnn.cerrarConexion();
        }
    }
}
