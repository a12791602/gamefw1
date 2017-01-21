package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebPtElectronic;

/**
 * 整形降序
 * 
 * @author Administrator
 * 
 */
public class ComparatorPtHotDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebPtElectronic u1 = (WebPtElectronic) o1;
		WebPtElectronic u2 = (WebPtElectronic) o2;
		if (u1.getEleHotNum() < u2.getEleHotNum()) {
			return 1;
		} else {
			return 0;
		}
	}

}
