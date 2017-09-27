package tpv.cirer.com.marivent.easyredsys;

/**
 * Created by JUAN on 10/09/2017.
 */

public class AppConfigImpl {

}
/*
public class AppConfigImpl implements AppConfig {

    static String getMerchantCode() {
        return "061978060"; //Este es el código de comercio
    }

    static String getTerminal() {
        return "001"; //Este es el identificador de la terminal
    }

    static String getSecretKey() {
        return "sq7HjrUOBfKmC576ILgskD5srU870gJ7"; //Esta es la clave secreta de tu pasarela en PRODUCCIÓN
        //La clave de preproducción es igual para todas las pasarelas por lo que no tienes que indicarla
    }

    static boolean isTestMode() {
        return true; //Establécelo a false cuando quieras hacer funcionar la pasarela en el entorno de producción
    }

    @Override
    public void saveNotification(Notification notification) {
        // Pon aquí lo que quieras hacer con la notificación. Seguramente, almacenar en tu base de datos que el pedido ha sido pagado
        // Las notificaciones aquí recibidas ya han pasado todas las comprobaciones de seguridad y son válidas
        Log.i("COBRADO","OK");

    }

}
*/