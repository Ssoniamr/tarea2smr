package dam.pmdm.tarea2smr;

/**
 * Clase que contiene todos los datos de un personaje.
 */
public class PersonajeData {

    private final int imagePersonaje;
    private final int imagePersonajeDetalle;
    private final String nombrePersonaje;
    private final String descripcionPersonaje;
    private final String caracteristicasPersonaje;

    /**
     * Constructor para inizializar los datos del personaje
     *
     * @param imagePersonaje           indica la imagen del personaje que se mostrara en el RecyclerView
     * @param imagePersonajeDetalle    indica la imagen del personaje que se mostrara en la pagina de detalle
     * @param nombrePersonaje          indica el nombre del personaje.
     * @param descripcionPersonaje     indica una breve descripcion del personaje.
     * @param caracteristicasPersonaje indica los poderes más caracteristicos del personaje.
     */
    public PersonajeData(int imagePersonaje, int imagePersonajeDetalle, String nombrePersonaje, String descripcionPersonaje, String caracteristicasPersonaje) {
        this.imagePersonaje = imagePersonaje;
        this.imagePersonajeDetalle = imagePersonajeDetalle;
        this.nombrePersonaje = nombrePersonaje;
        this.descripcionPersonaje = descripcionPersonaje;
        this.caracteristicasPersonaje = caracteristicasPersonaje;
    }

    /**
     * Método para obtener la imagen del personaje mostrada en el RecyclerView
     *
     * @return retorna la imagen del personaje mostrada en el RecyclerView
     */
    public int getImagePersonaje() {
        return imagePersonaje;
    }

    /**
     * Método para obtener el nombre del personaje.
     *
     * @return retorna el nombre del personaje
     */
    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    /**
     * Método para obtener la descripción del personaje.
     *
     * @return retorna la descripcion del personaje
     */
    public String getDescripcionPersonaje() {
        return descripcionPersonaje;
    }

    /**
     * Método para obtener los poderes del personaje.
     *
     * @return retorna los poderes del personaje
     */
    public String getCaracteristicasPersonaje() {
        return caracteristicasPersonaje;
    }

    /**
     * Método para obtener la imagen mostrada en la pagina de detalles.
     *
     * @return retorna la imagen mostrada en la pagina detalles.
     */
    public int getImagePersonajeDetalle() {
        return imagePersonajeDetalle;
    }
}
