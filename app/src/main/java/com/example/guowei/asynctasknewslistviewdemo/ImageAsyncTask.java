package com.example.guowei.asynctasknewslistviewdemo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;


/**
 * Created by Guowei on 2017/6/15.
 */

/**
 * 异步加载图片
 */
public class ImageAsyncTask extends AsyncTask<String,Void,Bitmap> {
    private String url;
    private ListView mlistview;
    public ImageAsyncTask(ListView listview,String url) {
        this.mlistview =listview;
        this.url=url;

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url= params[0];
        Bitmap bitmap =ImageLoader.getBitmapFromUrl(url);
        if(bitmap!=null){
            //放到内存中
            ImageLoader.addBitmapToCache(url,bitmap);
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        //从listview中根据tag获得imageview实例
        ImageView imageView = (ImageView) mlistview.findViewWithTag(url);
        if(bitmap!=null&&imageView!=null){
            imageView.setImageBitmap(bitmap);
        }

    }


}
