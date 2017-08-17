package com.ysten.anysctasksource;

import android.os.AsyncTask;

/**
 * author : wangjitao
 * e-mail : 543441727@qq.com
 * time   : 2017/08/16
 * desc   :
 * version: 1.0
 */
public class MyAsyncTask extends AsyncTask<String ,Integer,byte[]> {



    /**
     * 被调用后立即执行，一般用来在执行后台任务前对UI做一些标记
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
     * @param params
     * @return
     */
    @Override
    protected byte[] doInBackground(String... params) {
//        publishProgress(100);
        return HttpUrlConnectionUtils.getByte(params[0]);
    }

    /**
     * 当前进度的更新
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    /**
     * 当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
     * @param bytes
     */
    @Override
    protected void onPostExecute(byte[] bytes) {

    }

}
