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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mh.client.BaseFlatInfo;
import com.mh.commons.cache.MemCachedUtil;
import com.mh.commons.conf.CommonConstant;
import com.mh.commons.conf.WebConstants;
import com.mh.commons.utils.SecurityEncode;
import com.mh.entity.WebEdu;
import com.mh.entity.WebUser;
import com.mh.entity.WebUserFlat;
import com.mh.ifc.NSBIfcUtil;
import com.mh.ifc.http.ComEduConts;
import com.mh.ifc.http.Conts;
import com.mh.ifc.http.NsbConts;
import com.mh.ifc.http.NsbResResult;
import com.mh.ifc.util.ComUtil;
import com.mh.ifc.util.DateUtil;
import com.mh.service.FlatInterfaceService;
import com.mh.web.login.UserContext;
/**
 * SB
 * @author Administrator
 *
 */
@Service("sbInterfaceServiceImpl")
public class SbInterfaceServiceImpl extends BaseFlatInfo implements FlatInterfaceService {
	
	public String login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String typeCode = request.getParameter("typeCode");
			String url = "";
			String _p = "";
			UserContext uc = getUserContext(request);
			if(uc==null){
				if("sportbook".equals(typeCode)){
					url = CommonConstant.resCommMap.get(CommonConstant.SB_SPORTBOOK_LOGIN_URL);
					url += "/vender.aspx?lang=cs";
			    }
				return url;
			}
			WebUserFlat webUserFlat = webUserFlatService.getWebUserFlat(uc.getUserName());

			if (StringUtils.isEmpty(webUserFlat.getSbUserName())) {
				webUserFlat = NSBIfcUtil.registSbAccout(webUserFlat);
				if (webUserFlat.getSbStatus() != null && webUserFlat.getSbStatus().intValue() == 1) {
					webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
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
					logger.info("沙巴加密参数:"+buff.toString());
					_p = SecurityEncode.encoderByDES(buff.toString(),SecurityEncode.key);
					logger.info("沙巴加密后参数:"+_p);
				} catch (Exception e) {
					e.printStackTrace();
				}
				url += "/spd/index/"+_p+"?lang=cs";
				return url;
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
		if (StringUtils.isEmpty(webUserFlat.getSbUserName())) {
			webUserFlat = NSBIfcUtil.registSbAccout(webUserFlat);
			if (webUserFlat.getSbStatus() != null && webUserFlat.getSbStatus().intValue() == 1) {
				webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
			}
		}
		//查余额
		params.put(NsbConts.PLAYERNAME, webUserFlat.getSbUserName());
		NsbResResult result = NSBIfcUtil.balance(params);
		if (result != null && NsbConts.RES_CODE_0.equals(result.getError_code())) {
			String data = result.getData();
			JSONArray array = JSONArray.parseArray(data);
			String _blance = array.getJSONObject(0).get("balance").toString();
			blance = Double.valueOf(_blance);
		}
	
		return blance;
	}

	public String updateEdu(WebUserFlat webUserFlat, WebUser webUser,String flatName, String optType, int points, WebEdu webEdu)  {
		String tsMsg = "";
		Integer eduForward = webEdu.getEduForward();
		String eduForwardRemark = webEdu.getEduForwardRemark();
		if(StringUtils.isEmpty(webUserFlat.getSbUserName())){
			 try {
				webUserFlat =  NSBIfcUtil.registSbAccout(webUserFlat);
				if(webUserFlat.getSbStatus()!= null && webUserFlat.getSbStatus().intValue()==1){
					 webUserFlatDao.update(webUserFlat);
					 tsMsg = updateSbAccountPonit(webUser, flatName, webUserFlat.getSbUserName(), optType, points, eduForward, eduForwardRemark);
				 }else{
					 tsMsg="额度转换失败，错误代码："+webUserFlat.getReturnCode();
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }else{
			 tsMsg = updateSbAccountPonit(webUser, flatName, webUserFlat.getSbUserName(), optType, points, eduForward, eduForwardRemark);
		 }
		return tsMsg;
	}
	
	private String updateSbAccountPonit(WebUser webUser,String flatName,String flatAccount,String optType ,int points,int eduForward, String eduForwardRemark) {
		String eduOrder = ComUtil.getOrder();// 订单编号
		Date gmt_4_date = DateUtil.getGMT_4_Date();// 美东时间
		String userflat = flatAccount.substring(0, 2);
		
		if (StringUtils.equalsIgnoreCase(optType, WebConstants.EDU_TYPE_2)){// 转入 （主帐号-》游戏平台）
			return sbDepositOpt(webUser,flatName,optType, flatAccount, points,eduForward, eduForwardRemark);
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
		eduObj.put(ComEduConts.EDUPOINTS, -points+"");
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
		NsbResResult result = null;
		try{
			//转出
			Map<String, String> params = new HashMap<String, String>();
			params.put(NsbConts.DIRECTION, NsbConts.Withdraw);
			params.put(NsbConts.PLAYERNAME,flatAccount);
			params.put(NsbConts.AMOUNT, String.valueOf(points));
			params.put(NsbConts.OPTRANSID, eduOrder);
			params.put(NsbConts.WEBFLAG, userflat);
			
			result = NSBIfcUtil.transferMoney(params);
			
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
			if(result!= null && NsbConts.RES_CODE_0.equals(result.getError_code())){//成功
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
	
	private String sbDepositOpt(WebUser webUser,String flatName,String optType, String flatAccount, int points, int eduForward, String eduForwardRemark) {
		
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
			NsbResResult result = null;
			
			Map<String, String> params = new HashMap<String, String>();
			params.put(NsbConts.DIRECTION, NsbConts.Deposit);
			params.put(NsbConts.PLAYERNAME,flatAccount);
			params.put(NsbConts.AMOUNT, String.valueOf(points));
			params.put(NsbConts.OPTRANSID, eduOrder);
			params.put(NsbConts.WEBFLAG, userflat);
			result = NSBIfcUtil.transferMoney(params);
			
			if (result!= null && NsbConts.RES_CODE_0.equals(result.getError_code())) {
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

		String domain = this.getWebDomain(request) + "m/main?code=sport";
		NsbResResult result = null;
		String url = CommonConstant.resCommMap.get(CommonConstant.SB_MOBILE_LOGIN_URL);
		url += "/Deposit_ProcessLogin.aspx?lang=cs";
		try{
			UserContext uc = this.getUserContext(request);
			WebUserFlat webUserFlat = this.webUserFlatService.getWebUserFlat(uc.getUserName());
			Map<String,String> p = new HashMap<String,String>();
			p.put(NsbConts.PLAYERNAME, webUserFlat.getSbUserName());
			NsbResResult r = NSBIfcUtil.balance(p);
			if (r!= null && NsbConts.RES_CODE_0.equals(r.getError_code())) {
				String data = r.getData();
				JSONArray array = JSONArray.parseArray(data);
				String _blance = array.getJSONObject(0).get("balance").toString();
				Double blance = Double.valueOf(_blance);
				if(blance < 1){
					sendErrorMsg(response,"【很遗憾！您的沙巴平台余额不足！请前往会员中心充值】",domain);
					return null;
				}
			}else{
				sendErrorMsg(response,"【沙巴平台余额查询失败】",domain);
				return null;
			}
			
			if (StringUtils.isEmpty(webUserFlat.getSbUserName())) {
				webUserFlat = NSBIfcUtil.registSbAccout(webUserFlat);
				if (webUserFlat.getSbStatus() != null && webUserFlat.getSbStatus().intValue() == 1) {
					this.webUserFlatService.saveOrUpdateWebUserFlat(webUserFlat);
				} else {
					sendErrorMsg(response,"【沙巴】平台注册失败",domain);
					return null;
				}
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put(NsbConts.PLAYERNAME, webUserFlat.getSbUserName());// 游戏帐号
			result = NSBIfcUtil.login(params);
			if (result == null || StringUtils.isBlank(result.getSessionToken())) {
				sendErrorMsg(response,"【沙巴】平台登录失败",domain);
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
			sendErrorMsg(response,"【沙巴】平台登录失败",domain);
		}
		url += "&st=" + result.getSessionToken();
		return new ModelAndView("web/frameUrl").addObject("backToUrl", url);
	}

	@Override
	public void queryOrderStatus(HttpServletRequest request,HttpServletResponse response) {
	}
}
