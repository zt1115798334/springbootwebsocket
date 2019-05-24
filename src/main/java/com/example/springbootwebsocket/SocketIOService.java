package com.example.springbootwebsocket;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/5/24 11:40
 * description:
 */
public interface SocketIOService {
    //推送的事件
    public static final String PUSH_EVENT = "push_event";

    // 启动服务
    void start() throws Exception;

    // 停止服务
    void stop();

    // 推送信息
    void pushMessageToUser(PushMessage pushMessage);
}
