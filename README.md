# Календарь на Java \+ Spring Boot \+ Thymeleaf

## О проекте
Веб‑приложение для генерации и просмотра календарей по указанному году. Реализовано на Java 17 с использованием Spring Boot, Thymeleaf и PostgreSQL. Поддерживается аутентификация через OIDC (Keycloak), роли пользователя (USER / ADMIN), сохранение календарей для пользователей и админский просмотр.

## Технологии
- Java 17
- Spring Boot 3
- Thymeleaf
- Spring Security (OIDC / Keycloak)
- JPA / Hibernate
- PostgreSQL
- Flyway (миграции БД)
- Maven
- HTML + CSS (чистый, без JavaScript)

## Ключевой функционал
- Ввод года (>= 1600) и генерация календаря на весь год.
- Корректная отрисовка месяцев в сетке (по 7 дней: Пн..Вс). Исправлена логика заполнения недель — пустые ячейки рендерятся, сетка не «съезжает».
- Подсветка выходных (сб, вс).
- Отображение количества уникальных календарей (статическое значение в сервисе — 14).
- Сохранение сгенерированного календаря в базе и привязка к пользователю (уникальность пары username + year).
- Страница «Мои календари» — список сохранённых годов пользователя с возможностью открыть календарь.
- Админский интерфейс:
   - Список всех пользователей (`/admin/users`).
   - Просмотр сохранённых календарей конкретного пользователя (`/admin/users/{username}`).
- Страница профиля пользователя (`/profile`) — вывод полей, получаемых из OIDC:
   - `username`
   - `family_name`
   - `email`
   - `email_verified` (отображается по‑русски: "Почта подтверждена" / "Почта не подтверждена" / "Неизвестно")
   - `exp` (время жизни токена, если есть)
   - Дополнительные claims в таблице.

## URL и шаблоны
- `/` — главная страница с формой ввода года (`index.html`).
- `POST /calendar/generate` — генерирует календарь и показывает страницу `calendar-view.html`.
- `POST /calendar/save` — сохраняет связь пользователя с годом и перенаправляет на `/calendar/saved`.
- `/calendar/saved` — список сохранённых годов текущего пользователя (`saved-calendars.html`).
- `/profile` — профиль текущего пользователя (`profile.html`).
- `/admin/users` — список пользователей (требуется роль `ADMIN`).
- `/admin/users/{username}` — календари выбранного пользователя.

## Реализация календаря (важные детали)
- Генерация календаря происходит в `CalendarServiceImpl.generateCalendarYear(int year)`.
- Для каждого месяца вычисляется количество недель и создаются объекты:
   - `CalendarYear` → содержит `List<CalendarMonth>`
   - `CalendarMonth` → содержит `List<CalendarWeek>`
   - `CalendarWeek` → содержит `List<CalendarDay>`
- Каждая неделя хранит только реальные дни месяца (без пустых placeholder'ов). Для рендеринга шаблон использует метод `CalendarWeek.getDayAtPosition(int)` (позиции 1..7 — Пн..Вс). Это гарантирует ровную таблицу: шаблон всегда рендерит 7 ячеек на неделю и подставляет пустые ячейки там, где день отсутствует.
- `CalendarDay.dayOfWeek` сохраняется как строка (например, `MONDAY`), `isWeekend` — булево значение.

## База данных и миграции
Файлы миграций в `src/main/resources/db/migration`:
- `V1__create_calendar_tables.sql` — таблицы `calendar_year`, `calendar_month`, `calendar_week`, `calendar_day`.
- `V2__create_user_calendar_links.sql` — таблица `user_calendar_links` (username + calendar_year_id уникальны).

Структура таблиц:
- `calendar_year (id, year)`
- `calendar_month (id, month_number, calendar_year_id)`
- `calendar_week (id, week_number, calendar_month_id)`
- `calendar_day (id, day_of_month, day_of_week, is_weekend, calendar_week_id)`
- `user_calendar_links (id, username, calendar_year_id)`

## Безопасность / Keycloak
Конфигурация OAuth2/OIDC настроена в `application.yaml`.  
Пользователи и роли подставляются из OIDC claims (`realm_access.roles`). В `SecurityConfig` роли мапятся в `GrantedAuthority`. Путь `/admin/**` требует роль `ROLE_ADMIN`, остальные страницы — `ROLE_USER` или `ROLE_ADMIN`.

## Как запустить
1. Установить JDK 17 и Maven.
2. Поднять PostgreSQL и создать БД/пользователя, указанные в `application.yaml` (по умолчанию порт `5434`, имя `calendar_db`, пользователь `calendar_user`).
3. Настроить Keycloak (или изменить `application.yaml` для локальной работы без OIDC).
4. Склонировать репозиторий:
