package dam.pmdm.tarea2smr;

import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.tarea2smr.databinding.PersonajeCarviewBinding;

/**
 * Clase para crea los viewHolders del RecyclerView.
 */
public class PersonajeViewHolder extends RecyclerView.ViewHolder {

    private PersonajeCarviewBinding binding;

    /**
     * Constructor para inicializar un viewHolder
     *
     * @param binding indica el objeto PersonajeCarviewBinding generado que contiene las vistas
     *                del diseño XML.
     */
    public PersonajeViewHolder(PersonajeCarviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * vincula los datos del personaje pasados por parametro a viewHolder.
     *
     * @param personaje indica el objeto PersonajeData que contiene los datos del personaje.
     */
    public void bind(PersonajeData personaje) {
        binding.imagePersonaje.setImageResource(personaje.getImagePersonaje());
        binding.nombrePersonaje.setText(personaje.getNombrePersonaje());
        binding.executePendingBindings();
    }
}
