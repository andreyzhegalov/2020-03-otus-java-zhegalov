package hw16.dto;

import hw16.core.model.User;

public class UserSecureDto extends UserDto {
    private final String password;

    @Override
    public String toString() {
        return "UserSecureDto{" +
            "password = " + getPassword() +
            "}";
    }


    public UserSecureDto(User user) {
        super(user);
        this.password = user.getPassword();
    }

    public String getPassword() {
        return password;
    }

    public User toUser(){
        final var user = new User();
        user.setId(super.getId());
        user.setName(super.getName());
        user.setPassword(this.password);
        return user;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSecureDto object = (UserSecureDto) o;

        return !(password != null ? !password.equals(object.password) : object.password != null);
    }
}
