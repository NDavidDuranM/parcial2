package org.pucmm.web.Servicio;

import org.pucmm.web.Modelo.URLs;
import org.pucmm.web.Modelo.Usuario;
import org.pucmm.web.util.RolesApp;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

public class UsuarioServices {

    private static UsuarioServices instancia;
    private GestionDb gestionDb;

    public UsuarioServices(){
        gestionDb = new GestionDb(Usuario.class);

        //Creando el admin
        if(gestionDb.find("admin")== null)
        {
            Usuario admin = new Usuario("admin","admin","Administrador", RolesApp.ROLE_ADMIN);
            gestionDb.crear(admin);
        }

    }

    public static UsuarioServices getInstancia()
    {
        if(instancia == null)
        {
            instancia = new UsuarioServices();
        }
        return instancia;
    }

    public Usuario registrarUsuario(Usuario user)
    {
        gestionDb.crear(user);

        return user;
    }
    public void eliminarUsuario(Usuario user)
    {
        gestionDb.eliminar(user);
    }

    public Usuario getUsuario(String idUser)
    {
        return (Usuario) gestionDb.find(idUser);
    }

    public List<Usuario> getAllUsuarios()
    {
        return gestionDb.findAll();
    }

    public Set<URLs> getURLsByUsuario(String idUser)
    {
        return ((Usuario) gestionDb.find(idUser)).getUrls();
    }

    public void cambiaCredencial(String nombreUsuario)
    {
        Usuario user = (Usuario) gestionDb.find(nombreUsuario);
        if(user.getRol() == RolesApp.ROLE_ADMIN)
        {
            user.setRol(RolesApp.ROLE_USUARIO);
        }else
        {
            user.setRol(RolesApp.ROLE_ADMIN);
        }

        gestionDb.editar(user);
    }

    public Usuario editarUsuario(String idUser, String nombre, String password, RolesApp rol)
    {
        Usuario user = (Usuario) gestionDb.find(idUser);
        EntityManager em = gestionDb.getEntityManager();
        try{
            em.getTransaction().begin();
            user.setRol(rol);
            user.setNombre(nombre);
            user.setPassword(password);
            em.merge(user);
            em.getTransaction().commit();
        }finally
        {
            em.close();
        }

        return user;
    }

    public void eliminarUsuario(String idUser)
    {
        gestionDb.eliminar(idUser);
    }

}
