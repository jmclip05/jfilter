package com.json.ignore.advice;

import com.json.ignore.EnableJsonFilter;
import com.json.ignore.filter.BaseFilter;
import com.json.ignore.filter.FilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Object filter provide bean
 *
 * <p>This class will be detected and instantiated automatically by Spring Framework
 *
 * <p>The main task of this class is: getting from http request session information, getting annotations from methodParam and
 * trying to find filter associated with annotation.
 *
 * <p>This class also has cache for caching already found filter for better productivity
 */
@Component
public class FilterProvider {
    private final Map<Annotation, BaseFilter> items;
    private boolean enabled;

    /**
     * Creates a new instance of the {@link FilterProvider} class.
     *
     */
    public FilterProvider() {
        this.items = new ConcurrentHashMap<>();
    }

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        /*
         * Important! For enabling filtration, should be specified one of application bean with EnableJsonFilter annotation
         */
        enabled = isFilterEnabled(webApplicationContext);
    }

    /**
     * Returns true if found EnableJsonFilter annotation in one of Spring beans
     * @param webApplicationContext {@link WebApplicationContext}
     * @return true if found, else false
     */
    public static boolean isFilterEnabled(WebApplicationContext webApplicationContext) {
        return webApplicationContext.getBeansWithAnnotation(EnableJsonFilter.class).size() > 0;
    }

    /**
     * Returns one of filters which supports annotation specified in MethodParameter or in Spring Web Service where MethodParameter is declared
     * @param methodParameter {@link MethodParameter}
     * @return {@link BaseFilter}
     */
    private BaseFilter getBaseFilter(MethodParameter methodParameter) {
        Annotation key = FilterFactory.getFilterAnnotation(methodParameter);

        if (key != null) {
            if (items.containsKey(key)) {
                //Retrieve filter from cache
                return items.get(key);
            } else {
                //Create and put filter in cache
                BaseFilter filter = FilterFactory.getFromFactory(methodParameter);
                if (filter != null) {
                    items.put(key, filter);
                    return filter;
                }
            }
        }
        return null;
    }

    /**
     * Check if provider supports processing method
     * <p>
     * Condition checks if provider is enabled and FilterFactory accepts method, else returns false
     *
     * @param methodParameter the method parameter
     * @return the boolean
     */
    public boolean isAccept(MethodParameter methodParameter) {
        return enabled && FilterFactory.isAccept(methodParameter);
    }

    public BaseFilter getFilter(MethodParameter methodParameter) {
        return getBaseFilter(methodParameter);
    }

    /**
     * Clear cache.
     */
    public void clearCache() {
        items.clear();
    }

    /**
     * Cache size int.
     *
     * @return the int
     */
    public int cacheSize() {
        return items.size();
    }

}
