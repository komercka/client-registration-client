package cz.kb.openbanking.clientregistration.client.jersey;

import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpStatusCode;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * Basic abstract test class for the Client Registration API Jersey implementation.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
abstract class AbstractJerseyClientTest {

    protected static final int MOCK_SERVER_PORT = 1080;

    protected static final String MOCK_SERVER_URI = "http://localhost:" + MOCK_SERVER_PORT;

    protected ClientAndServer mockServer;

    @BeforeEach
    protected void createMockServer() {
        mockServer = new ClientAndServer(MOCK_SERVER_PORT);
    }

    @AfterEach
    protected void stopServer() {
        mockServer.stop();
    }

    /**
     * Configures mock server.
     *
     * @param urlPath      mock server URL
     * @param resourceName name of resource that contains a mock response
     * @param method       {@link HttpMethod} which will mock server return
     * @param status       {@link HttpStatusCode} which will mock server return
     */
    protected void configureServer(String urlPath, String resourceName, HttpMethod method, HttpStatusCode status) {
        if (StringUtils.isBlank(urlPath)) {
            throw new IllegalArgumentException("urlPath must not be empty or blank");
        }
        if (StringUtils.isBlank(resourceName)) {
            throw new IllegalArgumentException("resourceName must not be empty or blank");
        }
        if (method == null) {
            throw new IllegalArgumentException("method must not be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }

        mockServer
                .when(
                        request()
                                .withMethod(method.name())
                                .withPath(urlPath))
                .respond(
                        response()
                                .withStatusCode(status.code())
                                .withHeader(new Header("Content-Type", "application/json"))
                                .withBody(getResourceAsString(resourceName)));
    }

    /**
     * Gets resource represented as a string.
     *
     * @param resourceName resource name
     * @return resource represented as a string
     */
    protected String getResourceAsString(String resourceName) {
        String response;
        try {
            response = IOUtils.toString(
                    getClass().getClassLoader().getResourceAsStream(resourceName), Charset.defaultCharset());
        } catch (IOException e) {
            throw new IllegalStateException("Error in getting content of resource '" + resourceName + "'.");
        }

        return response;
    }
}
