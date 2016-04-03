/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import entidades.Producto;
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
public class GestionProductosImpl implements GestionProductos,Serializable {
    @PersistenceContext(unitName="almacenstockPU")
    EntityManager em;
    
    @Override
    public Producto obtenerProducto(String referencia, int usuario){
        Query q = em.createNamedQuery("Producto.findByReferencia");
        q.setParameter(1, referencia);
        q.setParameter(2, usuario);
        try{
            return (Producto) q.getSingleResult();
        }catch(Exception e){
            return new Producto();
            
        }
    }
    
    @Override
    public List<Producto> obtenerProductos(){
        return em.createNamedQuery("Producto.findAll").getResultList();
    }
    
    @Override
    public List<Producto> obtenerProductos(int user){
        Query q = em.createNamedQuery("Producto.findByUser");
        q.setParameter(1, user);
        
        return q.getResultList();
    }
    
    @Override
    public List<Producto> obtenerProductos(int user,int color){
        Query q = em.createNamedQuery("Producto.findByUserColor");
        q.setParameter(1, user);
        q.setParameter(2, color);
        
        return q.getResultList();
    }
    
    @Override
    public List<Producto> obtenerProductosCategoria(int user,int categoria){
        Query q = em.createNamedQuery("Producto.findByUserSubcategoria");
        q.setParameter(1, user);
        q.setParameter(2, categoria);
        
        return q.getResultList();
    }
    
    @Override
    @Transactional
    public boolean guardarProducto(Producto p){
        em.merge(p);
        return true;
    }

    @Override
    @Transactional
    public void crearProducto(Producto p) {
        em.persist(p);
    }
    
    @Override
    @Transactional
    public void eliminarProducto(Producto p) {
        p=em.merge(p);
        em.remove(p);
    }
}
