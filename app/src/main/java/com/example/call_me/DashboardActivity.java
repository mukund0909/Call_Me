package com.example.call_me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {
   EditText secretcode;
   Button join,share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        secretcode=findViewById(R.id.link);
        join=findViewById(R.id.joinbtn);
        share=findViewById(R.id.share);
        URL serverurl;
        try {
            serverurl= new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultoptions=new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverurl).setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultoptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretcode.getText().toString()).setWelcomePageEnabled(false).build();
                JitsiMeetActivity.launch(DashboardActivity.this,options);
            }
        });
    }
}