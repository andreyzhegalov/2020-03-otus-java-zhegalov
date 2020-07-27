package hw14.dto;

import hw14.core.model.User;

public class UserDto {
    private final String name;
    private final String password;

    public UserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public User toUser(){
        final var user = new User();
        user.setName(getName());
        user.setPassword(getPassword());
        return user;
    }
}

