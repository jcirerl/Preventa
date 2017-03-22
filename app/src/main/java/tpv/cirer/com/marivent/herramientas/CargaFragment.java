package tpv.cirer.com.marivent.herramientas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by JUAN on 01/01/2017.
 */

public class CargaFragment {
    private Fragment fragmentGenerico;
    private FragmentManager fragmentManager;
    public CargaFragment(){}

    public CargaFragment(Fragment fragmentgenerico, FragmentManager fragmentmanager){
        this.fragmentGenerico = fragmentgenerico;
        this.fragmentManager = fragmentmanager;
    }
    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
    public Fragment getFragment() {
        return fragmentGenerico;
    }
    public void setTransaction(int layout) {
        fragmentManager
                .beginTransaction()
                .replace(layout, fragmentGenerico,fragmentGenerico.getClass().getName())
                //                   .addToBackStack(fragmentoGenerico.getClass().getName())
                .commit();
    }
    public void setTransactionToBackStack(int layout) {
        fragmentManager
                .beginTransaction()
                .replace(layout, fragmentGenerico,fragmentGenerico.getClass().getName())
                .addToBackStack(null)
                .commit();
    }
    public void setTransactioncommitAllowingStateLoss(int layout) {
        fragmentManager
                .beginTransaction()
                .replace(layout, fragmentGenerico,fragmentGenerico.getClass().getName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
