package dam.pmdm.tarea2smr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dam.pmdm.tarea2smr.databinding.PersonajesDetailFragmentBinding;

/**
 * Clase que muestra los detalles de un personaje
 */
public class PersonajesDetailFragment extends Fragment {

    private PersonajesDetailFragmentBinding binding;

    /**
     * Infla el diseño para poder mostrar en fragmento que muestra los detalles del personaje.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return retorna la vista raiz para el diseño del fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PersonajesDetailFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Metodo que configura la vista del fragmento, obtiene los argumentos y los muestra.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            int imagen = getArguments().getInt("imagen");
            String name = getArguments().getString("name");
            String descripcion = getArguments().getString("descripcion");
            String caracteristicas = getArguments().getString("caracteristica");

            String negrita = getString(R.string.poderes_negrita);
            Spannable spannable = new SpannableString(caracteristicas);
            int start = caracteristicas.indexOf(negrita);
            int end = start + negrita.length();
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            binding.imagePersonaje.setImageResource(imagen);
            binding.nombrePersonaje.setText(name);
            binding.descripcionPersonaje.setText(descripcion);
            binding.caracteristicasPersonaje.setText(spannable);
            String seleccion = getString(R.string.seleccion_personaje)+ " " + binding.nombrePersonaje.getText();
            Toast.makeText(requireContext(), seleccion, Toast.LENGTH_SHORT).show();
        }
    }
}