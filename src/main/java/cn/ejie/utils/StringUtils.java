package cn.ejie.utils;

public class StringUtils {
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
}
