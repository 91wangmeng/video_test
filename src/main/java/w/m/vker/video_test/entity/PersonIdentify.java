package w.m.vker.video_test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Person identify.
 *
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno -management <br/>
 * @Date: 2018 /9/18 15:26 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@NoArgsConstructor
@Data
public class PersonIdentify extends BaseVideoData {
    private static final long serialVersionUID = -5747607600958163253L;
    private String rimage;
    private String bimage;
    private String age;
    private String gender;
    private String race;
    private String localtion;
    private String bid;
    private String tstart;
    private String tend;
    private String isWhiteList;
}
