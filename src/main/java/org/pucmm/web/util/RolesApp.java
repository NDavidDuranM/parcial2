package org.pucmm.web.util;

import io.javalin.core.security.Role;

/**
 * Enum para manejar los roles de la aplicacion.
 */
public enum RolesApp implements Role {
    ROLE_USUARIO,
    ROLE_ADMIN;
}
