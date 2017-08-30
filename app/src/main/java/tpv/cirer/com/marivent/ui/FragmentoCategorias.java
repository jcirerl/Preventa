package tpv.cirer.com.marivent.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.UpdateableFragment;
import tpv.cirer.com.marivent.modelo.Categoria;

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
            if(Filtro.getOptabtodos()) {
                viewPager.setOffscreenPageLimit(lcategoria.size()); // BIEN PARA 23.1.1
            }else {
                viewPager.setOffscreenPageLimit(1);
            }
            if (viewPager != null) {
                poblarViewPager(viewPager);
            }

            assert viewPager != null;
//            viewPager.setCurrentItem(0);
            tabLayout.setupWithViewPager(viewPager);
            changeTabsSize1();
            if (Filtro.getOptab()==0){
               tabLayout.setTabMode(TabLayout.MODE_FIXED);
            }else{
               tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
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
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    positionTab = tab.getPosition();
////                    Log.i("TAB POSITION: ",Integer.toString(positionTab));

////                    Log.i("TABLAYOUT: ",Integer.toString(tab.getPosition()));

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
    private void changeTabsSize1(){
        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getActivity());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());
                tabTextView.setTextColor(Color.parseColor("#FFFFFF"));
                // First tab is the selected tab, so if i==0 then set BOLD typeface
/*                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD);
                }
*/              Log.i("tabsize",String.valueOf(tabTextView.getTextSize()));
                tabTextView.setTextSize(Filtro.getOptipoarticulo());
            }

        }

    }
    private void changeTabsSize() {
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/"+ Constants.FontStyle);
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
  //                  ((TextView) tabViewChild).setTypeface(font);
                    ((TextView) tabViewChild).setTextSize(30);

                }
            }
        }
    }
    private void poblarViewPager(final ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
////        Log.i("Estoy PoblarViewPager",Integer.toString(lcategoria.size()));
////        Log.i("CreaPDD","Inicio Carga Categorias");
        Categoria categoria;
        for(int x=0;x<lcategoria.size();x++) {
            categoria = lcategoria.get(x);
////            Log.i("Poblando: ",Integer.toString(x)+" "+categoria.getCategoriaTipo_are()+" "+categoria.getCategoriaOrden());
            adapter.addFragment(FragmentoCategoria.newInstance(categoria.getCategoriaOrden(),cEstado), categoria.getCategoriaNombre_tipoare(),x);
        }
////        Log.i("CreaPDD","Final Carga Categorias");
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

        ImageView searchButton = (ImageView) searchView1.findViewById(android.support.v7.appcompat.R.id.search_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.accentColor)));
        }
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
 public void onResume() {
     super.onResume();
     if (!Filtro.getTag_fragment().contains("Linea")) {
      //   Filtro.setTag_fragment("FragmentoInicio");
         /// Poner Datos CABECERA
         ///    ((ActividadPrincipal) getActivity()).setCabecera("Inicio",0.00,1);
         ((ActividadPrincipal) getActivity()).setTitle(((ActividadPrincipal) getActivity()).getPalabras("Cartas"));
         Spannable text = new SpannableString(((ActividadPrincipal) getActivity()).getTitle());
         text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(((ActividadPrincipal) getActivity()), R.color.light_blue_500)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
         ((ActividadPrincipal) getActivity()).setTitle(text);

     }
 }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
 /*       View rootView = ((ActividadPrincipal)getActivity()).getWindow().getDecorView().findViewById(android.R.id.content);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.getMenu().removeItem(R.id.mi_search);
 */
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
