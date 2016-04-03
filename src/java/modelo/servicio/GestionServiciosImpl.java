/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.servicio;

import modelo.dao.*;
import entidades.Categoria;
import entidades.Color;
import entidades.Historico;
import entidades.Producto;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author -
 */
@Component(value = "servicio")
public class GestionServiciosImpl implements GestionServicios,Serializable {
    @Autowired
    GestionProductos gp;
    @Autowired
    GestionUsuarios gu;
    @Autowired
    GestionHistorico gh;

    @Override
    public Usuario obtenerUsuario(String nombre, String contrasena){
        List<Usuario> lista = gu.obtenerUsuario(nombre, contrasena);
        if(lista.isEmpty())
            return null;
        else
            return lista.get(0);
    }
    
    @Override
    public Usuario obtenerUsuario(String nombre){
        List<Usuario> lista = gu.obtenerUsuario(nombre);
        if(lista.isEmpty())
            return null;
        else
            return lista.get(0);
    }
    
    @Override
    public Usuario obtenerUsuarioEmail(String email){
        List<Usuario> lista = gu.obtenerUsuarioEmail(email);
        if(lista.isEmpty())
            return null;
        else
            return lista.get(0);
    }
    
    @Override
    public Producto obtenerProducto(String referencia,int usuario){
        return gp.obtenerProducto(referencia,usuario);
    }
    
    @Override
    public List<Producto> obtenerProductos(){
        return gp.obtenerProductos();
    }
    
    @Override
    public List<Producto> obtenerProductos(int user){
        
        return gp.obtenerProductos(user);
    }

    @Override
    public List<SelectItem> obtenerColores(int user) {
        List<Color> colors = gu.obtenerColores(user);
        List<SelectItem> res = new ArrayList();
        //res.add(new SelectItem(1, "Sin Definir"));
        for(Color c: colors){
            SelectItem selectItemc = new SelectItem( c.getId(),c.getNombre());
            res.add(selectItemc);
        }
        return res;
        /*List<Producto> prods = obtenerProductos(user);
        List<String> colors = new ArrayList();
        for(Producto p: prods){
            if(!colors.contains(p.getColor().getNombre()))
                colors.add(p.getColor().getNombre());
        }
        return colors;*/
    }
    
    @Override
    public List<SelectItem> obtenerSubcategorias(int user) {
        List<Categoria> cats = gu.obtenerCategorias(user);
        List<SelectItem> res = new ArrayList();
        for(Categoria c: cats){
            if(c.getCategoriaPadreRelacionada()!=null){
                SelectItem selectItemc = new SelectItem( c.getId(),c.getNombre());
                res.add(selectItemc);
            }
        }
        return res;
        /*List<Categoria> cats = gu.obtenerCategorias(user);
        List<SelectItem> res = new ArrayList();
        for(Categoria c: cats){
            if(c.getCategoriaPadreRelacionada()!=null){
                SelectItem selectItemc = new SelectItem( c.getId().toString(),c.getNombre());
                res.add(selectItemc);
            }
        }
        return res;*/
        /*List<Producto> prods = obtenerProductos(user);
        List<String> subcats = new ArrayList();
        for(Producto p: prods){
            if(!subcats.contains(p.getSubcategoria().getNombre()))
                subcats.add(p.getSubcategoria().getNombre());
        }
        return subcats;*/
    }
    
     /* private List<Categoria> obtenerSubcategoriasObjt(int user) {
        List<Producto> prods = obtenerProductos(user);
        List<Categoria> subcats = new ArrayList();
        for(Producto p: prods){
            if(!subcats.contains(p.getSubcategoria()))
                subcats.add(p.getSubcategoria());
        }
        return subcats;
    }*/
    
    @Override
    public List<SelectItem> obtenerCategoriasSubcategorias(int user) {
        List<Categoria> catspadres = new ArrayList();
        List <SelectItem> result= new ArrayList();
        result.add(new SelectItem(1, "Sin Categoria"));
        List<Categoria> cats = gu.obtenerCategorias(user);
        
        //Padres
        for(Categoria c: cats){
            if(c.getCategoriaPadreRelacionada()==null){
                catspadres.add(c);
            }
        }
        //Hijos
        for(Categoria c: catspadres){
            List<SelectItem> res = new ArrayList();
            for(Categoria sc: cats){
                if(sc.getCategoriaPadreRelacionada()!=null && sc.getCategoriaPadreRelacionada().getId().equals(c.getId())){
                    SelectItem selectItemc = new SelectItem( sc.getId(),sc.getNombre());
                    res.add(selectItemc);
                }
            }
        SelectItemGroup g = new SelectItemGroup(c.getNombre(), null, true, res.toArray(new SelectItem[res.size()]));
        result.add(g);
        }
        return result;
        
        /*List<Categoria> cats = obtenerSubcategoriasObjt(user);
        List<Categoria> catspadres = new ArrayList();
        List<SelectItem> res = new ArrayList();
        //Recorremos para obtener los padres
        for(Categoria c: cats){
            if(!catspadres.contains(c.getCategoriaPadreRelacionada())){
                catspadres.add(c.getCategoriaPadreRelacionada());
            }
        }
        //Recorremos los padres
        for(Categoria padre: catspadres){
            SelectItemGroup grupopadre = new SelectItemGroup(padre.getNombre());
            List <String> hijosdepadre= new ArrayList();
        //Recorremos todos los hijos
            for(Categoria hijo: cats){
                if(hijo.getCategoriaPadreRelacionada().equals(padre)){
                    //Añadimos los hijos del padre
                    hijosdepadre.add(hijo.getNombre());
                }
            }
            //Creamos la Lista con selectItems
            List<SelectItem> si = new ArrayList<SelectItem>();
            for(String s: hijosdepadre)
                si.add(new SelectItem(s, s));
            //Añadimos el grupo 
            grupopadre.setSelectItems((SelectItem[]) si.toArray());
            res.add(grupopadre);    
        }
        return res;*/
    }
    
    @Override
    public Categoria obtenerCategoria(int id){
        return gu.obtenerCategoria(id);
    }
    
    @Override
    public Color obtenerColor(int id) {
        return gu.obtenerColor(id);
    }

    @Override
    public void guardarUsuario(Usuario user) {
        
        gu.guardarUsuario(user);
    }

    @Override
    public boolean guardarProducto(Producto p) {
        return gp.guardarProducto(p);
    }

    @Override
    public void crearProducto(Producto p) {
        gp.crearProducto(p);
    }

    @Override
    public void eliminarProducto(Producto p) {
        gp.eliminarProducto(p);
    }

    @Override
    public List<String> obtenerStringProductos(int user) {
        List<Producto> l=this.obtenerProductos(user);
        List<String> resultado= new ArrayList();
        for(Producto p:l){
            resultado.add(p.getReferencia());
        }
        Collections.sort(resultado);
        return resultado;
    }

    @Override
    public Historico obtenerUltimoHistorico(String referencia, int usuario) {
        return gh.obtenerUltimoHistorico(referencia, usuario);
    }
    
    @Override
    public boolean guardarHistorico(Historico h) {
        return gh.guardarHistorico(h);
    }

    @Override
    public boolean eliminarHistorico(Historico h) {
        return gh.eliminarHistorico(h);
    }
    
    @Override
    public List<Historico> obtenerHistorico(String referencia, int usuario) {
        return gh.obtenerHistorico(referencia, usuario);
    }

    @Override
    public List<SelectItem> obtenerCategorias(int user) {
        List<Categoria> cats = gu.obtenerCategorias(user);
        List<SelectItem> res = new ArrayList();
        for(Categoria c: cats){
            if(c.getCategoriaPadreRelacionada()==null){
                SelectItem selectItemc = new SelectItem( c.getId(),c.getNombre());
                res.add(selectItemc);
            }
        }
        return res;
        /*List<Categoria> cats = obtenerSubcategoriasObjt(user);
        List<SelectItem> res = new ArrayList();
        
        //Recorremos para obtener los padres
        for(Categoria c: cats){
            SelectItem selectItemc = new SelectItem( c.getId().toString(),c.getNombre());
            if(c.getId()==1 && !res.contains(selectItemc)){
                res.add(selectItemc);
            }
            if(c.getCategoriaPadreRelacionada()!=null){
                selectItemc = new SelectItem(c.getCategoriaPadreRelacionada().getId().toString(),c.getCategoriaPadreRelacionada().getNombre());
                if(!res.contains(selectItemc)){
                    res.add(selectItemc);
                }
            }
        }
        return res;*/
    }
    

    @Override
    @Transactional
    public void eliminarColor(Color color){
        List<Producto> list =gp.obtenerProductos(color.getUsuario(), color.getId());
        for(Producto p:list){
            p.setColor(new Color(1));
        }
        gu.eliminarColor(color);
    }

    @Override
    public void guardarColor(Color color){
        gu.guardarColor(color);
    }
    //ToDo
    @Override
    @Transactional
    public void eliminarCategoria(Categoria categoria){
        if(categoria.getCategoriaPadreRelacionada()!=null){
            List<Producto> list =gp.obtenerProductosCategoria(categoria.getUsuario(), categoria.getId());
            for(Producto p:list){
                p.setSubcategoria(new Categoria(1));
            }
        }else{
            List<Categoria> listc = gu.obtenerCategorias(categoria.getUsuario());
            List<Producto> listp = gp.obtenerProductosCategoria(categoria.getUsuario(), categoria.getId());
            for(Categoria c:listc){
                if(c.getCategoriaPadreRelacionada()!=null && c.getCategoriaPadreRelacionada().getId() == categoria.getId()){ 
                    for(Producto p:listp){
                        if(p.getSubcategoria().getId() == c.getId()){
                            p.setSubcategoria(new Categoria(1));
                        }
                    }
                }
            } 
        }
        gu.eliminarCategoria(categoria);
    }
    //ToDo
    @Override
    public void guardarCategoria(Categoria categoria){
        gu.guardarCategoria(categoria);
    }
}
