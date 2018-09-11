CREATE TYPE gender AS ENUM ('male', 'female');
CREATE TYPE user_kind AS ENUM ('super_admin', 'parent', 'subuser', 'normal_user');

CREATE TABLE identifications_kinds (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  description VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE users (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  gender gender,
  birthday DATE,
  picture_url TEXT,
  identification VARCHAR(100),
  identification_kind_id BIGINT,
  email VARCHAR(150) NOT NULL UNIQUE,
  user_kind user_kind NOT NULL,
  enabled BOOLEAN DEFAULT FALSE,
  expired BOOLEAN DEFAULT FALSE,
  locked BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT current_timestamp,
  updated_at TIMESTAMP,
  user_id BIGINT,

  CONSTRAINT fk_users_users FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_identifications_kinds_users FOREIGN KEY (identification_kind_id) REFERENCES identifications_kinds (id)
);

CREATE TABLE socials (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  description VARCHAR(100)
);

CREATE TABLE users_socials (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  social_id BIGINT NOT NULL,
  social_registration_id TEXT,

  CONSTRAINT fk_users_users_socials FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_users_socials_socials FOREIGN KEY (social_id) REFERENCES socials (id)
);

CREATE UNIQUE INDEX ix_user_id_social_id ON users_socials (user_id, social_id);

CREATE TABLE groups (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  description VARCHAR(150) NOT NULL UNIQUE,
  user_id BIGINT NOT NULL,

  CONSTRAINT fk_groups_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE groups_users (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  group_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,

  CONSTRAINT fk_groups_users_groups FOREIGN KEY (group_id) REFERENCES groups (id),
  CONSTRAINT fk_groups_users_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE roles (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	description VARCHAR(50) NOT NULL,
	user_id BIGINT NOT NULL,

	CONSTRAINT fk_roles_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE roles_users (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  role_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,

  CONSTRAINT fk_roles_users_roles FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_roles_users_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE UNIQUE INDEX ix_role_role_user_id ON roles (description, user_id);

CREATE TABLE authorities (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  description VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE authorities_roles (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  authority_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,

  CONSTRAINT fk_authorities_roles_authorities FOREIGN KEY (authority_id) REFERENCES authorities (id),
  CONSTRAINT fk_authorities_roles_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);
