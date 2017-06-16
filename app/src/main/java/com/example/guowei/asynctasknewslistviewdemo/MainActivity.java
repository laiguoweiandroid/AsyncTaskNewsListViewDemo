package com.example.guowei.asynctasknewslistviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //接口数据地址
    private static final String URL="http://www.imooc.com/api/teacher?type=4&num=30";
    private ListView mListView;
    private NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //启动一条异步线程用于从网上有异步加载数据
        new NewsAsyncTask(mListView,this).execute(URL);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
    }
}
