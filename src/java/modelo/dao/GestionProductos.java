/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import entidades.Producto;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author tarde
 */
public interface GestionProductos {

    public Producto obtenerProducto(String referencia,int usuario);

    public List<Producto> obtenerProductos();

    public List<Producto> obtenerProductos(int usuario);
    
    public List<Producto> obtenerProductos(int user,int color);
    
    public List<Producto> obtenerProductosCategoria(int user,int categoria);
    
    public boolean guardarProducto(Producto p);
    
    public void crearProducto(Producto p);
    
    public void eliminarProducto(Producto p);

}
