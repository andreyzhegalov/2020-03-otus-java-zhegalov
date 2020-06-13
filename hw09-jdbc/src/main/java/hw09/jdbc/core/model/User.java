package hw09.jdbc.core.model;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class User {
    private final long id;
    private final String name;

    public User(){
        this.id = 0;
        this.name = new String();
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
