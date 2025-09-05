-- ユーザーテーブル
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	user_id TEXT PRIMARY KEY,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ユーザープロフィールテーブル
DROP TABLE IF EXISTS users_profile;
CREATE TABLE users_profile (
	user_id TEXT PRIMARY KEY,
	height INT,
	weight INT,
	birthday DATE,
	gender INT,
	active_level INT,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ユーザー目標テーブル
DROP TABLE IF EXISTS users_goal;
CREATE TABLE users_goal (
	user_id TEXT PRIMARY KEY,
	weight NUMERIC,
	goal_weight NUMERIC,
	start DATE,
	finish DATE,
	pfc INT,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 部位マスタ
DROP TABLE IF EXISTS body_parts;
CREATE TABLE body_parts (
	parts_id SERIAL PRIMARY KEY,
	name VARCHAR(50),
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 種目マスタ
DROP TABLE IF EXISTS exercises;
CREATE TABLE exercises (
	exercise_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	owner_user_id TEXT DEFAULT NULL,
	parts_id BIGINT NOT NULL,
	name VARCHAR(255),
	is_deleted INT DEFAULT 0,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (parts_id) REFERENCES body_parts(parts_id) ON DELETE CASCADE
);

-- ユーザー種目テーブル
DROP TABLE IF EXISTS my_exercises;
CREATE TABLE my_exercises (
	exercise_id TEXT,
	user_id TEXT,
	parts_id BIGINT NOT NULL,
	name VARCHAR(255),
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	PRIMARY KEY (exercise_id, user_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (parts_id) REFERENCES body_parts(parts_id) ON DELETE CASCADE
);

-- トレーニングトランザクション
DROP TABLE IF EXISTS trainings;
CREATE TABLE trainings (
	training_id TEXT PRIMARY KEY,
	date DATE NOT NULL,
	user_id TEXT NOT NULL,
	exercise_id TEXT NOT NULL,
	weight int,
	reps int,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 食事トランザクション
DROP TABLE IF EXISTS eatings;
CREATE TABLE eatings (
	eating_id TEXT PRIMARY KEY,
	date DATE NOT NULL,
	user_id TEXT NOT NULL,
	name VARCHAR(255),
	calories int,
	protein NUMERIC,
	fat NUMERIC,
	carbo NUMERIC,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 友達管理テーブル
DROP TABLE IF EXISTS friend_requests;
CREATE TABLE friend_requests (
	request_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	request_user_id TEXT NOT NULL,
	receive_user_id TEXT NOT NULL,
	status VARCHAR(20) NOT NULL CHECK(status IN ('pending', 'accepted', 'rejected')),
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UNIQUE(request_user_id, receive_user_id),
	FOREIGN KEY (request_user_id) REFERENCES users(user_id),
	FOREIGN KEY (receive_user_id) REFERENCES users(user_id)
);