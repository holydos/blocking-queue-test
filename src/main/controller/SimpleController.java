package main.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Created by HolyDos on 28.04.2016.
 */
public class SimpleController implements Controller {
    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("/WEB-INF/jsp/kurwa.jsp");
    }
}
