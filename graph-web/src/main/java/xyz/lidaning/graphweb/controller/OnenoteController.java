package xyz.lidaning.graphweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lidaning.common.JsonResult;
import xyz.lidaning.graphweb.ms.MsUtils;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/onenote")
public class OnenoteController {

    @GetMapping("/notes")
    public JsonResult notes() throws IOException {
        return JsonResult.success("sucess", MsUtils.myProfile());
    }
}
