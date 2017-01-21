package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebNewNtElectronic;

public class ComparatorNewNtHotDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebNewNtElectronic u1 = (WebNewNtElectronic) o1;
		WebNewNtElectronic u2 = (WebNewNtElectronic) o2;
		if (u1.getEleHotNum() < u2.getEleHotNum()) {
			return 1;
		} else {
			return 0;
		}
	}
}
