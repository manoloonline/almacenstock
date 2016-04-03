/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import entidades.Categoria;
import entidades.Color;
import entidades.Usuario;
import java.util.List;

/**
 *
 * @author tarde
 */
public interface GestionUsuarios {

    void guardarUsuario(Usuario user);
    public List<Usuario> obtenerUsuario(String nombre);
    public List<Usuario> obtenerUsuario(String nombre, String contrasena);
    public List<Usuario> obtenerUsuarioEmail(String email);
    public List<Categoria> obtenerCategorias(int user);
    public List<Color> obtenerColores(int user);
    public Categoria obtenerCategoria(int id);
    public Color obtenerColor(int id);
    public void guardarColor(Color color);
    public void guardarCategoria(Categoria categoria);
    public void eliminarCategoria(Categoria categoria);
    public void eliminarColor(Color color);
}
