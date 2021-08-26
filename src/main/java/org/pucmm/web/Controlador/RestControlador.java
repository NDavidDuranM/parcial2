package org.pucmm.web.Controlador;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import kong.unirest.Unirest;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.pucmm.web.Modelo.URL;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.URLServices;
import org.pucmm.web.Servicio.UsuarioServices;
import org.pucmm.web.util.RolesApp;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RestControlador {

    private Javalin app;
    Map<String, Object> modelo = new HashMap<>();

    public RestControlador(Javalin app)
    {
        this.app = app;
    }

    public void aplicarRutas() throws NumberFormatException {

        app.get("/api-rest/url/:usuario",ctx ->{
            ctx.json(UsuarioServices.getInstancia().getURLsByUsuario(ctx.pathParam("usuario")));
        });

        app.post("/api-rest/login", ctx -> {
            Usuario usuario = UsuarioServices.getInstancia().getUsuario(ctx.queryParam("nombreUsuario"));
            if (usuario != null) {
                if (usuario.getPassword().equals(ctx.queryParam("password"))) { //Si sus credenciales NO son correctas...
                    ctx.sessionAttribute("usuario", usuario);
                }else{
                    ctx.result("Clave no coincide");

                }
            }else{ctx.status(404);}
            ctx.status(200);
        });

        app.post("/api-rest/url",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            if (user != null) {
                URL url = URLServices.getInstance().nuevaUrlAcortada(ctx.queryParam("url"));
                URLServices.getInstance().registrarURLUsuario(user.getNombreUsuario(), url);

                //get imagen con unirest
                String json = Unirest.get("https://api.microlink.io?url=" + url.getOrigen() + "&screenshot=true&meta=false")
                        .asJson().getBody().getObject().getJSONObject("data")
                        .getJSONObject("screenshot").get("url").toString();
                try { //codifico imagen a b64
                    java.net.URL aux = new java.net.URL(json);
                    BufferedImage image = ImageIO.read(aux);
                    ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", dataStream);
                    json = Base64.getEncoder().encodeToString(dataStream.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //agrego imagen a objeto URL y lo envio al cliente
                url.B64preview = json;
                ctx.json(url);
            }else{ctx.result("necesita iniciar sesion");}
        });

    }

}
