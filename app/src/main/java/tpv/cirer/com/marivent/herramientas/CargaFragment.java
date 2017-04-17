package tpv.cirer.com.marivent.herramientas;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;

import tpv.cirer.com.marivent.R;

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
    public void setTransactionToBackStackTransition(Context mContext, int layout, Fragment current, ImageView ivProfile) {
        // Check that the device is running lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Inflate transitions to apply
            Transition changeTransform = TransitionInflater.from(mContext).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(mContext).
                    inflateTransition(android.R.transition.fade);

            // Setup exit32 transition on first fragment
            current.setSharedElementReturnTransition(changeTransform);
            current.setExitTransition(explodeTransform);

            // Setup enter transition on second fragment
            fragmentGenerico.setSharedElementEnterTransition(changeTransform);
            fragmentGenerico.setEnterTransition(explodeTransform);

            // Find the shared element (in Fragment A)
//            ImageView ivProfile = (ImageView) layout.findViewById(R.id.icon);

            // Add second fragment by replacing first
            fragmentManager
                    .beginTransaction()
                    .replace(layout, fragmentGenerico,fragmentGenerico.getClass().getName())
                    .addToBackStack("transaction")
                    .addSharedElement(ivProfile, "profile")
                    .commit();
        } else {
            // Code to run on older devices
            fragmentManager
                    .beginTransaction()
                    .replace(layout, fragmentGenerico,fragmentGenerico.getClass().getName())
                    .addToBackStack(null)
                    .commit();
        }
    }
    public void setTransactioncommitAllowingStateLoss(int layout) {
        fragmentManager
                .beginTransaction()
                .replace(layout, fragmentGenerico,fragmentGenerico.getClass().getName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
