package net.tomehachi.web.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import net.tomehachi.web.util.AppException;

public class MailDto {

    private InternetAddress to;
    private String subject;
    private Map<String, String> params = new HashMap<String, String>();

    public InternetAddress getTo() {
        return to;
    }
    public void setTo(String email) throws AppException {
        try {
            this.to = new InternetAddress(email);

        } catch (AddressException e) {
            throw new AppException("Toの設定に失敗しました. (email="+ email +")", e);
        }
    }
    public void setTo(String email, String name) throws AppException {
        try {
            this.to = new InternetAddress(email, name);

        } catch (UnsupportedEncodingException e) {
            throw new AppException("Toの設定に失敗しました. (email="+ email +", name="+ name +")", e);
        }
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MailDto put(String key, String value) {
        this.params.put(key, value);
        return this;
    }
    public Map<String, String> getParams() {
        return this.params;
    }
}
