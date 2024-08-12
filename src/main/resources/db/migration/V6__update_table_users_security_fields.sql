-- Add new fields for Spring Security to the users table
ALTER TABLE users
ADD COLUMN account_non_expired BOOLEAN DEFAULT TRUE,
ADD COLUMN account_non_locked BOOLEAN DEFAULT TRUE,
ADD COLUMN credentials_non_expired BOOLEAN DEFAULT TRUE,
ADD COLUMN is_enabled BOOLEAN DEFAULT TRUE;
