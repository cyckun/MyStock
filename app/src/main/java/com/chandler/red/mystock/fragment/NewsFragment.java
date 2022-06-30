package com.chandler.red.mystock.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chandler.red.mystock.R;
import com.chandler.red.mystock.activity.DisplayMessageActivity;
import com.chandler.red.mystock.activity.DisplayPriceActivity;
import com.chandler.red.mystock.activity.ExchangeActivity;
import com.chandler.red.mystock.activity.ImageShowActivity;
import com.chandler.red.mystock.activity.LoginActivity;
import com.chandler.red.mystock.activity.MyInfoActivity;
import com.chandler.red.mystock.entity.Stock;
import com.chandler.red.mystock.presenter.StockPresenter;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {

    public static final String EXTRA_MESSAGE = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        Button btn_price = (Button)view.findViewById(R.id.btn_real);
        btn_price.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayPriceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


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