package activity;


import game.sharedmemory.readerwriter.RegisterControllerFactory;
import main.MainActivity;

import com.example.datastreamengine.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WelcomeActivity extends Activity{
	
	private Spinner spinnerAlgs = null;
	private Button signInButton = null;
	private TextView ipTextView = null;
	ArrayAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		
		this.spinnerAlgs = (Spinner)findViewById(R.id.spinner_algs);
		this.signInButton = (Button)findViewById(R.id.btn_confirm);
		this.ipTextView = (TextView)findViewById(R.id.tv_ip);
		
		adapter = ArrayAdapter.createFromResource(this, R.array.spinner_algs_array, 
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		this.spinnerAlgs.setAdapter(adapter);
		
		this.spinnerAlgs.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String spinner_alg = (String) adapter.getItem(arg2);
				ipTextView.setText(spinner_alg);
				ipTextView.setVisibility(0);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.signInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		
	}
	
	public int getAlgType(){
		
		String spinner_alg = this.spinnerAlgs.getSelectedItem().toString();
		int alg_type;
		System.out.println("The chosen algorithm is: " + spinner_alg);
		if (spinner_alg.equals("WEAK_REGISTER"))
			alg_type  = RegisterControllerFactory.WEAK_REGISTER;
		else
			alg_type = RegisterControllerFactory.ATOMIC_REGISTER;
		
		return alg_type;
	}
	
}
