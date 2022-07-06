/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import bean.BackingBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author divine
 */
public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        BackingBean session = (BackingBean) req.getSession().getAttribute("bb");
        String url = req.getRequestURI();
        
        
        if(session == null || !session.isLogged){
            if(url.indexOf("index.html")>=0 || url.indexOf("reservations.xhtml") >=0 || url.indexOf("reserve.xhtml")>=0 || url.indexOf("tables.xhtml")>=0){
                res.sendRedirect(req.getServletContext().getContextPath() +"/login.xhtml");
            }else{
                chain.doFilter(request, response);
            }
        }else{
            if ( url.indexOf("login.xhtml") >=0){
                res.sendRedirect(req.getServletContext().getContextPath() +"/index.html");
            }else{
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
