package tpv.cirer.com.restaurante.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 07/11/2016.
 */

public interface IMyCajaViewHolderClicks {
    void onPotato(View caller,
                  String idCaja,
                  String nombreCaja,
                  String codigoCaja,
                  String Icon);

    void onOpen(Button callerButton, String idCaja);

    void onClose(Button callerButton, String idCaja);

    void onTomato(ImageView callerImage);

}
