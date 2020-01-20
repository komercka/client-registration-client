package cz.kb.openbanking.clientregistration.client.api.model;

import org.apache.commons.lang3.StringUtils;

/**
 * String indicator of the requested authentication method for the token endpoint.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @see SoftwareStatement
 * @since 1.0
 */
public enum TokenEndpointAuthMethodEnum {
    CLIENT_SECRET_POST("client_secret_post");

    private String authMethod;

    TokenEndpointAuthMethodEnum(String authMethod) {
        if (StringUtils.isBlank(authMethod)) {
            throw new IllegalArgumentException("authMethod must not be empty");
        }

        this.authMethod = authMethod;
    }

    /**
     * Gets authentication method for the token endpoint.
     */
    public String getAuthMethod() {
        return authMethod;
    }
}
