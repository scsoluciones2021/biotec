package rpt.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String PACIENTE = "ROLE_PACIENTE";
    
    public static final String MEDICO = "ROLE_MEDICO";

    private AuthoritiesConstants() {
    }
}
