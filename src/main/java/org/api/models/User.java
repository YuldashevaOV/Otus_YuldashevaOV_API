package org.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Модель пользователя
 * Ответы, Десериализация
 */

@Data
@NoArgsConstructor
public class User {
    private UUID id; // Уникальный идентификатор пользователя
    private String username; // Имя пользователя (логин)
    private String email; // Email пользователя
}
