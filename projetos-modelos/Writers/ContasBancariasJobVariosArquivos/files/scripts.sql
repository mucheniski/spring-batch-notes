-- appcursobatch.cliente definition

CREATE TABLE `cliente` (
  `email` varchar(100) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `idade` varchar(100) DEFAULT NULL,
  `faixa_salarial` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO appcursobatch.cliente (email, nome, idade, faixa_salarial)
VALUES('cliente1@email.com', 'cliente1', '36', '20000');

INSERT INTO appcursobatch.cliente (email, nome, idade, faixa_salarial)
VALUES('cliente2@email.com', 'cliente2', '32', '15000');

INSERT INTO appcursobatch.cliente (email, nome, idade, faixa_salarial)
VALUES('cliente3@email.com', 'cliente3', '1', '10');


-- appcursobatch.conta definition

CREATE TABLE `conta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(100) DEFAULT NULL,
  `limite` varchar(100) DEFAULT NULL,
  `cliente_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `conta_FK` (`cliente_id`),
  CONSTRAINT `conta_FK` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO appcursobatch.conta
(id, tipo, limite, cliente_id)
VALUES(1, 'DIAMANTE', '50000', 'cliente1@email.com');

INSERT INTO appcursobatch.conta
(id, tipo, limite, cliente_id)
VALUES(2, 'PLATINA', '30000', 'cliente2@email.com');

INSERT INTO appcursobatch.conta
(id, tipo, limite, cliente_id)
VALUES(3, 'OURO', '10000', 'cliente3@email.com');