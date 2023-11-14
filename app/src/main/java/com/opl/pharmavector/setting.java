package com.opl.pharmavector;

import com.opl.pharmavector.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class setting  extends Activity implements OnClickListener {
	public EditText textfeild;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		textfeild=(EditText)findViewById(R.id.link_feild);
		Button btnset = (Button)findViewById(R.id.set);
		Button btnclr = (Button)findViewById(R.id.clear);
		textfeild.setBackgroundResource(R.drawable.shape);
		btnset.setBackgroundResource(R.drawable.default_button);
		btnclr.setBackgroundResource(R.drawable.default_button);
		Button back_btn = (Button)findViewById(R.id.back_btn);

		back_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(setting.this, Login.class);
				startActivity(i);
			}
		});
		btnclr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textfeild.setText("");
			}
		});
	}

	@Override
	public void onClick(View v) {}
}

