package tpv.cirer.com.marivent.herramientas;

/**
 * Created by JUAN on 11/02/2017.
 */

public class Formatting {
    public static String rightPadding(String str, int num) {
        return String.format("%1$-" + num + "s", str);
    }
    public static String rightPadZeros(String str, int num) {
        return String.format("%1$-" + num + "s", str).replace(' ', '0');
    }

}
