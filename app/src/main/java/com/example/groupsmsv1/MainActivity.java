package com.example.groupsmsv1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * The main activity of the GroupSMSV1 application.
 */
public class MainActivity extends AppCompatActivity {

    /** Define a class-level tag for logging or debugging purposes */
    public static final String CLASS_TAG = "MainActivity";

    /** The currently set message. */
    private String message = "";
    /** The currently set phone number. */
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and configure the TextView for message details
        TextView tvMessageDetails = findViewById(R.id.tvMessageDetails);
        tvMessageDetails.setBackgroundColor(Color.GREEN);
        tvMessageDetails.setMovementMethod(new ScrollingMovementMethod());

        // Initialize default messages
        message = "No Message set";
        phone = "No phone set";

        // Initialize ActivityResultContract for starting activities
        ActivityResultContract simpleRawIntentContract;

        // Initialize callback classes for handling activity results
        HandleActivityResultForMessage handleActivityResultForMessage;
        HandleActivityResultForPhoneNumber handleActivityResultForPhoneNumber;

        // Initialize launchers for starting activities and handling results
        ActivityResultLauncher launchEditMessageActivity;
        ActivityResultLauncher launchEditSendToActivity;

        // Result is returned (ActivityResult object)
        simpleRawIntentContract = new ActivityResultContracts.StartActivityForResult();

        // Initialize callback classes
        handleActivityResultForMessage = new HandleActivityResultForMessage();
        handleActivityResultForPhoneNumber = new HandleActivityResultForPhoneNumber();

        // Instantiating launchActivity objects for EditMessage and EditSendTo
        // Registering functions to handle activity result contracts
        launchEditMessageActivity = registerForActivityResult(simpleRawIntentContract, handleActivityResultForMessage);
        launchEditSendToActivity = registerForActivityResult(simpleRawIntentContract, handleActivityResultForPhoneNumber);

        // Set initial summary
        setSummary();

        // Initialize and configure the "Edit Message" button
        Button btnEditMessage;
        btnEditMessage = this.findViewById(R.id.btnEditMessage);
        btnEditMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open EditMessage activity
                Intent editIntent;
                Activity sourceActivity;
                Class destinationClass;

                sourceActivity = MainActivity.this;
                destinationClass = EditMessage.class;

                // Intent determines which component to start
                editIntent = new Intent(sourceActivity, destinationClass);

                // Pass props into the child element (Bundling information)
                editIntent.putExtra("CURRENT_MESSAGE", message);

                // Start the EditMessage activity using the launcher
                launchEditMessageActivity.launch(editIntent);
            }
        });

        // Initialize and configure the "Edit Send To" button
        Button btnEditSendTo;
        btnEditSendTo = (Button) this.findViewById(R.id.btnEditSendTo);
        btnEditSendTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open EditSendTo activity
                Intent sendToIntent;
                Activity sourceActivity;
                Class destinationClass;

                sourceActivity = MainActivity.this;
                destinationClass = EditSendTo.class;

                sendToIntent = new Intent(sourceActivity, destinationClass);

                // Start the EditSendTo activity using the launcher
                launchEditSendToActivity.launch(sendToIntent);
            }
        });

        // Initialize and configure the "Send" button
        Button btnSend;
        btnSend = (Button) this.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastMessage = "Message sent";
                if (message.equalsIgnoreCase("No Message set")) {
                    toastMessage = "Message blank";
                }

                if (phone.equalsIgnoreCase("No phone set")) {
                    toastMessage = "Phone number is missing";
                }

                // Show a toast message
                showToast(toastMessage);
            }
        });
    }

    /** Sets the summary text based on the current message and phone number. */
    private void setSummary() {
        StringBuilder summary;
        summary = new StringBuilder("Sending to: \n");
        summary.append(phone);
        summary.append("\n\nMessage:\n");
        summary.append(message);

        TextView tvMessageDetails = (TextView) findViewById(R.id.tvMessageDetails);
        tvMessageDetails.setText(summary);
    }

    /**
     * Shows a toast message with the given text.
     * @param toastMessage The message to be displayed in the toast.
     */
    private void showToast(String toastMessage) {
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback class to handle activity result for editing message.
     */
    public class HandleActivityResultForMessage implements ActivityResultCallback<ActivityResult> {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String newMessage = data.getStringExtra("NEW_MESSAGE");
                message = newMessage;
                setSummary();
            }
        }
    }

    /**
     * Callback class to handle activity result for editing phone number.
     */
    public class HandleActivityResultForPhoneNumber implements ActivityResultCallback<ActivityResult> {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String newPhoneNumber = data.getStringExtra("PHONE_NUMBER");
                phone = newPhoneNumber;
                setSummary();
            }
        }
    }
}
