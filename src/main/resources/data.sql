INSERT INTO locations (id, name, coordinates, radius, image, opening_hours, type)
VALUES
('cbbc7d9d-50d2-4cd6-a35c-36afa087539c','Central Park', ST_SetSRID(ST_MakePoint(2, 2), 0), 2, 'centralpark.jpg', '06:00-22:00', 'PARK'),
('db4f2d89-0dd1-46b9-b508-859f2eff4f35', 'City Museum', ST_SetSRID(ST_MakePoint(30, 40), 0), 10, 'museum.jpg', '09:00-17:00', 'MUSEUM'),
('c7a9f17f-3e02-4feb-95b2-75de47e32446', 'Italian Restaurant', ST_SetSRID(ST_MakePoint(1, 1), 0), 1, 'restaurant.jpg', '11:00-23:00', 'RESTAURANT');

-- Restaurants
INSERT INTO locations (id, name, coordinates, radius, image, opening_hours, type)
VALUES
('073598ac-9d8c-4d5f-8179-0a4944d8d85c','Italian Bistro', ST_SetSRID(ST_MakePoint(2, 2), 0), 2, 'italian_bistro.jpg', '11:00-23:00', 'RESTAURANT'),
('f79b5d51-79be-469a-9208-d4a9bc68516c', 'Sushi Palace', ST_SetSRID(ST_MakePoint(5, 5), 0), 1, 'sushi_palace.jpg', '12:00-22:00', 'RESTAURANT'),
('593e9bed-67de-4e99-a446-7299f2704a1f', 'Burger Town', ST_SetSRID(ST_MakePoint(2, 3), 0), 5, 'burger_town.jpg', '10:00-21:00', 'RESTAURANT'),
('1e2a949a-9afe-424c-a7b2-1b069b44d2f2','Taco Fiesta', ST_SetSRID(ST_MakePoint(50, 25), 0), 5, 'taco_fiesta.jpg', '11:00-00:00', 'RESTAURANT'),
('aac8fb4a-67ee-4751-ac29-3f8f37e1d4a6', 'Steak House', ST_SetSRID(ST_MakePoint(60, 45), 0), 10, 'steak_house.jpg', '17:00-23:00', 'RESTAURANT'),
('7dc106e9-e5f2-4593-8123-506ec8799870', 'Vegan Delight', ST_SetSRID(ST_MakePoint(20, 50), 0), 15, 'vegan_delight.jpg', '09:00-21:00', 'RESTAURANT'),
('2ae46383-f373-4dac-a5ac-51cd5868970e', 'Pizza Corner', ST_SetSRID(ST_MakePoint(40, 35), 0), 20, 'pizza_corner.jpg', '10:00-23:00', 'RESTAURANT'),
('76844a26-91b2-43ba-88c3-2545eb207820', 'Seafood Bay', ST_SetSRID(ST_MakePoint(55, 15), 0), 40, 'seafood_bay.jpg', '12:00-22:00', 'RESTAURANT'),
('d3022dfe-0ba7-4ba4-9401-406027aeded8', 'BBQ Grill', ST_SetSRID(ST_MakePoint(70, 30), 0), 4, 'bbq_grill.jpg', '12:00-23:00', 'RESTAURANT');
