package w.m.vker.video_test.service.impl;

import cn.hutool.core.util.ArrayUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: sstest <br/>
 * @Date: 2018/9/17 10:56 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@Component
public class HeaderHandler extends ByteToMessageDecoder {

    private static final int SUFFIX_LENGTH = 4;
    private static final int PREFIX_LENGTH = 4;
    private static final int LENGTH_LENGTH = 4;
    private static final int TYPE_LENGTH = 4;
    private static final int ID_LENGTH = 4;
    private static final int RADIX = 16;
    private static final int SHORTEST_LENGTH = 16;

    /**
     * 消息头	消息内容	消息尾
     * Prefix	Length	Type	消息ID	JSON数据包	Surfix
     * 4byte	4byte	4byte     length            4byte
     * 0xFFEE		106	 -1	 message	0xFF00
     * 实际顺序是  prefix type id length message suffix
     * 由于传输过来的数据有问题
     *
     * @param ctx
     * @param in
     * @param out
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //最短消息长度,即最短获取长度字段的消息长度
        if (in.readableBytes() < SHORTEST_LENGTH) {
            return;
        }

        int beginIndex = in.readerIndex();
        //获取长度
        int length = adjustTheOrder(in, in.readerIndex() + PREFIX_LENGTH + TYPE_LENGTH + ID_LENGTH, LENGTH_LENGTH);
        //获取类型
        int type = adjustTheOrder(in, in.readerIndex() + PREFIX_LENGTH, TYPE_LENGTH);
        if ((in.readableBytes()) < length + PREFIX_LENGTH + TYPE_LENGTH + ID_LENGTH + SUFFIX_LENGTH + SUFFIX_LENGTH) {
            //如果剩余消息不是完整消息,即产生了拆包,则继续加载消息
            in.readerIndex(beginIndex);
            return;
        } else {
            //修改length和type的位置
            in.setInt(in.readerIndex() + LENGTH_LENGTH, length);
            in.setCharSequence(in.readerIndex() + PREFIX_LENGTH + TYPE_LENGTH + ID_LENGTH+1, type+"", Charset.forName("utf-8"));
        }
        //将读取索引设置为上次读取索引加本次消息长度加上消息头和消息尾的长度,即设置为下一条消息的开始位置
        in.readerIndex(in.readerIndex() + length + PREFIX_LENGTH + TYPE_LENGTH + ID_LENGTH + SUFFIX_LENGTH + SUFFIX_LENGTH);
        //消息头保留类型字段
        ByteBuf otherByteBufRef = in.slice(beginIndex +13, TYPE_LENGTH + length);
        otherByteBufRef.retain();
        out.add(otherByteBufRef);
    }

    private static int adjustTheOrder(ByteBuf byteBuf, int startIndex, int length) {
        String[] strings = new String[length];
        for (int i = startIndex; i < startIndex + length; i++) {
            strings[i - startIndex] = StringUtil.byteToHexStringPadded(byteBuf.getUnsignedByte(i));
        }
        return Integer.parseInt(String.join("", ArrayUtil.reverse(strings)), RADIX);
    }

}
