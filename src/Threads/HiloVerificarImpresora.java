package Threads;

import Controladores.ContParamAplicacion;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelos.DtsImpresora;
import Utils.Utilidades;

public class HiloVerificarImpresora implements Runnable {

    DtsImpresora dts;

    public HiloVerificarImpresora(DtsImpresora dt) {
        this.dts = dt;
    }

    @Override
    public void run() {
        int i = 8;
        String aChar = (new Character((char) i)).toString();

        try {
            this.dts.setVerificado(true);

            FileOutputStream os = new FileOutputStream(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
            PrintStream ps = new PrintStream(os);
            ps.print(aChar);
            ps.close();
            os.close();

            this.dts.setHabilitado(true);
        } catch (IOException e) {
            this.dts.setVerificado(true);
            this.dts.setHabilitado(false);
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
