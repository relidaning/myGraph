package xyz.lidaning.graphweb.ms;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Data
@Slf4j
@Component
public class MsUtils {

    @Value("${domain}")
    private String domain;
    private static String _domain;
    @Value("${clientId}")
    private String clientId;
    private static String _clientId;
    @Value("${clientSecret}")
    private String clientSecret;
    private static String _clientSecret;
    private static String _token;

    static OkHttpClient client = new OkHttpClient().newBuilder().build();
    static MediaType mediaType = MediaType.parse("text/plain");

    @PostConstruct
    private void init(){
        _domain = domain;
        _clientId = clientId;
        _clientSecret = clientSecret;
    }


    public static String getToken() throws IOException {
        log.info("[v] msProperties-> domain: "+_domain+", clientId: "+_clientId+", clientSecret: "+_clientSecret);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("grant_type","client_credentials")
                .addFormDataPart("client_id",_clientId)
                .addFormDataPart("client_secret",_clientSecret)
                .addFormDataPart("resource","https://graph.microsoft.com")
                .build();
        Request request = new Request.Builder()
                .url("https://login.microsoftonline.com/"+_domain+"/oauth2/token")
                .method("POST", body)
                .addHeader("Cookie", "buid=0.AXIA_9XzB8XyeUu8g55fI0bEWPEkLoyK_odAgtI8k8xdxkpyAAA.AQABAAEAAAD--DLA3VO7QrddgJg7WevrPQp16Ww3-GGkpfBhTIaI_ENoZh7jOa2529HhBK4GcvT_GWrqLQeALYeDehn1c7g9jsziWWQ2svVkc_kp5205fXv5VwQ-Ensd2Da-uXf3gFEgAA; esctx=AQABAAAAAAD--DLA3VO7QrddgJg7WevrdUkMXB94vaeMJhhsY_ddmQlvkoA_EGA5B7Kd0mqgYgrCNjpMlFAub_cSvt0Iy8rHfv5XVSmdApM3XbQgZAJeCBiWwqfBURSgFyLP07wl9fCKq4mpQ94YVn3cbX78FxWe2Q7mVqjS4AtFLnNk0uFy9UMcKzPtOYcJ4yjYQ_JftI0gAA; fpc=Ah04jSkmLuZNhoMW2o4HNRQZQvmuAQAAAI0La9oOAAAA3BT0EAEAAABgDWvaDgAAAAXr2DUBAAAAaAtr2g4AAAA; stsservicecookie=estsfd; x-ms-gateway-slice=estsfd")
                .build();
        Response response = client.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resultJson = mapper.readTree(response.body().string());
        _token = resultJson.get("access_token").asText();
        return _token;
    }

    public static String users() throws IOException {
        if(_token==null)
            getToken();
        Request request = new Request.Builder()
                .url("https://graph.microsoft.com/v1.0/users")
                .method("GET", null)
                .addHeader("Authorization", "Bearer "+_token)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public static String myProfile() throws IOException {
        if(_token==null)
            getToken();
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://graph.microsoft.com/v1.0/me")
                .method("POST", body)
                .addHeader("Authorization", "Bearer "+_token)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }





}
