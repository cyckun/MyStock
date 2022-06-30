package com.chandler.red.mystock.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

import com.chandler.red.mystock.BaseActivity;
import com.chandler.red.mystock.HttpRequest;
import com.chandler.red.mystock.R;
import com.chandler.red.mystock.fragment.NewsFragment;

import java.util.HashMap;

import butterknife.BindView;


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

    @BindView(R.id.stock_real_code)
    EditText stockcode;

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

        Bundle bundle = getIntent().getExtras();
        String stock_code = bundle.getString("stock_real_code");
        String stock_code_string = String.format(stock_code);
        String stock_price = bundle.getString("stock_real_price");
        String stock_price_string = String.format(stock_price);

        System.out.printf("stock code = %s", stock_code_string);
        System.out.printf("stock price = %s", stock_price_string);

        //Get html content
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 600; i++) {
                        try {
                            String url = "http://hq.sinajs.cn/list=" + stock_code_string;
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

                            String result = HttpRequest.sendGet(url, param, requestProperty);
                            String decodeout = new String(result.getBytes("ISO-8859-1"), "UTF-8");
                            // System.out.println( decodeout);
                            String[] split = decodeout.split(",");
                            System.out.println(split[3]);

                            // 核心策略
                            if (Float.valueOf(split[3]).floatValue() < Float.valueOf(stock_price_string)) {
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
                                    textView.setText(finalS);

                                }
                            });
                            Thread.sleep(1000 * 60); // sleep 1 minute.

                        } catch (Exception err) {
                            textView.setText((CharSequence) err);
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            textView.setText(e.toString());
        }
        //*/
    }
}