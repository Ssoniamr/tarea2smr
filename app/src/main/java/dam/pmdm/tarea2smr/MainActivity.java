package dam.pmdm.tarea2smr;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import dam.pmdm.tarea2smr.databinding.ActivityMainBinding;

/**
 * clase de la Activity principal de la aplicacion.
 * contiene las variables y metodos necesarios para configurar la interfaz de usairo, iniciar la
 * pantalla splash y de configurar y sincronizar la navegacion de los distintos menus y la actioBar.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavHostFragment navHostFragment;
    private ActionBar actionBar;
    private PreferenceScreen preferenceScreen;
    private SharedPreferences sharedPreferences;
    private String idioma;

    /**
     * metodo en el que se configura todo lo necesario para poder inicar la activity, la interfaz de
     * usuario y la navegacion. Tambi'en muestra la pantalla esplash para Android 12 o superior
     *
     * @param savedInstanceState en el caso de que exista, indica un estado anterior guardado de la activity para inicializarla desde ahí.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //muestra la pantalla splash por defecto en versiones de android superior a la 12
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //se infla el layoud usando viewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //------CONFIGURACION DE NAVEGACION.---------

        // mediante el fragmentManager obtenemos nuestro contenedor de fragmentos (navHostFragment)
        FragmentManager fragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);
        //si este no es nulo obtenemos su control de navegacion y lo conectamos con el menu lateral
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.navigation, navController);
        }

        // controla los cambios de navegacion indicados en el metodo onChangeView
        navController.addOnDestinationChangedListener(this::onChangeView);


        //conectamos el menu lateral con la actionBar
        toggle = new ActionBarDrawerToggle(this, binding.main, R.string.abir_menu, R.string.cerrar_menu);
        //se sincroniza el boton hamburguesa con el drawer, se añade como listener para que maneje los eventos
        // apertura/cierre del menu la teral y lo sincronizamos
        toggle.setDrawerIndicatorEnabled(true);
        binding.main.addDrawerListener(toggle);
        toggle.syncState();

        //obtenemos la actionBar y habilitamos el boton home (flecha de retroceso/hamburguesa)
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        //conectamos el controlador de navegacion con la actionBar
        NavigationUI.setupActionBarWithNavController(this, navController);

        //controla los cambios de navegacion del menu lateral indicados en el metodo onOptionsItemMenuLateral
        binding.navigation.setNavigationItemSelectedListener(this::onOptionsItemMenuLateral);

        idiomaGuardado();
    }

    /**
     * Metodo para dar al navController el control de la pila de navegacion, podiendo acceder al destino anterior,
     * de no poder se pasa el control a la configuracion predeterminada.
     * (es llamado cada vez que el usuario pulsa el boton de retroceso de la actionBar)
     *
     * @return retorna un booleano dependiendo de si el navControler pudo o no controlar dicha navegacion.
     */
    @Override
    public boolean onSupportNavigateUp() {
        boolean navigatedUp = navController.navigateUp();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toggle.setDrawerIndicatorEnabled(true);
        }
        return navigatedUp || super.onSupportNavigateUp();
    }

    /**
     * metodo que crea el menu de opciones, infla el archivo (menu.xml) que contiene los items a agregar
     * en el contenedor pasado por parametro
     * (es invocado cada vez que se crea la activity)
     *
     * @param menu indica el contenedor donde se creara el menú.
     * @return retorna un boleano dependiendo de si se pudo crear o no el menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Metodo que indica que hacer segun el item del menu selecionado
     * (es invocado cada vez que se selecciona un item del menu)
     *
     * @param item indica el item seleccionado
     * @return devuelve un bolenao dependiendo de si se pudo o no manejar la acción a realizar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;

        int itemElegido = item.getItemId();

        if (itemElegido == R.id.menu_acerca_de) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string._dialog)
                    .setMessage(R.string.informacion_acerca_de)
                    .setPositiveButton("ok", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * metodo que indica que hacer dependiendo del item del menu lateral seleccionado.
     * (es invocado cada vez que se selecciona un item del menu lateral)
     *
     * @param menuItem indica el item seleccionado.
     * @return devuelve un booleano dependiendo de si se pudo o no manejar la accion a realizar
     */
    private boolean onOptionsItemMenuLateral(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.preferenceScreen) {
            navController.navigate(R.id.preferenceScreen);
        } else if (menuItem.getItemId() == R.id.listPersonajesFragment) {
            navController.navigate(R.id.listPersonajesFragment);
            actionBar.setDisplayHomeAsUpEnabled(true);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();
        }
        binding.main.closeDrawers();
        return true;
    }

    /**
     * metodo  que cambia el boton de retrocesso y el boton hamburguesa segun el navDestination donde se encuentre.
     * (se ejecuta cada vez que se produzca un cambio de destino)
     *
     * @param navController  indica el controlador de navegacion
     * @param navDestination indica el nuevo destino (fragment) en el que nos encontramos.
     * @param bundle         indica datos adicionales a pasar en la navegacion, puede ser nulo.
     */
    private void onChangeView(NavController navController, NavDestination navDestination, Bundle bundle) {
        if (toggle == null) return;
        if (navDestination.getId() == R.id.personajesDetailFragment || navDestination.getId() == R.id.preferenceScreen) {
            toggle.setDrawerIndicatorEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toggle.syncState();
    }

    /**
     * metodo para actualizar el menu lateral
     * (se invoca cada vez que se produce un cambio de idioma)
     */
    public void refreshNavigationMenu() {
        Menu menu = binding.navigation.getMenu();
        menu.clear();
        binding.navigation.inflateMenu(R.menu.menu_lateral);
    }

    /**
     * Método para pasar los datos de un fragment a otro.
     * Obtiene la información del objeto PersonajeData perteneciente a la view que se selecciono y los envia
     * al fragmente personajesDetailFragment.
     *
     * @param personaje indica el objeto de tipo PersonajeData que contiene toda la informacion del personaje.
     * @param view      indica la vista, es decir, el item del RecyclerView que se selecciono
     */
    public void personajeClicked(PersonajeData personaje, View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("imagen", personaje.getImagePersonajeDetalle());
        bundle.putString("name", personaje.getNombrePersonaje());
        bundle.putString("descripcion", personaje.getDescripcionPersonaje());
        bundle.putString("caracteristica", personaje.getCaracteristicasPersonaje());

        Navigation.findNavController(view).navigate(R.id.personajesDetailFragment, bundle);

    }

    /**
     * Método para modificar el idioma de la aplicación.
     * utiliza la configuracion del sistmea y actualiza la app. Para ello invico a los metodos
     * {@link MainActivity#invalidateOptionsMenu()} , {@link MainActivity#refreshNavigationMenu()}
     * y {@link #updateActionBarTittle()}
     *
     * @param language indica el código del idioma al que se desea cambiar.
     */
    public void changeLanguage(String language) {
        try {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        } catch (Exception e) {
            System.out.println("no pudo trabajar con locale" + e);
        }

        //llama a los metodos para actualizar el resto de la app
        invalidateOptionsMenu();
        refreshNavigationMenu();
        updateActionBarTittle();
    }

    /**
     * metodo que actualiza los titulos de la app.
     * Se deben actualizar manualmente puesto que se estan cogiendo directamente del atributo label
     * en el nav_grahp.
     * (se invoca desde el metodo {@link #changeLanguage(String)} cada vez que se produzca un cambio
     * de idioma desde la ventana ajustes.
     */
    private void updateActionBarTittle() {
        navController.getGraph().findNode(R.id.preferenceScreen).setLabel(getString(R.string.ajustess));
        navController.getGraph().findNode(R.id.listPersonajesFragment).setLabel(getString(R.string.mundo_mario));
        navController.getGraph().findNode(R.id.personajesDetailFragment).setLabel(getString(R.string.personajes));
        NavDestination destino = navController.getCurrentDestination();
        if (destino != null) {
            actionBar.setTitle(destino.getLabel());
        }
    }

    /**
     * Metodo que recupera las preferencias de idioma guardadas e invoca el metodo {@link #changeLanguage(String)}
     * para cambiar el idioma de la app.
     * (es invocado cada vez que se lanza la MainActivity).
     */
    private void idiomaGuardado() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("check_espanol", false)) {
            idioma = "es";
        } else if (sharedPreferences.getBoolean("check_ingles", false)) {
            idioma = "en";
        }
        changeLanguage(idioma);
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

    }

    /**
     * metodo para asegurar que una vez creada la activity el boton hamburguesa sea visible
     *
     * @param state indica el estado guardado de la actividad
     */
    @Override
    protected void onPostCreate(Bundle state) {
        super.onPostCreate(state);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

}

