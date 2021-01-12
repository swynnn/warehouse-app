package com.example.warehouseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent); //retrieve the sms, sms msg stored in array, 1 sms has limit character
        //access sms details
        for (int i = 0; i < messages.length; i++) { //to access sms details
            SmsMessage currentMessage = messages[i]; //retrieving msg from array and stored in currentmessage
            String message = currentMessage.getDisplayMessageBody(); //real msg u want to show in the application

            Intent myIntent = new Intent();             //Intent is a messaging object you can use to request an action from another app component
            myIntent.setAction("mySMS");                //later can find in intentfilter
            myIntent.putExtra("SMSKEY", message); //store data as a key value pair
            context.sendBroadcast(myIntent);
        }
    }
}
