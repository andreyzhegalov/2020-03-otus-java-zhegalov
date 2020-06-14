[![codecov](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov/branch/feature/hw10-hibernate/graph/badge.svg)](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov)

# Использование Hibernate
**Цель**
На практике освоить основы Hibernate. Понять как аннотации-hibernate влияют на формирование sql-запросов.

**Требования**
1. Работа должна использовать базу данных H2.
2. За основу предыдущее ДЗ (Самодельный ORM).
3. Используйте предложенный на вебинаре api (пакет core).
4. Реализуйте функционал сохранения и чтения объекта User через Hibernate (Рефлексия больше не нужна).
5. Конфигурация Hibernate должна быть вынесена в файл.
6. Добавьте в User поля:
````
адрес (OneToOne) 
class AddressDataSet {
private String street;
}

и телефон (OneToMany)
class PhoneDataSet {
private String number;
}
`````
7. Разметьте классы таким образом, чтобы при сохранении/чтении объека User каскадно сохранялись/читались вложенные объекты.

# ВАЖНО
1. Hibernate должен создать **только три таблицы**: для телефонов, адресов и пользователей.
2) При сохранении нового объекта **не должно быть update-ов**. Посмотрите в логи и проверьте, что эти два требования выполняются.
