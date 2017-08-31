package cn.ejie.po;

import java.awt.image.BufferedImage;

public class VerifyMessage {
    private String verifyCode;//验证码
    private BufferedImage verifyImage;//验证码图片

    public VerifyMessage(BufferedImage verifyImage , String verifyCode) {
        this.verifyCode = verifyCode;
        this.verifyImage = verifyImage;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public BufferedImage getVerifyImage() {
        return verifyImage;
    }

    public void setVerifyImage(BufferedImage verifyImage) {
        this.verifyImage = verifyImage;
    }
}
