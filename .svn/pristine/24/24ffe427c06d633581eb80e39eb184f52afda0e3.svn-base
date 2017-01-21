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
import com.mh.commons.utils.ServletUtils;
import com.mh.entity.WebEdu;
import com.mh.entity.WebUser;
import com.mh.entity.WebUserFlat;
import com.mh.ifc.OGIfcUtil;
import com.mh.ifc.http.ComEduConts;
import com.mh.ifc.http.Conts;
import com.mh.ifc.http.OgConts;
import com.mh.ifc.http.OgResResult;
import com.mh.ifc.util.ComUtil;
import com.mh.ifc.util.DateUtil;
import com.mh.service.FlatInterfaceService;
import com.mh.web.login.UserContext;
/**
 * OG
 * @author Administrator
 *
 */
@Service("ogInterfaceServiceImpl")
public class OgInterfaceServiceImpl extends BaseFlatInfo implements FlatInterfaceService {
	
	public String login(HttpServletRequest request, HttpServletResponse response) {
		try {

			UserContext uc = this.getUserContext(request);
			WebUserFlat webUserFlat = this.webUserFlatService.getWebUserFlat(uc.getUserName());

			if (StringUtils.isEmpty(webUserFlat.getOgUserName())) {
				webUserFlat = OGIfcUtil.registOgAccout(webUserFlat);
				if (webUserFlat.getOgStatus() != null && webUserFlat.getOgStatus().intValue() == 1) {
					this.webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					return "";
				}
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put(OgConts.USERNAME, webUserFlat.getOgUserName());// 游戏帐号
			params.put(OgConts.PASSWORD, webUserFlat.getOgPassword());// 密码
			params.put(OgConts.GAMETYPE, "1");// 1pc  21mobile
			OgResResult result = OGIfcUtil.login(params);
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
		if (StringUtils.isEmpty(webUserFlat.getOgUserName())) {
			webUserFlat = OGIfcUtil.registOgAccout(webUserFlat);
			if (webUserFlat.getOgStatus() != null && webUserFlat.getOgStatus().intValue() == 1) {
				webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
			}
		}
		//查余额
		params.put(OgConts.USERNAME, webUserFlat.getOgUserName());
		params.put(OgConts.PASSWORD, webUserFlat.getOgPassword());
		OgResResult result = OGIfcUtil.balance(params);
		if (result != null && OgConts.RES_OK.equals(result.getResultCode())) {
			blance =  Double.valueOf(result.getResult());
		}
		return blance;
	}

	public String updateEdu(WebUserFlat webUserFlat, WebUser webUser,String flatName, String optType, int points, WebEdu webEdu)  {
		String tsMsg = "";
		Integer eduForward = webEdu.getEduForward();
		String eduForwardRemark = webEdu.getEduForwardRemark();
		if(StringUtils.isEmpty(webUserFlat.getOgUserName())){
			 try {
				webUserFlat =  OGIfcUtil.registOgAccout(webUserFlat);
				if(webUserFlat.getOgStatus()!= null && webUserFlat.getOgStatus().intValue()==1){
					 webUserFlatDao.update(webUserFlat);
					 tsMsg  = updateOgAccountPonit(webUser, flatName, webUserFlat.getOgUserName(), webUserFlat.getOgPassword(), optType, points, eduForward, eduForwardRemark);
				 }else{
					 tsMsg="额度转换失败，错误代码："+webUserFlat.getReturnCode();
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else{
			 tsMsg = updateOgAccountPonit(webUser, flatName, webUserFlat.getOgUserName(), webUserFlat.getOgPassword(), optType, points, eduForward, eduForwardRemark);
		 }
		return tsMsg;
	}
	
	private String updateOgAccountPonit(WebUser webUser,String flatName,String flatAccount, String password, String optType,int points,int eduForward, String eduForwardRemark) {
		String eduOrder = ComUtil.getNumr(10);// 订单编号
		Date gmt_4_date = DateUtil.getGMT_4_Date();// 美东时间
		String userflat = flatAccount.substring(0, 2);
		
		if (StringUtils.equalsIgnoreCase(optType, WebConstants.EDU_TYPE_2)){// 转入 （主帐号-》游戏平台）
			return OgDepositOpt(webUser,flatName,optType, flatAccount, password, points,eduForward, eduForwardRemark);
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
		OgResResult result = null;
		try{
			//转出
			Map<String, String> params = new HashMap<String, String>();
			params.put(OgConts.USERNAME,flatAccount);
			params.put(OgConts.PASSWORD, password);
			params.put(OgConts.CREDIT, String.valueOf(points));
			params.put(OgConts.BILLNO, eduOrder);
			params.put(OgConts.OPERTYPE, OgConts.OPER_TYPE_OUT);
			params.put(OgConts.WEBFLAG, userflat);
			result = OGIfcUtil.transferCredit(params);
		}catch (Exception e) {
			try {
				MemCachedUtil.setEduNotice(flatName);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return WebConstants.EXCPTION_MSG;
		}
		
		int edustatus = 0;
		WebUserFlat webUserFlat = webUserFlatDao.getWebUserFlat(webUser.getUserName());
		try{
			boolean queryStatus = this.queryOrderStatus(eduOrder, webUserFlat.getOgUserName(), webUserFlat.getOgPassword(), OgConts.OPER_TYPE_OUT, points + "");
			if(result!= null && OgConts.RES_OK.equals(result.getResultCode())){//成功
				if(queryStatus){
					//两次校验都成功 账户添加额度
					int rows = webUserDao.updateWebUserForMoney(webUser.getUserName(), points);
					if(rows < 1){
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
			}else{
				if(queryStatus){
					//两次校验不一致  异常订单
					return WebConstants.EXCPTION_MSG;
				}
			}
		}catch(Exception e){
			logger.error("额度转换失败",e);
			try {
				boolean queryStatus = this.queryOrderStatus(eduOrder, webUserFlat.getOgUserName(), webUserFlat.getOgPassword(), OgConts.OPER_TYPE_OUT, points + "");
				if(queryStatus){
					MemCachedUtil.setEduNotice(flatName);
					return WebConstants.EXCPTION_MSG;
				} 
			} catch (Exception e1) {
				e1.printStackTrace();
				return WebConstants.EXCPTION_MSG;
			}
		}
		webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转出" + (WebConstants.EDU_STATUS_1 == edustatus ? "成功" : "失败") , edustatus);
		return WebConstants.EDU_STATUS_1 == edustatus ? Conts.EDU_SUCCESS : Conts.EDU_FAILURE;
	}
	
	private String OgDepositOpt(WebUser webUser,String flatName,String optType, String flatAccount, String password, int points, int eduForward, String eduForwardRemark) {
		
		String logStrPrefix = eduForwardRemark+"额度操作主帐号" + webUser.getUserName() + " 转入金额" + points;
		String eduOrder = ComUtil.getNumr(10);// 订单编号
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
			logger.error("用户额度更新失败!");
			return "用户额度更新失败!";
		}
		
		int edustatus = WebConstants.EDU_STATUS_0;//默认失败
		WebUserFlat webUserFlat = webUserFlatDao.getWebUserFlat(webUser.getUserName());
		try {
			//财务记录
			addAccountRecord(webUser, flatName, optType, eduPoints, eduOrder, eduForwardRemark, gmt_4_date);
			/** 平台帐号添加额度 **/
			OgResResult result = null;
			Map<String, String> params = new HashMap<String, String>();
			params.put(OgConts.USERNAME,flatAccount);
			params.put(OgConts.PASSWORD, password);
			params.put(OgConts.CREDIT, String.valueOf(points));
			params.put(OgConts.BILLNO, eduOrder);
			params.put(OgConts.OPERTYPE, OgConts.OPER_TYPE_IN);
			params.put(OgConts.WEBFLAG, userflat);
			result = OGIfcUtil.transferCredit(params);
			
			boolean queryStatus = this.queryOrderStatus(eduOrder, webUserFlat.getOgUserName(), webUserFlat.getOgPassword(), OgConts.OPER_TYPE_IN, points + "");
			if (result != null && OgConts.RES_OK.equals(result.getResultCode())) {
				if(!queryStatus){
					return WebConstants.EXCPTION_MSG;
				}
				edustatus = WebConstants.EDU_STATUS_1;
			}else{
				if(queryStatus){
					edustatus = WebConstants.EDU_STATUS_1;
				} else {
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
			}
		}catch (Exception e) {
			logger.error(logStrPrefix + "异常!",e);
			try {
				MemCachedUtil.setEduNotice(flatName);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				boolean queryStatus = this.queryOrderStatus(eduOrder, webUserFlat.getOgUserName(), webUserFlat.getOgPassword(), OgConts.OPER_TYPE_IN, points + "");
				if(queryStatus){//异常订单
					return WebConstants.EXCPTION_MSG;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				return WebConstants.EXCPTION_MSG;
			}
		} 
		webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转入" + (edustatus == WebConstants.EDU_STATUS_1 ? "成功" : "失败"), edustatus);
		return WebConstants.EDU_STATUS_1 == edustatus ? Conts.EDU_SUCCESS : Conts.EDU_FAILURE ;
	}

	public ModelAndView mobileLogin(HttpServletRequest request,HttpServletResponse response) {
		String domain = this.getWebDomain(request) + "m/main";
		UserContext uc = getUserContext(request);
		try {
			WebUserFlat webUserFlat = webUserFlatService.getWebUserFlat(uc.getUserName());
			if (StringUtils.isEmpty(webUserFlat.getOgUserName())) {
				webUserFlat = OGIfcUtil.registOgAccout(webUserFlat);
				if (webUserFlat.getOgStatus() != null && webUserFlat.getOgStatus().intValue() == 1) {
					this.webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					sendErrorMsg(response,"【OG平台注册失败】",domain);
					return null;
				}
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put(OgConts.USERNAME, webUserFlat.getOgUserName());// 游戏帐号
			params.put(OgConts.PASSWORD, webUserFlat.getOgPassword());// 密码
			params.put(OgConts.GAMETYPE, "21");// 1pc  21mobile
			OgResResult result = OGIfcUtil.login(params);
			
			if (result != null) {
				return new ModelAndView("m/mg/mg").addObject("url", result.getUrl());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendErrorMsg(response,"【OG平台登陆失败】",domain);
		return null;
	}
	
	public boolean queryOrderStatus(String orderNum,String userName,String pwd,String opertype,String credit) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(OgConts.BILLNO, orderNum);// 订单号
		params.put(OgConts.USERNAME, userName);
		params.put(OgConts.PASSWORD, pwd);
		params.put(OgConts.OPERTYPE, opertype);
		params.put(OgConts.CREDIT, credit);
		OgResResult result = OGIfcUtil.queryOrderStatus(params);
		if(null != result && StringUtils.equals(result.getResultCode(), "1")){
			return true;
		}
		return false;
	}

	public void queryOrderStatus(HttpServletRequest request,HttpServletResponse response) {
		try {
			String orderNum = request.getParameter("orderNum");
			Map<String, String> params = new HashMap<String, String>();
			params.put(OgConts.BILLNO, orderNum);// 订单号
			params.put(OgConts.USERNAME, "a1nihaoma");
			params.put(OgConts.PASSWORD, "9cur88579y");
			params.put(OgConts.OPERTYPE, "IN");
			params.put(OgConts.CREDIT, "10");
			OgResResult result = OGIfcUtil.queryOrderStatus(params);
			ServletUtils.outPrintSuccess(request, response, result);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "异常");
		}
	}
}
