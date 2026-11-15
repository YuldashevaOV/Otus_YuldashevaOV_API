package org.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) для создания/обновления списка желаний.
 * Для отправки JSON в теле POST/PUT запросов.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private String title; // Обязательное поле
    private String description; // Опциональное поле
}
