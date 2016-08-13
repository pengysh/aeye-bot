package com.ai.bot.common.base;

public abstract class PersonalBotBase extends BotBase {

	private String personId;

	private String personName;

	public PersonalBotBase(String id, String personId, String personName) {
		super(id, null, null, StateBase.Idle);
		this.personId = personId;
		this.personName = personName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}
