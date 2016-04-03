/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managed.LoginBean;

/**
 *
 * @author -
 */
public class LoginFiltro implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
        HttpSession session = ((HttpServletRequest)request).getSession();
        LoginBean loginBean = (session != null) ? (LoginBean) session.getAttribute("loginBean") : null;
        String URI = ((HttpServletRequest)request).getRequestURI();
        if(loginBean.getU()==null){
            //System.out.println("No: " + ((HttpServletRequest)request).getRequestURI());
            if(URI.contains("/faces/opciones.xhtml")
            || URI.contains("/faces/cabecera.xhtml")
            || URI.contains("/faces/informe.xhtml")
            || URI.contains("/faces/portada.xhtml")
            || URI.contains("/faces/productos.xhtml")
            || URI.contains("/faces/inventario.xhtml")
            || URI.contains("/faces/productos.xhtml")){
                ((HttpServletRequest)request).getRequestDispatcher("/").forward(request, response);
                //((HttpServletResponse)response).sendRedirect("/");
            }
        }else{
            //System.out.println("Ok: " + ((HttpServletRequest)request).getRequestURI());   
            
        }
        chain.doFilter(request, response);
        }catch(Exception e){
            ((HttpServletRequest)request).getRequestDispatcher("/").forward(request, response);
            //chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        
    }
    
}
