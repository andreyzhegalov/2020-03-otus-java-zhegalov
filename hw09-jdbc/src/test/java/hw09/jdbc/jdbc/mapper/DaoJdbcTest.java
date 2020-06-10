package hw09.jdbc.jdbc.mapper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.mapper.testingclasses.CommonClass;
import hw09.jdbc.jdbc.sessionmanager.DatabaseSessionJdbc;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJdbcTest {
    @Test
    public void testInsert() throws SQLException {
        final DbExecutorImpl<CommonClass> mockedDbExecuter = Mockito.mock(DbExecutorImpl.class);
        Mockito.when(mockedDbExecuter.executeInsert(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1L);

        final DatabaseSessionJdbc mockedDbSessionJdbc = Mockito.mock(DatabaseSessionJdbc.class);

        final SessionManagerJdbc mockedSessionManager = Mockito.mock(SessionManagerJdbc.class);
        Mockito.when(mockedSessionManager.getCurrentSession()).thenReturn(mockedDbSessionJdbc);

        final JdbcMapper<CommonClass> daoJdbc = new DaoJbdc<>(mockedDbExecuter, mockedSessionManager);
        daoJdbc.insert(new CommonClass());
        Mockito.verify(mockedDbExecuter, Mockito.times(1)).executeInsert(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testUpate() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>(null, null).update(null));
    }

    @Test
    public void testInsertOrUpdate() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>(null, null).insertOrUpdate(null));
    }

    @Test
    public void testFindById() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>(null, null).findById(0, null));
    }
}
