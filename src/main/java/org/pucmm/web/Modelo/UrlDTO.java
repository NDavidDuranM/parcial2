package org.pucmm.web.Modelo;

import org.pucmm.web.Servicio.GestionDb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UrlDTO {
    private String direccionAcortada; //direccion acortada
    private String origen; //Origen que se va a acortar
    private Set<Cliente> clientes;
    public String B64preview;
    private Date fechaCreacion = new Date();
    private String nombreUsuario;

    public UrlDTO() {
        this.fechaCreacion = new Date();
        if(clientes == null)
        {
            clientes = new HashSet<>();
        }
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDireccionAcortada() {
        return direccionAcortada;
    }

    public void setDireccionAcortada(String direccion) {
        this.direccionAcortada = direccion;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public long getCantidadClientes()
    {
        GestionDb gestionURL = new GestionDb(URLs.class);
        return ((URLs) gestionURL.find(this.direccionAcortada)).getClientes().size();
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
