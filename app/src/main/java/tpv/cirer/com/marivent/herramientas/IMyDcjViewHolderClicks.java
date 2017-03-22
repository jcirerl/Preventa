package tpv.cirer.com.marivent.herramientas;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by JUAN on 21/11/2016.
 */

public interface IMyDcjViewHolderClicks {
    void onPotato(View caller,
                  String campoDcj,
                  String iconDcj,
                  String idDcj,
                  String turnoDcj,
                  String fechaaperturaDcj,
                  String fechaopenDcj,
                  String fechacloseDcj,
                  String saldoinicioDcj,
                  String saldofinalDcj,
                  String aperturaDcj);

    void onOpen(Button callerButton, String idDcj);

    void onClose(Button callerButton, String idDcj);

    void onPrint(Button callerButton,
                 String idDcj,
                 String aperturaDcj);

    void onTomato(ImageView callerImage);

}
