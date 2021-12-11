package com.github.henryx.qubu;

public enum JoinType {
    NO(""),
    CROSS("CROSS"),
    INNER("INNER"),
    NATURAL("NATURAL"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    LEFTOUTER("LEFT OUTER"),
    RIGHTOUTER("RIGHT OUTER"),
    FULLOUTER("FULL OUTER")
    ;

    private final String joinType;

    JoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getJoinType() {
        return joinType;
    }
}
