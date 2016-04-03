/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import entidades.Categoria;
import entidades.Color;
import entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author -
 */
@Component
public class GestionUsuariosImpl implements GestionUsuarios,Serializable {
    @PersistenceContext(unitName="almacenstockPU")
    EntityManager em;

    @Override
    public List<Usuario> obtenerUsuario(String nombre) {
        return em.createNamedQuery("Usuario.findByNombre").setParameter(1, nombre).getResultList();
    }
    
    @Override
    public List<Usuario> obtenerUsuario(String nombre, String contrasena){
        Query q = em.createNamedQuery("Usuario.findByNombreContrasena");
        q.setParameter(1, nombre);
        q.setParameter(2, contrasena);
        return q.getResultList();
    }
    
    @Override
    public List<Usuario> obtenerUsuarioEmail(String email) {
        return em.createNamedQuery("Usuario.findByEmail").setParameter(1, email).getResultList();
    }

    @Override
    @Transactional
    public void guardarUsuario(Usuario user) {
        em.merge(user);
    }
    
    @Override
    @Transactional
    public void guardarColor(Color color) {
        em.merge(color);
    }
    
    @Override
    @Transactional
    public void guardarCategoria(Categoria categoria) {
        em.merge(categoria);
    }
    
    @Override
    @Transactional
    public void eliminarColor(Color color){
        color = em.find(Color.class, color.getId());
        em.remove(color);
    }
    
    @Override
    @Transactional
    public void eliminarCategoria(Categoria categoria){
        categoria = em.find(Categoria.class, categoria.getId());
        em.remove(categoria);
    }
    
    @Override
    public List<Categoria> obtenerCategorias(int user) {
        return em.createNamedQuery("Categoria.findByUser").setParameter(1, user).getResultList();
    }
    
    @Override
    public Categoria obtenerCategoria(int id) {
        return em.find(Categoria.class, id);
    }
    
    @Override
    public Color obtenerColor(int id) {
        return em.find(Color.class, id);
    }

    @Override
    public List<Color> obtenerColores(int user) {
        return em.createNamedQuery("Color.findByUser").setParameter(1, user).getResultList();
    }
}
