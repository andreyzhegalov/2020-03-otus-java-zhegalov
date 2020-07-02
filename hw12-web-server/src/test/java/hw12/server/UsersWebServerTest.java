package hw12.server;

import static hw12.server.utils.HttpUrlConnectionHelper.buildUrl;
import static hw12.server.utils.HttpUrlConnectionHelper.sendRequest;
import static hw12.server.utils.WebServerHelper.COOKIE_HEADER;
import static hw12.server.utils.WebServerHelper.login;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;

import org.eclipse.jetty.http.HttpMethod;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hw12.core.service.DBServiceUser;
import hw12.core.service.DbServiceUserImpl;
import hw12.services.TemplateProcessor;
import hw12.services.UserAuthService;

public class UsersWebServerTest {
    private static final int WEB_SERVER_PORT = 8989;
    private static final String WEB_SERVER_URL = "http://localhost:" + WEB_SERVER_PORT + "/";
    private static final String LOGIN_URL = "login";
    private static final String ADMIN_URL = "admin";

    private static final String DEFAULT_USER_LOGIN = "admin";
    private static final String DEFAULT_USER_PASSWORD = "11111";
    private static final String INCORRECT_USER_LOGIN = "BadUser";

    private static UsersWebServer webServer;

    @BeforeAll
    static void setUp() throws Exception {
        TemplateProcessor templateProcessor = mock(TemplateProcessor.class);
        UserAuthService userAuthService = mock(UserAuthService.class);
        DBServiceUser dbUserService = mock(DbServiceUserImpl.class);

        given(userAuthService.authenticate(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD)).willReturn(true);
        given(userAuthService.authenticate(INCORRECT_USER_LOGIN, DEFAULT_USER_PASSWORD)).willReturn(false);

        webServer = new UsersWebServer(WEB_SERVER_PORT, templateProcessor, userAuthService, dbUserService);
        webServer.start();
    }

    @AfterAll
    static void tearDown() throws Exception {
        webServer.stop();
    }

    @Test
    void shouldReturnJSessionIdWhenLoggingInWithCorrectData() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL, null), DEFAULT_USER_LOGIN,
                DEFAULT_USER_PASSWORD);
        assertThat(jSessionIdCookie).isNotNull();
    }

    @Test
    void shouldNotReturnJSessionIdWhenLoggingInWithIncorrectData() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL, null), INCORRECT_USER_LOGIN,
                DEFAULT_USER_PASSWORD);
        assertThat(jSessionIdCookie).isNull();
    }

    @Test
    void shouldGetAdminPageWhenAuthorized() throws IOException {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL, null), DEFAULT_USER_LOGIN,
                DEFAULT_USER_PASSWORD);

        HttpURLConnection connection = sendRequest(buildUrl(WEB_SERVER_URL, ADMIN_URL, null), HttpMethod.GET);
        connection.setRequestProperty(COOKIE_HEADER,
                String.format("%s=%s", jSessionIdCookie.getName(), jSessionIdCookie.getValue()));
        int responseCode = connection.getResponseCode();

        assertThat(responseCode).isEqualTo(HttpURLConnection.HTTP_OK);
    }
}
