package cz.kb.openbanking.clientregistration.client.jersey;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kb.openbanking.clientregistration.client.api.SoftwareStatementsApi;
import cz.kb.openbanking.clientregistration.client.api.exception.ClientRegistrationClientException;
import cz.kb.openbanking.clientregistration.client.api.model.Jwt;
import cz.kb.openbanking.clientregistration.client.api.model.SoftwareStatement;
import cz.kb.openbanking.clientregistration.client.model.generated.Error;
import cz.kb.openbanking.clientregistration.client.model.generated.ErrorResponse;
import cz.kb.openbanking.clientregistration.client.model.generated.SoftwareStatementRequest;
import cz.kb.openbanking.clientregistration.client.model.generated.SoftwareStatementRequest.GrantTypesEnum;
import cz.kb.openbanking.clientregistration.client.model.generated.SoftwareStatementRequest.ResponseTypesEnum;
import cz.kb.openbanking.clientregistration.client.model.generated.SoftwareStatementRequest.TokenEndpointAuthMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cz.kb.openbanking.clientregistration.client.jersey.utils.RequestConstants.API_KEY_HEADER_NAME;
import static cz.kb.openbanking.clientregistration.client.jersey.utils.RequestConstants.CORRELATION_ID_HEADER_NAME;

/**
 * Jersey implementation of the {@link SoftwareStatementsApi}.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @see SoftwareStatementsApi
 * @since 1.0
 */
public class SoftwareStatementsJerseyImpl implements SoftwareStatementsApi {
    private static final Logger log = LoggerFactory.getLogger(SoftwareStatementsJerseyImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SOFTWARE_STATEMENTS_RESOURCE_PATH = "software-statements";

    /**
     * JAX-RS client.
     */
    private final Client client;

    /**
     * Client API Management API's base URL.
     */
    private final String baseUrl;

    /**
     * API key to use to authorize a request against KB API store.
     */
    private final String apiKey;

    /**
     * New instance.
     *
     * @param baseUrl backend application's base URI
     * @param apiKey  API key to use to authorize a request against KB API store
     */
    public SoftwareStatementsJerseyImpl(String baseUrl, String apiKey) {
        this(baseUrl, apiKey, ClientBuilder.newClient());
    }

    /**
     * New instance.
     *
     * @param baseUrl backend application's base URI
     * @param apiKey  API key to use to authorize a request against KB API store
     * @param client  JAX-RS client
     */
    public SoftwareStatementsJerseyImpl(String baseUrl, String apiKey, Client client) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new IllegalArgumentException("baseUrl must not be empty or blank");
        }
        if (StringUtils.isBlank(apiKey)) {
            throw new IllegalArgumentException("apiKey must not be empty or blank");
        }
        if (client == null) {
            throw new IllegalArgumentException("client must not be null");
        }

        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.client = client;
    }

    @Override
    public Jwt softwareStatement(SoftwareStatement request) {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }

        WebTarget webTarget = getClient().target(getBaseUrl()).path(SOFTWARE_STATEMENTS_RESOURCE_PATH);
        String correlationId = UUID.randomUUID().toString();
        log.debug("Call resource '{}' with correlation id '{}'.", webTarget.getUri(), correlationId);

        try {
            String jwt = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .header(CORRELATION_ID_HEADER_NAME, correlationId)
                    .header(API_KEY_HEADER_NAME, "Bearer " + getApiKey())
                    .header(HttpHeaders.ACCEPT, "*/*")
                    .post(Entity.json(mapToSoftwareStatementRequest(request)), String.class);

            return new Jwt(jwt);
        } catch (Exception e) {
            log.error("Calling of resource ends with error. Error: " + e.getMessage(), e);
            throw parseException(e);
        }
    }

    /**
     * Maps {@link SoftwareStatement} to the {@link SoftwareStatementRequest}.
     *
     * @param softwareStatement {@link SoftwareStatement}
     * @return {@link SoftwareStatementRequest}
     */
    private SoftwareStatementRequest mapToSoftwareStatementRequest(SoftwareStatement softwareStatement) {
        if (softwareStatement == null) {
            throw new IllegalArgumentException("softwareStatement must not be null");
        }

        SoftwareStatementRequest softwareStatementRequest = new SoftwareStatementRequest();
        softwareStatementRequest.setSoftwareName(softwareStatement.getSoftwareName());
        softwareStatementRequest.setSoftwareNameEn(softwareStatement.getSoftwareNameEn());
        softwareStatementRequest.setSoftwareId(softwareStatement.getSoftwareId());
        softwareStatementRequest.setSoftwareVersion(softwareStatement.getSoftwareVersion());
        softwareStatementRequest.setSoftwareUri(softwareStatement.getSoftwareUri());
        List<ResponseTypesEnum> responseTypes = new ArrayList<>();
        if (softwareStatement.getResponseTypes() != null) {
            softwareStatement.getResponseTypes().forEach(responseType ->
                    responseTypes.add(ResponseTypesEnum.fromValue(responseType.getResponseType())));
        }
        softwareStatementRequest.setResponseTypes(responseTypes);
        List<GrantTypesEnum> grantTypes = new ArrayList<>();
        if (softwareStatement.getGrantTypes() != null) {
            softwareStatement.getGrantTypes().forEach(grantType ->
                    grantTypes.add(GrantTypesEnum.fromValue(grantType.getGrantType())));
        }
        softwareStatementRequest.setGrantTypes(grantTypes);
        if (softwareStatement.getTokenEndpointAuthMethod() != null) {
            softwareStatementRequest.setTokenEndpointAuthMethod(
                    TokenEndpointAuthMethodEnum.fromValue(
                            softwareStatement.getTokenEndpointAuthMethod().getAuthMethod()));
        }
        softwareStatementRequest.setRedirectUris(softwareStatement.getRedirectUris());
        softwareStatementRequest.setContacts(softwareStatement.getContacts());
        softwareStatementRequest.setRegistrationBackUri(softwareStatement.getRegistrationBackUri());
        softwareStatementRequest.setLogoUri(softwareStatement.getLogoUri());
        softwareStatementRequest.setPolicyUri(softwareStatement.getPolicyUri());
        softwareStatementRequest.setTosUri(softwareStatement.getTosUri());

        return softwareStatementRequest;
    }

    /**
     * Parses exceptions thrown during calling backend application
     * and re-throws it as {@link ClientRegistrationClientException}.
     *
     * @param exception any exception thrown during calling backend application
     * @return {@link ClientRegistrationClientException}
     */
    private ClientRegistrationClientException parseException(Exception exception) {
        if (exception == null) {
            throw new IllegalArgumentException("exception must not be null");
        }

        ClientRegistrationClientException result;

        String errorMessage = "Error occurred during calling API. Error: ";
        if (exception instanceof WebApplicationException) {
            Response response = ((WebApplicationException) exception).getResponse();
            String errorBody = response.readEntity(String.class);
            try {
                ErrorResponse errorResponse = objectMapper.readValue(errorBody, ErrorResponse.class);
                if (errorResponse.getErrors().size() == 0) {
                    errorMessage += response.getStatusInfo().getReasonPhrase();
                } else if (errorResponse.getErrors().size() == 1) {
                    Error error = errorResponse.getErrors().get(0);
                    errorMessage += error.getErrorCode() + " " + error.getDescription();
                } else {
                    errorMessage = "More then one error (" + errorResponse.getErrors().size() +
                            ") occurred during calling API.";
                }
                result = new ClientRegistrationClientException(errorMessage, errorResponse.getErrors());
            } catch (IOException e) {
                result = new ClientRegistrationClientException(errorMessage
                        + response.getStatusInfo().getReasonPhrase());
            }
        } else {
            result = new ClientRegistrationClientException(errorMessage + exception.getMessage());
        }
        return result;
    }

    private Client getClient() {
        return client;
    }

    private String getBaseUrl() {
        return baseUrl;
    }

    private String getApiKey() {
        return apiKey;
    }
}
