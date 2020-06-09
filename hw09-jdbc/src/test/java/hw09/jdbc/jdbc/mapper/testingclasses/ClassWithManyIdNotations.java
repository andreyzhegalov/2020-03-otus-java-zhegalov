package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class ClassWithManyIdNotations {
    @Id
    final int field1 = 1;
    @Id
    final int field2 = 2;
}

