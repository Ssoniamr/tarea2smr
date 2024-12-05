package dam.pmdm.tarea2smr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dam.pmdm.tarea2smr.databinding.PersonajeCarviewBinding;

/**
 * Clase adaptador para mostrar una lista de personajes en un RecyclerView
 */
public class PersonajeRecyclerViewAdapter extends RecyclerView.Adapter<PersonajeViewHolder> {

    private final ArrayList<PersonajeData> personaje;
    private final Context context;

    /**
     * Constructor para inicializar el adactador
     *
     * @param personaje indica la lista de datos de personajes
     * @param context   indica el contexto de la actividad.
     */
    public PersonajeRecyclerViewAdapter(ArrayList<PersonajeData> personaje, Context context) {
        this.personaje = personaje;
        this.context = context;
    }

    /**
     * Método que crea un nuevo viewHolder
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return retorna el nuevo viewHolder creado
     */
    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonajeCarviewBinding binding = PersonajeCarviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PersonajeViewHolder(binding);
    }

    /**
     * vincula los datos del personaje actual a viewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        PersonajeData personajeActual = this.personaje.get(position);
        holder.bind(personajeActual);

        holder.itemView.setOnClickListener(view -> itemcliked(personajeActual, view));

    }

    /**
     * Método que maneja el evento clic sobre el item, indica hacia donde navegar llamando al metodo
     * {@link MainActivity#personajeClicked(PersonajeData, View)}
     *
     * @param personajeActual indica el objeto PersonajeData actual.
     * @param view            indica la vista del item que se clicó.
     */
    private void itemcliked(PersonajeData personajeActual, View view) {
        ((MainActivity) context).personajeClicked(personajeActual, view);
    }

    /**
     * Obtiene el tamaño del Arraylist que contiene la lista de personajes.
     *
     * @return retorna el numero de posiciones del Arraylist que contiene la lista de personajes.
     */
    @Override
    public int getItemCount() {
        return personaje.size();
    }
}
