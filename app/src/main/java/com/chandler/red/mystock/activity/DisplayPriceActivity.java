package com.chandler.red.mystock.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.TextView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;

import java.util.Random;

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


// alarm class
//public class VibrateUtil {
//    /**
//     * 让⼿机振动milliseconds毫秒
//     */
//    public static void vibrate(Context context, long milliseconds) {
//        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
//        if (vib.hasVibrator()) {  //判断⼿机硬件是否有振动器
//            vib.vibrate(milliseconds);
//        }
//    }
//
//    /**
//     * 让⼿机以我们⾃⼰设定的pattern[]模式振动
//     * long pattern[] = {1000, 20000, 10000, 10000, 30000};
//     */
//    public static void vibrate(Context context, long[] pattern, int repeat) {
//        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
//        if (vib.hasVibrator()) {
//            vib.vibrate(pattern, repeat);
//        }
//    }
//    /**
//     * 取消震动
//     */
//    public static void virateCancle(Context context) {
//        //关闭震动
//        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
//        vib.cancel();
//    }
//}

class TipHelper {
    // 播放默认铃声
    // 返回Notification id
    public static int PlaySound(final Context context) {
        NotificationManager mgr = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification nt = new Notification();
        nt.defaults = Notification.DEFAULT_SOUND;
        int soundId = new Random(System.currentTimeMillis())
                .nextInt(Integer.MAX_VALUE);
        mgr.notify(soundId, nt);
        return soundId;
    }
}
class Alarm {
    public  void Ring(Context context) { //手机响铃
        //context 上下文
        MediaPlayer player = MediaPlayer.create(context, R.raw.love);
        //raw是新建在/res下的文件夹，ls是raw文件下mp3文件
        player.start();
        try {
            Thread.sleep(10 * 1000);//响铃时间10s
        } catch (Exception e) {
        }
        player.stop();
    }
    public void Vib(Context context){  //手机震动
        Vibrator vibate = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibate.vibrate(10 * 1000);
    }
}

// end alarm class


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

                        // add alarm
                        if (Float.valueOf(split[3]).floatValue() > 10.0) {
                            // start alam
                            Alarm tip = new Alarm();
                            tip.Ring(getApplicationContext());
                            tip.Vib(getApplicationContext());
                        }
                        // end alarm

                        String finalS = "\n\n" + split[3];
                        new Handler(getMainLooper()).post(new Runnable() {   // TODO: where this func should be..
                            @Override
                            public void run() {
                                textView1.setText(finalS);

                            }
                        });

                    } catch (Exception err){
                        textView1.setText((CharSequence) err);
                    }
                }
            }).start();
        } catch (Exception e) {
            textView1.setText(e.toString());
        }
        //*/
    }
}