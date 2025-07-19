# synthetic-human-core-starter

## Описание проекта

В этом репозитории находится проект [synthetic-human-core-starter](https://github.com/mikl14/T1_HW_2/tree/main/synthetic-human-core-starter) — исходный код стартера, который подключается в проект [bishop-prototype](https://github.com/mikl14/T1_HW_2/tree/main/bishop-prototype), находящийся в том же репозитории.

## Быстрый запуск с Docker Compose

В репозитории есть файл `docker-compose.yml`, с помощью которого можно быстро развернуть окружение и проверить работу проекта.
### Однако
Для быстрого запуска вам понадобиться доступ к удаленному пакету [ru.homework.synthetic-human-core-starter](https://github.com/mikl14/T1_HW_2/packages/2586266).
Так как он загруженн на гитхаб вам придеться вставить в build.gradle ваш логин и токен для чтения.

## В случае если у вас нету Github аккаунта
воспользуйтесь, MVNLocal

# Структура  [synthetic-human-core-starter](https://github.com/mikl14/T1_HW_2/tree/main/synthetic-human-core-starter)

Проект содержит классы

Command - класс определяющий объект команды, в него команда переданная по REST десериализуется чере objectMapper, Имеет связанный enum для типов команд
DroidRestController - Абстрактный класс для наследования рест контроллеров, имеет метод валидации команд и метод отправляющий команду в работу
DroidService - Абстрактный класс, содержит логику обработки команд
WeylandWatchingYou - аннотация для логирования методов
WeylandAspect - аспект с логикой работы аннотации
