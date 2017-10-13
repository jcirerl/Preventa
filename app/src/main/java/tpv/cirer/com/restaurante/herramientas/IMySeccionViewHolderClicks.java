package tpv.cirer.com.restaurante.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 06/11/2016.
 */

public interface IMySeccionViewHolderClicks {
    void onPotato(View caller,
                  String idSeccion,
                  String nombreSeccion,
                  String codigoSeccion,
                  String Icon,
                  String Ivaincluido);

    void onOpen(Button callerButton, String idSeccion);

    void onClose(Button callerButton, String idSeccion);

    void onTomato(ImageView callerImage);

}
