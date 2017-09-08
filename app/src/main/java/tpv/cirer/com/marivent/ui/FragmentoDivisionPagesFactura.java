package tpv.cirer.com.marivent.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;

/**
 * Created by JUAN on 09/11/2016.
 */

public class FragmentoDivisionPagesFactura
        extends Fragment {
    private static final String TAG = "XXXXXXX";
    private static final String TAG_ONE = "one";
    private static final String TAG_TWO = "two";
    private static final int COLOR_ONE = 0xFF00FF00;
    private static final int COLOR_TWO = 0xFF0000FF;
    private static final String KEY_PAGE = "selected_page";


    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;

    private ViewPager viewPager;
    private int lastPage; // If landscape, page from portrait.

    int nFactura;
    int nId;
    String sSerie;
    String cEstado;
    String cMesa;
    View rootView;
    FloatingActionButton btnFab;
    public FragmentoDivisionPagesFactura() {
    }

    private static FragmentoDivisionPagesFactura PagesFactura = null;

    public static FragmentoDivisionPagesFactura newInstance(int id, String estado, String mesa, String serie, String factura) {
        FragmentoDivisionPagesFactura PagesFactura = new FragmentoDivisionPagesFactura();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        args.putString("ESTADO", estado);
        args.putString("MESA", mesa);
        args.putString("SERIE", serie);
        args.putInt("FACTURA", Integer.parseInt(factura));
        PagesFactura.setArguments(args);
        return PagesFactura;
    }

    public static FragmentoDivisionPagesFactura getInstance(){
        if(PagesFactura == null){
            PagesFactura = new FragmentoDivisionPagesFactura();
        }
        return PagesFactura;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nFactura = getArguments().getInt("FACTURA", 0);
        nId = getArguments().getInt("ID", 0);
        sSerie = getArguments().getString("SERIE", "");
        cEstado = getArguments().getString("ESTADO", "");
        cMesa = getArguments().getString("MESA", "");

        /// Poner Datos CABECERA
        Filtro.setCabecera(false);
        ((ActividadPrincipal) getActivity()).setCabecera(((ActividadPrincipal) getActivity()).getPalabras("Division")+" "+((ActividadPrincipal) getActivity()).getPalabras("Factura")+": "+Integer.toString(nFactura)+" "+((ActividadPrincipal) getActivity()).getPalabras(cEstado)+" "+((ActividadPrincipal) getActivity()).getPalabras("Mesa")+": "+cMesa,0.00,nFactura);

        if (savedInstanceState != null) {
            lastPage = savedInstanceState.getInt(KEY_PAGE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.division_activity, container, false);
        }


//        setContentView(R.layout.example_activity);

        FragmentManager fragmentManager = getChildFragmentManager();

        Fragment one = fragmentManager.findFragmentByTag(TAG_ONE);
        Fragment two = fragmentManager.findFragmentByTag(TAG_TWO);

        FragmentTransaction remove = fragmentManager.beginTransaction();
        if (one == null) {
//            one = ColorFragment.newInstance(COLOR_ONE);
            one = FragmentoDivisionLineaDocumentoFactura.newInstance(nId,cEstado,sSerie,nFactura);
//            one = new FragmentoLineaDocumentoFactura();
            Log.d(TAG, "Creating new fragment one.");
        } else {
            remove.remove(one);
            Log.d(TAG, "Found existing fragment one.");
        }
        if (two == null) {
//            two = ColorFragment.newInstance(COLOR_TWO);
            two = FragmentoCategorias.newInstance(cEstado);

//            two = new FragmentoCategorias();
            Log.d(TAG, "Creating new fragment two.");
        } else {
            remove.remove(two);
            Log.d(TAG, "Found existing fragment two.");
        }
        if (!remove.isEmpty()) {
            remove.commit();
            fragmentManager.executePendingTransactions();
        }

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        if (viewPager != null) {
            viewPager.setAdapter(new FragmentoDivisionPagesFactura.TwoFragmentAdapter(fragmentManager, one, two));
            viewPager.setCurrentItem(lastPage);
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.left_pane, one, TAG_ONE)
                    .add(R.id.right_pane, two, TAG_TWO)
                    .addToBackStack(null) //añadido
                    .commit();
        }

        return rootView;
    }
    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBarLayout = (AppBarLayout) padre.findViewById(R.id.appbar);

        tabLayout = new TabLayout(getActivity());
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBarLayout.addView(tabLayout);
    }

 /*   private void poblarViewPager(ViewPager viewPager) {
        FragmentoPagesFactura.AdaptadorSecciones adapter = new FragmentoPagesFactura.AdaptadorSecciones(getFragmentManager());
        adapter.addFragment(FragmentoCategoria.newInstance(0), getString(R.string.titulo_tab_platillos));
        adapter.addFragment(FragmentoCategoria.newInstance(1), getString(R.string.titulo_tab_bebidas));
        adapter.addFragment(FragmentoCategoria.newInstance(2), getString(R.string.titulo_tab_postres));
        viewPager.setAdapter(adapter);
    }
*/    /**
     * Un {@link FragmentStatePagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewPager != null) {
            outState.putInt(KEY_PAGE, viewPager.getCurrentItem());
        } else {
            outState.putInt(KEY_PAGE, lastPage);
        }
    }

    private static class TwoFragmentAdapter extends PagerAdapter {
        private final FragmentManager fragmentManager;
        private final Fragment one;
        private final Fragment two;
        private FragmentTransaction currentTransaction = null;
        private Fragment currentPrimaryItem = null;

        public TwoFragmentAdapter(FragmentManager fragmentManager, Fragment one, Fragment two) {
            this.fragmentManager = fragmentManager;
            this.one = one;
            this.two = two;
        }

        @Override public int getCount() {
            return 2;
        }

        @Override public Object instantiateItem(ViewGroup container, int position) {
            if (currentTransaction == null) {
                currentTransaction = fragmentManager.beginTransaction();
            }

            String tag = (position == 0) ? TAG_ONE : TAG_TWO;
            Fragment fragment = (position == 0) ? one : two;
            currentTransaction.add(container.getId(), fragment, tag);
            if (fragment != currentPrimaryItem) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }

            return fragment;
        }

        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            // With two pages, fragments should never be destroyed.
            throw new AssertionError();
        }

        @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Fragment fragment = (Fragment) object;
            if (fragment != currentPrimaryItem) {
                if (currentPrimaryItem != null) {
                    currentPrimaryItem.setMenuVisibility(false);
                    currentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    fragment.setMenuVisibility(false);
                    fragment.setUserVisibleHint(false);
                }
                currentPrimaryItem = fragment;
            }
        }

        @Override public void finishUpdate(ViewGroup container) {
            if (currentTransaction != null) {
                currentTransaction.commitAllowingStateLoss();
                currentTransaction = null;
                fragmentManager.executePendingTransactions();
            }
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return ((Fragment) object).getView() == view;
        }
    }

    public static class ColorFragment extends Fragment {
        private static final String KEY_COLOR = "color";
        private static final int DEFAULT_COLOR = 0xFFFF0000;

        public static FragmentoDivisionPagesFactura.ColorFragment newInstance(int color) {
            Bundle arguments = new Bundle();
            arguments.putInt(KEY_COLOR, color);

            FragmentoDivisionPagesFactura.ColorFragment fragment = new FragmentoDivisionPagesFactura.ColorFragment();
            fragment.setArguments(arguments);
            return fragment;
        }

        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                           Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            int color = arguments.getInt(KEY_COLOR, DEFAULT_COLOR);

            ImageView iv = new ImageView(getActivity());
            iv.setImageDrawable(new ColorDrawable(color));

            return iv;
        }
    }

}
