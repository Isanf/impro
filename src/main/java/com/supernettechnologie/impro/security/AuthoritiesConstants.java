package com.supernettechnologie.impro.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String CONCESSIONNAIRE = "CONCESSIONNAIRE";

    public static final String REVENDEUR = "REVENDEUR";

    public static final String STHGUICHET = "STHGUICHET";

    public static final String DGTTM = "DGTTM";

    public static final String DOUANE = "DOUANE";

    public static final String DG_DGTTM = "DG_DGTTM";

    public static final String DG_DOUANE = "DG_DOUANE";

    public static final String FIRSTLOGIN_OK = "FIRSTLOGIN_OK";

    private AuthoritiesConstants() {
    }
}
