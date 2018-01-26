-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Pát 19. led 2018, 17:50
-- Verze serveru: 10.1.28-MariaDB
-- Verze PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `kava`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `hodnoceni`
--

CREATE TABLE `hodnoceni` (
  `id` int(11) NOT NULL,
  `hodnoceni` int(11) NOT NULL,
  `komentar` varchar(500) CHARACTER SET utf16 COLLATE utf16_czech_ci NOT NULL,
  `datum` date NOT NULL,
  `id_kavarny` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Vypisuji data pro tabulku `hodnoceni`
--

INSERT INTO `hodnoceni` (`id`, `hodnoceni`, `komentar`, `datum`, `id_kavarny`) VALUES
(1, 5, 'Trololololololo', '2018-01-19', 1),
(2, 3, 'Nevim, nic moc', '2018-01-19', 1),
(3, 4, 'Ale tak jde to.', '2018-01-19', 1),
(4, 5, 'Dobrá kavárna!', '2018-01-19', 1);

-- --------------------------------------------------------

--
-- Struktura tabulky `kavarny`
--

CREATE TABLE `kavarny` (
  `id` int(11) NOT NULL,
  `nazev` varchar(45) NOT NULL,
  `adresa` varchar(250) NOT NULL,
  `popis` varchar(500) NOT NULL,
  `kavaNabidka` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Vypisuji data pro tabulku `kavarny`
--

INSERT INTO `kavarny` (`id`, `nazev`, `adresa`, `popis`, `kavaNabidka`) VALUES
(1, 'Kavárna u tří koček', 'Ječná 22 Praha 2', 'Kavárna plná koček a kafe', 'Pouze kapučíno'),
(2, 'nevimNevimX', 'nevim', 'nevimx', 'nevimxx');

-- --------------------------------------------------------

--
-- Struktura tabulky `uzivatele`
--

CREATE TABLE `uzivatele` (
  `id` int(11) NOT NULL,
  `jmeno` varchar(45) NOT NULL,
  `heslo` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `jeSpravce` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Vypisuji data pro tabulku `uzivatele`
--

INSERT INTO `uzivatele` (`id`, `jmeno`, `heslo`, `email`, `jeSpravce`) VALUES
(1, 'x', 'x', 'x', b'0'),
(2, 'Ahoj', 'Ahoj@seznam.cz', 'Ahoj', b'0'),
(3, 'y', 'y', 'y', b'1');

-- --------------------------------------------------------

--
-- Struktura tabulky `vzkazy`
--

CREATE TABLE `vzkazy` (
  `id` int(11) NOT NULL,
  `obsah` varchar(500) NOT NULL,
  `iduzivatel` int(11) NOT NULL,
  `idkavarna` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Klíče pro exportované tabulky
--

--
-- Klíče pro tabulku `hodnoceni`
--
ALTER TABLE `hodnoceni`
  ADD PRIMARY KEY (`id`);

--
-- Klíče pro tabulku `kavarny`
--
ALTER TABLE `kavarny`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `nazev_UNIQUE` (`nazev`);

--
-- Klíče pro tabulku `uzivatele`
--
ALTER TABLE `uzivatele`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `jmeno_UNIQUE` (`jmeno`);

--
-- Klíče pro tabulku `vzkazy`
--
ALTER TABLE `vzkazy`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `idkavarna_idx` (`idkavarna`),
  ADD KEY `iduzivatel_idx` (`iduzivatel`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `hodnoceni`
--
ALTER TABLE `hodnoceni`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pro tabulku `kavarny`
--
ALTER TABLE `kavarny`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pro tabulku `uzivatele`
--
ALTER TABLE `uzivatele`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pro tabulku `vzkazy`
--
ALTER TABLE `vzkazy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `vzkazy`
--
ALTER TABLE `vzkazy`
  ADD CONSTRAINT `idkavarna` FOREIGN KEY (`idkavarna`) REFERENCES `kavarny` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `iduzivatel` FOREIGN KEY (`iduzivatel`) REFERENCES `uzivatele` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
