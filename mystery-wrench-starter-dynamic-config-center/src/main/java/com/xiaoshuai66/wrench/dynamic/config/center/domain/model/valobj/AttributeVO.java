package com.xiaoshuai66.wrench.dynamic.config.center.domain.model.valobj;

/**
 * 属性调整值对象
 *
 * @Author 赵帅
 * @Create 2025/11/3 19:09
 */
public class AttributeVO {

    /**
     * 键 - 属性 fileName
     */
    private String attribute;

    /**
     * 值
     */
    private String value;

    public AttributeVO() {
    }

    public AttributeVO(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
