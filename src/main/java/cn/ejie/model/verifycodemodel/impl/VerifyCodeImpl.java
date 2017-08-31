package cn.ejie.model.verifycodemodel.impl;

import cn.ejie.model.verifycodemodel.VerifyCode;
import cn.ejie.po.VerifyMessage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by FireLang on 2017/5/18.
 */
public class VerifyCodeImpl implements VerifyCode {

    private String vecode;//最后生成的正确验证码
    private Random random;//随机类，供以后使用
    private String orain="23456789ABCDEFGHJKLMNPQRSTUVWXYZ";//随机源字符串，这是默认的字符串，你可以配置的
    private String fonts[]={"宋体","黑体","华文楷体","微软雅黑","楷体_GB2312"};//默认的验证码字体样式，这些样式在生成验证码的每一位的时候会随机选择使用
    private int w=107,h=42;//默认的验证码宽度和高度
    private Integer verifyCodeCount=5;//验证码位数，默认为5，可以配置。
    private Color backColor=Color.lightGray;//图片的背景颜色，默认为淡灰色

    public String getVecode() {
        return vecode;
    }

    public void setVecode(String vecode) {
        this.vecode = vecode;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public String getOrain() {
        return orain;
    }

    public void setOrain(String orain) {
        this.orain = orain;
    }

    public String[] getFonts() {
        return fonts;
    }

    public void setFonts(String[] fonts) {
        this.fonts = fonts;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Integer getVerifyCodeCount() {
        return verifyCodeCount;
    }

    public void setVerifyCodeCount(Integer verifyCodeCount) {
        this.verifyCodeCount = verifyCodeCount;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public VerifyCodeImpl(){}
    private int getRandomSize(){
        return random.nextInt(3)+18;
    }
    //获得字体
    private String getRandomFont(){
        return fonts[random.nextInt(fonts.length)];
    }
    //获取字体风格
    private int getRandomStyle(){
        return random.nextInt(4);
    }
    //获得随机颜色，但是该颜色有范围的
    private Color getRandomColor(){
        int rgb[]=new int[3];
        for(int i=0;i<3;i++){
            rgb[i]=random.nextInt(100);
        }
        return new Color(rgb[0],rgb[1],rgb[2]);
    }
    //生成随机验证码
    private String getRandomText(){
        StringBuilder str=new StringBuilder();
        for(int i=1;i<=getVerifyCodeCount();i++){
            str.append(orain.charAt(random.nextInt(orain.length())));
        }
        vecode=str.toString();
        return vecode;
    }
    //设置验证码干扰线
    private void setLine(Graphics2D graphics2d){
        int y1,y2;

        for(int i=1;i<=8;i++){
            int temp=getH()/3;//获取到1/3
            y1=random.nextInt(getH());
            y2=random.nextInt(getH());

            graphics2d.setFont(new Font(getRandomFont(),getRandomStyle(),getRandomSize()));
            graphics2d.setColor(getRandomColor());
            graphics2d.drawLine(0, y1, getW(), y2);
        }
    }
    //设置验证码噪点
    private void setPoint(BufferedImage image){

        for(int i=1;i<=300;i++){
            int x=random.nextInt(getW());
            int y=random.nextInt(getH());
            image.setRGB(x, y, getRandomColor().getRGB());
        }
    }

    @Override
    public BufferedImage getVerifyBufferedImage() {
        //生成随机数，此过程设置了vecode
        getRandomText();
        //生成缓存图片
        BufferedImage image=new BufferedImage(getW(), getH(), BufferedImage.TYPE_INT_BGR);
        //获取画笔
        Graphics2D graphics2d=(Graphics2D)image.getGraphics();
        //设置画笔颜色
        graphics2d.setColor(backColor);
        //用该颜色当做背景色
        graphics2d.fillRect(0, 0, getW(), getH());

        //开始在缓存图片上画验证码
        for(int j=1;j<=getVerifyCodeCount();j++){
            int x=(int)((j-1)*w/getVerifyCodeCount()*1.0)+2;
            graphics2d.setColor(getRandomColor());
            int fontSize = getRandomSize();
            graphics2d.setFont(new Font(getRandomFont(), getRandomStyle(), fontSize));
            int y = (getH()-fontSize)/2+fontSize;
            graphics2d.drawString(vecode.charAt(j-1)+"", x, y);
        }
        //画干扰线
        setLine(graphics2d);
        return image;
    }

    @Override
    public Object getVerifyObjectImage() {
        return null;
    }
    @Override
    public String getVerifyCode(){
        return vecode;
    }

    @Override
    public VerifyMessage getVerifyMessage() {
        return new VerifyMessage(getVerifyBufferedImage(),getVerifyCode());
    }
}
