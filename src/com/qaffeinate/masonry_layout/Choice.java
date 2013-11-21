package com.qaffeinate.masonry_layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choice extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		
		final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   

                Intent activityChangeIntent = new Intent(Choice.this, MainActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                Choice.this.startActivity(activityChangeIntent);
            }
        });
		
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click   

                Intent activityChangeIntent = new Intent(Choice.this, com.qaffeinate.masonry_layout.second.MainActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                Choice.this.startActivity(activityChangeIntent);
            }
        });
		
		
	}

	 

}
