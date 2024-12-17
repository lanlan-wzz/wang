package com.blue.chat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/24 16:37
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext=applicationContext;
    }
    /**
     * 根据类名获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return SpringBeanUtils.applicationContext.getBean(name);

    }
    /**
     * 根据类型获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return SpringBeanUtils.applicationContext.getBean(clazz);
    }
}
