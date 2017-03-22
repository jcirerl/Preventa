package tpv.cirer.com.marivent.herramientas;

import java.util.Observable;

/**
 * Created by JUAN on 12/10/2016.
 */

public class FilterManager extends Observable {
    private String query;

    public void setQuery(String query) {
        this.query = query;
        setChanged();
        notifyObservers();
    }

    public String getQuery() {
        return query;
    }
}
