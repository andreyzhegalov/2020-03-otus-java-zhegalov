package hw09.jdbc.jdbc.mapper;
import com.google.common.base.CaseFormat;

public class NameConverterHelper {
    public static String toLowerUnderScore(String in) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, in);
    }
}

