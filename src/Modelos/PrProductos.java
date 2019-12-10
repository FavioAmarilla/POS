package Modelos;

import java.sql.Date;
import java.sql.Timestamp;

public class PrProductos {

    int identificador, IdUnidad, idEmpresa;
    String descripcion;
    int idCategotia;
    String activo, usoInterno, itemInventario, itemVentas, itemCompras;
    int idUnidadMedida, idTipoImpuesto;
    String usrCre, fecCre, usrMod, fecMod;
    int idDtoVentas, idTipoGrupo, idLinea, idMarca;
    String codigo, codBarras, codBarrasAnt, codBarrasOrig, vrOrigen;
    int idPaisOrigen;
    String vrCondicion, controlado, cadFrio, controlarVcto, compraUnica, enviarCaja;
    int idCtaContCp, idCtaContVtas;
    double precioVenta, costoPromedio, costoRemplazo, porcRecargo;
    String precioBloqueado;
    int idProveedor;
    String archivoImg;
    int idSitioProv, idClaseProd;
    String segmento1, segmento2, segmento3, segmento4, segmento5;
    int idCtaContCost;
    String descAbribiada, admiteCodNodef, codigoBalanza;
    int idCtaContProd, maxStkNegativo, precioMayorista, precioDist, precioVentaMe, stockMinimo, stockMaximo;
    String texto, rucImport;
    double costoPromedioUs, costoPromedioEx, costoRemplazoEx, costoRemplazoUs;
    int idUdmDefecto;
    int idTipoCodBarras, nroSerieGrupo;
    String precioDiferido, codigoUnificacion, admiteVtaImporte;
    int idProdEnvase, idEdmEnvase, idLaboratorio, idPresentacion;
    double costoPromConIva, costoPromUsConIva, costoPromExConIva;
    double costoRemplazoConIva, costoRemplazoUsConIva, costoRemplazoExConIva;
    Timestamp fechaUltInact, fechaUltReactiv;
    String buscarEnCaja, accion;
    int idNovedad;
    Date fechaModificacion;

    public PrProductos() {
    }

    public PrProductos(int identificador, int IdUnidad, int idEmpresa, String descripcion, int idCategotia, String activo, String usoInterno, String itemInventario, String itemVentas, String itemCompras, int idUnidadMedida, int idTipoImpuesto, String usrCre, String fecCre, String usrMod, String fecMod, int idDtoVentas, int idTipoGrupo, int idLinea, int idMarca, String codigo, String codBarras, String codBarrasAnt, String codBarrasOrig, String vrOrigen, int idPaisOrigen, String vrCondicion, String controlado, String cadFrio, String controlarVcto, String compraUnica, String enviarCaja, int idCtaContCp, int idCtaContVtas, double precioVenta, double costoPromedio, double costoRemplazo, double porcRecargo, String precioBloqueado, int idProveedor, String archivoImg, int idSitioProv, int idClaseProd, String segmento1, String segmento2, String segmento3, String segmento4, String segmento5, int idCtaContCost, String descAbribiada, String admiteCodNodef, String codigoBalanza, int idCtaContProd, int maxStkNegativo, int precioMayorista, int precioDist, int precioVentaMe, int stockMinimo, int stockMaximo, String texto, String rucImport, double costoPromedioUs, double costoPromedioEx, double costoRemplazoEx, double costoRemplazoUs, int idUdmDefecto, int idTipoCodBarras, int nroSerieGrupo, String precioDiferido, String codigoUnificacion, String admiteVtaImporte, int idProdEnvase, int idEdmEnvase, int idLaboratorio, int idPresentacion, double costoPromConIva, double costoPromUsConIva, double costoPromExConIva, double costoRemplazoConIva, double costoRemplazoUsConIva, double costoRemplazoExConIva, Timestamp fechaUltInact, Timestamp fechaUltReactiv, String buscarEnCaja, Date fechaModificacion, String accion, int idNovedad) {
        this.identificador = identificador;
        this.IdUnidad = IdUnidad;
        this.idEmpresa = idEmpresa;
        this.descripcion = descripcion;
        this.idCategotia = idCategotia;
        this.activo = activo;
        this.usoInterno = usoInterno;
        this.itemInventario = itemInventario;
        this.itemVentas = itemVentas;
        this.itemCompras = itemCompras;
        this.idUnidadMedida = idUnidadMedida;
        this.idTipoImpuesto = idTipoImpuesto;
        this.usrCre = usrCre;
        this.fecCre = fecCre;
        this.usrMod = usrMod;
        this.fecMod = fecMod;
        this.idDtoVentas = idDtoVentas;
        this.idTipoGrupo = idTipoGrupo;
        this.idLinea = idLinea;
        this.idMarca = idMarca;
        this.codigo = codigo;
        this.codBarras = codBarras;
        this.codBarrasAnt = codBarrasAnt;
        this.codBarrasOrig = codBarrasOrig;
        this.vrOrigen = vrOrigen;
        this.idPaisOrigen = idPaisOrigen;
        this.vrCondicion = vrCondicion;
        this.controlado = controlado;
        this.cadFrio = cadFrio;
        this.controlarVcto = controlarVcto;
        this.compraUnica = compraUnica;
        this.enviarCaja = enviarCaja;
        this.idCtaContCp = idCtaContCp;
        this.idCtaContVtas = idCtaContVtas;
        this.precioVenta = precioVenta;
        this.costoPromedio = costoPromedio;
        this.costoRemplazo = costoRemplazo;
        this.porcRecargo = porcRecargo;
        this.precioBloqueado = precioBloqueado;
        this.idProveedor = idProveedor;
        this.archivoImg = archivoImg;
        this.idSitioProv = idSitioProv;
        this.idClaseProd = idClaseProd;
        this.segmento1 = segmento1;
        this.segmento2 = segmento2;
        this.segmento3 = segmento3;
        this.segmento4 = segmento4;
        this.segmento5 = segmento5;
        this.idCtaContCost = idCtaContCost;
        this.descAbribiada = descAbribiada;
        this.admiteCodNodef = admiteCodNodef;
        this.codigoBalanza = codigoBalanza;
        this.idCtaContProd = idCtaContProd;
        this.maxStkNegativo = maxStkNegativo;
        this.precioMayorista = precioMayorista;
        this.precioDist = precioDist;
        this.precioVentaMe = precioVentaMe;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.texto = texto;
        this.rucImport = rucImport;
        this.costoPromedioUs = costoPromedioUs;
        this.costoPromedioEx = costoPromedioEx;
        this.costoRemplazoEx = costoRemplazoEx;
        this.costoRemplazoUs = costoRemplazoUs;
        this.idUdmDefecto = idUdmDefecto;
        this.idTipoCodBarras = idTipoCodBarras;
        this.nroSerieGrupo = nroSerieGrupo;
        this.precioDiferido = precioDiferido;
        this.codigoUnificacion = codigoUnificacion;
        this.admiteVtaImporte = admiteVtaImporte;
        this.idProdEnvase = idProdEnvase;
        this.idEdmEnvase = idEdmEnvase;
        this.idLaboratorio = idLaboratorio;
        this.idPresentacion = idPresentacion;
        this.costoPromConIva = costoPromConIva;
        this.costoPromUsConIva = costoPromUsConIva;
        this.costoPromExConIva = costoPromExConIva;
        this.costoRemplazoConIva = costoRemplazoConIva;
        this.costoRemplazoUsConIva = costoRemplazoUsConIva;
        this.costoRemplazoExConIva = costoRemplazoExConIva;
        this.fechaUltInact = fechaUltInact;
        this.fechaUltReactiv = fechaUltReactiv;
        this.buscarEnCaja = buscarEnCaja;
        this.fechaModificacion = fechaModificacion;
        this.idNovedad = idNovedad;
        this.accion = accion;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getIdUnidad() {
        return IdUnidad;
    }

    public void setIdUnidad(int IdUnidad) {
        this.IdUnidad = IdUnidad;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategotia() {
        return idCategotia;
    }

    public void setIdCategotia(int idCategotia) {
        this.idCategotia = idCategotia;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getUsoInterno() {
        return usoInterno;
    }

    public void setUsoInterno(String usoInterno) {
        this.usoInterno = usoInterno;
    }

    public String getItemInventario() {
        return itemInventario;
    }

    public void setItemInventario(String itemInventario) {
        this.itemInventario = itemInventario;
    }

    public String getItemVentas() {
        return itemVentas;
    }

    public void setItemVentas(String itemVentas) {
        this.itemVentas = itemVentas;
    }

    public String getItemCompras() {
        return itemCompras;
    }

    public void setItemCompras(String itemCompras) {
        this.itemCompras = itemCompras;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getIdTipoImpuesto() {
        return idTipoImpuesto;
    }

    public void setIdTipoImpuesto(int idTipoImpuesto) {
        this.idTipoImpuesto = idTipoImpuesto;
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

    public int getIdDtoVentas() {
        return idDtoVentas;
    }

    public void setIdDtoVentas(int idDtoVentas) {
        this.idDtoVentas = idDtoVentas;
    }

    public int getIdTipoGrupo() {
        return idTipoGrupo;
    }

    public void setIdTipoGrupo(int idTipoGrupo) {
        this.idTipoGrupo = idTipoGrupo;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getCodBarrasAnt() {
        return codBarrasAnt;
    }

    public void setCodBarrasAnt(String codBarrasAnt) {
        this.codBarrasAnt = codBarrasAnt;
    }

    public String getCodBarrasOrig() {
        return codBarrasOrig;
    }

    public void setCodBarrasOrig(String codBarrasOrig) {
        this.codBarrasOrig = codBarrasOrig;
    }

    public String getVrOrigen() {
        return vrOrigen;
    }

    public void setVrOrigen(String vrOrigen) {
        this.vrOrigen = vrOrigen;
    }

    public int getIdPaisOrigen() {
        return idPaisOrigen;
    }

    public void setIdPaisOrigen(int idPaisOrigen) {
        this.idPaisOrigen = idPaisOrigen;
    }

    public String getVrCondicion() {
        return vrCondicion;
    }

    public void setVrCondicion(String vrCondicion) {
        this.vrCondicion = vrCondicion;
    }

    public String getControlado() {
        return controlado;
    }

    public void setControlado(String controlado) {
        this.controlado = controlado;
    }

    public String getCadFrio() {
        return cadFrio;
    }

    public void setCadFrio(String cadFrio) {
        this.cadFrio = cadFrio;
    }

    public String getControlarVcto() {
        return controlarVcto;
    }

    public void setControlarVcto(String controlarVcto) {
        this.controlarVcto = controlarVcto;
    }

    public String getCompraUnica() {
        return compraUnica;
    }

    public void setCompraUnica(String compraUnica) {
        this.compraUnica = compraUnica;
    }

    public String getEnviarCaja() {
        return enviarCaja;
    }

    public void setEnviarCaja(String enviarCaja) {
        this.enviarCaja = enviarCaja;
    }

    public int getIdCtaContCp() {
        return idCtaContCp;
    }

    public void setIdCtaContCp(int idCtaContCp) {
        this.idCtaContCp = idCtaContCp;
    }

    public int getIdCtaContVtas() {
        return idCtaContVtas;
    }

    public void setIdCtaContVtas(int idCtaContVtas) {
        this.idCtaContVtas = idCtaContVtas;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
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

    public double getPorcRecargo() {
        return porcRecargo;
    }

    public void setPorcRecargo(double porcRecargo) {
        this.porcRecargo = porcRecargo;
    }

    public String getPrecioBloqueado() {
        return precioBloqueado;
    }

    public void setPrecioBloqueado(String precioBloqueado) {
        this.precioBloqueado = precioBloqueado;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getArchivoImg() {
        return archivoImg;
    }

    public void setArchivoImg(String archivoImg) {
        this.archivoImg = archivoImg;
    }

    public int getIdSitioProv() {
        return idSitioProv;
    }

    public void setIdSitioProv(int idSitioProv) {
        this.idSitioProv = idSitioProv;
    }

    public int getIdClaseProd() {
        return idClaseProd;
    }

    public void setIdClaseProd(int idClaseProd) {
        this.idClaseProd = idClaseProd;
    }

    public String getSegmento1() {
        return segmento1;
    }

    public void setSegmento1(String segmento1) {
        this.segmento1 = segmento1;
    }

    public String getSegmento2() {
        return segmento2;
    }

    public void setSegmento2(String segmento2) {
        this.segmento2 = segmento2;
    }

    public String getSegmento3() {
        return segmento3;
    }

    public void setSegmento3(String segmento3) {
        this.segmento3 = segmento3;
    }

    public String getSegmento4() {
        return segmento4;
    }

    public void setSegmento4(String segmento4) {
        this.segmento4 = segmento4;
    }

    public String getSegmento5() {
        return segmento5;
    }

    public void setSegmento5(String segmento5) {
        this.segmento5 = segmento5;
    }

    public int getIdCtaContCost() {
        return idCtaContCost;
    }

    public void setIdCtaContCost(int idCtaContCost) {
        this.idCtaContCost = idCtaContCost;
    }

    public String getDescAbribiada() {
        return descAbribiada;
    }

    public void setDescAbribiada(String descAbribiada) {
        this.descAbribiada = descAbribiada;
    }

    public String getAdmiteCodNodef() {
        return admiteCodNodef;
    }

    public void setAdmiteCodNodef(String admiteCodNodef) {
        this.admiteCodNodef = admiteCodNodef;
    }

    public String getCodigoBalanza() {
        return codigoBalanza;
    }

    public void setCodigoBalanza(String codigoBalanza) {
        this.codigoBalanza = codigoBalanza;
    }

    public int getIdCtaContProd() {
        return idCtaContProd;
    }

    public void setIdCtaContProd(int idCtaContProd) {
        this.idCtaContProd = idCtaContProd;
    }

    public int getMaxStkNegativo() {
        return maxStkNegativo;
    }

    public void setMaxStkNegativo(int maxStkNegativo) {
        this.maxStkNegativo = maxStkNegativo;
    }

    public int getPrecioMayorista() {
        return precioMayorista;
    }

    public void setPrecioMayorista(int precioMayorista) {
        this.precioMayorista = precioMayorista;
    }

    public int getPrecioDist() {
        return precioDist;
    }

    public void setPrecioDist(int precioDist) {
        this.precioDist = precioDist;
    }

    public int getPrecioVentaMe() {
        return precioVentaMe;
    }

    public void setPrecioVentaMe(int precioVentaMe) {
        this.precioVentaMe = precioVentaMe;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRucImport() {
        return rucImport;
    }

    public void setRucImport(String rucImport) {
        this.rucImport = rucImport;
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

    public double getCostoRemplazoEx() {
        return costoRemplazoEx;
    }

    public void setCostoRemplazoEx(double costoRemplazoEx) {
        this.costoRemplazoEx = costoRemplazoEx;
    }

    public double getCostoRemplazoUs() {
        return costoRemplazoUs;
    }

    public void setCostoRemplazoUs(double costoRemplazoUs) {
        this.costoRemplazoUs = costoRemplazoUs;
    }

    public int getIdUdmDefecto() {
        return idUdmDefecto;
    }

    public void setIdUdmDefecto(int idUdmDefecto) {
        this.idUdmDefecto = idUdmDefecto;
    }

    public int getIdTipoCodBarras() {
        return idTipoCodBarras;
    }

    public void setIdTipoCodBarras(int idTipoCodBarras) {
        this.idTipoCodBarras = idTipoCodBarras;
    }

    public int getNroSerieGrupo() {
        return nroSerieGrupo;
    }

    public void setNroSerieGrupo(int nroSerieGrupo) {
        this.nroSerieGrupo = nroSerieGrupo;
    }

    public String getPrecioDiferido() {
        return precioDiferido;
    }

    public void setPrecioDiferido(String precioDiferido) {
        this.precioDiferido = precioDiferido;
    }

    public String getCodigoUnificacion() {
        return codigoUnificacion;
    }

    public void setCodigoUnificacion(String codigoUnificacion) {
        this.codigoUnificacion = codigoUnificacion;
    }

    public String getAdmiteVtaImporte() {
        return admiteVtaImporte;
    }

    public void setAdmiteVtaImporte(String admiteVtaImporte) {
        this.admiteVtaImporte = admiteVtaImporte;
    }

    public int getIdProdEnvase() {
        return idProdEnvase;
    }

    public void setIdProdEnvase(int idProdEnvase) {
        this.idProdEnvase = idProdEnvase;
    }

    public int getIdEdmEnvase() {
        return idEdmEnvase;
    }

    public void setIdEdmEnvase(int idEdmEnvase) {
        this.idEdmEnvase = idEdmEnvase;
    }

    public int getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(int idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public int getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(int idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public double getCostoPromConIva() {
        return costoPromConIva;
    }

    public void setCostoPromConIva(double costoPromConIva) {
        this.costoPromConIva = costoPromConIva;
    }

    public double getCostoPromUsConIva() {
        return costoPromUsConIva;
    }

    public void setCostoPromUsConIva(double costoPromUsConIva) {
        this.costoPromUsConIva = costoPromUsConIva;
    }

    public double getCostoPromExConIva() {
        return costoPromExConIva;
    }

    public void setCostoPromExConIva(double costoPromExConIva) {
        this.costoPromExConIva = costoPromExConIva;
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

    public Timestamp getFechaUltInact() {
        return fechaUltInact;
    }

    public void setFechaUltInact(Timestamp fechaUltInact) {
        this.fechaUltInact = fechaUltInact;
    }

    public Timestamp getFechaUltReactiv() {
        return fechaUltReactiv;
    }

    public void setFechaUltReactiv(Timestamp fechaUltReactiv) {
        this.fechaUltReactiv = fechaUltReactiv;
    }

    public String getBuscarEnCaja() {
        return buscarEnCaja;
    }

    public void setBuscarEnCaja(String buscarEnCaja) {
        this.buscarEnCaja = buscarEnCaja;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public int getIdNovedad(){
        return idNovedad;
    }
    
    public void setIdNovedad(int idNovedad){
        this.idNovedad = idNovedad;
    }
    
    public String getAccion(){
        return accion;
    }
    
    public void setAccion(String accion){
        this.accion = accion;
    }
    
}
