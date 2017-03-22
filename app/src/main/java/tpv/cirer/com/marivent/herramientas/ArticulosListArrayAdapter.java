package tpv.cirer.com.marivent.herramientas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.Articulos;

import java.util.List;

/**
 * Created by JUAN on 31/01/2017.
 */

public class ArticulosListArrayAdapter  extends ArrayAdapter<Articulos> {

    private final List<Articulos> list;
    private final Activity context;

    static class ViewHolder {
        protected TextView name;
        protected ImageView imagen;
    }

    public ArticulosListArrayAdapter(Activity context, List<Articulos> list) {
        super(context, R.layout.activity_articuloscode_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_articuloscode_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.imagen = (ImageView) view.findViewById(R.id.imagen);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.imagen.setImageDrawable(list.get(position).getImagen());
        return view;
    }
}
