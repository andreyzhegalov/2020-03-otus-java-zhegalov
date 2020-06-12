package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class CommonClass {
    @Id
    private int id;
    private String strField ;
    private int intField;

    public CommonClass(){
    }

    public CommonClass(int id, String strField, int intField) {
        this.id = id;
        this.strField = strField;
        this.intField = intField;
    }
}
