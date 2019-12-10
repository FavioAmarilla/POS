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
    private final int PAUSA_PARALELO = 50;
    private final int PAUSA_SERIAL = 100;

    public void verificarImpresora() {
        HiloVerificarImpresora testImpre = new HiloVerificarImpresora(dtsImpresora);
        Thread thread = new Thread(testImpre);
        thread.setPriority(10);
        thread.start();
    }

    public boolean habilitado() {
        if (!ContParamAplicacion.CAJA_VR_PUERTO_IMPR.equals("PA") && !ContParamAplicacion.CAJA_VR_PUERTO_IMPR.equals("SE")) {
            ControlMensajes.error("Puerto de impresion no definido", "Impresora");
            return false;
        }
        try {
            verificarImpresora();
            int i = 0;

            dtsImpresora.setVerificado(false);
            dtsImpresora.setHabilitado(false);

            while ((!dtsImpresora.isVerificado()) && (i <= 10)) {
                if (ContParamAplicacion.CAJA_VR_PUERTO_IMPR.equals("PA")) {
                    Thread.sleep(PAUSA_PARALELO);
                }
                if (ContParamAplicacion.CAJA_VR_PUERTO_IMPR.equals("SE")) {
                    Thread.sleep(PAUSA_SERIAL);
                }
                i++;
            }
            return dtsImpresora.isHabilitado();
        } catch (InterruptedException e) {
            Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public static boolean imprimir(String texto) {
        ControlImpresora impresora = new ControlImpresora();
        if (!impresora.habilitado()) {
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
            Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static boolean abrirGaveta() {
        System.out.println(ContParamAplicacion.CAJA_USAR_GAVETA);
        System.out.println(ContParamAplicacion.CAJA_PUERTO_IMPRESION);

        if (ContParamAplicacion.CAJA_USAR_GAVETA.equals("S")) {
            ControlImpresora impresora = new ControlImpresora();
            if (!impresora.habilitado()) {
                ControlMensajes.error("Gaveta no disponible", "Impresora");
                return false;
            }

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
                Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public static void cortarPapel() {
        if (ContParamAplicacion.CAJA_USAR_CORTE_PAPEL.equals("S")) {
            try {
                FileWriter fw = new FileWriter(ContParamAplicacion.CAJA_PUERTO_IMPRESION);
                char[] CORTAR_PAPEL = new char[]{0x1B, 'm'}; // codigo que corta el papel
                fw.write(CORTAR_PAPEL); // se manda a la impresora
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ControlImpresora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
