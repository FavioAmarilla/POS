package Utils;

public class FormatosTicket {

    private final int columnas = 39;

    public String separador() {
        String linea = "";

        for (int i = 0; i < columnas; i++) {
            linea = linea + "-";
        }

        return linea;
    }

    public String lineaEscritura(String texto) {
        int textLenght = texto.length();
        String linea = "";

        for (int i = 0; i < columnas - textLenght; i++) {
            linea = linea + ".";
        }

        return texto + linea;
    }

    public String separadorDoble() {
        String linea = "";

        for (int i = 0; i < columnas; i++) {
            linea = linea + "=";
        }

        return linea;
    }

    public String centrar(String texto) {
        int longitud = texto.length();
        int diferencia;
        String espacio = "";
        String linea = "";

        diferencia = columnas - longitud;
        for (int i = 0; i < diferencia / 2; i++) {
            espacio = espacio + " ";
        }

        linea = espacio + texto + espacio;
        return linea;
    }

    public String item(String cant, String pre, String tot) {
        int desde = 0;
        String espacio1 = "";
        String espacio2 = "";
        String detalle = "";
        String linea = "";

        for (int i = 1; i <= 9; i++) {
            espacio1 = espacio1 + " ";
        }

        desde = espacio1.length() + cant.length();
        for (int i = desde; i <= 17; i++) {
            espacio2 = espacio2 + " ";
        }

        detalle = espacio1 + cant + espacio2 + pre;
        linea = dobleColumna(detalle, tot);

        return linea;
    }

    public String dobleColumna(String texto, String total) {
        int longitud = (texto.length() + total.length());
        int diferencia;
        String espacio = "";
        String linea = "";

        diferencia = columnas - longitud;
        for (int i = 0; i < diferencia; i++) {
            espacio = espacio + " ";
        }

        linea = texto + espacio + total;
        return linea;
    }

    public String mediosPago(String texto, String texto1) {
        int longitud = (texto.length() + texto1.length());
        int diferencia = 0;
        String espacio = "";
        String linea = "";

        diferencia = 20 - longitud;

        for (int i = 0; i < diferencia; i++) {
            espacio = espacio + " ";
        }
        linea = texto + espacio + texto1;

        return linea;
    }

}
