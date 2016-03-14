package net.tomehachi.web.dto;

import java.util.Date;

/**
 * reCAPTCHA 処理用DTOクラス.<br>
 *
 * @author tomehachi
 */
public class ReCaptchaVerifyDto {

    public Boolean success;
    public Date challengeTs;
    public String errorCodes;

    // The secret parameter is missing
    public String MISSING_INPUT_SECRET = "missing-input-secret";
    // The secret parameter is invalid or malformed
    public String INVALID_INPUT_SECRET = "invalid-input-secret";
    // The response parameter is missing
    public String MISSING_INPUT_RESPONSE = "missing-input-response";
    // The response parameter is invalid or malformed
    public String INVALID_INPUT_RESPONSE = "invalid-input-response";

}
