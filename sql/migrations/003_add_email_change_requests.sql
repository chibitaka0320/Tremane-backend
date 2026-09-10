-- メールアドレス変更リクエスト（OTP）テーブル
-- ユーザー1人につき常に最新の1件のみ保持する（新しいコード発行時はUPSERTで上書き）
-- 認証コードの検証に成功するまでは、実際のメールアドレスは変更しない
CREATE TABLE IF NOT EXISTS email_change_requests (
	user_id TEXT PRIMARY KEY,
	new_email TEXT NOT NULL,
	code VARCHAR(6) NOT NULL,
	expires_at TIMESTAMP NOT NULL,
	attempt_count INT NOT NULL DEFAULT 0,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
