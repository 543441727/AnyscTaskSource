package com.ysten.anysctasksource;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog mDialog;
    private Button btn ;
    private ImageView mImageView ;

    private String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView() {
        mImageView= (ImageView) findViewById(R.id.imageView);
        btn = (Button) findViewById(R.id.btn) ;

        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();

                for (int i = 0; i < 10; i++) {
                    new MyAsyncTask().execute(url);
                }
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String ,Integer,byte[]> {



        /**
         * 被调用后立即执行，一般用来在执行后台任务前对UI做一些标记
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("wangjitao","onPreExecute"+Thread.currentThread().getName());
        }

        /**
         * 在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
         * @param params
         * @return
         */
        @Override
        protected byte[] doInBackground(String... params) {
            Log.i("wangjitao","doInBackground"+Thread.currentThread().getName());

            //请求网络数据
            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(params[0]);
                if (url != null) {
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    // 设置连接网络的超时时间
                    httpURLConnection.setRequestMethod("GET");//GET和POST必须全大写
                    httpURLConnection.setConnectTimeout(10000);//连接的超时时间
                    httpURLConnection.setReadTimeout(5000);//读数据的超时时间

                    // 表示设置本次http请求使用GET方式请求
                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200) {
                        // 从服务器获得一个输入流
                        InputStream is = httpURLConnection.getInputStream();

                        long total = httpURLConnection.getContentLength();
                        int count = 0;

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len=0;
                        while((len=is.read(buffer))!=-1){
                            baos.write(buffer, 0, len);

                            count += len;
                            publishProgress((int) ((count / (float) total) * 100));

                            //为了演示进度,休眠500毫秒
                            Thread.sleep(500);
                        }
                        is.close();
                        byte[] datas = baos.toByteArray();
                        baos.close();

                        return datas;

                    }
                }
            } catch (Exception e) {

            }


            return null;
        }

        /**
         * 当前进度的更新
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("wangjitao","onProgressUpdate"+Thread.currentThread().getName());
            mDialog.setProgress(values[0]);
        }

        /**
         * 当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
         * @param bytes
         */
        @Override
        protected void onPostExecute(byte[] bytes) {
            Log.i("wangjitao","onPostExecute"+Thread.currentThread().getName());
            mDialog.dismiss();
            mImageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }

    }
}
