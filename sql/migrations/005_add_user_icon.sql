-- ユーザーテーブルにプロフィールアイコン関連カラムを追加
ALTER TABLE users ADD COLUMN IF NOT EXISTS icon_url TEXT;
ALTER TABLE users ADD COLUMN IF NOT EXISTS icon_updated_at TIMESTAMP;
