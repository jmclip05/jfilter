package com.jfilter.components;

import com.jfilter.EnableJsonFilter;
import com.jfilter.filter.BaseFilter;
import com.jfilter.filter.FilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Object filter provider bean
 *
 * <p>This class will be detected and instantiated automatically by Spring Framework
 *
 * <p>The main task of this class is: getting from http request session information, getting annotations from methodParam and
 * trying to find filter associated with annotation.
 *
 * <p>This class also has cache for caching already found filter for better productivity
 */
@Component
public final class FilterProvider {
    private final Map<Annotation, BaseFilter> filters;
    private boolean enabled;

    /**
     * Creates a new instance of the {@link FilterProvider} class.
     */
    public FilterProvider() {
        this.filters = new ConcurrentHashMap<>();
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
     *
     * @param webApplicationContext {@link WebApplicationContext}
     * @return true if found, else false
     */
    public static boolean isFilterEnabled(WebApplicationContext webApplicationContext) {

        return webApplicationContext.getBeansWithAnnotation(EnableJsonFilter.class).size() > 0;
    }

    /**
     * Returns one of filters which supports annotation specified in MethodParameter or in Spring Web Service where MethodParameter is declared
     *
     * @param methodParameter {@link MethodParameter}
     * @return {@link BaseFilter}
     */
    private BaseFilter getBaseFilter(MethodParameter methodParameter) {
        Annotation key = FilterFactory.getFilterAnnotation(methodParameter);

        if (key != null) {
            if (filters.containsKey(key)) {
                //Retrieve filter from cache
                return filters.get(key);
            } else {
                //Create and put filter in cache
                BaseFilter filter = FilterFactory.getFromFactory(methodParameter);

                filters.put(key, filter);
                return filter;
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

    /**
     * Returns one of filters which supports annotation specified in MethodParameter or in Spring Web Service where MethodParameter is declared
     *
     * @param methodParameter {@link MethodParameter}
     * @return {@link BaseFilter}
     */
    public BaseFilter getFilter(MethodParameter methodParameter) {
        return getBaseFilter(methodParameter);
    }

    /**
     * Returns Optional of filters which supports annotation specified in MethodParameter or in Spring Web Service where MethodParameter is declared
     *
     * @param methodParameter {@link MethodParameter}
     * @return {@link BaseFilter}
     */
    public Optional<BaseFilter> getOptionalFilter(MethodParameter methodParameter) {
        return Optional.ofNullable(getFilter(methodParameter));
    }

    /**
     * Clear cache of filter list
     */
    public void clearCache() {
        filters.clear();
    }

    /**
     * Cache size int
     *
     * @return the int
     */
    public int cacheSize() {
        return filters.size();
    }

}
