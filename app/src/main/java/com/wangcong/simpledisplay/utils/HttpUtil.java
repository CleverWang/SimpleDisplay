package com.wangcong.simpledisplay.utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 功能：网络工具
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.5
 */
public class HttpUtil {

    /**
     * @param postData post需要传入的数据
     * @return 服务器返回的数据
     */
    public static String getData(Map<String, String> postData) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        // 添加post数据
        for (Map.Entry<String, String> entry : postData.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(Const.SERVER_URL)
                .post(requestBody)
                .build();
        String responseData = "";
        Response response;
//        try {
        response = client.newCall(request).execute();
        responseData = response.body().string();
//        } catch (IOException e) {
////            e.printStackTrace();
//            Log.d(Const.TAG, "getData: " + e.getMessage());
//        }
        return responseData;
    }
}
