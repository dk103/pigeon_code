package com.app.mail.pigeonmailer.com.app.mail.pigeonmailer.MailApi;

import android.os.AsyncTask;
import android.util.Log;

import com.github.sendgrid.SendGrid;

import java.util.Hashtable;

public class SendGridAsyncTask  extends AsyncTask<Hashtable<String, String>, Void, String> {
    private static final String TAG = "SendGrid";
    @Override
    protected String doInBackground(Hashtable<String, String>... hashtables) {
        Hashtable<String, String> h = hashtables[0];
        SendGridCredentials sendGridCredentials = new SendGridCredentials();
       // SendGrid sendGrid = new SendGrid("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 Copied!\n");
        SendGrid sendGrid = new SendGrid(sendGridCredentials.getUsername(), sendGridCredentials.getPassword());
        sendGrid.addTo(h.get("to"));
        sendGrid.setFrom(h.get("from"));
        sendGrid.setSubject(h.get("subject"));
        sendGrid.setText(h.get("text"));
        String response = sendGrid.send();
        Log.i(TAG,"response:-"+response);
        return response;
    }
}
