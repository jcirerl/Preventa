package tpv.cirer.com.marivent.herramientas;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by JUAN on 06/09/2017.
 */

public interface IMyArticulosNewViewHolderClicks {
    void onPotato(View caller,
                  ImageView imageArticulo,
                  String action,
                  String codigoArticulo,
                  String nombreArticulo,
                  String positionArticulo);

}
