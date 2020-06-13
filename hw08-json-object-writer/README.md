[![codecov](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov/branch/feature/hw08-json-object-writer/graph/badge.svg)](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov)

#  Json object writer
**Цель**
1. Научиться сериализовывать объект в json.
2. Написать свой json object writer (object to JSON string) аналогичный gson на основе javax.json.
3. Попрактиковаться в разборе структуры объекта.

**Требование**
Json object writer должен поддерживать сериализацию:
- примитивных типов
- массивов примитивных типов
- коллекций (interface Collection)
- null

**Пояснение**
Gson это делает так:
```java
Gson gson = new Gson();
AnyObject obj = new AnyObject(22, "test", 10);
String json = gson.toJson(obj);
```
Сделайте так:
```java
MyGson gson = new MyGson();
AnyObject obj = new AnyObject(22, "test", 10);
String myJson = gson.toJson(obj);
```

Должно получиться:
````java
AnyObject obj2 = gson.fromJson(myJson, AnyObject.class);
System.out.println(obj.equals(obj2));
````

