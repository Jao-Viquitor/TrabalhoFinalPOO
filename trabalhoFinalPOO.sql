-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Tempo de geração: 29-Abr-2021 às 16:06
-- Versão do servidor: 10.3.27-MariaDB-0+deb10u1
-- versão do PHP: 7.3.27-1~deb10u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `trabalhoFinalPOO`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `camarote_pista`
--

CREATE TABLE `camarote_pista` (
  `rg` varchar(250) NOT NULL,
  `credito` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `camarote_pista`
--

INSERT INTO `camarote_pista` (`rg`, `credito`) VALUES
('1234567890', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `rg` varchar(250) NOT NULL,
  `nome` varchar(250) NOT NULL,
  `tipo_entrada` varchar(250) NOT NULL,
  `valor_entrada` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`rg`, `nome`, `tipo_entrada`, `valor_entrada`) VALUES
('1234567890', 'Igor', 'CAMAROTE', 20),
('1234567891', 'vip', 'VIP', 20);

-- --------------------------------------------------------

--
-- Estrutura da tabela `consumo`
--

CREATE TABLE `consumo` (
  `id` int(11) NOT NULL,
  `cliente_rg` varchar(250) NOT NULL,
  `produto_id` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `consumo`
--

INSERT INTO `consumo` (`id`, `cliente_rg`, `produto_id`, `quantidade`) VALUES
(1, '1234567890', 2, 8),
(3, '1234567891', 2, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

CREATE TABLE `produto` (
  `id` int(11) NOT NULL,
  `titulo` varchar(250) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `valor_custo` float NOT NULL,
  `valor_venda` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`id`, `titulo`, `quantidade`, `valor_custo`, `valor_venda`) VALUES
(2, 'coca 250ml', 0, 2.5, 3.5);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `camarote_pista`
--
ALTER TABLE `camarote_pista`
  ADD PRIMARY KEY (`rg`);

--
-- Índices para tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`rg`);

--
-- Índices para tabela `consumo`
--
ALTER TABLE `consumo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `consumo_ibfk_1` (`cliente_rg`),
  ADD KEY `consumo_ibfk_2` (`produto_id`);

--
-- Índices para tabela `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `consumo`
--
ALTER TABLE `consumo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `produto`
--
ALTER TABLE `produto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `camarote_pista`
--
ALTER TABLE `camarote_pista`
  ADD CONSTRAINT `camarote_pista_ibfk_1` FOREIGN KEY (`rg`) REFERENCES `cliente` (`rg`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `consumo`
--
ALTER TABLE `consumo`
  ADD CONSTRAINT `consumo_ibfk_1` FOREIGN KEY (`cliente_rg`) REFERENCES `cliente` (`rg`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `consumo_ibfk_2` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
