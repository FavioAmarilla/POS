package Controladores;

import Modelos.PsPreciosAplicar;
import Modelos.PrUmProducto;
import Modelos.PrProductos;
import Modelos.PrCategoriasProd;
import Utils.Utilidades;
import Conexiones.ConexionJdbc;
import Utils.ControlMensajes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

public class ContPrProductos {

    private String sql = "";
    long prImp = 0;
    long umImp = 0;

    public DefaultTableModel precios(String condicion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        DefaultTableModel modelo;
        String[] titulos = {"CODIGO BARRAS", "DESCRIPCION", "PRECIO"};
        String[] registro = new String[3];
        modelo = new DefaultTableModel(null, titulos);
        DecimalFormat miles = new DecimalFormat("###,###.##");

        sql = "SELECT CODIGO_BARRAS, DESCRIPCION, PRECIO_VENTA "
                + "FROM PR_PRODUCTOS " + condicion;
        try {
            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                registro[0] = rs.getString("CODIGO_BARRAS");
                registro[1] = rs.getString("DESCRIPCION");
                registro[2] = miles.format(rs.getInt("PRECIO_VENTA"));
                modelo.addRow(registro);
            }
            pst.close();
            return modelo;
        } catch (SQLException ex) {
            ControlMensajes.error("precios: " + ex, ContPrProductos.class.getName());
            return null;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public int ultNovedad(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        int ultNovedad = 0;

        try {
            PreparedStatement pst = jdbc.local().prepareStatement("SELECT ULTIMO_ID_NOVEDAD FROM POS_APLIC_NOVEDADES WHERE NOMBRE_TABLA = '" + tabla + "'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                ultNovedad = rs.getInt(1);
            }

            pst.close();
            return ultNovedad;
        } catch (SQLException ex) {
            ControlMensajes.error("ultNovedad: " + ex, ContStkMovimStock.class.getName());
            return 0;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean novedades(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            int impDesde = ultNovedad(tabla);
            int idProducto = 0;
            int idNovedad = 0;
            String accion = "";

            PreparedStatement pst = jdbc.server().prepareStatement("SELECT COALESCE(IDENTIFICADOR, 0), COALESCE(IDENTIFICADOR_1, 0), COALESCE(TIPO_OPERACION, '') "
                    + "FROM POS_REGISTRO_NOVEDADES WHERE NOMBRE_TABLA = ? AND IDENTIFICADOR > ? ORDER BY IDENTIFICADOR ASC");
            pst.setString(1, tabla);
            pst.setInt(2, impDesde);

            ResultSet rs = pst.executeQuery();

            System.out.println("\tImportar " + tabla + " > " + String.valueOf(impDesde));

            while (rs.next()) {
                idNovedad = rs.getInt(1);
                idProducto = rs.getInt(2);
                accion = rs.getString(3);

                if (tabla.equals("PR_PRODUCTOS")) {
                    importarPrProductos(idProducto, idNovedad, accion);
                }

                if (tabla.equals("PR_UM_PRODUCTO")) {
                    importarPrUmProducto(idProducto, idNovedad, accion);
                }
            }

            if (tabla.equals("PR_PRODUCTOS")) {
                System.out.println("\tPRODUCTOS IMPORTADOS: " + prImp);
            }

            if (tabla.equals("PR_UM_PRODUCTO")) {
                System.out.println("\tPRODUCTOS UM IMPORTADOS: " + umImp);
            }

            pst.close();
            return true;
        } catch (SQLException ex) {
            ControlMensajes.error("novedades (productos): " + ex, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //OPERACIONES PARA LAS NOVEDADES A SER APLICADAS EN LA TABLA PR_PRODUCTOS
    public boolean importarPrProductos(int idProducto, int ultIdNovedad, String accion) {

        ConexionJdbc jdbc = new ConexionJdbc();
        PrProductos dts = new PrProductos();

        try {
            PreparedStatement pst = jdbc.server().prepareStatement("SELECT * FROM PR_PRODUCTOS WHERE IDENTIFICADOR = ?");
            pst.setInt(1, idProducto);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dts.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dts.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dts.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dts.setDescripcion(rs.getString("DESCRIPCION"));
                dts.setIdCategotia(rs.getInt("ID_CATEGORIA"));
                dts.setActivo(rs.getString("ACTIVO"));
                dts.setUsoInterno(rs.getString("USO_INTERNO"));
                dts.setItemInventario(rs.getString("ITEM_INVENTARIO"));
                dts.setItemVentas(rs.getString("ITEM_VENTAS"));
                dts.setItemCompras(rs.getString("ITEM_COMPRAS"));
                dts.setIdUnidadMedida(rs.getInt("ID_UNIDAD_MEDIDA"));
                dts.setIdTipoImpuesto(rs.getInt("ID_TIPO_IMPUESTO"));
                dts.setUsrCre(rs.getString("USR_CRE"));
                dts.setFecCre(Utilidades.getFecha("dd/MMM/yyyy"));
                dts.setIdDtoVentas(rs.getInt("ID_DPTO_VENTAS"));
                dts.setIdTipoGrupo(rs.getInt("ID_TIPO_GRUPO"));
                dts.setIdLinea(rs.getInt("ID_LINEA"));
                dts.setIdMarca(rs.getInt("ID_MARCA"));
                dts.setCodigo(rs.getString("CODIGO"));
                dts.setCodBarras(rs.getString("CODIGO_BARRAS"));
                dts.setCodBarrasAnt(rs.getString("CODIGO_ANTERIOR"));
                dts.setCodBarrasOrig(rs.getString("CODIGO_ORIGEN"));
                dts.setVrOrigen(rs.getString("VR_ORIGEN"));
                dts.setIdPaisOrigen(rs.getInt("ID_PAIS_ORIGEN"));
                dts.setVrCondicion(rs.getString("VR_CONDICION"));
                dts.setControlado(rs.getString("CONTROLADO"));
                dts.setCadFrio(rs.getString("CADENA_FRIO"));
                dts.setControlarVcto(rs.getString("CONTROLAR_VCTO"));
                dts.setCompraUnica(rs.getString("COMPRA_UNICA"));
                dts.setEnviarCaja(rs.getString("ENVIAR_CAJA"));
                dts.setIdCtaContCp(rs.getInt("ID_CTA_CONT_CP"));
                dts.setIdCtaContVtas(rs.getInt("ID_CTA_CONT_VTAS"));
                dts.setPrecioVenta(rs.getInt("PRECIO_VENTA"));
                dts.setCostoPromedio(rs.getDouble("COSTO_PROMEDIO"));
                dts.setCostoRemplazo(rs.getDouble("COSTO_REMPLAZO"));
                dts.setPorcRecargo(rs.getInt("PORC_RECARGO"));
                dts.setPrecioBloqueado(rs.getString("PRECIO_BLOQUEADO"));
                dts.setIdProveedor(rs.getInt("ID_PROVEEDOR"));
                dts.setArchivoImg(rs.getString("ARCHIVO_IMG"));
                dts.setIdSitioProv(rs.getInt("ID_SITIO_PROV"));
                dts.setIdClaseProd(rs.getInt("ID_CLASE_PROD"));
                dts.setSegmento1(rs.getString("SEGMENTO1"));
                dts.setSegmento2(rs.getString("SEGMENTO2"));
                dts.setSegmento3(rs.getString("SEGMENTO3"));
                dts.setSegmento4(rs.getString("SEGMENTO4"));
                dts.setSegmento5(rs.getString("SEGMENTO5"));
                dts.setIdCtaContCost(rs.getInt("ID_CTA_CONT_COST"));
                dts.setDescAbribiada(rs.getString("DESC_ABREVIADA"));
                dts.setAdmiteCodNodef(rs.getString("ADMITE_COD_NODEF"));
                dts.setCodigoBalanza(rs.getString("CODIGO_BALANZA"));
                dts.setIdCtaContProd(rs.getInt("ID_CTA_CONT_PROD"));
                dts.setMaxStkNegativo(rs.getInt("MAX_STK_NEGATIVO"));
                dts.setPrecioMayorista(rs.getInt("PRECIO_MAYORISTA"));
                dts.setPrecioDist(rs.getInt("PRECIO_DISTRIB"));
                dts.setPrecioVentaMe(rs.getInt("PRECIO_VENTA_ME"));
                dts.setStockMinimo(rs.getInt("STOCK_MINIMO"));
                dts.setStockMaximo(rs.getInt("STOCK_MAXIMO"));
                dts.setTexto(rs.getString("TEXTO"));
                dts.setRucImport(rs.getString("RUC_IMPORT"));
                dts.setCostoPromedioUs(rs.getDouble("COSTO_PROMEDIO_US"));
                dts.setCostoPromedioEx(rs.getDouble("COSTO_PROMEDIO_EX"));
                dts.setCostoRemplazoEx(rs.getDouble("COSTO_REMPLAZO_EX"));
                dts.setCostoRemplazoUs(rs.getDouble("COSTO_REMPLAZO_US"));
                dts.setIdUdmDefecto(rs.getInt("ID_UDM_DEFECTO"));
                dts.setIdTipoCodBarras(rs.getInt("ID_TIPO_COD_BARRAS"));
                dts.setNroSerieGrupo(rs.getInt("NRO_SERIE_GRUPO"));
                dts.setPrecioDiferido(rs.getString("PRECIO_DIFERIDO"));
                dts.setCodigoUnificacion(rs.getString("CODIGO_UNIFICACION"));
                dts.setAdmiteVtaImporte(rs.getString("ADMITE_VTA_X_IMPORTE"));
                dts.setIdProdEnvase(rs.getInt("ID_PROD_ENVASE"));
                dts.setIdEdmEnvase(rs.getInt("ID_UDM_ENVASE"));
                dts.setIdLaboratorio(rs.getInt("ID_LABORATORIO"));
                dts.setIdPresentacion(rs.getInt("ID_LABORATORIO"));
                dts.setCostoPromConIva(rs.getDouble("COSTO_PROMEDIO_CON_IVA"));
                dts.setCostoPromUsConIva(rs.getDouble("COSTO_PROMEDIO_US_CON_IVA"));
                dts.setCostoPromExConIva(rs.getDouble("COSTO_PROMEDIO_EX_CON_IVA"));
                dts.setCostoRemplazoConIva(rs.getDouble("COSTO_REMPLAZO_CON_IVA"));
                dts.setCostoRemplazoUsConIva(rs.getDouble("COSTO_REMPLAZO_US_CON_IVA"));
                dts.setCostoRemplazoExConIva(rs.getDouble("COSTO_REMPLAZO_EX_CON_IVA"));
                dts.setFechaUltInact(rs.getTimestamp("FECHA_ULT_INACT"));
                dts.setFechaUltReactiv(rs.getTimestamp("FECHA_ULT_REACTIV"));
                dts.setBuscarEnCaja(rs.getString("BUSCAR_EN_CAJA"));

                if (accion.equals("I")) {
                    if (insertarPrProductos(dts)) {
                        if (actualizarNovedades(ultIdNovedad, "PR_PRODUCTOS")) {
                            prImp++;
                            System.out.println("\t" + prImp + "- ULTIMO ID ACTUALIZADO (PR_PRODUCTOS - I)");
                        }
                    } else {
                        System.out.println("\tProducto no insertado");
                        return false;
                    }
                }

                if (accion.equals("U")) {
                    if (actualizarPrProductos(dts)) {
                        if (actualizarNovedades(ultIdNovedad, "PR_PRODUCTOS")) {
                            prImp++;
                            System.out.println("\t" + prImp + "- ULTIMO ID ACTUALIZADO (PR_PRODUCTOS - U)");
                        }
                    } else {
                        System.out.println("\tProducto no actualizado");
                        return false;
                    }
                }
            }

            pst.close();
            return true;

        } catch (SQLException e) {
            ControlMensajes.error("importarPrProductos: " + e, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarPrProductos(PrProductos dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO PR_PRODUCTOS(IDENTIFICADOR, ID_UNIDAD, ID_EMPRESA, DESCRIPCION, ID_CATEGORIA, ACTIVO, "
                    + "USO_INTERNO, ITEM_INVENTARIO, ITEM_VENTAS, ITEM_COMPRAS, ID_UNIDAD_MEDIDA, ID_TIPO_IMPUESTO, "
                    + "USR_CRE, FEC_CRE, ID_DPTO_VENTAS, ID_TIPO_GRUPO, ID_LINEA,ID_MARCA,CODIGO,"
                    + "CODIGO_BARRAS, CODIGO_ANTERIOR, CODIGO_ORIGEN, VR_ORIGEN, ID_PAIS_ORIGEN,"
                    + "VR_CONDICION, CONTROLADO, CADENA_FRIO, CONTROLAR_VCTO, COMPRA_UNICA, ENVIAR_CAJA,"
                    + "ID_CTA_CONT_CP, ID_CTA_CONT_VTAS, PRECIO_VENTA, COSTO_PROMEDIO, COSTO_REMPLAZO,"
                    + "PORC_RECARGO, PRECIO_BLOQUEADO, ID_PROVEEDOR, ARCHIVO_IMG, ID_SITIO_PROV, ID_CLASE_PROD,"
                    + "SEGMENTO1, SEGMENTO2, SEGMENTO3, SEGMENTO4, SEGMENTO5, ID_CTA_CONT_COST,"
                    + "DESC_ABREVIADA, ADMITE_COD_NODEF, CODIGO_BALANZA, ID_CTA_CONT_PROD,"
                    + "MAX_STK_NEGATIVO, PRECIO_MAYORISTA, PRECIO_DISTRIB, PRECIO_VENTA_ME, STOCK_MINIMO,"
                    + "STOCK_MAXIMO, TEXTO, RUC_IMPORT, COSTO_PROMEDIO_US, COSTO_PROMEDIO_EX,"
                    + "COSTO_REMPLAZO_EX, COSTO_REMPLAZO_US, ID_UDM_DEFECTO, ID_TIPO_COD_BARRAS,"
                    + "NRO_SERIE_GRUPO, PRECIO_DIFERIDO, CODIGO_UNIFICACION, ADMITE_VTA_X_IMPORTE, ID_PROD_ENVASE,"
                    + "ID_UDM_ENVASE,ID_LABORATORIO, ID_PRESENTACION, COSTO_PROMEDIO_CON_IVA,COSTO_PROMEDIO_US_CON_IVA,"
                    + "COSTO_PROMEDIO_EX_CON_IVA, COSTO_REMPLAZO_CON_IVA, COSTO_REMPLAZO_US_CON_IVA, COSTO_REMPLAZO_EX_CON_IVA,"
                    + "FECHA_ULT_INACT,  FECHA_ULT_REACTIV, BUSCAR_EN_CAJA) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdentificador());
            pst.setInt(2, dts.getIdUnidad());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setString(4, dts.getDescripcion());
            pst.setInt(5, dts.getIdCategotia());
            pst.setString(6, dts.getActivo());
            pst.setString(7, dts.getUsoInterno());
            pst.setString(8, dts.getItemInventario());
            pst.setString(9, dts.getItemVentas());
            pst.setString(10, dts.getItemCompras());
            pst.setInt(11, dts.getIdUnidadMedida());
            pst.setInt(12, dts.getIdTipoImpuesto());
            pst.setString(13, dts.getUsrCre());
            pst.setString(14, dts.getFecCre());
            pst.setInt(15, dts.getIdDtoVentas());
            pst.setInt(16, dts.getIdTipoGrupo());
            pst.setInt(17, dts.getIdLinea());
            pst.setInt(18, dts.getIdMarca());
            pst.setString(19, dts.getCodigo());
            pst.setString(20, dts.getCodBarras());
            pst.setString(21, dts.getCodBarrasAnt());
            pst.setString(22, dts.getCodBarrasOrig());
            pst.setString(23, dts.getVrOrigen());
            pst.setInt(24, dts.getIdPaisOrigen());
            pst.setString(25, dts.getVrCondicion());
            pst.setString(26, dts.getControlado());
            pst.setString(27, dts.getCadFrio());
            pst.setString(28, dts.getControlarVcto());
            pst.setString(29, dts.getCompraUnica());
            pst.setString(30, dts.getEnviarCaja());
            pst.setInt(31, dts.getIdCtaContCp());
            pst.setInt(32, dts.getIdCtaContVtas());
            pst.setDouble(33, dts.getPrecioVenta());
            pst.setDouble(34, dts.getCostoPromedio());
            pst.setDouble(35, dts.getCostoRemplazo());
            pst.setDouble(36, dts.getPorcRecargo());
            pst.setString(37, dts.getPrecioBloqueado());
            pst.setInt(38, dts.getIdProveedor());
            pst.setString(39, dts.getArchivoImg());
            pst.setInt(40, dts.getIdSitioProv());
            pst.setInt(41, dts.getIdClaseProd());
            pst.setString(42, dts.getSegmento1());
            pst.setString(43, dts.getSegmento2());
            pst.setString(44, dts.getSegmento3());
            pst.setString(45, dts.getSegmento4());
            pst.setString(46, dts.getSegmento5());
            pst.setInt(47, dts.getIdCtaContCost());
            pst.setString(48, dts.getDescAbribiada());
            pst.setString(49, dts.getAdmiteCodNodef());
            pst.setString(50, dts.getCodigoBalanza());
            pst.setInt(51, dts.getIdCtaContProd());
            pst.setInt(52, dts.getMaxStkNegativo());
            pst.setInt(53, dts.getPrecioMayorista());
            pst.setInt(54, dts.getPrecioDist());
            pst.setInt(55, dts.getPrecioVentaMe());
            pst.setInt(56, dts.getStockMinimo());
            pst.setInt(57, dts.getStockMaximo());
            pst.setString(58, dts.getTexto());
            pst.setString(59, dts.getRucImport());
            pst.setDouble(60, dts.getCostoPromedioUs());
            pst.setDouble(61, dts.getCostoPromedioEx());
            pst.setDouble(62, dts.getCostoRemplazoEx());
            pst.setDouble(63, dts.getCostoRemplazoUs());
            pst.setInt(64, dts.getIdUdmDefecto());
            pst.setInt(65, dts.getIdTipoCodBarras());
            pst.setInt(66, dts.getNroSerieGrupo());
            pst.setString(67, dts.getPrecioDiferido());
            pst.setString(68, dts.getCodigoUnificacion());
            pst.setString(69, dts.getAdmiteVtaImporte());
            pst.setInt(70, dts.getIdProdEnvase());
            pst.setInt(71, dts.getIdEdmEnvase());
            pst.setInt(72, dts.getIdLaboratorio());
            pst.setInt(73, dts.getIdPresentacion());
            pst.setDouble(74, dts.getCostoPromConIva());
            pst.setDouble(75, dts.getCostoPromUsConIva());
            pst.setDouble(76, dts.getCostoPromExConIva());
            pst.setDouble(77, dts.getCostoRemplazoConIva());
            pst.setDouble(78, dts.getCostoRemplazoUsConIva());
            pst.setDouble(79, dts.getCostoRemplazoExConIva());
            pst.setTimestamp(80, dts.getFechaUltInact());
            pst.setTimestamp(81, dts.getFechaUltReactiv());
            pst.setString(82, dts.getBuscarEnCaja());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarPrProductos: " + e, "FUNC_importarPrProductos");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarPrProductos(PrProductos dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE PR_PRODUCTOS SET \n"
                    + "ID_EMPRESA=?, ID_UNIDAD=?, DESCRIPCION=?, ID_CATEGORIA=?, \n"
                    + "ACTIVO=?, USO_INTERNO=?, ITEM_INVENTARIO=?, ITEM_VENTAS=?, ITEM_COMPRAS=?,\n"
                    + "ID_UNIDAD_MEDIDA=?, ID_TIPO_IMPUESTO=?, ID_DPTO_VENTAS=?, ID_TIPO_GRUPO=?, ID_LINEA=?, \n"
                    + "ID_MARCA=?, CODIGO=?, CODIGO_BARRAS=?, CODIGO_ANTERIOR=?, CODIGO_ORIGEN=?,\n"
                    + "VR_ORIGEN=?, ID_PAIS_ORIGEN=?, VR_CONDICION=?, CONTROLADO=?, CADENA_FRIO=?, \n"
                    + "CONTROLAR_VCTO=?, COMPRA_UNICA=?, ENVIAR_CAJA=?, USR_MOD=?, FEC_MOD=?,\n"
                    + "ID_CTA_CONT_CP=?, ID_CTA_CONT_VTAS=?,\n"
                    + "PORC_RECARGO=?, PRECIO_BLOQUEADO=?, ID_PROVEEDOR=?, ARCHIVO_IMG=?, ID_SITIO_PROV=?, \n"
                    + "ID_CLASE_PROD=?, SEGMENTO1=?, SEGMENTO2=?, SEGMENTO3=?, SEGMENTO4=?, \n"
                    + "SEGMENTO5=?, ID_CTA_CONT_COST=?, DESC_ABREVIADA=?, ADMITE_COD_NODEF=?, CODIGO_BALANZA=?, \n"
                    + "ID_CTA_CONT_PROD=?, MAX_STK_NEGATIVO=?, PRECIO_MAYORISTA=?, PRECIO_DISTRIB=?, PRECIO_VENTA_ME=?,\n"
                    + "STOCK_MINIMO=?, STOCK_MAXIMO=?, TEXTO=?, RUC_IMPORT=?, COSTO_PROMEDIO_US=?, \n"
                    + "COSTO_PROMEDIO_EX=?, COSTO_REMPLAZO_EX=?, COSTO_REMPLAZO_US=?, ID_UDM_DEFECTO=?, ID_TIPO_COD_BARRAS=?,"
                    + "NRO_SERIE_GRUPO=?, PRECIO_DIFERIDO=?, CODIGO_UNIFICACION=?, ADMITE_VTA_X_IMPORTE=?, ID_PROD_ENVASE=?, "
                    + "BUSCAR_EN_CAJA=?, COSTO_PROMEDIO_CON_IVA=?, COSTO_PROMEDIO_US_CON_IVA=?, COSTO_PROMEDIO_EX_CON_IVA=?,"
                    + "COSTO_PROMEDIO=?, COSTO_REMPLAZO=? WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            pst.setInt(1, dts.getIdEmpresa());
            pst.setInt(2, dts.getIdUnidad());
            pst.setString(3, dts.getDescripcion());
            pst.setInt(4, dts.getIdCategotia());
            pst.setString(5, dts.getActivo());
            pst.setString(6, dts.getUsoInterno());
            pst.setString(7, dts.getItemInventario());
            pst.setString(8, dts.getItemVentas());
            pst.setString(9, dts.getItemCompras());
            pst.setInt(10, dts.getIdUnidadMedida());
            pst.setInt(11, dts.getIdTipoImpuesto());
            pst.setInt(12, dts.getIdDtoVentas());
            pst.setInt(13, dts.getIdTipoGrupo());
            pst.setInt(14, dts.getIdLinea());
            pst.setInt(15, dts.getIdMarca());
            pst.setString(16, dts.getCodigo());
            pst.setString(17, dts.getCodBarras());
            pst.setString(18, dts.getCodBarrasAnt());
            pst.setString(19, dts.getCodBarrasOrig());
            pst.setString(20, dts.getVrOrigen());
            pst.setInt(21, dts.getIdPaisOrigen());
            pst.setString(22, dts.getVrCondicion());
            pst.setString(23, dts.getControlado());
            pst.setString(24, dts.getCadFrio());
            pst.setString(25, dts.getControlarVcto());
            pst.setString(26, dts.getCompraUnica());
            pst.setString(27, dts.getEnviarCaja());
            pst.setString(28, dts.getUsrMod());
            pst.setDate(29, dts.getFechaModificacion());
            pst.setInt(30, dts.getIdCtaContCp());
            pst.setInt(31, dts.getIdCtaContVtas());
            pst.setDouble(32, dts.getPorcRecargo());
            pst.setString(33, dts.getPrecioBloqueado());
            pst.setInt(34, dts.getIdProveedor());
            pst.setString(35, dts.getArchivoImg());
            pst.setInt(36, dts.getIdSitioProv());
            pst.setInt(37, dts.getIdClaseProd());
            pst.setString(38, dts.getSegmento1());
            pst.setString(39, dts.getSegmento2());
            pst.setString(40, dts.getSegmento3());
            pst.setString(41, dts.getSegmento4());
            pst.setString(42, dts.getSegmento5());
            pst.setInt(43, dts.getIdCtaContCost());
            pst.setString(44, dts.getDescAbribiada());
            pst.setString(45, dts.getAdmiteCodNodef());
            pst.setString(46, dts.getCodigoBalanza());
            pst.setInt(47, dts.getIdCtaContProd());
            pst.setInt(48, dts.getMaxStkNegativo());
            pst.setInt(49, dts.getPrecioMayorista());
            pst.setInt(50, dts.getPrecioDist());
            pst.setInt(51, dts.getPrecioVentaMe());
            pst.setInt(52, dts.getStockMinimo());
            pst.setInt(53, dts.getStockMaximo());
            pst.setString(54, dts.getTexto());
            pst.setString(55, dts.getRucImport());
            pst.setDouble(56, dts.getCostoPromedioUs());
            pst.setDouble(57, dts.getCostoPromedioEx());
            pst.setDouble(58, dts.getCostoRemplazoEx());
            pst.setDouble(59, dts.getCostoRemplazoUs());
            pst.setInt(60, dts.getIdUdmDefecto());
            pst.setInt(61, dts.getIdTipoCodBarras());
            pst.setInt(62, dts.getNroSerieGrupo());
            pst.setString(63, dts.getPrecioDiferido());
            pst.setString(64, dts.getCodigoUnificacion());
            pst.setString(65, dts.getAdmiteVtaImporte());
            pst.setInt(66, dts.getIdProdEnvase());
            pst.setString(67, dts.getBuscarEnCaja());
            pst.setDouble(68, dts.getCostoPromConIva());
            pst.setDouble(69, dts.getCostoPromUsConIva());
            pst.setDouble(70, dts.getCostoPromExConIva());
            pst.setDouble(71, dts.getCostoPromedio());
            pst.setDouble(72, dts.getCostoRemplazo());
            pst.setInt(73, dts.getIdentificador());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("update_dts_productos: " + e, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //OPERACIONES PARA LAS NOVEDADES A SER APLICADAS EN LA TABLA PR_UM_PRODUCTO
    public boolean importarPrUmProducto(int idProducto, int ultIdNovedad, String accion) {
        ConexionJdbc jdbc = new ConexionJdbc();
        PrUmProducto dtsU = new PrUmProducto();
        sql = "SELECT * FROM PR_UM_PRODUCTO WHERE ID_PRODUCTO = ?";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, idProducto);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                dtsU.setIdProducto(rs.getInt("ID_PRODUCTO"));
                dtsU.setIdUnidadMedida(rs.getInt("ID_UNIDAD_MEDIDA"));
                dtsU.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                dtsU.setIdUnidad(rs.getInt("ID_UNIDAD"));
                dtsU.setActivo(rs.getString("ACTIVO"));
                dtsU.setEsUmPrincipal(rs.getString("ES_UM_PRINCIPAL"));
                dtsU.setUsrCre(rs.getString("USR_CRE"));
                dtsU.setFecCre(Utilidades.getFecha("dd/MMM/yyyy"));
                dtsU.setIdPresentacion(rs.getInt("ID_PRESENTACION"));
                dtsU.setCostoPromedio(rs.getDouble("COSTO_PROMEDIO"));
                dtsU.setCostoRemplazo(rs.getDouble("COSTO_REMPLAZO"));
                dtsU.setPrecioVenta(rs.getInt("PRECIO_VENTA"));
                dtsU.setIdUdmConten(rs.getInt("ID_UDM_CONTEN"));
                dtsU.setContenido(rs.getInt("CONTENIDO"));
                dtsU.setPorcRecargo(rs.getInt("PORC_RECARGO"));
                dtsU.setPrecioBloqueado(rs.getString("PRECIO_BLOQUEADO"));
                dtsU.setPrecioVentaMe(rs.getInt("PRECIO_VENTA_ME"));
                dtsU.setStockMinimo(rs.getInt("STOCK_MINIMO"));
                dtsU.setStockMaximo(rs.getInt("STOCK_MAXIMO"));
                dtsU.setMaxStkNegativo(rs.getInt("MAX_STK_NEGATIVO"));
                dtsU.setPrecioMayorista(rs.getInt("PRECIO_MAYORISTA"));
                dtsU.setPrecioDistrib(rs.getInt("PRECIO_DISTRIB"));
                dtsU.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsU.setCostoPromedioUs(rs.getDouble("COSTO_PROMEDIO_US"));
                dtsU.setCostoPromedioEx(rs.getDouble("COSTO_PROMEDIO_EX"));
                dtsU.setCostoRemplazoUs(rs.getDouble("COSTO_REMPLAZO_US"));
                dtsU.setCostoRemplazoEx(rs.getDouble("COSTO_REMPLAZO_EX"));
                dtsU.setFechaDesde(rs.getString("FECHA_DESDE"));
                dtsU.setFechaHasta(rs.getString("FECHA_HASTA"));
                dtsU.setCostoPromedioConIva(rs.getDouble("COSTO_PROMEDIO_CON_IVA"));
                dtsU.setCostoPromedioUsConIva(rs.getDouble("COSTO_PROMEDIO_US_CON_IVA"));
                dtsU.setCostoPromedioExConIva(rs.getDouble("COSTO_PROMEDIO_EX_CON_IVA"));
                dtsU.setCostoRemplazoConIva(rs.getDouble("COSTO_REMPLAZO_CON_IVA"));
                dtsU.setCostoRemplazoUsConIva(rs.getDouble("COSTO_REMPLAZO_US_CON_IVA"));
                dtsU.setCostoRemplazoExConIva(rs.getDouble("COSTO_REMPLAZO_EX_CON_IVA"));
                dtsU.setCostoPromedioInicial(rs.getDouble("COSTO_PROMEDIO_INICIAL"));
                dtsU.setCostoRemplazoInicial(rs.getDouble("COSTO_REMPLAZO_INICIAL"));

                if (accion.equals("I")) {
                    if (insertarPrUmProducto(dtsU)) {
                        if (actualizarNovedades(ultIdNovedad, "PR_UM_PRODUCTO")) {
                            umImp++;
                            System.out.println("\t" + umImp + "- ULTIMO ID ACTUALIZADO (PR_UM_PRODUCTO - I)");
                        }
                    } else {
                        System.out.println("\tProducto Um no insertado");
                        return false;
                    }
                }

                if (accion.equals("U")) {
                    if (actualizarPrUmProducto(dtsU)) {
                        if (actualizarNovedades(ultIdNovedad, "PR_UM_PRODUCTO")) {
                            umImp++;
                            System.out.println("\t" + umImp + "- ULTIMO ID ACTUALIZADO (PR_UM_PRODUCTO - U)");
                        }
                    } else {
                        System.out.println("\tProducto Um no actualizado");
                        return false;
                    }
                }
            }

            pst.close();
            return true;
        } catch (SQLException e) {
            ControlMensajes.error("importarPrUmProducto: " + e, "FUNC_IMP_PRODUCTOS");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarPrUmProducto(PrUmProducto dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO PR_UM_PRODUCTO(ID_PRODUCTO, ID_UNIDAD_MEDIDA, ID_EMPRESA, ID_UNIDAD, ACTIVO, ES_UM_PRINCIPAL,"
                    + "USR_CRE, FEC_CRE, ID_PRESENTACION, COSTO_PROMEDIO, COSTO_REMPLAZO,"
                    + "PRECIO_VENTA, ID_UDM_CONTEN, CONTENIDO, PORC_RECARGO, PRECIO_BLOQUEADO, "
                    + "PRECIO_VENTA_ME, STOCK_MINIMO, STOCK_MAXIMO, MAX_STK_NEGATIVO, PRECIO_MAYORISTA, "
                    + "PRECIO_DISTRIB, IDENTIFICADOR,COSTO_PROMEDIO_US, COSTO_PROMEDIO_EX, "
                    + "COSTO_REMPLAZO_US, COSTO_REMPLAZO_EX, FECHA_DESDE, FECHA_HASTA,"
                    + "COSTO_PROMEDIO_CON_IVA, COSTO_PROMEDIO_US_CON_IVA, COSTO_PROMEDIO_EX_CON_IVA,"
                    + "COSTO_REMPLAZO_CON_IVA,COSTO_REMPLAZO_US_CON_IVA, COSTO_REMPLAZO_EX_CON_IVA, "
                    + "COSTO_PROMEDIO_INICIAL, COSTO_REMPLAZO_INICIAL) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdProducto());
            pst.setInt(2, dts.getIdUnidadMedida());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getIdUnidad());
            pst.setString(5, dts.getActivo());
            pst.setString(6, dts.getEsUmPrincipal());
            pst.setString(7, dts.getUsrCre());
            pst.setString(8, dts.getFecCre());
            pst.setInt(9, dts.getIdPresentacion());
            pst.setDouble(10, dts.getCostoPromedio());
            pst.setDouble(11, dts.getCostoRemplazo());
            pst.setDouble(12, dts.getPrecioVenta());
            pst.setInt(13, dts.getIdUdmConten());
            pst.setInt(14, dts.getContenido());
            pst.setInt(15, dts.getPorcRecargo());
            pst.setString(16, dts.getPrecioBloqueado());
            pst.setDouble(17, dts.getPrecioVentaMe());
            pst.setDouble(18, dts.getStockMinimo());
            pst.setDouble(19, dts.getStockMaximo());
            pst.setDouble(20, dts.getMaxStkNegativo());
            pst.setDouble(21, dts.getPrecioMayorista());
            pst.setDouble(22, dts.getPrecioDistrib());
            pst.setInt(23, dts.getIdentificador());
            pst.setDouble(24, dts.getCostoPromedioUs());
            pst.setDouble(25, dts.getCostoPromedioEx());
            pst.setDouble(26, dts.getCostoRemplazoUs());
            pst.setDouble(27, dts.getCostoRemplazoEx());
            pst.setString(28, dts.getFechaDesde());
            pst.setString(29, dts.getFechaHasta());
            pst.setDouble(30, dts.getCostoPromedioConIva());
            pst.setDouble(31, dts.getCostoPromedioUsConIva());
            pst.setDouble(32, dts.getCostoPromedioExConIva());
            pst.setDouble(33, dts.getCostoRemplazoConIva());
            pst.setDouble(34, dts.getCostoRemplazoUsConIva());
            pst.setDouble(35, dts.getCostoRemplazoExConIva());
            pst.setDouble(36, dts.getCostoPromedioInicial());
            pst.setDouble(37, dts.getCostoRemplazoInicial());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarPrUmProducto: " + e, "FUNC_IMP_PRODUCTOS");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarPrUmProducto(PrUmProducto dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE PR_UM_PRODUCTO SET ID_PRODUCTO= ?, ID_UNIDAD_MEDIDA= ?, ID_EMPRESA= ?, "
                    + "ID_UNIDAD= ?, ACTIVO= ?, ES_UM_PRINCIPAL= ?, USR_CRE= ?, FEC_CRE= ?, "
                    + "ID_PRESENTACION= ?, COSTO_PROMEDIO= ?, COSTO_REMPLAZO= ?, "
                    + "ID_UDM_CONTEN= ?, CONTENIDO= ?, PORC_RECARGO= ?, PRECIO_BLOQUEADO= ?, PRECIO_VENTA_ME= ?,"
                    + "STOCK_MINIMO= ?, STOCK_MAXIMO= ?, MAX_STK_NEGATIVO= ?, PRECIO_MAYORISTA= ?, PRECIO_DISTRIB= ?, "
                    + "IDENTIFICADOR= ?,COSTO_PROMEDIO_US= ?, COSTO_PROMEDIO_EX= ?, COSTO_REMPLAZO_US= ?, "
                    + "COSTO_REMPLAZO_EX= ?, FECHA_DESDE= ?, FECHA_HASTA= ?, COSTO_PROMEDIO_CON_IVA= ?, "
                    + "COSTO_PROMEDIO_US_CON_IVA= ?, COSTO_PROMEDIO_EX_CON_IVA= ?, COSTO_REMPLAZO_CON_IVA= ?, "
                    + "COSTO_REMPLAZO_US_CON_IVA= ?, COSTO_REMPLAZO_EX_CON_IVA= ?, COSTO_PROMEDIO_INICIAL= ?, "
                    + "COSTO_REMPLAZO_INICIAL= ? WHERE ID_PRODUCTO = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdProducto());
            pst.setInt(2, dts.getIdUnidadMedida());
            pst.setInt(3, dts.getIdEmpresa());
            pst.setInt(4, dts.getIdUnidad());
            pst.setString(5, dts.getActivo());
            pst.setString(6, dts.getEsUmPrincipal());
            pst.setString(7, dts.getUsrCre());
            pst.setString(8, dts.getFecCre());
            pst.setInt(9, dts.getIdPresentacion());
            pst.setDouble(10, dts.getCostoPromedio());
            pst.setDouble(11, dts.getCostoRemplazo());
            pst.setInt(12, dts.getIdUdmConten());
            pst.setInt(13, dts.getContenido());
            pst.setInt(14, dts.getPorcRecargo());
            pst.setString(15, dts.getPrecioBloqueado());
            pst.setDouble(16, dts.getPrecioVentaMe());
            pst.setDouble(17, dts.getStockMinimo());
            pst.setDouble(18, dts.getStockMaximo());
            pst.setDouble(19, dts.getMaxStkNegativo());
            pst.setDouble(20, dts.getPrecioMayorista());
            pst.setDouble(21, dts.getPrecioDistrib());
            pst.setInt(22, dts.getIdentificador());
            pst.setDouble(23, dts.getCostoPromedioUs());
            pst.setDouble(24, dts.getCostoPromedioEx());
            pst.setDouble(25, dts.getCostoRemplazoUs());
            pst.setDouble(26, dts.getCostoRemplazoEx());
            pst.setString(27, dts.getFechaDesde());
            pst.setString(28, dts.getFechaHasta());
            pst.setDouble(29, dts.getCostoPromedioConIva());
            pst.setDouble(30, dts.getCostoPromedioUsConIva());
            pst.setDouble(31, dts.getCostoPromedioExConIva());
            pst.setDouble(32, dts.getCostoRemplazoConIva());
            pst.setDouble(33, dts.getCostoRemplazoUsConIva());
            pst.setDouble(34, dts.getCostoRemplazoExConIva());
            pst.setDouble(35, dts.getCostoPromedioInicial());
            pst.setDouble(36, dts.getCostoRemplazoInicial());
            pst.setInt(37, dts.getIdProducto());

            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("actualizarPrUmProducto: " + e, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean actualizarNovedades(int numero, String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE POS_APLIC_NOVEDADES SET ULTIMO_ID_NOVEDAD = ? WHERE NOMBRE_TABLA = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, numero);
            pst.setString(2, tabla);
            int x = pst.executeUpdate();

            pst.close();
            return x != 0;
        } catch (SQLException ex) {
            ControlMensajes.error("actualizarNovedades: " + ex, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //OPERACIONES PARA LAS NOVEDADES A SER APLICADAS EN LA TABLA VTA_PRECIOS_A_APLICAR
    public boolean importarPreciosAplicar() {
        ConexionJdbc jdbc = new ConexionJdbc();
        PsPreciosAplicar dtsPrecios = new PsPreciosAplicar();
        sql = "SELECT * FROM PS_PRECIOS_A_APLICAR WHERE ID_CAJA= ? AND TRUNC(FECHA_APLICACION) <= TO_DATE(?, 'dd/MM/yyyy hh24:mi:ss') ORDER BY IDENTIFICADOR ASC";
        int idPrecioAplicar = 0;
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, ContParamAplicacion.CAJA_IDENTIFICADOR);
            pst.setString(2, Utilidades.getFecha("dd/MM/yyyy hh:mm:ss"));
            ResultSet rs = pst.executeQuery();

            int filas = 0;

            while (rs.next()) {
                idPrecioAplicar = rs.getInt("IDENTIFICADOR");
                dtsPrecios.setPrecioNuevo(rs.getInt("PRECIO_NUEVO"));
                dtsPrecios.setIdProducto(rs.getInt("ID_PRODUCTO"));
                dtsPrecios.setUsrMod(ContFndUsuarios.USR_NOMBRE);
                dtsPrecios.setFecMod(Utilidades.getFecha("dd/MMM/yyyy"));
                if (AplicarPrecioNuevoPr(dtsPrecios) && AplicarPrecioNuevoPrUm(dtsPrecios)) {
                    if (eliminarPrecioAplicado(idPrecioAplicar)) {
                        filas++;
                        System.out.println("\t" + filas + "- PRECIO ACTUALIZADO: ");
                    }
                }
            }
            System.out.println("\tPRECIOS ACTUALIZADOS: " + filas);
            pst.close();

            return true;
        } catch (SQLException e) {
            ControlMensajes.error("importarPreciosAplicar: " + e, "FUNC_importarPreciosAplicar");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean AplicarPrecioNuevoPr(PsPreciosAplicar dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE PR_PRODUCTOS SET PRECIO_VENTA=?, USR_MOD=?, FEC_MOD=? WHERE IDENTIFICADOR = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getPrecioNuevo());
            pst.setString(2, dts.getUsrMod());
            pst.setString(3, dts.getFecMod());
            pst.setInt(4, dts.getIdProducto());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("AplicarPrecioNuevoPr: " + e, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean AplicarPrecioNuevoPrUm(PsPreciosAplicar dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "UPDATE PR_UM_PRODUCTO SET PRECIO_VENTA = ?, USR_MOD=?, FEC_MOD=? WHERE ID_PRODUCTO = ?";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getPrecioNuevo());
            pst.setString(2, dts.getUsrMod());
            pst.setString(3, dts.getFecMod());
            pst.setInt(4, dts.getIdProducto());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("AplicarPrecioNuevoPrUm: " + e, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //Metodo que elimina en el server los precios ya aplicados en terminales de pos
    public boolean eliminarPrecioAplicado(int id) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "DELETE FROM PS_PRECIOS_A_APLICAR WHERE IDENTIFICADOR=?";
            PreparedStatement pst = jdbc.server().prepareStatement(sql);
            pst.setInt(1, id);

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("eliminarPrecioAplicado: " + e, "FUNC. IMP-PRECIOS-APLICAR");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    //Importacion de las categorias de los productos de la tabla PR_CATEGORIAS_PROD
    public boolean importarCategoriasProd() {
        ConexionJdbc jdbc = new ConexionJdbc();
        PrCategoriasProd dtsCat = new PrCategoriasProd();
        sql = "SELECT * FROM PR_CATEGORIAS_PROD";
        try {
            PreparedStatement pst = jdbc.server().prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            int filas = 0;

            vaciar_tabla("PR_CATEGORIAS_PROD");

            while (rs.next()) {
                dtsCat.setIdentificador(rs.getInt("IDENTIFICADOR"));
                dtsCat.setDescripcion(rs.getString("DESCRIPCION"));
                dtsCat.setUsrCre(rs.getString("USR_CRE"));
                dtsCat.setUsrMod(rs.getString("USR_MOD"));
                dtsCat.setAbreviatura(rs.getString("ABREVIATURA"));
                dtsCat.setFecCre(rs.getTimestamp("FEC_CRE"));
                dtsCat.setFecMod(rs.getTimestamp("FEC_MOD"));
                dtsCat.setCodigoReporte(rs.getString("CODIGO_REPORTE"));
                dtsCat.setVrFormaAdmin(rs.getString("VR_FORMA_ADMIN"));
                dtsCat.setCalcDigVerif(rs.getString("CALC_DIG_VERIF"));

                if (insertarCategoriasProd(dtsCat)) {
                    filas++;
                }
            }
            System.out.println("\tCATEGORIAS DE PROD. IMPORTADAS: " + filas);
            pst.close();

            return true;
        } catch (SQLException e) {
            ControlMensajes.error("importarCategoriasProd: " + e, "FUNC_importarPreciosAplicar");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean insertarCategoriasProd(PrCategoriasProd dts) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "INSERT INTO PR_CATEGORIAS_PROD (IDENTIFICADOR, DESCRIPCION, USR_CRE, USR_MOD, ABREVIATURA, "
                    + "FEC_CRE, FEC_MOD, CODIGO_REPORTE, VR_FORMA_ADMIN, CALC_DIG_VERIF)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = jdbc.local().prepareStatement(sql);
            pst.setInt(1, dts.getIdentificador());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getUsrCre());
            pst.setString(4, dts.getUsrMod());
            pst.setString(5, dts.getAbreviatura());
            pst.setTimestamp(6, dts.getFecCre());
            pst.setTimestamp(7, dts.getFecMod());
            pst.setString(8, dts.getCodigoReporte());
            pst.setString(9, dts.getVrFormaAdmin());
            pst.setString(10, dts.getCalcDigVerif());

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("insertarCategoriasProd: " + e, ContPrProductos.class.getName());
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

    public boolean vaciar_tabla(String tabla) {
        ConexionJdbc jdbc = new ConexionJdbc();
        try {
            sql = "DELETE FROM " + tabla;
            PreparedStatement pst = jdbc.local().prepareStatement(sql);

            int x = pst.executeUpdate();
            pst.close();
            return x != 0;
        } catch (SQLException e) {
            ControlMensajes.error("vaciar_tabla: " + e, "FUNC. IMP-PRECIOS-APLICAR");
            return false;
        } finally {
            jdbc.cerrarConexion();
        }
    }

}
