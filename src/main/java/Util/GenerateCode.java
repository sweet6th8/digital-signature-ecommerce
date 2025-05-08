package Util;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateCode {

    // Độ dài mặc định của mã reset
    private static final int DEFAULT_CODE_LENGTH = 8;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Tạo mã reset dựa trên độ dài tùy chỉnh.
     *
     * @param length Độ dài của mã
     * @return Mã reset ngẫu nhiên
     */
    public static String generateResetCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    /**
     * Tạo mã reset với độ dài mặc định.
     *
     * @return Mã reset ngẫu nhiên
     */
    public static String generateResetCode() {
        return generateResetCode(DEFAULT_CODE_LENGTH);
    }

    /**
     * Tạo mã reset base64 (tuỳ chọn).
     *
     * @return Mã reset dạng Base64
     */
    public static String generateBase64ResetCode() {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[DEFAULT_CODE_LENGTH];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng
        System.out.println("Mã reset thông thường: " + generateResetCode());
        System.out.println("Mã reset dạng Base64: " + generateBase64ResetCode());
    }
}
