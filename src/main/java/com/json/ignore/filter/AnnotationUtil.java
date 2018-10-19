package com.json.ignore.filter;

import com.json.ignore.filter.field.FieldFilterSetting;
import com.json.ignore.filter.field.FieldFilterSettings;
import com.json.ignore.filter.strategy.SessionStrategies;
import com.json.ignore.filter.strategy.SessionStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * This is util class used to help find annotations in class or method
 *
 */
public class AnnotationUtil {

    /**
     * Search for specified type list of annotation
     * @param method object's method which may have annotations
     * @param annotationClass name of annotation to search
     * @param <T> generic class
     * @return list of found annotations if found, else an array of length zero
     */
    public static <T extends Annotation> T[] getDeclaredAnnotations(Method method, Class<T> annotationClass) {
        return method.getDeclaredAnnotationsByType(annotationClass);
    }

    /**
     * Search for specified type of annotation
     * @param method object's method which may have annotation
     * @param annotationClass name of annotation to search
     * @param <T> generic class
     * @return annotation if found, else null
     */
    public static <T extends Annotation> T getDeclaredAnnotation(Method method, Class<T> annotationClass) {
        return method.getDeclaredAnnotation(annotationClass);
    }

    /**
     * Check for annotations id declared in method
     * @param method object's method which may have annotation
     * @param annotationClasses name of annotation to search
     * @return if one of specified annotation is found, else returns false
     */
    public static <T extends Annotation> boolean isAnnotationExists(Method method, Class<T>... annotationClasses) {
        for (Class<T> clazz : annotationClasses) {
            if (getDeclaredAnnotations(method, clazz).length > 0)
                return true;
        }
        return false;
    }

    /**
     * Search for {@link FieldFilterSetting} in method
     * @param method object's method which may have annotation
     * @return list of {@link FieldFilterSetting} if this type of annotation declared in method
     */
    public static FieldFilterSetting[] getSettingAnnotations(Method method) {
        FieldFilterSettings settings = AnnotationUtil.getDeclaredAnnotation(method, FieldFilterSettings.class);
        if (settings != null) {
            return  settings.value();
        } else
            return AnnotationUtil.getDeclaredAnnotations(method, FieldFilterSetting.class);
    }

    /**
     * Search for {@link SessionStrategy} in method
     * @param method bject's method which may have annotation
     * @return list of {@link SessionStrategy} if this type of annotation declared in method
     */
    public static SessionStrategy[] getStrategyAnnotations(Method method) {
        SessionStrategies strategies = AnnotationUtil.getDeclaredAnnotation(method, SessionStrategies.class);
        if(strategies != null) {
            return strategies.value();
        } else
            return  AnnotationUtil.getDeclaredAnnotations(method, SessionStrategy.class);
    }


}