package net.tomehachi.web.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {

    private String userId;
    private String password;

    /**
     * メールアドレスとパスワードを設定する.<br>
     *
     * @param userId メールアドレス
     * @param password パスワード
     */
    public MailAuthenticator(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /*
     * (非 Javadoc)
     * @see javax.mail.Authenticator#getPasswordAuthentication()
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.userId, this.password);
    }

}
