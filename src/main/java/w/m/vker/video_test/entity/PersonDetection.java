package w.m.vker.video_test.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Person detection.
 *
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno -management <br/>
 * @Date: 2018 /9/18 15:26 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@NoArgsConstructor
@Data
public class PersonDetection extends BaseVideoData {
    private static final long serialVersionUID = -7947459332531173784L;
    private String path;
    @JSONField(name = "IsAlarm")
    private int isAlarm;
    @JSONField(name = "IsFaling")
    private int isFaling;
}
