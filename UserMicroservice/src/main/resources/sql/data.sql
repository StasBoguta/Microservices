INSERT INTO users(email, password, role)
VALUES ('admin@email.com', '$2a$12$/APuI3.qU/7nfz1zSo.T0eNAi35Hc6i9CDuRszsAXJUnHxV.dJtvG', 'ADMIN'),
       ('mentor@email.com', '$2a$12$/APuI3.qU/7nfz1zSo.T0eNAi35Hc6i9CDuRszsAXJUnHxV.dJtvG', 'MENTOR'),
       ('mentee@email.com', '$2a$12$/APuI3.qU/7nfz1zSo.T0eNAi35Hc6i9CDuRszsAXJUnHxV.dJtvG', 'MENTEE');

INSERT INTO mentors(first_name, last_name, rating, user_id)
VALUES ('John', 'Doe', 4, 2);

INSERT INTO mentees(first_name, last_name, user_id)
VALUES ('Bob', 'Ross', 3);