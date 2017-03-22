package tpv.cirer.com.marivent.ui;

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
import android.widget.LinearLayout;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUAN on 08/11/2016.
 */

public class FragmentoFactura extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout bottomSheetViewgroup;
    private static FragmentoFactura fragmentfactura = null;
    int nPage;

    public FragmentoFactura() {
    }
    public static FragmentoFactura newInstance(int page) {
        FragmentoFactura fragmentfactura = new FragmentoFactura();
        Bundle args = new Bundle();
        args.putInt("page", page);
        fragmentfactura.setArguments(args);
        return fragmentfactura;
    }
    public static FragmentoFactura getInstance(){
        if(fragmentfactura == null){
            fragmentfactura = new FragmentoFactura();
        }
        return fragmentfactura;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_paginado, container, false);

        if (savedInstanceState == null) {

            insertarTabs(container);

            // Setear adaptador al viewpager.
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            poblarViewPager(viewPager,nPage);

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
                            Filtro.setTag_fragment("FragmentoOpenDocumentoFactura");
                            break;
                        case 1:
                            Filtro.setTag_fragment("FragmentoCloseDocumentoFactura");
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
    private void poblarViewPager(ViewPager viewPager, int npage) {
        AdaptadorFacturas adapter = new AdaptadorFacturas(getFragmentManager());
        adapter.addFragment(FragmentoOpenDocumentoFactura.newInstance(0, "Page # 1","13"),((ActividadPrincipal) getActivity()).getPalabras( getString(R.string.titulo_tab_OpenFactura)),"FragmentoOpenDocumentoFactura");
        adapter.addFragment(FragmentoCloseDocumentoFactura.newInstance(0,"Page # 2","13"),((ActividadPrincipal) getActivity()).getPalabras( getString(R.string.titulo_tab_CloseFactura)),"FragmentoCloseDocumentoFactura");
//        adapter.addFragment(new FragmentoPrintFactura(), getString(R.string.titulo_tab_PrintFactura));
        if(viewPager.getAdapter() == null)
            viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(npage);
//        adapter.getRegisteredFragment(viewPager.getCurrentItem());
        adapter.getRegisteredFragment(npage);
    }
    @Override
    public void onResume() {
        super.onResume();
        /// Poner Titulo CABECERA
        ((ActividadPrincipal) getActivity()).setCabecera(((ActividadPrincipal) getActivity()).getPalabras("Facturas"),0.00,1);

        poblarViewPager(viewPager,0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //      setHasOptionsMenu(true);
        if (fragmentfactura!=null) {
            nPage = getArguments().getInt("page", 0);
        }else{
            nPage =0;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      //  inflater.inflate(R.menu.menu_factura, menu);


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
    public class AdaptadorFacturas extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();
        private final List<String> tagFragmentos = new ArrayList<>();
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public AdaptadorFacturas(FragmentManager fm) {
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
    }

}
