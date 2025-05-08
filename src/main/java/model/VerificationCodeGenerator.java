package model;

import java.security.SecureRandom;

public class VerificationCodeGenerator {
    public static String generateCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // Tạo số từ 100000 đến 999999
        return String.valueOf(code);
    }
}
