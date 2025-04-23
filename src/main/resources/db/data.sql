-- SQL Script to insert example data into tables

-- Set the default schema
SET search_path TO encoder_test_system;

-- Insert example users
INSERT INTO users (username, email, password, role) VALUES
('Administrator', 'admin@example.com', '$2a$10$X7HYu.vWJj5hn7NyDY5.6.1sTF8Jh4H9XCmleWxpxQhzYT3ZX3GXi', 'ADMIN'),
('Participant1', 'participante1@example.com', '$2a$10$X7HYu.vWJj5hn7NyDY5.6.1sTF8Jh4H9XCmleWxpxQhzYT3ZX3GXi', 'PARTICIPANT'),
('Participant2', 'participante2@example.com', '$2a$10$X7HYu.vWJj5hn7NyDY5.6.1sTF8Jh4H9XCmleWxpxQhzYT3ZX3GXi', 'PARTICIPANT');

-- Insert example tests
INSERT INTO tests (title, description, target_language, time_limit, created_by, active) VALUES
('Basic Java Test', 'A test to evaluate basic Java knowledge', 'JAVA', 60, 1, true),
('Advanced Node.js Test', 'A test to evaluate advanced Node.js knowledge', 'NODEJS', 90, 1, true),
('Java Algorithms Test', 'Evaluation of algorithms and data structures in Java', 'JAVA', 120, 1, true);

-- Insert example questions for the first test (Basic Java)
INSERT INTO questions (statement, type, level, test_id, points) VALUES
('Write a program that prints "Hello World" to the console', 'CODE', 'BASIC', 1, 5),
('What is the output of the following code? System.out.println(5 + 3 + "2");', 'MULTIPLE_CHOICE', 'BASIC', 1, 3),
('Implement a function that calculates the factorial of a number', 'CODE', 'INTERMEDIATE', 1, 10);

-- Insert example questions for the second test (Advanced Node.js)
INSERT INTO questions (statement, type, level, test_id, points) VALUES
('Implement a basic HTTP server in Node.js', 'CODE', 'INTERMEDIATE', 2, 15),
('Which of the following is NOT a feature of Node.js?', 'MULTIPLE_CHOICE', 'INTERMEDIATE', 2, 5),
('Implement an asynchronous function that reads a file and returns its content', 'CODE', 'ADVANCED', 2, 20);

-- Insert test cases for the first code question (Hello World in Java)
INSERT INTO test_cases (question_id, input, expected_output, visible_to_user) VALUES
(1, '', 'Hello World', true),
(1, '', 'Hello World\n', false); -- Alternative case with line break

-- Insert test cases for the third question (factorial)
INSERT INTO test_cases (question_id, input, expected_output, visible_to_user) VALUES
(3, '5', '120', true),
(3, '0', '1', true),
(3, '10', '3628800', false);

-- Insert test cases for the fourth question (HTTP server)
INSERT INTO test_cases (question_id, input, expected_output, visible_to_user) VALUES
(4, '', 'Server running', true);

-- Insert test cases for the sixth question (file reading)
INSERT INTO test_cases (question_id, input, expected_output, visible_to_user) VALUES
(6, 'test.txt', 'file content', true);

-- Insert an example test attempt
INSERT INTO test_attempts (user_id, test_id, start_date, status) VALUES
(2, 1, CURRENT_TIMESTAMP - INTERVAL '30 minutes', 'IN_PROGRESS');

-- Insert example responses for the attempt
INSERT INTO user_responses (test_attempt_id, question_id, source_code, language_used, compilation_result, execution_result, score_obtained) VALUES
(1, 1, 'public class Main { public static void main(String[] args) { System.out.println("Hello World"); } }', 'JAVA', true, '[{"caso_id": 1, "superado": true}, {"caso_id": 2, "superado": true}]', 5),
(1, 2, NULL, NULL, NULL, NULL, 3), -- Response to multiple choice question
(1, 3, NULL, NULL, NULL, NULL, 0); -- Incorrect response to true/false question