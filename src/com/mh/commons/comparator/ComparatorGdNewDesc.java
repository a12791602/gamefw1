package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebGdElectronic;

public class ComparatorGdNewDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebGdElectronic u1 = (WebGdElectronic) o1;
		WebGdElectronic u2 = (WebGdElectronic) o2;
		if (u1.getEleIsNew() < u2.getEleIsNew()) {
			return 1;
		} else {
			return 0;
		}
	}
}
