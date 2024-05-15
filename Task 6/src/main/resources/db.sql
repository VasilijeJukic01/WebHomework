CREATE TABLE `Users` (
    `id` integer AUTO_INCREMENT PRIMARY KEY,
    `username` varchar(255),
    `password` varchar(255)
);

CREATE TABLE `Posts` (
    `id` integer AUTO_INCREMENT PRIMARY KEY,
    `author` varchar(255),
    `title` varchar(255),
    `body` varchar(255),
    `post_date` date
);

CREATE TABLE `Comments` (
    `id` integer AUTO_INCREMENT PRIMARY KEY,
    `author` varchar(255),
    `content` varchar(255),
    `post_id` integer
);

ALTER TABLE `Comments` ADD FOREIGN KEY (`post_id`) REFERENCES `Posts` (`id`);
