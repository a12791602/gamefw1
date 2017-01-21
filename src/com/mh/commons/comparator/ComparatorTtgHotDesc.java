package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebTtgElectronic;

public class ComparatorTtgHotDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebTtgElectronic u1 = (WebTtgElectronic) o1;
		WebTtgElectronic u2 = (WebTtgElectronic) o2;
		if (u1.getEleHotNum() < u2.getEleHotNum()) {
			return 1;
		} else {
			return 0;
		}
	}
}
