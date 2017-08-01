package tpv.cirer.com.marivent.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 08/11/2016.
 */

public interface IMyDocumentoFacturaViewHolderClicks {
    void onPotato(View caller,
                  ImageView imageDocumentoFactura,
                  String campoDocumentoFactura,
                  String iconDocumentoFactura,
                  String idDocumentoFactura,
                  String serieDocumentoFactura,
                  String facturaDocumentoFactura,
                  String mesaDocumentoFactura,
                  String estadoDocumentoFactura,
                  String empleadoDocumentoFactura,
                  String cajaDocumentoFactura,
                  String turnoDocumentoFactura,
                  String obsDocumentoFactura,
                  String lineasDocumentoFactura);

    void onDelete(Button callerButton,
                  ImageView imageDocumentoFactura,
                  String idDocumentoFactura,
                  String estadoDocumentoFactura,
                  String serieDocumentoFactura,
                  String facturaDocumentoFactura);

    void onUpdate(Button callerButton,
                  ImageView imageDocumentoFactura,
                  String idDocumentoFactura,
                  String estadoDocumentoFactura,
                  String mesaDocumentoFactura,
                  String serieDocumentoFactura,
                  String facturaDocumentoFactura );

    void onCobro (Button callerButton,
                  ImageView imageDocumentoFactura,
                  String idDocumentoFactura,
                  String estadoDocumentoFactura,
                  String serieDocumentoFactura,
                  String facturaDocumentoFactura);
    void onFactura (Button callerButton,
                  ImageView imageDocumentoFactura,
                  String idDocumentoFactura,
                  String estadoDocumentoFactura,
                  String serieDocumentoFactura,
                  String facturaDocumentoFactura);
    void onTomato(ImageView callerImage);

}
