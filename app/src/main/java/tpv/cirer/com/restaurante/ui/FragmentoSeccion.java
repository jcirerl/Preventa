package tpv.cirer.com.restaurante.ui;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.Filtro;

/**
 * Created by JUAN on 13/09/2016.
 */
public class FragmentoSeccion extends Fragment {

   private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static FragmentoSeccion fragmentseccion = null;


     public static FragmentoSeccion newInstance() {
        FragmentoSeccion fragmentseccion = new FragmentoSeccion();
        return fragmentseccion;
    }
    public static FragmentoSeccion getInstance(){
        if(fragmentseccion == null){
            fragmentseccion = new FragmentoSeccion();
        }
        return fragmentseccion;
    }

    public FragmentoSeccion() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_paginado, container, false);

        if (savedInstanceState == null) {
            insertarTabs(container);

            // Setear adaptador al viewpager.
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            poblarViewPager(viewPager);

            assert viewPager != null;
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int positionTab = tab.getPosition();
                    Log.i("TAB POSITION: ",Integer.toString(positionTab));

                    Log.i("TABLAYOUT: ",Integer.toString(tab.getPosition()));

                    switch(tab.getPosition()){
                        case 0:
                            Filtro.setTag_fragment("FragmentoOpenSeccion");
                            break;
                        case 1:
                            Filtro.setTag_fragment("FragmentoCloseSeccion");
                            break;

                    }
                    super.onTabSelected(tab);
                }
            });
        }
        return view;
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBarLayout = (AppBarLayout) padre.findViewById(R.id.appbar);

        tabLayout = new TabLayout(getActivity());
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBarLayout.addView(tabLayout);
    }
    private void poblarViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
        adapter.addFragment(FragmentoOpenSeccion.newInstance(0, "FragmentoOpenSeccion"),((ActividadPrincipal) getActivity()).getPalabras(getString(R.string.titulo_tab_OpenSeccion)),"FragmentoOpenSeccion");
        adapter.addFragment(FragmentoCloseSeccion.newInstance(1, "FragmentoCloseSeccion"),((ActividadPrincipal) getActivity()).getPalabras(getString(R.string.titulo_tab_CloseSeccion)),"FragmentoCloseSeccion");
        adapter.addFragment(new FragmentoPrintSeccion(), ((ActividadPrincipal) getActivity()).getPalabras(getString(R.string.titulo_tab_PrintSeccion)),"");
        if(viewPager.getAdapter() == null)
            viewPager.setAdapter(adapter);
////        viewPager.setAdapter(adapter);
        adapter.getRegisteredFragment(viewPager.getCurrentItem());
//        adapter.getActiveFragment(viewPager,0);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
///        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /// Poner Datos CABECERA
        ((ActividadPrincipal) getActivity()).setCabecera(((ActividadPrincipal) getActivity()).getPalabras("Secciones"),0.00,0);
        poblarViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
///       inflater.inflate(R.menu.menu_seccion, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
    }

/**
 * Un {@link FragmentStatePagerAdapter} que gestiona las secciones, fragmentos y
 * títulos de las pestañas
 */
public class AdaptadorSecciones extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentos = new ArrayList<>();
    private final List<String> titulosFragmentos = new ArrayList<>();
    private final List<String> tagFragmentos = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

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

    public void addFragment(Fragment fragment, String title, String tag) {
        fragmentos.add(fragment);
        titulosFragmentos.add(title);
        tagFragmentos.add(tag);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titulosFragmentos.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        Filtro.setTag_fragment(tagFragmentos.get(position));
        Log.i("registered", Integer.toString(position)+" "+Filtro.getTag_fragment());
        return registeredFragments.get(position);
    }

    public Fragment getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  getFragmentManager().findFragmentByTag(name);
    }

    public String makeFragmentName(int viewId, int index) {
        Log.i("switcher","android:switcher:" + viewId + ":" + index);
        Filtro.setTag_fragment(titulosFragmentos.get(index));
        return "android:switcher:" + viewId + ":" + index;
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
}
