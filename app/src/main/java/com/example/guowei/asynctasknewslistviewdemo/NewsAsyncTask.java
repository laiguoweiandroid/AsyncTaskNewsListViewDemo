package com.example.guowei.asynctasknewslistviewdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Guowei on 2017/6/15.
 */

public class NewsAsyncTask extends AsyncTask<String,Void,List<Bean>>{
    private static final String TAG = "NewsAsyncTask";
    private NewsAdapter adapter;
    private ListView mListView;
    private Context context;

    public NewsAsyncTask( ListView mListView, Context context) {
        this.mListView = mListView;
        this.context = context;
    }
   //后台线程负责连网获取数据
    @Override
    protected List<Bean> doInBackground(String... params) {
        return JsonUtil.getJsonData(params[0]);
    }
   //主线程更新ui
    @Override
    protected void onPostExecute(List<Bean> been) {
        Log.d(TAG, "onPostExecute: "+been.toString());

        if(adapter==null){
            adapter= new NewsAdapter(mListView,context,been);
            mListView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

    }
}
