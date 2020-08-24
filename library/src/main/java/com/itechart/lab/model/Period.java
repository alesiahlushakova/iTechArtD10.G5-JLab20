package com.itechart.lab.model;

public enum Period {
    ONE(1),
    TWO(2),
    THREE(3),
    SIX(6),
    TWELVE(12);

    public int label;
    private Period(int label){
        this.label = label;
    }

}
