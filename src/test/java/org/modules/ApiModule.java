package org.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.api.clients.WishlistClient;

/**
 * DI-модуль для API-тестов.
 */
public class ApiModule extends AbstractModule {

    private final RequestSpecification requestSpec;

    public ApiModule(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    // Клиент с токеном
    @Provides
    @Singleton
    public WishlistClient getWishlistClient() {
        return new WishlistClient(requestSpec);
    }

    // Спецификация без токена (для негативных тестов)
    @Provides
    @Singleton
    @Named("noAuth")
    public RequestSpecification getNoAuthSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(System.getProperty("api.url"))
                .setContentType("application/json")
                .build();
    }

    // Клиент без токена
    @Provides
    @Singleton
    @Named("noAuth")
    public WishlistClient getNoAuthWishlistClient(@Named("noAuth") RequestSpecification noAuthSpec) {
        return new WishlistClient(noAuthSpec);
    }
}