package com.chandler.red.mystock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chandler.red.mystock.R;
import com.chandler.red.mystock.activity.DisplayMessageActivity;
import com.chandler.red.mystock.activity.DisplayPriceActivity;
import com.chandler.red.mystock.activity.DisplayWeekActivity;
import com.chandler.red.mystock.activity.ExchangeActivity;
import com.chandler.red.mystock.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.stock_real_code)
    EditText  stockCode;
    @BindView(R.id.stock_real_price)
    EditText stockPrice;
    @BindView(R.id.btn_metric)
    Button btnMetric;

    public static final String EXTRA_MESSAGE = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText tv_code;
    private Button btn_metric;

    @BindView(R.id.my_news)
    TextView my_News;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_news);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
                startActivity(intent);
            }
        });

        Button btn_week = (Button)view.findViewById(R.id.btn_week);
        btn_week.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayWeekActivity.class);
                startActivity(intent);
            }
        });
        Button btn_metric = (Button)view.findViewById(R.id.btn_metric);
        btn_metric.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayPriceActivity.class);
                Bundle bundle = new Bundle();
                // transfer EditText to DisplayPriceActivity;
                EditText code = getActivity().findViewById(R.id.stock_real_code);
                EditText price = getActivity().findViewById(R.id.stock_real_price);
                EditText price_upper_tx = getActivity().findViewById(R.id.stock_real_price_upper);
                RadioButton price_upper = getActivity().findViewById(R.id.price_upper);
                RadioButton price_base = getActivity().findViewById(R.id.price_base);
                RadioButton default_code = getActivity().findViewById(R.id.default_code);
                int upper_checked = 0, base_checked = 0, default_code_checked = 0;
                if (price_upper.isChecked()) {
                    upper_checked = 1;
                }
                if (price_base.isChecked()) {
                    base_checked = 1;
                }
                if (default_code.isChecked()) {
                    default_code_checked = 1;
                }
                bundle.putString("stock_real_code", code.getText().toString());
                bundle.putString("stock_real_price", price.getText().toString());
                bundle.putString("stock_real_price_upper", price_upper_tx.getText().toString());
                bundle.putInt("price_upper", upper_checked);
                bundle.putInt("price_base", base_checked);
                bundle.putInt("default_code", default_code_checked);
                intent.putExtras(bundle);
//                intent.setAction("android.intent.action.MAIN");
//                intent.addCategory("android.intent.category.HOME");
                startActivity(intent);
            }
        });
        return view;
    }

//    @OnClick({R.id.btn_metric})
//    public void onViewClicked() {
//        String stockcode = stockCode.getText().toString();
//        String real_price  = stockPrice.getText().toString();
//        if (stockcode == null) return ;
//        System.out.println("test");
//    }

    @OnClick({R.id.my_news, R.id.my_sell})
    public void onViewClicked(View view) {
        if (view.getId() != R.id.my_photo) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1000);
            return;
        }
        if (view.getId() == R.id.my_news) {
            Intent intent = new Intent(getActivity(), ExchangeActivity.class);
            intent.putExtra("page", 0);
            startActivity(intent);
        }
    }

}