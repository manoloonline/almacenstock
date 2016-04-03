/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author -
 */
@ManagedBean
@RequestScoped
public class CabeceraBean implements Serializable{

    /**
     * Creates a new instance of CabeceraBean
     */
    
    @ManagedProperty(value = "#{loginBean.usuario}" )
    String usuario;
    
    public CabeceraBean() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String toProductos(){
        return "productos";
    }
    public String toInformes(){
        return "informe";
    }
    public String toInventario(){
        return "inventario";
    }
    public String toOpciones(){
        return "opciones";
    }
    public String desconectar(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }
}
