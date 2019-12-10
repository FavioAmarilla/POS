package Controladores;

import Modelos.TcConceptosArqueo;
import Modelos.TcCajas;
import Utils.Utilidades;
import Utils.ControlMensajes;
import Conexiones.ConexionJdbc;
import Modelos.TcTiposTarjetas;
import Utils.FuncionesBd;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContTcCajas {

    int idCaja;
    private String sql = "";

    public boolean imp_tc_cajas() {
        ConexionJdbc jdbc = new ConexionJdbc();
        TcCajas dtsT = new TcCajas();
        sql = "SELECT IDENTIFICADOR, ID_UNIDAD, ID_EMPRESA, NUMERO, DESCRIPCION, VR_TIPO_CAJA,ID_SITIO, "
                + "EQUIPO, USR_CRE, FEC_CRE, USR_MOD, FEC_MOD, ID_PUNTO_EMISION, VR_TIPO_IMPRESORA, "
                + "IMPR_CINTA_AUDIT, USAR_CORTE_PAPEL, USAR_REGISTRO_CTRL, VR_PUERTO_IMPR, USA_GAVETA, "
                + "PUERTO_IMPRESION, ACTIVO FROM TC_CAJAS WHERE EQUIPO = '" + Utilidades.getHostname() + "' AND ACTIVO='S'";

        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            int filas = 0;
            if (rs.next()) {
                FuncionesBd.truncate("TC_CAJAS");

                dtsT.setIdentificador(rs.getInt("IDENTIFICADOR"));
                idCaja = rs.getInt("IDENTIFICADOR");
                dtsT.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsT.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsT.setNumero(rs.getInt("NUMERO"));
                dtsT.setDescripcion(rs.getString("DESCRIPCION"));
                dtsT.setVrTipoCaja(rs.getString("VR_TIPO_CAJA"));
                dtsT.setIdSitio(rs.getInt("ID_SITIO"));
                dtsT.setEquipo(rs.getString("EQUIPO"));
                dtsT.setUserCre(rs.getString("USR_CRE"));
                dtsT.setFecCre(FuncionesBd.sysdate());
                dtsT.setUserMod(rs.getString("USR_CRE"));
                dtsT.setFecMod(FuncionesBd.sysdate());
                dtsT.setIdPuntoEmision(rs.getInt("ID_PUNTO_EMISION"));
                dtsT.setVrTipoImpresora(rs.getString("VR_TIPO_IMPRESORA"));
                dtsT.setImpCintaAudit(rs.getString("IMPR_CINTA_AUDIT"));
                dtsT.setUsarCortePapel(rs.getString("USAR_CORTE_PAPEL"));
                dtsT.setUsarRegistroControl(rs.getString("IMPR_CINTA_AUDIT"));
                dtsT.setVrPuertoImpresion(rs.getString("VR_PUERTO_IMPR"));
                dtsT.setUsarGaveta(rs.getString("USA_GAVETA"));
                dtsT.setPuertoImpresion(rs.getString("PUERTO_IMPRESION"));
                dtsT.setActivo(rs.getString("ACTIVO"));

                filas++;
                insert_tc_cajas(dtsT);
            }
            System.out.println("\tCAJAS IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("imp_tc_cajas: " + e, ContTcCajas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert_tc_cajas(TcCajas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_CAJAS(IDENTIFICADOR, ID_UNIDAD, ID_EMPRESA, NUMERO, DESCRIPCION, VR_TIPO_CAJA,ID_SITIO, "
                    + "EQUIPO, USR_CRE, FEC_CRE, USR_MOD, FEC_MOD, ID_PUNTO_EMISION, VR_TIPO_IMPRESORA, "
                    + "IMPR_CINTA_AUDIT, USAR_CORTE_PAPEL, USAR_REGISTRO_CTRL, VR_PUERTO_IMPR, USA_GAVETA, "
                    + "PUERTO_IMPRESION, ACTIVO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdUnidad());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getNumero());
            pst.setString(5, dts.getDescripcion());
            pst.setString(6, dts.getVrTipoCaja());
            pst.setInt(7, dts.getIdSitio());
            pst.setString(8, dts.getEquipo());
            pst.setString(9, dts.getUserCre());
            pst.setTimestamp(10, dts.getFecCre());
            pst.setString(11, dts.getUserMod());
            pst.setTimestamp(12, dts.getFecMod());
            pst.setInt(13, dts.getIdPuntoEmision());
            pst.setString(14, dts.getVrTipoImpresora());
            pst.setString(15, dts.getImpCintaAudit());
            pst.setString(16, dts.getUsarCortePapel());
            pst.setString(17, dts.getUsarRegistroControl());
            pst.setString(18, dts.getVrPuertoImpresion());
            pst.setString(19, dts.getUsarGaveta());
            pst.setString(20, dts.getPuertoImpresion());
            pst.setString(21, dts.getActivo());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insert_tc_cajas: " + e, ContTcCajas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    public boolean imp_puntos_emision() {
        ConexionJdbc jdbc = new ConexionJdbc();
        int filas = 0;
        TcCajas dtsT = new TcCajas();

        sql = "SELECT * FROM VTA_PUNTOS_EMISION WHERE ID_CAJA = " + idCaja + "";
        try {

            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("VTA_PUNTOS_EMISION");

            while (rs.next()) {
                dtsT.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsT.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsT.setIdSitio(rs.getInt("ID_SITIO"));
                dtsT.setCodigo(rs.getString("CODIGO"));
                dtsT.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsT.setDescripcion(rs.getString("DESCRIPCION"));
                dtsT.setUserCre(rs.getString("USR_CRE"));
                dtsT.setFecCre(rs.getTimestamp("FEC_CRE"));
                dtsT.setIdCaja(rs.getInt("ID_CAJA"));
                dtsT.setActivo(rs.getString("ACTIVO"));
                dtsT.setEmitirNc(rs.getString("EMITIR_NC"));
                dtsT.setEquipo(rs.getString("EQUIPO"));

                filas++;
                insert_puntos_emision(dtsT);
            }
            System.out.println("\tPUNTOS DE EMISION IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("imp_puntos_emision: " + e, ContTcCajas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insert_puntos_emision(TcCajas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO VTA_PUNTOS_EMISION(IDENTIFICADOR, ID_UNIDAD, ID_SITIO, CODIGO, ID_EMPRESA, "
                    + "DESCRIPCION,USR_CRE,FEC_CRE, ID_CAJA, ACTIVO, EMITIR_NC, EQUIPO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdUnidad());
            pst.setInt(3, dts.getIdSitio());
            pst.setString(4, dts.getCodigo());
            pst.setInt(5, dts.getIdEmpresa());
            pst.setString(6, dts.getDescripcion());
            pst.setString(7, dts.getUserCre());
            pst.setTimestamp(8, dts.getFecCre());
            pst.setInt(9, dts.getIdCaja());
            pst.setString(10, dts.getActivo());
            pst.setString(11, dts.getEmitirNc());
            pst.setString(12, dts.getEquipo());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insert_puntos_emision: " + e, ContTcCajas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    //==========================================================================
    public boolean impConceptosArqueo() {
        ConexionJdbc jdbc = new ConexionJdbc();
        TcConceptosArqueo dts = new TcConceptosArqueo();
        int filas = 0;
        sql = "SELECT * FROM TC_CONCEPTOS_ARQUEO";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("TC_CONCEPTOS_ARQUEO");
            while (rs.next()) {
                dts.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setVrTipoConcepto(rs.getString("VR_TIPO_CONCEPTO"));
                dts.setVrUso(rs.getString("VR_USO"));
                dts.setVrSigno(rs.getString("VR_SIGNO"));

                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setAbreviatura(rs.getString("ABREVIATURA"));
                dts.setNumero(rs.getString("NUMERO"));
                dts.setIdFormaPago(rs.getString("ID_FORMA_PAGO"));

                dts.setIdTransaccion(rs.getString("ID_TRANSACCION"));
                dts.setRequiereAut(rs.getString("REQUIERE_AUT"));
                dts.setIdTipoCobro(rs.getInt("ID_TIPO_COBRO"));
                dts.setCodigoPos(rs.getString("CODIGO_POS"));
                dts.setActivo(rs.getString("ACTIVO"));

                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));

                filas++;
                insrConceptosArqueo(dts);
            }

            System.out.println("\tCONCEPTOS DE ARQUEO IMPORTADOS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("impConceptosArqueo: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insrConceptosArqueo(TcConceptosArqueo dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_CONCEPTOS_ARQUEO(IDENTIFICADOR, DESCRIPCION, "
                    + "VR_TIPO_CONCEPTO, VR_USO, VR_SIGNO, USR_CRE, FEC_CRE, "
                    + "ABREVIATURA, NUMERO, ID_FORMA_PAGO, ID_TRANSACCION, "
                    + "REQUIERE_AUT, ID_TIPO_COBRO, CODIGO_POS, ACTIVO, "
                    + "ID_EMPRESA, ID_UNIDAD) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getVrTipoConcepto());
            pst.setString(4, dts.getVrUso());
            pst.setString(5, dts.getVrSigno());
            pst.setString(6, dts.getUsrCre());
            pst.setTimestamp(7, dts.getFecCre());
            pst.setString(8, dts.getAbreviatura());
            pst.setString(9, dts.getNumero());
            pst.setString(10, dts.getIdFormaPago());
            pst.setString(11, dts.getIdTransaccion());
            pst.setString(12, dts.getRequiereAut());
            pst.setInt(13, dts.getIdTipoCobro());
            pst.setString(14, dts.getCodigoPos());
            pst.setString(15, dts.getActivo());
            pst.setInt(16, dts.getIdEmpresa());
            pst.setInt(17, dts.getIdUnidad());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insrConceptosArqueo: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    public boolean impProcesadoras() {
        ConexionJdbc jdbc = new ConexionJdbc();
        TcTiposTarjetas dts = new TcTiposTarjetas();
        int filas = 0;
        sql = "SELECT * FROM TC_PROC_TARJETAS";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("TC_PROC_TARJETAS");
            while (rs.next()) {
                dts.setIdProcesadora(rs.getInt("IDENTIFICADOR"));
                dts.setCodigo(rs.getString("CODIGO"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setCodigoComercio(rs.getString("CODIGO_COMERCIO"));
                dts.setVrFrecuencia(rs.getString("VR_FRECUENCIA"));

                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setDireccion(rs.getString("DIRECCION"));
                dts.setTelefonos(rs.getString("TELEFONOS"));
                dts.setUsrMod(rs.getString("USR_MOD"));

                dts.setFecMod(rs.getTimestamp("FEC_MOD"));
                dts.setPorcComision(rs.getInt("PORC_COMISION"));
                dts.setPorcComisionTc(rs.getInt("PORC_COMISION_TC"));
                dts.setPorcIvaComisTc(rs.getInt("PORC_IVA_COMIS_TC"));
                dts.setPorcRetenIvaTc(rs.getInt("PORC_RETEN_IVA_TC"));

                dts.setPorcRetenRentaTc(rs.getInt("PORC_RETEN_RENTA_TC"));
                dts.setPorcComisionTd(rs.getInt("PORC_COMISION_TD"));
                dts.setPorcIvaComisTd(rs.getInt("PORC_IVA_COMIS_TD"));
                dts.setPorcRetenIvaTd(rs.getInt("PORC_RETEN_IVA_TD"));
                dts.setPorcRetenRentaTd(rs.getInt("PORC_RETEN_RENTA_TD"));

                dts.setCodigoPos(rs.getString("CODIGO_POS"));
                dts.setActivo(rs.getString("ACTIVO"));

                filas++;
                insrProcesadoras(dts);
            }

            System.out.println("\tPROCESADORAS DE TARJETAS IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("impProcesadoras: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insrProcesadoras(TcTiposTarjetas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_PROC_TARJETAS(IDENTIFICADOR, CODIGO, DESCRIPCION, "
                    + "CODIGO_COMERCIO, VR_FRECUENCIA, USR_CRE, FEC_CRE, DIRECCION, "
                    + "TELEFONOS, USR_MOD, FEC_MOD, PORC_COMISION, PORC_COMISION_TC, "
                    + "PORC_IVA_COMIS_TC, PORC_RETEN_IVA_TC, PORC_RETEN_RENTA_TC, "
                    + "PORC_COMISION_TD, PORC_IVA_COMIS_TD, PORC_RETEN_IVA_TD, "
                    + "PORC_RETEN_RENTA_TD, CODIGO_POS, ACTIVO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdProcesadora());
            pst.setString(2, dts.getCodigo());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCodigoComercio());
            pst.setString(5, dts.getVrFrecuencia());
            pst.setString(6, dts.getUsrCre());
            pst.setTimestamp(7, dts.getFecCre());
            pst.setString(8, dts.getDireccion());
            pst.setString(9, dts.getTelefonos());
            pst.setString(10, dts.getUsrMod());
            pst.setTimestamp(11, dts.getFecMod());
            pst.setInt(12, dts.getPorcComision());
            pst.setInt(13, dts.getPorcComisionTc());
            pst.setInt(14, dts.getPorcIvaComisTc());
            pst.setInt(15, dts.getPorcRetenIvaTc());
            pst.setInt(16, dts.getPorcRetenRentaTc());
            pst.setInt(17, dts.getPorcComisionTd());
            pst.setInt(18, dts.getPorcIvaComisTc());
            pst.setInt(19, dts.getPorcRetenIvaTd());
            pst.setInt(20, dts.getPorcRetenRentaTd());
            pst.setString(21, dts.getCodigoPos());
            pst.setString(22, dts.getActivo());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insrtProcesadoras: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //==========================================================================
    public boolean impTiposTarjetas() {
        ConexionJdbc jdbc = new ConexionJdbc();
        TcTiposTarjetas dts = new TcTiposTarjetas();
        int filas = 0;
        sql = "SELECT * FROM TC_TIPOS_TARJETAS";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            FuncionesBd.truncate("TC_TIPOS_TARJETAS");
            while (rs.next()) {
                dts.setIdTipoTarjeta(rs.getInt("IDENTIFICADOR"));
                dts.setIdProcesadora(rs.getInt("ID_PROCESADORA"));
                dts.setIdMArca(rs.getInt("ID_MARCA"));
                dts.setVrTipoTarjeta(rs.getString("VR_TIPO_TARJETA"));
                dts.setCodigo(rs.getString("CODIGO"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(rs.getTimestamp("FEC_CRE"));
                dts.setUsrMod(rs.getString("USR_MOD"));
                dts.setFecMod(rs.getTimestamp("FEC_MOD"));
                dts.setIdEntidad(rs.getInt("ID_ENTIDAD"));
                dts.setPorcComision(rs.getInt("PORC_COMISION"));
                dts.setPorcIvaComis(rs.getInt("PORC_IVA_COMIS"));
                dts.setPorcRetenIva(rs.getInt("PORC_RETEN_IVA"));
                dts.setPorcRetenRenta(rs.getInt("PORC_RETEN_RENTA"));
                dts.setCodigoPos(rs.getString("CODIGO_POS"));
                dts.setActivo(rs.getString("ACTIVO"));

                filas++;
                insrTiposTarjetas(dts);
            }

            System.out.println("\tTIPOS DE TARJETAS IMPORTADAS: " + filas);
            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("impProcesadoras: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insrTiposTarjetas(TcTiposTarjetas dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO TC_TIPOS_TARJETAS(IDENTIFICADOR, ID_PROCESADORA, "
                    + "ID_MARCA, VR_TIPO_TARJETA, CODIGO, DESCRIPCION, USR_CRE, "
                    + "FEC_CRE, USR_MOD, FEC_MOD, ID_ENTIDAD, PORC_COMISION, "
                    + "PORC_IVA_COMIS, PORC_RETEN_IVA, PORC_RETEN_RENTA, CODIGO_POS, "
                    + "ACTIVO) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdTipoTarjeta());
            pst.setInt(2, dts.getIdProcesadora());
            pst.setInt(3, dts.getIdMArca());
            pst.setString(4, dts.getVrTipoTarjeta());
            pst.setString(5, dts.getCodigo());
            pst.setString(6, dts.getDescripcion());
            pst.setString(7, dts.getUsrCre());
            pst.setTimestamp(8, dts.getFecCre());
            pst.setString(9, dts.getUsrMod());
            pst.setTimestamp(10, dts.getFecMod());
            pst.setInt(11, dts.getIdEntidad());
            pst.setInt(12, dts.getPorcComision());
            pst.setInt(13, dts.getPorcIvaComis());
            pst.setInt(14, dts.getPorcRetenIva());
            pst.setInt(15, dts.getPorcRetenRenta());
            pst.setString(16, dts.getCodigoPos());
            pst.setString(17, dts.getActivo());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insrTiposTarjetas: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }
    
    
     public boolean getAutorizacionSupervidor(String codigo) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            boolean respuesta = false;
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT 1 FROM PS_CAJEROS WHERE CODIGO_AUTORIZ=?");
            pst.setString(1, codigo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                respuesta = true;
            }
            return respuesta;
        } catch (SQLException e) {
            ControlMensajes.error("getAutorizacionSupervidor: " + e, ContFndMonedas.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
