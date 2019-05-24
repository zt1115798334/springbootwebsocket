package com.example.springbootwebsocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2019/3/29 15:22
 * description:
 */
@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {
    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav = new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public Object pushToWeb(@PathVariable String cid, String message) {
        try {
            ImController.sendInfo(message, cid);
        } catch (IOException e) {
            e.printStackTrace();
            return cid + "#" + e.getMessage();
        }
        return cid;
    }
}
