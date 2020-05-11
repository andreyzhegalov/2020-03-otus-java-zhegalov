[![codecov](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov/branch/feature/hw05-aop/graph/badge.svg)](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov)

# Автоматическое логирование.
**Цель**
Понять как реализуется AOP, какие для этого есть технические средства.

**Требования**
Разработать такой функционал, который позвлолит:
1. пометить метод класса самодельной аннотацией @Log, например, так:
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

В консоле дожно быть:
````
executed method: calculation, param: 6
````
3. **Явного вызова логирования быть не должно.**

# Использование
Для тестирования и представления результатов работы проект содержит тестовый класс ```hw05.aop.ClassForTestingLog```
Реализация агента и тестовый класс для находятся в одном jar архиве (для простоты использования).
Для получения результата достаточно выполнить:
```bash
java -javaagent:testLogAnnotation.jar -jar testLogAnnotation.jar
````
Также проект содержит отладочное приложение, валидирующее вносимые изменнения с помощью ASM утилиты [CheckClassAdapter](https://asm.ow2.io/javadoc/org/objectweb/asm/util/CheckClassAdapter.html).
Для запуска необходимо выполнить 
```bash
java -jar asmTester.jar <path to ClassForTestingLog.class>
```
