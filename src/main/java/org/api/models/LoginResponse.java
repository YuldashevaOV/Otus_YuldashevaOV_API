package org.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель ответа на логин
 * Содержит токен, который нужно передавать в заголовке Authorization.
 */

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token; // токен для авторизации
}
