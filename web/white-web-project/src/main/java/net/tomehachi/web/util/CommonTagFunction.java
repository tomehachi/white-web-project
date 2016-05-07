package net.tomehachi.web.util;


public class CommonTagFunction {

    public static boolean collectionIncludes(String collection, String target) {
        String[] array = collection
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll(" ", "")
                .split(",");

        boolean result = false;
        for(String element : array) {
            if(target.equals(element)) {
                result = true;
            }
        }
        return result;
    }
}
