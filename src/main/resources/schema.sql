CREATE TABLE IF NOT EXISTS recipes (
  id           BIGINT IDENTITY PRIMARY KEY,
  name         VARCHAR(50),
  author       VARCHAR(50),
  source_id    BIGINT,
  month        VARCHAR(10),
  year         INTEGER,
  location     VARCHAR(200),
  course_id    BIGINT,
  ethnicity_id BIGINT,
  comments     VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS sources (
  id          BIGINT IDENTITY PRIMARY KEY,
  description VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS courses (
  id          BIGINT IDENTITY PRIMARY KEY,
  description VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS ethnicities (
  id          BIGINT IDENTITY PRIMARY KEY,
  description VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS categories (
  id          BIGINT IDENTITY PRIMARY KEY,
  description VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS recipe_categories (
  id          BIGINT IDENTITY PRIMARY KEY,
  category_id BIGINT,
  recipe_id   BIGINT
);
