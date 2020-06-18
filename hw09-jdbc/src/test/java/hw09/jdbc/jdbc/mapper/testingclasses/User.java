package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class User {
    @Id
    private long userId;
    private String userName;
    private int userAge;

    public User(){
    }

    public User(long id, String name, int age) {
        this.userId = id;
        this.userName = name;
        this.userAge  = age;
    }

    public long getUserId()
    {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int age){
        this.userAge = age;
    }
}
