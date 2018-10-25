package w.m.vker.video_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import w.m.vker.video_test.service.impl.VideoIdentifyServiceImpl;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class VideoTestApplication {
    @Resource
    VideoIdentifyServiceImpl videoIdentifyService;

    @GetMapping("/test")
    public void test() {
        videoIdentifyService.search();
    }

    public static void main(String[] args) {
        SpringApplication.run(VideoTestApplication.class, args);
    }
}
