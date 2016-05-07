DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS change_password_key;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS auth_log;
DROP TABLE IF EXISTS user_auth;

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

CREATE TABLE user_profile (
    user_id INT COMMENT 'ユーザID',
    first_name VARCHAR(64) NOT NULL COMMENT '名',
    family_name VARCHAR(64) NOT NULL COMMENT '姓',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES user_auth (user_id)
) COMMENT 'ユーザプロフィール情報';

INSERT INTO user_auth ( email, password ) VALUES ('webmaster@white-web-project.com','4c694b55c0303b31e932437cc38ca5a76f79bd8e8a3bd3cb18df33a53a2e2a068a549d6744ad16864913ea16172387a122cadc33f43ffc2b3de714f695e5');
INSERT INTO user_role ( user_id, role ) VALUES ((SELECT LAST_INSERT_ID() FROM user_auth), 'admin');
INSERT INTO user_role ( user_id, role ) VALUES ((SELECT LAST_INSERT_ID() FROM user_auth), 'user');
INSERT INTO change_password_key ( user_id, done ) VALUES ((SELECT LAST_INSERT_ID() FROM user_auth), TRUE);
INSERT INTO user_profile ( user_id, first_name, family_name, created_at, updated_at ) VALUES (1,'太郎','管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
