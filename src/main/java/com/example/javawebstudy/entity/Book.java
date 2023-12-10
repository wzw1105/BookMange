package com.example.javawebstudy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Book {
    int bid;
    String name;
    Date date;
    int remainQuantity;
    int totalQuantity;
}
