package com.mh.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class WebAntiHackIOSController extends BaseController 
{
  @RequestMapping(value="/antiHack_ios")
  public ModelAndView initWebData(HttpServletRequest request,HttpServletResponse response,
      @RequestParam(value="code",required=false,defaultValue="") String code)
  {
    ModelAndView mv=new ModelAndView("web/antiHack_ios");
    return mv.addObject("code", code);
  }
}
