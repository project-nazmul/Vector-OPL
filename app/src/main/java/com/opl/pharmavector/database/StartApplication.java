package com.opl.pharmavector.database;

//import android.support.multidex.MultiDex;

import androidx.multidex.MultiDex;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;

public class StartApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		initializeDB();
		MultiDex.install(this);
	}

	protected void initializeDB() {
		Configuration.Builder configurationBuilder = new Configuration.Builder(
				this);
		configurationBuilder.addModelClasses(CategoryDB.class);

		ActiveAndroid.initialize(configurationBuilder.create());
	}

}
