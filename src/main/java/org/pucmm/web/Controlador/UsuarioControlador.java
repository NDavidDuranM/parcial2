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

public class UsuarioControlador {

    private Javalin app;
    Map<String, Object> modelo = new HashMap<>();

    public UsuarioControlador(Javalin app)
    {
        this.app = app;
    }

    public void aplicarRutas() throws NumberFormatException {

        app.get("/usuario/registrarse",ctx ->{
            ctx.render("/vistas/templates/registro.html");
        });

        app.post("/usuario/registrarse",ctx ->{

            if(UsuarioServices.getInstancia().getUsuario(ctx.formParam("usuario")) != null)
            {
                ctx.result("El usuario ya existe");
            }else {
                Usuario user = new Usuario(ctx.formParam("usuario"), ctx.formParam("password"), ctx.formParam("nombre"), RolesApp.ROLE_USUARIO);

                if (UsuarioServices.getInstancia().registrarUsuario(user) != null) {
                    ctx.redirect("/");
                } else {
                    ctx.redirect("/usuario/registrarse");
                }
            }
        });

        app.post("/usuario/registrarse-dashboard",ctx ->{

           System.out.println(ctx.formParam("admin"));
            if(ctx.sessionAttribute("usuario") == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                if (UsuarioServices.getInstancia().getUsuario(ctx.formParam("usuario")) != null) {
                    ctx.result("El usuario ya existe");
                } else {
                    Usuario user = new Usuario(ctx.formParam("usuario"), ctx.formParam("password"), ctx.formParam("nombre"), RolesApp.ROLE_USUARIO);
                    if (ctx.formParam("admin")!=null){user.setRol(RolesApp.ROLE_ADMIN);}

                    if (UsuarioServices.getInstancia().registrarUsuario(user) != null) {
                        ctx.redirect("/dashboard/usuarios");
                    } else {
                        ctx.redirect("/usuario/crear");
                    }
                }
            }
        });


        app.get("/usuario/iniciarSesion",ctx ->{

            if(ctx.cookie("usuario_recordado") != null)
            {
                Usuario user = UsuarioServices.getInstancia().getUsuario(ctx.cookie("usuario_recordado"));
                if(user != null)
                {
                    ctx.sessionAttribute("usuario", user);
                    ctx.sessionAttribute("vistaUsuario", user.getNombreUsuario());
                    ctx.redirect("/dashboard");
                }else
                {
                    ctx.result("El usuario guardado ya no se encuentra disponible");
                    Cookie cookie_usuario = new Cookie("usuario_recordado",ctx.formParam(""));
                    cookie_usuario.setMaxAge(0);
                    ctx.res.addCookie(cookie_usuario);
                }
            }
            else
            {
                ctx.render("/vistas/templates/login.html");
            }

        });

        app.get("/usuario/cerrarSesion",ctx ->{

            Cookie cookie_usuario = new Cookie("usuario_recordado",ctx.formParam(""));
            Cookie cookie_password = new Cookie("password_recordado",ctx.formParam(""));
            cookie_usuario.setMaxAge(0);
            cookie_password.setMaxAge(0);
            ctx.res.addCookie(cookie_usuario);
            ctx.res.addCookie(cookie_password);

            if(ctx.req.getSession() != null)
            {
                ctx.sessionAttribute("usuario", null);
                ctx.sessionAttribute("vistaUsuario", null);
                ctx.req.getSession().invalidate();
            }

            ctx.redirect("/");

        });


        app.post("/usuario/iniciarSesion",ctx ->{

            Usuario usuario = UsuarioServices.getInstancia().getUsuario(ctx.formParam("nombreUsuario"));

            if (usuario != null) { //Si el usuario existe...
                if (!usuario.getPassword().equals(ctx.formParam("password"))) { //Si sus credenciales NO son correctas...
                    ctx.redirect("/usuario/iniciarSesion");
                }
                else{ //Si las credenciales del usuario son correctas...

                    //Guardamos el usuario en una cookie
                    if(ctx.formParam("recordar") != null)
                    {
                        if(ctx.formParam("recordar").equals("on"))
                        {

                            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                            String encryptedPassword = passwordEncryptor.encryptPassword(ctx.formParam("password"));

                            Cookie cookie_usuario = new Cookie("usuario_recordado",ctx.formParam("nombreUsuario"));
                            Cookie cookie_password = new Cookie("password_recordado",encryptedPassword);
                            cookie_usuario.setMaxAge(604800); //una semana
                            cookie_password.setMaxAge(604800);

                            ctx.res.addCookie(cookie_usuario);
                            ctx.res.addCookie(cookie_password);
                        }
                    }

                    //ctx.sessionAttribute("usuario", ctx.formParam("nombreUsuario"));
                    ctx.sessionAttribute("usuario", usuario);
                    ctx.sessionAttribute("vistaUsuario", ctx.formParam("nombreUsuario")); //Una variable separada para ver a un usuario diferente
                    ctx.redirect("/");
                }
            }else //Si el usuario no existe...
            {
                ctx.result("Este usuario no se encuentra registrado");
            }
        });

        app.get("/dashboard/usuarios", ctx -> {

            if(ctx.sessionAttribute("usuario") == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                Usuario tmp = ctx.sessionAttribute("usuario");
                modelo.put("usuarioActual", tmp.getNombreUsuario());
                modelo.put("usuarios", UsuarioServices.getInstancia().getAllUsuarios());

                if (modelo.get("selected") == null) {
                    modelo.put("selected", new Usuario("", "", "", RolesApp.ROLE_USUARIO));
                }
                ctx.render("/vistas/templates/users.html", modelo);
            }
        });


        app.get("/usuario/crear",ctx -> {
            if(ctx.sessionAttribute("usuario") == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                ctx.render("/vistas/templates/createUser.html", modelo);
            }
        });

        app.post("/usuario/verUsuario",ctx -> {
            if(ctx.sessionAttribute("usuario") == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                Usuario user = UsuarioServices.getInstancia().getUsuario(ctx.formParam("usuarioLista"));
                modelo.put("selected", user);
                ctx.redirect("/dashboard/usuarios");
            }
        });

        app.post("/usuario/eliminar",ctx -> {
            UsuarioServices.getInstancia().eliminarUsuario(ctx.formParam("eliminar"));
            modelo.put("selected", new Usuario("", "", "", RolesApp.ROLE_USUARIO));
            ctx.redirect("/dashboard/usuarios");
        });

        app.post("usuario/editar",ctx -> {
            Usuario tmp = ctx.sessionAttribute("usuario");
            if(ctx.sessionAttribute("usuario") == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                if (modelo.get("usuarioActual") == "admin") {
                    UsuarioServices.getInstancia().editarUsuario(
                            ctx.formParam("usuario"), ctx.formParam("nombre"), ctx.formParam("password"), RolesApp.ROLE_ADMIN);
                } else {
                    if(ctx.formParam("admin") != null)
                    {
                         UsuarioServices.getInstancia().editarUsuario(ctx.formParam("usuario"), ctx.formParam("nombre"), ctx.formParam("password"), RolesApp.ROLE_ADMIN);
                    }else
                    {
                        UsuarioServices.getInstancia().editarUsuario(ctx.formParam("usuario"), ctx.formParam("nombre"), ctx.formParam("password"), RolesApp.ROLE_USUARIO);
                    }
                }

                ctx.redirect("/dashboard/usuarios");
                //modelo.put("selected", new Usuario("", "", "", false));
            }
        });

    }

}
