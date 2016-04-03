/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entidades.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import modelo.servicio.GestionServicios;

/**
 *
 * @author -
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

    /**
     * Creates a new instance of LoginBean
     */
    private String usuario;
    private String contrasena;
    private boolean recordar;
    private String tema;
    private Usuario u;
    
    @ManagedProperty("#{servicio}")
    private GestionServicios gs;
    
    public LoginBean() {    
        cargarValorCookies();
        tema = "bootstrap";
        /*Para detectar el final de la sesion
        IndicadorTimeoutSesion timeout = new IndicadorTimeoutSesion();
        HttpSession sesion  = ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        sesion.setMaxInactiveInterval(1);
        sesion.setAttribute("timeout", timeout);*/
    }

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }

    public GestionServicios getGs() {
        return gs;
    }

    public void setGs(GestionServicios gs) {
        this.gs = gs;
    }
    
    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public boolean isRecordar() {
        return recordar;
    }

    public void setRecordar(boolean recordar) {
        this.recordar = recordar;
    }

    public String getUsuario() {
        
        return usuario;
    }

    public void setUsuario(String usuario) {        
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String entrar(){
        enviarCookies();
        u = gs.obtenerUsuario(usuario,contrasena);
        if(u==null){
            contrasena="";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Usuario o contraseña no validos",""));
            //System.out.println("asdfasdf");
            return "";
        }else{
            tema = u.getTema();
            return "portada";
        }
    }
    
    public String registro(){
        return "registro";
    }
    
    public String recuperar(){
        return "recordar";
    }
    
    private void enviarCookies(){
        HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Map<String,Object> properties = new HashMap<String,Object>();  
        properties.put("path",request.getContextPath());
        properties.put("maxAge", 31622400);
        
        if(recordar == false){
            FacesContext.getCurrentInstance()
            .getExternalContext()
            .addResponseCookie("recordar", "false", properties);
        }else{
            FacesContext.getCurrentInstance()
            .getExternalContext()
            .addResponseCookie("recordar", "true", properties);
            
            FacesContext.getCurrentInstance()
            .getExternalContext()
            .addResponseCookie("usuario", usuario, properties);
            
            FacesContext.getCurrentInstance()
            .getExternalContext()
            .addResponseCookie("contrasena", contrasena, properties);
        }
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .addResponseCookie("dontShowCookies", "true", properties);
    }
    
    private void cargarValorCookies(){
        HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie [] cookies = request.getCookies();
        boolean cargar =false;
        if (cookies==null) {
        } else {
            for(Cookie c:cookies){
                if(c.getName().equals("recordar")&&c.getValue().equals("true")){
                    cargar = true;
                }
            }
        }
        if(cargar == true){
            recordar=true;
            for(Cookie c:cookies){
                if(c.getName().equals("usuario"))
                    usuario = c.getValue();
                if(c.getName().equals("contrasena"))
                    contrasena = c.getValue();
            }
        }
    }
    
    public String onload(){
        //tema = "bootstrap";
        return "";
    }
}
