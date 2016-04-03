/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author -
 */
@Entity
@Table(name = "historico")
@NamedQueries({
    @NamedQuery(name = "Historico.findAll", query = "SELECT h FROM Historico h"),
    @NamedQuery(name = "Historico.findLastDateByRef", query = "SELECT h FROM Historico h" +
                                                              " WHERE h.historicoPK.referencia = ?1 AND  h.historicoPK.usuario = ?2 ORDER BY h.historicoPK.fecha desc")
})
public class Historico implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoricoPK historicoPK;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private int cantidad;
    @JoinColumn(name = "REFERENCIA", referencedColumnName = "REFERENCIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public Historico() {
    }

    public Historico(HistoricoPK historicoPK) {
        this.historicoPK = historicoPK;
    }

    public Historico(HistoricoPK historicoPK, int cantidad) {
        this.historicoPK = historicoPK;
        this.cantidad = cantidad;
    }

    public Historico(String referencia, Date fecha, int usuario) {
        this.historicoPK = new HistoricoPK(referencia, fecha, usuario);
    }

    public Historico(String referencia, Date fecha, int usuario,int cantidad) {
        this.historicoPK = new HistoricoPK(referencia, fecha, usuario);
        this.cantidad = cantidad;
    }
    public HistoricoPK getHistoricoPK() {
        return historicoPK;
    }

    public void setHistoricoPK(HistoricoPK historicoPK) {
        this.historicoPK = historicoPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historicoPK != null ? historicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historico)) {
            return false;
        }
        Historico other = (Historico) object;
        if ((this.historicoPK == null && other.historicoPK != null) || (this.historicoPK != null && !this.historicoPK.equals(other.historicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Historico[ historicoPK=" + historicoPK + " ]";
    }
    
}
