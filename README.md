Биржа для проведения торгов криптовалютами
=====


Описание проекта
----------
Сервис является RESTfull API приложением, которое представляет 
собой упрощенный вариант биржи для проведения торгов криптовалютами.

Обязательные функциональные требования
----------

- Spring Boot;

- Возвращаемые данные в формате JSON;

### Сервис позволяет выполнить (минимальные требования)

#### Возможности для пользователя:

- Зарегистрироваться;
- Посмотреть баланс своего кошелька;
- Пополнить кошелек;
- Вывести деньги с биржи;
- Посмотреть актуальные курсы валют;
- Обменять валюты по установленному курсу;

#### Возможности для администратора:

- Изменить курсы валют;
- Посмотреть общую сумму на всех счетах для указанной валюты
- Посмотреть количество операций, которые были проведены за указанный период
- Посмотреть актуальные курсы валют

### Дополнительные требования, которые были реализованы:

- подключение базы данных PostgreSQL для хранения данных о балансе пользовательских кошельков и истории операций;

- формат может быть изменен добавлением header "accept" к HTTP-запросу

`accept:application/json или accept:application/xml 
`
- подключен и настроен swagger
- пополнение кошелька пользователя может происходить любой валютой
- в приложение ведется логгирование

Системные требования
----------

* JDK 17
* Apache Maven
* PostreSQL
* Любое средство для развертывания базы данных PostgreSQL

Стек технологий
----------

* Java 17
* Spring Boot 3
* Apache Maven
* Jackson
* Hibernate
* PostreSQL
* OpenApi 2

Установка проекта из репозитория 
----------
1. Локально развернуть PostgreSQL и в нем создать базу данных

2. Клонировать репозиторий и перейти в него

3. Открыть файл ```application.propetries``` и изменить следующие строчки
на данные, соответствующие вашей базе:
```properties 
spring.datasource.url=jdbc:postgresql://localhost:5432/Crypto
spring.datasource.username=root
spring.datasource.password=root
```

4. Открыть командную строку в директории проекта и запустить проект
введя следующую команду:
```
mvn spring-boot:run
```
5. Если потребуются тестовые данные в базу, запустить скрипт
```
src/main/resources/static/script.sql
```
Если запускаете приложение без предложенных тестовых данных необходимо в базе данных
в таблицу "Administrator" добавить запись с UUID, который будет являться secret_key
для администраторов приложения.  
Для примера можете взять этот: 9cdd51fe-d2ff-4d1e-a602-1a4ac19b1cb1

## Endpoints

Все Endpoints представлены без `localhost:8090`

## Пользователь
### Регистрация нового пользователя

`POST /logUp`

#### Тело запроса:


```
{
    "username": "vasya_vezunchik",
    "email": "vasyu_kolbasit@mail.ru"
}
```    
#### Ответ:

```
{
    secret_key : AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA
}
``` 

#### Пример запроса:

![image](https://github.com/saved2223/CryptoTrading/blob/master/images/request.png)


#### Пример ответа:

##### XML

![image](https://github.com/saved2223/CryptoTrading/blob/master/images/responseXml.png)

##### JSON

![image](https://github.com/saved2223/CryptoTrading/blob/master/images/responseJson.png)

> Выбор формата ответа(JSON or XML):

Для выбора формата ответа достаточно выбрать Header `accept` или изменить его значение.

- Для JSON: `application/json`

- Для XML: `application/xml`




### Просмотр баланса своего кошелька.

`GET /walletBalance`

#### Параметры:

```
{
	"secret_key": "AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA"
}
```

#### Ответ:

```
{
	"BTC_wallet": "0.0031589",
	"TON_wallet": "254.87",
	"RUB_wallet": "3500"
}
```

### Пополнение кошелька.

`POST /topUpWallet`

#### Тело запроса:

```
{
	"secret_key": "AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA",
	"RUB_wallet": "1000"
}
```

#### Ответ:
```
{
	"RUB_wallet": "4500"
}
```

### Вывод денег с биржи.

`POST /withdrawMoney`

#### Тело запроса:

```
{
	"secret_key": "AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA",
	"currency": "RUB",
	"count": "1500",
	"credit_card": "1234 5678 9012 3456"
}
```

#### Ответ:

```
{
	"RUB_wallet": "3000"
}
```


### Просмотр актуальных курсов валют 
данный запрос доступен и пользователю и администратору.

`GET /currencyRates`

#### Параметры:

```
{
	"secret_key": "AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA",
	"currency": "TON"
}
```

#### Ответ:

```
{
	"BTC": "0.00009564",
	"RUB": "180"
}
```


### Обмен валют по установленному курсу.

`POST /exchangeCurrency`

#### Тело запроса:

```
{
	"secret_key": "AAFeyWzOnlD-9G4i662PdKn2B-b4BwrCNA",
	"currency_from": "RUB",
	"currency_to": "TON",
	"amoun": "2000"
}
```

#### Ответ:

```
{
	"currency_from": "RUB",
	"currency_to": "TON",
	"amount_from": "2000",
	"amount_to": "11.11"	
}
```


## Администратор
### Изменить курс валют.

`POST /admin/changeRate`

#### Тело запроса:

```
{
	"secret_key": "1071daaabf1cda35d207030c898d07ff16c934b7"
	"base_currency": "TON",
	"BTC": "0.000096",
	"RUB": "184"
}
```

#### Ответ:

```
{
	"BTC": "0.000096",
	"RUB": "184"
}
```


### Посмотреть общую сумму на всех пользовательских счетах для указанной валюты.

`GET /admin/totalAmountOfCurrency`

#### Параметры:

```
{
	"secret_key": "1071daaabf1cda35d207030c898d07ff16c934b7",
	"currency": "RUB"
}
```

#### Ответ:

```
{
	"RUB": "15006813",
}
```


### Посмотреть количество операций, которые были проведены за указанный период.

`GET /admin/transactions`

#### Параметры:

```
{
	"secret_key": "1071daaabf1cda35d207030c898d07ff16c934b7"
	"date_from": "18.02.2023",
	"date_to": "19.02.2023"
}
```

#### Ответ:

```
{
	"transaction_count": "154"
}
```


Документация к проекту
----------
Документация для API после установки доступна по адресу:

```localHost:8090/swagger```

[Видео с демонстрацией работы](https://drive.google.com/file/d/11uo1WQ-eysJRTLalKtlojNe1JghlchIr/view?usp=sharing)
