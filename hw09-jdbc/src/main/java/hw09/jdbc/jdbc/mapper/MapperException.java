package hw09.jdbc.jdbc.mapper;

public class MapperException extends RuntimeException {

    public MapperException(Exception ex) {
        super(ex);
    }

    public MapperException(String msg) {
        super(msg);
    }

}

