package com.example.springbootwebsocket;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/5/24 11:42
 * description:
 */
@Getter
@Setter
public class PushMessage {

    private String message;
    private String loginUserNum;
}
