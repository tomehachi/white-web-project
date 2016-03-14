package net.tomehachi.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class SecurityUtil {

    /** 暗号化アルゴリズム名 */
    private static final String ENCRYPT_ALGORITHMN = "SHA-512";

    private static Logger logger = Logger.getLogger(SecurityUtil.class);

    private SecurityUtil() {}


    public static String encode(String input) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(ENCRYPT_ALGORITHMN);

        } catch (NoSuchAlgorithmException e) {
            logger.error("文字列を暗号化中に例外が発生しました.", e);
        }
        messageDigest.update(input.getBytes());
        byte[] bytes = messageDigest.digest();

        StringBuffer result = new StringBuffer();
        for(int b : bytes) {
            result.append(Integer.toHexString(b & 0xff));
        }
        return result.toString();
    }

    private static String[] LOWER_ALPHABET = new String[] {
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
        "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };

    private static String[] UPPER_ALPHABET = new String[] {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static String[] NUMBER = new String[] {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    public static String randomString(int length) {
        StringBuffer result = new StringBuffer();

        List<String> charList = new ArrayList<String>();
        charList.addAll(Arrays.asList(LOWER_ALPHABET));
        charList.addAll(Arrays.asList(UPPER_ALPHABET));
        charList.addAll(Arrays.asList(NUMBER));
        charList.addAll(Arrays.asList(getPasswordSymbolArray()));
        String[] CHARACTER = charList.toArray(new String[charList.size()]);
        for(int i=0; i<length; i++) {
            result.append(
                    CHARACTER[(int)Math.round( (double)(Math.random()*(CHARACTER.length - 1)) )]
            );
        }
        return result.toString();
    }

    public static boolean includesLower(String str) {
        for(String lower : LOWER_ALPHABET) {
            if(str.contains(lower)) {
                return true;
            }
        }
        return false;
    }
    public static boolean includesUpper(String str) {
        for(String upper : UPPER_ALPHABET) {
            if(str.contains(upper)) {
                return true;
            }
        }
        return false;
    }
    public static boolean includesNumber(String str) {
        for(String number : NUMBER) {
            if(str.contains(number)) {
                return true;
            }
        }
        return false;
    }
    public static boolean includesSymbol(String str) {
        for(String symbol : getPasswordSymbolArray()) {
            if(str.contains(symbol)) {
                return true;
            }
        }
        return false;
    }
    public static boolean includesInvalidPasswordChar(String str) {
        for(int i=0; i<str.length()-1; i++) {
            String singleChar = str.substring(i, i+1);
            if(!includesLower(singleChar)
                    && !includesUpper(singleChar)
                    && !includesNumber(singleChar)
                    && !includesSymbol(singleChar)) {
                return true;
            }
        }
        return false;
    }

    public static String getPasswordSymbolRegexp() {
        String[] symbols = getPasswordSymbolArray();
        StringBuffer result = new StringBuffer();

        result.append("[");
        for(String symbol: symbols) {
            result.append("\\"+symbol);
        }
        result.append("]");

        return result.toString();
    }

    public static String[] getPasswordSymbolArray() {
        return ResourceBundle.getBundle("security").getString("password.policy.symbols").split(" ");
    }

    public static String getPasswordSymbols() {
        return ResourceBundle.getBundle("security").getString("password.policy.symbols");
    }

    public static String getPasswordForgotPassword() {
        return SecurityUtil.randomString(
                Integer.parseInt(
                        ResourceBundle
                        .getBundle("security")
                        .getString("password.forgot.password.length")
                )
        );
    }
}
