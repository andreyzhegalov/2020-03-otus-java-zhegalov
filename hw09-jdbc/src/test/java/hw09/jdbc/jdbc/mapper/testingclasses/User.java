package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class User {
    @Id
    private int id = 1;
    private String name = "user";
    private int age = 1;
}


