package com.jfilter.mock;

import com.jfilter.filter.DynamicFilter;
import com.jfilter.components.DynamicSessionFilter;
import com.jfilter.filter.FieldFilterSetting;
import com.jfilter.filter.SessionStrategy;
import com.jfilter.filter.FileFilterSetting;
import com.jfilter.mock.config.MockDynamicNullFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import java.lang.reflect.Method;

public class MockMethods {

    @FieldFilterSetting(fields = {"id", "password"})
    public static MethodParameter mockIgnoreSettingsMethod(Object param) {
        return findMethodParameterByName("mockIgnoreSettingsMethod");
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "ADMIN", ignoreFields = {
            @FieldFilterSetting(fields = {"id"})
    })

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"email", "password"})
    })

    public static MethodParameter mockIgnoreStrategiesMethod(Object param) {
        return findMethodParameterByName("mockIgnoreStrategiesMethod");
    }

    @SessionStrategy(attributeName = "ROLE", attributeValue = "USER", ignoreFields = {
            @FieldFilterSetting(fields = {"id", "password"})
    })
    public static MethodParameter mockIgnoreStrategyMethod(Object param) {
        return findMethodParameterByName("mockIgnoreStrategyMethod");
    }

    @FieldFilterSetting(fields = {"id"})
    public static MethodParameter singleAnnotation(Object param) {
        return findMethodParameterByName("singleAnnotation");
    }

    @FieldFilterSetting(fields = {"strValue", "intValue", "items", "items2"})
    public static MethodParameter mockClass(Object param) {
        return findMethodParameterByName("mockClass");
    }

    @FieldFilterSetting(fields = {"strValue", "intValue", "items2"})
    public static MethodParameter mockClass2(Object param) {
        return findMethodParameterByName("mockClass2");
    }

    @FieldFilterSetting(fields = {"id"})
    public static MethodParameter secondSingleAnnotation(Object param) {
        return findMethodParameterByName("secondSingleAnnotation");
    }

    @FieldFilterSetting(fields = {"filters"})
    public static MethodParameter withoutFileFilters(Object param) {
        return findMethodParameterByName("withoutFileFilters");
    }

    @FieldFilterSetting(fields = {"password"})
    public static MethodParameter thirdSingleAnnotation(Object param) {
        return findMethodParameterByName("thirdSingleAnnotation");
    }


    @FieldFilterSetting(className = MockUser.class, fields = {"id", "email", "fullName"})
    @FieldFilterSetting(className = MockUser.class, fields = {"password", "intValue", "collectionValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"mapValue", "boolValue", "byteValue", "charValue"})
    @FieldFilterSetting(className = MockUser.class, fields = {"doubleValue", "floatValue", "longValue", "shortValue"})
    public static MethodParameter multipleAnnotation(Object param) {
        return findMethodParameterByName("multipleAnnotation");
    }

    public static MethodParameter methodWithoutAnnotations(Object param) {
        return findMethodParameterByName("methodWithoutAnnotations");
    }

    @Lazy
    public static MethodParameter methodWithLazyAnnotation(Object param) {
        return findMethodParameterByName("methodWithLazyAnnotation");
    }

    @FileFilterSetting(fileName = "config.xml")
    public static MethodParameter fileAnnotation(Object param) {
        return findMethodParameterByName("fileAnnotation");
    }

    @FileFilterSetting(fileName = "config.yaml")
    public static MethodParameter fileAnnotationYaml(Object param) {
        return findMethodParameterByName("fileAnnotationYaml");
    }

    @FileFilterSetting(fileName = "bad_config.xml")
    public static MethodParameter fileBadConfig(Object param) {
        return findMethodParameterByName("fileBadConfig");
    }

    @FileFilterSetting(fileName = "unknown_config.xml")
    public static MethodParameter fileNotExist(Object param) {
        return findMethodParameterByName("fileNotExist");
    }

    @FileFilterSetting(fileName = "config_no_controllers.xml")
    public static MethodParameter fileAnnotationNoControllers(Object param) {
        return findMethodParameterByName("fileAnnotationNoControllers");
    }

    @FileFilterSetting(fileName = "config_no_strategies.xml")
    public static MethodParameter fileAnnotationNoStrategies(Object param) {
        return findMethodParameterByName("fileAnnotationNoStrategies");
    }

    @FileFilterSetting(fileName = "config_class_duplicated.xml")
    public static MethodParameter fileAnnotationClassDuplicated(Object param) {
        return findMethodParameterByName("fileAnnotationClassDuplicated");
    }

    @DynamicFilter(DynamicSessionFilter.class)
    public static MethodParameter dynamicSessionFilter(Object param) {
        return findMethodParameterByName("dynamicSessionFilter");
    }

    @DynamicFilter(MockDynamicNullFilter.class)
    public static MethodParameter mockDynamicNullFilter(Object param) {
        return findMethodParameterByName("mockDynamicNullFilter");
    }

    @FileFilterSetting(fileName = "config_class_not_found.xml")
    public static MethodParameter fileAnnotationClassNotFound(Object param) {
        return findMethodParameterByName("fileAnnotationClassNotFound");
    }

    @FileFilterSetting()
    public static MethodParameter fileAnnotationEmpty(Object param) {
        return findMethodParameterByName("fileAnnotationEmpty");
    }

    @FileFilterSetting(fileName = "config_io_exception.xml")
    public static MethodParameter fileLocked(Object param) {
        return findMethodParameterByName("fileLocked");
    }

    @FileFilterSetting(fileName = "config_dynamic.xml")
    public static MethodParameter fileFilterDynamic(Object param) {
        return findMethodParameterByName("fileFilterDynamic");
    }


    private static Method findMethodByName(String methodName) {
        Method[] methods = MockMethods.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

    private static MethodParameter findMethodParameterByName(String methodName) {
        Method method = findMethodByName(methodName);
        if (method != null) {
            return new MethodParameter(method, 0);
        } else
            return null;
    }
}
