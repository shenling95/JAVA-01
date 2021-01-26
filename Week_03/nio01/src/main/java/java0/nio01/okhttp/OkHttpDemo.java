package java0.nio01.okhttp;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by xfx on 2021/1/21 20:04
 */
public class OkHttpDemo {
    public static final String URL = "http://localhost:8801";

//    public static void main(String[] args){
//        OkHttpClient okHttpClient;
//        okHttpClient = new OkHttpClient.Builder()
//                            .connectTimeout(60, TimeUnit.SECONDS)
//                            .readTimeout(60, TimeUnit.SECONDS)
//                            .build();
//        Request request = new Request.Builder().url(URL).get().build();
//        final Call call = okHttpClient.newCall(request);
//        Response response = null;
//        try {
//            response = call.execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
//
//    }

    public static String agent(){
        OkHttpClient okHttpClient;
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(URL).get().build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        String result = "";
        try {
            response = call.execute();
            System.out.println(result = response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
