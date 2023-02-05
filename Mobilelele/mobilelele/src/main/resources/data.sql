--insert into users (username, created, first_name, last_name, image_url, is_active, password)
--values ('starshine', NOW(), 'Gabs', 'Gabriela', null, 1, 'topsecret');

insert into brands (name, created)
values ('Toyota', '19690501'),
('Lexus', '19920302'),
('Volvo', '19530808'),
('Mazda', '20121018');

insert into models (category, name, start_year, created, brand_id, image_url)
values ('CAR', 'LS500h (XF50)', '20180101', '20180101', 2, 'https://car2sale.ru/uploads/Pictures/Lexus/LS350/Exterior/Supreme_Grey/198d3e15-fb63-46a0-bbaa-10a260a29de2.jpg'),
('CAR', 'Land Cruiser J200', '20080101', '20210910', 1, 'https://media.ed.edmunds-media.com/toyota/land-cruiser/2017/oem/2017_toyota_land-cruiser_4dr-suv_base_fq_oem_1_1280x855.jpg'),
('CAR', 'S90', '20180101', '20180101', 3, 'https://hips.hearstapps.com/hmg-prod/images/2023-volvo-s90-103-1668439862.jpg'),
('CAR', '6 (GJ)', '20120101', '20180101', 4, 'https://storage.carspending.com/cache/vehicles/9217420e-9cca-4179-801d-13b5bb3fb8e9/5ceaa5244c876.jpg');