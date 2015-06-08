package activity;


import network.AtomicAPNetwork;
import network.OverlayNetworkFactory;
import game.GameModel;
import game.sharedmemory.data.kvstore.KVStoreInMemory;
import game.sharedmemory.readerwriter.RegisterControllerFactory;
import main.MainActivity;

import buffer.BufferManager;

import com.example.datastreamengine.R;

import constant.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WelcomeActivity extends Activity{
	
	private static final String TAG = WelcomeActivity.class.getName();
	
	private Spinner spinnerAlgs = null;
	private Button confirmBtn = null;
	private Button connectBtn = null;
	private TextView ipTextView = null;
	private Button startServerBtn = null;
	ArrayAdapter adapter;
	
	//屏幕宽度和高度
	private int tableWidth;
	private int tableHeight;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		
		this.spinnerAlgs = (Spinner)findViewById(R.id.spinner_algs);
		this.confirmBtn = (Button)findViewById(R.id.btn_confirm);
		
		this.startServerBtn = (Button)findViewById(R.id.btn_launch_conn);
		this.connectBtn = (Button)findViewById(R.id.btn_connect);
		adapter = ArrayAdapter.createFromResource(this, R.array.spinner_algs_array, 
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		this.spinnerAlgs.setAdapter(adapter);
		
		WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        Constant.initConst(tableWidth, tableHeight);
		
		this.spinnerAlgs.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String spinner_alg = (String) adapter.getItem(arg2);
				if(spinner_alg.equals("ATOMIC_REGISTER")){
					startServerBtn.setVisibility(View.VISIBLE);
					confirmBtn.setEnabled(false);
				}
				if(spinner_alg.equals("WEAK_REGISTER")){
					startServerBtn.setVisibility(View.INVISIBLE);
					confirmBtn.setEnabled(true);
				}
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		this.connectBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int alg_type = getAlgType();
				Log.i(TAG, "get type = " + alg_type);
				OverlayNetworkFactory.INSTANCE.setNetwork(alg_type);
				RegisterControllerFactory.INSTANCE.setRegisterController(alg_type);
				OverlayNetworkFactory.INSTANCE.getOverlayNetwork().connect();
				connectBtn.setEnabled(false);
				GameModel.INSTANCE.initialize();
				BufferManager.INSTANCE.startThread(); 
			}
		});
		
		this.startServerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AtomicAPNetwork.INSTANCE.new ServerTask().execute();
				KVStoreInMemory.INSTANCE.setIsAtomic(true);
				confirmBtn.setEnabled(true);
				startServerBtn.setEnabled(false);
				startServerBtn.setText("started");
			}
		});
	}
	
	public int getAlgType(){
		
		String spinner_alg = this.spinnerAlgs.getSelectedItem().toString();
		Log.i(TAG, "spinner_alg = " + spinner_alg);
		int alg_type;
		if (spinner_alg.equals("WEAK_REGISTER"))
			alg_type  = RegisterControllerFactory.WEAK_REGISTER;
		else
			alg_type = RegisterControllerFactory.ATOMIC_REGISTER;
		
		return alg_type;
	}
	
}
