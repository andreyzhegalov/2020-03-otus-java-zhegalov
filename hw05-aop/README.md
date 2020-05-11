[![codecov](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov/branch/feature/hw05-aop/graph/badge.svg)](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov)

# Автоматическое логирование
**Цель**
Понять как реализуется AOP, какие для этого есть технические средства.

**Требования**
Разработать такой функционал, который позволит:
1. Пометить метод класса самодельной аннотацией @Log, например, так:
```java
class TestLogging {
@Log
public void calculation(int param) {};
}
```

2. При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
Например так.
```java
class Demo {
public void action() {
new TestLogging().calculation(6);
}
}
````

В консоле должно быть:
````
executed method: calculation, param: 6
````
3. **Явного вызова логирования быть не должно.**

### Использование
Для тестирования и представления результатов работы проект содержит тестовый класс [```hw05.aop.ClassForTestingLog```](https://github.com/andreyzhegalov/2020-03-otus-java-zhegalov/blob/feature/hw05-aop/hw05-aop/src/main/java/hw05/aop/ClassForTestingLog.java)
Реализация агента и тестовый класс для находятся в одном итоговом jar архиве (для простоты использования).
Для получения результата достаточно выполнить:
```bash
gradle build
java -javaagent:hw05-aop/build/libs/testLogAnnotation.jar -jar hw05-aop/build/libs/testLogAnnotation.jar
````
##### Дополнительно
Для разработки, отладки использовалось вспомогательное приложение. Приложение позволяет валидировать вносимые изменения с помощью ASM утилиты [CheckClassAdapter](https://asm.ow2.io/javadoc/org/objectweb/asm/util/CheckClassAdapter.html), и выводить список JVM команд.
Для запуска необходимо выполнить
```bash
java -javaagent:hw05-aop/build/libs/testLogAnnotation.jar -jar hw05-aop/build/libs/testLogAnnotation.jar
```
