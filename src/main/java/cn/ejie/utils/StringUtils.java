package cn.ejie.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {
    private static SimpleDateFormat enDateFormate = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat zhDateFormate = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 对字符串通过指定字符串填充到指定长度！
     * @param origin
     * @param fillCharacter
     * @param allLength
     * @return
     */
    public static String fillPreString(String origin,char fillCharacter,int allLength){
        int originLen = origin.length();
        if(originLen==allLength){
            return origin;
        }
        int willFillLen = allLength-originLen;
        StringBuilder resultString = new StringBuilder(origin);

        for (int i = 0; i < willFillLen; i++) {
            resultString.insert(0,fillCharacter);
        }
        return resultString.toString();
    }

    /**
     * 中文时间字符串到 英文规则时间字符串。如 1997年11月5日 字符串 调用该方法后，就会变成 1997-11-5
     * @param zhDateStr
     * @return
     */
    public static String zhDateStrToENDateStr(String zhDateStr){
        Date date = null;
        String targetDateStr = null;
        try {
            date = zhDateFormate.parse(zhDateStr);
            targetDateStr = enDateFormate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetDateStr;
    }

    /**
     * 计算与参数时间与现在所差的月数
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(String date)
            throws ParseException {

        int result = 0;

        Date now=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String nowTime=format.format(now);

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(format.parse(nowTime));
        c2.setTime(format.parse(date));

        result = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);

        return result;

    }
}
