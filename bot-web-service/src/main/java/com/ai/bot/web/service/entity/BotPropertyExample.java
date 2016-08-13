package com.ai.bot.web.service.entity;

import java.util.ArrayList;
import java.util.List;

public class BotPropertyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BotPropertyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("property_id is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("property_id is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(Integer value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(Integer value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(Integer value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(Integer value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(Integer value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<Integer> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<Integer> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(Integer value1, Integer value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andBotTypeIsNull() {
            addCriterion("bot_type is null");
            return (Criteria) this;
        }

        public Criteria andBotTypeIsNotNull() {
            addCriterion("bot_type is not null");
            return (Criteria) this;
        }

        public Criteria andBotTypeEqualTo(String value) {
            addCriterion("bot_type =", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeNotEqualTo(String value) {
            addCriterion("bot_type <>", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeGreaterThan(String value) {
            addCriterion("bot_type >", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bot_type >=", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeLessThan(String value) {
            addCriterion("bot_type <", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeLessThanOrEqualTo(String value) {
            addCriterion("bot_type <=", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeLike(String value) {
            addCriterion("bot_type like", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeNotLike(String value) {
            addCriterion("bot_type not like", value, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeIn(List<String> values) {
            addCriterion("bot_type in", values, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeNotIn(List<String> values) {
            addCriterion("bot_type not in", values, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeBetween(String value1, String value2) {
            addCriterion("bot_type between", value1, value2, "botType");
            return (Criteria) this;
        }

        public Criteria andBotTypeNotBetween(String value1, String value2) {
            addCriterion("bot_type not between", value1, value2, "botType");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameIsNull() {
            addCriterion("bot_property_name is null");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameIsNotNull() {
            addCriterion("bot_property_name is not null");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameEqualTo(String value) {
            addCriterion("bot_property_name =", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameNotEqualTo(String value) {
            addCriterion("bot_property_name <>", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameGreaterThan(String value) {
            addCriterion("bot_property_name >", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameGreaterThanOrEqualTo(String value) {
            addCriterion("bot_property_name >=", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameLessThan(String value) {
            addCriterion("bot_property_name <", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameLessThanOrEqualTo(String value) {
            addCriterion("bot_property_name <=", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameLike(String value) {
            addCriterion("bot_property_name like", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameNotLike(String value) {
            addCriterion("bot_property_name not like", value, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameIn(List<String> values) {
            addCriterion("bot_property_name in", values, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameNotIn(List<String> values) {
            addCriterion("bot_property_name not in", values, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameBetween(String value1, String value2) {
            addCriterion("bot_property_name between", value1, value2, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andBotPropertyNameNotBetween(String value1, String value2) {
            addCriterion("bot_property_name not between", value1, value2, "botPropertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsIsNull() {
            addCriterion("property_words is null");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsIsNotNull() {
            addCriterion("property_words is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsEqualTo(String value) {
            addCriterion("property_words =", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsNotEqualTo(String value) {
            addCriterion("property_words <>", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsGreaterThan(String value) {
            addCriterion("property_words >", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsGreaterThanOrEqualTo(String value) {
            addCriterion("property_words >=", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsLessThan(String value) {
            addCriterion("property_words <", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsLessThanOrEqualTo(String value) {
            addCriterion("property_words <=", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsLike(String value) {
            addCriterion("property_words like", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsNotLike(String value) {
            addCriterion("property_words not like", value, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsIn(List<String> values) {
            addCriterion("property_words in", values, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsNotIn(List<String> values) {
            addCriterion("property_words not in", values, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsBetween(String value1, String value2) {
            addCriterion("property_words between", value1, value2, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andPropertyWordsNotBetween(String value1, String value2) {
            addCriterion("property_words not between", value1, value2, "propertyWords");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueIsNull() {
            addCriterion("bot_property_value is null");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueIsNotNull() {
            addCriterion("bot_property_value is not null");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueEqualTo(String value) {
            addCriterion("bot_property_value =", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueNotEqualTo(String value) {
            addCriterion("bot_property_value <>", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueGreaterThan(String value) {
            addCriterion("bot_property_value >", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueGreaterThanOrEqualTo(String value) {
            addCriterion("bot_property_value >=", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueLessThan(String value) {
            addCriterion("bot_property_value <", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueLessThanOrEqualTo(String value) {
            addCriterion("bot_property_value <=", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueLike(String value) {
            addCriterion("bot_property_value like", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueNotLike(String value) {
            addCriterion("bot_property_value not like", value, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueIn(List<String> values) {
            addCriterion("bot_property_value in", values, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueNotIn(List<String> values) {
            addCriterion("bot_property_value not in", values, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueBetween(String value1, String value2) {
            addCriterion("bot_property_value between", value1, value2, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andBotPropertyValueNotBetween(String value1, String value2) {
            addCriterion("bot_property_value not between", value1, value2, "botPropertyValue");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(String value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(String value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(String value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(String value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(String value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLike(String value) {
            addCriterion("data_type like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotLike(String value) {
            addCriterion("data_type not like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<String> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<String> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(String value1, String value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(String value1, String value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechIsNull() {
            addCriterion("part_of_speech is null");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechIsNotNull() {
            addCriterion("part_of_speech is not null");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechEqualTo(String value) {
            addCriterion("part_of_speech =", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechNotEqualTo(String value) {
            addCriterion("part_of_speech <>", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechGreaterThan(String value) {
            addCriterion("part_of_speech >", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechGreaterThanOrEqualTo(String value) {
            addCriterion("part_of_speech >=", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechLessThan(String value) {
            addCriterion("part_of_speech <", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechLessThanOrEqualTo(String value) {
            addCriterion("part_of_speech <=", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechLike(String value) {
            addCriterion("part_of_speech like", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechNotLike(String value) {
            addCriterion("part_of_speech not like", value, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechIn(List<String> values) {
            addCriterion("part_of_speech in", values, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechNotIn(List<String> values) {
            addCriterion("part_of_speech not in", values, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechBetween(String value1, String value2) {
            addCriterion("part_of_speech between", value1, value2, "partOfSpeech");
            return (Criteria) this;
        }

        public Criteria andPartOfSpeechNotBetween(String value1, String value2) {
            addCriterion("part_of_speech not between", value1, value2, "partOfSpeech");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}