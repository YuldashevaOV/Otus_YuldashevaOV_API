package org.extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.api.dto.LoginDto;
import org.api.models.LoginResponse;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.modules.ApiModule;

import static io.restassured.RestAssured.given;

public class ApiExtension implements BeforeEachCallback {

    private static RequestSpecification requestSpec;

    @Override
    public void beforeEach(ExtensionContext context) {
        // Получение токена (один раз за все тесты)
        if (requestSpec == null) {
            requestSpec = buildRequestSpec();
        }

        // Создание Injector (передаем requestSpec)
        Injector injector = Guice.createInjector(new ApiModule(requestSpec));
        injector.injectMembers(context.getRequiredTestInstance());
    }

    /**
     * RequestSpecification с токеном авторизации.
     */
    private RequestSpecification buildRequestSpec() {
        String baseUrl = System.getProperty("api.url");
        String login = System.getProperty("login");
        String password = System.getProperty("password");

        if (baseUrl == null || login == null || password == null) {
            throw new IllegalStateException("Не переданы api.url, login или password!");
        }

        // Получение токена
        LoginResponse loginResponse = given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(new LoginDto(login, password))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        // Спецификация
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + loginResponse.getToken())
                .log(LogDetail.ALL)
                .build();
    }
}
