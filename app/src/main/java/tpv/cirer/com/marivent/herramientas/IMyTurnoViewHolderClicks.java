package tpv.cirer.com.marivent.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 08/11/2016.
 */

public interface IMyTurnoViewHolderClicks {
    void onPotato(View caller,
                  String idTurno,
                  String nombreTurno,
                  String codigoTurno,
                  String Icon);

    void onOpen(Button callerButton, String idTurno);

    void onClose(Button callerButton, String idTurno);

    void onTomato(ImageView callerImage);

}
