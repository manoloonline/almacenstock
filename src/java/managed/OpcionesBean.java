/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import modelo.servicio.GestionServicios;
import temas.Theme;
import temas.ThemeServiceBean;

/**
 *
 * @author -
 */
@ManagedBean
@RequestScoped
public class OpcionesBean {

    /**
     * Creates a new instance of OpcionesBean
     */
    private String tema;
    
    private Theme theme;   
    private List<Theme> themes;
    private String usuario;
    private String email;
    private String contrasenaAnt;
    private String contrasenaNueva;
    private String repiteContrasena;
     
    @ManagedProperty("#{themeServiceBean}")
    private ThemeServiceBean service;

    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    
    @ManagedProperty("#{servicio}")
    private GestionServicios gs;
    
    @ManagedProperty("#{param.i}")
    private String i;
    
    public OpcionesBean() {

    }
    
    @PostConstruct
    public void init() {
        usuario = loginBean.getU().getNombre();
        email = loginBean.getU().getEmail();
        themes = service.getThemes();
        for(Theme t:themes){
            if(t.getName().equals(loginBean.getTema())){
                theme = t;
                tema = t.getName();
            }
        }
    }

    public String getContrasenaAnt() {
        return contrasenaAnt;
    }

    public void setContrasenaAnt(String contrasenaAnt) {
        this.contrasenaAnt = contrasenaAnt;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }

    public String getRepiteContrasena() {
        return repiteContrasena;
    }

    public void setRepiteContrasena(String repiteContrasena) {
        this.repiteContrasena = repiteContrasena;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setTema(String tema) {
        this.tema = tema;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public ThemeServiceBean getService() {
        return service;
    }

    public void setService(ThemeServiceBean service) {
        this.service = service;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }
    
    public String aplicarTema(){
        //System.out.println("Mierda: "+tema);
        loginBean.setTema(tema);
        loginBean.getU().setTema(tema);
        gs.guardarUsuario(loginBean.getU());
        //System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("i"));
        try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() +"/faces/opciones.xhtml?i=4");
        }catch(IOException e){
            return "opciones";
        }
        /*Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
        params.put("i","4");
        i="4";*/
        return "opciones";
    }
    
    public String guardarDatos(){
        loginBean.getU().setNombre(usuario);
        loginBean.getU().setEmail(email);
        gs.guardarUsuario(loginBean.getU());
    return "";
    }
    
    public String guardarContraseña(){
    
        return "";
    }
}
