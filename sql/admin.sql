-- リフレッシュトークン管理テーブル
DROP TABLE IF EXISTS refresh_tokens;
CREATE TABLE refresh_tokens (
	token_id SERIAL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	token VARCHAR(512) NOT NULL,
	expiry_date TIMESTAMP NOT NULL,
	revoked BOOLEAN DEFAULT FALSE,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);