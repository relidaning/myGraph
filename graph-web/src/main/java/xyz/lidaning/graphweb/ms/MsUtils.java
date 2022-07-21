package xyz.lidaning.graphweb.ms;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class MsUtils {

    @Value("${domain}")
    private String domain;
    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;

    private static MsUtils instance;

    public static MsUtils getInstance(){
        if(instance==null)
            instance = new MsUtils();
        return instance;
    }


    public static String getToken(){
        log.info("domain: "+getInstance().domain+", clientId: "+getInstance().clientId+", clientSecret: "+getInstance().clientSecret);
        return "";
    }


}
