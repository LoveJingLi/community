package com.miao.community_m.provider;

import com.alibaba.fastjson.JSON;
import com.miao.community_m.dto.AccessTokenDTO;
import com.miao.community_m.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
//        accessTokenDTO.setClient_id(clientId);
//        accessTokenDTO.setClient_secret(clientSecret);
//        accessTokenDTO.setRedirect_uri(redirectUri);

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000, TimeUnit.MILLISECONDS)
                .build();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token = " + token);
            return token;
        } catch (Exception e) {
//            log.error("getAccessToken error,{}", accessTokenDTO, e);
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string(); //拿到当前String的对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
//            log.error("getUser error,{}", accessToken, e);
            e.printStackTrace();
        }
        return null;
    }

}
