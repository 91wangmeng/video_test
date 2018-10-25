package w.m.vker.video_test.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Copyright: Zhejiang Drore Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno-management <br/>
 * @Date: 18.10.22 14:00 <br/>
 * @Author: <a href="6492178@gmail.com">wmm</a>
 */
@Data
public class FaceDetection extends BaseVideoData {

    @JSONField(name = "Age")
    private String age;
    @JSONField(name = "Gender")
    private String gender;

    private String path;

}
