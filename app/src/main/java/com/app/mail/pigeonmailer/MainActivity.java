package com.app.mail.pigeonmailer;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mail.pigeonmailer.com.app.mail.pigeonmailer.MailApi.GMailSender;
import com.app.mail.pigeonmailer.com.app.mail.pigeonmailer.MailApi.SendGridAsyncTask;
import com.google.firebase.auth.FirebaseAuth;



import java.io.IOException;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PigeonMailMain";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
      /*  if (mAuth.getCurrentUser() == null) {
            Log.i(TAG,"user is not defined");

            startActivity(new Intent(this, LoginActivity.class));

            finish();
        }*/
    }

    public void doLogOut(View view) {
        Log.d(TAG,"inside log out");
        mAuth.signOut();

        startActivity(new Intent(this, LoginActivity.class));
       // Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getDisplayName()+" logged Out ", Toast.LENGTH_LONG).show();
        finish();


    }

    public void doSendEmail(View view){

        Handler handler =new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"sending..",Toast.LENGTH_SHORT).show();
                sendMailUsingSendGrid( "dmishra2243@gmail.com",

                        "dmishra2243@gmail.com","Test mail", "This mail has been sent from android app along with attachment");

                Toast.makeText(getApplicationContext(),"Message Sent Succesfully",Toast.LENGTH_SHORT).show();
                Log.i(TAG,"mail sent success");

            }
        });

//        new Thread(new Runnable() {
//
//            public void run() {
//
//                try {
//
//                    GMailSender sender = new GMailSender(
//
//                            "Pigeon2243",
//
//                            "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");
//
//
//
//
//
//                    sender.sendMail("demo mail", "Hope doing great............",
//
//                            "dmishra2243@gmail.com",
//
//                            "mishra.dheeraj92@gmail.com");
//
//
//
//                 //   Toast.makeText(MainActivity.this,"Message Sent Succesfully",Toast.LENGTH_LONG).show();
//                    Log.i(TAG,"mail sent success");
//
//
//
//
//
//                } catch (Exception e) {
//
//                 //   Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
//
//
//
//                }
//
//            }
//
//        }).start();



    }



    private void sendMailUsingSendGrid(String from, String to, String subject, String mailBody){
        Hashtable<String, String> params = new Hashtable<>();
        params.put("to", to);
        params.put("from", from);
        params.put("subject", subject);
        params.put("text", mailBody);

        SendGridAsyncTask email = new SendGridAsyncTask();
        try{
            email.execute(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
