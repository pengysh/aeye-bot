package com.ai.bot.web.service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatMessageExample() {
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

        public Criteria andMessageIdIsNull() {
            addCriterion("message_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNotNull() {
            addCriterion("message_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageIdEqualTo(Long value) {
            addCriterion("message_id =", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotEqualTo(Long value) {
            addCriterion("message_id <>", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThan(Long value) {
            addCriterion("message_id >", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThanOrEqualTo(Long value) {
            addCriterion("message_id >=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThan(Long value) {
            addCriterion("message_id <", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThanOrEqualTo(Long value) {
            addCriterion("message_id <=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdIn(List<Long> values) {
            addCriterion("message_id in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotIn(List<Long> values) {
            addCriterion("message_id not in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdBetween(Long value1, Long value2) {
            addCriterion("message_id between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotBetween(Long value1, Long value2) {
            addCriterion("message_id not between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andAccountRefIsNull() {
            addCriterion("account_ref is null");
            return (Criteria) this;
        }

        public Criteria andAccountRefIsNotNull() {
            addCriterion("account_ref is not null");
            return (Criteria) this;
        }

        public Criteria andAccountRefEqualTo(String value) {
            addCriterion("account_ref =", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefNotEqualTo(String value) {
            addCriterion("account_ref <>", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefGreaterThan(String value) {
            addCriterion("account_ref >", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefGreaterThanOrEqualTo(String value) {
            addCriterion("account_ref >=", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefLessThan(String value) {
            addCriterion("account_ref <", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefLessThanOrEqualTo(String value) {
            addCriterion("account_ref <=", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefLike(String value) {
            addCriterion("account_ref like", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefNotLike(String value) {
            addCriterion("account_ref not like", value, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefIn(List<String> values) {
            addCriterion("account_ref in", values, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefNotIn(List<String> values) {
            addCriterion("account_ref not in", values, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefBetween(String value1, String value2) {
            addCriterion("account_ref between", value1, value2, "accountRef");
            return (Criteria) this;
        }

        public Criteria andAccountRefNotBetween(String value1, String value2) {
            addCriterion("account_ref not between", value1, value2, "accountRef");
            return (Criteria) this;
        }

        public Criteria andFromAccountIsNull() {
            addCriterion("from_account is null");
            return (Criteria) this;
        }

        public Criteria andFromAccountIsNotNull() {
            addCriterion("from_account is not null");
            return (Criteria) this;
        }

        public Criteria andFromAccountEqualTo(String value) {
            addCriterion("from_account =", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountNotEqualTo(String value) {
            addCriterion("from_account <>", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountGreaterThan(String value) {
            addCriterion("from_account >", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountGreaterThanOrEqualTo(String value) {
            addCriterion("from_account >=", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountLessThan(String value) {
            addCriterion("from_account <", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountLessThanOrEqualTo(String value) {
            addCriterion("from_account <=", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountLike(String value) {
            addCriterion("from_account like", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountNotLike(String value) {
            addCriterion("from_account not like", value, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountIn(List<String> values) {
            addCriterion("from_account in", values, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountNotIn(List<String> values) {
            addCriterion("from_account not in", values, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountBetween(String value1, String value2) {
            addCriterion("from_account between", value1, value2, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andFromAccountNotBetween(String value1, String value2) {
            addCriterion("from_account not between", value1, value2, "fromAccount");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andMessageIsNull() {
            addCriterion("message is null");
            return (Criteria) this;
        }

        public Criteria andMessageIsNotNull() {
            addCriterion("message is not null");
            return (Criteria) this;
        }

        public Criteria andMessageEqualTo(String value) {
            addCriterion("message =", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotEqualTo(String value) {
            addCriterion("message <>", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThan(String value) {
            addCriterion("message >", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThanOrEqualTo(String value) {
            addCriterion("message >=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThan(String value) {
            addCriterion("message <", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThanOrEqualTo(String value) {
            addCriterion("message <=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLike(String value) {
            addCriterion("message like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotLike(String value) {
            addCriterion("message not like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageIn(List<String> values) {
            addCriterion("message in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotIn(List<String> values) {
            addCriterion("message not in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageBetween(String value1, String value2) {
            addCriterion("message between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotBetween(String value1, String value2) {
            addCriterion("message not between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andToAccountIsNull() {
            addCriterion("to_account is null");
            return (Criteria) this;
        }

        public Criteria andToAccountIsNotNull() {
            addCriterion("to_account is not null");
            return (Criteria) this;
        }

        public Criteria andToAccountEqualTo(String value) {
            addCriterion("to_account =", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotEqualTo(String value) {
            addCriterion("to_account <>", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountGreaterThan(String value) {
            addCriterion("to_account >", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountGreaterThanOrEqualTo(String value) {
            addCriterion("to_account >=", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountLessThan(String value) {
            addCriterion("to_account <", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountLessThanOrEqualTo(String value) {
            addCriterion("to_account <=", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountLike(String value) {
            addCriterion("to_account like", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotLike(String value) {
            addCriterion("to_account not like", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountIn(List<String> values) {
            addCriterion("to_account in", values, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotIn(List<String> values) {
            addCriterion("to_account not in", values, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountBetween(String value1, String value2) {
            addCriterion("to_account between", value1, value2, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotBetween(String value1, String value2) {
            addCriterion("to_account not between", value1, value2, "toAccount");
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