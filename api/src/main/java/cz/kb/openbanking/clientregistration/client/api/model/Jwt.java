package cz.kb.openbanking.clientregistration.client.api.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents JWT token.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public class Jwt {

    /**
     * JWT token.
     */
    private final String token;

    /**
     * New instance.
     *
     * @param token JWT in a string representation
     */
    public Jwt(String token) {
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token must not be empty or blank");
        }

        this.token = token;
    }

    /**
     * Gets JWT token.
     *
     * @return JWT token
     */
    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Jwt)) {
            return false;
        }

        final Jwt jwt = (Jwt) o;

        return new EqualsBuilder()
                .append(getToken(), jwt.getToken())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getToken())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .toString();
    }
}
