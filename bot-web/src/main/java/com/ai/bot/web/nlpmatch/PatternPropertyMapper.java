package com.ai.bot.web.nlpmatch;

import java.util.regex.Pattern;

import com.ai.bot.web.service.entity.BotProperty;

public class PatternPropertyMapper {

	private BotProperty botProperty;
	private String value;
	private Pattern pattern;

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public BotProperty getBotProperty() {
		return botProperty;
	}

	public void setBotProperty(BotProperty botProperty) {
		this.botProperty = botProperty;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
