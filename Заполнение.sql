USE `timetable_infosystem`;

/* Tutors */

CALL A7_set_tutor('abdisunov@mybase.ru', '4509', '235097', 'Андрей', 'Дисунов', 'Борисович', '0', @result_status);
CALL A7_set_tutor('aivasylyev@mybase.ru', '4511', '131755', 'Антон', 'Васильев', 'Иванович', '0', @result_status);
CALL A7_set_tutor('mnkrylyev@mybase.ru', '4511', '921471', 'Матвей', 'Крыльев', 'Николаевич', '0', @result_status);
CALL A7_set_tutor('asvasylyev@mybase.ru', '4507', '125981', 'Анатолий', 'Васильев', 'Сергеевич', '0', @result_status);
CALL A7_set_tutor('parshenov@mybase.ru', '4513', '925184', 'Пётр', 'Рженов', 'Алексеевич', '0', @result_status);
CALL A7_set_tutor('osyaga@mybase.ru', '4519', '236164', 'Ольга', 'Яга', 'Станиславовна', '0', @result_status);
CALL A7_set_tutor('nplukina@mybase.ru', '4507', '128811', 'Наталья', 'Лукина', 'Петровна', '0', @result_status);
CALL A7_set_tutor('apchikov@mybase.ru', '4519', '648921', 'Александр', 'Чиков', 'Павлович', '1', @result_status);


/* Students */

CALL A9_set_student('sakuznetsov@edu.mybase.ru', '4519', '689214', 'Сергей', 'Кузнецов', 'Алексеевич', 'apchikov@mybase.ru', @result_status);
CALL A9_set_student('iitolmachov@edu.mybase.ru', '4519', '235126', 'Иван', 'Толмачёв', 'Иванович', 'apchikov@mybase.ru', @result_status);
CALL A9_set_student('kpkuznetsov@edu.mybase.ru', '4518', '622412', 'Константин', 'Кузнецов', 'Павлович', 'asvasylyev@mybase.ru', @result_status);
CALL A9_set_student('easamorov@edu.mybase.ru', '4517', '689233', 'Евгений', 'Саморов', 'Алексеевич', 'nplukina@mybase.ru', @result_status);
CALL A9_set_student('aifomin@edu.mybase.ru', '4520', '681444', 'Андрей', 'Фомин', 'Игоревич', 'aivasylyev@mybase.ru', @result_status);


/* Rooms */

CALL A11_set_room ('А', '127', 'nplukina@mybase.ru', @result_status);
CALL A11_set_room ('А', '128', 'nplukina@mybase.ru', @result_status);
CALL A11_set_room ('А', '101', 'nplukina@mybase.ru', @result_status);
CALL A11_set_room ('Б', '113', 'aivasylyev@mybase.ru', @result_status);
CALL A11_set_room ('Б', '313', 'nplukina@mybase.ru', @result_status);
CALL A11_set_room ('Б', '121', 'mnkrylyev@mybase.ru', @result_status);
CALL A11_set_room ('Б', '822', 'apchikov@mybase.ru', @result_status);


/* CLasses */

CALL A3_set_class ('10.10.2020', '13:00', 'Органическая химия', 'Б', '113', 'aivasylyev@mybase.ru', @result_status);
CALL A3_set_class ('10.10.2020', '15:00', 'Органическая химия', 'Б', '113', 'aivasylyev@mybase.ru', @result_status);
CALL A3_set_class ('10.10.2020', '19:00', 'Основы радиоуглеродного анализа', 'Б', '113', 'abdisunov@mybase.ru', @result_status);
CALL A3_set_class ('10.10.2020', '12:00', 'Линейная алгебра', 'Б', '822', 'apchikov@mybase.ru', @result_status);
CALL A3_set_class ('10.10.2020', '14:30', 'Математический анализ', 'Б', '822', 'apchikov@mybase.ru', @result_status);
CALL A3_set_class ('10.10.2020', '17:00', 'Дифференциальные уравнения', 'А', '128', 'nplukina@mybase.ru', @result_status);
CALL A3_set_class ('11.10.2020', '12:00', 'Математический анализ', 'Б', '822', 'apchikov@mybase.ru', @result_status);
CALL A3_set_class ('11.10.2020', '14:30', 'Математический анализ', 'Б', '822', 'apchikov@mybase.ru', @result_status);
CALL A3_set_class ('11.10.2020', '16:00', 'Алгебраическая топология и её приложения', 'А', '101', 'mnkrylyev@mybase.ru', @result_status);
CALL A3_set_class ('11.10.2020', '17:00', 'Линейная алгебра', 'Б', '822', 'apchikov@mybase.ru', @result_status);
CALL A3_set_class ('11.10.2020', '17:30', 'Основы радиоуглеродного анализа', 'Б', '113', 'abdisunov@mybase.ru', @result_status);
CALL A3_set_class ('11.10.2020', '19:00', 'Органическая химия', 'Б', '113', 'aivasylyev@mybase.ru', @result_status);



/* Tutors on class */
CALL A5_set_tutor_to_class('aivasylyev@mybase.ru', 3, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('aivasylyev@mybase.ru', 11, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 4, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 5, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 6, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 7, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 8, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 9, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('osyaga@mybase.ru', 10, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('apchikov@mybase.ru', 6, 'Ассистент', @result_status);
CALL A5_set_tutor_to_class('apchikov@mybase.ru', 9, 'Ассистент', @result_status);

/* Students on class */
CALL A13_set_student_to_class('sakuznetsov@edu.mybase.ru', 5, 'Высокий', @result_status);
CALL A13_set_student_to_class('sakuznetsov@edu.mybase.ru', 7, 'Высокий', @result_status);
CALL A13_set_student_to_class('sakuznetsov@edu.mybase.ru', 8, 'Высокий', @result_status);
CALL A13_set_student_to_class('sakuznetsov@edu.mybase.ru', 4, 'Средний', @result_status);
CALL A13_set_student_to_class('sakuznetsov@edu.mybase.ru', 10, 'Средний', @result_status);
CALL A13_set_student_to_class('sakuznetsov@edu.mybase.ru', 9, 'Низкий', @result_status);
CALL A13_set_student_to_class('easamorov@edu.mybase.ru', 4, 'Средний', @result_status);
CALL A13_set_student_to_class('easamorov@edu.mybase.ru', 10, 'Средний', @result_status);
CALL A13_set_student_to_class('easamorov@edu.mybase.ru', 9, 'Высокий', @result_status);
CALL A13_set_student_to_class('iitolmachov@edu.mybase.ru', 6, 'Высокий', @result_status);
CALL A13_set_student_to_class('iitolmachov@edu.mybase.ru', 9, 'Высокий', @result_status);
CALL A13_set_student_to_class('aifomin@edu.mybase.ru', 1, 'Низкий', @result_status);
CALL A13_set_student_to_class('aifomin@edu.mybase.ru', 2, 'Низкий', @result_status);
CALL A13_set_student_to_class('aifomin@edu.mybase.ru', 3, 'Высокий', @result_status);
CALL A13_set_student_to_class('aifomin@edu.mybase.ru', 13, 'Низкий', @result_status);
CALL A13_set_student_to_class('aifomin@edu.mybase.ru', 12, 'Высокий', @result_status);
CALL A13_set_student_to_class('kpkuznetsov@edu.mybase.ru', 1, 'Высокий', @result_status);
CALL A13_set_student_to_class('kpkuznetsov@edu.mybase.ru', 2, 'Высокий', @result_status);
CALL A13_set_student_to_class('kpkuznetsov@edu.mybase.ru', 3, 'Высокий', @result_status);
CALL A13_set_student_to_class('kpkuznetsov@edu.mybase.ru', 13, 'Высокий', @result_status);
CALL A13_set_student_to_class('kpkuznetsov@edu.mybase.ru', 12, 'Высокий', @result_status);