package com.github.config;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeanPostProcessorLogging implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebDriver)
            log.info("Bean with name {} success created ", beanName);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DisposableBean) {
            DisposableBean disposableBean = (DisposableBean) bean;
            try {
                disposableBean.destroy();
                log.debug("Bean with name {} has been Destroyed");
            } catch (Exception e) {
                log.error(e.getMessage().concat(" ").concat(beanName));
            }
            System.out.println("Bean destroyed: " + beanName);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

}
