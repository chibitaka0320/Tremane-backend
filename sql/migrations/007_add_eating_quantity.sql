-- 食品記録（eatings）に数量（単位に対応する数値、例: 100g・2個）を追加
ALTER TABLE eatings ADD COLUMN IF NOT EXISTS quantity NUMERIC;
