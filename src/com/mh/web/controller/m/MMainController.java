package com.mh.web.controller.m;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mh.commons.comparator.ComparatorCarouselDesc;
import com.mh.commons.conf.ResourceConstant;
import com.mh.commons.constants.WebSiteManagerConstants;
import com.mh.commons.utils.ServletUtils;
import com.mh.entity.WebCarousel;
import com.mh.entity.WebGongGao;
import com.mh.entity.WebMgElectronic;
import com.mh.entity.WebNewNtElectronic;
import com.mh.entity.WebOsElectronic;
import com.mh.entity.WebPage;
import com.mh.entity.WebPromos;
import com.mh.entity.WebPtElectronic;
import com.mh.entity.WebTtgElectronic;
import com.mh.web.controller.BaseController;
import com.mh.web.login.UserContext;
import com.mh.web.util.MobilePage;

@Controller
@RequestMapping("/m")
public class MMainController extends BaseController {
	/**
	 * 总页数
	 */
	private static final int PAGE_NUM = 5;

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "code", required = false) String code) {
		if (StringUtils.isBlank(code)) {
			code = "index";
		}
		ModelAndView model = new ModelAndView("m/index/" + code);
		UserContext webUser = this.getUserContext(request);
		// 公告
		List<WebGongGao> ggList = WebSiteManagerConstants.WEBGONGGAO_LIST;
		List<WebCarousel> carousel = WebSiteManagerConstants.WEBCAROUSEL_LIST;
		List<WebCarousel> carousels = new ArrayList<WebCarousel>();
		if (carousel != null) {
			for (WebCarousel webCarousel : carousel) {
				if (webCarousel.getCrsType() == 1) {
					carousels.add(webCarousel);
				}
			}
		}

		Collections.sort(carousels, new Comparator<WebCarousel>() {
			public int compare(WebCarousel o1, WebCarousel o2) {
				return o2.getCrsIndex().compareTo(o1.getCrsIndex());
			}
		});

		if (code.equals("slot_mg")) {
			mgPage(model);
		} else if (code.equals("slot_pt")) {
			ptPage(model);
		} else if (code.equals("slot_os")) {
			osPage(model);
		} else if (code.equals("slot_ttg")) {
			ttgPage(model);
		} else if (code.equals("slot_nt")) {
			ntPage(model);
		}

		return model.addObject("webUser", webUser).addObject("ggList", ggList).addObject("code", code).addObject("carousels", carousels)
				.addObject("ctxMap", WebSiteManagerConstants.ctxMap);
	}

	@RequestMapping("/help")
	public ModelAndView help(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "code", required = false, defaultValue = "") String code) {
		ModelAndView model = new ModelAndView("m/index/foot_help");
		UserContext webUser = this.getUserContext(request);
		Map<String, WebPage> pageContent = WebSiteManagerConstants.WEBPAGE_MOBILE_MAP;
		// 公告
		List<WebGongGao> ggList = WebSiteManagerConstants.WEBGONGGAO_LIST;
		List<WebCarousel> carousel = WebSiteManagerConstants.WEBCAROUSEL_LIST;
		List<WebCarousel> carousels = new ArrayList<WebCarousel>();
		if (carousel != null) {
			for (WebCarousel webCarousel : carousel) {
				if (webCarousel.getCrsType() == 1) {
					carousels.add(webCarousel);
				}
			} 
		}
		if (!pageContent.isEmpty()) {
			if (pageContent.containsKey(code)) {
				model.addObject("pageContent", pageContent.get(code).getPgContent());
			}
		}
		return model.addObject("webUser", webUser).addObject("code", code).addObject("ctxMap", WebSiteManagerConstants.ctxMap).addObject("ggList", ggList).addObject("carousels", carousels);
	}

	private void mgPage(ModelAndView model) {
		List<WebMgElectronic> list = ResourceConstant.MOBILE_MG_ELECTRONIC_LIST;
		MobilePage<WebMgElectronic> page = new MobilePage<WebMgElectronic>();
		page.toPageSlot(list, PAGE_NUM, model);
		pageFoot(model);
	}

	private void osPage(ModelAndView model) {
		List<WebOsElectronic> list = ResourceConstant.MOBILE_OS_ELECTRONIC_LIST;
		MobilePage<WebOsElectronic> page = new MobilePage<WebOsElectronic>();
		page.toPageSlot(list, PAGE_NUM, model);
		pageFoot(model);
	}

	private void ttgPage(ModelAndView model) {
		List<WebTtgElectronic> list = ResourceConstant.MOBILE_TTG_ELECTRONIC_LIST;
		//model.addObject("ttgslot", list);
		 MobilePage<WebTtgElectronic> page = new MobilePage<WebTtgElectronic>();
		 page.toPageSlot(list, PAGE_NUM,model);
		 pageFoot(model);
	}

	private void ptPage(ModelAndView model) {
		List<WebPtElectronic> list = ResourceConstant.MOBILE_PT_ELECTRONIC_LIST;
		MobilePage<WebPtElectronic> page = new MobilePage<WebPtElectronic>();
		page.toPageSlot(list, PAGE_NUM, model);
		pageFoot(model);
	}
	
	private void ntPage(ModelAndView model) {
		List<WebNewNtElectronic> list = ResourceConstant.MOBILE_NEWNT_ELECTRONIC_LIST;
		model.addObject("ntslot", list);
	}

	private void pageFoot(ModelAndView model) {
		List<String> pageList = new ArrayList<String>();
		for (int i = 1; i <= PAGE_NUM; i++) {
			pageList.add("page" + i);
		}
		model.addObject("pageList", pageList);
	}
	
	@RequestMapping("/getCarouselList")
	public void getCarouselList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<WebCarousel> list = WebSiteManagerConstants.WEBCAROUSEL_LIST;
			Collections.sort(list, new ComparatorCarouselDesc());
			for (WebCarousel webCarousel : list) {
				webCarousel.setSysUserName(null);
			}
			ServletUtils.outPrintSuccess(request, response, list);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintSuccess(request, response, "服务器异常");
		}
	}
	
	@RequestMapping("/getGongGaoList")
	public void getGongGaoList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<WebGongGao> ggList = WebSiteManagerConstants.WEBGONGGAO_LIST;
			for (WebGongGao webGongGao : ggList) {
				webGongGao.setId(null);
				webGongGao.setManagerUserName(null);
			}
			ServletUtils.outPrintSuccess(request, response, ggList);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "服务器异常");
		}
	}
	
	@RequestMapping("/getPromosList")
	public void getPromosList(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<WebPromos> promosList = WebSiteManagerConstants.LbWEBPROMOS_LIST;
			for (WebPromos promos : promosList) {
				promos.setId(null);
			}
			ServletUtils.outPrintSuccess(request, response, promosList);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "服务器异常");
		}
	}
	
	@RequestMapping("/getEleGameList")
	public void getEleGameList(HttpServletRequest request, HttpServletResponse response) {
		String flat = request.getParameter("flat");
		try {
			Map<String,List<?>> map = new HashMap<String, List<?>>();
			map.put("mg", ResourceConstant.MOBILE_MG_ELECTRONIC_LIST);
			map.put("pt", ResourceConstant.MOBILE_PT_ELECTRONIC_LIST);
			map.put("os", ResourceConstant.MOBILE_OS_ELECTRONIC_LIST);
			map.put("ttg", ResourceConstant.MOBILE_TTG_ELECTRONIC_LIST);
			map.put("nt", ResourceConstant.MOBILE_NEWNT_ELECTRONIC_LIST);
			ServletUtils.outPrintSuccess(request, response, map.get(flat));
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "服务器异常");
		}
	}
}
