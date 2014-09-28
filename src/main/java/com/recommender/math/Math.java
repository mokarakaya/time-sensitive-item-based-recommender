package com.recommender.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Math {
	@SuppressWarnings("unchecked")
	public static List sortByValue(final Map m) {
	        List keys = new ArrayList();
	        keys.addAll(m.keySet());
	        Collections.sort(keys, new Comparator() {
	            public int compare(Object o1, Object o2) {
	                Object v1 = m.get(o1);
	                Object v2 = m.get(o2);
	                if (v1 == null) {
	                    return (v2 == null) ? 0 : 1;
	                }
	                else if (v1 instanceof Comparable) {
	                    return ((Comparable) v1).compareTo(v2)*-1;
	                }
	                else {
	                    return 0;
	                }
	            }
	        });
	        return keys;
	    }
}
