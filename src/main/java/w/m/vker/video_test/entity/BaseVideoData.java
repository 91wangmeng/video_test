package w.m.vker.video_test.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno-management <br/>
 * @Date: 2018/9/18 15:31 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@Data
public class BaseVideoData implements Serializable {
    private static final long serialVersionUID = 2174862434161605348L;
    private String id;
    private String time;
    private String cstreamid;
}
