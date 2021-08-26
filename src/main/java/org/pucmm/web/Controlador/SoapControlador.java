package org.pucmm.web.Controlador;

import com.sun.net.httpserver.HttpContext;
import io.javalin.Javalin;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.pucmm.web.Servicio.SoapServices;
import javax.xml.ws.Endpoint;
import java.lang.reflect.Method;

public class SoapControlador{
    private Javalin app;

    public SoapControlador(Javalin app){
        this.app = app;
    }

    public void aplicarRutas(){
        Server server = app.server().server();
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        server.setHandler(contextHandlerCollection);

        try {
            System.out.println("IM WALKING HERE");
            HttpContext context = build(server, "/ws");
            SoapServices wsa = new SoapServices();
            Endpoint endpoint = javax.xml.ws.Endpoint.create(wsa);
            endpoint.publish(context);
            // Para acceder al wsdl en http://localhost:7000/ws/SoapServices?wsdl
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private HttpContext build(Server server, String contextString) throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true);
        JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(contextString);
        Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
        method.setAccessible(true);
        HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
        contextHandler.start();
        return ctx;
    }
}