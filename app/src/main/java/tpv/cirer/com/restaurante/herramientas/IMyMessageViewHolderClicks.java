package tpv.cirer.com.restaurante.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 31/03/2017.
 */

public interface IMyMessageViewHolderClicks {
    void onPotato(View caller,
                         String idMessage,
                         String mesaMessage,
                         String comensalesMessage,
                         String creadoMessage,
                         String activoMessage,
                         String cajaMessage);

    void onUpdate(Button callerButton,
                         String idMessage,
                         String activoMessage,
                         String comensalesMessage,
                         String userMessage,
                         String mesaMessage);

    void onDelete(Button callerButton,
                         String idMessage,
                         String activoMessage);

    void onTomato(ImageView callerImage);

}
