# Inside

## Описание
- В БД создать пару sql табличек со связями (**foreign keys**)

- Сделать **HTTP POST** эндпоинт который получает данные в **json** вида : \
{ \
    name: "имя отправителя" \
    password: "пароль" \
} 

- Этот эндпоинт проверяет пароль по БД и создает **jwt токен** (срок действия токена и алгоритм подписи не принципиален, 
для генерации и работе с токеном можно использовать готовую библиотечку) \
В токен записывает данные: name: "имя отправителя" 
и отправляет токен в ответ, тоже **json** вида: \
{ \
    token: "тут сгенерированный токен" \
}

- Сервер слушает и отвечает в какой-нибудь эндпоинт в него на вход поступают данные в формате **json**: \
Сообщения клиента-пользователя: \
{ \
    name:       "имя отправителя", \
    message:    "текст сообщение" \
}

- В заголовках указан **Bearer** токен (полученный из эндпоинта выше) \
Проверить токен, в случае успешной проверки токена, полученное сообщение сохранить в БД.

Если пришло сообщение вида: \
{ \
    name:       "имя отправителя", \
    message:    "history 10" \
}

- Проверить токен, в случае успешной проверки токена отправить отправителю 10 последних сообщений из БД

- Добавить описание и инструкцию по запуску и комментарии в коде, если изменяете формат сообщений, то подробное описание ендпоинтов и их полей.

- Завернуть все компоненты в докер

## Инструкция по запуску
### Ввести следующие команды в папке с проектом:
- docker build . -t
- docker run -p 8080:8080 -t insidetest
### После старта приложения используя HTTP клиент Postman(к примеру):
- Отправляем POST запрос на адрес localhost:8080/user/create для создания юзера в БД с body в следующем формате: \
{ \
    name: "имя отправителя" \
    password: "пароль" \
}
- Отправляем POST запрос на адрес localhost:8080/user/login с body как в предыдущем запросе для авторизации созданного юзера. 
Получаем в ответ JWT токен.
- Отправляем POST запрос на адрес localhost:8080/user/message/create для создания Сообщения, 
создаем header Authorization c **Bearer** токен (полученный из эндпоинта выше, например: Bearer eyJhbGciOiJIUzUxMiJ9BNo4BdNe3rRxTlPNVgjMq892-i_ACcjQtq2g) \
 с body в следующем формате: \
{ \
    name:       "имя отправителя", \
    message:    "текст сообщение" \
}
- Для получения 10-ти последних сообщений из БД отправляем POST запрос на адрес localhost:8080/user/message/create с body в следующем формате:
{ \
    name:       "имя отправителя", \
    message:    "history 10" \
}
