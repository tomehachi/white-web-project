package net.tomehachi.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import net.arnx.jsonic.JSON;
import net.tomehachi.web.dto.ReCaptchaVerifyDto;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.seasar.struts.util.RequestUtil;

public class AppUtil {

    private AppUtil() {}

    public static String getHostName() {
        try {
            return InetAddress.getByName(RequestUtil.getRequest().getRemoteAddr()).getHostName();
        } catch (UnknownHostException e) {
            return RequestUtil.getRequest().getRemoteAddr();
        }
    }

    public static BufferedReader getFileReader(String path) throws AppException {
        try {
            return new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(path)), Charset.forName("UTF-8")));

        } catch (FileNotFoundException e) {
            throw new AppException(path + " を読み込むのを失敗しました.", e);
        }
    }

    public static Object getObjectField(Object obj, String fieldName) throws AppException {
        try {
            return obj.getClass().getField(fieldName).get(obj);

        } catch (IllegalArgumentException e) {
            throw new AppException("Objectから値を取得する際に例外発生.", e);

        } catch (SecurityException e) {
            throw new AppException("Objectから値を取得する際に例外発生.", e);

        } catch (IllegalAccessException e) {
            throw new AppException("Objectから値を取得する際に例外発生.", e);

        } catch (NoSuchFieldException e) {
            throw new AppException("Objectから値を取得する際に例外発生.", e);
        }
    }

    public static void setObjectField(Object obj, String fieldName, Object fieldInstance) throws AppException {
        try {
            if(fieldInstance != null) { obj.getClass().getField(fieldName).set(obj, fieldInstance); }

        } catch (IllegalArgumentException e) {
            throw new AppException("Objectに値を設定する際に例外発生.", e);

        } catch (SecurityException e) {
            throw new AppException("Objectに値を設定する際に例外発生.", e);

        } catch (IllegalAccessException e) {
            throw new AppException("Objectに値を設定する際に例外発生.", e);

        } catch (NoSuchFieldException e) {
            throw new AppException("Objectに値を設定する際に例外発生.", e);
        }
    }

    public static Object invoke(Object obj, String methodName, Object[] objs, Class<?>[] clazzs)
            throws AppException {
        Class<?> clazz = obj.getClass();
        try {
            return clazz.getMethod(methodName, clazzs).invoke(obj, objs);

        } catch (IllegalAccessException e) {
            throw new AppException("Objectのメソッドにinvokeする際に例外発生.", e);

        } catch (IllegalArgumentException e) {
            throw new AppException("Objectのメソッドにinvokeする際に例外発生.", e);

        } catch (InvocationTargetException e) {
            throw new AppException("Objectのメソッドにinvokeする際に例外発生.", e);

        } catch (NoSuchMethodException e) {
            throw new AppException("Objectのメソッドにinvokeする際に例外発生.", e);

        } catch (SecurityException e) {
            throw new AppException("Objectのメソッドにinvokeする際に例外発生.", e);
        }
    }

    /**
     * クライアントサイドで reCAPTCHA で操作した結果を問い合わせ、ボット判定を返す.<br>
     *
     * @param gRecaptchaResponse reCAPTCHAからのレスポンス
     * @param endUserIp ユーザIPアドレス
     * @return 判定結果 (人間である = true)
     * @throws AppException
     */
    public static boolean isValidReCaptchaResponse(String gRecaptchaResponse, String endUserIp) throws AppException {
        ReCaptchaVerifyDto verifyDto = null;
        String json = null;
        try {
            json = Request
            .Post("https://www.google.com/recaptcha/api/siteverify")
            .bodyForm(Form.form()
                    .add("secret", ResourceBundle.getBundle("security").getString("reCAPTCHA.secretKey"))
                    .add("response", gRecaptchaResponse)
                    .add("remoteip", endUserIp)
                    .build())
            .execute().returnContent().asString();
        } catch (ClientProtocolException e) {
            throw new AppException("reCAPTCHA 認証時に ClientProtocolException 発生.", e);

        } catch (IOException e) {
            throw new AppException("reCAPTCHA 認証時に IOException 発生.", e);
        }
        verifyDto = JSON.decode(json, ReCaptchaVerifyDto.class);

        return verifyDto.success;
    }

    /**
     * 翌日0時のTimestampを取得する.<br>
     *
     * @return 翌日0時のTimestamp
     * @throws AppException 日付パース例外
     */
    public static Timestamp getTomorrow0Oclock() throws AppException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        int y = calendar.get(Calendar.YEAR);
        int M = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat fmt = new SimpleDateFormat("y/M/d HH:mm:ss");
        try {
            return new Timestamp(fmt.parse(y + "/" + M + "/" + d + " 00:00:00").getTime());
        } catch (ParseException e) {
            throw new AppException("翌日0時を設定するのに失敗しました.", e);
        }
    }

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static String dateFormat(Timestamp timestamp) {
        return DATE_FORMAT.format(new Date(timestamp.getTime()));
    }

    private static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static String dateTimeFormat(Timestamp timestamp) {
        return DATE_TIME_FORMAT.format(new Date(timestamp.getTime()));
    }

    public static String[] getFieldStringArrayOf(List<?> list, String fieldName) throws AppException {
        List<String> result = new ArrayList<String>();
        for(Object element: list) {
            result.add(getObjectField(element, fieldName).toString());
        }
        return result.toArray(new String[result.size()]);
    }
}
