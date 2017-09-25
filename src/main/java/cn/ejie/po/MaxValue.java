package cn.ejie.po;

import cn.ejie.annotations.BeanPropertyErrorType;

public class MaxValue {
    @BeanPropertyErrorType(propertyName = "最大值的键")
    private String key;//键
    @BeanPropertyErrorType(propertyName = "最大值的值")
    private String value;//值

    @Override
    public String toString() {
        return "MaxValue{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
