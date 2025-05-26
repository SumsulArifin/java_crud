
-- Create database (run this in terminal, not inside the SQL file)
-- CREATE DATABASE conservation_db;

-- Connect to the database
-- \c conservation_db

-- TABLE CREATION
-- Problem 0: Create rangers table
CREATE TABLE rangers (
    ranger_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region VARCHAR(100) NOT NULL
);

-- Problem 0: Create species table
CREATE TABLE species (
    species_id SERIAL PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(150),
    discovery_date DATE,
    conservation_status VARCHAR(50)
);

-- Problem 0: Create sightings table
CREATE TABLE sightings (
    sighting_id SERIAL PRIMARY KEY,
    ranger_id INT REFERENCES rangers(ranger_id),
    species_id INT REFERENCES species(species_id),
    location VARCHAR(100),
    sighting_time TIMESTAMP,
    notes TEXT
);

-- DATA INSERTION
-- Problem 0: Insert sample rangers
INSERT INTO rangers (name, region) VALUES
('Alice Green', 'Northern Hills'),
('Bob White', 'River Delta'),
('Carol King', 'Mountain Range');

-- Problem 0: Insert sample species
INSERT INTO species (common_name, scientific_name, discovery_date, conservation_status) VALUES
('Snow Leopard', 'Panthera uncia', '1775-01-01', 'Endangered'),
('Bengal Tiger', 'Panthera tigris tigris', '1758-01-01', 'Endangered'),
('Red Panda', 'Ailurus fulgens', '1825-01-01', 'Vulnerable'),
('Asiatic Elephant', 'Elephas maximus indicus', '1758-01-01', 'Endangered');

-- Problem 0: Insert sample sightings
INSERT INTO sightings (species_id, ranger_id, location, sighting_time, notes) VALUES
(1, 1, 'Peak Ridge', '2024-05-10 07:45:00', 'Camera trap image captured'),
(2, 2, 'Bankwood Area', '2024-05-12 16:20:00', 'Juvenile seen'),
(3, 3, 'Bamboo Grove East', '2024-05-15 09:10:00', 'Feeding observed'),
(1, 2, 'Snowfall Pass', '2024-05-18 18:30:00', NULL);

-- Problem 1: Register a new ranger
INSERT INTO rangers (name, region)
VALUES ('Derek Fox', 'Coastal Plains');

-- Problem 2: Count unique species ever sighted
SELECT COUNT(DISTINCT species_id) AS unique_species_count
FROM sightings;

-- Problem 3: Find all sightings where location includes "Pass"
SELECT * FROM sightings
WHERE location ILIKE '%Pass%';

-- Problem 4: List each ranger’s name and their total number of sightings
SELECT r.name, COUNT(s.sighting_id) AS total_sightings
FROM rangers r
LEFT JOIN sightings s ON r.ranger_id = s.ranger_id
GROUP BY r.name;

-- Problem 5: List species that have never been sighted
SELECT s.common_name
FROM species s
LEFT JOIN sightings si ON s.species_id = si.species_id
WHERE si.sighting_id IS NULL;

-- Problem 6: Show the most recent 2 sightings
SELECT sp.common_name, si.sighting_time, r.name
FROM sightings si
JOIN species sp ON si.species_id = sp.species_id
JOIN rangers r ON si.ranger_id = r.ranger_id
ORDER BY si.sighting_time DESC
LIMIT 2;

-- Problem 7: Update species discovered before 1800 to have status 'Historic'
UPDATE species
SET conservation_status = 'Historic'
WHERE discovery_date < '1800-01-01';

-- Problem 8: Label each sighting's time of day
SELECT sighting_id,
    CASE
        WHEN EXTRACT(HOUR FROM sighting_time) < 12 THEN 'Morning'
        WHEN EXTRACT(HOUR FROM sighting_time) BETWEEN 12 AND 17 THEN 'Afternoon'
        ELSE 'Evening'
    END AS time_of_day
FROM sightings;

-- Problem 9: Delete rangers who have never sighted any species
DELETE FROM rangers
WHERE ranger_id NOT IN (SELECT DISTINCT ranger_id FROM sightings);
