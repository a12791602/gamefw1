package com.mh.commons.comparator;

import java.util.Comparator;

import com.mh.entity.WebCarousel;

public class ComparatorCarouselDesc implements Comparator {

	public int compare(Object o1, Object o2) {
		WebCarousel u1 = (WebCarousel) o1;
		WebCarousel u2 = (WebCarousel) o2;
		if (u1.getCrsIndex() < u2.getCrsIndex()) {
			return 1;
		} else {
			return 0;
		}
	}
}
