package cz.kb.openbanking.clientregistration.client.jersey.utils;

/**
 * Contains all common constants for requests to use in the Client Registration API client.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public final class RequestConstants {

    public static final String CORRELATION_ID_HEADER_NAME = "x-correlation-id";

    public static final String API_KEY_HEADER_NAME = "x-api-key";

    public static final String CLIENT_CERT_HEADER_NAME = "x-client-cert";

    /**
     * No instance.
     */
    private RequestConstants() {
    }
}
