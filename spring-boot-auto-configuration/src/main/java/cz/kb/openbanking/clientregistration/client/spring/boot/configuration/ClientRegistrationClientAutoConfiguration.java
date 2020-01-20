package cz.kb.openbanking.clientregistration.client.spring.boot.configuration;

import cz.kb.openbanking.clientregistration.client.jersey.SoftwareStatementsJerseyImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Client Registration API client's Spring Boot auto-configuration.
 * This configuration is depended on Jersey implementation provided by 'jersey-impl' artifact
 * and only prepares it for using in a Spring application.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @see ClientRegistrationClientProperties
 * @since 1.0
 */
@Configuration
@PropertySource(value = "classpath:client-registration-client-config.properties")
@EnableConfigurationProperties(ClientRegistrationClientProperties.class)
public class ClientRegistrationClientAutoConfiguration {

    /**
     * Provides default Jersey JAX-RS client.
     *
     * @return {@link Client}
     */
    @Bean
    @ConditionalOnMissingBean
    public Client getClient() {
        return ClientBuilder.newClient();
    }

    /**
     * Provides {@link SoftwareStatementsJerseyImpl} based on {@link ClientRegistrationClientProperties}.
     *
     * @param clientProperties properties of the Client Registration API client
     * @param client           Jersey JAX-RS client
     * @return {@link SoftwareStatementsJerseyImpl}
     */
    @Bean
    @ConditionalOnMissingBean
    public SoftwareStatementsJerseyImpl getSoftwareStatementsJerseyImpl(
            ClientRegistrationClientProperties clientProperties, Client client) {
        Assert.notNull(clientProperties, "clientProperties must not be null");
        Assert.hasText(clientProperties.getBaseUri(), "baseUri must not be empty");
        Assert.hasText(clientProperties.getApiKey(), "apiKey must not be empty");
        Assert.notNull(client, "client must not be null");

        return new SoftwareStatementsJerseyImpl(clientProperties.getBaseUri(), clientProperties.getApiKey(), client);
    }
}
