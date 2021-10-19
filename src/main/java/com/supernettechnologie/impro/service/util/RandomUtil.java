package com.supernettechnologie.impro.service.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;
    private static final int DEF_COUNT_NUMERIQUE = 5;
    private static final int DEF_COUNT_SERIE = 24;
    private static final int DEF_COUNT_SERIES = 14;
    private static final int DEF_COUNT_SERIEC = 7;
    private static final int DEF_COUNT_OTP = 6;

    private static final SecureRandom SECURE_RANDOM;

    static {
        SECURE_RANDOM = new SecureRandom();
        SECURE_RANDOM.nextBytes(new byte[64]);
    }

    private RandomUtil() {
    }

    private static String generateRandomAlphanumericString() {
        return RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM);
    }
    public static String generateRandomNumericString() {
        return RandomStringUtils.random(DEF_COUNT_NUMERIQUE, 0, 0, false, true, null, SECURE_RANDOM);
    }

    public static String generateRandomSerialNumericString() {
        return RandomStringUtils.random(DEF_COUNT_SERIE, 0, 0, false, true, null, SECURE_RANDOM);
    }
    public static String generateRandomSerialNumericStrings() {
        return RandomStringUtils.random(DEF_COUNT_SERIES, 0, 0, false, true, null, SECURE_RANDOM);
    }
    public static String generateRandomSerialNumericStringc() {
        return RandomStringUtils.random(DEF_COUNT_SERIEC, 0, 0, false, true, null, SECURE_RANDOM);
    }

    public static String generateRandomUserOtp() {
        return RandomStringUtils.random(DEF_COUNT_OTP, 0, 0, false, true, null, SECURE_RANDOM);
    }

    /**
     * Generate a password.
     *
     * @return the generated password.
     */
    public static String generatePassword() {
        return generateRandomAlphanumericString();
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key.
     */
    public static String generateActivationKey() {
        return generateRandomAlphanumericString();
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key.
     */
    public static String generateResetKey() {
        return generateRandomAlphanumericString();
    }
}
