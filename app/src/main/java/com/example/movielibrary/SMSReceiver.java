package com.example.movielibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "";
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            message = currentMessage.getDisplayMessageBody();



        }
        Intent my_intent = new Intent();
        my_intent.setAction("SMS_ACTION");
        my_intent.putExtra("KEY10", message);
        context.sendBroadcast(my_intent);

    }

}
