package Modelos;

public class DtsImpresora {

    protected boolean verificado;
    protected boolean habilitado;

    public DtsImpresora() {
    }

    public synchronized boolean isVerificado() {
        return verificado;
    }

    public synchronized void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public synchronized boolean isHabilitado() {
        return habilitado;
    }

    public synchronized void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

}
