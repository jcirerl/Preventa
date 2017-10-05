package tpv.cirer.com.marivent.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 06/10/2016.
 */

public interface IMyLineaDocumentoPedidoViewHolderClicks {
    void onPotato(View caller,
                  ImageView imageLineaDocumentoPedido,
                  String idLineaDocumentoPedido,
                  String nombreLineaDocumentoPedido,
                  String articuloLineaDocumentoPedido,
                  String iconLineaDocumentoPedido,
                  String cantLineaDocumentoPedido,
                  String obsLineaDocumentoPedido,
                  String swfacturaLineaDocumentoPedido,
                  String individualLineaDocumentoPedido,
                  String tipoplatoLineaDocumentoPedido,
                  String nombreplatoLineaDocumentoPedido);
    void onAdd(Button callerButton,
               String idLineaDocumentoPedido,
               String swfacturaLineaDocumentoPedido,
               String individualLineaDocumentoPedido);
    void onMinus(Button callerButton,
                 String idLineaDocumentoPedido,
                 String cantLineaDocumentoPedido,
                 String swfacturaLineaDocumentoPedido,
                 String individualLineaDocumentoPedido);
    void onActivo(String activo);

    void onTomato(ImageView callerImage);
    //    public void onPotato(View caller);

}
