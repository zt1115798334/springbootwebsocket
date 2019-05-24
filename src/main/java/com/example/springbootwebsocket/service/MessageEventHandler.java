package com.example.springbootwebsocket.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.springbootwebsocket.ClientInfo;
import com.example.springbootwebsocket.MessageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/5/24 13:28
 * description:
 */
@Component
@Slf4j
public class MessageEventHandler {
    //会话集合
    private static final ConcurrentSkipListMap<String, ClientInfo> webSocketMap = new ConcurrentSkipListMap<>();
    //静态变量，用来记录当前在线连接数。（原子类、线程安全）
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    private final SocketIOServer server;

    @Autowired
    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }


    /**
     * connect事件处理，当客户端发起连接时将调用
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        log.info("web socket连接:" + clientId);
        UUID session = client.getSessionId();
        ClientInfo si = webSocketMap.get(clientId);
        // 如果没有连接信息、则新建会话信息
        if (si == null) {
            si = new ClientInfo();
            si.setOnline(true);
            //在线数加1
            log.info("socket 建立新连接、sessionId:" + session + "、clientId:" + clientId + "、当前连接数：" + onlineCount.incrementAndGet());
        }
        // 更新设置客户端连接信息
        si.setLeastSignificantBits(session.getLeastSignificantBits());
        si.setMostSignificantBits(session.getMostSignificantBits());
        si.setLastConnectedTime(new Date());
        //将会话信息更新保存至集合中
        webSocketMap.put(clientId, si);
    }

    /**
     * disconnect事件处理，当客户端断开连接时将调用
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        webSocketMap.remove(clientId);
        //在线数减1
        log.info("socket 断开连接、sessionId:" + client.getSessionId() + "、clientId:" + clientId + "、当前连接数：" + onlineCount.decrementAndGet());
    }

    /**
     * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
     *
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(value = "message_event")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
        System.out.println("===============进入了onEvent方法==============");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = simpleDateFormat.format(new Date());

        String targetClientId = data.getTargetClientId();
        ClientInfo clientInfo = webSocketMap.get(targetClientId);
        if (clientInfo != null && clientInfo.isOnline()) {
            UUID target = new UUID(clientInfo.getMostSignificantBits(), clientInfo.getLeastSignificantBits());
            log.info("目标会话UUID:" + target);
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId(data.getSourceClientId());
            sendData.setTargetClientId(data.getTargetClientId());
            sendData.setMsg(data.getMsg());
            // 向当前会话发送信息
            client.sendEvent("message_event", sendData);
            // 向目标会话发送信息
            server.getClient(target).sendEvent("message_event", sendData);
        }
    }
}
