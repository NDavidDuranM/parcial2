package org.pucmm.web.Servicio;

import kong.unirest.Unirest;
import org.pucmm.web.Modelo.Cliente;
import org.pucmm.web.Modelo.URL;
import org.pucmm.web.Modelo.Usuario;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Set;


@WebService
public class SoapServices{
    private URLServices UrlService = URLServices.getInstance();
    private UsuarioServices usuarioService = UsuarioServices.getInstancia();
    private Usuario usuario = null;

    @WebMethod
    public Set<URL> getEnlacesByUser(String nombreUsuario){
        return usuarioService.getURLsByUsuario(nombreUsuario);
    }

    @WebMethod
    public boolean login(String nombreUsuario, String clave){
        Usuario tmp = usuarioService.getUsuario(nombreUsuario);
        if (tmp!=null){
            if (tmp.getPassword().equals(clave)){
                usuario = tmp;
                return true;
            }else{return false;}
        }else{return false;}

    }

    @WebMethod
    public URL registrarEnlace(String direccion){
        URL url = URLServices.getInstance().nuevaUrlAcortada(direccion);
        URLServices.getInstance().registrarURLUsuario(usuario.getNombreUsuario(), url);

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

        return url;
    }

    @WebMethod
    public Usuario getUsuario(){return this.usuario;}

    @WebMethod
    public String getFechaString(URL url){return url.getFechaCreacion().toString();}

}
