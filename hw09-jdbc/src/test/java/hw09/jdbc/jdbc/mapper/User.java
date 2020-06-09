package hw09.jdbc.jdbc.mapper;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class User {
    @Id
    private int id = 0;
    private String name = new String();
    private int age = 0;

    public User(){
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}


