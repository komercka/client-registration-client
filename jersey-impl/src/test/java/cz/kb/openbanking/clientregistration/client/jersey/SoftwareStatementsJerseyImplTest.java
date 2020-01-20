package cz.kb.openbanking.clientregistration.client.jersey;

import cz.kb.openbanking.clientregistration.client.api.SoftwareStatementsApi;
import cz.kb.openbanking.clientregistration.client.api.model.GrantTypesEnum;
import cz.kb.openbanking.clientregistration.client.api.model.Jwt;
import cz.kb.openbanking.clientregistration.client.api.model.ResponseTypesEnum;
import cz.kb.openbanking.clientregistration.client.api.model.SoftwareStatement;
import cz.kb.openbanking.clientregistration.client.api.model.TokenEndpointAuthMethodEnum;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.model.JsonSchemaBody;
import org.mockserver.verify.VerificationTimes;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;

/**
 * Test class for {@link SoftwareStatementsJerseyImpl}.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
class SoftwareStatementsJerseyImplTest extends AbstractJerseyClientTest {

    public static final String RESPONSE_RESOURCE_NAME = "response-software-statement.json";

    /**
     * Test for {@link SoftwareStatementsJerseyImpl#softwareStatement(SoftwareStatement)}.
     *
     */
    @Test
    void postSoftwareStatements() {
        configureServer("/software-statements", RESPONSE_RESOURCE_NAME, HttpMethod.POST, HttpStatusCode.CREATED_201);

        SoftwareStatementsApi softwareStatementsApi = new SoftwareStatementsJerseyImpl(MOCK_SERVER_URI, "apiKey");

        Jwt jwt = softwareStatementsApi.softwareStatement(getRequest());
        assertThat(jwt).isNotNull();
        assertThat(jwt.getToken()).isEqualTo(getResourceAsString(RESPONSE_RESOURCE_NAME));

        mockServer.verify(
                request()
                        .withPath("/software-statements")
                        .withMethod(HttpMethod.POST.name())
                        .withHeader("x-api-key", "Bearer apiKey")
                        .withHeader("x-correlation-id")
                        .withHeader("Accept", "application/json")
                        .withBody(JsonSchemaBody.jsonSchemaFromResource("request-software-statement.json")),
                VerificationTimes.exactly(1)
        );
    }

    private SoftwareStatement getRequest() {
        SoftwareStatement req = new SoftwareStatement("Nejlepší produkt", "f64bf2e447e545228c78e07b081a82ee", "1.0",
                Arrays.asList("https://client.example.org/callback", "https://client.example.org/callback-backup"),
                "https://client.example.org/backuri");
        req.setSoftwareNameEn("Best product");
        req.setSoftwareUri("https://client.example.org");
        req.setTokenEndpointAuthMethod(TokenEndpointAuthMethodEnum.CLIENT_SECRET_POST);
        req.setGrantTypes(Collections.singletonList(GrantTypesEnum.AUTHORIZATION_CODE));
        req.setResponseTypes(Collections.singletonList(ResponseTypesEnum.CODE));
        req.setContacts(Collections.singletonList("example@goodsoft.com"));
        req.setLogoUri("https://client.example.org/logo.png");
        req.setTosUri("https://client.example.org/tos");
        req.setPolicyUri("https://client.example.org/policy");

        return req;
    }
}