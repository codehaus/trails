package org.trailsframework.conversations.services;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.MethodAdvice;
import org.apache.tapestry5.ioc.services.AspectDecorator;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.PageRenderRequestParameters;

public class RequestHandlerDecoratorImpl implements RequestHandlerDecorator {
	private final AspectDecorator aspectDecorator;

	private final ConversationManager conversationManager;

	private final Cookies cookies;

	public RequestHandlerDecoratorImpl(AspectDecorator aspectDecorator, ConversationManager conversationManager, Cookies cookies) {
		this.aspectDecorator = aspectDecorator;
		this.conversationManager = conversationManager;
		this.cookies = cookies;
	}

	public <T> T build(Class<T> serviceInterface, T delegate) {

		MethodAdvice advice = new MethodAdvice() {
			public void advise(Invocation invocation) {
				if ("handle".equals(invocation.getMethodName())) conversationManager.activateConversation(invocation.getParameter(0));
				invocation.proceed();
			}
		};

		return aspectDecorator.build(serviceInterface, delegate, advice, String.format("<Conversational context interceptor for %s>", serviceInterface.getName()));
	}
}