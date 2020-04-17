[![codecov](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov/branch/feature/hw02-myarraylist/graph/badge.svg)](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov)

# DIY ArrayList
**Цель:**
Изучить как устроена стандартная коллекция ArrayList. Попрактиковаться в создании своей коллекции.
Написать свою реализацию ArrayList на основе массива
```java
class DIYarrayList<T> implements List<T>{...}
```
**Требования:**
1. Проверить, что на ней работают методы из java.util.Collections:
    a. ```Collections.addAll(Collection<? super T> c, T... elements)```
    b. ```Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)```
    c. ```Collections.static <T> void sort(List<T> list, Comparator<? super T> c)```
2. Проверяйте на коллекциях с 20 и больше элементами.
3. DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
4. Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.
