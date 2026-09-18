-- 食事記録（複数の食品をまとめる親レコード）テーブルを追加
CREATE TABLE IF NOT EXISTS meals (
    meal_id TEXT PRIMARY KEY,
    date DATE NOT NULL,
    user_id TEXT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 食品記録（eatings）に、所属する食事記録IDと単位を追加
ALTER TABLE eatings ADD COLUMN IF NOT EXISTS meal_id TEXT REFERENCES meals(meal_id) ON DELETE CASCADE;
ALTER TABLE eatings ADD COLUMN IF NOT EXISTS unit VARCHAR(10);
