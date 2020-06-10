package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class CommonClass {
    @Id
    private final int id;
    private final String strField ;
    private final int intField;

    public CommonClass(int id, String strField, int intField) {
        this.id = id;
        this.strField = strField;
        this.intField = intField;
    }
}
