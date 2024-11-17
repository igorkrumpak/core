package si.iitech.util;

public class StringUtils {

    public static boolean exists(String value) {
        return value != null && !value.isEmpty();
    }
    
    public static boolean equals(String value1, String value2) {
    	if (value1 == null && value2 == null) return true;
    	if (value1 == null && value2 != null) return false;
    	if (value1 != null && value2 == null) return false;
        return value1.contentEquals(value2);
    }

  
}
