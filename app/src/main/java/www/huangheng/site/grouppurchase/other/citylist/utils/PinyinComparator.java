package www.huangheng.site.grouppurchase.other.citylist.utils;

import java.util.Comparator;

import www.huangheng.site.grouppurchase.other.citylist.bean.CityModel;


public class PinyinComparator implements Comparator<CityModel> {

	public int compare(CityModel o1, CityModel o2) {
		if (o1.getFirstName().equals("@") || o2.getFirstName().equals("#")) {
			return -1;
		} else if (o1.getFirstName().equals("#") || o2.getFirstName().equals("@")) {
			return 1;
		} else {
			return (o1.getFirstName()).compareTo(o2.getFirstName());
		}
	}

}
