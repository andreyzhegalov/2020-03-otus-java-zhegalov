package hw16.dto;

import java.util.List;
import ru.otus.messagesystem.client.ResultDataType;

public class UserListDto extends ResultDataType{
    final private List<UserDto> userList;

    public UserListDto(List<UserDto> userList){
        this.userList = userList;
    }

    public List<UserDto> getUserList() {
        return userList;
    }
}

