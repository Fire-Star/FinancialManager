package cn.ejie.dao;

import cn.ejie.po.MaxValue;

public interface MaxValueMapper {
    public String findValueByKey(String key) throws Exception;

    public void updataMaxValue(MaxValue maxValue) throws Exception;

    public void insertMaxValue(MaxValue maxValue) throws Exception;
}
