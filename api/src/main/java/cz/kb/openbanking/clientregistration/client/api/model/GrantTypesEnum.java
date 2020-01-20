package cz.kb.openbanking.clientregistration.client.api.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Defines all possible authorization grant types.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public enum GrantTypesEnum {
    AUTHORIZATION_CODE("authorization_code");

    private String grantType;

    GrantTypesEnum(String grantType) {
        if (StringUtils.isBlank(grantType)) {
            throw new IllegalArgumentException("grantType must not be empty");
        }

        this.grantType = grantType;
    }

    /**
     * Gets grant type.
     *
     * @return grant type
     */
    public String getGrantType() {
        return grantType;
    }
}
