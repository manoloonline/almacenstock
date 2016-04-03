/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entidades.Usuario;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import modelo.servicio.GestionServicios;

/**
 *
 * @author -
 */
@ManagedBean
@RequestScoped
public class RecordarBean implements Serializable{

    /**
     * Creates a new instance of LoginBean
     */
    private Usuario usuario;
    private String email;
    
    @ManagedProperty("#{servicio}")
    private GestionServicios gs;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
    public String recordar(){
        String captchaValidacion = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("g-recaptcha-response");
        if(captchaValidacion.equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe solucionar el Captcha", "Debe validar el Captcha"));
            return "";
        }else{          
                usuario = gs.obtenerUsuarioEmail(email);
                final String username = "almacenstockonline@gmail.com";
		final String password = "talento00";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mcflurro@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("Recordar datos de conexión");
			message.setText(" Estimado usuario:"
				+ "\n\n Le recordamos su datos para identificarse en la aplicacion:"
                                + "\n\n Usuario: " + usuario.getNombre()
                                + "\n Contraseña: " + usuario.getContraseña()
                                + "\n\n Saludos!");

			Transport.send(message);
                        
		} catch (MessagingException e) {
			throw new RuntimeException(e);
                }
                
            return "index";
        }
    }
    public String volver(){
            return "index";
        }
    
    public void validateEmail(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        Usuario user = gs.obtenerUsuarioEmail(value.toString());
        if (user == null) {
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
