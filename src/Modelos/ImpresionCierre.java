package Modelos;

public class ImpresionCierre {
    int totalBruto, totalCancelacion,totalDevolucion;
    int totalAnulacion, totalDescuentos;
    int clientes, articulos, promArtClie;
    int totalGravado, totalImpuesto, totalExento;
    int redondeoDecimal, ticketInicial, ticketFinal;
    int reposicion;

    public ImpresionCierre() {
    }

    public ImpresionCierre(int totalBruto, int totalCancelacion, int totalDevolucion, int totalAnulacion, int totalDescuentos, int clientes, int articulos, int promArtClie, int totalGravado, int totalImpuesto, int totalExento, int redondeoDecimal, int ticketInicial, int ticketFinal, int reposicion) {
        this.totalBruto = totalBruto;
        this.totalCancelacion = totalCancelacion;
        this.totalDevolucion = totalDevolucion;
        this.totalAnulacion = totalAnulacion;
        this.totalDescuentos = totalDescuentos;
        this.clientes = clientes;
        this.articulos = articulos;
        this.promArtClie = promArtClie;
        this.totalGravado = totalGravado;
        this.totalImpuesto = totalImpuesto;
        this.totalExento = totalExento;
        this.redondeoDecimal = redondeoDecimal;
        this.ticketInicial = ticketInicial;
        this.ticketFinal = ticketFinal;
        this.reposicion = reposicion;
    }

    public int getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(int totalBruto) {
        this.totalBruto = totalBruto;
    }

    public int getTotalCancelacion() {
        return totalCancelacion;
    }

    public void setTotalCancelacion(int totalCancelacion) {
        this.totalCancelacion = totalCancelacion;
    }

    public int getTotalDevolucion() {
        return totalDevolucion;
    }

    public void setTotalDevolucion(int totalDevolucion) {
        this.totalDevolucion = totalDevolucion;
    }

    public int getTotalAnulacion() {
        return totalAnulacion;
    }

    public void setTotalAnulacion(int totalAnulacion) {
        this.totalAnulacion = totalAnulacion;
    }

    public int getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(int totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public int getClientes() {
        return clientes;
    }

    public void setClientes(int clientes) {
        this.clientes = clientes;
    }

    public int getArticulos() {
        return articulos;
    }

    public void setArticulos(int articulos) {
        this.articulos = articulos;
    }

    public int getPromArtClie() {
        return promArtClie;
    }

    public void setPromArtClie(int promArtClie) {
        this.promArtClie = promArtClie;
    }

    public int getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(int totalGravado) {
        this.totalGravado = totalGravado;
    }

    public int getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(int totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public int getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(int totalExento) {
        this.totalExento = totalExento;
    }

    public int getRedondeoDecimal() {
        return redondeoDecimal;
    }

    public void setRedondeoDecimal(int redondeoDecimal) {
        this.redondeoDecimal = redondeoDecimal;
    }

    public int getTicketInicial() {
        return ticketInicial;
    }

    public void setTicketInicial(int ticketInicial) {
        this.ticketInicial = ticketInicial;
    }

    public int getTicketFinal() {
        return ticketFinal;
    }

    public void setTicketFinal(int ticketFinal) {
        this.ticketFinal = ticketFinal;
    }

    public int getReposicion() {
        return reposicion;
    }

    public void setReposicion(int reposicion) {
        this.reposicion = reposicion;
    }
    
    
   
    
}
