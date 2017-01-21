package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebGdElectronic;

public class ComparatorGdSortIndexDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebGdElectronic u1 = (WebGdElectronic) o1;
		WebGdElectronic u2 = (WebGdElectronic) o2;
		if (u1.getEleSortIndex() < u2.getEleSortIndex()) {
			return 1;
		} else {
			return 0;
		}
	}
}
