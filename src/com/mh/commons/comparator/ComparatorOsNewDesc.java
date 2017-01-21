package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebOsElectronic;

public class ComparatorOsNewDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebOsElectronic u1 = (WebOsElectronic) o1;
		WebOsElectronic u2 = (WebOsElectronic) o2;
		if (u1.getEleIsNew() < u2.getEleIsNew()) {
			return 1;
		} else {
			return 0;
		}
	}
}
