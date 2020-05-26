[![codecov](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov/branch/feature/hw07-atm-department/graph/badge.svg)](https://codecov.io/gh/andreyzhegalov/2020-03-otus-java-zhegalov)

# Департамент ATM

**Цель**
Написать приложение ATM Департамент:
1. Департамент может содержать несколько ATM.
2. Департамент может собирать сумму остатков со всех ATM.
3. Департамент может инициировать событие – восстановить состояние всех ATM до начального (начальные состояния у разных ATM могут быть разными).

**Требования**
1. Применить на практике шаблоны проектирования.
2. Исправить ошибки проектирования, допущенные в предыдущей работе.
3. Использвать максиммальное количество паттернов.

**Результат**
В решении были применены следующие шаблоны:
1. *Command* - для представления действий департамента с ATM (GetBalanceCommand, SaveCurrentStateCommand, RestoreLastStateCommand).
2. *Observer* - для централизованной передачи команд подписанным ATM (AtmObserver).
3. *Chain of responsibility* - для обхода по подписчикам в observer (AtmChainHandler).
4. *Momento* - для сохранения состояния менеджера ячеек в ATM (CellManagerMomento).
5. *Prototype* - для создания копии ячеек при создании снимка (CellPrototype).
6. *Strategy* - для выбора алгоримта работы с ячейками в CellManager (CellStategy). Реализована только CellStrategy1 с логикой из предыдущего задания.
