package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class TimeFilter implements HttpRequestFilter, HttpResponseFilter {
    private static long start;
    private static long end;

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        this.start = System.currentTimeMillis();
    }

    @Override
    public void filter(FullHttpResponse response) {
        this.end = System.currentTimeMillis();
//        System.out.println(this.start);
//        System.out.println(this.end);
        System.out.println("请求响应时间为:" + (this.end - this.start) + "ms");
    }
}
