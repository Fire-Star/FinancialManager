package cn.ejie.pocustom;

import cn.ejie.po.User;

/**
 * Created by Administrator on 2017/8/9.
 */
public class UserCustom extends User{
    /**
     * 这里面写扩展属性。
     */
    public UserCustom(String username, String password) {
        super(username, password);
    }

    public UserCustom() {
    }

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
