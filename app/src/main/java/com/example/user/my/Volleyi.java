package com.example.user.my;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by user on 2016/1/6.
 */
public class Volleyi extends Activity {

    private Button volley;
    private TextView text;
    private ImageView imageView;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.volley);


        volley = (Button) findViewById(R.id.volley);

        webview = (WebView) findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl("file:///android_asset/index.html");


        imageView = (ImageView) findViewById(R.id.imageView2);

        text = (TextView) findViewById(R.id.textView);


        init();

        imageload();
    }

    private void init() {
        RequestQueue mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET,"http://api.sina.cn/sinago/list.json?channel=hdpic_story&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=1", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Gson g=new Gson();

                        AAAAA aaaaa = g.fromJson(response.toString(), AAAAA.class);

                        List<AAAAA.DataEntity.ListEntity> list = aaaaa.getData().getList();

                        String title = aaaaa.getData().getList().get(0).getLong_title();

                        Log.i("TAG","解析出来的数据....."+ title);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });



        mQueue.add(jsonObjectRequest);




    }


private void imageload(){
    String imageurl="http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";

    RequestQueue requestQueue=Volley.newRequestQueue(this);

    final LruCache<String,Bitmap>lruCache=new LruCache<String,Bitmap>(20);

    ImageLoader.ImageCache imageCache=new ImageLoader.ImageCache() {
        @Override
        public Bitmap getBitmap(String s) {
            return lruCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {

            lruCache.put(s,bitmap);

        }
    };
    ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher);

   ImageLoader imageLoader=new ImageLoader(requestQueue,imageCache);

    imageLoader.get(imageurl,listener);
}



}
