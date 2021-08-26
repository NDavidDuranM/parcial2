package org.pucmm.web.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.junit.Ignore;
import org.pucmm.web.Servicio.GestionDb;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class URL implements Serializable {

    @Id
    @Column(name="id")
    private String direccionAcortada; //direccion acortada
    private String origen; //Origen que se va a acortar

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Cliente> clientes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    public String B64preview;
    private Date fechaCreacion = new Date();


    public URL() {
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
        GestionDb gestionURL = new GestionDb(URL.class);
        return ((URL) gestionURL.find(this.direccionAcortada)).getClientes().size();
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
}
