package dam.pmdm.tarea2smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dam.pmdm.tarea2smr.databinding.ListPersonajesFragmentBinding;

/**
 * clase que contiene un Arraylist de personajes para mostrar en un RecyclerView
 */
public class ListPersonajesFragment extends Fragment {

    private ListPersonajesFragmentBinding binding;
    private ArrayList<PersonajeData> personaje;
    private PersonajeRecyclerViewAdapter adapter;

    /**
     * metodo en el que se infla el diseño del fragmento contenedor de la lista de personajes
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return la vista raiz para el diseño del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ListPersonajesFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método que inicializa la lista de personajes (llamando al metodo {@link #listaPersonajes()} )
     * y pasa esta lista de personaje al RecyclerView atraves de un adactador y un LayoutManager
     * vertical. Finalmente muestra  un mensaje de usuario.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //llamada al metodo que cra la lista de personajes
        listaPersonajes();
        //creacion del adapter y el LinerLauotManager
        adapter = new PersonajeRecyclerViewAdapter(personaje, getActivity());
        binding.personajesRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.personajesRecyclerview.setAdapter(adapter);
        //mensaje de bienbenida que se mostrara cada vez que se cargue la lista
        String bienbenida = getString(R.string.bienbenida);
        Snackbar.make(view, bienbenida, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Metódo que añade objetos de tipo PersonajesData y los añade a un Arraylist
     */
    private void listaPersonajes() {
        personaje = new ArrayList<PersonajeData>();
        personaje.add(new PersonajeData(
                R.drawable.mario_list,
                R.drawable.mario_detalle,
                getString(R.string.mario_bros),
                getString(R.string.caracteristica_mario),
                getString(R.string.poderes_mario)
        ));
        personaje.add(new PersonajeData(
                R.drawable.luigi_list,
                R.drawable.luigi_detalle,
                getString(R.string.luigi),
                getString(R.string.caracteristicas_luigi),
                getString(R.string.poderes_luigi)
        ));
        personaje.add(new PersonajeData(
                R.drawable.peach_list,
                R.drawable.peach_detalle,
                getString(R.string.peach),
                getString(R.string.caracteristicas_peach),
                getString(R.string.poderes_peach)
        ));
        personaje.add(new PersonajeData(
                R.drawable.toad_list,
                R.drawable.toad_detalle,
                getString(R.string.toad),
                getString(R.string.caracteristicas_toad),
                getString(R.string.poderes_toad)
        ));
    }


}

