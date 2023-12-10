package com.example.javawebstudy.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    int bid;
    Student student;
    Book book;
    Date borrowTime; // 借书时间
    Date dueTime; // 应还书时间
}
