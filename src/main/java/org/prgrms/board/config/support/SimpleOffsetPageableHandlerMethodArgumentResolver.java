package org.prgrms.board.config.support;

import static org.apache.commons.lang3.math.NumberUtils.*;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimpleOffsetPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final long DEFAULT_OFFSET = 0L;
	private static final int DEFAULT_LIMIT = 5;

	private final String offsetParam;
	private final String limitParam;

	public SimpleOffsetPageableHandlerMethodArgumentResolver() {
		this("offset", "limit");
	}

	public SimpleOffsetPageableHandlerMethodArgumentResolver(String offsetParam, String limitParam) {
		this.offsetParam = offsetParam;
		this.limitParam = limitParam;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Pageable.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) throws Exception {

		String offsetStr = webRequest.getParameter(offsetParam);
		String limitStr = webRequest.getParameter(limitParam);

		long offset = toLong(offsetStr, DEFAULT_OFFSET);
		int limit = toInt(limitStr, DEFAULT_LIMIT);

		if (offset < 0) {
			offset = DEFAULT_OFFSET;
		}

		if (limit < 1 || limit > 10) {
			limit = DEFAULT_LIMIT;
		}

		return new SimpleOffsetPageRequest(offset, limit);
	}
}
