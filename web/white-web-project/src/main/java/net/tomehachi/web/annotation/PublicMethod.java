package net.tomehachi.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 公開アクションを示す注釈クラス.<br>
 * 認証を必要としないアクション.<br>
 *
 * @author tomehachi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PublicMethod {
}
