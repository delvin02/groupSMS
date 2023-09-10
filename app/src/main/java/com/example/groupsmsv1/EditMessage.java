package com.example.groupsmsv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The EditMessage Activity allows users to edit a message and returns the updated message.
 */
public class EditMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);

        Intent editIntent;
        EditText etMessage;

        // get the intent object from parent (Broadcast Receivers)
        editIntent = this.getIntent();
        String myMessage;

        // extract attribute from object
        myMessage = editIntent.getStringExtra("CURRENT_MESSAGE");
        etMessage = (EditText) this.findViewById(R.id.etMessage);
        etMessage.setText(myMessage);

        Button btnDone = (Button) this.findViewById(R.id.btnEditMessageDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("NEW_MESSAGE", ((EditText) findViewById(R.id.etMessage)).getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}