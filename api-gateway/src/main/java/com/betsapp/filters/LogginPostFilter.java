package com.betsapp.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class LogginPostFilter extends ZuulFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(LogginPostFilter.class);
	
	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	@Override
	public int filterOrder() {
		return 0;
	}
	
	@Override
	public String filterType() {
		return "post"; // post, error
	}
	
	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		
		logger.info("Post Request -> {} URI -> {}", request, request.getRequestURI());
		
		return null;
	}

}
