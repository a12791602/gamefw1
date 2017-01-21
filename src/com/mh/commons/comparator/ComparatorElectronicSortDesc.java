package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.TWebElectronicClass;

public class ComparatorElectronicSortDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		TWebElectronicClass c1  =  (TWebElectronicClass)o1;
		TWebElectronicClass c2  =  (TWebElectronicClass)o2;
		if (c1.getEleIndex() < c2.getEleIndex()) {
			return 1;
		} else {
			return 0;
		}
	}

}
