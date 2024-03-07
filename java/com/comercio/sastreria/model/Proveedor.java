package com.comercio.sastreria.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @Column(name = "id_proveedor")
    private String idProveedor;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "ruc_prov", nullable = false, unique = true)
    private String rucProveedor;

    @Column(name = "dir_prov")
    private String direccionProveedor;

    @Column(name = "telef_prov")
    private String telefonoProveedor;

    @Column(name = "contacto")
    private String contactoProveedor;

    @Column(name = "correo_prov")
    private String correoProveedor;
    
    @Column(name = "fecha", nullable = false)
	private Timestamp fecha;

    public Proveedor() {
    }

    public Proveedor(String idProveedor, String razonSocial, String rucProveedor, String direccionProveedor,
			String telefonoProveedor, String contactoProveedor, String correoProveedor, Timestamp fecha) {
		super();
		this.idProveedor = idProveedor;
		this.razonSocial = razonSocial;
		this.rucProveedor = rucProveedor;
		this.direccionProveedor = direccionProveedor;
		this.telefonoProveedor = telefonoProveedor;
		this.contactoProveedor = contactoProveedor;
		this.correoProveedor = correoProveedor;
		this.fecha = fecha;
	}

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getContactoProveedor() {
        return contactoProveedor;
    }

    public void setContactoProveedor(String contactoProveedor) {
        this.contactoProveedor = contactoProveedor;
    }

    public String getCorreoProveedor() {
        return correoProveedor;
    }

    public void setCorreoProveedor(String correoProveedor) {
        this.correoProveedor = correoProveedor;
    }

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
}