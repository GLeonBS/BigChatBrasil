package com.bigchatbrasil.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class MyBeansUtil<T, R> {
    public void copyNonNullProperties(T target, R in) {
        if (in == null || target == null)
            return;

        final BeanWrapper src = new BeanWrapperImpl(in);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            Object propertyValue = trg.getPropertyValue(property.getName());
            if (providedObject != null && !(providedObject instanceof Collection<?>) && (Objects.isNull(
                    propertyValue) || providedObject.getClass() == propertyValue.getClass())) {
                trg.setPropertyValue(
                        property.getName(),
                        providedObject);
            }
        }
    }
}