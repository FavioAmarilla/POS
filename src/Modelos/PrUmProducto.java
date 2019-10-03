package Modelos;

public class PrUmProducto {

    int idProducto, idUnidadMedida, idEmpresa, idUnidad;
    String activo, esUmPrincipal, usrCre, fecCre;
    int idPresentacion;
    int idUdmConten, contenido, porcRecargo;
    String precioBloqueado;
    double precioVentaMe, stockMinimo, stockMaximo, precioVenta;
    double maxStkNegativo, precioMayorista, precioDistrib;
    int identificador;
    double costoPromedio, costoRemplazo;
    double costoPromedioUs, costoPromedioEx;
    double costoRemplazoUs, costoRemplazoEx;
    String fechaDesde, fechaHasta;
    double costoPromedioConIva, costoPromedioUsConIva;
    double costoPromedioExConIva, costoRemplazoConIva;
    double costoRemplazoUsConIva, costoRemplazoExConIva;
    double costoPromedioInicial, costoRemplazoInicial;

    public PrUmProducto() {
    }

    public PrUmProducto(int idProducto, int idUnidadMedida, int idEmpresa, int idUnidad, String activo, String esUmPrincipal, String usrCre, String fecCre, int idPresentacion, int idUdmConten, int contenido, int porcRecargo, String precioBloqueado, double precioVentaMe, double stockMinimo, double stockMaximo, double precioVenta, double maxStkNegativo, double precioMayorista, double precioDistrib, int identificador, double costoPromedio, double costoRemplazo, double costoPromedioUs, double costoPromedioEx, double costoRemplazoUs, double costoRemplazoEx, String fechaDesde, String fechaHasta, double costoPromedioConIva, double costoPromedioUsConIva, double costoPromedioExConIva, double costoRemplazoConIva, double costoRemplazoUsConIva, double costoRemplazoExConIva, double costoPromedioInicial, double costoRemplazoInicial) {
        this.idProducto = idProducto;
        this.idUnidadMedida = idUnidadMedida;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.activo = activo;
        this.esUmPrincipal = esUmPrincipal;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.idPresentacion = idPresentacion;
        this.idUdmConten = idUdmConten;
        this.contenido = contenido;
        this.porcRecargo = porcRecargo;
        this.precioBloqueado = precioBloqueado;
        this.precioVentaMe = precioVentaMe;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.precioVenta = precioVenta;
        this.maxStkNegativo = maxStkNegativo;
        this.precioMayorista = precioMayorista;
        this.precioDistrib = precioDistrib;
        this.identificador = identificador;
        this.costoPromedio = costoPromedio;
        this.costoRemplazo = costoRemplazo;
        this.costoPromedioUs = costoPromedioUs;
        this.costoPromedioEx = costoPromedioEx;
        this.costoRemplazoUs = costoRemplazoUs;
        this.costoRemplazoEx = costoRemplazoEx;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.costoPromedioConIva = costoPromedioConIva;
        this.costoPromedioUsConIva = costoPromedioUsConIva;
        this.costoPromedioExConIva = costoPromedioExConIva;
        this.costoRemplazoConIva = costoRemplazoConIva;
        this.costoRemplazoUsConIva = costoRemplazoUsConIva;
        this.costoRemplazoExConIva = costoRemplazoExConIva;
        this.costoPromedioInicial = costoPromedioInicial;
        this.costoRemplazoInicial = costoRemplazoInicial;
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getEsUmPrincipal() {
        return esUmPrincipal;
    }

    public void setEsUmPrincipal(String esUmPrincipal) {
        this.esUmPrincipal = esUmPrincipal;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public String getFecCre() {
        return fecCre;
    }

    public void setFecCre(String fecCre) {
        this.fecCre = fecCre;
    }

    public int getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(int idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public int getIdUdmConten() {
        return idUdmConten;
    }

    public void setIdUdmConten(int idUdmConten) {
        this.idUdmConten = idUdmConten;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public int getPorcRecargo() {
        return porcRecargo;
    }

    public void setPorcRecargo(int porcRecargo) {
        this.porcRecargo = porcRecargo;
    }

    public String getPrecioBloqueado() {
        return precioBloqueado;
    }

    public void setPrecioBloqueado(String precioBloqueado) {
        this.precioBloqueado = precioBloqueado;
    }

    public double getPrecioVentaMe() {
        return precioVentaMe;
    }

    public void setPrecioVentaMe(double precioVentaMe) {
        this.precioVentaMe = precioVentaMe;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(double stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getMaxStkNegativo() {
        return maxStkNegativo;
    }

    public void setMaxStkNegativo(double maxStkNegativo) {
        this.maxStkNegativo = maxStkNegativo;
    }

    public double getPrecioMayorista() {
        return precioMayorista;
    }

    public void setPrecioMayorista(double precioMayorista) {
        this.precioMayorista = precioMayorista;
    }

    public double getPrecioDistrib() {
        return precioDistrib;
    }

    public void setPrecioDistrib(double precioDistrib) {
        this.precioDistrib = precioDistrib;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public double getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public double getCostoRemplazo() {
        return costoRemplazo;
    }

    public void setCostoRemplazo(double costoRemplazo) {
        this.costoRemplazo = costoRemplazo;
    }

    public double getCostoPromedioUs() {
        return costoPromedioUs;
    }

    public void setCostoPromedioUs(double costoPromedioUs) {
        this.costoPromedioUs = costoPromedioUs;
    }

    public double getCostoPromedioEx() {
        return costoPromedioEx;
    }

    public void setCostoPromedioEx(double costoPromedioEx) {
        this.costoPromedioEx = costoPromedioEx;
    }

    public double getCostoRemplazoUs() {
        return costoRemplazoUs;
    }

    public void setCostoRemplazoUs(double costoRemplazoUs) {
        this.costoRemplazoUs = costoRemplazoUs;
    }

    public double getCostoRemplazoEx() {
        return costoRemplazoEx;
    }

    public void setCostoRemplazoEx(double costoRemplazoEx) {
        this.costoRemplazoEx = costoRemplazoEx;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public double getCostoPromedioConIva() {
        return costoPromedioConIva;
    }

    public void setCostoPromedioConIva(double costoPromedioConIva) {
        this.costoPromedioConIva = costoPromedioConIva;
    }

    public double getCostoPromedioUsConIva() {
        return costoPromedioUsConIva;
    }

    public void setCostoPromedioUsConIva(double costoPromedioUsConIva) {
        this.costoPromedioUsConIva = costoPromedioUsConIva;
    }

    public double getCostoPromedioExConIva() {
        return costoPromedioExConIva;
    }

    public void setCostoPromedioExConIva(double costoPromedioExConIva) {
        this.costoPromedioExConIva = costoPromedioExConIva;
    }

    public double getCostoRemplazoConIva() {
        return costoRemplazoConIva;
    }

    public void setCostoRemplazoConIva(double costoRemplazoConIva) {
        this.costoRemplazoConIva = costoRemplazoConIva;
    }

    public double getCostoRemplazoUsConIva() {
        return costoRemplazoUsConIva;
    }

    public void setCostoRemplazoUsConIva(double costoRemplazoUsConIva) {
        this.costoRemplazoUsConIva = costoRemplazoUsConIva;
    }

    public double getCostoRemplazoExConIva() {
        return costoRemplazoExConIva;
    }

    public void setCostoRemplazoExConIva(double costoRemplazoExConIva) {
        this.costoRemplazoExConIva = costoRemplazoExConIva;
    }

    public double getCostoPromedioInicial() {
        return costoPromedioInicial;
    }

    public void setCostoPromedioInicial(double costoPromedioInicial) {
        this.costoPromedioInicial = costoPromedioInicial;
    }

    public double getCostoRemplazoInicial() {
        return costoRemplazoInicial;
    }

    public void setCostoRemplazoInicial(double costoRemplazoInicial) {
        this.costoRemplazoInicial = costoRemplazoInicial;
    }

   
}
