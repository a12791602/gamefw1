package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebAgElectronic;

public class ComparatorAgHotDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebAgElectronic u1 = (WebAgElectronic) o1;
		WebAgElectronic u2 = (WebAgElectronic) o2;
		if (u1.getEleHotNum() < u2.getEleHotNum()) {
			return 1;
		} else {
			return 0;
		}
	}
}
