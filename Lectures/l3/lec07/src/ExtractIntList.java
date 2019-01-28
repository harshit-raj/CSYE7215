package l3.lec07.src;

import java.util.*;

// Class that uses stack confinement

public class ExtractIntList {

	public static List<Integer> extract (Vector<Integer> list, Integer val) {
		ArrayList<Integer> matchList = new ArrayList<Integer> ();
		for (Integer i : list) {
			if (i.equals(val)) matchList.add(i);
		}
		return matchList;
	}

}
