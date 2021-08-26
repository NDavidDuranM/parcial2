package org.pucmm.web;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import org.pucmm.web.Controlador.*;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.BootStrapServices;
import org.pucmm.web.Servicio.UsuarioServices;
import org.pucmm.web.util.RolesApp;
import java.sql.SQLException;

public class Main {

    private static String modoConexion = "";

    public static void main(String[] args) throws SQLException {
        //Iniciando la BD
        BootStrapServices.getInstancia().init();

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/vistas");
            config.registerPlugin(new RouteOverviewPlugin("/rutas"));
            config.enableCorsForAllOrigins();
        });
        new SoapControlador(app).aplicarRutas();
        app.start(7000);

        //agregando default users.
        if(UsuarioServices.getInstancia().getUsuario("admin") == null){
            UsuarioServices.getInstancia().registrarUsuario(new Usuario("admin","admin","admin", RolesApp.ROLE_ADMIN));
            UsuarioServices.getInstancia().registrarUsuario(new Usuario("user","user","user", RolesApp.ROLE_USUARIO));
        }


        //Clases gestoras de rutas
        new DashboardControlador(app).aplicarRutas();
        new URLControlador(app).aplicarRutas();
        new UsuarioControlador(app).aplicarRutas();
        new RestControlador(app).aplicarRutas();


    }


}
