package cz.kb.openbanking.clientregistration.client.api.model;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents client's software statement.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public class SoftwareStatement {
    private final String softwareName;
    private String softwareNameEn;
    private final String softwareId;
    private final String softwareVersion;
    private String softwareUri;
    private final List<String> redirectUris;
    private TokenEndpointAuthMethodEnum tokenEndpointAuthMethod;
    private List<GrantTypesEnum> grantTypes;
    private List<ResponseTypesEnum> responseTypes;
    private final String registrationBackUri;
    private List<String> contacts;
    private String logoUri;
    private String tosUri;
    private String policyUri;

    /**
     * New instance (contains only required parameters).
     *
     * @param softwareName        name of a software
     * @param softwareId          software ID
     * @param softwareVersion     current software version
     * @param redirectUris        list of redirect URIs
     * @param registrationBackUri URI to which redirect callback with software statement will be sent
     */
    public SoftwareStatement(String softwareName, String softwareId, String softwareVersion,
                             Collection<String> redirectUris, String registrationBackUri) {
        if (StringUtils.isBlank(softwareName)) {
            throw new IllegalArgumentException("softwareName must not be empty");
        }
        if (StringUtils.isBlank(softwareId)) {
            throw new IllegalArgumentException("softwareId must not be empty");
        }
        if (StringUtils.isBlank(softwareVersion)) {
            throw new IllegalArgumentException("softwareVersion must not be empty");
        }
        if (redirectUris.isEmpty()) {
            throw new IllegalArgumentException("redirectUris must not be empty");
        }
        if (StringUtils.isBlank(registrationBackUri)) {
            throw new IllegalArgumentException("registrationBackUri must not be empty");
        }

        this.softwareName = softwareName;
        this.softwareId = softwareId;
        this.softwareVersion = softwareVersion;
        this.redirectUris = new ArrayList<>(redirectUris);
        this.registrationBackUri = registrationBackUri;
    }

    public void setSoftwareNameEn(String softwareNameEn) {
        this.softwareNameEn = softwareNameEn;
    }

    public void setSoftwareUri(String softwareUri) {
        this.softwareUri = softwareUri;
    }

    public void setTokenEndpointAuthMethod(TokenEndpointAuthMethodEnum tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    public void setGrantTypes(Collection<GrantTypesEnum> grantTypes) {
        this.grantTypes = new ArrayList<>(grantTypes);
    }

    public void setResponseTypes(Collection<ResponseTypesEnum> responseTypes) {
        this.responseTypes = new ArrayList<>(responseTypes);
    }

    public void setContacts(Collection<String> contacts) {
        this.contacts = new ArrayList<>(contacts);
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public void setTosUri(String tosUri) {
        this.tosUri = tosUri;
    }

    public void setPolicyUri(String policyUri) {
        this.policyUri = policyUri;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    @Nullable
    public String getSoftwareNameEn() {
        return softwareNameEn;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    @Nullable
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @Nullable
    public String getSoftwareUri() {
        return softwareUri;
    }

    public List<String> getRedirectUris() {
        return redirectUris == null ? null : Collections.unmodifiableList(redirectUris);
    }

    @Nullable
    public TokenEndpointAuthMethodEnum getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    @Nullable
    public List<GrantTypesEnum> getGrantTypes() {
        return grantTypes == null ? null : Collections.unmodifiableList(grantTypes);
    }

    @Nullable
    public List<ResponseTypesEnum> getResponseTypes() {
        return responseTypes == null ? null : Collections.unmodifiableList(responseTypes);
    }

    public String getRegistrationBackUri() {
        return registrationBackUri;
    }

    @Nullable
    public List<String> getContacts() {
        return contacts == null ? null : Collections.unmodifiableList(contacts);
    }

    @Nullable
    public String getLogoUri() {
        return logoUri;
    }

    @Nullable
    public String getTosUri() {
        return tosUri;
    }

    @Nullable
    public String getPolicyUri() {
        return policyUri;
    }
}
