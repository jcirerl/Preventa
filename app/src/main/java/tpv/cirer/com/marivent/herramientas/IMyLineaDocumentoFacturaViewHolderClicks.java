package tpv.cirer.com.marivent.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 09/11/2016.
 */

public interface IMyLineaDocumentoFacturaViewHolderClicks {
    void onPotato(View caller,
                  ImageView imageLineaDocumentoFactura,
                  String campoLineaDocumentoFactura,
                  String idLineaDocumentoFactura,
                  String nombreLineaDocumentoFactura,
                  String articuloLineaDocumentoFactura,
                  String iconLineaDocumentoFactura,
                  String cantLineaDocumentoFactura,
                  String preuLineaDocumentoFactura,
                  String importeLineaDocumentoFactura,
                  String tiva_idLineaDocumentoFactura);
    void onAdd(Button callerButton,
               String idLineaDocumentoFactura,
               String cantLineaDocumentoFactura);
    void onMinus(Button callerButton,
                 String idLineaDocumentoFactura,
                 String cantLineaDocumentoFactura);

    void onTomato(ImageView callerImage);
    //    public void onPotato(View caller);

}
