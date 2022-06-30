package com.chandler.red.mystock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.chandler.red.mystock.BaseActivity;
import com.chandler.red.mystock.HttpRequest;
import com.chandler.red.mystock.R;
import com.chandler.red.mystock.fragment.NewsFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class DisplayPriceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(NewsFragment.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.urlcontent);
        textView.setText(message);

        //Get html content

        TextView textView1 = findViewById(R.id.urlcontent);
        // textView1.setText("tstlslll");
        ///*
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                            String url = "http://hq.sinajs.cn/list=sh600054";
                            // String param = "company=0&MinsgType=a1";
                            String param = "";
                            HashMap<String, String> requestProperty = new HashMap<>();
                            //  requestProperty.put("Host", "http://localhost:8081");
                            requestProperty.put("Accept", "*/*");
                            // requestProperty.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
                            // requestProperty.put("Accept-Encoding", "gzip, deflate");
                            requestProperty.put("Connection", "Keep-Alive");
                            requestProperty.put("Referer", "http://finance.sina.com.cn");
                            requestProperty.put("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");

                            String result= HttpRequest.sendGet(url, param, requestProperty);
                            String decodeout = new String(result.getBytes("ISO-8859-1"), "UTF-8");
                            // System.out.println( decodeout);
                            String[] split = decodeout.split(",");
                            System.out.println(split[3]);

                    } catch (Exception err){
                        textView1.setText((CharSequence) err);
                    }

                    String s = "testseterlldlll***************";

                    String finalS = s;
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            textView1.setText(finalS);

                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            textView1.setText(e.toString());
        }
        //*/
    }
}