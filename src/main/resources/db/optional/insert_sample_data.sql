-- Insert Users
INSERT INTO users (user_name, email, password, registration_date) VALUES ('Alice', 'alice@example.com', 'password123', '2024-01-01');
INSERT INTO users (user_name, email, password, registration_date) VALUES ('Bob', 'bob@example.com', 'password456', '2024-02-01');

-- Insert Courses
INSERT INTO courses (course_name, category) VALUES ('Java Programming', 'PROGRAMMING');
INSERT INTO courses (course_name, category) VALUES ('Web Development', 'WEB_DEVELOPMENT');

-- Insert Topics
INSERT INTO topics (title, message, creation_date, status, author_id, course_id) VALUES ('Introduction to Java', 'Basics of Java programming.', '2024-03-01', 'OPEN', 1, 1);
INSERT INTO topics (title, message, creation_date, status, author_id, course_id) VALUES ('Advanced Java', 'Deep dive into Java.', '2024-03-02', 'OPEN', 2, 1);
INSERT INTO topics (title, message, creation_date, status, author_id, course_id) VALUES ('HTML Basics', 'Introduction to HTML.', '2024-03-03', 'OPEN', 1, 2);
INSERT INTO topics (title, message, creation_date, status, author_id, course_id) VALUES ('CSS Basics', 'Introduction to CSS.', '2024-03-04', 'OPEN', 2, 2);

-- Insert Responses for each Topic
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Great introduction!', 1, '2024-03-05', 2);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Thanks for the insights.', 1, '2024-03-06', 1);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Very informative!', 2, '2024-03-07', 1);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Looking forward to more content.', 2, '2024-03-08', 2);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Clear explanation.', 3, '2024-03-09', 2);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Helpful resource.', 3, '2024-03-10', 1);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Easy to understand.', 4, '2024-03-11', 1);
INSERT INTO responses (message, topic_id, creation_date, responder_id) VALUES ('Good job.', 4, '2024-03-12', 2);
