package Modelos;

import java.sql.Timestamp;
import java.util.Date;

public class StkItemsMvStock {

    long identificador, idMovimiento;
    int idEmpresa;
    int idUnidad, idTransaccion, idAlmacen;
    String vrAccion;
    int idProducto, idUndMedida;
    double cantidad;
    String usrCre;
    double cantidadUb;
    double precioUnitUb, costoUnitUb, precioUnitario, costoUnitario;
    String codBarras;
    int idEstado, idMotivo;
    long idItemVenta, idMovimItems;
    Timestamp fecCre;
    Date fecha;

    public StkItemsMvStock() {
    }

    public StkItemsMvStock(int identificador, int idMovimiento, int idEmpresa, int idUnidad, int idTransaccion, int idAlmacen, String vrAccion, int idProducto, int idUndMedida, double cantidad, String usrCre, double cantidadUb, double precioUnitUb, double costoUnitUb, double precioUnitario, double costoUnitario, String codBarras, int idEstado, int idMotivo, int idItemVenta, int idMovimItems, Timestamp fecCre, Date fecha) {
        this.identificador = identificador;
        this.idMovimiento = idMovimiento;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idTransaccion = idTransaccion;
        this.idAlmacen = idAlmacen;
        this.vrAccion = vrAccion;
        this.idProducto = idProducto;
        this.idUndMedida = idUndMedida;
        this.cantidad = cantidad;
        this.usrCre = usrCre;
        this.cantidadUb = cantidadUb;
        this.precioUnitUb = precioUnitUb;
        this.costoUnitUb = costoUnitUb;
        this.precioUnitario = precioUnitario;
        this.costoUnitario = costoUnitario;
        this.codBarras = codBarras;
        this.idEstado = idEstado;
        this.idMotivo = idMotivo;
        this.idItemVenta = idItemVenta;
        this.idMovimItems = idMovimItems;
        this.fecCre = fecCre;
        this.fecha = fecha;
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getVrAccion() {
        return vrAccion;
    }

    public void setVrAccion(String vrAccion) {
        this.vrAccion = vrAccion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUndMedida() {
        return idUndMedida;
    }

    public void setIdUndMedida(int idUndMedida) {
        this.idUndMedida = idUndMedida;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public double getCantidadUb() {
        return cantidadUb;
    }

    public void setCantidadUb(double cantidadUb) {
        this.cantidadUb = cantidadUb;
    }

    public double getPrecioUnitUb() {
        return precioUnitUb;
    }

    public void setPrecioUnitUb(double precioUnitUb) {
        this.precioUnitUb = precioUnitUb;
    }

    public double getCostoUnitUb() {
        return costoUnitUb;
    }

    public void setCostoUnitUb(double costoUnitUb) {
        this.costoUnitUb = costoUnitUb;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public long getIdItemVenta() {
        return idItemVenta;
    }

    public void setIdItemVenta(long idItemVenta) {
        this.idItemVenta = idItemVenta;
    }

    public long getIdMovimItems() {
        return idMovimItems;
    }

    public void setIdMovimItems(long idMovimItems) {
        this.idMovimItems = idMovimItems;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

}
