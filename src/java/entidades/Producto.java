/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author -
 */
@Entity
@Table(name = "productos")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByUser", query = "SELECT p FROM Producto p WHERE p.usuario=?1"),
    @NamedQuery(name = "Producto.findByUserColor", query = "SELECT p FROM Producto p WHERE p.usuario=?1 AND p.colorRelacionado.id=?2"),
    @NamedQuery(name = "Producto.findByUserSubcategoria", query = "SELECT p FROM Producto p WHERE p.usuario=?1 AND p.subcategoriaRelacionada.id=?2"),
    @NamedQuery(name = "Producto.findByReferencia", query = "SELECT p FROM Producto p WHERE p.referencia=?1 AND p.usuario=?2")})
    
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROD")
    private String id;
    @Basic(optional = false)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Basic(optional = false)
    @Column(name = "STOCK_MIN")
    private int stockMin;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "PIEZAS_CAJA")
    private int piezasCaja;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private double precio;
    @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "FOTO")
    private byte[] foto;
    
    @ManyToOne
    @JoinColumn(name="color",referencedColumnName = "id")
    private Color colorRelacionado;
    
    @ManyToOne
    @JoinColumn(name="subcategoria",referencedColumnName = "id")
    private Categoria subcategoriaRelacionada;
    
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private int usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<Historico> historicoCollection;
    
    
    public Producto() {
        
    }

    public Producto(String referencia) {
        this.referencia = referencia;
    }

    public Producto(String referencia, int stockMin, String descripcion, int piezasCaja, double precio, Color color, int usuario) {
        this.referencia = referencia;
        this.stockMin = stockMin;
        this.descripcion = descripcion;
        this.piezasCaja = piezasCaja;
        this.precio = precio;
        this.colorRelacionado = color;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Color getColorRelacionado() {
        return colorRelacionado;
    }

    public void setColorRelacionado(Color colorRelacionado) {
        this.colorRelacionado = colorRelacionado;
    }

    public Categoria getSubcategoriaRelacionada() {
        return subcategoriaRelacionada;
    }

    public void setSubcategoriaRelacionada(Categoria subcategoriaRelacionada) {
        this.subcategoriaRelacionada = subcategoriaRelacionada;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPiezasCaja() {
        return piezasCaja;
    }

    public void setPiezasCaja(int piezasCaja) {
        this.piezasCaja = piezasCaja;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Color getColor() {
        return colorRelacionado;
    }

    public void setColor(Color color) {
        this.colorRelacionado = color;
    }

    public Categoria getSubcategoria() {
        return subcategoriaRelacionada;
    }

    public void setSubcategoria(Categoria subcategoria) {
        this.subcategoriaRelacionada = subcategoria;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public Collection<Historico> getHistoricoCollection() {
        return historicoCollection;
    }

    public void setHistoricoCollection(Collection<Historico> historicoCollection) {
        this.historicoCollection = historicoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return referencia;
    }
    
}
