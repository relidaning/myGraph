package xyz.lidaning.graphweb.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lidaning.common.JsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class Callback {

    @GetMapping("/callback")
    public JsonResult callback(HttpServletRequest request, HttpServletResponse response){
        log.info("[x] callback...");
        return JsonResult.success();
    }
}
