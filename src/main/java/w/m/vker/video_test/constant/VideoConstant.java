package w.m.vker.video_test.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import w.m.vker.video_test.entity.*;

import java.util.Arrays;

/**
 * The enum Data type.
 *
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno -management <br/>
 * @Date: 2018 /9/20 14:19 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */


public class VideoConstant {
    /**
     * The constant TODAY_CAR_COUNT.
     */
    public static final String TODAY_CAR_COUNT = "today_car_count";
    /**
     * The constant FACE.
     */
    public static final String FACE = "face";
    /**
     * The constant PERSON.
     */
    public static final String PERSON = "person";

    /**
     * The enum Data type.
     */
    @AllArgsConstructor
    @Getter
    public enum DataType {
        /**
         * Face detection data type.
         */
        FACE_DETECTION(505, "人脸检测", PersonDetection.class),
        /**
         * Face detection data type.
         */
        PERSON_DETECTION(106, "行人检测", FaceDetection.class),
        /**
         * Face identify data type.
         */
        FACE_IDENTIFY(107, "人脸识别", PersonIdentify.class),
        /**
         * Car detection data type.
         */
        CAR_DETECTION(406, "车辆检测", CarDetection.class),
        /**
         * Car identify data type.
         */
        CAR_IDENTIFY(407, "车辆识别", CarIdentify.class),
        /**
         * Person density data type.
         */
        PERSON_DENSITY(604, "行人密度", PersonDensity.class);
        private int type;
        private String name;
        private Class<? extends BaseVideoData> dataclass;

        /**
         * Gets by type.
         *
         * @param type the type
         * @return the by type
         */
        public static DataType getByType(int type) {
            return Arrays.stream(DataType.values()).filter(dataType -> type == dataType.getType()).findAny().orElseThrow(()->new RuntimeException("不存在"));
        }
    }
}
