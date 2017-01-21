package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebBbinElectronic;

public class ComparatorBbinSortIndexDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebBbinElectronic u1 = (WebBbinElectronic) o1;
		WebBbinElectronic u2 = (WebBbinElectronic) o2;
		if (u1.getEleSortIndex() < u2.getEleSortIndex()) {
			return 1;
		} else {
			return 0;
		}
	}
}
