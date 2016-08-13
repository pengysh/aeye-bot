package com.ai.bot.web.service.entity;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageGroupExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatMessageGroupExample() {
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

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andAccountIdsIsNull() {
            addCriterion("account_ids is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdsIsNotNull() {
            addCriterion("account_ids is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdsEqualTo(String value) {
            addCriterion("account_ids =", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsNotEqualTo(String value) {
            addCriterion("account_ids <>", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsGreaterThan(String value) {
            addCriterion("account_ids >", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsGreaterThanOrEqualTo(String value) {
            addCriterion("account_ids >=", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsLessThan(String value) {
            addCriterion("account_ids <", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsLessThanOrEqualTo(String value) {
            addCriterion("account_ids <=", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsLike(String value) {
            addCriterion("account_ids like", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsNotLike(String value) {
            addCriterion("account_ids not like", value, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsIn(List<String> values) {
            addCriterion("account_ids in", values, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsNotIn(List<String> values) {
            addCriterion("account_ids not in", values, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsBetween(String value1, String value2) {
            addCriterion("account_ids between", value1, value2, "accountIds");
            return (Criteria) this;
        }

        public Criteria andAccountIdsNotBetween(String value1, String value2) {
            addCriterion("account_ids not between", value1, value2, "accountIds");
            return (Criteria) this;
        }

        public Criteria andIdsCountIsNull() {
            addCriterion("ids_count is null");
            return (Criteria) this;
        }

        public Criteria andIdsCountIsNotNull() {
            addCriterion("ids_count is not null");
            return (Criteria) this;
        }

        public Criteria andIdsCountEqualTo(Integer value) {
            addCriterion("ids_count =", value, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountNotEqualTo(Integer value) {
            addCriterion("ids_count <>", value, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountGreaterThan(Integer value) {
            addCriterion("ids_count >", value, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("ids_count >=", value, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountLessThan(Integer value) {
            addCriterion("ids_count <", value, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountLessThanOrEqualTo(Integer value) {
            addCriterion("ids_count <=", value, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountIn(List<Integer> values) {
            addCriterion("ids_count in", values, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountNotIn(List<Integer> values) {
            addCriterion("ids_count not in", values, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountBetween(Integer value1, Integer value2) {
            addCriterion("ids_count between", value1, value2, "idsCount");
            return (Criteria) this;
        }

        public Criteria andIdsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("ids_count not between", value1, value2, "idsCount");
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