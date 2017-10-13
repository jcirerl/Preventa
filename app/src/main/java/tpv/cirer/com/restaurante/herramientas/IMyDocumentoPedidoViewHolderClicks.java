package tpv.cirer.com.restaurante.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 29/10/2016.
 */

public interface IMyDocumentoPedidoViewHolderClicks {
    void onPotato(View caller,
                         String campoDocumentoPedido,
                         String iconDocumentoPedido,
                         String idDocumentoPedido,
                         String pedidoDocumentoPedido,
                         String comensalesDocumentoPedido,
                         String mesaDocumentoPedido,
                         String estadoDocumentoPedido,
                         String empleadoDocumentoPedido,
                         String cajaDocumentoPedido,
                         String turnoDocumentoPedido,
                         String obsDocumentoPedido,
                         String lineasDocumentoPedido);

    void onDelete(Button callerButton,
                         String idDocumentoPedido,
                         String estadoDocumentoPedido,
                         String pedidoDocumentoPedido);

    void onUpdate(Button callerButton,
                         String idDocumentoPedido,
                         String estadoDocumentoPedido,
                         String mesaDocumentoPedido,
                         String pedidoDocumentoPedido );

    void onInvoice(Button callerButton,
                         String pedidoDocumentoPedido,
                         String estadoDocumentoPedido );
    void onTomato(ImageView callerImage);
}
