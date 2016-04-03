/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entidades.Usuario;
import java.io.Serializable;
import java.text.MessageFormat;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import modelo.servicio.GestionServicios;

/**
 *
 * @author -
 */
@ManagedBean
@ViewScoped
public class RegistroBean implements Serializable{

    /**
     * Creates a new instance of LoginBean
     */
    private String usuario;
    private String contrasena;
    private String rcontrasena;
    private String email;
    private String tema;
    private boolean aceptar;
    
    @ManagedProperty("#{servicio}")
    private GestionServicios gs;
    
    public RegistroBean() {    
        tema = "aristo";
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getRcontrasena() {
        return rcontrasena;
    }

    public void setRcontrasena(String rcontrasena) {
        this.rcontrasena = rcontrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public String registrar(){
            String captchaValidacion = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("g-recaptcha-response");
            if(captchaValidacion.equals("")){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe solucionar el Captcha", "Debe validar el Captcha"));
                return "";
            }else{
                gs.guardarUsuario(new Usuario(null, usuario, contrasena, email, "aristo"));
                return "index";
            }
        }
    public String volver(){
            return "index";
        }
    
    public void validateCheckBox(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        if (value.equals(Boolean.FALSE)) {
            String requiredMessage = ((UIInput) component).getRequiredMessage();

            if (requiredMessage == null) {
                Object label = component.getAttributes().get("label");
                if (label == null || (label instanceof String && ((String) label).length() == 0)) {
                    label = component.getValueExpression("label");
                }
                if (label == null) {
                    label = component.getClientId(context);
                }
                requiredMessage = MessageFormat.format(UIInput.REQUIRED_MESSAGE_ID, label);
            }

            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage));
        }
    }
    
    public void validateUsuario(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        Usuario user = gs.obtenerUsuario(value.toString());
        if (user != null) {
            String requiredMessage = ((UIInput) component).getRequiredMessage();

            if (requiredMessage == null) {
                Object label = component.getAttributes().get("label");
                if (label == null || (label instanceof String && ((String) label).length() == 0)) {
                    label = component.getValueExpression("label");
                }
                if (label == null) {
                    label = component.getClientId(context);
                }
                requiredMessage = MessageFormat.format(UIInput.REQUIRED_MESSAGE_ID, label);
            }

            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage));
        }
    }
    
    public void validateEmail(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        Usuario user = gs.obtenerUsuarioEmail(value.toString());
        if (user != null) {
            String requiredMessage = ((UIInput) component).getRequiredMessage();

            if (requiredMessage == null) {
                Object label = component.getAttributes().get("label");
                if (label == null || (label instanceof String && ((String) label).length() == 0)) {
                    label = component.getValueExpression("label");
                }
                if (label == null) {
                    label = component.getClientId(context);
                }
                requiredMessage = MessageFormat.format(UIInput.REQUIRED_MESSAGE_ID, label);
            }

            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage));
        }
    }
}
