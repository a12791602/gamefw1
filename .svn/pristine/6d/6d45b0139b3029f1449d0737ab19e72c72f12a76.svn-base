/**   
 * 文件名称: WebGameController.java<br/>
 * 版本号: V1.0<br/>   
 * 创建人: alex<br/>  
 * 创建时间 : 2015-7-11 上午9:57:14<br/>
 */
package com.mh.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.mh.client.FlatClient;
import com.mh.commons.cache.CpConfigCache;
import com.mh.commons.conf.CommonConstant;
import com.mh.commons.conf.ResourceConstant;
import com.mh.commons.conf.WebConstants;
import com.mh.commons.constants.WebSiteManagerConstants;
import com.mh.commons.utils.SecurityEncode;
import com.mh.commons.utils.ServletUtils;
import com.mh.entity.TWebUserEleFavourite;
import com.mh.entity.WebGongGao;
import com.mh.entity.WebUser;
import com.mh.entity.WebUserFlat;
import com.mh.ifc.NSBIfcUtil;
import com.mh.ifc.SBTIfcUtil;
import com.mh.ifc.http.NsbConts;
import com.mh.ifc.http.NsbResResult;
import com.mh.ifc.http.SbtApiConstants;
import com.mh.ifc.http.SbtResBean;
import com.mh.service.WebUserEleFavouriteService;
import com.mh.service.WebUserFlatService;
import com.mh.service.WebUserService;
import com.mh.web.login.UserContext;
import com.mh.web.servlet.MySessionContext;
import com.mh.web.util.CheckDeviceUtil;

/**
 * 
 * 游戏平台Controller 类描述: TODO<br/>
 * 创建人: TODO alex<br/>
 * 创建时间: 2015-7-11 上午9:57:14<br/>
 */
@Controller
public class WebGameController extends BaseController {

	@Autowired
	private WebUserFlatService webUserFlatService;

	@Autowired
	private WebUserService webUserService;

	@Autowired
	private FlatClient flatClient;

	@Autowired
	private WebUserEleFavouriteService webUserEleFavouriteService;

	/**
	 * 跳转到相关页面 方法描述: TODO</br>
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @return ModelAndView
	 */
	@RequestMapping("/goPageCenter")
	public ModelAndView goPageCenter(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam("code") String code) {
		/** 以下的webUser用途、公告不能从数据库取 **/
		WebUser webUser = null;
		if (MySessionContext.getSession(request.getSession().getId()) != null) {
			UserContext uc = this.getUserContext(request);
			if (uc != null) {
				webUser = this.webUserService.findWebrUseByUserName(uc.getUserName());
			}
		}
		List<WebGongGao> dataList = WebSiteManagerConstants.WEBGONGGAO_LIST;
		ModelAndView mv = new ModelAndView("web/" + code);

		mv.addObject("dataList", dataList);
		mv.addObject("webUser", webUser);
		mv.addObject("lottery_list", CpConfigCache.GAME_CACHE_MAP);// 彩票类型

		if ("help".equals(code)) {
			mv.addObject("wpList", WebSiteManagerConstants.WEBPAGE_LIST);
		} else if ("promos".equals(code)) {
			mv.addObject("ptList", WebSiteManagerConstants.WEBPROMOSTYPE_LIST);
			mv.addObject("lbPromoList", WebSiteManagerConstants.LbWEBPROMOS_LIST);
		} else if ("sb".equals(code)) {
			/** 判断是否维护 **/
			String msg = WebSiteManagerConstants.ctxMap.get(CommonConstant.PLAT_PARAM_SB + "_" + WebConstants.WEB_WEIHU_STATUS_OFF);
			if (StringUtils.isNotBlank(msg)) {
				mv.setViewName("sport/weihu");
				return mv;
			}
			String backUrl = this.sb(request, response);
			mv.setViewName("web/sport_sb");
			mv.addObject("backToUrl", backUrl);
			return new ModelAndView("redirect:" + backUrl);
		} else if ("sbt".equals(code)) {
			/** 判断是否维护 **/
			String msg = WebSiteManagerConstants.ctxMap.get(CommonConstant.PLAT_PARAM_SBT + "_" + WebConstants.WEB_WEIHU_STATUS_OFF);
			if (StringUtils.isNotBlank(msg)) {
				mv.addObject("msg", msg);
				mv.setViewName("sport/weihu");
				return mv;
			}
			String backUrl = this.sbt(request);
			mv.setViewName("web/sport_sbt");
			mv.addObject("backToUrl", backUrl);
		} else if ("lottery".equals(code)) {// 彩票
			String cpparam = request.getParameter("cpparam");
			if (StringUtils.isNotEmpty(cpparam)) {
				mv.addObject("cpparam", cpparam);
			}
		}
		return mv;
	}

	@RequestMapping("/hg/goPageCenter")
	public ModelAndView gohgPageCenter(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam("code") String code) {
		/** 以下的webUser用途、公告不能从数据库取 **/
		WebUser webUser = null;
		if (MySessionContext.getSession(request.getSession().getId()) != null) {
			UserContext uc = this.getUserContext(request);
			if (uc != null) {
				webUser = this.webUserService.findWebrUseByUserName(uc.getUserName());
			}
		}
		List<WebGongGao> dataList = WebSiteManagerConstants.WEBGONGGAO_LIST;
		ModelAndView mv = new ModelAndView("web/hgPlay/" + code);

		mv.addObject("dataList", dataList);
		mv.addObject("webUser", webUser);

		if ("help".equals(code)) {
			mv.addObject("wpList", WebSiteManagerConstants.WEBPAGE_LIST);
		} else if ("promos".equals(code)) {
			mv.addObject("ptList", WebSiteManagerConstants.WEBPROMOSTYPE_LIST);
			mv.addObject("lbPromoList", WebSiteManagerConstants.LbWEBPROMOS_LIST);
		}
		return mv;
	}

	/**
	 * 电子游戏平台入口 方法描述: TODO</br>
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @return ModelAndView
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/electronic")
	public ModelAndView electronic(HttpServletRequest request, HttpServletResponse response, @RequestParam("code") String code) throws UnsupportedEncodingException {
		ModelAndView view = new ModelAndView("web/" + code + "Electronic");
		String gameType1 = request.getParameter("gameType1");
		String gameType2 = request.getParameter("gameType2");
		String status = request.getParameter("status");
		String gameName = request.getParameter("gameName");
		if (StringUtils.isNotEmpty(gameName)) {
			gameName = new String(gameName.getBytes("ISO-8859-1"), "utf-8");
		}
		TWebUserEleFavourite eleFavourite = null;
		WebUser webUser = null;
		UserContext uc = this.getUserContext(request);
		if (uc != null) {
			webUser = this.webUserService.findWebrUseByUserName(uc.getUserName());
		}
		if (StringUtils.equals(ResourceConstant.FAVOURITE, gameType1) && null == webUser) {
			sendErrorMsg(response, "请登录", getWebDomain(request));
			return null;
		} else if (null != webUser) {
			String device = StringUtils.defaultIfEmpty(request.getParameter("device"), StringUtils.equals(CheckDeviceUtil.checkDevice(request), "pc") ? "0" : "1");
			if (!device.equals("1") && !device.equals("0")) {
				device = StringUtils.equals(CheckDeviceUtil.checkDevice(request), "pc") ? "0" : "1";
			}

			eleFavourite = new TWebUserEleFavourite();
			eleFavourite.setUserName(webUser.getUserName());
			eleFavourite.setFlat(code);
			eleFavourite.setClient(device);// 0:PC 1:MOBILE
		}

		String key = StringUtils.isEmpty(gameType1) ? "all" : gameType1;
		if (WebConstants.FLAT_NAME_MG.equals(code)) {
			key = StringUtils.isEmpty(gameType1) ? "all" : gameType1 + (StringUtils.isEmpty(gameType2) ? "" : "_" + gameType2);
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForMg(key, gameType1, gameName, eleFavourite));
			view.addObject("mg_all", ResourceConstant.MG_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_TTG.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForTtg(key, gameType1, gameName, eleFavourite));
			view.addObject("ttg_all", ResourceConstant.TTG_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_GD.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForGd(key, gameType1, gameName, eleFavourite));
			view.addObject("gd_all", ResourceConstant.GD_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_PT.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForPt(key, gameType1, gameName, eleFavourite));
			view.addObject("pt_all", ResourceConstant.PT_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_OS.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForOs(key, gameType1, gameName, eleFavourite));
			view.addObject("os_all", ResourceConstant.OS_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_NEWNT.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForNt(key, gameType1, gameName, eleFavourite));
			view.addObject("newnt_all", ResourceConstant.NEWNT_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_AG.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForAg(key, gameType1, gameName, eleFavourite));
			view.addObject("ag_all", ResourceConstant.AG_ELECTRONIC_LIST);
		} else if (WebConstants.FLAT_NAME_BBIN.equals(code)) {
			view.addObject(ResourceConstant.RESULT, webUserEleFavouriteService.getShowGameListForBbin(key, gameType1, gameName, eleFavourite));
			view.addObject("bbin_all", ResourceConstant.BBIN_ELECTRONIC_LIST);
		}

		return view.addObject("code", code).addObject("webUser", webUser).addObject("dataList", WebSiteManagerConstants.WEBGONGGAO_LIST).addObject("gameType1", gameType1)
				.addObject("gameType2", gameType2).addObject("webUser", webUser).addObject("electronicClass", ResourceConstant.ELECTRONIC_CLASS_MAP.get(code)).addObject("status", status)
				.addObject("gameName", gameName);
	}

	@RequestMapping("/mobileelectronic")
	public void mobileelectronic(HttpServletRequest request, HttpServletResponse response) {
		String gameName = request.getParameter("gameName");// 游戏中文名
		String flat = request.getParameter("code");// 平台
		String type = request.getParameter("gameType");// 分类

		UserContext uc = getUserContext(request);
		TWebUserEleFavourite eleFavourite = null;
		if (StringUtils.equals(ResourceConstant.FAVOURITE, type) && null == uc) {
			ServletUtils.outPrintFail(request, response, "请登陆后查看您的收藏！");
			return;
		} else if (null != uc) {
			eleFavourite = new TWebUserEleFavourite();
			eleFavourite.setUserName(uc.getUserName());
			eleFavourite.setFlat(flat);
			eleFavourite.setClient(StringUtils.equals(CheckDeviceUtil.checkDevice(request), "pc") ? "0" : "1");// 0:PC 1:MOBILE
		}

		try {
			if (StringUtils.isNotEmpty(gameName)) {
				gameName = new String(gameName.getBytes("ISO-8859-1"), "utf-8");
			}
			List<?> list = null;
			if (WebConstants.FLAT_NAME_MG.equals(flat)) {
				list = webUserEleFavouriteService.getShowGameListForMg(type, gameName, eleFavourite);
			} else if (WebConstants.FLAT_NAME_TTG.equals(flat)) {
				list = webUserEleFavouriteService.getShowGameListForTtg(type, gameName, eleFavourite);
			} else if (WebConstants.FLAT_NAME_PT.equals(flat)) {
				list = webUserEleFavouriteService.getShowGameListForPt(type, gameName, eleFavourite);
			} else if (WebConstants.FLAT_NAME_OS.equals(flat)) {
				list = webUserEleFavouriteService.getShowGameListForOs(type, gameName, eleFavourite);
			} else if (WebConstants.FLAT_NAME_NT.equals(flat)) {
				list = webUserEleFavouriteService.getShowGameListForNt(type, gameName, eleFavourite);
			}
			ServletUtils.outPrintSuccess(request, response, list);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "服务器异常");
			e.printStackTrace();
		}
	}

	/**
	 * 游戏平台入口 方法描述: TODO</br>
	 * 
	 * @param request
	 * @param response
	 * @param gameType
	 * @return ModelAndView
	 */
	@RequestMapping("/forwardGame")
	public ModelAndView forwardGame(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = flatClient.flatLogin(request, response);
		return view;
	}

	@RequestMapping("/queryOrderStatus")
	public void queryOrderStatus(HttpServletRequest request, HttpServletResponse response) {
		flatClient.queryOrderStatus(request, response);
	}

	/**
	 * SB 方法描述: TODO</br>
	 * 
	 * @param request
	 * @return String
	 */
	private String sb(HttpServletRequest request, HttpServletResponse response) {
		try {
			String typeCode = request.getParameter("typeCode");
			String url = "";
			String _p = "";
			UserContext uc = this.getUserContext(request);
			if (uc == null) {
				if ("sportbook".equals(typeCode)) {
					url = CommonConstant.resCommMap.get(CommonConstant.SB_SPORTBOOK_LOGIN_URL);
					url += "/vender.aspx?lang=cs";
				}
				return url;
			}
			WebUserFlat webUserFlat = this.webUserFlatService.getWebUserFlat(uc.getUserName());

			if (StringUtils.isEmpty(webUserFlat.getSbUserName())) {
				webUserFlat = NSBIfcUtil.registSbAccout(webUserFlat);
				if (webUserFlat.getSbStatus() != null && webUserFlat.getSbStatus().intValue() == 1) {
					this.webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					return "";
				}
			}

			url = CommonConstant.resCommMap.get(CommonConstant.PT_LOGINAUT_URL);
			Map<String, String> params = new HashMap<String, String>();
			params.put(NsbConts.PLAYERNAME, webUserFlat.getSbUserName());// 游戏帐号
			NsbResResult result = NSBIfcUtil.login(params);
			if (result != null && NsbConts.RES_CODE_0.equals(result.getError_code())) {
				String token = result.getSessionToken();
				Double blance = 0.0;
				result = NSBIfcUtil.balance(params);
				if (result != null && NsbConts.RES_CODE_0.equals(result.getError_code())) {
					String data = result.getData();
					JSONArray array = JSONArray.parseArray(data);
					String _blance = array.getJSONObject(0).get("balance").toString();
					blance = Double.valueOf(_blance);
				}
				StringBuffer buff = new StringBuffer();

				buff.append(token);
				buff.append("|");
				buff.append(typeCode);
				buff.append("|");
				buff.append(blance);
				try {
					logger.info("沙巴加密参数:" + buff.toString());
					_p = SecurityEncode.encoderByDES(buff.toString(), SecurityEncode.key);
					logger.info("沙巴加密后参数:" + _p);
				} catch (Exception e) {
					e.printStackTrace();
				}
				url += "/spd/index/" + _p + "?lang=cs";
				return url;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * sbt
	 * 
	 * @param request
	 * @return
	 */
	private String sbt(HttpServletRequest request) {
		try {

			UserContext uc = this.getUserContext(request);
			if (null == uc) {
				Map<String, String> params = new HashMap<String, String>();
				params.put(SbtApiConstants.USER_ID, "");// 游戏帐号
				SbtResBean result = SBTIfcUtil.login(params);
				if (result != null) {
					String url = CommonConstant.resCommMap.get(CommonConstant.SBT_LOGIN_URL);
					url += "?stoken=" + CommonConstant.resCommMap.get(CommonConstant.MARCHID) + "_" + result.getToken();
					url += "&langid=" + SbtApiConstants.LANGUAGE_CODE;
					url += "&oddsstyleid=" + SbtApiConstants.ODDS_STYLE;
					return url;
				}
			}
			WebUserFlat webUserFlat = this.webUserFlatService.getWebUserFlat(uc.getUserName());
			if (StringUtils.isEmpty(webUserFlat.getSbtUserName())) {
				webUserFlat = SBTIfcUtil.registSbtAccout(webUserFlat);
				if (webUserFlat.getSbtStatus() != null && webUserFlat.getSbtStatus().intValue() == 1) {
					this.webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					return "";
				}
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put(SbtApiConstants.USER_ID, webUserFlat.getSbtUserName());// 游戏帐号
			SbtResBean result = SBTIfcUtil.login(params);
			if (result != null) {
				return result.getLoginUrl();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 游戏收藏
	 * @param request
	 * @param response
	 */
	@RequestMapping("/eleFavourite")
	public void eleFavourite(HttpServletRequest request, HttpServletResponse response, TWebUserEleFavourite favourite) {
		List<String> flatList = new ArrayList<String>();
		flatList.add(CommonConstant.PLAT_PARAM_AG);
		flatList.add(CommonConstant.PLAT_PARAM_MG);
		flatList.add(CommonConstant.PLAT_PARAM_OS);
		flatList.add(CommonConstant.PLAT_PARAM_GD);
		flatList.add(CommonConstant.PLAT_PARAM_TTG);
		flatList.add(CommonConstant.PLAT_PARAM_NEWNT);
		flatList.add(CommonConstant.PLAT_PARAM_PT);
		flatList.add(CommonConstant.PLAT_PARAM_BBIN);
		try {
			UserContext uc = this.getUserContext(request);
			if (null == uc) {
				ServletUtils.outPrintFail(request, response, "请先登陆");
				return;
			}

			String device = StringUtils.defaultIfEmpty(request.getParameter("device"), StringUtils.equals(CheckDeviceUtil.checkDevice(request), "pc") ? "0" : "1");
			if (!device.equals("1") && !device.equals("0")) {
				device = StringUtils.equals(CheckDeviceUtil.checkDevice(request), "pc") ? "0" : "1";
			}
			favourite.setClient(device);// 0:PC 1:MOBILE
			if (StringUtils.isEmpty(favourite.getFlat()) || StringUtils.isEmpty(favourite.getGameCode()) || !flatList.contains(favourite.getFlat())) {
				ServletUtils.outPrintFail(request, response, "无效参数 ");
				return;
			}

			favourite.setUserName(uc.getUserName());
			int favouriteCount = webUserEleFavouriteService.getFavouriteCount(favourite);
			if (favouriteCount > 0) {
				// ServletUtils.outPrintFail(request, response, "该游戏已收藏 ");
				webUserEleFavouriteService.cancelEleFavourite(favourite);
				ServletUtils.outPrintFail(request, response, "取消收藏成功 ");
				return;
			}

			String gameName = "";
			if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_AG)) {
				gameName = webUserEleFavouriteService.getAgEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_MG)) {
				gameName = webUserEleFavouriteService.getMgEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_OS)) {
				gameName = webUserEleFavouriteService.getOsEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_GD)) {
				gameName = webUserEleFavouriteService.getGdEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_TTG)) {
				gameName = webUserEleFavouriteService.getTtgEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_NEWNT)) {
				gameName = webUserEleFavouriteService.getNtEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_PT)) {
				gameName = webUserEleFavouriteService.getPtEleGameName(favourite);
			} else if (StringUtils.equals(favourite.getFlat(), CommonConstant.PLAT_PARAM_BBIN)) {
				gameName = webUserEleFavouriteService.getBbinEleGameName(favourite);
			}

			if (StringUtils.isEmpty(gameName)) {
				ServletUtils.outPrintFail(request, response, "该游戏不存在 ");
				return;
			}

			Date now = new Date();
			favourite.setCreateTime(now);
			favourite.setAutoUpdateTime(now);
			favourite.setGameName(gameName);
			webUserEleFavouriteService.insertEleFavourite(favourite);

			ServletUtils.outPrintSuccess(request, response, "收藏成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "收藏失败");
			e.printStackTrace();
		}
	}

	/**
	 * 取消游戏收藏
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelEleFavourite")
	public void cancelEleFavourite(HttpServletRequest request, HttpServletResponse response, TWebUserEleFavourite favourite) {
		List<String> flatList = new ArrayList<String>();
		flatList.add(CommonConstant.PLAT_PARAM_AG);
		flatList.add(CommonConstant.PLAT_PARAM_MG);
		flatList.add(CommonConstant.PLAT_PARAM_OS);
		flatList.add(CommonConstant.PLAT_PARAM_GD);
		flatList.add(CommonConstant.PLAT_PARAM_TTG);
		flatList.add(CommonConstant.PLAT_PARAM_NEWNT);
		try {
			UserContext uc = this.getUserContext(request);
			if (null == uc) {
				ServletUtils.outPrintFail(request, response, "请先登陆");
				return;
			}
			if (StringUtils.isEmpty(favourite.getFlat()) || StringUtils.isEmpty(favourite.getGameCode()) || !flatList.contains(favourite.getFlat())) {
				ServletUtils.outPrintFail(request, response, "无效参数 ");
				return;
			}

			favourite.setUserName(uc.getUserName());
			ServletUtils.outPrintSuccess(request, response, "取消成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "取消失败");
			e.printStackTrace();
		}
	}

	@RequestMapping("/valid/checkflatsatus")
	public void checkFlatSatus(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("code");
		String msg = "";
		boolean status = false;
		msg = WebSiteManagerConstants.ctxMap.get(type + "_" + WebConstants.WEB_WEIHU_STATUS_OFF);
		if (StringUtils.isNotBlank(msg)) {
			status = true;
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("status", status);
		dataMap.put("msg", msg);

		// 判断用户
		if (!status) {
			List<String> list = new ArrayList<String>();
			list.add(CommonConstant.PLAT_PARAM_AG);
			list.add(CommonConstant.PLAT_PARAM_BBIN);
			list.add(CommonConstant.PLAT_PARAM_DS);
			list.add(CommonConstant.PLAT_PARAM_HG);
			list.add(CommonConstant.PLAT_PARAM_MG);
			list.add(CommonConstant.PLAT_PARAM_NT);
			list.add(CommonConstant.PLAT_PARAM_PT);
			list.add(CommonConstant.PLAT_PARAM_HUANGGUAN);
			list.add(CommonConstant.PLAT_PARAM_CAIPIAO);
			list.add(CommonConstant.PLAT_PARAM_DOUJI);
			list.add(CommonConstant.PLAT_PARAM_AB);
			list.add(CommonConstant.PLAT_PARAM_OG);
			list.add(CommonConstant.PLAT_PARAM_OS);
			list.add(CommonConstant.PLAT_PARAM_SB);
			list.add(CommonConstant.PLAT_PARAM_GD);
			list.add(CommonConstant.PLAT_PARAM_TTG);
			list.add(CommonConstant.PLAT_PARAM_SBT);
			list.add(CommonConstant.PLAT_PARAM_NEWNT);
			list.add(CommonConstant.PLAT_PARAM_AGFISH);
			list.add(CommonConstant.PLAT_PARAM_SA);
			list.add(CommonConstant.PLAT_PARAM_VG);

			if (list.contains(type)) {
				UserContext uc = this.getUserContext(request);

				if (uc == null && type.equals(CommonConstant.PLAT_PARAM_SBT) || type.equals(CommonConstant.PLAT_PARAM_CAIPIAO)) {// 彩票 sbt可以不登陆
					ServletUtils.outPrintSuccess(request, response, dataMap);
					return;
				}

				if (uc == null) {
					if (CommonConstant.PLAT_PARAM_HUANGGUAN.equals(type) || CommonConstant.PLAT_PARAM_SB.equals(type)) {
						dataMap.put("status", false);
					} else {
						dataMap.put("status", true);
					}
					dataMap.put("msg", "请先登录！");
					ServletUtils.outPrintSuccess(request, response, dataMap);
					return;
				}
				/*
				 * WebUser webUser = this.webUserService.findWebrUseByUserName(uc.getUserName()); if (webUser != null && webUser.getBetFlat() != null &&
				 * !"".equals(webUser.getBetFlat())) { String betFlat = webUser.getBetFlat(); if (betFlat.indexOf(type) != -1) { dataMap.put("status", true);
				 * dataMap.put("msg", "无权限访问该平台，请联系我们处理！"); } }
				 */
				boolean flag = this.webUserService.checkFlatPermissions(type, uc.getUserName());
				if (flag) {
					dataMap.put("status", true);
					dataMap.put("msg", "无权限访问该平台，请联系我们处理！");
				}
			}

		}
		ServletUtils.outPrintSuccess(request, response, dataMap);
	}

	public WebUserFlatService getWebUserFlatService() {
		return webUserFlatService;
	}

	public void setWebUserFlatService(WebUserFlatService webUserFlatService) {
		this.webUserFlatService = webUserFlatService;
	}

	public WebUserService getWebUserService() {
		return webUserService;
	}

	public void setWebUserService(WebUserService webUserService) {
		this.webUserService = webUserService;
	}
}
