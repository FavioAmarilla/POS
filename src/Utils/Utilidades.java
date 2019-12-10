package Utils;

import Pos.POS;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilidades {

    public Utilidades() {

    }

    public static String getFecha(String formato) {
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formatfecha = new SimpleDateFormat(formato);
        return formatfecha.format(fecha);
    }

    public static String getHostname() {
        try {
            InetAddress direccion = InetAddress.getLocalHost();
//            return "CAJA2";
            return direccion.getHostName().toUpperCase();
        } catch (UnknownHostException ex) {
            ControlMensajes.error("hostname: " + ex, Utilidades.class.getName());
            return null;
        }
    }

    public static boolean getPing() {
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(POS.COMANDO_PING + " " + POS.IP_SERVIDOR + " " + POS.PARAMETROS_PING);

            return p.waitFor() == 0;
        } catch (IOException | InterruptedException t) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, t);
            return false;
        }
    }
}
