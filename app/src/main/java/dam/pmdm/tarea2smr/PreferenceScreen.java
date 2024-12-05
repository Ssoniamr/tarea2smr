package dam.pmdm.tarea2smr;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

/**
 * Clase para guardar las preferencias del idioma de la aplicación.
 */
public class PreferenceScreen extends PreferenceFragmentCompat {

    public static CheckBoxPreference checkBoxEspanol;
    public static CheckBoxPreference checkBoxIngles;
    public static PreferenceCategory preferenceCategory;
    public static String idioma;

    /**
     * Método que inicializa las referencias y les asigna listener para manejarlas mediante el
     * método {@link #onPreferenceChange(Preference, Object)}
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     *                           this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     *                           {@link PreferenceScreen} with this key.
     */
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferencescreen, rootKey);

        checkBoxEspanol = findPreference("check_espanol");
        checkBoxIngles = findPreference("check_ingles");
        preferenceCategory = findPreference("preference_grupo_idioma");

        if (checkBoxEspanol != null)
            checkBoxEspanol.setOnPreferenceChangeListener(this::onPreferenceChange);
        if (checkBoxIngles != null)
            checkBoxIngles.setOnPreferenceChangeListener(this::onPreferenceChange);

    }

    /**
     * Método que aegura que solo una preferencia está marcada e invoca al metodo {@link MainActivity#changeLanguage(String)}
     * pasandole por parametro el idioma seleccionado.
     * es invocado cada vez que cambia el valor de una preferenica.
     *
     * @param preference indica el objeto Preference que cambio de valor
     * @param valor      indica el nuevo valor del ojbto preferencia pasado
     * @return true para indicar que el nuevo valor puede ser guardado.
     */
    private boolean onPreferenceChange(Preference preference, Object valor) {
        if ((Boolean) valor) {
            if (preference.getKey().equals("check_espanol")) {
                if (checkBoxIngles != null) {
                    checkBoxIngles.setChecked(false);
                    idioma = "es";
                }
            } else if (preference.getKey().equals("check_ingles")) {
                if (checkBoxEspanol != null) {
                    checkBoxEspanol.setChecked(false);
                    idioma = "en";
                }
            }
        }
        if(((MainActivity) getActivity())!=null) {
            ((MainActivity) getActivity()).changeLanguage(idioma);
        }
        updateLanguajeView();
        return true;
    }

    /**
     * Metodo para actualizar las vistas despues de cambiar el idioma
     */
    public void updateLanguajeView() {
        preferenceCategory.setTitle(R.string.idioma_titulo);
        preferenceCategory.setSummary(R.string.elige_un_idioma);
        checkBoxEspanol.setTitle(R.string.espanol);
        checkBoxIngles.setTitle(R.string.ingles);
    }


}