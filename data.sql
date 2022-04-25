INSERT INTO email (email_address) VALUES ('laurentgina@mail.com'),
('sophiefoncek@mail.com'),
('agathefeeling@mail.com');
INSERT INTO phone (phone_number) VALUES ('65455-8547'),
('658965-7854'),
('0101425888');
INSERT INTO home
(
   address,
   city,
   zip
)
VALUES
(
   '13 rue de la Paix',
   'Paris',
   '75000'
),

(
   'rue du chateau',
   'Ville',
   '65458'
),

(
   '3 allée des Lilas',
   'village',
   '40210'
);
INSERT INTO allergie (allergie_name) VALUES ('Pollen'),
('Lait'),
('Chats');
INSERT INTO medication (medication_name) VALUES ('Aspririne 2 par jours '),
('Meicament 100mg'),
('medoc bidul 2 piqures par jour');
INSERT INTO person
(
   first_name,
   last_name,
   email_id,
   phone_id,
   home_id
)
VALUES
(
   'Laurent',
   'GINA',
   1,
   2,
   1
),

(
   'Sophie',
   'FONCEK',
   1,
   3,
   2
),

(
   'Agathe',
   'FEELING',
   2,
   1,
   3
),

(
   'Hugo',
   'FEELING',
   3,
   1,
   1
);