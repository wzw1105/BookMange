package com.example.javawebstudy.entity;

import lombok.Data;

@Data
public class Student {
    int sid;
    String name;
    String grade;
    int borrowQuantity;
    int needReturnQuantity;
}
