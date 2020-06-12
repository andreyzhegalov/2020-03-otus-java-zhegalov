package hw09.jdbc.jdbc.mapper.testingclasses;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class CommonClass {
    @Id
    private int intId;
    private String strField ;
    private int intField;

    public CommonClass(){
    }

    public CommonClass(int intId, String strField, int intField) {
        this.intId = intId;
        this.strField = strField;
        this.intField = intField;
    }
}
