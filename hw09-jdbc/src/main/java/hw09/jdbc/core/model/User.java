package hw09.jdbc.core.model;

import hw09.jdbc.jdbc.mapper.annotations.Id;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class User {
    @Id
    private final long id;
    private final String name;
    private int age;

    public User(){
        this.id = -1;
        this.name = new String();
        this.age = -1;
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        this.age = -1;
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
            "id = " + getId() +
            ", name = " + getName() +
            ", age = " + getAge() +
            "}";
    }

}
