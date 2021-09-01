package org.pucmm.web;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import org.pucmm.web.Controlador.*;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.Servicio.BootStrapServices;
import org.pucmm.web.Servicio.UsuarioServices;
import org.pucmm.web.util.RolesApp;

import java.io.IOException;
import java.sql.SQLException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import org.pucmm.web.gRPC.UsuarioServicesGrpc;

public class Main {

    private static String modoConexion = "";

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
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

        /*
            Para probar el servidor gRPC.
        */
        //Puerto del servidor.
        int port = 50051;

        //Inicializando el servidor
        Server server = ServerBuilder.forPort(port)
                .addService(new UsuarioServicesGrpc())// indicando el servicio registrado.
                .build()
                .start();
        System.out.println("Servidor gRPC iniciando y escuchando en " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Cerrando servidor por la JVM. ");
            if (server != null) {
                server.shutdown();
            }
            System.err.println("Servidor abajo!...");
        }));
        server.awaitTermination();
    }
}
