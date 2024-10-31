package com.chandler.red.mystock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.chandler.red.mystock.BaseActivity;
import com.chandler.red.mystock.HttpRequest;
import com.chandler.red.mystock.R;
import com.chandler.red.mystock.fragment.NewsFragment;

import java.util.Arrays;

public class DisplayWeekActivity extends BaseActivity {

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

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // String s = HttpRequest.sendGet("https://datareco.bytedance.net/news/html", "");
                    String s = HttpRequest.sendGet("http://124.222.162.36:5000/news/week", "");

                    // String stest = "金融界2021私募策略荟：寻找下一代世界级公司+沪指站上3600点+金融界机构投资者论坛暨中国房地产企业峰会(2020)+中国市场将进一步成为全球资本避风港。+苏宁基金：领先科技“智”“慧”投资+创近5年新高+券商板块强势爆发+新年首周融资客净买入超440亿+关注年报预喜板块的投资机会(股)+“杀向”三大赛道+抱团股大反攻+外资逆势抄底释放什么信号？+与其吐槽抱团+白酒新能源再度启动+不如潜伏核心资产+抱团主线调整+全球流动性拐点来了?四大指标亮黄灯+机构博弈激烈+债市综述：资金价格反弹+下载新浪财经App,随时随地看突发新闻+加载中...+国家动用医保基金支付新冠疫苗费用+[石家庄疫情主要分布在农村+隔夜利率创三周新高+60秒后刷新+农民占70%+[抗疫一线+累计本土确诊病例326例]+世卫组织：很高兴与中国在疫苗和病毒溯源等方面合作]一线楼市2020年末现翘尾行情+[北京楼市成交量创\"317新政\"后新高+上海去年一二手房创四年新高]+[广深楼市土地供应或持续放量+房价何去何从+房企去杠杆势在必行]沪指收复3600+股民：牛市真来了+[北向买84亿+新高后如何操作+私募调研路线曝光]+牛年策略会+有色业景气可持续+新能源车投资机会解读+央行拟出台征信办法+习近平：确保全面建设社会主义现代化国家开好局+征信机构不得过度采集信息+山西鸿润公司越界盗采被指超百亿元+叫停后仍疯狂作业+中国债券论坛：周诚君、李扬、朱民等演讲+北上广深等迈入无县时代+黑猫投诉|2020年黑猫投诉平台直播电商投诉数据+撤县设市成多地\"十四五\"重点+申万宏源85后投行精英竟敢\"PS\"担保函+金城医药商誉爆雷7亿却逆转+诈骗300多万元+2020胡润世界500强榜发布：苹果微软亚马逊排名前三+开盘跌12%收盘暴涨超13%+2020科技风云榜开幕+“巴菲特指标”显示全球股市泡沫化达金融危机前水平+建设银行湖州分行两任行长因涉嫌受贿被逮捕寄语大资管丨曾刚：树立风险收益相匹配的投资理念+邬贺铨孙洁王成录等嘉宾亮相+新浪财经APP新年摇一摇+20股贵州茅台现金等你拿+抱团股大幅波动+北京文化、旋极信息、嘉澳环保索赔征集中+知名公私募：不要慌+[方正：牛市第3阶段进加速期+中航证券：军工经历质变到量变拐点]+证监会1号罚单:庄家控制196账户操纵华平+第六届券商APP评选火热开启!+却遭5个跌停+";
                    // System.out.println(s);

                    String s_new = "";
                    for (int i=0; i < s.split(",").length; i++)
                        s_new += (s.split(",")[i] + "\n");

                    String finalS_new = s_new;
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(finalS_new);
                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            textView.setText(e.toString());
        }
        //*/
    }
}