delete from user;
delete from therapy;
delete from patient_card;

insert into user (id, login, pass, first_name, last_name, birthday, role, is_Active) values
('1', 'doctor', '$2y$12$cgDbs4Q9skVO156JDSUypeC67fixHAGT7H9uhmWmnjelJgwF8PEp6', 'Ivan', 'Ivanov', '1900-01-01', 'DOCTOR', '1'),
('2', 'patient', '$2y$12$GoDiB9wN667mqXqyy0qIS.88i1nCOc/uP/W.D030Qs944L.dDXgle', 'Petr', 'Petrov', '1998-02-03', 'PATIENT', '1'),
('3', 'admin', '$2y$12$jWVGw8LnZdAiHbStrgvrNOz8/0M139wxo6PpVN2YQIfqMjJ6KrdIy', 'Drakula', 'Pensilvanskiy', '1800-01-01', 'ADMIN', '1'),
('4', 'del', 'del', 'Del', 'Delov', '2001-01-01', 'PATIENT', '1'),
('5', 'another_patient', 'del', 'Del', 'Delov', '2001-01-01', 'PATIENT', '1'),
('6', 'patient_without_card', 'del', 'Del', 'Delov', '2001-01-01', 'PATIENT', '1');

insert into patient_card (id, patient_id) values
('1', '2'),
('2', '5');

insert into therapy(id, description, start_date, end_date, patient_card_id, doctor_id) values
('1', 'Some description', '2021-01-01', '2025-01-01', '1', '1');


