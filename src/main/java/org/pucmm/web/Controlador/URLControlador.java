package org.pucmm.web.Controlador;

import io.javalin.Javalin;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.URLServices;
import org.pucmm.web.Servicio.UsuarioServices;
import org.pucmm.web.util.RolesApp;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class URLControlador {

    private Javalin app;
    private String dominio = "https://shorter.jgshopping.games/";

    Map<String, Object> modelo = new HashMap<>();

    public URLControlador(Javalin app)
    {
        this.app = app;
        modelo.put("dominio",dominio);
    }

    public void aplicarRutas() throws NumberFormatException {

        app.get("/",ctx -> {
            Usuario tmp = ctx.sessionAttribute("usuario");
            isUserLogged(modelo,tmp);
            ctx.render("vistas/templates/index.html",modelo);
        });

        app.post("/acortar", ctx -> {
            Cookie cookie_url = new Cookie(URLServices.getInstance().crearRetornarUrlAcortada(ctx.formParam("url")),ctx.formParam("url"));
            cookie_url.setMaxAge(3600*24*30*6); //6 meses
            ctx.res.addCookie(cookie_url);
            ctx.redirect("/");
        });

        app.post("/acortar-registrar", ctx -> {
            if(ctx.sessionAttribute("usuario") == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                URLServices.getInstance().registrarURLUsuario(ctx.sessionAttribute("usuario"), URLServices.getInstance().nuevaUrlAcortada(ctx.formParam("url")));
                URLServices.getInstance().getUrlsCliente().clear();
                ctx.redirect("/dashboard");
            }
        });

        app.post("/url/eliminar", ctx -> {
            Usuario tmp = ctx.sessionAttribute("usuario");
            if(tmp == null)
            {
                ctx.redirect("/usuario/iniciarSesion");
            }else {
                URLServices.getInstance().eliminarURL(ctx.sessionAttribute("vistaUsuario"), ctx.formParam("eliminar"));
                ctx.redirect("/misLinks");
            }
        });

        app.get("/misLinks", ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            if (user != null){
                modelo.put("urls",UsuarioServices.getInstancia().getURLsByUsuario(user.getNombreUsuario()));
            }else{
                modelo.put("urls", URLServices.getInstance().getUrlsCliente());
            }
            ctx.render("vistas/templates/misLinks.html", modelo);
        });

        app.get("/:url",ctx -> {

            if(!ctx.pathParam("url").equalsIgnoreCase("favicon.ico"))
            {
                ctx.redirect("redireccionar/"+ctx.pathParam("url"));
            }
        });

        app.get("/redireccionar/:url", ctx -> {

            //Obteniendo el cliente (navegador) desde donde se accede
            //String[] InfoNavegador = ctx.userAgent();
            //String[] DatosCliente = InfoNavegador[1].split(";");
            String nombreCliente = ctx.userAgent();
            LocalDate fechaAcceso = LocalDate.now();
            LocalTime horaAcceso = LocalTime.now();



            //Sistema operativo
            String os = System.getProperty("os.name");

            URLServices.getInstance().visitar(ctx.pathParam("url"),nombreCliente,ctx.host(),fechaAcceso, horaAcceso, os);
            String direccion = URLServices.getInstance().expandirURL(ctx.pathParam("url"));
            if(direccion.contains("https:"))
            {
                ctx.redirect(URLServices.getInstance().expandirURL(ctx.pathParam("url")));
            }else if(!direccion.contains("http:"))
            {
                ctx.redirect("http://"+ URLServices.getInstance().expandirURL(ctx.pathParam("url")));
            }

        });

    }

    private void isUserLogged(Map<String, Object> modelo, Usuario tmp){
        if(tmp!=null){
            modelo.put("loggedIn",1);
            if (tmp.getRol() == RolesApp.ROLE_ADMIN){
                modelo.put("isAdmin", 1);
            }else{
                modelo.put("isAdmin", 0);
            }
        }else{
            modelo.put("loggedIn",0);
        }
    }

}
