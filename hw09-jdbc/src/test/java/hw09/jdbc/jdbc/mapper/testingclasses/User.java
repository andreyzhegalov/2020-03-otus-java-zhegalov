package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class User {
    @Id
    private long id;
    private String name;
    private int age;

    public User(){
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age  = age;
    }

    public long getId()
    {
        return id;
    }
}
