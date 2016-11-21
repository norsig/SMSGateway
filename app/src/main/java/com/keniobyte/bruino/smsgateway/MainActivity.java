/*
 * Copyright (c) Bruno Sarverry
 *
 * This file is licensed for Bruno Sarverry under GPL3 licence
 *
 */

package com.keniobyte.bruino.smsgateway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    //Connects to a websocket on Report Police web backend and listens for sms notifications:
    // https://github.com/Keniobyte/PoliceReportsWeb
    private final static String URL_LISTENER = "ws://192.168.15.103:8888/realtime/sms_websocket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // JSON format keniobyte
        String t = "{\"phone_number\":\"+549123456789\"" +
                ",\"verification_code\":\"ASD123\"" +
                ",\"message\":\"test message\"}";

        try {
            JSONObject j = new JSONObject(t);
            Log.i(TAG, j.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            WebSocket webSocket = new WebSocketFactory().createSocket(URL_LISTENER);
            webSocket.addListener(new WebSocketAdapter() {
                @Override
                public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
                    super.onConnectError(websocket, exception);
                    Log.i(TAG, exception.toString());
                }

                @Override
                public void onTextMessage(WebSocket websocket, String request_sms) throws Exception {
                    Log.i(TAG, "Message notification received");
                    sendSMS(request_sms);
                }
            });

            webSocket.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendSMS(String request) {
        try {
            JSONObject jsonRequest = new JSONObject(stringFormat(request));
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(jsonRequest.getString("phone_number")
                    , null
                    , jsonRequest.getString("message") + jsonRequest.getString("verification_code")
                    , null
                    , null);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "Error: " + e.toString());
        }
    }

    private String stringFormat(String string) {
        String a = string.substring(1, string.length() - 1);
        String b = a.replace("\\", "");
        return b;
    }
}