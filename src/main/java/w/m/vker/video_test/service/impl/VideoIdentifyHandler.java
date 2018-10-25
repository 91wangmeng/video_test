package w.m.vker.video_test.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import w.m.vker.video_test.constant.VideoConstant;
import w.m.vker.video_test.entity.BaseVideoData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: sstest <br/>
 * @Date: 2018/9/11 17:43 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class VideoIdentifyHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("#################################################################");
        log.info("#######################视频识别服务断开连接##########################");
        log.info("#################################################################");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("#################################################################");
        log.info("#######################视频识别服务建立连接##########################");
        log.info("#################################################################");



    }


    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        log.error("#################################################################");
        log.info("#######################视频识别服务连接失败##########################");
        log.error("#################################################################");
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
        try {

            int type = Integer.parseInt(s.substring(0, 3));
            String content = s.substring(3);
            JSONObject jsonObject = JSON.parseObject(content.replaceAll("\r", "").replaceAll("\n", ""));
            if (!jsonObject.isEmpty()) {
                log.debug("接收到数据的类型:{},数据内容:{}", JSON.toJSONString(VideoConstant.DataType.getByType(type).getName()), content);
                List<BaseVideoData> videoData = jsonObject.entrySet()
                        .stream()
                        .map(stringObjectEntry -> {
                            String data = JSON.toJSONString(stringObjectEntry.getValue());
                            return JSON.toJavaObject(JSON.parseObject(data), VideoConstant.DataType.getByType(type).getDataclass());
                        }).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("解析异常");
            channelHandlerContext.close();
        }
    }

    private String addPrefix(String key, String face) {
        return StrUtil.addPrefixIfNot(StrUtil.addPrefixIfNot(StrUtil.fillBefore(key, '0', 2), "hik"), face);
    }

}
