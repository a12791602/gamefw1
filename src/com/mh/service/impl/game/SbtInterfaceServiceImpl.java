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
import com.mh.ifc.SBTIfcUtil;
import com.mh.ifc.http.ComEduConts;
import com.mh.ifc.http.Conts;
import com.mh.ifc.http.SbtApiConstants;
import com.mh.ifc.http.SbtResBean;
import com.mh.ifc.util.ComUtil;
import com.mh.ifc.util.DateUtil;
import com.mh.service.FlatInterfaceService;
import com.mh.web.login.UserContext;
/**
 * SBT
 * @author Administrator
 *
 */
@Service("sbtInterfaceServiceImpl")
public class SbtInterfaceServiceImpl extends BaseFlatInfo implements FlatInterfaceService {
	
	public String login(HttpServletRequest request, HttpServletResponse response) {
		try {

			UserContext uc = getUserContext(request);
			if(null == uc){
				Map<String, String> params = new HashMap<String, String>();
				params.put(SbtApiConstants.USER_ID, "");// 游戏帐号
				SbtResBean result = SBTIfcUtil.login(params);
				if (result != null) {
					logger.info("返回的url:" + result.getLoginUrl());
					return result.getLoginUrl();
				}
			}
			WebUserFlat webUserFlat = webUserFlatService.getWebUserFlat(uc.getUserName());
			if (StringUtils.isEmpty(webUserFlat.getSbtUserName())) {
				webUserFlat = SBTIfcUtil.registSbtAccout(webUserFlat);
				if (webUserFlat.getSbtStatus() != null && webUserFlat.getSbtStatus().intValue() == 1) {
					webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					return "";
				}
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put(SbtApiConstants.USER_ID, webUserFlat.getSbtUserName());// 游戏帐号
			SbtResBean result = SBTIfcUtil.login(params);
			if (result != null) {
				logger.info("返回的url:" + result.getLoginUrl());
				return result.getLoginUrl();
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
		if (StringUtils.isEmpty(webUserFlat.getSbtUserName())) {
			webUserFlat = SBTIfcUtil.registSbtAccout(webUserFlat);
			if (webUserFlat.getSbtStatus() != null && webUserFlat.getSbtStatus().intValue() == 1) {
				webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
			}
		}
		//查余额
		params.put(SbtApiConstants.USER_ID, webUserFlat.getSbtUserName());
		SbtResBean result = SBTIfcUtil.balance(params);
		if(result!=null && SbtApiConstants.RESP_SUCCESS_CODE.equals(result.getErrorCode())){
			blance = Double.valueOf(result.getBalance());
		}
		return blance;
	}

	public String updateEdu(WebUserFlat webUserFlat, WebUser webUser,String flatName, String optType, int points, WebEdu webEdu)  {
		String tsMsg = "";
		Integer eduForward = webEdu.getEduForward();
		String eduForwardRemark = webEdu.getEduForwardRemark();
		if(StringUtils.isEmpty(webUserFlat.getSbtUserName())){
			 try {
				webUserFlat = SBTIfcUtil.registSbtAccout(webUserFlat);
				if(webUserFlat.getSbtStatus()!= null && webUserFlat.getSbtStatus().intValue()==1){
					 webUserFlatDao.update(webUserFlat);
					 tsMsg = updateSbtAccountPonit(webUser, flatName, webUserFlat.getSbtUserName(), optType, points, eduForward, eduForwardRemark);
				 }else{
					 tsMsg="额度转换失败，错误代码："+webUserFlat.getReturnCode();
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }else{
			 tsMsg = updateSbtAccountPonit(webUser, flatName, webUserFlat.getSbtUserName(), optType, points, eduForward, eduForwardRemark);
		 }
		return tsMsg;
	}
	
	private String updateSbtAccountPonit(WebUser webUser,String flatName,String flatAccount,String optType,int points,int eduForward, String eduForwardRemark) {
		String eduOrder = ComUtil.getOrder();// 订单编号
		Date gmt_4_date = DateUtil.getGMT_4_Date();// 美东时间
		String userflat = flatAccount.substring(0, 2);
		
		if (StringUtils.equalsIgnoreCase(optType, WebConstants.EDU_TYPE_2)){// 转入 （主帐号-》游戏平台）
			return SbtDepositOpt(webUser,flatName,optType, flatAccount, points,eduForward, eduForwardRemark);
		}
		
		//先添加额度记录
		try {
			addWebEduRecord(webUser,WebConstants.EDU_STATUS_INIT, eduOrder, -points, eduForward, eduForwardRemark, gmt_4_date, flatName);
		} catch (Exception e2) {
			return "额度记录添加失败!";
		}
		
		/**请求API 创建EDU记录**/
		JSONObject eduObj = new JSONObject();
		eduObj.put(ComEduConts.EDUORDER, eduOrder);
		eduObj.put(ComEduConts.USERNAME, webUser.getUserName());
		eduObj.put(ComEduConts.EDUPOINTS, points+"");
		eduObj.put(ComEduConts.EDUTYPE, WebConstants.EDU_TYPE_1);
		eduObj.put(ComEduConts.EDUSTATUS, "-1");
		eduObj.put(ComEduConts.EDUIP, "0.0.0.0");
		eduObj.put(ComEduConts.EDUFORWARD, eduForward+"");
		eduObj.put(ComEduConts.EDUFORWARDREMARK, eduForwardRemark);
		eduObj.put(ComEduConts.WEBFLAG, userflat);
		eduObj.put(ComEduConts.FLATNAME,flatName);
		eduObj.put(ComEduConts.FLATUSERNAME,flatAccount);
		
		Map<String,String> paramMap= new HashMap<String,String>();
		paramMap.put("params", eduObj.toString());
		boolean eduResult = optInterfaceEdu(paramMap);
		if(!eduResult){
			//超端添加失败,订单状态改为失败
			webEduDao.updateEduRecord(webUser.getUserName(), eduOrder, eduForwardRemark+"，转出失败" , WebConstants.EDU_STATUS_0);
			return Conts.EDU_FAILURE;
		}
		
		//转出（游戏平-》台主帐号）
		SbtResBean result = null;
		try{
			//转出
			Map<String, String> params = new HashMap<String, String>();
			params.put(SbtApiConstants.USER_ID,flatAccount);
			params.put(SbtApiConstants.BALANCE, String.valueOf(points));
			params.put(SbtApiConstants.TRANSACTION, eduOrder);
			params.put(SbtApiConstants.WEBFLAG, userflat);
			result = SBTIfcUtil.withdrawal(params);
			
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
			boolean queryStatus = this.queryOrderStatus(eduOrder);
			if(result!= null && SbtApiConstants.RESP_SUCCESS_CODE.equals(result.getErrorCode())){//成功
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
				boolean queryStatus = this.queryOrderStatus(eduOrder);//这里查询到订单就是异常单
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
	
	private String SbtDepositOpt(WebUser webUser,String flatName,String optType, String flatAccount, int points, int eduForward, String eduForwardRemark) {
		
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
		jsonObj.put(ComEduConts.EDUTYPE,  WebConstants.EDU_TYPE_2);
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
		try {
			//财务记录
			addAccountRecord(webUser, flatName, optType, eduPoints, eduOrder, eduForwardRemark, gmt_4_date);
			/** 平台帐号添加额度 **/
			SbtResBean result = null;
			Map<String, String> params = new HashMap<String, String>();
			params.put(SbtApiConstants.USER_ID,flatAccount);
			params.put(SbtApiConstants.BALANCE, String.valueOf(points));
			params.put(SbtApiConstants.TRANSACTION, eduOrder);
			params.put(SbtApiConstants.WEBFLAG, userflat);
			result = SBTIfcUtil.deposit(params);
			
			boolean queryStatus = this.queryOrderStatus(eduOrder);
			if (result!= null && SbtApiConstants.RESP_SUCCESS_CODE.equals(result.getErrorCode())) {
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
				boolean queryStatus = this.queryOrderStatus(eduOrder);
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

	public ModelAndView mobileLogin(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean queryOrderStatus(String orderNum) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put(SbtApiConstants.TRANSACTION, orderNum);// 订单号
		SbtResBean result = SBTIfcUtil.queryOrderStatus(params);
		if(null != result && StringUtils.equals(result.getErrorCode(), "0")){
			return true;
		}
		return false;
	}

	public void queryOrderStatus(HttpServletRequest request,HttpServletResponse response) {
		try {
			String orderNum = request.getParameter("orderNum");
			Map<String, String> params = new HashMap<String, String>();
			params.put(SbtApiConstants.TRANSACTION, orderNum);// 订单号
			SbtResBean result = SBTIfcUtil.queryOrderStatus(params);
			ServletUtils.outPrintSuccess(request, response, result);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "异常");
		}
	}
}
