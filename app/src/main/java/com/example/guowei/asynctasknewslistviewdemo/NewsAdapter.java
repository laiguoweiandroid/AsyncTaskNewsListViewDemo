package com.example.guowei.asynctasknewslistviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guowei on 2017/6/15.
 */

public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context context;
    private List<Bean> mNewsBean;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private int start,end;
    public static String []Urls;
    private boolean isFirstIn ;

    public NewsAdapter(ListView mListView,Context context, List<Bean> mBeans) {
        this.context = context;
        this.inflater= LayoutInflater.from(context);
        this.mNewsBean=mBeans;
        imageLoader = new ImageLoader(mListView);
        Urls=new String[mBeans.size()];
        for(int i=0;i<mBeans.size();i++){
            Urls[i]=mBeans.get(i).getmPhotoUrl();
        }
        mListView.setOnScrollListener(this);
        isFirstIn=true;
    }

    @Override
    public int getCount() {
        return mNewsBean.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= inflater.inflate(R.layout.item_layout,parent,false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Bean bean = mNewsBean.get(position);
        holder.ivPhoto.setTag(bean.getmPhotoUrl());
        holder.ivPhoto.setImageResource(R.mipmap.ic_launcher);
        imageLoader.showImageByUrl(holder.ivPhoto,bean.getmPhotoUrl());
        holder.tvTitle.setText(bean.getTitle());
        holder.tvContent.setText(bean.getContent());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE){
            //加载可见项
            imageLoader.loadImageVisible(start,end);
        }else{
           //停止任务
            imageLoader.cancelAlltask();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        start=firstVisibleItem;
        end = firstVisibleItem+visibleItemCount;
        if(isFirstIn&&visibleItemCount>0){
            imageLoader.loadImageVisible(start,end);
            isFirstIn=false;
        }
    }

    class ViewHolder{
        TextView tvTitle;
        TextView tvContent;
        ImageView ivPhoto;
    }
}
