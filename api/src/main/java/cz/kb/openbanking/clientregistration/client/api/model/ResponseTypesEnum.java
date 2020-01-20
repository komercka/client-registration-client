package cz.kb.openbanking.clientregistration.client.api.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Defines all possible response types.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public enum ResponseTypesEnum {
    CODE("code");

    private String responseType;

    ResponseTypesEnum(String responseType) {
        if (StringUtils.isBlank(responseType)) {
            throw new IllegalArgumentException("responseType must not be empty");
        }

        this.responseType = responseType;
    }

    /**
     * Gets response type.
     *
     * @return response type
     */
    public String getResponseType() {
        return responseType;
    }
}
