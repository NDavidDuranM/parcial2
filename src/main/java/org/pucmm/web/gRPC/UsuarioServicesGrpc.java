package org.pucmm.web.gRPC;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.pucmm.web.Modelo.URLs;
import org.pucmm.web.Modelo.UrlDTO;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.UsuarioServices;
import org.pucmm.web.Servicio.URLServices;
import usuariorn.UsuarioRn;
import usuariorn.UsuariornGrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

public class UsuarioServicesGrpc extends UsuariornGrpc.UsuariornImplBase {

    //Instancia del servicio.
    private final UsuarioServices usuarioServices = UsuarioServices.getInstancia();
    private final URLServices urlServices = URLServices.getInstance();

    @Override
    public void registrarURL(UsuarioRn.RegistrarURLRequest request, StreamObserver<UsuarioRn.RegistrarURLResponse> responseObserver) {

        String nombreUsuario = request.getNombreUsuario(); //Nombre de usuario.
        String origen = request.getOrigen(); //Enlace original.

        Usuario usuario = usuarioServices.getUsuario(nombreUsuario);
        URLs nuevoUrl = urlServices.nuevaUrlAcortada(origen);
        UrlDTO nuevoUrlDTO = new UrlDTO();

        nuevoUrlDTO.setOrigen(nuevoUrl.getOrigen());
        nuevoUrlDTO.setDireccionAcortada(nuevoUrl.getDireccionAcortada());
        nuevoUrlDTO.setNombreUsuario(usuario.getNombreUsuario());
        nuevoUrlDTO.setFechaCreacion(nuevoUrl.getFechaCreacion());

        String urlToRead = "https://api.linkpreview.net/?key=67db3f3aa5eeca9cf8b7246e84ba5cd4&q=" + nuevoUrlDTO.getOrigen();
        StringBuilder result = new StringBuilder();

        URL url = null;
        try {
            url = new URL(urlToRead);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            assert url != null; // Por si acaso
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject2 = new JSONObject(result.toString());
        nuevoUrlDTO.B64preview = getByteArrayFromImageURL(jsonObject2.get("image").toString());

        responseObserver.onNext(UsuarioRn.RegistrarURLResponse.newBuilder().setResponse(new Gson().toJson(nuevoUrlDTO)).build());
        responseObserver.onCompleted();
    }

    private String getByteArrayFromImageURL(String url) {

        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void getURL(UsuarioRn.GetURLRequest request, StreamObserver<UsuarioRn.GetUrlResponse> responseObserver) {
        ModelMapper modelMapper = new ModelMapper();
        List<UrlDTO> urlDTO = modelMapper.map(usuarioServices.getUsuario(request.getNombreUsuario()).getUrls(), new TypeToken<List<UrlDTO>>(){}.getType());

        responseObserver.onNext(UsuarioRn.GetUrlResponse.newBuilder().setResponse(new Gson().toJson(urlDTO)).build());
        responseObserver.onCompleted();
    }
}
