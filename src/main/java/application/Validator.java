package application;

import java.util.regex.Pattern;

public class Validator {
    private static final String FIELD_VALIDATION = "^[a-zA-Z]*$";
    private static final Pattern EMAIL_VALIDATION = Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");
    private static final Pattern PASSWORD_VALIDATION = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    private static final Pattern DATE_VALIDATION = Pattern.compile("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
            + "|^(((19|20])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
            + "|^(((19|20)[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
            + "|^(((19|20)[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

    public static boolean checkAge(int age) {
        return age >= 18;
    }

    public static boolean checkEmail(String email) {
        return EMAIL_VALIDATION.matcher(email).matches();
    }

    public static boolean checkDate(String date) {
        return DATE_VALIDATION.matcher(date).matches();
    }

    public static boolean checkStrings(String field) {
        return field.matches(FIELD_VALIDATION);
    }

    public static boolean checkDigits(String field) {
        return field.matches("\\d+");
    }

    static boolean checkPassword(String password) {
        return PASSWORD_VALIDATION.matcher(password).matches();
    }
}
