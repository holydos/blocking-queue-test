package com.vutichenko.bqtest.controller;

import com.vutichenko.bqtest.pojo.SimpleEntity;
import com.vutichenko.bqtest.service.EntityManagerUtil;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by HolyDos on 28.04.2016.
 */
@org.springframework.stereotype.Controller
public class SimpleController  {

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mv = new ModelAndView("/WEB-INF/jsp/display.jsp");
        EntityManagerUtil.savePojoEntity("a random 2");
        SimpleEntity se = EntityManagerUtil.getEntityList().iterator().next();
        mv.getModelMap().addAttribute("id",se.getEntityId());
        mv.getModelMap().addAttribute("randomValue",se.getRandomValue());
        return mv;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employeeHome", "employee", new SimpleEntity());
    }

    @RequestMapping(value = "/display.htm", method = RequestMethod.POST)
    public String submit(@ModelAttribute("pojoEntity")SimpleEntity simpleEntity,
                         BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("id", 11/*pojoEntity.getEntityId()*/);
        model.addAttribute("randomValue", 1515/*pojoEntity.getRandomValue()*/);
        return "employeeView";
    }

}
