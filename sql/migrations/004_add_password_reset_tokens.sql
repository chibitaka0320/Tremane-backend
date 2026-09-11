-- パスワード再設定トークンテーブル
-- ユーザー1人につき常に最新の1件のみ保持する（新しいトークン発行時はUPSERTで上書き）
-- メール内のリンク（tremane://resetPasswordConfirm?token=xxx）経由でアプリに渡される
CREATE TABLE IF NOT EXISTS password_reset_tokens (
	user_id TEXT PRIMARY KEY,
	token TEXT NOT NULL,
	expires_at TIMESTAMP NOT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_password_reset_tokens_token ON password_reset_tokens (token);
