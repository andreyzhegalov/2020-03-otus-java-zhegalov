# Сравнение разных сборщиков мусора
**Цель**
На примере простого приложения понять какое влияние оказывают сборщики мусора Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.

**Требования**
1. Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).
2. Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.
3. Собрать статистику (количество сборок, время на сборки) по разным GC.
4.  **Сделать вывод** какой gc лучше и почему?
5. Выводы оформить в файле Сonclusions.md в корне папки проекта. Результаты измерений сведите в таблицу.
