package Pos;

import Vistas.FrmLogin;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class POS {

    public static final PropertyResourceBundle prbConfig = (PropertyResourceBundle) PropertyResourceBundle.getBundle("Config");
    public static final PropertyResourceBundle prbLinux = (PropertyResourceBundle) PropertyResourceBundle.getBundle("Linux");
    public static final PropertyResourceBundle prbWindows = (PropertyResourceBundle) PropertyResourceBundle.getBundle("Windows");

    public static String SISTEMA_OPERATIVO = "";
    public static String IP_SERVIDOR = "";
    public static String IP_LOCAL = "";
    public static String DB_NAME = "";
    public static String DB_USER = "";
    public static String DB_PASS = "";
    public static String VERSION_SOFTWARE = "";
    public static String LOOK_AND_FEEL = "";
    public static String IMG_LOGO_MARCA = "";
    public static String IMG_LOGO_LOGIN = "";
    public static String IMG_LOGO_MENU = "";
    public static String IMG_LOGO_POS = "";
    public static String CARPETA_IMG_PRODUCTOS = "";
    public static String COMANDO_PING = "";
    public static String PARAMETROS_PING = "";

    static void parametrosPOS() {
        SISTEMA_OPERATIVO = prbConfig.getString("SISTEMA_OPERATIVO");
        IP_SERVIDOR = prbConfig.getString("IP_SERVIDOR");
        IP_LOCAL = prbConfig.getString("IP_LOCAL");
        DB_NAME = prbConfig.getString("DB_NAME");
        DB_USER = prbConfig.getString("DB_USER");
        DB_PASS = prbConfig.getString("DB_PASS");
        VERSION_SOFTWARE = prbConfig.getString("VERSION_SOFTWARE");

        if (SISTEMA_OPERATIVO.equals("Linux")) {
            LOOK_AND_FEEL = prbLinux.getString("LOOK_AND_FEEL");
            IMG_LOGO_MARCA = prbLinux.getString("IMG_LOGO_MARCA");
            IMG_LOGO_LOGIN = prbLinux.getString("IMG_LOGO_LOGIN");
            IMG_LOGO_MENU = prbLinux.getString("IMG_LOGO_MENU");
            IMG_LOGO_POS = prbLinux.getString("IMG_LOGO_POS");
            CARPETA_IMG_PRODUCTOS = prbLinux.getString("CARPETA_IMG_PRODUCTOS");
            COMANDO_PING = prbLinux.getString("COMANDO_PING");
            PARAMETROS_PING = prbLinux.getString("PARAMETROS_PING");
        }

        if (SISTEMA_OPERATIVO.equals("Windows")) {
            LOOK_AND_FEEL = prbWindows.getString("LOOK_AND_FEEL");
            IMG_LOGO_MARCA = prbWindows.getString("IMG_LOGO_MARCA");
            IMG_LOGO_LOGIN = prbWindows.getString("IMG_LOGO_LOGIN");
            IMG_LOGO_MENU = prbWindows.getString("IMG_LOGO_MENU");
            IMG_LOGO_POS = prbWindows.getString("IMG_LOGO_POS");
            CARPETA_IMG_PRODUCTOS = prbWindows.getString("CARPETA_IMG_PRODUCTOS");
            COMANDO_PING = prbWindows.getString("COMANDO_PING");
            PARAMETROS_PING = prbWindows.getString("PARAMETROS_PING");
        }
        
        System.out.println("CONECTADO A: " + IP_SERVIDOR);
    }

    public static void main(String[] args) {
        parametrosPOS();

        try {
            UIManager.setLookAndFeel(LOOK_AND_FEEL);
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        }

        FrmLogin frm = new FrmLogin();
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
}
