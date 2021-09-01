package org.pucmm.web.Controlador;

import io.javalin.Javalin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kong.unirest.Unirest;
import org.pucmm.web.Modelo.URLs;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.URLServices;
import org.pucmm.web.Servicio.UsuarioServices;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RestControlador {

    private Javalin app;
    public final static String KEY = "tres tristes tigres tragaban trigo en un trigal";
    //private final static Key KEY = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.ES256);
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
                if (usuario.getPassword().equals(ctx.queryParam("password"))) {
                    ctx.sessionAttribute("usuario", usuario);
                }else{
                    ctx.result("Clave no coincide").status(400);
                }
            }else{ctx.status(404);}
            ctx.status(200).result(generarJWT(usuario));
        });

        app.before("/api-rest/url", ctx -> {
            String token = ctx.header("Authorization");
            if(token == null){
                ctx.status(400).result("Token error");
            }
            String jwt = token.replace("Bearer", "");
            try {
                Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                //Claims claims = Jwts.parser().setSigningKey(KEY)
                        .parseClaimsJws(jwt).getBody();

            }catch (io.jsonwebtoken.JwtException e){
                ctx.status(400).result("Token error");
            }
        });

        app.post("/api-rest/url",ctx -> {
            Usuario user = ctx.sessionAttribute("usuario");
            if (user != null) {
                URLs url = URLServices.getInstance().nuevaUrlAcortada(ctx.queryParam("url"));
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

    private static String generarJWT(Usuario usuario){
        LocalDateTime ldt = LocalDateTime.now();
        Date exp = Date.from(ldt.toInstant(ZoneOffset.ofHours(-12)));
        return Jwts.builder().setIssuer("Grupo 9").setSubject("Proyecto final")
                .setExpiration(exp)
                .claim("usuario", usuario.getNombreUsuario())
                .claim("rol", usuario.getRol())
                //.signWith(KEY)
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
    }

}
