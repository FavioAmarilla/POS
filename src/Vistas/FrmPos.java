package Vistas;

import Atxy2k.CustomTextField.RestrictedTextField;
import Controladores.ContParamAplicacion;
import Utils.FormatosTicket;
import Utils.RenderTablaPos;
import Utils.ControlMensajes;
import Utils.Utilidades;
import Conexiones.ConexionJdbc;
import Conexiones.ConexionRs;
import Controladores.ContTcControlCaja;
import Modelos.VtaComprobantes;
import Modelos.VtaItemsComprob;
import Controladores.ContExportarTransacciones;
import Controladores.ContFndUsuarios;
import Controladores.ContStkMovimStock;
import Controladores.ContVtaComprobantes;
import Controladores.ContPrProductos;
import Controladores.ContStkParametros;
import Controladores.ContVtaControlComprob;
import Modelos.StkItemsMvStock;
import Modelos.StkParametros;
import Pos.POS;
import Utils.ControlImpresora;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public final class FrmPos extends javax.swing.JFrame implements Runnable {

    String hora, minutos, segundos, ampm;
    Thread hiloReloj;

    ConexionJdbc jdbc = new ConexionJdbc();
    ConexionRs cnRs = new ConexionRs();

    ContExportarTransacciones contExportarRef = new ContExportarTransacciones();
    ContVtaComprobantes contComprobante = new ContVtaComprobantes();
    ContStkMovimStock contMovimStock = new ContStkMovimStock();
    ContTcControlCaja contControlCaja = new ContTcControlCaja();
    ContVtaControlComprob contControlComprob = new ContVtaControlComprob();
    ContParamAplicacion contParamAplicacion = new ContParamAplicacion();
    ContPrProductos contProducto = new ContPrProductos();
    FormatosTicket ticket = new FormatosTicket();
    ContStkParametros contStkParametros = new ContStkParametros();

    VtaComprobantes dtsCp = new VtaComprobantes();
    VtaItemsComprob dtsVi = new VtaItemsComprob();
    StkParametros stkParametros = new StkParametros();

    static ControlImpresora impresora = new ControlImpresora();
    RestrictedTextField restricted;
    public static DefaultTableModel tabla = new DefaultTableModel();
    DecimalFormat miles = new DecimalFormat("###,###.##");
    SimpleDateFormat formato;

    //VARIABLES PARA EL CODIGO DE BARRAS
    public static int codTipoUnid = 0;
    public static String codigoPr = "";
    public static double codCantidad = 0;

    //VARIABLES PARA VTA_ITEMS_COMPROB
    public static long idItemVta = 0;
    public static int idProducto = 0;
    public static String codBarras = "";
    public static String descripcion = "";
    public static int idUndMedida = 0;
    public static double costoUnit = 0;
    public static double precioUnit = 0;
    public static double precioUnitNeto = 0;
    public static double impuestoUnitario = 0;
    public static double importeExento = 0;
    public static double importeGravado = 0;
    public static double importeImpuesto = 0;
    public static int idTipoImpuesto = 0;
    public static int porcImpuesto = 0;
    public static int idProveedor = 0;
    public static int idSitioProv = 0;
    public static int importeItem = 0;

    //VARIABLES DEL COMPROBANTE
    public static long identificador = 0;
    public static String numeroComprobante = "";
    public static long totalMonto;
    public static long totalPagar;
    public static int totalItems;
    public static boolean facturando = false;
    public static int nroTicket;
    public static double montoGravado5;
    public static double montoGravado10;
    public static double montoImpuesto;
    public static double montoImpuesto5;
    public static double montoImpuesto10;
    public static double montoExento;

    //VARIABLES UTILES 
    String[] imagen = new String[3];
    static boolean menu = false;
    static int indiceTabla = 0;
    public static boolean devolucion;

    public FrmPos() {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);

        inicio();

        restricted = new RestrictedTextField(txtCodigoBarras);
        restricted.setOnlyNums(true);
        restricted.setOnlyText(false);
        restricted.setLimit(13);

        restricted = new RestrictedTextField(txtCantidad);
        restricted.setOnlyNums(true);
        restricted.setOnlyText(false);

        panelAcciones.setVisible(menu);

        lblDescripcionProd.setText("");
        lblPrecioProd.setText("");
        lblPreciotTotal.setText("");
        lblCantidad.setText("");

        titulosTabla();
    }

    private void inicio() {
        conexion();
        int[] ultimoNro = contControlComprob.ultimoNro();
        int[] timbrado = contControlComprob.timbrado();

        lblNroTimbrado.setText(String.valueOf(timbrado[1]));
        txtCodigoBarras.requestFocus();
        lblSucursal.setText(ContParamAplicacion.SUC_DESCRIPCION + " - " + ContParamAplicacion.CAJA_DESCRIPCION);
        lblEmpresa.setText(ContParamAplicacion.EMP_NOMBRE);
        lblUser.setText(ContFndUsuarios.USR_NOMBRE_COMPLETO + " -");
        lblFecha.setText(Utilidades.getFecha("dd/MM/yyyy"));
        if (ultimoNro[1] < 1) {
            lblComprobante.setText(numero(String.valueOf(contControlComprob.nroInicialCtrl())));
        } else {
            lblComprobante.setText(numero(String.valueOf(ultimoNro[1] + 1)));
        }
        lblTotalPagar.setText("TOTAL:  0");
        lblTotal.setText("Sub total: 0");
        lblTotalItems.setText("TOTAL ITEMS:  0");
        devolucion = false;
        hiloReloj = new Thread(this);
        hiloReloj.start();
        tablaProductos.getTableHeader().setReorderingAllowed(false);

        if (devolucion == false) {
            lblModoVenta.setForeground(new Color(53, 69, 220));
            lblModoVenta.setText("MODO VENTA: Normal");
        } else {
            lblModoVenta.setForeground(Color.RED);
            lblModoVenta.setText("MODO VENTA: Devolucion");
        }

        imagen();
    }

    private void imagen() {
        File archivo = new File(POS.IMG_LOGO_POS);
        if (archivo.exists()) {
            ImageIcon icon = new ImageIcon(POS.IMG_LOGO_POS);

            int scale = 3;

            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            BufferedImage buffer = new BufferedImage(scale * width, scale * height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = buffer.createGraphics();
            graphics.scale(scale, scale);
            icon.paintIcon(null, graphics, 0, 0);
            graphics.dispose();

            logoEmpresaPos.setHorizontalAlignment(JLabel.CENTER);
            logoEmpresaPos.setVerticalAlignment(JLabel.CENTER);
            logoEmpresaPos.setIcon(icon);
        } else {
            logoEmpresaPos.setBackground(Color.GRAY);
        }
    }

    private boolean getParametrosStock() {
        stkParametros = contStkParametros.getParametrosStock(ContParamAplicacion.SUC_IDENTIFICADOR);

        if (stkParametros != null) {
            return true;
        } else {
            ControlMensajes.error("Parametros de stock no definidos", "Menu cajero");
            return false;
        }
    }

    private void autorizacion(String form) {
        FrmAutorizacion frm = new FrmAutorizacion();
        frm.formulario = form;
        frm.setVisible(true);
    }

    private void mostrarMenu() {
        menu = menu == false;
        panelAcciones.setVisible(menu);
    }

    private void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new RenderTablaPos());
        }
    }

    private void conexion() {
        if (Utilidades.getPing()) {
            if (jdbc.server() != null) {
                FrmPos.lblConexion.setText("EN LINEA");
                FrmPos.lblConexion.setForeground(new Color(0, 121, 56));
            } else {
                FrmPos.lblConexion.setText("SIN CONEXION");
                FrmPos.lblConexion.setForeground(Color.RED);
            }
            jdbc.cerrarConexion();
        } else {
            FrmPos.lblConexion.setText("SIN CONEXION");
            FrmPos.lblConexion.setForeground(Color.RED);
        }
    }

    //si es un producto para devolucion se valida:
    //que exista el producto en la lista de items
    //que la cantidad ingresada no supere la cantidad en la lista de items
    boolean validarDevolucionProducto(String codigoBarras, Double cantidad) {
        String codigo = "";
        int cantidadCodigo = 0;
        double cantidadProducto = 0;

        if (tablaProductos.getRowCount() > 0) {
            for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                codigo = tabla.getValueAt(i, 0).toString();
                if (codigo.equals(codigoBarras)) {
                    cantidadCodigo++;
                    cantidadProducto = cantidadProducto + Double.parseDouble(tabla.getValueAt(i, 2).toString());
                }
            }
        }

        if (cantidadCodigo <= 0 || cantidadProducto == 0) {
            ControlMensajes.error("Producto para devolucion no definido", "Punto de ventas");
            return false;
        }

        if (cantidad > cantidadProducto) {
            ControlMensajes.error("Cantidad debe ser menor o igual a " + String.valueOf(cantidadProducto), "Punto de ventas");
            return false;
        }

        return true;
    }

    private boolean leerCodBarras(String codigo, int idCategoria) {

        codigoPr = txtCodigoBarras.getText().substring(0, 7);

        switch (idCategoria) {
            case 1662://Productos Pesables
                codCantidad = Double.parseDouble(txtCodigoBarras.getText().substring(7, 12)) / 1000;
                txtCantidad.setText(String.valueOf(codCantidad));

                if (codCantidad < 0.0 && txtCantidad.getText().isEmpty()) {
                    txtCantidad.setText("1.0");
                }

                codCantidad = Double.parseDouble(txtCantidad.getText());
                if (codCantidad <= 0.0) {
                    ControlMensajes.error("Cantidad debe ser mayor a cero(0)", "Punto de ventas");
                    return false;
                }

                if (devolucion) {
                    if (validarDevolucionProducto(txtCodigoBarras.getText(), codCantidad)) {
                        if (!contProducto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigo) + "' AND PR.ACTIVO='S'")) {//si no existe contProducto pesable
                            ControlMensajes.error("Producto no definido", "Punto de ventas");
                            txtCantidad.setText("");
                            txtCodigoBarras.requestFocus();
                        }
                    }
                } else {
                    if (!contProducto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigo) + "' AND PR.ACTIVO='S'")) {//si no existe contProducto pesable
                        ControlMensajes.error("Producto no definido", "Punto de ventas");
                        txtCantidad.setText("");
                        txtCodigoBarras.requestFocus();
                    }
                }

                break;
            case 1663://Productos Unitarios
                codCantidad = Double.parseDouble(txtCodigoBarras.getText().substring(7, 12));

                if (codCantidad <= 0.0) {
                    if (txtCantidad.getText().isEmpty()) {
                        txtCantidad.setText("1.0");
                    }
                } else {
                    txtCantidad.setText(String.valueOf(codCantidad));
                }

                codCantidad = Double.parseDouble(txtCantidad.getText());
                if (codCantidad <= 0.0) {
                    ControlMensajes.error("Cantidad debe ser mayor a cero(0)", "Punto de ventas");
                    return false;

                }

                if (devolucion) {
                    if (validarDevolucionProducto(txtCodigoBarras.getText(), codCantidad)) {
                        if (!contProducto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigoPr) + "' AND PR.ACTIVO='S'")) {//si no existe contProducto unitario
                            ControlMensajes.error("Producto no definido", "Punto de ventas");
                            txtCantidad.setText("");
                            txtCodigoBarras.requestFocus();
                        }
                    }
                } else {
                    if (!contProducto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigoPr) + "' AND PR.ACTIVO='S'")) {//si no existe contProducto unitario
                        ControlMensajes.error("Producto no definido", "Punto de ventas");
                        txtCantidad.setText("");
                        txtCodigoBarras.requestFocus();
                    }
                }

                break;
            default://Productos Normales
                if (txtCantidad.getText().isEmpty()) {
                    txtCantidad.setText("1.0");
                }

                codCantidad = Double.parseDouble(txtCantidad.getText());

                if (devolucion) {
                    if (validarDevolucionProducto(txtCodigoBarras.getText(), codCantidad)) {
                        if (!contProducto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigo) + "' AND PR.ACTIVO='S'")) {
                            ControlMensajes.error("Producto no definido", "Punto de ventas");
                            System.out.println("\tProducto " + codigo + " no existe en almacenes");
                            txtCantidad.setText("");
                            txtCodigoBarras.requestFocus();
                        }
                    }
                } else {
                    if (!contProducto(" WHERE PR.CODIGO_BARRAS = '" + codigoBarras(codigo) + "' AND PR.ACTIVO='S'")) {
                        ControlMensajes.error("Producto no definido", "Punto de ventas");
                        System.out.println("\tProducto " + codigo + " no existe en almacenes");
                        txtCantidad.setText("");
                        txtCodigoBarras.requestFocus();
                    }
                }
                break;
        }

        return true;

    }

    private String codigoBarras(String codigo) {
        int longitud = codigo.length();

        for (int i = 0; i < 13 - longitud; i++) {
            codigo += "0";
        }

        return codigo;
    }

    private boolean contProducto(String condicion) {
        String sql = "SELECT PR.IDENTIFICADOR, PR.CODIGO_BARRAS, PR.DESCRIPCION, "
                + "PR.ID_UNIDAD_MEDIDA, "
                + "PR.PRECIO_VENTA * " + codCantidad + " AS IMPORTE_ITEM, "
                + "PR.ID_TIPO_IMPUESTO AS ID_TIPO_IMPUESTO, "
                + "FND_TIPOS_IMPUESTO.VALOR AS PORCENTAJE_IMP, "
                + "PR.ID_PROVEEDOR AS ID_PROVEEDOR,  "
                + "PR.ID_SITIO_PROV AS ID_SITIO_PROV,"
                + "PR.PRECIO_VENTA AS PRECIO_UNITARIO,"
                + "PR_UM_PRODUCTO.COSTO_REMPLAZO AS COSTO_UNITARIO "
                + "FROM PR_PRODUCTOS PR "
                + "LEFT JOIN PR_UM_PRODUCTO ON PR.IDENTIFICADOR = PR_UM_PRODUCTO.ID_PRODUCTO "
                + "LEFT JOIN FND_TIPOS_IMPUESTO ON FND_TIPOS_IMPUESTO.IDENTIFICADOR = PR.ID_TIPO_IMPUESTO "
                + condicion;

        boolean existe = false;
        try {
            ResultSet rs = null;
            rs = cnRs.consultar(sql);

            if (rs.next()) {
                idProducto = rs.getInt(1);
                codBarras = rs.getString(2);
                descripcion = rs.getString(3);
                idUndMedida = rs.getInt(4);
                importeItem = rs.getInt(5);
                idTipoImpuesto = rs.getInt(6);
                porcImpuesto = rs.getInt(7);
                idProveedor = rs.getInt(8);
                idSitioProv = rs.getInt(9);
                precioUnit = rs.getInt(10);
                costoUnit = rs.getDouble(11);
                importeExento = 0;

                if (devolucion) {
                    if (porcImpuesto == 0) {
                        importeExento = precioUnit;
                        montoExento -= precioUnit;
                        precioUnitNeto = precioUnit;
                        impuestoUnitario = 0;
                        importeImpuesto = 0;
                    }
                    if (porcImpuesto == 5) {
                        montoGravado5 -= importeItem / 1.05;
                        precioUnitNeto = precioUnit / 1.05;
                        montoImpuesto5 -= importeItem / 21.0;
                        impuestoUnitario = precioUnit / 21.0;
                        importeImpuesto = importeItem / 21.0;
                    }
                    if (porcImpuesto == 10) {
                        montoGravado10 -= importeItem / 1.1;
                        precioUnitNeto = precioUnit / 1.1;
                        montoImpuesto10 -= importeItem / 11.0;
                        impuestoUnitario = precioUnit / 11.0;
                        importeImpuesto = importeItem / 11.0;
                    }
                } else {
                    if (porcImpuesto == 0) {
                        precioUnitNeto = precioUnit;
                        importeExento = precioUnit;
                        montoExento += precioUnit;
                        impuestoUnitario = 0;
                        importeImpuesto = 0;
                    }
                    if (porcImpuesto == 5) {
                        montoGravado5 += importeItem / 1.05;
                        precioUnitNeto = precioUnit / 1.05;
                        montoImpuesto5 += importeItem / 21.0;
                        impuestoUnitario = precioUnit / 21.0;
                        importeImpuesto = importeItem / 21.0;
                    }
                    if (porcImpuesto == 10) {
                        montoGravado10 += importeItem / 1.1;
                        precioUnitNeto = precioUnit / 1.1;
                        montoImpuesto10 += importeItem / 11.0;
                        impuestoUnitario = precioUnit / 11.0;
                        importeImpuesto = importeItem / 11.0;
                    }
                }

                idItemVta = contComprobante.identificadorItem();

                if (itemVenta()) {
                    registrosTabla();
                    setRedondeoSedeco();
                    totalesVenta();
                } else {
                    ControlMensajes.informacion("Item no registrado", "Punto de ventas");
                }

                existe = true;
            } else {
                System.out.println("\tProducto " + codBarras + " no definido");
                txtCantidad.setText("");
                txtCodigoBarras.requestFocus();
                existe = false;
            }
            rs.close();
            cnRs.cerrarConexion();
        } catch (SQLException ex) {
            ControlMensajes.error("contProducto: " + ex, FrmPos.class.getName());
            existe = false;
        }
        return existe;
    }

    public static void titulosTabla() {
        tabla.setColumnCount(0);
        tabla.setRowCount(0);

        tabla.addColumn("CODIGO BARRAS");
        tabla.addColumn("DESCRIPCION");
        tabla.addColumn("CANTIDAD");
        tabla.addColumn("PRECIO UNIT.");
        tabla.addColumn("TOTAL");
        //COLUMNAS PARA MOVIM. DE STOCK QUE NO SERAN VISIBLES EN LA TABLA
        tabla.addColumn("ID PRODUCTO");
        tabla.addColumn("ID UND. MED.");
        tabla.addColumn("COSTO");
        tabla.addColumn("PRECIO");
        tabla.addColumn("IDENTIFICADOR");
        tabla.addColumn("PORCENTAJE IMP.");
        tabla.addColumn("ID TIPO IMPUESTO");
        tabla.addColumn("ID PROVEEDOR");
        tabla.addColumn("ID SITIO PROVEE");
        tabla.addColumn("ID MV STOCK");

        tablaProductos.setModel(tabla);
        formatoColumnas();
    }

    private void registrosTabla() {
        setCellRender(tablaProductos);

        String[] registros = new String[15];

        if (devolucion) {
            codCantidad = 0 - codCantidad;
        }

        registros[0] = txtCodigoBarras.getText();
        registros[1] = descripcion;
        registros[2] = String.valueOf(codCantidad);
        registros[3] = String.valueOf(miles.format(precioUnit));
        registros[4] = String.valueOf(miles.format(importeItem));
        registros[5] = String.valueOf(idProducto);
        registros[6] = String.valueOf(idUndMedida);
        registros[7] = String.valueOf(costoUnit);
        registros[8] = String.valueOf(precioUnit);
        registros[9] = String.valueOf(idItemVta);
        registros[10] = String.valueOf(porcImpuesto);
        registros[11] = String.valueOf(idTipoImpuesto);
        registros[12] = String.valueOf(idProveedor);
        registros[13] = String.valueOf(idSitioProv);
        registros[14] = String.valueOf(contMovimStock.idMovItem("L"));
        tabla.addRow(registros);
        tablaProductos.setModel(tabla);

        if (devolucion) {
            totalMonto = totalMonto - importeItem;
            totalItems = totalItems - 1;
        } else {
            totalMonto = totalMonto + importeItem;
            totalItems = totalItems + 1;
        }

        lblTotal.setText("Sub total: " + miles.format(totalMonto));
        lblTotalItems.setText("TOTAL ITEMS: " + String.valueOf(totalItems));

        if (descripcion.length() > 40) {
            lblDescripcionProd.setText(descripcion.substring(0, 40));
        } else {
            lblDescripcionProd.setText(descripcion);
        }

        lblCantidad.setText("CANT: " + codCantidad);
        lblPrecioProd.setText("P. UNIT: " + miles.format(precioUnit));
        lblPreciotTotal.setText("IMPORTE: " + miles.format(importeItem));

        itemsTicket(registros[0], registros[1], String.valueOf(porcImpuesto), registros[2], registros[3], registros[4]);

        devolucion = false;
        lblModoVenta.setForeground(new Color(53, 69, 220));
        lblModoVenta.setText("MODO VENTA: Normal");

        txtCodigoBarras.setText("");
        txtCantidad.setText("");

        if (totalItems > 10) {
            Dimension tamnho = tablaProductos.getSize();
            Point p = new Point(0, tamnho.height);
            jScrollPane1.getViewport().setViewPosition(p);
        }

        if (Utilidades.getPing()) {
            contProducto.importarPreciosAplicar();
        }

    }

    private boolean getPromocionProducto() {

        return true;
    }

    public static void formatoColumnas() {

        tablaProductos.getColumnModel().getColumn(5).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(5).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(6).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(6).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(7).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(7).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(7).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(8).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(8).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(8).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(9).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(9).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(9).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(10).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(10).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(10).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(11).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(11).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(11).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(12).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(12).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(12).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(13).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(13).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(13).setPreferredWidth(0);

        tablaProductos.getColumnModel().getColumn(14).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(14).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(14).setPreferredWidth(0);

    }

    private void menuCajero() {
        FrmMenuCajero frm = new FrmMenuCajero();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        frm.toFront();
    }

    private void precios() {
        FrmPosPrecios frm = new FrmPosPrecios();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void clientes() {
        FrmPosCliente frm = new FrmPosCliente();
        frm.idVenta = identificador;
        frm.nroComp = numeroComprobante;
        frm.setTablaProductos(listaProductos());
        frm.setStkParametros(stkParametros);
        frm.setVisible(true);
        frm.toFront();
    }

    private ArrayList<StkItemsMvStock> listaProductos() {
        ArrayList<StkItemsMvStock> lista = new ArrayList<>();

        for (int i = 0; i < tablaProductos.getRowCount(); i++) {
            StkItemsMvStock itemsStock = new StkItemsMvStock();
            itemsStock.setCantidad(Double.parseDouble(tablaProductos.getValueAt(i, 2).toString()));
            itemsStock.setIdProducto(Integer.parseInt(tablaProductos.getValueAt(i, 5).toString()));
            itemsStock.setIdUndMedida(Integer.parseInt(tablaProductos.getValueAt(i, 6).toString()));
            itemsStock.setCostoUnitario(Double.parseDouble(tablaProductos.getValueAt(i, 7).toString()));
            itemsStock.setPrecioUnitario(Double.parseDouble(tablaProductos.getValueAt(i, 8).toString()));
            itemsStock.setIdItemVenta(Long.parseLong(tablaProductos.getValueAt(i, 9).toString()));
            itemsStock.setIdentificador(Long.parseLong(tablaProductos.getValueAt(i, 14).toString()));

            lista.add(i, itemsStock);
        }

        return lista;
    }

    private void cerrar() {
        if (tablaProductos.getRowCount() <= 0) {
            if (ControlMensajes.confirmacion("Deseas salir del sistema", "Punto de ventas")) {
                menuCajero();
                this.dispose();
            } else {
                txtCodigoBarras.requestFocus();
            }
        } else {
            ControlMensajes.error("Debe finalizar la transaccion de venta", "Punto de ventas");
            txtCodigoBarras.requestFocus();
        }
    }

    private String numero(String nro) {
        String ceros = "";
        int dif;
        dif = ContParamAplicacion.VTA_CTRL_COMP_LONG_NRO_COMPROB - nro.length();

        if (dif > 0) {
            for (int i = 0; i < dif; i++) {
                ceros = ceros + "0";
            }
        } else {
            ceros = "";
        }
        return ContParamAplicacion.SUC_CODIGO_CTRL_COMP + "-" + ContParamAplicacion.PEM_CODIGO + "-" + ceros + nro;
    }

    public static void reiniciarValores() {
        lblModoVenta.setForeground(new Color(53, 69, 220));
        lblModoVenta.setText("MODO VENTA: Normal");

        txtCodigoBarras.setText("");
        txtCantidad.setText("");
        lblTotalPagar.setText("TOTAL:  0");
        lblTotal.setText("Sub total:  0");
        lblTotalItems.setText("TOTAL ITEMS:  0");

        lblDescripcionProd.setText("");
        lblPrecioProd.setText("");
        lblPreciotTotal.setText("");
        lblCantidad.setText("");

        codTipoUnid = 0;
        codigoPr = "";
        codCantidad = 0;

        //VARIABLES PARA VTA_ITEMS_COMPROB
        idItemVta = 0;
        idProducto = 0;
        codBarras = "";
        descripcion = "";
        idUndMedida = 0;
        costoUnit = 0;
        precioUnit = 0;
        impuestoUnitario = 0;
        importeExento = 0;
        importeGravado = 0;
        importeImpuesto = 0;
        idTipoImpuesto = 0;
        porcImpuesto = 0;
        idProveedor = 0;
        idSitioProv = 0;
        importeItem = 0;

        //VARIABLES DEL COMPROBANTE
        identificador = 0;
        numeroComprobante = "";
        totalMonto = 0;
        totalItems = 0;
        facturando = false;
        nroTicket = 0;
        montoExento = 0;
        montoGravado5 = 0;
        montoGravado10 = 0;
        montoImpuesto = 0;
        montoImpuesto5 = 0;
        montoImpuesto10 = 0;

        tabla.setColumnCount(0);
        tabla.setRowCount(0);
        titulosTabla();

        facturando = false;
        menu = false;
        indiceTabla = 0;
        devolucion = false;
    }

    //==========================================================================
    private boolean nuevaVenta() {
        int[] ultimoNro = contControlComprob.ultimoNro();
        int[] timbrado = contControlComprob.timbrado();

        if (ultimoNro[1] + 1 > ContParamAplicacion.VTA_ASIG_COMP_NUMERO_HASTA) {
            ControlMensajes.error("Numero maximo de comprobantes emitidos", "Punto de ventas");
            return false;
        } else {
            if (Utilidades.getPing()) {
                contProducto.novedades("PR_PRODUCTOS");
                contProducto.novedades("PR_UM_PRODUCTO");
                contProducto.importarPreciosAplicar();
            }

            dtsCp.setIdentificador(identificador);
            dtsCp.setIdTransaccion(4);
            dtsCp.setNumero(numeroComprobante);
            dtsCp.setFefecha(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsCp.setEstado("00");
            dtsCp.setIdSitio(ContParamAplicacion.SUC_IDENTIFICADOR);
            dtsCp.setIdMoneda(ContParamAplicacion.UND_ID_MONEDA);
            dtsCp.setUsuario(ContFndUsuarios.USR_NOMBRE);
            dtsCp.setUsrCre(ContFndUsuarios.USR_NOMBRE);
            dtsCp.setFeCre(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsCp.setIdCaja(ContParamAplicacion.CAJA_IDENTIFICADOR);
            dtsCp.setNroTicket(nroTicket);
            dtsCp.setNroRegImpos(String.valueOf(timbrado[1]));
            dtsCp.setIdRegistroCtrl(timbrado[0]);
            dtsCp.setIdPuntoEmision(ContParamAplicacion.PEM_IDENTIFICADOR);
            dtsCp.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
            dtsCp.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
            dtsCp.setIdControlCaja(contControlCaja.idApertura());
            dtsCp.setFeEnvSrv(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsCp.setMontoTotal(totalMonto);
            dtsCp.setIdTipoComprob(FrmLogin.idTipoComprob);
            dtsCp.setCantidadPagos(1);
            dtsCp.setDiasPorPago(1);
            dtsCp.setIdAlmacen(21);
            dtsCp.setAtributo1(ContParamAplicacion.CAJA_DESCRIPCION);
            dtsCp.setIdProcesoVenta(63);

            return contComprobante.newVenta(dtsCp);
        }
    }

    private boolean itemVenta() {
        dtsVi.setIdentificador(idItemVta);
        dtsVi.setIdComprobante(identificador);
        dtsVi.setIdEmpresa(ContParamAplicacion.EMP_IDENTIFICADOR);
        dtsVi.setIdUnidad(ContParamAplicacion.UND_IDENTIFICADOR);
        dtsVi.setNumeroItem(totalItems + 1);
        dtsVi.setVrTipoLinea("PR");
        dtsVi.setUsrCre(ContFndUsuarios.USR_NOMBRE);
        dtsVi.setFeCre(Utilidades.getFecha("dd/MMM/yyyy"));
        dtsVi.setIdProducto(idProducto);
        dtsVi.setIdUnidadMedida(idUndMedida);
        if (devolucion) {
            dtsVi.setCantidad(0 - codCantidad);
            dtsVi.setCantidadUb(0 - codCantidad);
            dtsVi.setImporteItem(0 - importeItem);
            dtsVi.setImporteImpuesto(0 - importeImpuesto);
            dtsVi.setImporteGravado(0 - importeGravado);
        } else {
            dtsVi.setCantidad(codCantidad);
            dtsVi.setCantidadUb(codCantidad);
            dtsVi.setImporteItem(importeItem);
            dtsVi.setImporteImpuesto(importeImpuesto);
            dtsVi.setImporteGravado(importeGravado);
        }
        dtsVi.setCostoUnitario(costoUnit);
        dtsVi.setPrecioUnitNeto(precioUnitNeto);
        dtsVi.setPrecioUnitario(precioUnit);
        dtsVi.setPrecioUnitarioUb(precioUnit);
        dtsVi.setImpuestoUnitario(impuestoUnitario);
        dtsVi.setImporteExento(importeExento);
        dtsVi.setIdTipoImpuesto(idTipoImpuesto);
        dtsVi.setImpuestoIncluido("S");
        dtsVi.setPorcImpuesto(porcImpuesto);
        dtsVi.setIdProveedor(idProveedor);
        dtsVi.setIdSitioProv(idSitioProv);

        return contComprobante.agregarItem(dtsVi);
    }

    private void totalesVenta() {
        dtsCp.setMontoTotal(totalPagar);
        dtsCp.setMontoTotalReal(totalMonto);
        dtsCp.setMontoExento(montoExento);
        dtsCp.setMontoGravado((Math.round(montoGravado5) + Math.round(montoGravado10)));
        dtsCp.setMontoGravado5(Math.round(montoGravado5));
        dtsCp.setMontoGravado10(Math.round(montoGravado10));
        dtsCp.setMontoImpuesto((Math.round(montoImpuesto5) + Math.round(montoImpuesto10)));
        dtsCp.setMontoImpuesto5(Math.round(montoImpuesto5));
        dtsCp.setMontoImpuesto10(Math.round(montoImpuesto10));
        dtsCp.setMontoPagado(0);
        dtsCp.setIdentificador(identificador);
        dtsCp.setIdFormaPago(0);
        dtsCp.setDescuento347(0);

        if (contComprobante.totales(dtsCp, 0)) {
            System.out.println("\tTotales del comprobante actualizado");
        }
    }

    private void cancelarComprobante() {
        int[] ultimoNro = contControlComprob.ultimoNro();
        System.out.println("<INICIO PROCESO DE CANCELACION DE COMPROBANTE>");
        if (ControlMensajes.confirmacion("Deseas cancelar el comprobante: " + numeroComprobante, "Punto de ventas")) {
            dtsCp.setUsrAnulacion(ContFndUsuarios.USR_NOMBRE);
            dtsCp.setFeAnulacion(Utilidades.getFecha("dd/MMM/yyyy"));
            dtsCp.setDescripcion("CANCELACION A PEDIDO DEL CLIENTE");
            dtsCp.setIdentificador(identificador);

            System.out.println("\tBASE DE DATOS LOCAL");
            if (contComprobante.cancelarComp(dtsCp, 0)) {
                if (contComprobante.ultNroUsado(ultimoNro[1] + 1, ultimoNro[0], 0)) {
                    System.out.println("\tComprobante cancelado: " + contComprobante.comprobante);
                    System.out.println("\tUltimo numero usado actualizado");
                    ControlMensajes.informacion("Comprobante cancelado: " + numeroComprobante, "Punto de ventas");
                }

                if (Utilidades.getPing()) {
                    System.out.println("\tBASE DE DATOS SERVER");
                    if (contComprobante.ultNroUsado(ultimoNro[1] + 1, ultimoNro[0], 1)) {
                        System.out.println("\tUltimo numero usado modificado");
                    } else {
                        System.out.println("\tUltimo numero usado no modificado");
                    }
                }

                cancelacionTicket();
                FrmPos.reiniciarValores();
                lblComprobante.setText(numero(String.valueOf(ultimoNro[0])));
            } else {
                ControlMensajes.error("Comprobante no cancelado1: " + numeroComprobante, "Punto de ventas");
                System.out.println("\tComprobante no cancelado: " + numeroComprobante);
            }
            txtCodigoBarras.requestFocus();
        } else {
            ControlMensajes.informacion("Comprobante no cancelado: " + numeroComprobante, "Punto de ventas");
            System.out.println("\tComprobante no cancelado: " + numeroComprobante);
        }
        System.out.println("<FIN PROCESO DE CANCELACION DE COMPROBANTE>");
    }

    private void encabezadoTicket() {
        formato = new SimpleDateFormat("dd/MM/yyyy");
        String lineaImpresion = "";

        lineaImpresion += ticket.centrar("TRADE GROUP S.A") + "\n";
        lineaImpresion += ticket.centrar(ContParamAplicacion.EMP_NOMBRE.toUpperCase()) + "\n";
        lineaImpresion += ticket.centrar("RUC.: " + ContParamAplicacion.EMP_NUMERO_DOCUMENTO + "  TEL: " + ContParamAplicacion.EMP_TELEFONOS) + "\n";
        lineaImpresion += ticket.centrar(ContParamAplicacion.SUC_DIRECCION.toUpperCase()) + "\n";
        lineaImpresion += ticket.centrar("TIMBRADO NRO.: " + ContParamAplicacion.VTA_CTRL_NUMERO + " VTO.: " + formato.format(ContParamAplicacion.VTA_CTRL_FECHA_VENCIMIENTO)) + "\n";
        lineaImpresion += "\n";
        lineaImpresion += "LOCAL.: " + ContParamAplicacion.SUC_DESCRIPCION.toUpperCase() + " \n";
        lineaImpresion += "CAJA..: " + ContParamAplicacion.CAJA_DESCRIPCION.toUpperCase() + " " + lblFecha.getText() + " " + lblHora.getText() + "\n";
        lineaImpresion += "CAJERO: " + ContFndUsuarios.USR_NOMBRE_COMPLETO.toUpperCase() + "\n";
        lineaImpresion += FrmLogin.tipoComprob.toUpperCase() + ": " + numeroComprobante + "\n";
        lineaImpresion += "\n";
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += ticket.dobleColumna("Articulo Cantidad Precio", "Total") + "\n";
        lineaImpresion += ticket.separador() + "\n";

        ControlImpresora.imprimir(lineaImpresion);

    }

    private void itemsTicket(String cod, String des, String porc, String cant, String precio, String total) {
        String lineaImpresion = "";

        if (devolucion) {
            lineaImpresion = ticket.centrar("--DEVOLUCION--") + "\n";
            devolucion = false;
        }
        if (cant.equals("1.0")) {
            if (des.length() > 13) {
                lineaImpresion += ticket.dobleColumna(cod + " " + des.substring(0, 13) + " " + porc + "%", total) + "\n";
            } else {
                lineaImpresion += ticket.dobleColumna(cod + " " + des + " " + porc + "%", total) + "\n";
            }
        } else {
            if (des.length() > 19) {
                lineaImpresion += ticket.dobleColumna(cod + " " + des.substring(0, 19), porc + "%") + "\n";
                lineaImpresion += ticket.item(cant, precio, total) + "\n";
            } else {
                lineaImpresion += ticket.dobleColumna(cod + " " + des, porc + "%") + "\n";
                lineaImpresion += ticket.item(cant, precio, total) + "\n";
            }
        }
        System.out.println(lineaImpresion);
        ControlImpresora.imprimir(lineaImpresion);
    }

    private void cancelacionTicket() {
        String lineaImpresion = "";

        lineaImpresion = "\n";
        lineaImpresion += "\n";
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += ticket.centrar("CANCELACION") + "\n";
        lineaImpresion += ticket.centrar("Fecha: " + lblHora.getText()) + "\n";
        lineaImpresion += ticket.separador() + "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";
        lineaImpresion += "\n";

        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmPos.class.getName()).log(Level.SEVERE, null, ex);
        }

        ControlImpresora.imprimir(lineaImpresion);
        ControlImpresora.cortarPapel();
    }

    public void setRedondeoSedeco() {
        int multiplo = 50;
        long total_vta = totalMonto;
        long total_a_pagar = 0;

        total_a_pagar = multiplo * (Math.round(total_vta / multiplo));

        totalPagar = total_a_pagar;
        lblTotalPagar.setText("TOTAL: " + miles.format(total_a_pagar));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEncabezado = new javax.swing.JPanel();
        lblComprobante = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        logoEmpresaPos = new javax.swing.JLabel();
        lblFecha1 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblNroTimbrado = new javax.swing.JLabel();
        lblTimbrado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        panelProductos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigoBarras = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        lblModoVenta = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTotalItems = new javax.swing.JLabel();
        lblTotalPagar = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        panelAcciones = new javax.swing.JPanel();
        btnPrecios = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnDevoluciones = new javax.swing.JButton();
        btnReimpresion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblSucursal = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();
        lblConexion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblDescripcionProd = new javax.swing.JLabel();
        lblPrecioProd = new javax.swing.JLabel();
        lblPreciotTotal = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(".::PUNTO DE VENTAS::.");
        setMinimumSize(new java.awt.Dimension(1092, 766));
        setUndecorated(true);
        getContentPane().setLayout(null);

        panelEncabezado.setBackground(new java.awt.Color(8, 7, 6));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

        lblComprobante.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblComprobante.setForeground(new java.awt.Color(204, 204, 0));
        lblComprobante.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblComprobante.setText("000-000-0000000");

        lblHora.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblHora.setForeground(java.awt.Color.white);
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHora.setText("Fecha");

        logoEmpresaPos.setBackground(new java.awt.Color(153, 153, 153));
        logoEmpresaPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblFecha1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblFecha1.setForeground(new java.awt.Color(254, 254, 254));
        lblFecha1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFecha1.setText("Comprobante Nro.");

        lblFecha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(204, 204, 0));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFecha.setText("FECHA");

        lblNroTimbrado.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblNroTimbrado.setForeground(new java.awt.Color(204, 204, 0));
        lblNroTimbrado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNroTimbrado.setText("12345667");

        lblTimbrado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTimbrado.setForeground(new java.awt.Color(254, 254, 254));
        lblTimbrado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTimbrado.setText("Timbrado Nro.");

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoEmpresaPos, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134)
                .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblFecha1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTimbrado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNroTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoEmpresaPos, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTimbrado)
                .addGap(0, 0, 0)
                .addComponent(lblNroTimbrado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFecha1)
                .addGap(0, 0, 0)
                .addComponent(lblComprobante)
                .addContainerGap())
        );

        getContentPane().add(panelEncabezado);
        panelEncabezado.setBounds(10, 60, 720, 200);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        tablaProductos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tablaProductos.setForeground(new java.awt.Color(0, 0, 0));
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaProductos.setRowHeight(25);
        jScrollPane1.setViewportView(tablaProductos);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 360, 720, 280);

        panelProductos.setBackground(new java.awt.Color(0, 0, 0));
        panelProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Codigo de Barras...");

        txtCodigoBarras.setBackground(new java.awt.Color(0, 9, 18));
        txtCodigoBarras.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtCodigoBarras.setForeground(java.awt.Color.white);
        txtCodigoBarras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoBarras.setCaretColor(java.awt.Color.white);
        txtCodigoBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoBarrasKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Cantidad...");

        txtCantidad.setBackground(new java.awt.Color(0, 9, 18));
        txtCantidad.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtCantidad.setForeground(java.awt.Color.white);
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setCaretColor(java.awt.Color.white);
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
        });

        lblModoVenta.setBackground(java.awt.Color.lightGray);
        lblModoVenta.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblModoVenta.setForeground(new java.awt.Color(53, 69, 220));
        lblModoVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblModoVenta.setText("MODO DE VENTA: Devolucion");

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigoBarras)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(lblModoVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblModoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(panelProductos);
        panelProductos.setBounds(741, 60, 300, 200);

        jPanel4.setBackground(java.awt.Color.darkGray);
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTotalItems.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalItems.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalItems.setText("TOTAL ITEMS: 0");

        lblTotalPagar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTotalPagar.setForeground(new java.awt.Color(204, 204, 0));
        lblTotalPagar.setText("TOTAL: 12345678910");

        lblTotal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("Sub total: 12345678910");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalItems)
                    .addComponent(lblTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal))
                .addGap(0, 26, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblTotalPagar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalItems)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(740, 492, 300, 90);

        panelAcciones.setBackground(java.awt.Color.white);
        panelAcciones.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnPrecios.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnPrecios.setText("Precios (F5)");
        btnPrecios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPreciosMouseClicked(evt);
            }
        });
        btnPrecios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPreciosKeyPressed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnSalir.setText("Salir (Esc)");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
        });
        btnSalir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalirKeyPressed(evt);
            }
        });

        btnDevoluciones.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnDevoluciones.setText("Devolucion (F6)");
        btnDevoluciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDevolucionesMouseClicked(evt);
            }
        });
        btnDevoluciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDevolucionesKeyPressed(evt);
            }
        });

        btnReimpresion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnReimpresion.setText("Reimpresion (F7)");
        btnReimpresion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReimpresionMouseClicked(evt);
            }
        });
        btnReimpresion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnReimpresionKeyPressed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar (F8)");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });

        btnConfirmar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnConfirmar.setText("Confirmar (F10)");
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseClicked(evt);
            }
        });
        btnConfirmar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConfirmarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelAccionesLayout = new javax.swing.GroupLayout(panelAcciones);
        panelAcciones.setLayout(panelAccionesLayout);
        panelAccionesLayout.setHorizontalGroup(
            panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReimpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelAccionesLayout.setVerticalGroup(
            panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReimpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(panelAcciones);
        panelAcciones.setBounds(10, 650, 1030, 77);

        jPanel1.setBackground(new java.awt.Color(204, 204, 0));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblSucursal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblSucursal.setText("SUCURSAL");

        lblUser.setBackground(java.awt.Color.darkGray);
        lblUser.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUser.setText("USUARIO");

        lblEmpresa.setBackground(java.awt.Color.darkGray);
        lblEmpresa.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpresa.setText("EMPRESA");

        lblConexion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblConexion.setForeground(new java.awt.Color(0, 121, 56));
        lblConexion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblConexion.setText("Sin Conexion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(lblEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConexion)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSucursal)
                    .addComponent(lblUser)
                    .addComponent(lblEmpresa)
                    .addComponent(lblConexion))
                .addGap(10, 10, 10))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(14, 12, 1030, 43);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(740, 270, 300, 210);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

        lblDescripcionProd.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblDescripcionProd.setForeground(new java.awt.Color(250, 233, 242));
        lblDescripcionProd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescripcionProd.setText("GASEOSA COCA COLA 500ML");

        lblPrecioProd.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblPrecioProd.setForeground(new java.awt.Color(129, 140, 182));
        lblPrecioProd.setText("PRECIO UNITARIO: 123123123");

        lblPreciotTotal.setBackground(new java.awt.Color(129, 140, 182));
        lblPreciotTotal.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblPreciotTotal.setForeground(new java.awt.Color(129, 140, 182));
        lblPreciotTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPreciotTotal.setText("PRECIO TOTAL: 1423423");

        lblCantidad.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(129, 140, 182));
        lblCantidad.setText("PRECIO UNITARIO: 123123123");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescripcionProd, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblPreciotTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblDescripcionProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecioProd)
                    .addComponent(lblPreciotTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidad))
                .addContainerGap())
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(10, 270, 720, 80);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("IMPORTAR PRODUCTOS NUEVOS (F11)");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(740, 620, 300, 15);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DESPLEGAR // OCULTAR MENU (F12)");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(740, 590, 300, 15);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("DESPLEGAR // OCULTAR MENU (F12)");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(740, 590, 300, 15);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No se registraron items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos no importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtCodigoBarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarrasKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                panelAcciones.setVisible(false);

                if (!getParametrosStock()) {
                    return;
                }

                if (!contControlComprob.validarTimbrado(Utilidades.getFecha("dd/MM/yyyy"))) {
                    ControlMensajes.error("Timbrado no definido", "Punto de ventas");
                    return;
                }

                if (!contControlComprob.validarPeriodoGestion(Utilidades.getFecha("dd/MM/yyyy"))) {
                    ControlMensajes.error("Periodo administrativo no definido", "Punto de ventas");
                    return;
                }

                if (!contControlCaja.valApertura()) {
                    ControlMensajes.error("Control de caja no definido para la fecha:: " + Utilidades.getFecha("dd/MM/yyyy"), "Punto de ventas");
                }

                if (!impresora.habilitado()) {
                    ControlMensajes.error("Impresora no disponible", "Punto de ventas");
                    return;
                }

                if (txtCodigoBarras.getText().isEmpty()) {
                    txtCantidad.requestFocus();
                    return;
                }

                if (!txtCantidad.getText().isEmpty()) {
                    if (Double.parseDouble(txtCantidad.getText()) <= 0) {
                        ControlMensajes.error("Cantidad debe ser mayor a cero", "Punto de ventas");
                        return;
                    }
                }

                conexion();

                if (facturando) { //AGREGAMOS ITEMS AL COMPROBANTE
                    if (txtCodigoBarras.getText().length() < 13) {
                        String ceros = "";
                        for (int i = 0; i < 13 - txtCodigoBarras.getText().length(); i++) {
                            ceros += "0";
                        }
                        txtCodigoBarras.setText(ceros + txtCodigoBarras.getText());
                    }

                    //Obtenemos la categoria al cual corresponde el contProducto y se obtiene el codigo de barras
                    int idCategoria = contComprobante.idCategoria(txtCodigoBarras.getText().substring(0, 2));
                    String cod = "";
                    switch (idCategoria) {
                        case 1662://Productos Pesables
                        case 1663: //Productos Unitarios
                            cod = txtCodigoBarras.getText().substring(0, 7);
                            break;
                        default://Productos normales
                            cod = txtCodigoBarras.getText();
                            break;
                    }
                    leerCodBarras(cod, idCategoria);

                } else { //SE CREA UN NUEVO COMPROBANTE
                    int[] ultimoNro = contControlComprob.ultimoNro();
                    devolucion = false;

                    if (txtCodigoBarras.getText().length() < 13) {
                        String ceros = "";
                        for (int i = 0; i < 13 - txtCodigoBarras.getText().length(); i++) {
                            ceros += "0";
                        }
                        txtCodigoBarras.setText(ceros + txtCodigoBarras.getText());
                    }

                    //Obtenemos la categoria al cual corresponde el contProducto y se obtiene el codigo de barras
                    int idCategoria = contComprobante.idCategoria(txtCodigoBarras.getText().substring(0, 2));
                    String cod = "";
                    switch (idCategoria) {
                        case 1662://Productos Pesables
                        case 1663: //Productos Unitarios
                            cod = txtCodigoBarras.getText().substring(0, 7);
                            break;
                        default://Productos normales
                            cod = txtCodigoBarras.getText();
                            break;
                    }

                    if (!contComprobante.valProducto(codigoBarras(cod))) { // || contComprobante.valProducto(txtCodigoBarras.getText())) {
                        ControlMensajes.error("Producto no definido", "Punto de ventas");
                        txtCantidad.setText("");
                        txtCodigoBarras.requestFocus();
                        return;
                    }

                    System.out.println("<INICIO DE PROCESO DE FACTURACION>");
                    identificador = contComprobante.identificador();
                    nroTicket = ultimoNro[1] + 1;
                    numeroComprobante = String.valueOf(numero(String.valueOf(nroTicket)));

                    if (nuevaVenta()) {
                        if (contComprobante.ultNroUsado(ultimoNro[1] + 1, ultimoNro[0], 0)) {
                            System.out.println("\tUltimo numero usado modificado");
                            encabezadoTicket();
                            leerCodBarras(cod, idCategoria);
                            facturando = true;
                        } else {
                            ControlMensajes.error("Ultimo numero usado no actualizado", "Punto de ventas");
                        }
                    } else {
                        ControlMensajes.error("Comprobante no registrado", "Punto de ventas");
                    }
                }

                break;

            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;

        }
    }//GEN-LAST:event_txtCodigoBarrasKeyPressed

    private void btnPreciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPreciosMouseClicked
        if (evt.getClickCount() == 1) {
            precios();
            txtCodigoBarras.requestFocus();
        }
    }//GEN-LAST:event_btnPreciosMouseClicked

    private void btnDevolucionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDevolucionesMouseClicked
        if (evt.getClickCount() == 1) {
            if (totalItems > 0) {
                lblModoVenta.setForeground(Color.RED);
                lblModoVenta.setText("MODO VENTA: Devolucion");
                txtCodigoBarras.requestFocus();
            } else {
                ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
            }
        }
    }//GEN-LAST:event_btnDevolucionesMouseClicked

    private void btnReimpresionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReimpresionMouseClicked
        if (evt.getClickCount() == 1) {
            autorizacion("reimpresion");
        }
    }//GEN-LAST:event_btnReimpresionMouseClicked

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        if (evt.getClickCount() == 1) {
            cerrar();
            txtCodigoBarras.requestFocus();
        }
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnPreciosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPreciosKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    lblModoVenta.setForeground(Color.RED);
                    lblModoVenta.setText("MODO VENTA: Devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_btnPreciosKeyPressed

    private void btnDevolucionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDevolucionesKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    lblModoVenta.setForeground(Color.RED);
                    lblModoVenta.setText("MODO VENTA: Devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_btnDevolucionesKeyPressed

    private void btnReimpresionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnReimpresionKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    lblModoVenta.setForeground(Color.RED);
                    lblModoVenta.setText("MODO VENTA: Devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_btnReimpresionKeyPressed

    private void btnSalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalirKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    lblModoVenta.setForeground(Color.RED);
                    lblModoVenta.setText("MODO VENTA: Devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_btnSalirKeyPressed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        if (evt.getClickCount() == 1) {
            if (tablaProductos.getRowCount() > 0) {
                cancelarComprobante();
                txtCodigoBarras.requestFocus();
            } else {
                ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
            }
        }
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    lblModoVenta.setForeground(Color.RED);
                    lblModoVenta.setText("MODO VENTA: Devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                    ControlMensajes.informacion("Productos importados", "Punto de ventas");
                } else {
                    ControlMensajes.informacion("Productos importados \n Sin conexion a servidor", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnConfirmarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseClicked
        if (evt.getClickCount() == KeyEvent.VK_ENTER) {
            if (totalItems > 0 && devolucion != true) {
                clientes();
            } else {
                ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
            }
        }
    }//GEN-LAST:event_btnConfirmarMouseClicked

    private void btnConfirmarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConfirmarKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F5:
                precios();
                break;

            case KeyEvent.VK_F6:
                if (tablaProductos.getRowCount() > 0) {
                    autorizacion("devolucion");
                    lblModoVenta.setForeground(Color.RED);
                    lblModoVenta.setText("MODO VENTA: Devolucion");
                    txtCodigoBarras.requestFocus();
                } else {
                    ControlMensajes.error("No existen items para devolucion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F7:
                autorizacion("reimpresion");
                txtCodigoBarras.requestFocus();
                break;

            case KeyEvent.VK_F8:
                if (tablaProductos.getRowCount() > 0) {
                    txtCodigoBarras.requestFocus();
                    autorizacion("cancelacion");
                } else {
                    ControlMensajes.error("No existe comprobante para cancelacion", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F10:
                if (tablaProductos.getRowCount() > 0) {
                    clientes();
                } else {
                    ControlMensajes.error("No se registraron items al comprobante", "Punto de ventas");
                }
                break;

            case KeyEvent.VK_F11:
                if (Utilidades.getPing()) {
                    contProducto.novedades("PR_PRODUCTOS");
                    contProducto.novedades("PR_UM_PRODUCTO");
                    contProducto.importarPreciosAplicar();
                }
                break;

            case KeyEvent.VK_F12:
                mostrarMenu();
                break;

            case KeyEvent.VK_ESCAPE:
                cerrar();
                break;
        }
    }//GEN-LAST:event_btnConfirmarKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnDevoluciones;
    private javax.swing.JButton btnPrecios;
    private javax.swing.JButton btnReimpresion;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblCantidad;
    public static javax.swing.JLabel lblComprobante;
    public static javax.swing.JLabel lblConexion;
    public static javax.swing.JLabel lblDescripcionProd;
    public static javax.swing.JLabel lblEmpresa;
    public static javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFecha1;
    public static javax.swing.JLabel lblHora;
    public static javax.swing.JLabel lblModoVenta;
    public static javax.swing.JLabel lblNroTimbrado;
    public static javax.swing.JLabel lblPrecioProd;
    public static javax.swing.JLabel lblPreciotTotal;
    private javax.swing.JLabel lblSucursal;
    private javax.swing.JLabel lblTimbrado;
    public static javax.swing.JLabel lblTotal;
    public static javax.swing.JLabel lblTotalItems;
    public static javax.swing.JLabel lblTotalPagar;
    public static javax.swing.JLabel lblUser;
    private javax.swing.JLabel logoEmpresaPos;
    private javax.swing.JPanel panelAcciones;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelProductos;
    public static javax.swing.JTable tablaProductos;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCodigoBarras;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == hiloReloj) {
            calcula();
            lblHora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }

}
