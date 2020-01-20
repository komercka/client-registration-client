package cz.kb.openbanking.clientregistration.client.api.exception;

import cz.kb.openbanking.clientregistration.client.model.generated.Error;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Basic exception that could be thrown by Client Registration API client.
 *
 * @author <a href="mailto:aleh_kuchynski@kb.cz">Aleh Kuchynski</a>
 * @since 1.0
 */
public class ClientRegistrationClientException extends RuntimeException {

    private final Collection<Error> errors = new LinkedList<>();

    /**
     * New instance.
     *
     * @param message error message
     */
    public ClientRegistrationClientException(String message) {
        super(message);
    }

    /**
     * New instance.
     *
     * @param message error message
     * @param errors  list of errors
     */
    public ClientRegistrationClientException(String message, Collection<Error> errors) {
        this(message);

        this.errors.addAll(errors);
    }

    /**
     * Get all {@link Error}s.
     *
     * @return list of {@link Error}s
     */
    public Collection<Error> getErrors() {
        return Collections.unmodifiableCollection(errors);
    }
}
