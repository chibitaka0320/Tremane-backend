-- ユーザーテーブルにID（検索用ハンドル）関連カラムを追加
ALTER TABLE users ADD COLUMN IF NOT EXISTS handle VARCHAR(16);
ALTER TABLE users ADD COLUMN IF NOT EXISTS handle_updated_at TIMESTAMP;

-- ユーザーID（検索用ハンドル）の大文字小文字を区別しない一意制約
CREATE UNIQUE INDEX IF NOT EXISTS users_handle_lower_idx ON users (LOWER(handle)) WHERE handle IS NOT NULL;
