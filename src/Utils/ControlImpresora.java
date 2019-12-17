package Utils;

import Controladores.ContParamAplicacion;
import Modelos.DtsImpresora;
import Threads.HiloVerificarImpresora;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlImpresora {

    DtsImpresora dtsImpresora = new DtsImpresora();
    private final long PAUSA_PARALELO = 50;
    private final long PAUSA_SERIAL = 100;

    public void verificarImpresora() {
        HiloVerificarImpresora testImpre = new HiloVerificarImpresora(this.dtsImpresora);
        Thread thread = new Thread((Runnable) testImpre);
        thread.setPriority(10);
        thread.start();
    }

    public boolean habilitado() {
        try {
            this.dtsImpresora.setVerificado(false);
            this.dtsImpresora.setHabilitado(false);

            verificarImpresora();
            int i = 0;
            long demoraTestImpresion = 0;
            if (ContParamAplicacion.CAJA_VR_PUERTO_IMPR.equals("PA")) {
                demoraTestImpresion = PAUSA_PARALELO;
            }
            if (ContParamAplicacion.CAJA_VR_PUERTO_IMPR.equals("SE")) {
                demoraTestImpresion = PAUSA_SERIAL;
            }

            while (!this.dtsImpresora.isVerificado() && i <= 10) {
                Thread.sleep(demoraTestImpresion);
                i++;
            }

            return this.dtsImpresora.isHabilitado();
        } catch (InterruptedException e) {
            ControlMensajes.error("Validacion() " + e, "Impresora");
            Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public static boolean imprimir(String texto) {
        ControlImpresora impresora = new ControlImpresora();
        if (!impresora.habilitado() || !impresora.habilitado() || !impresora.habilitado()) {
            ControlMensajes.error("Impresora no disponible", "Impresora");
            return false;
        }
        try {
            FileOutputStream os = new FileOutputStream(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
            PrintStream ps = new PrintStream(os);

            ps.print(texto);
            os.close();
            ps.close();
        } catch (FileNotFoundException ex) {
            ControlMensajes.error("Impresion() " + ex, "Impresora");
            Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ControlMensajes.error("Impresion() " + ex, "Impresora");
            Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static void abrirGaveta() {
        if (ContParamAplicacion.CAJA_USAR_GAVETA.equals("S")) {
            try {
                FileWriter fw = new FileWriter(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
                fw.write(27);
                fw.write(112);
                fw.write(0);
                fw.write(150);
                fw.write(150);
                fw.write(0);
                fw.close();
            } catch (IOException ex) {
                ControlMensajes.error("Gaveta() " + ex, "Impresora");
                Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void cortarPapel() {
        if (ContParamAplicacion.CAJA_USAR_CORTE_PAPEL.equals("S")) {
            try {
                FileWriter fw = new FileWriter(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
                char[] CORTAR_PAPEL = new char[]{0x1B, 'm'}; // codigo que corta el papel
                fw.write(CORTAR_PAPEL); // se manda a la impresora
                fw.close();
            } catch (IOException ex) {
                ControlMensajes.error("CortarPapel() " + ex, "Impresora");
                Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
