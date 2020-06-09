package hw09.jdbc.jdbc.mapper;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class User {
    @Id
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}


