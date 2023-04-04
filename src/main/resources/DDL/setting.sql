CREATE SCHEMA `acb` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `acb`.`account_book` (
                                     `id` INT NOT NULL AUTO_INCREMENT COMMENT '가계부 생성 시퀀스',
                                     `usr_id` INT NOT NULL COMMENT '유저 id 값',
                                     `date` DATETIME NOT NULL COMMENT '가계부 해당 날짜',
                                     `spent_money` INT NOT NULL DEFAULT 0 COMMENT '사용한 돈',
                                     `memo` VARCHAR(500) NULL COMMENT '메모',
                                     `used_yn` VARCHAR(1) NOT NULL COMMENT '사용 여부',
                                     `reg_dt` DATETIME NOT NULL COMMENT '등록된 날짜',
                                     `upt_dt` DATETIME NULL COMMENT '업데이트된 날짜',
                                     PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `acb`.`customer` (
                              `id` INT NOT NULL AUTO_INCREMENT COMMENT '유저 시퀀스값',
                              `email` VARCHAR(100) NOT NULL COMMENT 'email',
                              `name` VARCHAR(45) NOT NULL COMMENT '이름',
                              `password` VARCHAR(100) NOT NULL COMMENT '비밀번호',
                              `reg_dt` DATE NOT NULL COMMENT '등록일',
                              `upt_dt` DATE COMMENT '수정일',
                              PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
