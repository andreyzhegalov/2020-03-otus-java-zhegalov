package hw09.jdbc.jdbc.dao;

import java.util.Optional;

import hw09.jdbc.core.dao.UserDao;
import hw09.jdbc.core.model.User;
import hw09.jdbc.core.sessionmanager.SessionManager;

public class UserDaoMapperJdbc implements UserDao {

	@Override
	public Optional<User> findById(long id) {
        throw new UnsupportedOperationException();
	}

	@Override
	public long insertUser(User user) {
        throw new UnsupportedOperationException();
	}

	@Override
	public SessionManager getSessionManager() {
        throw new UnsupportedOperationException();
	}
}

