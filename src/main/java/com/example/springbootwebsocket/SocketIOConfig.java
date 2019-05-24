package com.example.springbootwebsocket;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/5/24 11:32
 * description:
 */
@Configuration
public class SocketIOConfig {

    /**
     * 以下配置在上面的application.properties中已经注明
     *
     * @return
     */

    @Autowired
    private SocketIOProperties socketIOProperties;

    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(socketIOProperties.getHost());
        config.setPort(socketIOProperties.getPort());
        config.setBossThreads(socketIOProperties.getBossThreads());
        config.setWorkerThreads(socketIOProperties.getWorkThreads());
        config.setAllowCustomRequests(socketIOProperties.isAllowCustomRequests());
        config.setUpgradeTimeout(socketIOProperties.getUpgradeTimeout());
        config.setPingTimeout(socketIOProperties.getPingTimeout());
        config.setPingInterval(socketIOProperties.getPingInterval());
        return new SocketIOServer(config);
    }

}
