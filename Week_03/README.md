学习笔记

# 代理的压测，和直接访问的压测

代码在nio01里面，主要运行

![image-20210126150755028](E:\java01\Week_03\image-20210126150755028.png)

和![image-20210126150814984](E:\java01\Week_03\image-20210126150814984.png)

这个。使用okhttp进行代理访问

## 代理访问8808

![image-20210126150124754](E:\java01\Week_03\image-20210126150124754.png)

另外代理访问还会出现大量的报错，看信息是socket缓冲区满了，接收不到了，感觉是代理访问慢了点，导致有大量的访问接收不到。

```java
java.net.SocketException: No buffer space available (maximum connections reached?): connect
	at java.net.DualStackPlainSocketImpl.connect0(Native Method)
	at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:83)
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:172)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
	at java.net.Socket.connect(Socket.java:589)
	at okhttp3.internal.platform.Platform.connectSocket(Platform.java:129)
	at okhttp3.internal.connection.RealConnection.connectSocket(RealConnection.java:245)
	at okhttp3.internal.connection.RealConnection.connect(RealConnection.java:165)
	at okhttp3.internal.connection.StreamAllocation.findConnection(StreamAllocation.java:257)
	at okhttp3.internal.connection.StreamAllocation.findHealthyConnection(StreamAllocation.java:135)
	at okhttp3.internal.connection.StreamAllocation.newStream(StreamAllocation.java:114)
	at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.java:42)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
	at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.java:93)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
	at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.java:93)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.java:126)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
	at okhttp3.RealCall.getResponseWithInterceptorChain(RealCall.java:200)
	at okhttp3.RealCall.execute(RealCall.java:77)
	at java0.nio01.okhttp.OkHttpDemo.agent(OkHttpDemo.java:48)
	at java0.nio01.mynetty.HttpRequestHandler.channelRead0(HttpRequestHandler.java:36)
	at java0.nio01.mynetty.HttpRequestHandler.channelRead0(HttpRequestHandler.java:16)
	at io.netty.channel.SimpleChannelInboundHandler.channelRead(SimpleChannelInboundHandler.java:99)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:103)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:436)
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:324)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:296)
	at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:251)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.run(Thread.java:748)
```



## 直接访问8801

![image-20210126150208359](E:\java01\Week_03\image-20210126150208359.png)

# 路由

路由的代码，我下下来就是一个完整的。。。我只能尝试全部理解，并且学习这么优秀的编码方式