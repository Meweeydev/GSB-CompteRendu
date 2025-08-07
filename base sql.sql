-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Hôte : maxencf1.mysql.db
-- Généré le : jeu. 07 août 2025 à 18:40
-- Version du serveur : 8.0.42-33
-- Version de PHP : 8.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `maxencf1`
--

-- --------------------------------------------------------

--
-- Structure de la table `compterendu`
--

CREATE TABLE `compterendu` (
  `id_cr` int NOT NULL,
  `date_visite` date DEFAULT NULL,
  `commentaire` text,
  `id_utilisateur` int DEFAULT NULL,
  `id_praticien` int DEFAULT NULL,
  `file_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `produits` varchar(11) NOT NULL,
  `id_status` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `compterendu`
--

INSERT INTO `compterendu` (`id_cr`, `date_visite`, `commentaire`, `id_utilisateur`, `id_praticien`, `file_path`, `produits`, `id_status`) VALUES
(114, '2025-06-02', 'Présentation du nouveau produit', 1, 10, NULL, '2,5,6', 1),
(115, '2025-06-02', 'Discussion sur les effets secondaires', 2, 11, 'assets/upload/GSB.pdf', '1,3', 1),
(116, '2025-06-02', 'Bon retour du praticien', 1, 13, 'assets/upload/GSB.pdf', '4,6,8', 1),
(117, '2025-06-03', 'Rendez-vous court mais efficace', 2, 13, NULL, '2,7', 1),
(118, '2025-06-02', 'Le praticien semble intéressé', 1, 14, NULL, '5', 1),
(119, '2025-06-03', 'A approfondir la prochaine fois', 2, 15, NULL, '3,4,7', 1),
(120, '2025-06-03', 'Demande d’échantillons supplémentaires', 1, 16, NULL, '1,6', 1),
(121, '2025-06-03', 'Discussion technique approfondie', 2, 17, NULL, '2,5,9', 1),
(122, '2025-06-03', 'Praticien satisfait', 1, 18, NULL, '3,7,8', 1),
(123, '2025-06-03', 'Accueil très professionnel', 2, 10, NULL, '1,9,10', 1),
(124, '2025-06-04', 'Prévoit de recommander à ses patients', 1, 11, NULL, '2,4,6', 1),
(125, '2025-06-04', 'Rencontre très positive', 2, 13, NULL, '1,3', 1),
(126, '2025-06-04', 'Ouvert à tester davantage', 1, 13, NULL, '7,8', 1),
(127, '2025-06-04', 'A noter des retours positifs', 2, 14, NULL, '3,6', 1),
(128, '2025-06-04', 'Praticien disponible pour future étude', 1, 15, NULL, '2,4,9', 1),
(129, '2025-06-05', 'Rien à signaler de spécial', 2, 16, NULL, '1,5,10', 1),
(130, '2025-06-05', 'A apprécié la documentation', 1, 17, NULL, '2,3,8', 1),
(131, '2025-06-05', 'Suggère d’ajouter un autre principe actif', 2, 18, NULL, '4,7', 1),
(132, '2025-06-05', 'Souhaite organiser un webinaire', 1, 10, NULL, '3,9', 1),
(133, '2025-06-05', 'Retour très constructif', 2, 11, NULL, '5,6', 1),
(134, '2025-06-06', 'Question sur la posologie', 1, 13, NULL, '2,4,10', 1),
(135, '2025-06-06', 'Souhaite une formation plus complète', 2, 13, NULL, '1,8', 1),
(136, '2025-06-06', 'Praticien indécis', 1, 14, NULL, '3,5', 1),
(137, '2025-06-06', 'Rendez-vous rapide', 2, 15, NULL, '7,9', 1),
(138, '2025-06-06', 'En attente des résultats patients', 1, 16, NULL, '6,10', 1),
(139, '2025-06-07', 'Intéressé par les dernières études', 2, 17, NULL, '1,2,3', 1),
(140, '2025-06-07', 'Souhaite plus de statistiques cliniques', 1, 18, NULL, '4,7,8', 1),
(141, '2025-06-07', 'Prévoit une présentation interne', 2, 10, NULL, '3,6,9', 1),
(142, '2025-06-07', 'Retour modéré mais positif', 1, 11, NULL, '5,10', 1),
(143, '2025-06-07', 'Pas encore convaincu', 2, 15, NULL, '1,2', 1),
(144, '2025-06-08', 'Réunion dans un cadre agréable', 1, 13, NULL, '3,5,8', 1),
(145, '2025-06-08', 'Aimerait un test comparatif', 2, 14, NULL, '2,6', 1),
(146, '2025-06-08', 'Avis très favorable', 1, 15, NULL, '7,8,9', 1),
(147, '2025-06-08', 'Souhaite recevoir d’autres données', 2, 16, NULL, '4,10', 1),
(148, '2025-06-08', 'Demande d’assistance technique', 1, 17, NULL, '1,3,6', 1),
(149, '2025-06-09', 'Enthousiasmé par les résultats', 2, 18, NULL, '5,7', 1),
(150, '2025-06-09', 'Prochaine visite déjà planifiée', 1, 10, NULL, '2,3,9', 1),
(151, '2025-06-09', 'Discussion approfondie sur le mode d’action', 2, 11, NULL, '4,6,8', 1),
(152, '2025-06-09', 'Satisfait du suivi effectué', 1, 14, NULL, '1,10', 1),
(153, '2025-06-09', 'Demande accès au rapport complet', 2, 13, NULL, '3,5,7', 1),
(154, '2025-06-10', 'Retour neutre', 1, 14, NULL, '2,6,8', 1),
(155, '2025-06-10', 'Souhaite une démonstration', 2, 15, NULL, '1,4', 1),
(156, '2025-06-10', 'Échange très formateur', 1, 16, NULL, '5,7,10', 1),
(157, '2025-06-10', 'Praticien demande plus de preuves cliniques', 2, 17, NULL, '3,6', 1),
(158, '2025-06-11', 'Rendez-vous repoussé', 1, 18, NULL, '2,8', 1),
(159, '2025-06-11', 'En attente d’évaluation interne', 2, 10, NULL, '4,9', 1),
(160, '2025-06-11', 'Intérêt pour un partenariat', 1, 11, NULL, '1,7,10', 1),
(161, '2025-06-12', 'Retours patients très positifs', 2, 11, NULL, '3,5,6', 1),
(162, '2025-06-12', 'Échange très cordial', 1, 13, NULL, '2,4,8', 1),
(163, '2025-06-13', 'Dernier échange avant validation', 2, 14, NULL, '6,7,9', 1),
(175, '2025-06-08', '1234', 1, 10, NULL, '1,2', 1),
(176, '2025-06-08', 'test', 1, 10, NULL, '1,3', 1);

-- --------------------------------------------------------

--
-- Structure de la table `galerie_groupes`
--

CREATE TABLE `galerie_groupes` (
  `id` int NOT NULL,
  `titre` varchar(100) DEFAULT NULL,
  `description` text,
  `date_ajout` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `galerie_groupes`
--

INSERT INTO `galerie_groupes` (`id`, `titre`, `description`, `date_ajout`) VALUES
(3, 'OPTEX 12M et ip 4MP', 'Installation d\'un détecteur OPTEX 12M et une caméra ip 4MP sur une alarme risco\r\n', '2025-07-15 19:41:53'),
(4, ' optex ', 'Installation d\'un système optex détection extérieure 12m de chaque côté.\r\n', '2025-07-15 19:44:03'),
(5, 'caméra intelligence ', 'Installation d\'une caméra plaque avec intelligence artificielle\r\n', '2025-07-15 19:44:29'),
(6, 'ML PROTECTION', 'ML PROTECTION 14 ANS D\'EXPÉRIENCE 71680 CRÊCHES-SUR-SAÔNE \r\nVOUS PROPOSE UNE ALARME \r\nSANS ABONNEMENT \r\nDEVIS GRATUIT', '2025-07-15 19:49:25');

-- --------------------------------------------------------

--
-- Structure de la table `galerie_images`
--

CREATE TABLE `galerie_images` (
  `id` int NOT NULL,
  `groupe_id` int NOT NULL,
  `image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `galerie_images`
--

INSERT INTO `galerie_images` (`id`, `groupe_id`, `image_url`) VALUES
(5, 3, 'uploads/1752601313_495601975_1277112194418851_6323966445655218888_n.jpg'),
(6, 3, 'uploads/1752601313_496116138_1277111981085539_6411645882377387522_n.jpg'),
(7, 4, 'uploads/1752601443_image_2025-07-15_194354165.png'),
(8, 5, 'uploads/1752601469_image_2025-07-15_194421633.png'),
(9, 6, 'uploads/1752601765_494210931_1266427472153990_6336127820161806332_n.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `note_produit`
--

CREATE TABLE `note_produit` (
  `id_note` int NOT NULL,
  `id_produit` int NOT NULL,
  `id_praticien` int NOT NULL,
  `note` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `note_produit`
--

INSERT INTO `note_produit` (`id_note`, `id_produit`, `id_praticien`, `note`) VALUES
(1, 1, 10, 5),
(2, 1, 10, 5),
(3, 2, 10, 3);

-- --------------------------------------------------------

--
-- Structure de la table `praticien`
--

CREATE TABLE `praticien` (
  `id_praticien` int NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `adresse` text,
  `telephone` varchar(20) DEFAULT NULL,
  `Status` int NOT NULL DEFAULT '5'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `praticien`
--

INSERT INTO `praticien` (`id_praticien`, `nom`, `specialite`, `adresse`, `telephone`, `Status`) VALUES
(10, 'M.PHILIPPON', 'Ophtalmologue', '55 Rte du Vieux Moulin, 71570 La Chapelle-de-Guinchay, France', '06.58.47.61.84', 5),
(11, 'K.ECHALLIER', 'Orthodentiste', '14 Rte des Burriers, 71570 La Chapelle-de-Guinchay, France', '06.58.47.61.84', 5),
(13, 'Dr. Jean Dupont', 'Médecin généraliste', '33 Rte des Daroux, 71570 La Chapelle-de-Guinchay, France', '01 23 45 67 89', 5),
(14, 'Dr. Marie Curie', 'Oncologue', '456 Avenue des Champs-Élysées, Paris, 75008', '01 98 76 54 32', 5),
(15, 'Dr. Pierre Martin', 'Cardiologue', '789 Boulevard Saint-Germain, Paris, 75006', '01 11 22 33 44', 5),
(16, 'Dr. Lucas Bernard', 'Dermatologue', '654 Rue de la Paix, Marseille, 13001', '04 56 78 90 12', 5),
(17, 'Dr. Julien Fournier', 'Chirurgien orthopédique', '852 Route de la Mer, Nice, 06000', '04 93 12 34 56', 5),
(18, 'Dr Vielmont Jean-Luc', 'Médecin généraliste', '164 Chem. de Balme, 71850 Charnay-lès-Mâcon, France', '01 23 45 67 89', 5),
(20, 'Dr. PHILIPPON Frédéric ', 'Cancérologue', '53 Cr Albert Thomas, 69003 Lyon, France', '06.72.13.31.25', 5);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `description` text,
  `categorie` varchar(50) DEFAULT NULL,
  `date_ajout` date NOT NULL,
  `Status` int NOT NULL DEFAULT '5'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `nom`, `description`, `categorie`, `date_ajout`, `Status`) VALUES
(1, 'Paracétamol', 'Médicament anti-douleur et antipyrétique couramment utilisé.', 'Analgésiques', '2024-10-07', 5),
(2, 'Ibuprofène', 'Anti-inflammatoire non stéroïdien utilisé pour réduire la douleur, l’inflammation et la fièvre.', 'Anti-inflammatoires', '2024-10-07', 5),
(3, 'Amoxicilline', 'Antibiotique utilisé pour traiter un large éventail d’infections bactériennes.', 'Antibiotiques', '2024-10-07', 5),
(4, 'Aspirine', 'Médicament utilisé pour soulager la douleur, réduire la fièvre et prévenir les caillots sanguins.', 'Analgésiques', '2024-10-07', 5),
(5, 'Lisinopril', 'Médicament utilisé pour traiter l’hypertension et l’insuffisance cardiaque.', 'Antihypertenseurs', '2024-10-07', 5),
(6, 'Atorvastatine', 'Médicament utilisé pour réduire le cholestérol et prévenir les maladies cardiaques.', 'Anticholestérolémiants', '2024-10-25', 5),
(7, 'Simvastatine', 'Médicament utilisé pour réduire le taux de cholestérol dans le sang.', 'Anticholestérolémiants', '2024-10-07', 5),
(8, 'Oméprazole', 'Inhibiteur de la pompe à protons utilisé pour traiter les ulcères gastriques et le reflux gastro-oesophagien.', 'Antiacides', '2024-10-07', 5),
(9, 'Metformine', 'Médicament utilisé pour traiter le diabète de type 2.', 'Antidiabétiques', '2024-10-07', 5),
(10, 'Sertraline', 'Antidépresseur utilisé pour traiter la dépression, les troubles d’anxiété et d’autres troubles mentaux.', 'Antidépresseurs', '2024-10-07', 5);

-- --------------------------------------------------------

--
-- Structure de la table `region`
--

CREATE TABLE `region` (
  `id_region` int NOT NULL,
  `region_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `region`
--

INSERT INTO `region` (`id_region`, `region_name`) VALUES
(1, 'Auvergne-Rhône-Alpes'),
(2, 'Bourgogne-Franche-Comté'),
(3, 'Bretagne'),
(4, 'Centre-Val de Loire'),
(5, 'Corse'),
(6, 'Grand Est'),
(7, 'Hauts-de-France'),
(8, 'Île-de-France'),
(9, 'Normandie'),
(10, 'Nouvelle-Aquitaine'),
(11, 'Occitanie'),
(12, 'Pays de la Loire'),
(13, 'Provence-Alpes-Côte d\'Azur'),
(14, 'Guadeloupe'),
(15, 'Martinique'),
(16, 'Guyane'),
(17, 'La Réunion'),
(18, 'Mayotte');

-- --------------------------------------------------------

--
-- Structure de la table `status`
--

CREATE TABLE `status` (
  `id` int NOT NULL,
  `status` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `status`
--

INSERT INTO `status` (`id`, `status`) VALUES
(1, 'En cours de traitement'),
(2, 'Modification en attente'),
(3, 'Terminer'),
(4, 'Refuser'),
(5, 'Activer'),
(6, 'Désactiver');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mdp` text NOT NULL,
  `role` enum('Visiteur','Delegue','Responsable') NOT NULL DEFAULT 'Visiteur',
  `region` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `nom`, `prenom`, `username`, `email`, `mdp`, `role`, `region`) VALUES
(1, 'DUPONT', 'Jean', 'DJ', 'jean.dupont@gsb.fr', '$2y$10$vFUlXaCd9L1heEBYFmq5GeSDO0BarrvKNKmX/orWPyXhOJ2g0JX9y', 'Visiteur', 2),
(2, 'LUC', 'Martin', 'LM', 'martin.luc@gsb.fr', '$2y$10$vFUlXaCd9L1heEBYFmq5GeSDO0BarrvKNKmX/orWPyXhOJ2g0JX9y', 'Delegue', 2),
(3, 'DURAND', 'Marie', 'DM', 'marie.durand@gsb.fr', '$2y$10$vFUlXaCd9L1heEBYFmq5GeSDO0BarrvKNKmX/orWPyXhOJ2g0JX9y', 'Responsable', 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `compterendu`
--
ALTER TABLE `compterendu`
  ADD PRIMARY KEY (`id_cr`),
  ADD KEY `compterendu_ibfk_1` (`id_utilisateur`),
  ADD KEY `compterendu_ibfk_2` (`id_praticien`),
  ADD KEY `fk_CR_status` (`id_status`);

--
-- Index pour la table `galerie_groupes`
--
ALTER TABLE `galerie_groupes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `galerie_images`
--
ALTER TABLE `galerie_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `groupe_id` (`groupe_id`);

--
-- Index pour la table `note_produit`
--
ALTER TABLE `note_produit`
  ADD PRIMARY KEY (`id_note`),
  ADD KEY `id_praticien` (`id_praticien`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `praticien`
--
ALTER TABLE `praticien`
  ADD PRIMARY KEY (`id_praticien`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`),
  ADD KEY `fk_produit_status` (`Status`);

--
-- Index pour la table `region`
--
ALTER TABLE `region`
  ADD PRIMARY KEY (`id_region`);

--
-- Index pour la table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `fk_user_region` (`region`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `compterendu`
--
ALTER TABLE `compterendu`
  MODIFY `id_cr` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=177;

--
-- AUTO_INCREMENT pour la table `galerie_groupes`
--
ALTER TABLE `galerie_groupes`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `galerie_images`
--
ALTER TABLE `galerie_images`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `note_produit`
--
ALTER TABLE `note_produit`
  MODIFY `id_note` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `praticien`
--
ALTER TABLE `praticien`
  MODIFY `id_praticien` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `status`
--
ALTER TABLE `status`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `compterendu`
--
ALTER TABLE `compterendu`
  ADD CONSTRAINT `compterendu_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE CASCADE,
  ADD CONSTRAINT `compterendu_ibfk_2` FOREIGN KEY (`id_praticien`) REFERENCES `praticien` (`id_praticien`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_CR_status` FOREIGN KEY (`id_status`) REFERENCES `status` (`id`);

--
-- Contraintes pour la table `galerie_images`
--
ALTER TABLE `galerie_images`
  ADD CONSTRAINT `galerie_images_ibfk_1` FOREIGN KEY (`groupe_id`) REFERENCES `galerie_groupes` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `note_produit`
--
ALTER TABLE `note_produit`
  ADD CONSTRAINT `note_produit_ibfk_1` FOREIGN KEY (`id_praticien`) REFERENCES `praticien` (`id_praticien`),
  ADD CONSTRAINT `note_produit_ibfk_2` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `fk_produit_status` FOREIGN KEY (`Status`) REFERENCES `status` (`id`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `fk_user_region` FOREIGN KEY (`region`) REFERENCES `region` (`id_region`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
