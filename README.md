# Loan-processing-service
Restful service that allows to take a loan, and make a decision for orders.

##Stack
- Java 17
- Spring(Web, JDBC template, Cloud...)
- PostgresSQL
- Liquibase
- Util: Docker, Zipkin
***
##Instruction for starting service
>   Весь сервис состоит из 4х микросервисов. Три из них являются вспомогательным, а 4й
 (Loan-service) несет в себе бизнес логику.
1. Первым запускается config-server, содержащий конфиг проперти для работы сервиса. _Работает на порту 8888._
2. Следующим должен запускаться discovery server, который отвечает за показ аналитики eureka
о работе сервиса. _Работает на порту 8761._
3. Если на машине не предустановлен postgre, то можно перейти в консоли в  директорию с compose файлом 
**Loan-service/docker/docker-compose.yml** и выполнить docker compose up для полнятия контейнера. 
Также в данном контейнере будет содержаться Zipkin (работающий на порту 9411). Перейдя в браузере по данному порту, можно будет
аналитику по всем запросам ко всем endpoint.
>Данный пункт не является обязательным и требует наличия на машине Docker.
4. Запускается Loan-service. В нем содержится вся бизнес логика сервиса. 
_Работает на порту 8081._
5. Запуск GateWay на порту 8222. Служит для унификации все endpoint на один порт 
(сейчас поддреживает только Loan-service).
>Является необязательным к запуску.
***
##Documentation Swagger.
Документацию к Loan-service можно найти _Loan-service/doc-swagger.json_
***
Перейдя в Git  в ветку documentation_branch. Запустив сервис, можно перейти по _localhost8081:/loan-order-doc_ и протестировать 
функционал Loan-service.
