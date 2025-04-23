-- Active: 1743602494265@@localhost@5432@encoder_test_db

-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS encoder_test_system;

-- Drop tables if they exist
DROP TABLE IF EXISTS encoder_test_system.user_responses;
DROP TABLE IF EXISTS encoder_test_system.test_attempts;
DROP TABLE IF EXISTS encoder_test_system.test_cases;
DROP TABLE IF EXISTS encoder_test_system.questions;
DROP TABLE IF EXISTS encoder_test_system.tests;
DROP TABLE IF EXISTS encoder_test_system.users;
DROP TYPE IF EXISTS encoder_test_system.user_role;
DROP TYPE IF EXISTS encoder_test_system.programming_language;
DROP TYPE IF EXISTS encoder_test_system.question_type;
DROP TYPE IF EXISTS encoder_test_system.question_level;
DROP TYPE IF EXISTS encoder_test_system.attempt_status;

-- Set the default schema
SET search_path TO encoder_test_system;

-- Create enumerated types
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
        CREATE TYPE encoder_test_system.user_role AS ENUM ('ADMIN', 'PARTICIPANT');
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'programming_language') THEN
        CREATE TYPE encoder_test_system.programming_language AS ENUM ('JAVA', 'NODEJS', 'PYTHON', 'CSHARP', 'JAVASCRIPT');
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'question_type') THEN
        CREATE TYPE encoder_test_system.question_type AS ENUM ('CODE', 'MULTIPLE_CHOICE', 'TRUE_FALSE');
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'question_level') THEN
        CREATE TYPE encoder_test_system.question_level AS ENUM ('BASIC', 'INTERMEDIATE', 'ADVANCED');
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'attempt_status') THEN
        CREATE TYPE encoder_test_system.attempt_status AS ENUM ('IN_PROGRESS', 'COMPLETED');
    END IF;
END $$;

-- Create tables

-- User Table
CREATE TABLE IF NOT EXISTS encoder_test_system.users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role user_role NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Test Table
CREATE TABLE IF NOT EXISTS encoder_test_system.tests (
    test_id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    target_language programming_language NOT NULL,
    time_limit INTEGER NOT NULL, -- in minutes
    created_by INTEGER NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (created_by) REFERENCES encoder_test_system.users(user_id)
);

-- Question Table
CREATE TABLE IF NOT EXISTS encoder_test_system.questions (
    question_id SERIAL PRIMARY KEY,
    statement TEXT NOT NULL,
    type question_type NOT NULL,
    level question_level NOT NULL,
    test_id INTEGER NOT NULL,
    points INTEGER NOT NULL,
    FOREIGN KEY (test_id) REFERENCES encoder_test_system.tests(test_id)
);

-- TestCase Table
CREATE TABLE IF NOT EXISTS encoder_test_system.test_cases (
    test_case_id SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL,
    input TEXT,
    expected_output TEXT NOT NULL,
    visible_to_user BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES encoder_test_system.questions(question_id)
);

-- TestAttempt Table
CREATE TABLE IF NOT EXISTS encoder_test_system.test_attempts (
    test_attempt_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    test_id INTEGER NOT NULL,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,
    total_score INTEGER DEFAULT 0,
    status attempt_status DEFAULT 'IN_PROGRESS',
    FOREIGN KEY (user_id) REFERENCES encoder_test_system.users(user_id),
    FOREIGN KEY (test_id) REFERENCES encoder_test_system.tests(test_id)
);

-- UserResponse Table
CREATE TABLE IF NOT EXISTS encoder_test_system.user_responses (
    user_response_id SERIAL PRIMARY KEY,
    test_attempt_id INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    source_code TEXT,
    language_used programming_language,
    compilation_result BOOLEAN,
    execution_result JSONB, -- Array of passed cases or not
    score_obtained INTEGER DEFAULT 0,
    FOREIGN KEY (test_attempt_id) REFERENCES encoder_test_system.test_attempts(test_attempt_id),
    FOREIGN KEY (question_id) REFERENCES encoder_test_system.questions(question_id)
);

-- Indexes to improve performance
CREATE INDEX IF NOT EXISTS idx_test_created_by ON encoder_test_system.tests(created_by);
CREATE INDEX IF NOT EXISTS idx_question_test_id ON encoder_test_system.questions(test_id);
CREATE INDEX IF NOT EXISTS idx_test_case_question_id ON encoder_test_system.test_cases(question_id);
CREATE INDEX IF NOT EXISTS idx_attempt_user_id ON encoder_test_system.test_attempts(user_id);
CREATE INDEX IF NOT EXISTS idx_attempt_test_id ON encoder_test_system.test_attempts(test_id);
CREATE INDEX IF NOT EXISTS idx_response_attempt_id ON encoder_test_system.user_responses(test_attempt_id);
CREATE INDEX IF NOT EXISTS idx_response_question_id ON encoder_test_system.user_responses(question_id);

-- Documentation comments
COMMENT ON TABLE encoder_test_system.users IS 'Stores information about system users';
COMMENT ON TABLE encoder_test_system.tests IS 'Stores information about programming tests';
COMMENT ON TABLE encoder_test_system.questions IS 'Stores questions associated with each test';
COMMENT ON TABLE encoder_test_system.test_cases IS 'Stores test cases to evaluate code responses';
COMMENT ON TABLE encoder_test_system.test_attempts IS 'Records user attempts when taking a test';
COMMENT ON TABLE encoder_test_system.user_responses IS 'Stores user responses to questions';