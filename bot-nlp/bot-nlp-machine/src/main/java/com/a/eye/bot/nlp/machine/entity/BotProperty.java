package com.a.eye.bot.nlp.machine.entity;

public class BotProperty {
    private Integer propertyId;

    private String botType;

    private String botPropertyName;

    private String propertyWords;

    private String botPropertyValue;

    private String dataType;

    private String partOfSpeech;

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getBotType() {
        return botType;
    }

    public void setBotType(String botType) {
        this.botType = botType == null ? null : botType.trim();
    }

    public String getBotPropertyName() {
        return botPropertyName;
    }

    public void setBotPropertyName(String botPropertyName) {
        this.botPropertyName = botPropertyName == null ? null : botPropertyName.trim();
    }

    public String getPropertyWords() {
        return propertyWords;
    }

    public void setPropertyWords(String propertyWords) {
        this.propertyWords = propertyWords == null ? null : propertyWords.trim();
    }

    public String getBotPropertyValue() {
        return botPropertyValue;
    }

    public void setBotPropertyValue(String botPropertyValue) {
        this.botPropertyValue = botPropertyValue == null ? null : botPropertyValue.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech == null ? null : partOfSpeech.trim();
    }
}