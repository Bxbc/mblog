package me.bxbc.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/2/15
 */
public class MyBeanUtlis {
    /**
     * 获取所有属性值为null的属性
     * @param source
     * @return
     */

    public static String[] getNullPropertyName (Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for(PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
