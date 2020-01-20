package cz.kb.openbanking.clientregistration.client.api;

import cz.kb.openbanking.clientregistration.client.api.model.Jwt;
import cz.kb.openbanking.clientregistration.client.api.model.SoftwareStatement;

/**
 * Software statement API.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public interface SoftwareStatementsApi {

    /**
     * Software statement.
     *
     * @param request {@link SoftwareStatement}
     * @return JWT token with software statement that should be used during client registration
     */
    Jwt softwareStatement(SoftwareStatement request);
}
