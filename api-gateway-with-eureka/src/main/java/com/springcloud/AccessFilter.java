package com.springcloud;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 网关过滤器
 * @author: yaoyao
 * @date 2020-02-24
 */
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    /*
    过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。
    这里定义为pre，代表会在请求被路由之前执行。
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /*
    过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /*
    判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有请求都会生效。
    实际运用中我们可以利用该函数来指定过滤器的有效范围。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*
    过滤器的具体逻辑。这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由。
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        String accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("access token is empty");
            return null;
        }
        log.info("access token ok");
        return null;
    }
}