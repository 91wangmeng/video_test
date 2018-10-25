package w.m.vker.video_test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Person density.
 *
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno -management <br/>
 * @Date: 2018 /9/18 15:27 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@NoArgsConstructor
@Data
public class PersonDensity extends BaseVideoData{
    private static final long serialVersionUID = 9181629324613335075L;
    private String count;
    private String localtion;
    private String isline;
    private String lineLocation;
    private String directionAcount;
    private String interval;
}
