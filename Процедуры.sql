USE `timetable_infosystem`;

/* Проверка логина и пароля, U0 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U0_login_and_password_check;
CREATE PROCEDURE U0_login_and_password_check(IN email VARCHAR(45), IN password_ CHAR(8),  OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Преподаватели` WHERE (`Почта` LIKE email AND `Пароль` LIKE password_ AND `Права на супервайзинг` LIKE 1))) THEN
		SET result_status = 3;
	END IF;
    IF (EXISTS(SELECT * FROM `Преподаватели` WHERE (`Почта` LIKE email AND `Пароль` LIKE password_ AND `Права на супервайзинг` LIKE 0))) THEN
		SET result_status = 2;
	END IF;
    IF (EXISTS(SELECT * FROM `Учащиеся` WHERE (`Почта` LIKE email AND `Пароль` LIKE password_))) THEN
		SET result_status = 1;
	END IF;
    IF NOT(EXISTS(SELECT * FROM `Преподаватели` WHERE (`Почта` LIKE email AND `Пароль` LIKE password_)) OR EXISTS(SELECT * FROM `Учащиеся` WHERE (`Почта` LIKE email AND `Пароль` LIKE password_))) THEN
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Информация о преподавателях и их статистика, A1 */ /* QUALITY CONTROL FINAL PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A1_tutors_info;
CREATE PROCEDURE A1_tutors_info (IN sort_type TINYINT, OUT result_status TINYINT)
BEGIN
	SET result_status = 1;
	SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Серия паспорта`, `Номер паспорта`, `Права на супервайзинг`, IFNULL(`Всего занятий в сетке`, 0) AS `Всего занятий в сетке`,
    IFNULL(`Занятий основным преподавателем`, 0) AS `Занятий основным преподавателем`, IFNULL(`Занятий ассистентом`, 0) AS `Занятий ассистентом`,
    IFNULL(`Число курируемых`, 0) AS `Число курируемых`, IFNULL(`Число кабинетов`, 0) AS `Число кабинетов` FROM (
		(SELECT * FROM `Преподаватели`) AS `Часть 1`
        LEFT JOIN
        (SELECT `Почта ответственного` AS `Почта`, COUNT(*) AS `Число кабинетов` FROM `Кабинеты` GROUP BY `Почта ответственного`) AS `Часть 2`
        USING (`Почта`)
		LEFT JOIN
		(SELECT `Почта куратора` AS `Почта`, COUNT(*) AS `Число курируемых` FROM `Учащиеся` GROUP BY `Почта куратора`) AS `Часть 3`
		USING (`Почта`)
        LEFT JOIN
		(SELECT `Почта преподавателя` AS `Почта`, COUNT(*) AS `Всего занятий в сетке` FROM `Преподаватели на занятиях` GROUP BY `Почта преподавателя`) AS `Часть 4`
        USING(`Почта`)
        LEFT JOIN
        (SELECT `Почта преподавателя` AS `Почта`, `Статус преподавателя на занятии`, COUNT(*) AS `Занятий основным преподавателем` FROM `Преподаватели на занятиях` GROUP BY `Почта преподавателя`, `Статус преподавателя на занятии` HAVING (`Статус преподавателя на занятии` LIKE 'Основной')) AS `Часть 5`
        USING(`Почта`)
        LEFT JOIN
        (SELECT `Почта преподавателя` AS `Почта`, `Статус преподавателя на занятии`,  COUNT(*) AS `Занятий ассистентом` FROM `Преподаватели на занятиях` GROUP BY `Почта преподавателя`, `Статус преподавателя на занятии` HAVING (`Статус преподавателя на занятии` LIKE 'Ассистент')) AS `Часть 6`
        USING(`Почта`)
    ) ORDER BY CASE
        WHEN sort_type LIKE 1 THEN `Всего занятий в сетке`
        WHEN sort_type LIKE 2 THEN `Число курируемых`
		WHEN sort_type LIKE 3 THEN `Число кабинетов`
	END DESC,
    CASE
		WHEN sort_type LIKE 0 THEN `Фамилия`
	END ASC,
    CASE
		WHEN sort_type LIKE 0 THEN `Имя`
	END ASC,
    CASE
		WHEN sort_type LIKE 0 THEN `Отчество`
	END ASC;
END $$
DELIMITER;


/* Информация об учащихся и их статистика, A2 */ /* QUALITY CONTROL FINAL PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A2_students_info;
CREATE PROCEDURE A2_students_info(IN sort_type TINYINT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Учащиеся`)) THEN
		SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Серия паспорта`, `Номер паспорта`, `Почта куратора`,
		IFNULL(`Всего занятий в сетке`, 0) AS `Всего занятий в сетке`, IFNULL(`Занятий с высоким приоритетом`, 0) AS `Занятий с высоким приоритетом`,
        IFNULL(`Занятий со средним приоритетом`, 0) AS `Занятий со средним приоритетом`, IFNULL(`Занятий с низким приоритетом`, 0) AS `Занятий с низким приоритетом`  FROM (
			(SELECT * FROM `Учащиеся`) AS `Часть 1`
			LEFT JOIN 
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, Count(*) AS `Всего занятий в сетке` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`) AS `Часть 2`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, Count(*) AS `Занятий с высоким приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Высокий')) AS `Часть 3`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, Count(*) AS `Занятий со средним приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Средний')) AS `Часть 4`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, Count(*) AS `Занятий с низким приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Низкий')) AS `Часть 5`
			USING(`Почта`)
		) ORDER BY CASE
        WHEN sort_type LIKE 1 THEN `Всего занятий в сетке`
		END DESC,
		CASE
			WHEN sort_type LIKE 0 THEN `Фамилия`
		END ASC,
		CASE
			WHEN sort_type LIKE 0 THEN `Имя`
		END ASC,
		CASE
			WHEN sort_type LIKE 0 THEN `Отчество`
		END ASC;
		SET result_status = 1;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Поставить занятие, A3 */ /* QUALITY CONTROL 2 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A3_set_class;
CREATE PROCEDURE A3_set_class(IN date_ VARCHAR(10), IN time_ VARCHAR(5), IN class_name VARCHAR(45), IN room_building VARCHAR(1), IN room_number VARCHAR(5), IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF ((SELECT Count(*) FROM `Занятия`) > 0) THEN
		SET @new_ind = (SELECT max(`Идентификатор`)+1 FROM `Занятия`);  
	ELSE 
		SET @new_ind = 1;
	END IF;
    IF (NOT EXISTS(
		SELECT * FROM `Занятия` WHERE (
			`Номер кабинета` LIKE room_number AND `Корпус кабинета` LIKE room_building 
            AND `Дата` LIKE STR_TO_DATE(date_, '%d.%m.%Y') AND ABS(TIME_TO_SEC(`Время`) - TIME_TO_SEC(STR_TO_DATE(time_, '%H:%i'))) < 7200 
        )) AND TIME_TO_SEC(STR_TO_DATE(time_, '%H:%i')) > TIME_TO_SEC(STR_TO_DATE('06:59', '%H:%i')) 
        AND TIME_TO_SEC(STR_TO_DATE(time_, '%H:%i')) < TIME_TO_SEC(STR_TO_DATE('21:01', '%H:%i'))
    ) THEN
		IF (EXISTS (SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email) AND EXISTS (SELECT * FROM `Кабинеты` WHERE ( `Корпус` LIKE room_building AND `Номер` LIKE room_number))) THEN
			INSERT INTO `Занятия`(`Идентификатор`, `Дата`, `Время`, `Название дисциплины`, `Корпус кабинета`, `Номер кабинета`) 
			VALUES (@new_ind, STR_TO_DATE(date_, '%d.%m.%Y'), STR_TO_DATE(time_, '%H:%i'), class_name, room_building, room_number);
			INSERT INTO `Преподаватели на занятиях`(`Почта преподавателя`, `Идентификатор занятия`, `Статус преподавателя на занятии`) 
			VALUES (email,  (SELECT `Идентификатор` FROM `Занятия` WHERE `Идентификатор` LIKE @new_ind), 'Основной');
			SET result_status = 1;
		ELSE 
			SET result_status = 0;
		END IF;
	ELSE
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Удаление занятия, A4 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A4_drop_class;
CREATE PROCEDURE A4_drop_class(IN class_id INT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Занятия` WHERE `Идентификатор` LIKE class_id)) THEN
		DELETE FROM `Занятия` WHERE `Идентификатор` LIKE class_id;
        SET result_status = 1;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Назначить препода на занятие или изменить его статус на занятии, A5 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A5_set_tutor_to_class;
CREATE PROCEDURE A5_set_tutor_to_class(IN email VARCHAR(45), IN class_id INT, IN status_ VARCHAR(9), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Занятия` WHERE (`Идентификатор` LIKE class_id)) AND EXISTS(SELECT * FROM `Преподаватели` WHERE (`Почта` LIKE email))) THEN
		IF (status_ LIKE 'Ассистент') THEN 
			IF (EXISTS (SELECT * FROM `Преподаватели на занятиях` WHERE (`Почта преподавателя` LIKE email AND `Идентификатор занятия` LIKE class_id AND `Статус преподавателя на занятии` LIKE 'Ассистент'))) THEN
				SET result_status = 1;
            END IF;
            IF (EXISTS (SELECT * FROM `Преподаватели на занятиях` WHERE (`Почта преподавателя` LIKE email AND `Идентификатор занятия` LIKE class_id AND `Статус преподавателя на занятии` LIKE 'Основной'))) THEN
				SET result_status = 0;
			END IF;
            IF (NOT EXISTS (SELECT * FROM `Преподаватели на занятиях` WHERE (`Идентификатор занятия` LIKE class_id AND `Почта преподавателя` LIKE email))) THEN
				INSERT INTO `Преподаватели на занятиях` (`Почта преподавателя` , `Идентификатор занятия`, `Статус преподавателя на занятии`) 
				VALUES (email, class_id, 'Ассистент');
                SET result_status = 1;
			END IF;
		ELSE
        SET @prep_email = (SELECT `Почта преподавателя` FROM `Преподаватели на занятиях` WHERE (`Идентификатор занятия` LIKE class_id AND `Статус преподавателя на занятии` LIKE 'Основной'));
			IF (EXISTS (SELECT * FROM `Преподаватели на занятиях` WHERE (`Почта преподавателя` LIKE email AND `Идентификатор занятия` LIKE class_id))) THEN
				UPDATE `Преподаватели на занятиях` SET `Статус преподавателя на занятии` = 'Ассистент' WHERE (`Почта преподавателя` LIKE @prep_email AND `Идентификатор занятия` LIKE class_id);
                UPDATE `Преподаватели на занятиях` SET `Статус преподавателя на занятии` = 'Основной' WHERE (`Почта преподавателя` LIKE email AND `Идентификатор занятия` LIKE class_id);
			ELSE 
				UPDATE `Преподаватели на занятиях` SET `Статус преподавателя на занятии` = 'Ассистент' WHERE (`Почта преподавателя` LIKE @prep_email AND `Идентификатор занятия` LIKE class_id);
				INSERT INTO `Преподаватели на занятиях` (`Почта преподавателя` , `Идентификатор занятия`, `Статус преподавателя на занятии`) 
				VALUES (email, class_id, 'Основной');
			END IF;
            SET result_status = 1;
		END IF;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Удаление препода с занятия, A6 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A6_drop_tutor_from_class;
CREATE PROCEDURE A6_drop_tutor_from_class(IN email VARCHAR(45), IN class_id INT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Преподаватели на занятиях` WHERE (`Идентификатор занятия` LIKE class_id AND `Почта преподавателя` LIKE email))) THEN
		DELETE FROM `Преподаватели на занятиях` WHERE (`Почта преподавателя` LIKE email AND `Идентификатор занятия` LIKE class_id);
        SET result_status = 1;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Добавить преподавателя или изменить данные существующего, A7 */  /* QUALITY CONTROL 1 PASSED */
DELIMITER $$
DROP PROCEDURE IF EXISTS A7_set_tutor;
CREATE PROCEDURE A7_set_tutor(IN email VARCHAR(45), IN pasport_series VARCHAR(4), IN pasport_number VARCHAR(6), IN name_ VARCHAR(45), IN surname_ VARCHAR(45), IN last_name VARCHAR(45), IN supervising_flag TINYINT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS (SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN
		SET result_status = 0;
    ELSE
		IF (EXISTS (SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email)) THEN
			UPDATE `Преподаватели` SET `Серия паспорта` = pasport_series WHERE `Почта` LIKE email;
			UPDATE `Преподаватели` SET `Номер паспорта` = pasport_number WHERE `Почта` LIKE email;
			UPDATE `Преподаватели` SET `Имя` = name_ WHERE `Почта` LIKE email;
			UPDATE `Преподаватели` SET `Фамилия` = surname_ WHERE `Почта` LIKE email;
			UPDATE `Преподаватели` SET `Отчество` = last_name WHERE `Почта` LIKE email;
			UPDATE `Преподаватели` SET `Права на супервайзинг` = supervising_flag WHERE `Почта` LIKE email;
		ELSE
			INSERT INTO `Преподаватели` (`Почта`, `Серия паспорта`, `Номер паспорта`, `Имя`, `Фамилия`, `Отчество`, `Права на супервайзинг`, `Пароль`) 
		VALUES (email, pasport_series, pasport_number, name_, surname_, last_name, supervising_flag, (SELECT CONVERT(FLOOR(RAND()*1e8), CHAR(8))));
		END IF;
        SET result_status = 1;
	END IF;
END $$
DELIMITER;

/* Удаление препода, A8 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A8_drop_tutor;
CREATE PROCEDURE A8_drop_tutor(IN email VARCHAR(45), IN self_email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF NOT (email LIKE self_email) THEN
		IF ((EXISTS (SELECT * FROM `Кабинеты` WHERE `Почта ответственного` LIKE email)) OR (EXISTS (SELECT * FROM `Учащиеся` WHERE `Почта куратора` LIKE email))) THEN
			SET result_status = 0;
		ELSE
			DELETE FROM `Преподаватели` WHERE (`Почта` LIKE email);
			SET result_status = 1;
		END IF;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Добавить учащегося или изменить информацию о существющем, A9 */ /* QUALITY CONTROL FINAL PASSED */
DELIMITER $$  
DROP PROCEDURE IF EXISTS A9_set_student;
CREATE PROCEDURE A9_set_student (IN email VARCHAR(45), IN pasport_series VARCHAR(4), IN pasport_number VARCHAR(6), IN name_ VARCHAR(45), IN surname_ VARCHAR(45), IN last_name VARCHAR(45), IN superviser_email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF (NOT EXISTS (SELECT * FROM `Преподаватели` WHERE `Почта` LIKE superviser_email) OR EXISTS (SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email)) THEN
		SET result_status = 0;
    ELSE
		IF (EXISTS (SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN
			UPDATE `Учащиеся` SET `Серия паспорта` = pasport_series WHERE `Почта` LIKE email;
			UPDATE `Учащиеся` SET `Номер паспорта` = pasport_number WHERE `Почта` LIKE email;
			UPDATE `Учащиеся` SET `Имя` = name_ WHERE `Почта` LIKE email;
			UPDATE `Учащиеся` SET `Фамилия` = surname_ WHERE `Почта` LIKE email;
			UPDATE `Учащиеся` SET `Отчество` = last_name WHERE `Почта` LIKE email;
			UPDATE `Учащиеся` SET `Почта куратора` = superviser_email WHERE `Почта` LIKE email;
		ELSE
			INSERT INTO `Учащиеся` (`Почта` , `Серия паспорта`, `Номер паспорта`, `Имя`, `Фамилия`, `Отчество`, `Почта куратора`, `Пароль`) 
		VALUES (email, pasport_series, pasport_number, name_, surname_, last_name, superviser_email, (SELECT CONVERT(FLOOR(RAND()*1e8), CHAR(8))));
		END IF;
		SET result_status = 1;
	END IF;
END $$
DELIMITER;

/* Удаление студента, A10*/ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A10_drop_student;
CREATE PROCEDURE A10_drop_student(IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN
		DELETE FROM `Учащиеся` WHERE (`Почта` LIKE email);
        SET result_status = 1;
	ELSE
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Добавить кабинет или изменить информацию о кабинете, A11 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A11_set_room;
CREATE PROCEDURE A11_set_room (IN room_building VARCHAR(1), IN room_number VARCHAR(5), IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email)) THEN
		IF (EXISTS (SELECT * FROM `Кабинеты` WHERE (`Номер` LIKE room_number AND `Корпус` LIKE room_building))) THEN
			UPDATE `Кабинеты` SET `Почта ответственного` = email WHERE (`Номер` LIKE room_number AND `Корпус` LIKE room_building);
		ELSE
			INSERT INTO `Кабинеты` (`Корпус` , `Номер`, `Почта ответственного`) 
			VALUES (room_building, room_number, email);
		END IF;
        SET result_status = 1;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Удаление кабинета, A12 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A12_drop_room;
CREATE PROCEDURE A12_drop_room (IN room_building VARCHAR(1), IN room_number VARCHAR(5), OUT result_status TINYINT)
BEGIN
	IF (EXISTS (SELECT * FROM `Кабинеты` WHERE (`Корпус` LIKE room_building AND `Номер` LIKE room_number))) THEN
		IF (EXISTS (SELECT * FROM `Занятия` WHERE (`Корпус кабинета` LIKE room_building AND `Номер кабинета` LIKE room_number))) THEN
			SET result_status = 0;
		ELSE
			DELETE FROM `Кабинеты` WHERE (`Корпус` LIKE room_building AND `Номер` LIKE room_number);
			SET result_status = 1;
		END IF;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Добавить учащегося на занятие или изменить статус занятия, A13 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$
DROP PROCEDURE IF EXISTS A13_set_student_to_class;
CREATE PROCEDURE A13_set_student_to_class(IN email VARCHAR(45), IN class_id INT, IN priority VARCHAR(7), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email) AND EXISTS(SELECT * FROM `Занятия` WHERE `Идентификатор` LIKE class_id)) THEN
		IF (priority LIKE 'Высокий') THEN 
			SET @class_date = (SELECT `Дата` FROM `Занятия` WHERE (`Идентификатор` LIKE class_id));
			SET @class_time = (SELECT `Время` FROM `Занятия` WHERE (`Идентификатор` LIKE class_id));
			IF (EXISTS (SELECT * FROM `Учащиеся на занятиях` WHERE (`Почта учащегося` LIKE email AND `Идентификатор занятия` LIKE class_id))) THEN
				IF (NOT EXISTS (SELECT * FROM (
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата`, `Время` FROM `Занятия`) AS `Часть 1`
					JOIN
					(SELECT * FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 2`
					USING (`Идентификатор занятия`)
				) WHERE (`Приоритет` LIKE 'Высокий' AND `Дата` LIKE @class_date AND ABS(TIME_TO_SEC(`Время`) - TIME_TO_SEC(@class_time)) < 7200))) THEN
					UPDATE `Учащиеся на занятиях` SET `Приоритет` = 'Высокий' WHERE (`Почта учащегося` LIKE email AND `Идентификатор занятия` LIKE class_id);
					SET result_status = 1;
				ELSE
					SET result_status = 0;
				END IF;
			ELSE
				IF (NOT EXISTS (SELECT * FROM (
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата`, `Время` FROM `Занятия`) AS `Часть 1`
					JOIN
					(SELECT * FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 2`
					USING (`Идентификатор занятия`)
				) WHERE (`Приоритет` LIKE 'Высокий' AND `Дата` LIKE @class_date AND ABS(TIME_TO_SEC(`Время`) - TIME_TO_SEC(@class_time)) < 7200))) THEN
					INSERT INTO `Учащиеся на занятиях` (`Почта учащегося` , `Идентификатор занятия`, `Приоритет`) 
					VALUES (email, class_id, 'Высокий');
					SET result_status = 1;
				ELSE
					SET result_status = 0;
				END IF;
			END IF;
		ELSE
			IF (EXISTS (SELECT * FROM `Учащиеся на занятиях` WHERE (`Почта учащегося` LIKE email AND `Идентификатор занятия` LIKE class_id))) THEN
				UPDATE `Учащиеся на занятиях` SET `Приоритет` = priority WHERE (`Почта учащегося` LIKE email AND `Идентификатор занятия` LIKE class_id);
			ELSE
				INSERT INTO `Учащиеся на занятиях` (`Почта учащегося` , `Идентификатор занятия`, `Приоритет`) 
				VALUES (email, class_id, priority);
			END IF;
			SET result_status = 1;
		END IF;
	ELSE
		SET result_status = 0;
	END IF;
END $$
#DELIMITER;

/* Удаление учащегося с занятия, A14 */  /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A14_drop_student_from_class;
CREATE PROCEDURE A14_drop_student_from_class(IN email VARCHAR(45), IN class_id INT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Учащиеся на занятиях` WHERE (`Идентификатор занятия` LIKE class_id AND `Почта учащегося` LIKE email))) THEN
		DELETE FROM `Учащиеся на занятиях` WHERE (`Почта учащегося` LIKE email AND `Идентификатор занятия` LIKE class_id);
        SET result_status = 1;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Список паролей, A15 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS A15_get_passwords;
CREATE PROCEDURE A15_get_passwords(OUT result_status TINYINT)
BEGIN
	(SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Пароль`, 'Преподаватель' AS `Статус` FROM `Преподаватели`)
	UNION
	(SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Пароль`, 'Учащийся' AS `Статус` FROM `Учащиеся`)
	ORDER BY `Статус`, `Фамилия`, `Имя`, `Отчество`;
    SET result_status = 1;
END $$
DELIMITER;

/* Расписание студента или преподавателя, U1 */  /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U1_get_personal_timetable;
CREATE PROCEDURE U1_get_personal_timetable(IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
    IF (EXISTS(SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email)) THEN
		IF (EXISTS(SELECT * FROM `Преподаватели на занятиях` WHERE `Почта преподавателя` LIKE email)) THEN 
			SELECT `Название дисциплины`, `Дата`, `Время`, `Корпус кабинета`, `Номер кабинета`, `Статус преподавателя на занятии` FROM (
				(SELECT * FROM `Преподаватели на занятиях` WHERE `Почта преподавателя` LIKE email) AS `Часть 1`
				JOIN
				(SELECT `Идентификатор` AS `Идентификатор занятия`, `Название дисциплины`, `Дата`, `Время`, `Корпус кабинета`, `Номер кабинета` FROM `Занятия`) AS `Часть 2`
				USING(`Идентификатор занятия`)
			) ORDER BY `Дата` ASC, `Время` ASC;
            SET result_status = 1;
		ELSE
			SET result_status = 0;
		END IF;
    END IF;
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN 
		IF (EXISTS(SELECT * FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email)) THEN 
			SELECT `Название дисциплины`, `Дата`, `Время`, `Корпус кабинета`, `Номер кабинета`,  `Приоритет` AS `Приоритет занятия`FROM (
				(SELECT * FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 1`
				JOIN
				(SELECT `Идентификатор` AS `Идентификатор занятия`, `Название дисциплины`, `Дата`, `Время`, `Корпус кабинета`, `Номер кабинета` FROM `Занятия`) AS `Часть 2`
				USING(`Идентификатор занятия`)
			) ORDER BY `Дата` ASC, `Время` ASC;
			SET result_status = 1;
		ELSE 
			SET result_status = 0;
		END IF;
    END IF;
    IF NOT(EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email) OR EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN
		SET result_status = 0;
    END IF;
END $$
DELIMITER;

/* Расписание кабинета, U2 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U2_get_room_timetable;
CREATE PROCEDURE U2_get_room_timetable(IN room_building VARCHAR(1), IN room_number VARCHAR(5), OUT result_status TINYINT)
BEGIN
	IF (EXISTS (SELECT * FROM `Кабинеты` WHERE (`Корпус` LIKE room_building AND `Номер` LIKE room_number))) THEN
		IF (EXISTS (SELECT * FROM `Занятия` WHERE (`Корпус кабинета` LIKE room_building AND `Номер кабинета` LIKE room_number))) THEN
			SELECT `Название дисциплины`, `Дата`, `Время` FROM (
				(SELECT * FROM `Кабинеты` WHERE (`Корпус` LIKE room_building AND `Номер` LIKE room_number)) AS `Часть 1`
				JOIN
				(SELECT `Идентификатор` AS `Идентификатор занятия`, `Название дисциплины`, `Дата`, `Время`, `Номер кабинета` AS `Номер`, `Корпус кабинета` AS `Корпус` FROM `Занятия`) AS `Часть 2`
				USING(`Номер`, `Корпус`)
			) ORDER BY `Дата` ASC, `Время` ASC;
			SET result_status = 1;
		ELSE 
			SET result_status = 0;
		END IF;
	ELSE
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Список занятий и информация об основных преподавателях, их ведущих, U3 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U3_get_classes_info;
CREATE PROCEDURE U3_get_classes_info(OUT result_status TINYINT)
BEGIN
	IF (EXISTS (SELECT * FROM `Занятия`)) THEN 
		SELECT * FROM (
			(SELECT * FROM `Занятия`) AS `Часть 1`
			JOIN
			(SELECT `Почта преподавателя` AS `Почта основного преподавателя`, `Идентификатор занятия` AS `Идентификатор` FROM `Преподаватели на занятиях` WHERE `Статус Преподавателя на занятии` LIKE 'Основной') AS `Часть 2`
			USING (`Идентификатор`)
			JOIN
			(SELECT `Почта` AS `Почта основного преподавателя`, `Фамилия` AS `Фамилия основного преподавателя`, `Имя` AS `Имя основного преподавателя` , `Отчество` AS `Отчество основного преподавателя` FROM `Преподаватели`) AS `Часть 3`
			USING (`Почта основного преподавателя`)
		) ORDER BY `Дата` ASC, `Время` ASC;
        SET result_status = 1;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Список преподавателей, ведущих занятие, U4 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U4_get_class_tutors_info;
CREATE PROCEDURE U4_get_class_tutors_info(IN class_id INT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS (SELECT * FROM `Занятия` WHERE `Идентификатор` LIKE class_id)) THEN 
		SELECT * FROM (
			(SELECT `Почта преподавателя`, `Статус преподавателя на занятии` FROM `Преподаватели на занятиях` WHERE `Идентификатор занятия` LIKE class_id) AS `Часть 1`
			JOIN
			(SELECT `Почта` AS `Почта преподавателя`, `Фамилия` AS `Фамилия преподавателя`, `Имя` AS `Имя преподавателя`, `Отчество` AS `Отчество преподавателя` FROM `Преподаватели`) AS `Часть 2`
			USING (`Почта преподавателя`)
		) ORDER BY `Статус преподавателя на занятии` DESC, `Фамилия преподавателя` ASC, `Имя преподавателя` ASC, `Отчество преподавателя` ASC;
        SET result_status = 1;
	ELSE
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Конфликты в расписании студента или преподавателя, U5 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U5_get_conflict_classes;
CREATE PROCEDURE U5_get_conflict_classes(IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email)) THEN 
		IF (EXISTS(
			SELECT DISTINCT * FROM (
				(SELECT * FROM (
					(SELECT * FROM `Преподаватели на занятиях` WHERE `Почта преподавателя` LIKE email) AS `Часть 1.1`
					JOIN
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата` AS `Дата занятия`, `Время` AS `Время занятия`, `Название дисциплины` FROM `Занятия`) AS `Часть 1.2`
					USING (`Идентификатор занятия`)
				)) AS `Часть 1`
				JOIN
				(SELECT * FROM (
					(SELECT `Идентификатор занятия` AS `Идентификатор 2` FROM `Преподаватели на занятиях` WHERE `Почта преподавателя` LIKE email) AS `Часть 2.1`
					JOIN
					(SELECT  `Идентификатор` AS `Идентификатор 2`, `Дата` AS `Дата занятия`, `Время` AS `Время 2` FROM `Занятия`) AS `Часть 2.2`
					USING (`Идентификатор 2`)
				)) AS `Часть 2`
				USING(`Дата занятия`)
			) WHERE ( (NOT(`Идентификатор 2` LIKE `Идентификатор занятия`)) AND (ABS(TIME_TO_SEC(`Время занятия`) - TIME_TO_SEC(`Время 2`)) < 7200) )
        )) THEN
			SELECT DISTINCT `Идентификатор занятия`, `Дата занятия`, `Время занятия`, `Название дисциплины`, `Статус преподавателя на занятии` FROM (
				(SELECT * FROM (
					(SELECT * FROM `Преподаватели на занятиях` WHERE `Почта преподавателя` LIKE email) AS `Часть 1.1`
					JOIN
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата` AS `Дата занятия`, `Время` AS `Время занятия`, `Название дисциплины` FROM `Занятия`) AS `Часть 1.2`
					USING (`Идентификатор занятия`)
				)) AS `Часть 1`
				JOIN
				(SELECT * FROM (
					(SELECT `Идентификатор занятия` AS `Идентификатор 2` FROM `Преподаватели на занятиях` WHERE `Почта преподавателя` LIKE email) AS `Часть 2.1`
					JOIN
					(SELECT  `Идентификатор` AS `Идентификатор 2`, `Дата` AS `Дата занятия`, `Время` AS `Время 2` FROM `Занятия`) AS `Часть 2.2`
					USING (`Идентификатор 2`)
				)) AS `Часть 2`
				USING(`Дата занятия`)
			) WHERE ( (NOT(`Идентификатор 2` LIKE `Идентификатор занятия`)) AND (ABS(TIME_TO_SEC(`Время занятия`) - TIME_TO_SEC(`Время 2`)) < 7200) )
			ORDER BY `Дата занятия` ASC, `Время занятия` ASC;
            SET result_status = 1;
		ELSE 
			SET result_status = 0;
		END IF;
    END IF;
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN 
		IF (EXISTS(
			SELECT * FROM (
				(SELECT * FROM (
					(SELECT * FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 1.1`
					JOIN
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата` AS `Дата занятия`, `Время` AS `Время занятия`, `Название дисциплины` FROM `Занятия`) AS `Часть 1.2`
					USING (`Идентификатор занятия`)
				)) AS `Часть 1`
				JOIN
				(SELECT * FROM (
					(SELECT `Идентификатор занятия` AS `Идентификатор 2` FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 2.1`
					JOIN
					(SELECT  `Идентификатор` AS `Идентификатор 2`, `Дата` AS `Дата занятия`, `Время` AS `Время 2` FROM `Занятия`) AS `Часть 2.2`
					USING (`Идентификатор 2`)
				)) AS `Часть 2`
				USING(`Дата занятия`)
            ) WHERE ( (NOT(`Идентификатор 2` LIKE `Идентификатор занятия`)) AND (ABS(TIME_TO_SEC(`Время занятия`) - TIME_TO_SEC(`Время 2`)) < 7200) )
        )) THEN
			SELECT DISTINCT `Идентификатор занятия`, `Дата занятия`, `Время занятия`, `Название дисциплины`, `Приоритет` AS `Приоритет посещения занятия` FROM (
				(SELECT * FROM (
					(SELECT * FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 1.1`
					JOIN
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата` AS `Дата занятия`, `Время` AS `Время занятия`, `Название дисциплины` FROM `Занятия`) AS `Часть 1.2`
					USING (`Идентификатор занятия`)
				)) AS `Часть 1`
				JOIN
				(SELECT * FROM (
					(SELECT `Идентификатор занятия` AS `Идентификатор 2` FROM `Учащиеся на занятиях` WHERE `Почта учащегося` LIKE email) AS `Часть 2.1`
					JOIN
					(SELECT  `Идентификатор` AS `Идентификатор 2`, `Дата` AS `Дата занятия`, `Время` AS `Время 2` FROM `Занятия`) AS `Часть 2.2`
					USING (`Идентификатор 2`)
				)) AS `Часть 2`
				USING(`Дата занятия`)
			) WHERE ( (NOT(`Идентификатор 2` LIKE `Идентификатор занятия`)) AND (ABS(TIME_TO_SEC(`Время занятия`) - TIME_TO_SEC(`Время 2`)) < 7200) )
			ORDER BY `Дата занятия` ASC, `Время занятия` ASC;
            SET result_status = 1;
		ELSE
			SET result_status = 0;
		END IF;
    END IF;
    IF NOT(EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email) OR EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN
		SET result_status = 0;
    END IF;
END $$
DELIMITER;

/* Список учащихся, записанных на занятие, U7 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U7_get_class_students_info;
CREATE PROCEDURE U7_get_class_students_info(IN class_id INT, OUT result_status TINYINT)
BEGIN
	IF (EXISTS (SELECT * FROM `Учащиеся на занятиях` WHERE `Идентификатор занятия` LIKE class_id)) THEN 
		SELECT * FROM (
			(SELECT `Почта` AS `Почта учащегося`, `Фамилия` AS `Фамилия учащегося`, `Имя` AS `Имя учащегося`, `Отчество` AS `Отчество учащегося` FROM `Учащиеся`) AS `Часть 1`
			JOIN
			(SELECT `Почта учащегося`, `Приоритет` AS `Приоритет занятия` FROM `Учащиеся на занятиях` WHERE `Идентификатор занятия` LIKE class_id) AS `Часть 2`
			USING (`Почта учащегося`)
		) ORDER BY `Фамилия учащегося` ASC, `Имя учащегося` ASC, `Отчество учащегося` ASC;
        SET result_status = 1;
	ELSE
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Информация об учащемся или преподавателе, U6 */ /* QUALITY CONTROL FINAL PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS U6_get_exact_info;
CREATE PROCEDURE U6_get_exact_info(IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email)) THEN 
		SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Серия паспорта`, `Номер паспорта`, `Права на супервайзинг`,
        IFNULL(`Всего занятий в сетке`, 0) AS `Всего занятий в сетке`, IFNULL(`Занятий основным преподавателем`, 0) AS `Занятий основным преподавателем`,
        IFNULL(`Занятий ассистентом`, 0) AS `Занятий ассистентом`, IFNULL(`Число курируемых`, 0) AS `Число курируемых`,
        IFNULL(`Число кабинетов`, 0) AS `Число кабинетов под ответственностью`  FROM (
			(SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email) AS `Часть 1`
            LEFT JOIN
            (SELECT `Почта ответственного` AS `Почта`, COUNT(*) AS `Число кабинетов` FROM `Кабинеты` GROUP BY `Почта ответственного`) AS `Часть 2`
			USING(`Почта`)
            LEFT JOIN
			(SELECT `Почта куратора` AS `Почта`, COUNT(*) AS `Число курируемых` FROM `Учащиеся` GROUP BY `Почта куратора`) AS `Часть 3`
			USING (`Почта`)
			LEFT JOIN 
			(SELECT `Почта преподавателя` AS `Почта`,  COUNT(*) AS `Всего занятий в сетке` FROM `Преподаватели на занятиях` GROUP BY `Почта преподавателя`) AS `Часть 4`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта преподавателя` AS `Почта`, `Статус преподавателя на занятии` AS `Статус`, COUNT(*) AS `Занятий основным преподавателем` FROM `Преподаватели на занятиях` GROUP BY `Почта преподавателя`, `Статус преподавателя на занятии` HAVING (`Статус` LIKE 'Основной')) AS `Часть 5`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта преподавателя` AS `Почта`, `Статус преподавателя на занятии` AS `Статус`,  COUNT(*) AS `Занятий ассистентом` FROM `Преподаватели на занятиях` GROUP BY `Почта преподавателя`, `Статус преподавателя на занятии` HAVING (`Статус` LIKE 'Ассистент')) AS `Часть 6`
			USING(`Почта`)
    );
    END IF;
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN 
		SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Серия паспорта`, `Номер паспорта`, `Почта куратора`,
		IFNULL(`Всего занятий в сетке`, 0) AS `Всего занятий в сетке`, IFNULL(`Занятий с высоким приоритетом`, 0) AS `Занятий с высоким приоритетом`, 
		IFNULL(`Занятий со средним приоритетом`, 0) AS `Занятий со средним приоритетом`, IFNULL(`Занятий с низким приоритетом`, 0) AS `Занятий с низким приоритетом` FROM (
			(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email) AS `Часть 1`
			LEFT JOIN 
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, COUNT(*) AS `Всего занятий в сетке` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`) AS `Часть 2`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`,  COUNT(*) AS `Занятий с высоким приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Высокий')) AS `Часть 3`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`,  COUNT(*) AS `Занятий со средним приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Средний')) AS `Часть 4`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`,  COUNT(*) AS `Занятий с низким приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Низкий')) AS `Часть 5`
			USING(`Почта`)
    );
    END IF;
    IF NOT(EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email) OR EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта` LIKE email)) THEN
		SET result_status = 0;
    END IF;
END $$
DELIMITER;

/* Информация о подопечных, T1 */ /* QUALITY CONTROL FINAL PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS T1_get_info_on_related_students;
CREATE PROCEDURE T1_get_info_on_related_students(IN email VARCHAR(45), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Преподаватели` WHERE `Почта` LIKE email) AND EXISTS(SELECT * FROM `Учащиеся` WHERE `Почта куратора` LIKE email)) THEN 
		SELECT `Фамилия`, `Имя`, `Отчество`, `Почта`, `Серия паспорта`, `Номер паспорта`, `Почта куратора`,
		IFNULL(`Всего занятий в сетке`, 0) AS `Всего занятий в сетке`, IFNULL(`Занятий с высоким приоритетом`, 0) AS `Занятий с высоким приоритетом`, 
        IFNULL(`Занятий со средним приоритетом`, 0) AS `Занятий со средним приоритетом`, IFNULL(`Занятий с низким приоритетом`, 0) AS `Занятий с низким приоритетом` FROM (
			(SELECT * FROM `Учащиеся` WHERE `Почта куратора` LIKE email) AS `Часть 1`
			LEFT JOIN 
			(SELECT `Почта учащегося` AS `Почта`, COUNT(*) AS `Всего занятий в сетке` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`) AS `Часть 2`
			USING(`Почта`)
			LEFT JOIN
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, COUNT(*) AS `Занятий с высоким приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Высокий')) AS `Часть 3`
			USING(`Почта`)
			LEFT JOIN 
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, COUNT(*) AS `Занятий со средним приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Средний')) AS `Часть 4`
			USING(`Почта`)
			LEFT JOIN 
			(SELECT `Почта учащегося` AS `Почта`, `Приоритет`, COUNT(*) AS `Занятий с низким приоритетом` FROM `Учащиеся на занятиях` GROUP BY `Почта учащегося`, `Приоритет` HAVING (`Приоритет` LIKE 'Низкий')) AS `Часть 5`
			USING(`Почта`)
		);
        SET result_status = 1;
    ELSE
		SET result_status = 0;
    END IF;
END $$
DELIMITER;

/* Назначить подопечного на занятие или изменить приоритет, T2 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$
DROP PROCEDURE IF EXISTS T2_set_related_student_to_class;
CREATE PROCEDURE T2_set_related_student_to_class(IN students_email VARCHAR(45), IN tutors_email VARCHAR(45), IN class_id INT, IN priority VARCHAR(7), OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE (`Почта` LIKE students_email AND `Почта куратора` LIKE tutors_email))) THEN
		IF (priority LIKE 'Высокий') THEN 
			SET @class_date = (SELECT `Дата` FROM `Занятия` WHERE (`Идентификатор` LIKE class_id));
			SET @class_time = (SELECT `Время` FROM `Занятия` WHERE (`Идентификатор` LIKE class_id));
			IF (EXISTS (SELECT * FROM `Учащиеся на занятиях` WHERE (`Почта учащегося` LIKE students_email AND `Идентификатор занятия` LIKE class_id))) THEN
				IF (NOT EXISTS (SELECT * FROM (
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата`, `Время` FROM `Занятия`) AS `Часть 1`
					JOIN
					(SELECT * FROM `Учащиеся на занятиях`) AS `Часть 2`
					USING (`Идентификатор занятия`)
				) WHERE (`Приоритет` LIKE 'Высокий' AND `Дата` LIKE @class_date AND ABS(TIME_TO_SEC(`Время`) - TIME_TO_SEC(@class_time)) < 7200))) THEN
					UPDATE `Учащиеся на занятиях` SET `Приоритет` = 'Высокий' WHERE (`Почта учащегося` LIKE students_email AND `Идентификатор занятия` LIKE class_id);
					SET result_status = 1;
				ELSE
					SET result_status = 0;
				END IF;
			ELSE
				IF (NOT EXISTS (SELECT * FROM (
					(SELECT `Идентификатор` AS `Идентификатор занятия`, `Дата`, `Время` FROM `Занятия`) AS `Часть 1`
					JOIN
					(SELECT * FROM `Учащиеся на занятиях`) AS `Часть 2`
					USING (`Идентификатор занятия`)
				) WHERE (`Приоритет` LIKE 'Высокий' AND `Дата` LIKE @class_date AND ABS(TIME_TO_SEC(`Время`) - TIME_TO_SEC(@class_time)) < 7200))) THEN
					INSERT INTO `Учащиеся на занятиях` (`Почта учащегося` , `Идентификатор занятия`, `Приоритет`) 
					VALUES (students_email, class_id, 'Высокий');
					SET result_status = 1;
				ELSE
					SET result_status = 0;
				END IF;
			END IF;
		ELSE
			IF (EXISTS (SELECT * FROM `Учащиеся на занятиях` WHERE (`Почта учащегося` LIKE students_email AND `Идентификатор занятия` LIKE class_id))) THEN
				UPDATE `Учащиеся на занятиях` SET `Приоритет` = priority WHERE (`Почта учащегося` LIKE students_email AND `Идентификатор занятия` LIKE class_id);
			ELSE
				INSERT INTO `Учащиеся на занятиях` (`Почта учащегося` , `Идентификатор занятия`, `Приоритет`) 
				VALUES (students_email, class_id, priority);
			END IF;
            SET result_status = 1;
		END IF;
	ELSE 
		SET result_status = 0;
	END IF;
END $$
DELIMITER;

/* Удаление подопечного с занятия, T3 */ /* QUALITY CONTROL 1 PASSED */
DELIMITER $$ 
DROP PROCEDURE IF EXISTS T3_drop_related_student_from_class;
CREATE PROCEDURE T3_drop_related_student_from_class(IN students_email VARCHAR(45), IN tutors_email VARCHAR(45), IN class_id INT,  OUT result_status TINYINT)
BEGIN
	IF (EXISTS(SELECT * FROM `Учащиеся` WHERE (`Почта` LIKE students_email AND `Почта куратора` LIKE tutors_email))) THEN
		DELETE FROM `Учащиеся на занятиях` WHERE (`Почта учащегося` LIKE students_email AND `Идентификатор занятия` LIKE class_id);
        SET result_status = 1;
	ELSE
		SET result_status = 0;
    END IF;
END $$
DELIMITER;

/* Контроль целостности при удалении преподавателя с занятия, SUP1 */
DELIMITER $$
DROP TRIGGER IF EXISTS SUP1_control;
CREATE TRIGGER SUP1_control AFTER DELETE ON `Преподаватели на занятиях`
FOR EACH ROW BEGIN
	IF (OLD.`Статус преподавателя на занятии` LIKE 'Основной') THEN
		DELETE FROM `Занятия` WHERE `Идентификатор` LIKE OLD.`Идентификатор занятия`;
	END IF;
END $$

/* Удаление старых занятий из сетки, SUP2 */
DROP EVENT IF EXISTS SUP2_delete_ancient_classes;
CREATE EVENT SUP2_delete_ancient_classes
	ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL EVERY 1 DAY
    DO DELETE FROM `Занятия` WHERE `Дата` <  CURRENT_DATE ();