package com.example.springbootwebsocket;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/5/24 13:29
 * description:
 */
@Getter
@Setter
public class ClientInfo {
    private String clientId;
    private boolean isOnline;
    private long mostSignificantBits;
    private long leastSignificantBits;
    private Date lastConnectedTime;
}
