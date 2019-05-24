package com.example.springbootwebsocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/5/24 11:34
 * description:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.socketio")
public class SocketIOProperties {

    private String host;

    private Integer port;

    private int bossThreads;

    private int workThreads;

    private boolean allowCustomRequests;

    private int upgradeTimeout;

    private int pingTimeout;

    private int pingInterval;
}
