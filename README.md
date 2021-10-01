Application Pay My Buddy
Script SQL :

CREATE TABLE user (
                id SMALLINT AUTO_INCREMENT NOT NULL,
                email VARCHAR(65) NOT NULL,
                password VARCHAR(65) NOT NULL,
                firstname VARCHAR(25) NOT NULL,
                lastname VARCHAR(45) NOT NULL,
                wallet DECIMAL(8,2) NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX user_idx
 ON user
 ( email );

CREATE TABLE send (
                id SMALLINT AUTO_INCREMENT NOT NULL,
                id_author SMALLINT NOT NULL,
                id_friend SMALLINT,
                date DATE NOT NULL,
                amount_send DECIMAL(8,2) NOT NULL,
                amount_sampling DECIMAL(8,2) NOT NULL,
                description VARCHAR(100),
                PRIMARY KEY (id, id_author)
);


CREATE TABLE friend_list (
                id_user SMALLINT NOT NULL,
                id_friend SMALLINT NOT NULL,
                PRIMARY KEY (id_user, id_friend)
);


ALTER TABLE friend_list ADD CONSTRAINT user_friend_list_fk
FOREIGN KEY (id_user)
REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE friend_list ADD CONSTRAINT user_friend_list_fk1
FOREIGN KEY (id_friend)
REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE send ADD CONSTRAINT user_send_fk
FOREIGN KEY (id_author)
REFERENCES user (id)
ON DELETE CASCADE
ON UPDATE NO ACTION;

ALTER TABLE send ADD CONSTRAINT user_send_fk1
FOREIGN KEY (id_friend)
REFERENCES user (id)
ON DELETE SET NULL
ON UPDATE NO ACTION;