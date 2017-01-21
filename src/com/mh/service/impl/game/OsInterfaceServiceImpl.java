package com.mh.service.impl.game;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mh.client.BaseFlatInfo;
import com.mh.commons.cache.MemCachedUtil;
import com.mh.commons.conf.WebConstants;
import com.mh.commons.utils.IPSeeker;
import com.mh.entity.WebEdu;
import com.mh.entity.WebUser;
import com.mh.entity.WebUserFlat;
import com.mh.ifc.OSIfcUtil;
import com.mh.ifc.http.ComEduConts;
import com.mh.ifc.http.Conts;
import com.mh.ifc.http.OsConts;
import com.mh.ifc.http.OsResResult;
import com.mh.ifc.util.ComUtil;
import com.mh.ifc.util.DateUtil;
import com.mh.service.FlatInterfaceService;
import com.mh.web.login.UserContext;
/**
 * OS
 * @author Administrator
 *
 */
@Service("osInterfaceServiceImpl")
public class OsInterfaceServiceImpl extends BaseFlatInfo implements FlatInterfaceService {

	public String login(HttpServletRequest request, HttpServletResponse response) {
		try {

			UserContext uc = getUserContext(request);
			WebUserFlat webUserFlat = webUserFlatService.getWebUserFlat(uc.getUserName());

			if (StringUtils.isEmpty(webUserFlat.getOsUserName())) {
				webUserFlat = OSIfcUtil.registOsAccout(webUserFlat);
				if (webUserFlat.getOsStatus() != null && webUserFlat.getOsStatus().intValue() == 1) {
					webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					return "";
				}
			}
			String gameCode = request.getParameter("gameName");
			Map<String, String> params = new HashMap<String, String>();
			params.put(OsConts.USERNAME, webUserFlat.getOsUserName());// 游戏帐号
			params.put(OsConts.IPADRESS, IPSeeker.getIpAddress(request));// 密码
			params.put(OsConts.GAMECODE, gameCode);
			params.put(OsConts.PLAYMODEL, OsConts.MODEL_REAL);//真实模式
			OsResResult result = OSIfcUtil.login(params);
			if (result != null) {
				return result.getUrl();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public double searchUserBlance(WebUserFlat webUserFlat) throws Exception {
		double blance = 0;
		Map<String,String> params = new HashMap<String,String>();
		//注册
		if (StringUtils.isEmpty(webUserFlat.getOsUserName())) {
			webUserFlat = OSIfcUtil.registOsAccout(webUserFlat);
			if (webUserFlat.getOsStatus() != null && webUserFlat.getOsStatus().intValue() == 1) {
				webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
			}
		}
		//查余额
		params.put(OsConts.USERNAME, webUserFlat.getOsUserName());
		OsResResult result = OSIfcUtil.balance(params);
		if (result != null && OsConts.RES_OK.equals(result.getErrorCode())) {
			blance =  result.getBalance().doubleValue();
		}
	
		return blance;
	}

	public String updateEdu(WebUserFlat webUserFlat, WebUser webUser,String flatName, String optType, int points, WebEdu webEdu)  {
		String tsMsg = "";
		Integer eduForward = webEdu.getEduForward();
		String eduForwardRemark = webEdu.getEduForwardRemark();
		if(StringUtils.isEmpty(webUserFlat.getOsUserName())){
			 try {
				webUserFlat =  OSIfcUtil.registOsAccout(webUserFlat);
				if(webUserFlat.getOsStatus()!= null && webUserFlat.getOsStatus().intValue()==1){
					 webUserFlatDao.update(webUserFlat);
					 tsMsg = updateOsAccountPonit(webUser, flatName, webUserFlat.getOsUserName(), optType, points, eduForward, eduForwardRemark);
				 }else{
					 tsMsg="额度转换失败，错误代码："+webUserFlat.getReturnCode();
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else{
			 tsMsg = updateOsAccountPonit(webUser, flatName, webUserFlat.getOsUserName(), optType, points, eduForward, eduForwardRemark);
		 }
		return tsMsg;
	}
	
	private String updateOsAccountPonit(WebUser webUser,String flatName,String flatAccount,String optType ,int points,int eduForward, String eduForwardRemark) {
		String eduOrder = ComUtil.getOrder();// 订单编号
		Date gmt_4_date = DateUtil.getGMT_4_Date();// 美东时间
		String userflat = flatAccount.substring(0, 2);
		
		if (StringUtils.equalsIgnoreCase(optType, WebConstants.EDU_TYPE_2)){// 转入 （主帐号-》游戏平台）
			return osDepositOpt(webUser,flatName,optType, flatAccount, points,eduForward, eduForwardRemark);
		}
		
		//先添加额度记录
		try {
			addWebEduRecord(webUser,WebConstants.EDU_STATUS_INIT, eduOrder, -points, eduForward, eduForwardRemark, gmt_4_date, flatName);
		} catch (Exception e2) {
			return "额度记录添加失败!";
		}
		
		/**请求API 创建EDU记录**/
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(ComEduConts.EDUORDER, eduOrder);
		jsonObj.put(ComEduConts.USERNAME, webUser.getUserName());
		jsonObj.put(ComEduConts.EDUPOINTS, -points+"");
		jsonObj.put(ComEduConts.EDUTYPE, WebConstants.EDU_TYPE_1);
		jsonObj.put(ComEduConts.EDUSTATUS, "-1");
		jsonObj.put(ComEduConts.EDUIP, "0.0.0.0");
		jsonObj.put(ComEduConts.EDUFORWARD, eduForward+"");
		jsonObj.put(ComEduConts.EDUFORWARDREMARK, eduForwardRemark);
		jsonObj.put(ComEduConts.WEBFLAG, userflat);
		jsonObj.put(ComEduConts.FLATNAME,flatName);
		jsonObj.put(ComEduConts.FLATUSERNAME,flatAccount);
		
		Map<String,String> paramMap= new HashMap<String,String>();
		paramMap.put("params", jsonObj.toString());
		boolean eduResult = optInterfaceEdu(paramMap);
		if(!eduResult){
			//超端添加失败,订单状态改为失败
			webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转出失败" , WebConstants.EDU_STATUS_0);
			return Conts.EDU_FAILURE;
		}
		
		//转出（游戏平-》台主帐号）
		OsResResult result = null;
		try{
			//转出
			Map<String, String> params = new HashMap<String, String>();
			params.put(OsConts.USERNAME,flatAccount);
			params.put(OsConts.AMOUNT, String.valueOf(points));
			params.put(OsConts.BILLNO, eduOrder);
			params.put(OsConts.WEBFLAG, userflat);
			
			result = OSIfcUtil.withdrawal(params);
			
		}catch (Exception e) {
			try {
				MemCachedUtil.setEduNotice(flatName);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return WebConstants.EXCPTION_MSG;
		}
		
		int edustatus = WebConstants.EDU_STATUS_0;
		try{
			if(result!= null && OsConts.RES_OK.equals(result.getErrorCode())){//成功
				int rows = webUserDao.updateWebUserForMoney(webUser.getUserName(), points);
				if(rows<1){
					//如果这里失败了  不能回滚事务,应该是异常订单,让客服审核
					logger.error("更新用户额度失败！");
					return WebConstants.EXCPTION_MSG;
				}
				//主帐号增加额度
				double userMoney = webUser.getUserMoney().doubleValue() + points;
				webUser.setUserMoney(userMoney);
				//资金流水
				addAccountRecord(webUser, flatName, optType, -points, eduOrder, eduForwardRemark, gmt_4_date);;
				edustatus = WebConstants.EDU_STATUS_1;
			}
		}catch(Exception e){
			return WebConstants.EXCPTION_MSG;
		}
		webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转出" + (WebConstants.EDU_STATUS_1 == edustatus ? "成功" : "失败") , edustatus);
		return WebConstants.EDU_STATUS_1 == edustatus ? Conts.EDU_SUCCESS : Conts.EDU_FAILURE;
	}
	
	private String osDepositOpt(WebUser webUser,String flatName,String optType, String flatAccount, int points, int eduForward, String eduForwardRemark) {
		
		String logStrPrefix = eduForwardRemark+"额度操作主帐号" + webUser.getUserName() + " 转入金额" + points;
		String eduOrder = ComUtil.getOrder();// 订单编号
		Date gmt_4_date = DateUtil.getGMT_4_Date();// 美东时间
		int eduPoints = points;
		String userflat = flatAccount.substring(0, 2);
		
		//入款记录
		try {
			addWebEduRecord(webUser, WebConstants.EDU_STATUS_INIT, eduOrder, eduPoints, eduForward, eduForwardRemark, gmt_4_date, flatName);
		} catch (Exception e2) {
			e2.printStackTrace();
			return "额度记录添加失败!";
		}
		
		/**请求API 创建EDU记录**/
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(ComEduConts.EDUORDER, eduOrder);
		jsonObj.put(ComEduConts.USERNAME, webUser.getUserName());
		jsonObj.put(ComEduConts.EDUPOINTS, eduPoints);
		jsonObj.put(ComEduConts.EDUTYPE, "2");
		jsonObj.put(ComEduConts.EDUSTATUS, "-1");
		jsonObj.put(ComEduConts.EDUIP, "0.0.0.0");
		jsonObj.put(ComEduConts.EDUFORWARD, eduForward+"");
		jsonObj.put(ComEduConts.EDUFORWARDREMARK, eduForwardRemark);
		jsonObj.put(ComEduConts.WEBFLAG, userflat);
		jsonObj.put(ComEduConts.FLATNAME,flatName);
		jsonObj.put(ComEduConts.FLATUSERNAME,flatAccount);
		
		Map<String,String> paramMap= new HashMap<String,String>();
		paramMap.put("params", jsonObj.toString());
		boolean eduResult = optInterfaceEdu(paramMap);
		if(!eduResult){
			webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转入失败", WebConstants.EDU_STATUS_0);
			logger.info("调用API创建edu记录失败");
			return Conts.EDU_FAILURE;
		}
 
		// 减少用户余额
		double userMoney = webUser.getUserMoney().doubleValue() - points;
		webUser.setUserMoney(userMoney);
		int rows = webUserDao.updateWebUserForMoney(webUser.getUserName(), -points);
		if(rows<0){
			webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转入失败", WebConstants.EDU_STATUS_0);
			logger.error("更新用户额度失败！");
			return "用户额度更新失败!";
		}
		int edustatus = WebConstants.EDU_STATUS_0;//默认失败
		try {
			//财务记录
			addAccountRecord(webUser, flatName, optType, eduPoints, eduOrder, eduForwardRemark, gmt_4_date);
			/** 平台帐号添加额度 **/
			OsResResult result = null;
			
			Map<String, String> params = new HashMap<String, String>();
			params.put(OsConts.USERNAME,flatAccount);
			params.put(OsConts.AMOUNT, String.valueOf(points));
			params.put(OsConts.BILLNO, eduOrder);
			params.put(OsConts.WEBFLAG, userflat);
			result = OSIfcUtil.deposit(params);
			
			if (result!= null && OsConts.RES_OK.equals(result.getErrorCode())) {
				edustatus = WebConstants.EDU_STATUS_1;
			}else{
				/** 主帐号增加额度 **/
				userMoney = webUser.getUserMoney().doubleValue() +eduPoints;
				webUser.setUserMoney(userMoney);
				rows = webUserDao.updateWebUserForMoney(webUser.getUserName(), eduPoints);
				if(rows < 1){
					logger.error("更新用户额度失败！");
					return WebConstants.EXCPTION_MSG;
				} else {
					//财务记录
					addAccountRecord(webUser, flatName, optType, -eduPoints, eduOrder, "额度转化失败，返还金额", gmt_4_date);
				}
			}
		}catch (Exception e) {
			logger.error(logStrPrefix + "异常!",e);
			try {
				MemCachedUtil.setEduNotice(flatName);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return WebConstants.EXCPTION_MSG;
		} 
		webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转入" + (WebConstants.EDU_STATUS_1 == edustatus ? "成功" : "失败") , edustatus);
		return WebConstants.EDU_STATUS_1 == edustatus ? Conts.EDU_SUCCESS : Conts.EDU_FAILURE ;
	}

	public ModelAndView mobileLogin(HttpServletRequest request,HttpServletResponse response) {
		OsResResult result = null;
		String domain = this.getWebDomain(request) + "m/main?code=slot_os";
		try {
			UserContext uc = this.getUserContext(request);
			WebUserFlat webUserFlat = this.webUserFlatService.getWebUserFlat(uc.getUserName());
			if (StringUtils.isEmpty(webUserFlat.getOsUserName())) {
				webUserFlat = OSIfcUtil.registOsAccout(webUserFlat);
				if (webUserFlat.getOsStatus() != null && webUserFlat.getOsStatus().intValue() == 1) {
					this.webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					sendErrorMsg(response,"【OS平台注册用户失败】",domain);
					return null;
				}
			}
			String gameCode = request.getParameter("gameName");
			Map<String, String> params = new HashMap<String, String>();
			params.put(OsConts.USERNAME, webUserFlat.getOsUserName());// 游戏帐号
			params.put(OsConts.IPADRESS, IPSeeker.getIpAddress(request));// 密码
			params.put(OsConts.GAMECODE, gameCode);
			params.put(OsConts.PLAYMODEL, OsConts.MODEL_REAL);//真实模式
			
			params.put(OsConts.LOGIN_URL, this.getWebDomain(request) + "m/main");
			params.put(OsConts.ERROR_URL, this.getWebDomain(request) + "m/main");
			params.put(OsConts.BACK_URL, domain);
			/*
			if(ResourceConstant.OS_ELECTRONIC_H5GAME.contains(gameCode)){
				params = new HashMap<String, String>();
				params.put(OsConts.USERNAME, webUserFlat.getOsUserName());// 游戏帐号
				params.put(OsConts.IPADRESS, IPSeeker.getIpAddress(request));// 密码
				params.put(OsConts.GAMECODE, gameCode);
				params.put(OsConts.PLAYMODEL, OsConts.MODEL_REAL);//真实模式
				result = OSIfcUtil.login(params);
			}else{*/
				result = OSIfcUtil.login_h5(params);
			/*}*/
			if (result == null) {
				sendErrorMsg(response,"【OS】平台登录失败",domain);
				return null;
			}
 
		} catch (Exception e) {
			e.printStackTrace();
			sendErrorMsg(response,"【OS】平台登录失败",domain);
		}
		return new ModelAndView("redirect:"+result.getUrl());
	}

	@Override
	public void queryOrderStatus(HttpServletRequest request,HttpServletResponse response) {
	}
}
