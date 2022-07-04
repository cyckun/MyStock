package com.chandler.red.mystock.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;
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
    public  void Ring(Context context, int loop) { //手机响铃
        //context 上下文
        int res = 0;
        if (loop%2 == 0) {
            res = R.raw.soft;
        } else {
            res = R.raw.love;
        }
        MediaPlayer player = MediaPlayer.create(context, res);
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
        vibate.vibrate(3 * 1000);
    }

    public  void Di(Context context) { //手机响铃
        //context 上下文
        MediaPlayer player = MediaPlayer.create(context, R.raw.di);
        //raw是新建在/res下的文件夹，ls是raw文件下mp3文件
        player.start();
        try {
            Thread.sleep(1 * 1000);//响铃时间1s
        } catch (Exception e) {
        }
        player.stop();
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // never close screen.

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.urlcontent);
        textView.setText(message);

        Bundle bundle = getIntent().getExtras();
        String stock_code = bundle.getString("stock_real_code");
        String stock_code_string = String.format(stock_code);
        if (stock_code_string == "") {    // just for test;
            stock_code_string = "sh600000";
        }
        String stock_price = bundle.getString("stock_real_price");
        String stock_price_string = String.format(stock_price);

        System.out.printf("stock code = %s", stock_code_string);
        System.out.printf("stock price = %s", stock_price_string);

        //Get html content
        try {
            String finalStock_code_string = stock_code_string;
            String[] aim_code = finalStock_code_string.split(";");
            String[] aim_price = stock_price_string.split(";");

            int real_price_index_cn = 3;
            int real_price_index_hk = 6;
            int real_price_index_gb = 1;   // xinlang api, note update.
            int real_price_index = real_price_index_cn;
            int metric_onwork_time = 600;  // metric 10 hours = 600 minites;

            // calculate index
            // gb_ must only one code input; TODO: update here;
            if (aim_code[0].contains("gb")) real_price_index = real_price_index_gb;
            else if (aim_code[0].contains("hk")) real_price_index = real_price_index_hk;

            int finalReal_price_index = real_price_index;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Alarm tip = new Alarm();
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
                    String aim_result = "";
                    for (int i = 0; i < metric_onwork_time; i++) { // 600 minutes; 10 hour
                        // tip.Di(getApplicationContext());
                        if (i % 30 == 0)  aim_result = "";  // 定期删除，最多显示30条；
                        try {
                            for (int j = 0; j < aim_code.length; j++) {
                                String url = "http://hq.sinajs.cn/list=" + aim_code[j];
                                String result = HttpRequest.sendGet(url, param, requestProperty);
                                String decodeout = new String(result.getBytes("ISO-8859-1"), "UTF-8");
                                // System.out.println( decodeout);
                                String[] split = decodeout.split(",");
                                if (split.length >= finalReal_price_index) {   // get the price from Source;
                                    aim_result += split[finalReal_price_index];
                                    aim_result += "\n";
                                    System.out.println(split[finalReal_price_index]);
                                    // 核心策略
                                    if (aim_price[j] == "") continue;  // In Case NULL, continue;
                                    try {
                                        Float.valueOf(aim_price[j]);
                                    } catch (Exception err) {
                                        aim_result = "float value invalide";
                                        continue;
                                    }
                                    if (Float.valueOf(split[finalReal_price_index]).floatValue() < Float.valueOf(aim_price[j])) {
                                        // start alam
                                        tip.Ring(getApplicationContext(), j);  // choose diff warning music;
                                        tip.Vib(getApplicationContext());
                                    }
                                } else {
                                    continue;
                                }
                            }

                            // end alarm
                            if (aim_result =="") aim_result = "no result";
                            String finalS = "\n\n" + aim_result;
                            new Handler(getMainLooper()).post(new Runnable() {   // TODO: where this func should be..
                                @Override
                                public void run() {
                                    textView.setText(finalS);
                                }
                            });
                            Thread.sleep(1000 * 60); // sleep 1 minute.

                        } catch (Exception err) {
                            textView.setText((CharSequence) err.toString());
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