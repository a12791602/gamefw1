/**   
 * 文件名称: WebSitDataHelper.java<br/>
 * 版本号: V1.0<br/>   
 * 创建人: zoro<br/>  
 * 创建时间 : 2015-7-25 下午3:22:59<br/>
 */
package com.mh.web.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mh.commons.cache.MemCachedUtil;
import com.mh.commons.comparator.ComparatorAgHotDesc;
import com.mh.commons.comparator.ComparatorAgNewDesc;
import com.mh.commons.comparator.ComparatorAgSortIndexDesc;
import com.mh.commons.comparator.ComparatorBbinHotDesc;
import com.mh.commons.comparator.ComparatorBbinNewDesc;
import com.mh.commons.comparator.ComparatorBbinSortIndexDesc;
import com.mh.commons.comparator.ComparatorElectronicSortDesc;
import com.mh.commons.comparator.ComparatorGdHotDesc;
import com.mh.commons.comparator.ComparatorGdNewDesc;
import com.mh.commons.comparator.ComparatorGdSortIndexDesc;
import com.mh.commons.comparator.ComparatorMgHotDesc;
import com.mh.commons.comparator.ComparatorMgNewDesc;
import com.mh.commons.comparator.ComparatorMgSortIndexDesc;
import com.mh.commons.comparator.ComparatorNewNtHotDesc;
import com.mh.commons.comparator.ComparatorNewNtNewDesc;
import com.mh.commons.comparator.ComparatorNewNtSortIndexDesc;
import com.mh.commons.comparator.ComparatorOsHotDesc;
import com.mh.commons.comparator.ComparatorOsNewDesc;
import com.mh.commons.comparator.ComparatorOsSortIndexDesc;
import com.mh.commons.comparator.ComparatorPtHotDesc;
import com.mh.commons.comparator.ComparatorPtNewDesc;
import com.mh.commons.comparator.ComparatorPtSortIndexDesc;
import com.mh.commons.comparator.ComparatorTtgHotDesc;
import com.mh.commons.comparator.ComparatorTtgNewDesc;
import com.mh.commons.comparator.ComparatorTtgSortIndexDesc;
import com.mh.commons.conf.ResourceConstant;
import com.mh.commons.conf.WebConstants;
import com.mh.entity.TWebElectronicClass;
import com.mh.entity.WebAgElectronic;
import com.mh.entity.WebBbinElectronic;
import com.mh.entity.WebGdElectronic;
import com.mh.entity.WebMgElectronic;
import com.mh.entity.WebNewNtElectronic;
import com.mh.entity.WebOsElectronic;
import com.mh.entity.WebPtElectronic;
import com.mh.entity.WebTtgElectronic;
import com.mh.web.util.FastUtil;

/**
 * 类描述: TODO<br/>
 * 创建人: TODO zoro<br/>
 * 创建时间: 2015-7-25 下午3:22:59<br/>
 */
@SuppressWarnings("all")
public class WebResourceDataHelper {
	private static final Logger logger = LoggerFactory.getLogger(WebResourceDataHelper.class);

	public synchronized void initData() {
		logger.info("***********初始化平台游戏数据开始***********");
		try {
			getBaseData();
		} catch (Exception e) {
			logger.info("初始化平台游戏数据出错...");
			e.printStackTrace();
		}
		logger.info("***********初始化平台游戏数据结束***********");
	}

	/**
	 * 初始化 方法描述: TODO</br> void
	 */
	public synchronized void getBaseData() {
		/** PC端 **/
		getMgElectronicDataList();// MG
		getTtgElectronicDataList();// TTG
		getPtElectronicDataList();// PT
		getOsElectronicDataList();// OS
		getNewNtElectronicDataList();// NT
		getGdElectronicDataList();// GD
		getAgElectronicDataList();// AG
		getBbinElectronicDataList();// BBIN

		/** 手机端 **/
		getMobileMgElectronicDataList();// MG
		getMobilePtElectronicDataList();// PT
		getMobileOsElectronicDataList();// OS
		getMobileTtgElectronicDataList();// TTG
		getMobileNewNtElectronicDataList();// NT

		/** 平台游戏分类 **/
		getElectronicClassList();
	}

	/**
	 * 平台游戏分类
	 */
	private void getElectronicClassList() {
		ResourceConstant.MG_ELECTRONIC_CLASS.clear();
		ResourceConstant.OS_ELECTRONIC_CLASS.clear();
		ResourceConstant.PT_ELECTRONIC_CLASS.clear();
		ResourceConstant.NEWNT_ELECTRONIC_CLASS.clear();
		ResourceConstant.TTG_ELECTRONIC_CLASS.clear();
		ResourceConstant.GD_ELECTRONIC_CLASS.clear();
		ResourceConstant.AG_ELECTRONIC_CLASS.clear();
		ResourceConstant.BBIN_ELECTRONIC_CLASS.clear();
		String electronicClass = (String) MemCachedUtil.get("electronicClass");// 平台分类
		List<TWebElectronicClass> list = (List<TWebElectronicClass>) FastUtil.parseArray(electronicClass, TWebElectronicClass.class);
		for (TWebElectronicClass ele : list) {
			if (ele.getEleStatus().intValue() == 1 && !StringUtils.equals("Mobile", ele.getEleType())) {
				if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_MG)) {
					ResourceConstant.MG_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_OS)) {
					ResourceConstant.OS_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_PT)) {
					ResourceConstant.PT_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_NEWNT)) {
					ResourceConstant.NEWNT_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_TTG)) {
					ResourceConstant.TTG_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_GD)) {
					ResourceConstant.GD_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_AG)) {
					ResourceConstant.AG_ELECTRONIC_CLASS.add(ele);
				} else if (StringUtils.equals(ele.getEleFlat(), WebConstants.FLAT_NAME_BBIN)) {
					ResourceConstant.BBIN_ELECTRONIC_CLASS.add(ele);
				}
			}
		}
		Collections.sort(ResourceConstant.MG_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.OS_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.PT_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.NEWNT_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.TTG_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.GD_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.AG_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		Collections.sort(ResourceConstant.BBIN_ELECTRONIC_CLASS, new ComparatorElectronicSortDesc());
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_MG, ResourceConstant.MG_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_OS, ResourceConstant.OS_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_PT, ResourceConstant.PT_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_NEWNT, ResourceConstant.NEWNT_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_TTG, ResourceConstant.TTG_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_GD, ResourceConstant.GD_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_AG, ResourceConstant.AG_ELECTRONIC_CLASS);
		ResourceConstant.ELECTRONIC_CLASS_MAP.put(WebConstants.FLAT_NAME_BBIN, ResourceConstant.BBIN_ELECTRONIC_CLASS);
	}

	/**
	 * NT手机H5游戏
	 * 
	 * @return
	 */
	public void getMobileNewNtElectronicDataList() {
		ResourceConstant.MOBILE_NEWNT_ELECTRONIC_NEW.clear();
		ResourceConstant.MOBILE_NEWNT_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("newNt");
		if (StringUtils.isNotBlank(json)) {
			List<WebNewNtElectronic> allGameList = (List<WebNewNtElectronic>) FastUtil.parseArray(json, WebNewNtElectronic.class);
			List<WebNewNtElectronic> showGameList = new ArrayList<WebNewNtElectronic>();
			for (WebNewNtElectronic nt : allGameList) {
				if (nt.getStatus().intValue() == 1 && "Mobile".equalsIgnoreCase(nt.getEleGameType1())) {
					showGameList.add(nt);
					if (null != nt.getEleIsNew() && nt.getEleIsNew().intValue() > 0) {
						ResourceConstant.MOBILE_NEWNT_ELECTRONIC_NEW.add(nt);
					}
					if (null != nt.getEleHotNum() && nt.getEleHotNum() > 0) {
						ResourceConstant.MOBILE_NEWNT_ELECTRONIC_HOT.add(nt);
					}
				}
			}
			Collections.sort(showGameList, new ComparatorNewNtSortIndexDesc());
			ResourceConstant.MOBILE_NEWNT_ELECTRONIC_LIST = showGameList;
		}
	}

	public void getMobileMgElectronicDataList() {
		ResourceConstant.MOBILE_MG_ELECTRONIC_NEW.clear();
		ResourceConstant.MOBILE_MG_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("mg");
		if (StringUtils.isNotBlank(json)) {
			List<WebMgElectronic> allGameList = (List<WebMgElectronic>) FastUtil.parseArray(json, WebMgElectronic.class);
			List<WebMgElectronic> showGameList = new ArrayList<WebMgElectronic>();
			for (WebMgElectronic mg : allGameList) {
				if (mg.getStatus().intValue() == 1 && "HTML5".equals(mg.getEleGameName())) {
					if (null != mg.getEleIsNew() && mg.getEleIsNew().intValue() > 0) {// 新款游戏
						ResourceConstant.MOBILE_MG_ELECTRONIC_NEW.add(mg);
					}
					if (null != mg.getEleHotNum() && mg.getEleHotNum() > 0) {// 热门游戏
						ResourceConstant.MOBILE_MG_ELECTRONIC_HOT.add(mg);
					}
					showGameList.add(mg);
				}
			}
			Collections.sort(showGameList, new ComparatorMgSortIndexDesc());
			ResourceConstant.MOBILE_MG_ELECTRONIC_LIST = showGameList;
		}
	}

	/**
	 * pt
	 * 
	 * @return
	 */
	public void getMobilePtElectronicDataList() {
		ResourceConstant.MOBILE_PT_ELECTRONIC_NEW.clear();
		ResourceConstant.MOBILE_PT_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("mobilePt");
		if (StringUtils.isNotBlank(json)) {
			List<WebPtElectronic> allGameList = (List<WebPtElectronic>) FastUtil.parseArray(json, WebPtElectronic.class);
			List<WebPtElectronic> showGameList = new ArrayList<WebPtElectronic>();
			for (WebPtElectronic pt : allGameList) {
				if (pt.getStatus().intValue() == 1 && pt.getPlatform().intValue() == 1) {
					showGameList.add(pt);
					if (null != pt.getEleIsNew() && pt.getEleIsNew().intValue() > 0) {
						ResourceConstant.MOBILE_PT_ELECTRONIC_NEW.add(pt);
					}
					if (null != pt.getEleHotNum() && pt.getEleHotNum() > 0) {
						ResourceConstant.MOBILE_PT_ELECTRONIC_HOT.add(pt);
					}
				}
			}
			Collections.sort(showGameList, new ComparatorPtSortIndexDesc());
			ResourceConstant.MOBILE_PT_ELECTRONIC_LIST = showGameList;
		}
	}

	public void getMobileOsElectronicDataList() {
		ResourceConstant.MOBILE_OS_ELECTRONIC_HOT.clear();
		ResourceConstant.MOBILE_OS_ELECTRONIC_NEW.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("os");
		if (StringUtils.isNotBlank(json)) {
			List<WebOsElectronic> allGameList = (List<WebOsElectronic>) FastUtil.parseArray(json, WebOsElectronic.class);
			List<WebOsElectronic> showGameList = new ArrayList<WebOsElectronic>();
			List<String> h5Game = new ArrayList<String>();
			for (WebOsElectronic os : allGameList) {
				if (null != os.getEleGameType2() && StringUtils.equals(os.getEleGameType2(), "1")) {// 1 特殊游戏
					h5Game.add(os.getEleGameId());
				}
				if (os.getStatus().intValue() == 1 && ("Mobile".equals(os.getEleGameName()))) {
					showGameList.add(os);
					if (null != os.getEleIsNew() && os.getEleIsNew().intValue() > 0) {
						ResourceConstant.MOBILE_OS_ELECTRONIC_NEW.add(os);
					}
					if (null != os.getEleHotNum() && os.getEleHotNum() > 0) {
						ResourceConstant.MOBILE_OS_ELECTRONIC_HOT.add(os);
					}
				}
			}
			ComparatorOsSortIndexDesc comparator = new ComparatorOsSortIndexDesc();
			Collections.sort(showGameList, comparator);
			ResourceConstant.OS_ELECTRONIC_H5GAME = h5Game;// h5特殊游戏
			ResourceConstant.MOBILE_OS_ELECTRONIC_LIST = showGameList;
		}
	}

	/**
	 * MG游戏列表
	 */
	public void getMgElectronicDataList() {
		ResourceConstant.MG_ELECTRONIC_NEW.clear();
		ResourceConstant.MG_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("mg");
		if (StringUtils.isNotBlank(json)) {
			List<WebMgElectronic> allGameList = (List<WebMgElectronic>) FastUtil.parseArray(json, WebMgElectronic.class);// 所有游戏列表
			List<WebMgElectronic> showGameList = new ArrayList<WebMgElectronic>();
			for (WebMgElectronic mg : allGameList) {
				if (mg.getStatus().intValue() == 1 && !("HTML5".equals(mg.getEleGameName()))) {
					showGameList.add(mg);
				}
			}
			ComparatorMgSortIndexDesc sort = new ComparatorMgSortIndexDesc();
			Collections.sort(showGameList, sort);

			Map<String, List<WebMgElectronic>> allGameType = new HashMap<String, List<WebMgElectronic>>();
			for (WebMgElectronic mg : showGameList) {
				String gameType = mg.getEleGameType1();
				List<WebMgElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebMgElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				if (null != mg.getEleIsNew() && mg.getEleIsNew().intValue() > 0) {// 新款游戏
					ResourceConstant.MG_ELECTRONIC_NEW.add(mg);
				}
				if (null != mg.getEleHotNum() && mg.getEleHotNum() > 0) {// 热门游戏
					ResourceConstant.MG_ELECTRONIC_HOT.add(mg);
				}
				list.add(mg);
				allGameType.put(gameType, list);
			}

			allGameType.put("all", showGameList);
			ResourceConstant.MG_ELECTRONIC_LIST = allGameType;
			Collections.sort(ResourceConstant.MG_ELECTRONIC_HOT, new ComparatorMgHotDesc());
			Collections.sort(ResourceConstant.MG_ELECTRONIC_NEW, new ComparatorMgNewDesc());
		}
	}

	/**
	 * TTG游戏列表
	 */
	public void getTtgElectronicDataList() {
		ResourceConstant.TTG_ELECTRONIC_NEW.clear();
		ResourceConstant.TTG_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("ttg");
		if (StringUtils.isNotBlank(json)) {
			List<WebTtgElectronic> allGameList = (List<WebTtgElectronic>) FastUtil.parseArray(json, WebTtgElectronic.class);
			List<WebTtgElectronic> showGameList = new ArrayList<WebTtgElectronic>();
			for (WebTtgElectronic ttg : allGameList) {
				if (ttg.getStatus().intValue() == 1 && !("Mobile".equals(ttg.getEleGameType1()))) {
					showGameList.add(ttg);
				}
			}

			ComparatorTtgSortIndexDesc comparator = new ComparatorTtgSortIndexDesc();
			Collections.sort(showGameList, comparator);

			Map<String, List<WebTtgElectronic>> allGameType = new HashMap<String, List<WebTtgElectronic>>();
			for (WebTtgElectronic ttg : showGameList) {
				String gameType = ttg.getEleGameType1();
				List<WebTtgElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebTtgElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				if (null != ttg.getEleIsNew() && ttg.getEleIsNew().intValue() > 0) {
					ResourceConstant.TTG_ELECTRONIC_NEW.add(ttg);
				}
				if (null != ttg.getEleHotNum() && ttg.getEleHotNum() > 0) {
					ResourceConstant.TTG_ELECTRONIC_HOT.add(ttg);
				}
				list.add(ttg);
				allGameType.put(gameType, list);
			}
			allGameType.put("all", showGameList);
			ResourceConstant.TTG_ELECTRONIC_LIST = allGameType;
			Collections.sort(ResourceConstant.TTG_ELECTRONIC_HOT, new ComparatorTtgHotDesc());
			Collections.sort(ResourceConstant.TTG_ELECTRONIC_NEW, new ComparatorTtgNewDesc());
		}
	}

	public void getOsElectronicDataList() {
		ResourceConstant.OS_ELECTRONIC_NEW.clear();
		ResourceConstant.OS_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("os");
		if (StringUtils.isNotBlank(json)) {
			List<WebOsElectronic> allGameList = (List<WebOsElectronic>) FastUtil.parseArray(json, WebOsElectronic.class);
			List<WebOsElectronic> showGameList = new ArrayList<WebOsElectronic>();
			for (WebOsElectronic os : allGameList) {
				if (os.getStatus().intValue() == 1 && "Casino".equals(os.getEleGameName())) {
					showGameList.add(os);
				}
			}

			Collections.sort(showGameList, new ComparatorOsSortIndexDesc());

			Map<String, List<WebOsElectronic>> allGameType = new HashMap<String, List<WebOsElectronic>>();
			for (WebOsElectronic os : showGameList) {
				String gameType = os.getEleGameType1();
				List<WebOsElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebOsElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				if (null != os.getEleIsNew() && os.getEleIsNew().intValue() > 0) {
					ResourceConstant.OS_ELECTRONIC_NEW.add(os);
				}
				if (null != os.getEleHotNum() && os.getEleHotNum() > 0) {
					ResourceConstant.OS_ELECTRONIC_HOT.add(os);
				}

				list.add(os);
				allGameType.put(gameType, list);
			}
			allGameType.put("all", showGameList);
			ResourceConstant.OS_ELECTRONIC_LIST = allGameType;
			Collections.sort(ResourceConstant.OS_ELECTRONIC_HOT, new ComparatorOsHotDesc());
			Collections.sort(ResourceConstant.OS_ELECTRONIC_NEW, new ComparatorOsNewDesc());
		}
	}

	/**
	 * GD游戏
	 */
	public void getGdElectronicDataList() {
		ResourceConstant.GD_ELECTRONIC_NEW.clear();
		ResourceConstant.GD_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("gd");
		if (StringUtils.isNotBlank(json)) {
			List<WebGdElectronic> allGameList = (List<WebGdElectronic>) FastUtil.parseArray(json, WebGdElectronic.class);
			List<WebGdElectronic> showGameList = new ArrayList<WebGdElectronic>();
			for (WebGdElectronic gd : allGameList) {
				if (gd.getStatus().intValue() == 1) {
					showGameList.add(gd);
				}
			}
			ComparatorGdSortIndexDesc comparator = new ComparatorGdSortIndexDesc();
			Collections.sort(showGameList, comparator);

			Map<String, List<WebGdElectronic>> allGameType = new HashMap<String, List<WebGdElectronic>>();
			for (WebGdElectronic gd : showGameList) {
				String gameType = gd.getEleGameType1();
				List<WebGdElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebGdElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				list.add(gd);
				allGameType.put(gameType, list);
				if (null != gd.getEleIsNew() && gd.getEleIsNew().intValue() > 0) {
					ResourceConstant.GD_ELECTRONIC_NEW.add(gd);
				}
				if (null != gd.getEleHotNum() && gd.getEleHotNum() > 0) {
					ResourceConstant.GD_ELECTRONIC_HOT.add(gd);
				}
			}
			allGameType.put("all", showGameList);
			ResourceConstant.GD_ELECTRONIC_LIST = allGameType;

			Collections.sort(ResourceConstant.GD_ELECTRONIC_HOT, new ComparatorGdHotDesc());
			Collections.sort(ResourceConstant.GD_ELECTRONIC_NEW, new ComparatorGdNewDesc());
		}
	}

	/**
	 * AG游戏
	 */
	public void getAgElectronicDataList() {
		ResourceConstant.AG_ELECTRONIC_NEW.clear();
		ResourceConstant.AG_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("ag");
		if (StringUtils.isNotBlank(json)) {
			List<WebAgElectronic> allGameList = (List<WebAgElectronic>) FastUtil.parseArray(json, WebAgElectronic.class);
			List<WebAgElectronic> showGameList = new ArrayList<WebAgElectronic>();
			for (WebAgElectronic ag : allGameList) {
				if (ag.getStatus().intValue() == 1) {
					showGameList.add(ag);
				}
			}
			ComparatorAgSortIndexDesc comparator = new ComparatorAgSortIndexDesc();
			Collections.sort(showGameList, comparator);

			Map<String, List<WebAgElectronic>> allGameType = new HashMap<String, List<WebAgElectronic>>();
			for (WebAgElectronic ag : showGameList) {
				String gameType = ag.getEleGameType1();
				List<WebAgElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebAgElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				if (null != ag.getEleIsNew() && ag.getEleIsNew().intValue() > 0) {
					ResourceConstant.AG_ELECTRONIC_NEW.add(ag);
				}
				if (null != ag.getEleHotNum() && ag.getEleHotNum() > 0) {
					ResourceConstant.AG_ELECTRONIC_HOT.add(ag);
				}
				list.add(ag);
				allGameType.put(gameType, list);
			}
			allGameType.put("all", showGameList);
			ResourceConstant.AG_ELECTRONIC_LIST = allGameType;

			Collections.sort(ResourceConstant.AG_ELECTRONIC_HOT, new ComparatorAgHotDesc());
			Collections.sort(ResourceConstant.AG_ELECTRONIC_NEW, new ComparatorAgNewDesc());
		}
	}

	/**
	 * BBIN游戏
	 */
	public void getBbinElectronicDataList() {
		ResourceConstant.BBIN_ELECTRONIC_NEW.clear();
		ResourceConstant.BBIN_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("bbin");
		if (StringUtils.isNotBlank(json)) {
			List<WebBbinElectronic> allGameList = (List<WebBbinElectronic>) FastUtil.parseArray(json, WebBbinElectronic.class);
			List<WebBbinElectronic> showGameList = new ArrayList<WebBbinElectronic>();
			for (WebBbinElectronic bbin : allGameList) {
				if (bbin.getStatus().intValue() == 1) {
					showGameList.add(bbin);
				}
			}
			Collections.sort(showGameList, new ComparatorBbinSortIndexDesc());

			Map<String, List<WebBbinElectronic>> allGameType = new HashMap<String, List<WebBbinElectronic>>();
			for (WebBbinElectronic bbin : showGameList) {
				String gameType = bbin.getEleGameType1();
				List<WebBbinElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebBbinElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				if (null != bbin.getEleIsNew() && bbin.getEleIsNew().intValue() > 0) {
					ResourceConstant.BBIN_ELECTRONIC_NEW.add(bbin);
				}
				if (null != bbin.getEleHotNum() && bbin.getEleHotNum() > 0) {
					ResourceConstant.BBIN_ELECTRONIC_HOT.add(bbin);
				}
				list.add(bbin);
				allGameType.put(gameType, list);
			}
			allGameType.put("all", showGameList);
			ResourceConstant.BBIN_ELECTRONIC_LIST = allGameType;

			Collections.sort(ResourceConstant.BBIN_ELECTRONIC_HOT, new ComparatorBbinHotDesc());
			Collections.sort(ResourceConstant.BBIN_ELECTRONIC_NEW, new ComparatorBbinNewDesc());
		}
	}

	public void getNewNtElectronicDataList() {
		ResourceConstant.NEWNT_ELECTRONIC_NEW.clear();
		ResourceConstant.NEWNT_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("newNt");
		if (StringUtils.isNotBlank(json)) {
			List<WebNewNtElectronic> allGameList = (List<WebNewNtElectronic>) FastUtil.parseArray(json, WebNewNtElectronic.class);
			List<WebNewNtElectronic> showGameList = new ArrayList<WebNewNtElectronic>();
			for (WebNewNtElectronic nt : allGameList) {
				if (nt.getStatus().intValue() == 1 && !"Mobile".equalsIgnoreCase(nt.getEleGameType1())) {
					showGameList.add(nt);
				}
			}

			ComparatorNewNtSortIndexDesc sort = new ComparatorNewNtSortIndexDesc();
			Collections.sort(showGameList, sort);

			Map<String, List<WebNewNtElectronic>> allGameType = new HashMap<String, List<WebNewNtElectronic>>();
			for (WebNewNtElectronic nt : showGameList) {
				String gameType = nt.getEleGameType1();
				List<WebNewNtElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebNewNtElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				list.add(nt);
				allGameType.put(gameType, list);
				if (null != nt.getEleIsNew() && nt.getEleIsNew().intValue() > 0) {
					ResourceConstant.NEWNT_ELECTRONIC_NEW.add(nt);
				}
				if (null != nt.getEleHotNum() && nt.getEleHotNum() > 0) {
					ResourceConstant.NEWNT_ELECTRONIC_HOT.add(nt);
				}
			}
			allGameType.put("all", showGameList);
			ResourceConstant.NEWNT_ELECTRONIC_LIST = allGameType;

			Collections.sort(ResourceConstant.NEWNT_ELECTRONIC_HOT, new ComparatorNewNtHotDesc());
			Collections.sort(ResourceConstant.NEWNT_ELECTRONIC_NEW, new ComparatorNewNtNewDesc());
		}
	}

	public void getPtElectronicDataList() {
		ResourceConstant.PT_ELECTRONIC_NEW.clear();
		ResourceConstant.PT_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("pt");
		if (StringUtils.isNotBlank(json)) {
			List<WebPtElectronic> allGameList = (List<WebPtElectronic>) FastUtil.parseArray(json, WebPtElectronic.class);
			List<WebPtElectronic> showGameList = new ArrayList<WebPtElectronic>();
			for (WebPtElectronic pt : allGameList) {
				if (pt.getStatus().intValue() == 1 && pt.getPlatform().intValue() == 0) {
					showGameList.add(pt);
				}
			}
			Collections.sort(showGameList, new ComparatorPtSortIndexDesc());

			Map<String, String> ptCodeMap = new HashMap<String, String>();
			Map<String, List<WebPtElectronic>> allGameType = new HashMap<String, List<WebPtElectronic>>();
			for (WebPtElectronic pt : showGameList) {
				ptCodeMap.put(pt.getEleGameIndex(), pt.getEleGameCnname());
				String gameType = pt.getEleGameType1();
				List<WebPtElectronic> list = null;
				if (!allGameType.containsKey(gameType)) {
					list = new ArrayList<WebPtElectronic>();
				} else {
					list = allGameType.get(gameType);
				}
				if (null != pt.getEleIsNew() && pt.getEleIsNew().intValue() > 0) {
					ResourceConstant.PT_ELECTRONIC_NEW.add(pt);
				}
				if (null != pt.getEleHotNum() && pt.getEleHotNum() > 0) {
					ResourceConstant.PT_ELECTRONIC_HOT.add(pt);
				}
				list.add(pt);
				allGameType.put(gameType, list);
			}
			allGameType.put("all", showGameList);
			allGameType.put("support", this.getPtElectronicDataListBySupport(1));

			ResourceConstant.PT_ELECTRONIC_LIST = allGameType;
			ResourceConstant.PT_ELECTRONIC = ptCodeMap;

			Collections.sort(ResourceConstant.PT_ELECTRONIC_HOT, new ComparatorPtHotDesc());
			Collections.sort(ResourceConstant.PT_ELECTRONIC_NEW, new ComparatorPtNewDesc());
		}
	}

	public List<WebPtElectronic> getPtElectronicDataListBySupport(int isSupport) {
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("pt");
		List<WebPtElectronic> showGameList = new ArrayList<WebPtElectronic>();
		if (StringUtils.isNotBlank(json)) {
			List<WebPtElectronic> allGameList = (List<WebPtElectronic>) FastUtil.parseArray(json, WebPtElectronic.class);
			for (WebPtElectronic pt : allGameList) {
				if (pt.getIsSupport() != null && pt.getStatus().intValue() == 1 && pt.getPlatform().intValue() == 0 && pt.getIsSupport().intValue() == isSupport) {
					showGameList.add(pt);
				}
			}
			Collections.sort(showGameList, new ComparatorPtSortIndexDesc());
		}
		return showGameList;
	}

	public void getMobileTtgElectronicDataList() {
		ResourceConstant.MOBILE_TTG_ELECTRONIC_NEW.clear();
		ResourceConstant.MOBILE_TTG_ELECTRONIC_HOT.clear();
		Map<String, String> map = (Map<String, String>) MemCachedUtil.get("ybslot_result");
		String json = map.get("mobileTtg");
		if (StringUtils.isNotBlank(json)) {
			List<WebTtgElectronic> allGameList = (List<WebTtgElectronic>) FastUtil.parseArray(json, WebTtgElectronic.class);
			List<WebTtgElectronic> showGameList = new ArrayList<WebTtgElectronic>();
			for (WebTtgElectronic ttg : allGameList) {
				if (ttg.getStatus().intValue() == 1 && "Mobile".equals(ttg.getEleGameType1())) {
					showGameList.add(ttg);
					if (null != ttg.getEleIsNew() && ttg.getEleIsNew().intValue() > 0) {
						ResourceConstant.MOBILE_TTG_ELECTRONIC_NEW.add(ttg);
					}
					if (null != ttg.getEleHotNum() && ttg.getEleHotNum() > 0) {
						ResourceConstant.MOBILE_TTG_ELECTRONIC_HOT.add(ttg);
					}
				}
			}
			Collections.sort(showGameList, new ComparatorTtgSortIndexDesc());
			ResourceConstant.MOBILE_TTG_ELECTRONIC_LIST = showGameList;
		}
	}
}
