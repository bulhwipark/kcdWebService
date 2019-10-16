package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.SearchVo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpClientSearch{
    public String httpClientRequest(SearchVo searchVo) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        String URL = "http://1.224.169.78:8080/MAIN/concepts";
        String parameter = "?activeFilter=true&term=" +
                URLEncoder.encode(searchVo.getTerm(), "UTF-8") +
                "&termActive=true&ecl=" + URLEncoder.encode(searchVo.getEcl(), "UTF-8") +
                "&offset=0&limit=50";

        System.out.println(URL + parameter);
        HttpGet httpGet = new HttpGet(URL + parameter);
        ///response
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);
        System.out.println(result);
        return result;
    }
}
