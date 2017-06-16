package com.example.guowei.asynctasknewslistviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Guowei on 2017/6/15.
 */

public class ImageLoader {
//    private ImageView imageView;
//    private String urlTag;

    //用于缓存图片
    private static LruCache<String,Bitmap> cache;
    private ListView mlistView;
    private Set<ImageAsyncTask> mTask;

    public ImageLoader(ListView listView) {
        this.mlistView=listView;
        mTask =new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();

        cache =new LruCache<String,Bitmap>(maxMemory/4){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

//    private Handler handler =new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if(urlTag.equals(imageView.getTag())){
//                imageView.setImageBitmap((Bitmap) msg.obj);
//            }
//        }
//    };
    public void showImageByUrl(ImageView imageView, final String url){
//        this.imageView=imageView;
//        this.urlTag =url;
        Bitmap bitmap= getBitmapFromCache(url);
        //内存中存在获取显示
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
        //没有就显示默认图片，让滑动去加载
        else{
            //通过线程的方法加载图片
//        showbyThread(url);
            //通过异步任务的方法加载图片
            imageView.setImageResource(R.mipmap.ic_launcher);
//            new ImageAsyncTask(mlistView,url).execute(url);
        }


    }

    /**
     * 从内存中获取图片
     * @param url
     * @return
     */
    private Bitmap getBitmapFromCache(String url) {
        if(!TextUtils.isEmpty(url)){
            return cache.get(url);
        }
        return null;
    }

//    private void showbyThread(final String url) {
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Bitmap bitmap=getBitmapFromUrl(url);
//                Message message = Message.obtain();
//                message.obj=bitmap;
//                addBitmapToCache(url,bitmap);
//                handler.sendMessage(message);
//            }
//        }.start();
//    }

    /**
     * 根据url获取bitmap
     * @param urlString
     * @return
     */
    public static Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap=null;
        InputStream is;
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap= BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 把图片放到缓存中
     * @param url
     * @param bitmap
     */
    public static void addBitmapToCache(String url, Bitmap bitmap) {
        if(!TextUtils.isEmpty(url)&&bitmap!=null){
            cache.put(url,bitmap);
        }
    }

    /**
     * 取消所有的加载线程
     */
    public void cancelAlltask() {
        for (ImageAsyncTask mtask:mTask){
            mtask.cancel(true);
        }
    }

    /**
     * 加载可见项的图片
     * @param start
     * @param end
     */
    public void loadImageVisible(int start, int end) {
        for(int i=start;i<end;i++){
            String url=NewsAdapter.Urls[i];
            Bitmap bitmap=getBitmapFromCache(url);
            //如果缓存中没有，新建加载线程加载图片
            if(bitmap==null){
                ImageAsyncTask task=new ImageAsyncTask(mlistView,url);
                task.execute(url);
                mTask.add(task);
            }
            //存在的话，成句tag从listview中获取imageview实例显示图片
            else {
                ImageView imageView = (ImageView) mlistView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
