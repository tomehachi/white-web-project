package net.tomehachi.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仮認証済みでもアクセス可能なアクションを定義する注釈クラス.<br>
 * ※ デフォルトでは、パスワード忘れのためのワンタイムパスワード認証時にアクセス可能なアクションに付与している.<br>
 *
 * @author tomehachi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TemporaryMethod {

}
