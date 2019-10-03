package Modelos;

public class PsPreciosAplicar {
    int identificador;
    int idEmpresa;
    int idUnidad;
    int idSitio;
    int idAplicacion;
    int idProducto;
    int idUnidadMed;
    int idCaja;
    String usrCre;
    String fecCre;
    int precioNuevo;
    String fechaAplicacion;
    int precioAnterior;
    String codBarras;
    String precioSugerido;
    String usrMod;
    String fecMod;

    public PsPreciosAplicar() {
    }

    public PsPreciosAplicar(int identificador, int idEmpresa, int idUnidad, int idSitio, int idAplicacion, int idProducto, int idUnidadMed, int idCaja, String usrCre, String fecCre, int precioNuevo, String fechaAplicacion, int precioAnterior, String codBarras, String precioSugerido, String usrMod, String fecMod) {
        this.identificador = identificador;
        this.idEmpresa = idEmpresa;
        this.idUnidad = idUnidad;
        this.idSitio = idSitio;
        this.idAplicacion = idAplicacion;
        this.idProducto = idProducto;
        this.idUnidadMed = idUnidadMed;
        this.idCaja = idCaja;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.precioNuevo = precioNuevo;
        this.fechaAplicacion = fechaAplicacion;
        this.precioAnterior = precioAnterior;
        this.codBarras = codBarras;
        this.precioSugerido = precioSugerido;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
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

    public int getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(int idSitio) {
        this.idSitio = idSitio;
    }

    public int getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(int idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUnidadMed() {
        return idUnidadMed;
    }

    public void setIdUnidadMed(int idUnidadMed) {
        this.idUnidadMed = idUnidadMed;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
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

    public int getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(int precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public String getfechaAplicacion() {
        return fechaAplicacion;
    }

    public void setfechaAplicacion(String fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public int getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(int precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getPrecioSugerido() {
        return precioSugerido;
    }

    public void setPrecioSugerido(String precioSugerido) {
        this.precioSugerido = precioSugerido;
    }

    public String getUsrMod() {
        return usrMod;
    }

    public void setUsrMod(String usrMod) {
        this.usrMod = usrMod;
    }

    public String getFecMod() {
        return fecMod;
    }

    public void setFecMod(String fecMod) {
        this.fecMod = fecMod;
    }

    
    
    
}
