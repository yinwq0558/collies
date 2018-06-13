package org.collies.colliet.heartbeat;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class HeartBeatService {
    private static final String URL_HEART_BEAT = "http://%s:%s/heartbeat";
    private static final long SYNC_DURATION = 5000L;

    @Scheduled(fixedRate = SYNC_DURATION)
    public void heartBeat() {
        String url = String.format(URL_HEART_BEAT, "localhost", "12288");
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        Map map = new HashMap();
        map.put("ip", "127.0.0.1");
        map.put("port", "12299");
        map.put("host", "test");
        map.put("duration", SYNC_DURATION);
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(JSON.toJSONString(map), Charset.forName("UTF-8")));

        try {
            httpclient.execute(httpPost);
            // 心跳日志输出
            System.out.println(String.format("[%s] HEART BEAT:%s",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), JSON.toJSONString(map)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
