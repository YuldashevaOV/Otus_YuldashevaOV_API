markdown

# Домашнее задание "Rest-assured"
Юлдашева О.В.

## Описание
Окружение: https://api.wishlist.otus.kartushin.su

Проект реализован с использованием:
* Java 21
* Junit 5
* Google Guice (DI)
* Maven
* Rest Assured

## Реализованные сценарии
1.  **Сценарий 1:** Списки желаний.
2.  **Сценарий 2:** Подарки.
3.  **Сценарий 3:** Пользователи.

## Как запустить тесты
### 1. Запуск всех тестов:
```bash
mvn clean test
```
### 2. Запуск Сценарий 1 (Списки желаний):
```bash
mvn clean test -Dtest=WishlistTest
```

## Дополнительно
### Проверка стиля кода (Checkstyle):
```bash
mvn checkstyle:check
```
### Поиск багов (SpotBugs):
```bash
mvn spotbugs:check
```