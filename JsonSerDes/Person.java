package com.JawaWeb.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Person {
    private String name;
    private Integer age;
    private List<String> arr = new ArrayList<>();

    public Person(String name, Integer age, List<String> arr) {
        this.name = name;
        this.age = age;
        this.arr = arr;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public List<String> getArr() {
        return arr;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("arr=" + arr)
                .toString();
    }
}
