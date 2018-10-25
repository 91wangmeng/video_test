package w.m.vker.video_test.service.impl;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import w.m.vker.video_test.service.VideoIdentifyService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Video identify service.
 *
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: miteno -management <br/>
 * @Date: 2018 /9/18 15:36 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">wmm</a>
 */
@Service
@Slf4j
public class VideoIdentifyServiceImpl implements VideoIdentifyService {
    public static final int DELAY = 1 * 60;
    @Value("${video.ip}")
    private String ip;
    @Value("${video.port}")
    private int port;
    @Resource
    private VideoIdentifyHandler videoIdentifyHandler;
    private Channel channel;


    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        new Thread(this::receive).start();

    }

    private void receive() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap clientBootstrap = new Bootstrap();
        try {
            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(new InetSocketAddress(ip, port));
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addFirst("logging", new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast("Correct order", new HeaderHandler());
                    pipeline.addFirst("logging1", new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                    pipeline.addLast(videoIdentifyHandler);
                    pipeline.addLast(new MessageHandler());

                }
            });
            clientBootstrap.localAddress(30086);
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channel = channelFuture.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("#################################################################");
            log.error("#######################视频识别服务连接失败##########################");
            log.error("#################################################################");
        } finally {
            group.shutdownGracefully();
            reconnect();
        }
    }

    private void reconnect() {
        log.warn("与服务端断开连接,{}秒后重试", DELAY);
        try {
            Thread.sleep(DELAY * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        receive();
    }

    @Override
    public void search() {

        channel.writeAndFlush(JSON.parseObject("{\"fun\":\"101\",\"data\":{\"tstart\":\"2018-10-25 16:05:28.000\",\"tend\":\"2018-10-25 16:36:58.000\",\"agefrom\":\"29\",\"ageto\":\"39\",\"gender\":\"1\"}}"));
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        VideoIdentifyServiceImpl videoIdentifyService = new VideoIdentifyServiceImpl();
        videoIdentifyService.ip = "192.168.36.88";
        videoIdentifyService.port = 33344;
        videoIdentifyService.videoIdentifyHandler = new VideoIdentifyHandler();
        new Thread(()->videoIdentifyService.receive()).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        videoIdentifyService.search();
    }
}
