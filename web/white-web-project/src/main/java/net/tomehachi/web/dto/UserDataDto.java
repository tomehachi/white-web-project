package net.tomehachi.web.dto;

import java.io.Serializable;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

/**
 * セッションに保存するユーザ情報DTO<br>
 *
 * @author nakaoka.kentarou
 */
@Component(instance = InstanceType.SESSION)
public class UserDataDto implements Serializable {
    public Integer userId;
    public String requestedUrl = "";

    public Boolean isTemporary = false;

    public boolean isSignedIn() {
        return userId != null && isTemporary != null && !isTemporary;
    }
}
