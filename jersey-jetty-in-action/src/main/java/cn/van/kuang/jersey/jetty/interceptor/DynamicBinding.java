package cn.van.kuang.jersey.jetty.interceptor;

import cn.van.kuang.jersey.jetty.resource.QueryResource;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by VanKuang on 16/3/14.
 */
public class DynamicBinding implements DynamicFeature {

    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        if (QueryResource.class.equals(resourceInfo.getResourceClass())
                && resourceInfo.getResourceMethod().getName().equals("dynamicBinding")) {
            featureContext.register(CompressInterceptor.class);
        }
    }

}
