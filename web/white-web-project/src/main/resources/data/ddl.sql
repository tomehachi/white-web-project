DROP TABLE IF EXISTS change_password_key;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user_auth;
DROP TABLE IF EXISTS auth_log;

/* -- 認証に用いる情報 -- */
CREATE TABLE user_auth (
    user_id INT NOT NULL AUTO_INCREMENT COMMENT 'シーケンスID',
    email VARCHAR(128) NOT NULL UNIQUE COMMENT 'メールアドレス',
    password VARCHAR(128) NOT NULL COMMENT 'パスワード',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    CONSTRAINT PRIMARY KEY (user_id)
) COMMENT 'ユーザ認証情報';

/* -- 認証ログ -- */
CREATE TABLE auth_log (
    auth_log_id INT NOT NULL AUTO_INCREMENT COMMENT 'シーケンスID',
    user_id INT COMMENT '認証しようとしたユーザ',
    password VARCHAR(128) COMMENT '失敗時に入力されたパスワード',
    result BOOLEAN COMMENT '認証結果',
    ip_address VARCHAR(128) COMMENT 'ログインを試行したIPアドレス',
    host VARCHAR(256) COMMENT 'ログインを試行したホスト名',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '試行日時',
    CONSTRAINT PRIMARY KEY (auth_log_id)
) COMMENT '認証ログ';

CREATE TABLE user_role (
    user_id INT COMMENT 'ユーザID',
    role ENUM('admin', 'user') NOT NULL COMMENT 'ロール',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES user_auth (user_id)
) COMMENT 'ユーザロール';

CREATE TABLE change_password_key (
    user_id INT COMMENT 'ユーザID',
    change_password_key VARCHAR(128) COMMENT 'パスワード変更認証キー',
    done BOOLEAN NOT NULL DEFAULT FALSE COMMENT '使用済みフラグ',
    expired_at TIMESTAMP NOT NULL DEFAULT '1970-01-01 09:00:01' COMMENT '有効期限',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
    CONSTRAINT PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES user_auth (user_id)
) COMMENT 'パスワード変更認証キー';

INSERT INTO user_auth ( email, password ) VALUES ('admin@white-web-project.com','2758127b442d9eb145c7f5ad5294d5a594948db6bb592ffbf8edbedfdae99f8103bfa178decfc70f47bc496df2b225b48698053200a173d4e0b91ea506580');
INSERT INTO user_role ( user_id, role ) VALUES ((SELECT LAST_INSERT_ID() FROM user_auth), 'admin');
INSERT INTO user_role ( user_id, role ) VALUES ((SELECT LAST_INSERT_ID() FROM user_auth), 'user');
INSERT INTO change_password_key ( user_id, done ) VALUES ((SELECT LAST_INSERT_ID() FROM user_auth), TRUE);
