drop database if exists survey_db;
create database if not exists survey_db;
use survey_db;

-- 1. category_dtl 테이블 생성
CREATE TABLE `category_dtl` (
  `category_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `category_name` VARCHAR(50) NOT NULL UNIQUE,
  `category_count` INT NOT NULL
);

-- 2. user_mst 테이블 생성
CREATE TABLE `user_mst` (
  `user_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_name` VARCHAR(50) NOT NULL UNIQUE,
  `user_email` VARCHAR(50) NOT NULL,
  `user_password` VARCHAR(250) NOT NULL,
  `role` VARCHAR(50) NOT NULL,
  `provider` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  `join_path` VARCHAR(5) NOT NULL COMMENT '가입경로 (HOME, KAKAO, NAVER)',
  `sns_id` VARCHAR(255) DEFAULT NULL COMMENT 'OAuth 사용자 아이디'
);

-- 3. company_mst 테이블 생성
CREATE TABLE `company_mst` (
  `company_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `company_name` VARCHAR(50) NOT NULL UNIQUE,
  `company_email` VARCHAR(50) NOT NULL,
  `company_password` VARCHAR(100) NOT NULL,
  `role` VARCHAR(50) NOT NULL,
  `join_status` INT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL
);

-- 4. survey_information 테이블 생성
CREATE TABLE `survey_information` (
  `survey_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `survey_title` varchar(50) NOT NULL,
  `survey_content` text NOT NULL,
  `survey_present_img` varchar(50) DEFAULT NULL,
  `participant_count` INT NOT NULL,
  `survey_status` INT NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL
);

-- 5. survey_status_dtl 테이블 생성
CREATE TABLE `survey_status_dtl` (
  `status_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `survey_code` INT NOT NULL,
  `survey_status` INT NOT NULL,
  `status_content` VARCHAR(50) NOT NULL,
  FOREIGN KEY (`survey_code`) REFERENCES `survey_information`(`survey_code`)
);

-- 6. user_dtl 테이블 생성
CREATE TABLE `user_dtl` (
  `user_code` INT NOT NULL PRIMARY KEY,
  `user_birth` VARCHAR(100) DEFAULT NULL,
  `user_gender` VARCHAR(50) DEFAULT NULL,
  `user_nickname` VARCHAR(50) DEFAULT NULL,
  `user_account` VARCHAR(50) DEFAULT NULL,
  `user_img` LONGTEXT DEFAULT NULL,
  `user_money` INT DEFAULT NULL,
  FOREIGN KEY (`user_code`) REFERENCES `user_mst`(`user_code`)
);

-- 7. company_file 테이블 생성
CREATE TABLE `company_file` (
  `file_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `company_code` INT NOT NULL,
  `file_name` VARCHAR(500) NOT NULL DEFAULT '',
  `create_date` DATETIME NOT NULL,
  FOREIGN KEY (`company_code`) REFERENCES `company_mst`(`company_code`)
);

-- 8. notice_mst 테이블 생성
CREATE TABLE `notice_mst` (
  `notice_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_code` INT DEFAULT NULL,
  `notice_title` VARCHAR(100) NOT NULL,
  `notice_content` TEXT NOT NULL,
  `create_date` DATE NOT NULL,
  `update_date` DATE NOT NULL,
  FOREIGN KEY (`user_code`) REFERENCES `user_mst`(`user_code`)
);

-- 9. survey_question 테이블 생성
CREATE TABLE `survey_question` (
  `question_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `survey_code` INT NOT NULL,
  `question_title` VARCHAR(50) NOT NULL,
  `question_img` VARCHAR(50) DEFAULT NULL,
  `question_essential` INT NOT NULL,
  `select_type` INT NOT NULL,
  `detail_question` TEXT DEFAULT NULL,
  FOREIGN KEY (`survey_code`) REFERENCES `survey_information`(`survey_code`)
);

-- 10. question_choice 테이블 생성
CREATE TABLE `question_choice` (
  `option_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `question_code` INT NOT NULL,
  `option_content` VARCHAR(50) NOT NULL DEFAULT '',
  `options_img` VARCHAR(50) DEFAULT NULL,
  FOREIGN KEY (`question_code`) REFERENCES `survey_question`(`question_code`)
);

-- 11. survey_answer 테이블 생성
CREATE TABLE `survey_answer` (
  `answer_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `question_code` INT NOT NULL,
  `survey_answer_short` VARCHAR(50) DEFAULT NULL,
  `survey_answer_long` TEXT DEFAULT NULL,
  `detail_answer` TEXT DEFAULT NULL,
  FOREIGN KEY (`question_code`) REFERENCES `survey_question`(`question_code`)
);

-- 12. survey_respondent 테이블 생성
CREATE TABLE `survey_respondent` (
  `respondent_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `survey_code` INT DEFAULT NULL,
  `user_code` INT DEFAULT NULL,
  `apply_date` DATETIME NOT NULL,
  FOREIGN KEY (`survey_code`) REFERENCES `survey_information`(`survey_code`),
  FOREIGN KEY (`user_code`) REFERENCES `user_mst`(`user_code`)
);

-- 13. apply_file 테이블 생성
CREATE TABLE `apply_file` (
  `file_code` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `survey_code` INT NOT NULL,
  `file_name` VARCHAR(50) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  FOREIGN KEY (`survey_code`) REFERENCES `survey_information`(`survey_code`)
);

-- 14. apply_information 테이블 생성
CREATE TABLE `apply_information` (
  `survey_code` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `application_class` varchar(50),
  `survey_class` varchar(50),
  `survey_password` varchar(50),
  `user_code` int,
  `company_code` int,
  `category_code` int,
  `survey_target_gender` varchar(50),
  `survey_target_age` varchar(50),
  `survey_per_money` int,
  `participant_max` int,
  `survey_period_start` varchar(50),
  `survey_period_stop` varchar(50)
);
