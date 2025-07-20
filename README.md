#Домашнее задание №3
# synthetic-human-core-starter

## Описание

**synthetic-human-core-starter** — стартер разработанный согласно ТЗ в ДЗ3

## Содержание

- [Описание](#описание)
- [Быстрый запуск](#быстрый-запуск)
- [Требования](#требования)
- [Структура проекта](#структура-проекта)
  - [synthetic-human-core-starter](#synthetic-human-core-starter-модуль)
  - [bishop-prototype](#bishop-prototype-модуль)

## Быстрый запуск

1. В директории присутствует файл `docker-compose.yml`.
2. Выполните команду для запуска окружения: docker-compose up --build

3. Для доступа к удалённому пакету [ru.homework.synthetic-human-core-starter](https://github.com/mikl14/T1_HW_2/packages/2586266) потребуется:
- ваш логин на GitHub
- персональный токен, добавленный в `build.gradle`

**Важно!** Если у вас нет аккаунта GitHub, используйте локальный вариант установки через MVNLocal.

## Требования

- Docker и docker-compose
- Java и Gradle (для ручной сборки или работы с зависимостями)
- GitHub-аккаунт и токен (если используете удалённый пакет)

## Структура проекта

### synthetic-human-core-starter

Основные компоненты:

- **Command** — класс команды, в который десериализуется команда из REST-запроса (связан с enum типов команд)
- **DroidRestController** — абстрактный родительский класс REST-контроллеров (валидирует и отправляет команду в обработку)
- **DroidService** — абстрактный сервис для логики обработки входящих команд
- **WeylandWatchingYou** — аннотация для логирования методов
- **WeylandAspect** — аспект, реализующий поведение логирования с помощью аннотации

### bishop-prototype модуль

- **Bishop** — наследует `DroidService`, переопределяя некоторые методы базового класса
- **RestController** — наследует `DroidRestController`

---
