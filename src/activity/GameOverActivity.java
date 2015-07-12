package activity;

import main.MainActivity;

import com.example.datastreamengine.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameOverActivity extends Activity {
	
	private Button yesBtn = null;
	private Button noBtn =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gameover_activity);
		
		yesBtn = (Button)findViewById(R.id.btn_yes);
		noBtn = (Button)findViewById(R.id.btn_no);
		
		this.yesBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		this.noBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
}
