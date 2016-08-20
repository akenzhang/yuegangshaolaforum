package com.mantianhong.home.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Mysearch;
import com.mantianhong.bean.MysearchRoot;
import com.mantianhong.home.adapter.HomeSearchActivityAdapter;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DBUtils;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/21.
 */
public class HomeSearchActivity extends BaseActivity {

    private TextView home_search_back;
    private EditText home_search_edittext;
    private Button home_search_button;
    private ListView home_search_listview;

    private List<Mysearch> mList;

    @Override
    protected int getLayout() {
        return R.layout.home_search_activity;
    }

    @Override
    protected void initView() {
        home_search_back = (TextView) this.findViewById(R.id.id_home_search_back);
        home_search_edittext = (EditText) this.findViewById(R.id.id_home_search_edittext);
        home_search_button = (Button) this.findViewById(R.id.id_home_search_button);
        home_search_listview = (ListView) this.findViewById(R.id.id_home_search_listview);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        home_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeSearchActivity.this.finish();
            }
        });

    }

    @Override
    protected void bindData() {

        //获得mList数据
        String strSQL ="";
        new DBUtils() {
            @Override
            protected void successRequest(String result) {

                Gson gson = new Gson();
                MysearchRoot root = gson.fromJson(result,MysearchRoot.class);

                if(!result.contains("找不到记录")){
                    HomeSearchActivityAdapter adapter = new HomeSearchActivityAdapter(mList,R.layout.home_search_detail_activity,HomeSearchActivity.this);
                    home_search_listview.setAdapter(adapter);
                }

            }
        }.getAsync(strSQL);

    }
}
