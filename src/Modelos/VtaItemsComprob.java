package Modelos;

import java.sql.Timestamp;

public class VtaItemsComprob {
    
    long identificador, idComprobante,idEmpresa,idUnidad,numeroItem;
    String vrTipoLinea;
    String usrCre, usrMod;
    int idProducto,idUnidadMedida;
    double cantidad, cantidadUb;
    double costoUnitario;
    double precioUnitario, precioUnitNeto, precioUnitarioUb;
    double impuestoUnitario, descuentoUnitario,importeExento,importeGravado;
    double importeDescuento, importeItem, importeImpuesto;
    int porcDescuento, idTipoImpuesto;
    String impuestoIncluido;
    int porcImpuesto, idProveedor,idSitioProv;
    int idMovStkItem;
    
    Timestamp fecCre, fecMod;
    String feCre, feMod;
    
    public VtaItemsComprob() {
    }

    public VtaItemsComprob(int identificador, int idComprobante, int idEmpresa, int idUnidad, int numeroItem, String vrTipoLinea, String usrCre, String usrMod, int idProducto, int idUnidadMedida, double cantidad, double cantidadUb, double costoUnitario, double precioUnitario, double precioUnitNeto, double precioUnitarioUb, double impuestoUnitario, double descuentoUnitario, double importeExento, double importeGravado, double importeDescuento, double importeItem, double importeImpuesto, int porcDescuento, int idTipoImpuesto, String impuestoIncluido, int porcImpuesto, int idProveedor, int idSitioProv, int idMovStkItem, Timestamp fecCre, Timestamp fecMod, String feCre, String feMod) {
        this.identificador = identificador;
        this.idComprobante = idComprobante;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.numeroItem = numeroItem;
        this.vrTipoLinea = vrTipoLinea;
        this.usrCre = usrCre;
        this.usrMod = usrMod;
        this.idProducto = idProducto;
        this.idUnidadMedida = idUnidadMedida;
        this.cantidad = cantidad;
        this.cantidadUb = cantidadUb;
        this.costoUnitario = costoUnitario;
        this.precioUnitario = precioUnitario;
        this.precioUnitNeto = precioUnitNeto;
        this.precioUnitarioUb = precioUnitarioUb;
        this.impuestoUnitario = impuestoUnitario;
        this.descuentoUnitario = descuentoUnitario;
        this.importeExento = importeExento;
        this.importeGravado = importeGravado;
        this.importeDescuento = importeDescuento;
        this.importeItem = importeItem;
        this.importeImpuesto = importeImpuesto;
        this.porcDescuento = porcDescuento;
        this.idTipoImpuesto = idTipoImpuesto;
        this.impuestoIncluido = impuestoIncluido;
        this.porcImpuesto = porcImpuesto;
        this.idProveedor = idProveedor;
        this.idSitioProv = idSitioProv;
        this.idMovStkItem = idMovStkItem;
        this.fecCre = fecCre;
        this.fecMod = fecMod;
        this.feCre = feCre;
        this.feMod = feMod;
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public long getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(int numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getVrTipoLinea() {
        return vrTipoLinea;
    }

    public void setVrTipoLinea(String vrTipoLinea) {
        this.vrTipoLinea = vrTipoLinea;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadUb() {
        return cantidadUb;
    }

    public void setCantidadUb(double cantidadUb) {
        this.cantidadUb = cantidadUb;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioUnitNeto() {
        return precioUnitNeto;
    }

    public void setPrecioUnitNeto(double precioUnitNeto) {
        this.precioUnitNeto = precioUnitNeto;
    }

    public double getPrecioUnitarioUb() {
        return precioUnitarioUb;
    }

    public void setPrecioUnitarioUb(double precioUnitarioUb) {
        this.precioUnitarioUb = precioUnitarioUb;
    }

    public double getImpuestoUnitario() {
        return impuestoUnitario;
    }

    public void setImpuestoUnitario(double impuestoUnitario) {
        this.impuestoUnitario = impuestoUnitario;
    }

    public double getDescuentoUnitario() {
        return descuentoUnitario;
    }

    public void setDescuentoUnitario(double descuentoUnitario) {
        this.descuentoUnitario = descuentoUnitario;
    }

    public double getImporteExento() {
        return importeExento;
    }

    public void setImporteExento(double importeExento) {
        this.importeExento = importeExento;
    }

    public double getImporteGravado() {
        return importeGravado;
    }

    public void setImporteGravado(double importeGravado) {
        this.importeGravado = importeGravado;
    }

    public double getImporteDescuento() {
        return importeDescuento;
    }

    public void setImporteDescuento(double importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public double getImporteItem() {
        return importeItem;
    }

    public void setImporteItem(double importeItem) {
        this.importeItem = importeItem;
    }

    public double getImporteImpuesto() {
        return importeImpuesto;
    }

    public void setImporteImpuesto(double importeImpuesto) {
        this.importeImpuesto = importeImpuesto;
    }

    public int getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(int porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public int getIdTipoImpuesto() {
        return idTipoImpuesto;
    }

    public void setIdTipoImpuesto(int idTipoImpuesto) {
        this.idTipoImpuesto = idTipoImpuesto;
    }

    public String getImpuestoIncluido() {
        return impuestoIncluido;
    }

    public void setImpuestoIncluido(String impuestoIncluido) {
        this.impuestoIncluido = impuestoIncluido;
    }

    public int getPorcImpuesto() {
        return porcImpuesto;
    }

    public void setPorcImpuesto(int porcImpuesto) {
        this.porcImpuesto = porcImpuesto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdSitioProv() {
        return idSitioProv;
    }

    public void setIdSitioProv(int idSitioProv) {
        this.idSitioProv = idSitioProv;
    }

    public int getIdMovStkItem() {
        return idMovStkItem;
    }

    public void setIdMovStkItem(int idMovStkItem) {
        this.idMovStkItem = idMovStkItem;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public String getFeCre() {
        return feCre;
    }

    public void setFeCre(String feCre) {
        this.feCre = feCre;
    }

    public String getFeMod() {
        return feMod;
    }

    public void setFeMod(String feMod) {
        this.feMod = feMod;
    }

    
    
  
}
