package com.abn.assessment.recipe.service;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider {

	private final MessageSource messageSource;

	public MessageProvider(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(String messageCode) {
		return getMessage(messageCode, null);
	}

	public String getMessage(String messageCode, List<Object> args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(messageCode, args != null ? args.toArray() : null, locale);
	}
}
