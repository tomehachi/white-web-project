package net.tomehachi.web.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.tomehachi.web.util.AppException;
import net.tomehachi.web.util.AppUtil;


public class MailUtil {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("mail");

    public void sendMail(MailDto dto, String templateFileName) throws AppException {
        /* SMTP接続設定を取得 */
        Properties props = new Properties();
        props.put("mail.smtp.host", bundle.getString("mail.smtp.host"));
        props.put("mail.smtp.port", bundle.getString("mail.smtp.port"));
        props.put("mail.debug", bundle.getString("mail.debug"));

        /* セッション生成 */
        Session session;
        if(Boolean.parseBoolean(bundle.getString("mail.smtp.auth"))) {
            // SMTP認証を行なう.
            props.put("mail.smtp.auth", bundle.getString("mail.smtp.auth"));
            session = Session.getInstance(props, new MailAuthenticator(
                    bundle.getString("mail.smtp.auth.userId"),
                    bundle.getString("mail.smtp.auth.password")
            ));
        } else {
            // SMTP認証を行なわない.
            props.put("mail.smtp.auth", bundle.getString("mail.smtp.auth"));
            session = Session.getInstance(props, null);
        }

        // デバッグモードを設定する.
        session.setDebug(Boolean.parseBoolean(bundle.getString("mail.debug")));

        try{
            // 接続
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(bundle.getString("mail.smtp.from")));
            msg.setReplyTo(new InternetAddress[] {
                    new InternetAddress(bundle.getString("mail.smtp.replyTo"))});
            msg.setRecipient(Message.RecipientType.TO, dto.getTo());
            msg.setSubject(dto.getSubject());
            msg.setSentDate(new Date());

            msg.setText(createMessageBody(dto.getParams(), templateFileName));

            Transport.send(msg);

        } catch(MessagingException e){
            throw new AppException("メール送信中に例外発生.", e);
        }
    }

    private static final Pattern PLACE_HOLDER_PATTERN = Pattern.compile(".*?(\\{[ ]*(.*?)[ ]*\\}).*");

    private String createMessageBody(Map<String, String> params, String templateFileName) throws AppException {
        BufferedReader reader = AppUtil.getFileReader(templateFileName);
        StringBuffer messageBody = new StringBuffer();

        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                Matcher matcher = PLACE_HOLDER_PATTERN.matcher(line);
                boolean matches = matcher.matches();
                while(matches) {
                    String stringToReplace = matcher.group(1);
                    String key = matcher.group(2);
                    String value = params.get(key);
                    line = line.replace(stringToReplace, value == null ? "" : value);

                    matcher = PLACE_HOLDER_PATTERN.matcher(line);
                    matches = matcher.matches();
                }
                messageBody.append(line).append("\r\n");
            }
        } catch (IOException e) {
            throw new AppException("ファイル読み込み中に例外発生.", e);
        }
        return messageBody.toString();
    }

}
