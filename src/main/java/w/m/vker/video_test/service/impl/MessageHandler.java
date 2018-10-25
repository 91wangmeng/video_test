package w.m.vker.video_test.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @Copyright: Zhejiang Drore Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno-management <br/>
 * @Date: 2018/10/12 11:09 <br/>
 * @Author: <a href="6492178@gmail.com">wmm</a>
 */
public class MessageHandler extends MessageToByteEncoder<JSONObject> {

    private static final int PREFIX = 65518;
    private static final int SUFIX = 65280;
    private static final int PIC_SEARCH = 301;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, JSONObject jsonObject, ByteBuf byteBuf) {
        //ByteBuf buffer = Unpooled.buffer();
        byteBuf.writeInt(PREFIX);
        byteBuf.writeInt(PIC_SEARCH);
        byteBuf.writeInt(1234);
        byteBuf.writeInt(jsonObject.toJSONString().length());
        //byteBuf.writeBytes(Unpooled.copiedBuffer(StrUtil.fillBefore(Integer.toHexString(jsonObject.toJSONString().length()),'0',4).getBytes()));
        //byteBuf.writeBytes(Unpooled.copiedBuffer(StrUtil.fillBefore(Integer.toHexString(204),'0',4).getBytes()));
        long guid = jsonObject.getLongValue("guid");
        byteBuf.writeCharSequence(jsonObject.toJSONString(), Charset.forName("utf-8"));
        byteBuf.writeInt(SUFIX);
    }

    public static void main(String[] args) {
        System.out.println(StrUtil.fillBefore(Integer.toHexString(204),'0',4));
    }

}
