package cn.ejie.model.verifycodemodel;

import cn.ejie.po.VerifyMessage;

import java.awt.image.BufferedImage;

/**
 * Created by FireLang on 2017/5/18.
 */
public interface VerifyCode {
    //获得缓存图片
    public BufferedImage getVerifyBufferedImage();
    //获得图片对象
    public Object getVerifyObjectImage();
    //获得验证码
    public String getVerifyCode();

    public VerifyMessage getVerifyMessage();
}
