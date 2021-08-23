package org.pucmm.web.Controlador;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.UsuarioServices;
import org.pucmm.web.util.RolesApp;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class WebServicesControlador {

    private Javalin app;
    Map<String, Object> modelo = new HashMap<>();

    public WebServicesControlador(Javalin app)
    {
        this.app = app;
    }

    public void aplicarRutas() throws NumberFormatException {

        app.get("/api-rest/url/:usuario",ctx ->{

            ctx.json(UsuarioServices.getInstancia().getUsuario(ctx.pathParam("usuario")));
            //ctx.json(UsuarioServices.getInstancia().getURLsByUsuario(ctx.pathParam("usuario")));
        });

        app.post("/api-rest/url/:url",ctx ->{

            if(UsuarioServices.getInstancia().getUsuario(ctx.formParam("usuario")) != null)
            {
                ctx.result("El usuario ya existe");
            }else {
                Usuario user = new Usuario(ctx.formParam("usuario"), ctx.formParam("password"), ctx.formParam("nombre"), RolesApp.ROLE_USUARIO);

                if (UsuarioServices.getInstancia().registrarUsuario(user) != null) {
                    ctx.redirect("/dashboard");
                } else {
                    ctx.redirect("/usuario/registrarse");
                }
            }
        });



    }

}
