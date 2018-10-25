package w.m.vker.video_test.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno-management <br/>
 * @Date: 2018/9/18 15:26 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@NoArgsConstructor
@Data
public class CarIdentify extends BaseVideoData {

    private static final long serialVersionUID = 7725042707074061719L;
    private String rimage;
    private LocalDateTime tstart;
    private LocalDateTime tend;
    private String color;
    private String model;
    private int hasPlateNum;
    @JSONField(name = "PlateNum")
    private String plateNum;
    @JSONField(name = "PlateNumPath")
    private String plateNumPath;
    private String isWhiteList;
}
