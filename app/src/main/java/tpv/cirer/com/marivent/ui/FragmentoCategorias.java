package tpv.cirer.com.marivent.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.UpdateableFragment;
import tpv.cirer.com.marivent.modelo.Categoria;
import tpv.cirer.com.marivent.modelo.Comida;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.comidas;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lcategoria;


/**
 * Fragmento que contiene otros fragmentos anidados para representar las categorías
 * de comidas
 */
public class FragmentoCategorias extends Fragment {
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final String KEY_COLOR = "color";
//    public static ArrayList<ArrayList<Comida>> comidas; //Definicion array de comidas
    public static ArrayList<AdaptadorCategorias> adaptadorcategorias = null;
    private static FragmentoCategorias Categorias = null;
    int positionTab;
    String cEstado;
    SearchView searchView = null;
    FragmentoCategoria fragmentoCategoria;

///    private FilterManager filterManager;

    public static FragmentoCategorias newInstance(String estado) {
        FragmentoCategorias Categorias = new FragmentoCategorias();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("ESTADO", estado);
        Categorias.setArguments(args);
        return Categorias;
    }

    public static FragmentoCategorias getInstance(){
        if(Categorias == null){
            Categorias = new FragmentoCategorias();
        }
        return Categorias;
    }

    public FragmentoCategorias() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_paginado, container, false);

        if (savedInstanceState == null) {
            insertarTabs(container);

            // Setear adaptador al viewpager.
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            viewPager.setOffscreenPageLimit(lcategoria.size()); // Por defecto 1 poner a zero para que refresque cada fragment

            if (viewPager != null) {
                poblarViewPager(viewPager);
            }

            assert viewPager != null;
//            viewPager.setCurrentItem(0);
            tabLayout.setupWithViewPager(viewPager);
////            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            // Mario Velasco's code
/*            tabLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    int tabLayoutWidth = tabLayout.getWidth();

                    DisplayMetrics metrics = new DisplayMetrics();
                    ((ActividadPrincipal)getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int deviceWidth = metrics.widthPixels;

                    if (tabLayoutWidth < deviceWidth)
                    {
                        tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                    } else
                    {
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }
                }
            });
  */     //     tabLayout.canScrollHorizontally(View.LAYOUT_DIRECTION_RTL);
            tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
/*                     for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                        Comida comida = it.next();
                        it.remove();
                    }
*/                       positionTab = tab.getPosition();
                    Log.i("TAB POSITION: ",Integer.toString(positionTab));

                    int x = 0;
//                    if (comidas.size() <= tab.getPosition()){
                        for (ArrayList<Comida> i : comidas) { // iterate -list by list

                            if (x == tab.getPosition()) {
                                for (Comida comida : i) { //iterate element by element in a list
                                    Log.i("TABLAYOUT COMIDA: ",comida.getNombre());
//                                    lcomida.add(comida);
                                }
                            }
                            x++;
                        }
 //                   }
                    Log.i("TABLAYOUT: ",Integer.toString(tab.getPosition()));

                    switch(tab.getPosition()){

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

    private void poblarViewPager(final ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
/*        AdaptadorSecciones adapter = new AdaptadorSecciones(
                getActivity(),                    // pass the context,
                getChildFragmentManager(),        // the fragment manager
                FragmentoCategorias.getFilterManager()   // and the filter manager
        );
*/        Log.i("Estoy PoblarViewPager",Integer.toString(lcategoria.size()));
        Categoria categoria;
        for(int x=0;x<lcategoria.size();x++) {
            categoria = lcategoria.get(x);
            Log.i("Poblando: ",Integer.toString(x)+" "+categoria.getCategoriaTipo_are()+" "+categoria.getCategoriaOrden());
            adapter.addFragment(FragmentoCategoria.newInstance(categoria.getCategoriaOrden(),cEstado), categoria.getCategoriaNombre_tipoare(),x);
        }
//        adapter.addFragment(FragmentoCategoria.nuevaInstancia(0), getString(R.string.titulo_tab_platillos));
//        adapter.addFragment(FragmentoCategoria.nuevaInstancia(1), getString(R.string.titulo_tab_bebidas));
//        adapter.addFragment(FragmentoCategoria.nuevaInstancia(2), getString(R.string.titulo_tab_postres));
        viewPager.setAdapter(adapter);

    }
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // CREAMOS EL ARRAY DE ARRAYS DE COMIDAS
 ////       comidas = new ArrayList<ArrayList<Comida>>();
        adaptadorcategorias = new ArrayList<AdaptadorCategorias>();
        cEstado = getArguments().getString("ESTADO", "");
         /// Poner Datos CABECERA
         ((ActividadPrincipal) getActivity()).setCabecera(((ActividadPrincipal) getActivity()).getTitle().toString(),0.00,1);

         ///        Categorias = this;
 ///        filterManager = new FilterManager();

     }

/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_categorias, menu);
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_categorias, menu);
        MenuItem mSearchMenuItem = menu.findItem(R.id.mi_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                FragmentoCategoria.getInstance().adaptador.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do something
                return true;
            }
        });
    }
    */
/*@Override
public void onPrepareOptionsMenu(Menu menu) {
    menu.findItem(R.id.mi_search).setVisible(true);
    super.onPrepareOptionsMenu(menu);

}*/
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
{
    inflater.inflate(R.menu.menu_categorias, menu);
    MenuItem searchItem1 = menu.findItem(R.id.mi_search);



    if(searchItem1 != null)
    {

        AdaptadorCategorias adaptadorx = null;

        SearchView searchView1 = (SearchView) MenuItemCompat.getActionView(searchItem1);
        // use this method for search process
        searchView1.setOnSearchClickListener(new View.OnClickListener()
        {
            Fragment fragmentoCategoria;
            @Override
            public void onClick(View v)
            {
                //Search view is expanded
                AdaptadorSecciones pagerAdapter = (AdaptadorSecciones) viewPager
                        .getAdapter();
                Fragment viewPagerFragment = (Fragment) viewPager
                        .getAdapter().instantiateItem(viewPager, positionTab);
                fragmentoCategoria = (FragmentoCategoria) viewPagerFragment;

                Filtro.setTipo_are(pagerAdapter.getPageTitle(positionTab).toString());

                Filtro.setSearch(true);
////                 FragmentoCategoria.getInstance().onResume();


                //              showSearchPage();
            }
        });
        searchView1.setOnCloseListener(new SearchView.OnCloseListener()
        {
            @Override
            public boolean onClose()
            {
                //Search View is collapsed
//                hideSearchPage();
                Filtro.setSearch(false);
                return false;
            }
        });
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                // use this method when query submitted
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                // use this method for auto complete search process
                Log.e("SearchValueIs",":"+newText);
                // this is your adapter that will be filtered
/*
                                 switch (positionTab) {
                                    case 0:
                                        FragmentoCategoria.getInstance().adaptador0.getFilter().filter(newText);
                                        break;
                                    case 1:
                                        FragmentoCategoria.getInstance().adaptador1.getFilter().filter(newText);
                                        break;
                                    case 2:
                                        FragmentoCategoria.getInstance().adaptador2.getFilter().filter(newText);
                                        break;
                                }
*/
                adaptadorcategorias.get(positionTab).getFilter().filter(newText);

///                fragmentoCategoria.adaptador.getFilter().filter(newText);

                return false;
            }
        });
    }
    super.onCreateOptionsMenu(menu, inflater);

}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mi_search:
                 Log.i("SEARCH","OK");
                // Not implemented here
                return true;

            default:
                break;
        }

        return false;
    }
/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_categorias, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.mi_search);
        searchView = (SearchView) menu.findItem(R.id.mi_search)
                .getActionView();

        if (mSearchMenuItem != null) {
            searchView = (SearchView) mSearchMenuItem.getActionView();
        }
        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(this
//                    .getComponentName()));

        }
        searchView.setIconifiedByDefault(true);

//        MenuItemCompat.expandActionView(mSearchMenuItem);
        MenuItemCompat.setOnActionExpandListener(mSearchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        Filtro.setSearch(true);
                        FragmentoCategoria.getInstance().onResume();
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        Filtro.setSearch(false);
                        return true; // Return true to expand action view
                    }
                });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String query) {

                // this is your adapter that will be filtered
                AdaptadorSecciones pagerAdapter = (AdaptadorSecciones) viewPager
                        .getAdapter();

                for (int z = positionTab; z == positionTab; z++) {

                    Fragment viewPagerFragment = (Fragment) viewPager
                            .getAdapter().instantiateItem(viewPager, z);
                    Log.i("VIEWPAGER; ", pagerAdapter.getPageTitle(z).toString()+" "+Integer.toString(z));
                    Filtro.setTipo_are(pagerAdapter.getPageTitle(z).toString());
                    if (viewPagerFragment != null
                            && viewPagerFragment.isAdded()) {

                        if (viewPagerFragment instanceof FragmentoCategoria) {
                            fragmentoCategoria = (FragmentoCategoria) viewPagerFragment;
                            if (fragmentoCategoria != null) {
                                Log.i("VIEWPAGER1; ", "dentro");
 //                               fragmentoCategoria.adaptador = new AdaptadorCategorias(getActivity(), comidas.get(z),z);
 //                               fragmentoCategoria.recViewcomida.setAdapter(fragmentoCategoria.adaptador);
 //                               fragmentoCategoria.recViewcomida.invalidate();
                                fragmentoCategoria.adaptador.getFilter().filter(query);
/*                                switch (i) {
                                    case 0:
                                        fragmentoCategoria.adaptador0.getFilter().filter(query);
                                        break;
                                    case 1:
                                        fragmentoCategoria.adaptador1.getFilter().filter(query);
                                        break;
                                    case 2:
                                        fragmentoCategoria.adaptador2.getFilter().filter(query);
                                        break;
                                }
*/
 ///                               adaptadorcategorias.get(i).getFilter().filter(query);
                                 //                                fragmentoCategoria.adaptador.getFilter().filter(query);

 ////                           }
/*                        } else if (viewPagerFragment instanceof SystemDefaultAppFragment) {
                            groupsFragment = (SystemDefaultAppFragment) viewPagerFragment;
                            if (groupsFragment != null) {
                                groupsFragment.beginSearch(query);
                            }*/
/*                        }
                    }
                }

                return false;

            }

            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the

                return false;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return;
    }
*/

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
        private int mposition;

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("TAB ADAPTADOR: ",titulosFragmentos.get(position)+" "+Integer.toString(this.getCount()));

            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(Fragment fragment, String title, int position) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
            mposition = position;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("TAB CATEGORIA: ",titulosFragmentos.get(position));

            return titulosFragmentos.get(position);
        }
 /*       @Override
        public int getItemPosition(Object object) {
            // Causes adapter to reload all Fragments when
            // notifyDataSetChanged is called
            notifyDataSetChanged();
            return POSITION_NONE;
        }
*/
 @Override
 public int getItemPosition(Object object) {
     Log.i("UPDATE POSITION","");

     if (object instanceof UpdateableFragment) {
         ((UpdateableFragment) object).update(mposition);
     }
     //don't return POSITION_NONE, avoid fragment recreation.
     return super.getItemPosition(object);
 }
        public void update(int xyzData) {
            this.mposition = xyzData;
            notifyDataSetChanged();
        }

    }

}
