package com.opl.pharmavector.database;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Select;

public class DatabaseQueryHelper {

	public static void SaveSingleOrderDatabase(String id, int quantity,
			String CUST_CODE, String DELIVERY_DATE, String ORDER_NUMBER,
			String ENTRY_DATE, String MPO_CODE, String AM_PM) {
		CategoryDB db = new CategoryDB();
		db.pid = id;
		db.quantity = quantity;
		db.CUST_CODE = CUST_CODE;
		db.MPO_CODE = MPO_CODE;
		db.AM_PM = AM_PM;
		db.DELIVERY_DATE = DELIVERY_DATE;
		db.ORDER_NUMBER = ORDER_NUMBER;
		db.ENTRY_DATE = ENTRY_DATE;
		db.save();
	}

	// public static void DeleteSingleDatabase(String id) {
	// Log.w("id", ""+id);
	// new Delete()
	// .from(CategoryDB.class)
	// .where("notificationid = ?", id).execute();
	// }

	public static ArrayList<CategoryDB> getAll() {
		ArrayList<CategoryDB> notiDBs = new ArrayList<CategoryDB>();
		List<CategoryDB> teamDB = new Select().all().from(CategoryDB.class)
				.execute();
		notiDBs.addAll(teamDB);
		return notiDBs;
	}

}
