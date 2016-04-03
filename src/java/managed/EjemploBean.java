package managed;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entidades.ProductoNodoBean;
import entidades.Categoria;
import entidades.Color;
import entidades.Producto;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import modelo.servicio.GestionServicios;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import net.coobird.thumbnailator.*;

/**
 *
 * @author -
 */
@ManagedBean
@SessionScoped
public class EjemploBean implements Serializable{

    /**
     * Creates a new instance of EjemploBean
     */
    @ManagedProperty(value = "#{loginBean}" )
    LoginBean loginBean;
    List <ProductoNodoBean> lista;
    List <ProductoNodoBean> listaFiltro;
    
    @ManagedProperty("#{servicio}")
    private GestionServicios gs;
    
    private List<SelectItem> categorias;
    private List<SelectItem> subcategorias;
    private List<SelectItem> categoriasGroup;
    private List<SelectItem> colores;
    
    private Producto nuevo;
    private Color nuevoColor;
    private Color eliminarColor;
    private Categoria nuevaCategoria;
    private Categoria nuevaSubcategoria;
    private Categoria eliminarCategoria;
    private Categoria eliminarSubcategoria;
    private UploadedFile file;
    
    public EjemploBean() {
    }
    
    @PostConstruct
    public void init() {
            lista = transformarListaProductoNodo(gs.obtenerProductos(loginBean.getU().getId()));
            listaFiltro = transformarListaProductoNodo(gs.obtenerProductos(loginBean.getU().getId()));
            nuevo = productoNuevo();
            nuevoColor = colorNuevo();
            nuevaCategoria = categoriaNuevo();
            nuevaSubcategoria = categoriaNuevo();
            eliminarColor  = colorNuevo();
            eliminarCategoria  = categoriaNuevo();
            eliminarSubcategoria  = categoriaNuevo();
            nuevaSubcategoria.setCategoriaPadreRelacionada(new Categoria()) ;
            colores = gs.obtenerColores(loginBean.getU().getId());
            categorias = gs.obtenerCategorias(loginBean.getU().getId());
            subcategorias = gs.obtenerSubcategorias(loginBean.getU().getId());
            categoriasGroup = gs.obtenerCategoriasSubcategorias(loginBean.getU().getId());
            
            
    }

    public List<ProductoNodoBean> getListaFiltro() {
        return listaFiltro;
    }
    
    public void setListaFiltro(List<ProductoNodoBean> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public Producto getNuevo() {
        return nuevo;
    }

    public void setNuevo(Producto nuevo) {
        this.nuevo = nuevo;
    }

    public Color getNuevoColor() {
        return nuevoColor;
    }

    public void setNuevoColor(Color nuevoColor) {
        this.nuevoColor = nuevoColor;
    }

    public Categoria getNuevaCategoria() {
        return nuevaCategoria;
    }

    public Categoria getNuevaSubcategoria() {
        return nuevaSubcategoria;
    }

    public void setNuevaSubcategoria(Categoria nuevaSubcategoria) {
        this.nuevaSubcategoria = nuevaSubcategoria;
    }

    public void setNuevaCategoria(Categoria nuevaCategoria) {
        this.nuevaCategoria = nuevaCategoria;
    }

    public Color getEliminarColor() {
        return eliminarColor;
    }

    public void setEliminarColor(Color eliminarColor) {
        this.eliminarColor = eliminarColor;
    }

    public Categoria getEliminarCategoria() {
        return eliminarCategoria;
    }

    public void setEliminarCategoria(Categoria eliminarCategoria) {
        this.eliminarCategoria = eliminarCategoria;
    }

    public Categoria getEliminarSubcategoria() {
        return eliminarSubcategoria;
    }

    public void setEliminarSubcategoria(Categoria eliminarSubcategoria) {
        this.eliminarSubcategoria = eliminarSubcategoria;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public GestionServicios getGs() {
        return gs;
    }

    public void setGs(GestionServicios gs) {
        this.gs = gs;
    }


    public List<ProductoNodoBean> getLista() {
        return lista;
    }

    public void setLista(List<ProductoNodoBean> lista) {
        this.lista = lista;
    }
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    public List<SelectItem> getColores() {
        return colores;
    }

    public void setColores(List<SelectItem> colores) {
        this.colores = colores;
    }

    public List<SelectItem> getCategoriasGroup() {
        return categoriasGroup;
    }

    public void setCategoriasGroup(List<SelectItem> categoriasGroup) {
        this.categoriasGroup = categoriasGroup;
    }
    
    public List<SelectItem> getSubcategorias() {
        return gs.obtenerSubcategorias(loginBean.getU().getId());
    }

    public List<SelectItem> getCategorias() {
        if(categorias==null)
            categorias = gs.obtenerCategorias(loginBean.getU().getId());
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }
    
    public void onRowEdit(RowEditEvent event) {
        Producto editado= (Producto) event.getObject();
        gs.guardarProducto(editado);
        
        FacesMessage msg = new FacesMessage("Producto editado", editado.getReferencia());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", ((Producto) event.getObject()).getReferencia());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        Producto prod = listaFiltro.get(event.getRowIndex()).getProducto();
        if(event.getColumn().getColumnKey().contains("Subcategoria")){
            prod.setSubcategoria(gs.obtenerCategoria(Integer.valueOf(newValue.toString())));
            
        }
        if(event.getColumn().getColumnKey().contains("color")){
            prod.setColor(gs.obtenerColor(Integer.valueOf(newValue.toString())));
           
        }
        
        gs.guardarProducto(prod);
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage("Valor cambiado en "+ event.getColumn().getColumnKey(), "Anterior: " + oldValue + ", Nuevo:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void upload(FileUploadEvent event) {

        Producto p = gs.obtenerProducto(event.getComponent().getAttributes().get("refProd").toString(), loginBean.getU().getId());
        try{
            BufferedImage img = Thumbnails.of(new ByteArrayInputStream(event.getFile().getContents())).size(300, 300).asBufferedImage();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", outputStream);
            p.setFoto(outputStream.toByteArray());
        }catch(IOException e){
            p.setFoto(event.getFile().getContents());
        }
        gs.guardarProducto(p);

        FacesMessage message = new FacesMessage("Completado", event.getFile().getFileName() + " se ha guardado en "+ event.getComponent().getAttributes().get("refProd") + ".");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
        public StreamedContent getImage(){
            FacesContext context = FacesContext.getCurrentInstance();

            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
                return new DefaultStreamedContent();
            }
            else {
                // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
                String id = context.getExternalContext().getRequestParameterMap().get("id");
                Producto p = gs.obtenerProducto(id, loginBean.getU().getId());
                if(p.getFoto()!=null){
                    return new DefaultStreamedContent(new ByteArrayInputStream(p.getFoto()));
                }else{
                    String relativeWebPath = "/images/sin_imagen.jpg";
                    String absoluteDiskPath = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath(relativeWebPath);
                    File file = new File(absoluteDiskPath);
                    try{
                        return new DefaultStreamedContent(new FileInputStream(file));
                    }catch(FileNotFoundException e){
                        return null;
                    }
                }
            }
        }

    public String crearProducto(){
        gs.crearProducto(nuevo);
        nuevo = productoNuevo();
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/productos.xhtml?i=2");
        }catch(IOException e){
            return "productos";
        }
        return "";
    }
    
    private Producto productoNuevo(){
        Producto nuevop = new Producto();
        nuevop.setUsuario(loginBean.getU().getId());
        nuevop.setPiezasCaja(1);
        nuevop.setStockMin(0);
        nuevop.setColor(new Color(1));
        nuevop.setSubcategoria(new Categoria(1));
        return nuevop;
    }
    
    private Color colorNuevo(){
        Color nuevoc = new Color();
        nuevoc.setUsuario(loginBean.getU().getId());
        return nuevoc;
    }
    
    private Categoria categoriaNuevo(){
        Categoria nuevoc = new Categoria();
        nuevoc.setUsuario(loginBean.getU().getId());
        return nuevoc;
    }

    public String crearCategoria(){
        //System.out.println("Categoria-------------------------------");
        gs.guardarCategoria(nuevaCategoria);
        categorias = gs.obtenerCategorias(loginBean.getU().getId());
        subcategorias = gs.obtenerSubcategorias(loginBean.getU().getId());
        categoriasGroup = gs.obtenerCategoriasSubcategorias(loginBean.getU().getId());
        return "";
    } 
    
    public String crearSubcategoria(){
        //System.out.println("Subcategoria+++++++++++++++++++++++++++++");
        gs.guardarCategoria(nuevaSubcategoria);
        categorias = gs.obtenerCategorias(loginBean.getU().getId());
        subcategorias = gs.obtenerSubcategorias(loginBean.getU().getId());
        categoriasGroup = gs.obtenerCategoriasSubcategorias(loginBean.getU().getId());
        return "";
    }
    
    public String eliminarCategoria(){
        //System.out.println("Eliminar cat");
        gs.eliminarCategoria(eliminarCategoria);
        categorias = gs.obtenerCategorias(loginBean.getU().getId());
        subcategorias = gs.obtenerSubcategorias(loginBean.getU().getId());
        categoriasGroup = gs.obtenerCategoriasSubcategorias(loginBean.getU().getId());
        return "";
    } 
    
    public String eliminarSubcategoria(){
        //System.out.println("Eliminar subcat");
        gs.eliminarCategoria(eliminarSubcategoria);
        categorias = gs.obtenerCategorias(loginBean.getU().getId());
        subcategorias = gs.obtenerSubcategorias(loginBean.getU().getId());
        categoriasGroup = gs.obtenerCategoriasSubcategorias(loginBean.getU().getId());
        return "";
    } 
    
    public String eliminarProductos(){
        for(ProductoNodoBean p:listaFiltro){
            if(p.isSeleccionado()){
                gs.eliminarProducto(p.getProducto());
            }
        }
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/productos.xhtml?i=2");
        }catch(IOException e){
            return "productos";
        }
        return "";
    }
    
    private List<ProductoNodoBean> transformarListaProductoNodo(List<Producto> lista){
        List<ProductoNodoBean> resultado;
        resultado = new ArrayList();
        for(Producto p :lista){
            resultado.add(new ProductoNodoBean(p));
        }
        return resultado;
    }
    public String crearColor(){
        gs.guardarColor(nuevoColor);
        nuevoColor = colorNuevo();
        colores = gs.obtenerColores(loginBean.getU().getId());
        return "";
    }
    public String eliminarColor(){
        //System.out.println(eliminarColor.getId() + "  " + eliminarColor.getNombre());
        gs.eliminarColor(eliminarColor);
        eliminarColor = colorNuevo();
        colores = gs.obtenerColores(loginBean.getU().getId());
        return "";
    }
}
