package hw12.services;

public class UserAuthServiceImpl implements UserAuthService {


    @Override
    public boolean authenticate(String login, String password) {
        // return userDao.findByLogin(login)
        //         .map(user -> user.getPassword().equals(password))
        //         .orElse(false);
        return false;
    }

}
