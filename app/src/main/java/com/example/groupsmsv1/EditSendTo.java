package com.example.groupsmsv1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The EditSendTo Activity allows users to edit a phone number and returns the updated phone number.
 */
public class EditSendTo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_send_to);

        Intent editSendToIntent;
        EditText etPhoneNumber;

        // Get the intent that started this activity
        editSendToIntent = this.getIntent();
        String myPhoneNumber;

        // Initialize and configure the "Done" button
        Button btnDone = (Button) this.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create an intent to return the edited phone number
                Intent intent = new Intent();
                intent.putExtra("PHONE_NUMBER", ((EditText) findViewById(R.id.etPhoneNumber)).getText().toString());
                setResult(RESULT_OK, intent);

                // Finish this activity and return to the calling activity
                finish();
            }
        });
    }
}
