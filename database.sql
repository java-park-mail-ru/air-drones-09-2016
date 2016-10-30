CREATE SCHEMA airdrone

create TABLE airdrone.User(
              idUser SERIAL NOT NULL,
              email VARCHAR(100) PRIMARY KEY NOT NULL,
              password VARCHAR(100) NOT NULL,
              username VARCHAR(100),
              rating INT DEFAULT 0
            );