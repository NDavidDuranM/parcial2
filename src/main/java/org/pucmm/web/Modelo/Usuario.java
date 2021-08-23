package org.pucmm.web.Modelo;

import org.pucmm.web.util.RolesApp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Usuario implements Serializable {

    @Id
    private String nombreUsuario;
    private String password;
    private String nombre;
    private RolesApp rol;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<URL> urls;

    public Usuario(){}

    public Usuario(String usuario, String password, String nombre, RolesApp rol){
        this.nombreUsuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<URL> getUrls() {
        return urls;
    }

    public void setUrls(Set<URL> urls) {
        this.urls = urls;
    }

    public RolesApp getRol() {
        return rol;
    }
    public void setRol(RolesApp rol) {
        this.rol = rol;
    }
}
