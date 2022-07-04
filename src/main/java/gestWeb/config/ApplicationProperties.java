package gestWeb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to GestWeb.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    public final ClientData clientData = new ClientData();

    public ClientData getClientData() {
        return clientData;
    }

    public static class ClientData {
        private String name = "GestWeb";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    // --------------------- Propiedad para archivos ----------------
    private final ArchivoProperties archivo = new ArchivoProperties();

    // Retornamos el objeto de la clase que contiene las propiedades necesarios
    public ArchivoProperties getArchivoProperties() {
        return archivo;
    }

    // Creamos la clase que contendra las propiedades necesarias para el manejo de archivos
    public static class ArchivoProperties {

        private String baseUrl = "";

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

    }
}
