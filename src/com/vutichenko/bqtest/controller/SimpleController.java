package com.vutichenko.bqtest.controller;

import com.vutichenko.bqtest.pojo.SimpleEntity;
import com.vutichenko.bqtest.service.EntityManagerUtil;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by HolyDos on 28.04.2016.
 */
@org.springframework.stereotype.Controller
public class SimpleController {

    EntityManagerUtil managerUtil = new EntityManagerUtil();
    int limit = 10;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mv = new ModelAndView("/WEB-INF/jsp/display.jsp");
        SimpleEntityProducer producer = new SimpleEntityProducer();
        SimpleEntityConsumer consumer = new SimpleEntityConsumer();
        producer.start();
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.start();
       /* try {
            managerUtil.savePojoEntity("a random 2",10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SimpleEntity se = EntityManagerUtil.getEntityList().iterator().next();
        mv.getModelMap().addAttribute("id", se.getEntityId());
        mv.getModelMap().addAttribute("randomValue", se.getRandomValue());*/
        return mv;
    }

    private class SimpleEntityProducer extends Thread {


        @Override
        public void run() {



            while (true) {
                try {
                    managerUtil.savePojoEntity("a random 2", limit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class SimpleEntityConsumer extends Thread {

        @Override
        public void run() {
            while (true) {
                List<SimpleEntity> entities = EntityManagerUtil.getEntityList();
                try {
                    if(entities.size()>0)
                    managerUtil.removeSimpleEntity(entities.get(entities.size()-1).getEntityId(),limit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employeeHome", "employee", new SimpleEntity());
    }

    @RequestMapping(value = "/display.htm", method = RequestMethod.POST)
    public String submit(@ModelAttribute("pojoEntity") SimpleEntity simpleEntity,
                         BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("id", 11/*pojoEntity.getEntityId()*/);
        model.addAttribute("randomValue", 1515/*pojoEntity.getRandomValue()*/);
        return "employeeView";
    }

}
