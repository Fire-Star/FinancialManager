package cn.ejie.service;

import cn.ejie.model.verifycodemodel.VerifyCode;
import cn.ejie.po.VerifyMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 * 验证码的service
 */
@Service
public class VertifyCodeService {

    @Resource(name = "verifyCodeImpl")
    private VerifyCode verifyCode;

    public VerifyMessage getVerifyMessage(){
        return verifyCode.getVerifyMessage();
    }
}
