/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.servicio;

import entidades.Categoria;
import entidades.Color;
import entidades.Historico;
import entidades.Producto;
import entidades.Usuario;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author tarde
 */
public interface GestionServicios {
    
    public void eliminarProducto(Producto p) ;
    
    public void crearProducto(Producto p);

    public boolean guardarProducto(Producto p);
    
    public Usuario obtenerUsuario(String nombre);
    
    public Usuario obtenerUsuario(String nombre, String contrasena);
    
    public Usuario obtenerUsuarioEmail(String email);
    
    void guardarUsuario(Usuario user);
    
    Producto obtenerProducto(String referencia,int usuario);

    List<String> obtenerStringProductos(int usuario);
    
    List<Producto> obtenerProductos();

    List<Producto> obtenerProductos(int usuario);
    
    List<SelectItem> obtenerColores(int usuario);
    
    List<SelectItem> obtenerCategorias(int user);
    
    public Categoria obtenerCategoria(int id);
    
    public Color obtenerColor(int id);
    
    List<SelectItem> obtenerSubcategorias(int user);
    
    List<SelectItem> obtenerCategoriasSubcategorias(int user);
    
    public Historico obtenerUltimoHistorico(String referencia, int usuario);
    
    public boolean guardarHistorico(Historico h);
    
    public List<Historico> obtenerHistorico(String referencia, int usuario);
   
    public boolean eliminarHistorico(Historico h);
    
    public void eliminarColor(Color color);
    
    public void guardarColor(Color color);
    
    public void eliminarCategoria(Categoria categoria);
    
    public void guardarCategoria(Categoria categoria);
}
