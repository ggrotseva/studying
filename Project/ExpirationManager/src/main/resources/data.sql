insert into expiration_date_manager.users (`id`, `email`, `first_name`, `is_deleted`, `last_name`, `password`, `username`) values
('g.grotseva@gmail.com', 'Gabriela', 0, 'Grotseva', '$2a$10$kP/4PFikrONdWwy6r3QSe..KtPUhP4wyx2a2h0561m.jLRDVvsqlq', 'starshine'),
('pesho@email.email', 'Pesho', 0, 'Peshov', '$2a$10$D7VhedxydbMWPVDMRqGGI.e46jQOQHXpSCfqxgNimHCihA6fD1IiK', 'peshkata')

insert into expiration_date_manager.users_user_roles (`user_id`, `user_role_id`) values
(1, 1),
(1, 2),
(2, 1);

insert into expiration_date_manager.categories (`description`, `name`, `user_id`) values
('biscuits, pralines, chocolates, etc.', 'sweets', '1'),
('chickpeas, beans, coconut milk, etc.', 'cans', '1'),
('jams, tahini, honey, pickles, etc.', 'jars', '1'),
('pasta, flours, oats, rice, etc.', 'grains', '1'),
('biscuits, pralines, chocolates, etc.', 'sweets', '2'),
('chickpeas, beans, coconut milk, etc.', 'cans', '2'),
('jams, tahini, honey, pickles, etc.', 'jars', '2'),
('pasta, flours, oats, rice, etc.', 'grains', '2'),
('макарони', 'паста', '2');

insert into expiration_date_manager.recipes (`created`, `image_url`, `ingredients_description`, `modified`, `name`, `preparation`, `type`, `author_id`) values
('2023-03-25 12:33:48.790383', '/images/egg-toast.jpg',
'• 400 гр брашно\r\n• 1/2 с.л. сол\r\n• 1 ч.л. захар\r\n• 6 с.л. олио\r\n• 250 мл вода\r\n• 1 бакпулвер',
'2023-03-25 12:33:48.791370', 'содени питки на тиган',
'Брашното се пресява, прави се кладенче и се излива водата, в която са разтворени солта, захарта и бакпулвера.\r\nПрибавя се и олиото и се замесва.\r\nТестото се оставя да си почине около 20 мин.\r\nРазделя се на около 12 части, всяка от които се разточва на тънко и се пече на сух тиган. Когато се махне от тигана, се маже с масло. \r\nМоже също да се поръсят с шарена сол или да се натъркат с чесън.',
'SAVORY', 2),

('2023-03-20 17:15:48.790383', '/images/pancakes.jpg',
'• 2 яйца\r\n• 1/2 с.л. кокосово масло - разтопено\r\n• 4-5 ч.л. кокосова захар\r\n• 2 ч.л. бакпулвер\r\n• 9 с.л. брашно\r\n• есенция ванилия/портокал\r\n• ядки/шоколад по желание',
'2023-03-20 17:15:48.791370', 'лесни курабийки',
'Яйцата се разбиват със захарта и кокосовото масло. Слагат се ароматите. Прибавят се брашното и бакпулвера. Накрая се добавят и ядките/шоколада.\r\nОт полученото тесто се оформят около 7-8 бисквитки. Пекат се на загрята фурна на 200 градуса за около 15 мин.',
'SWEET', 1),

('2023-03-25 13:42:00.870999', '/images/pancakes.jpg',
'• 200 гр. черен шоколад\r\n• 110 гр. масло\r\n• 1/2 ч.ч. захар\r\n• 3 яйца\r\n• щипка сол\r\n• 1 ч.л. ванилова есенция\r\n• 1/2 ч.ч. брашно\r\n• 1/4 ч.ч. какао\r\n• ядки/шоколад по желание',
'2023-03-25 13:42:00.870999', 'брауни',
'Маслото и шоколада се разтопяват на водна баня. Оставя се настрана да се охлади.\r\nПрез това се рзбиват яйцата и захарта на пяна. Добавят се ванилията и солта. Накрая и изстиналата шоколадова смес. \r\nКъм това се прибавят и пресятите брашно и какао.\r\nДобавят се и шоколада/ядките по желание.\r\nИзсипва се във формата, която е намазана с масло и поръсена с какао.\r\nПече се на 180 градуса за около 25-30 мин или докато при проба с клечка, тя излиза не напълно чиста. Ако е напълно чиста, значи е печен прекалено много.\r\nКогато се извади може да се поръси с едра сол.',
'SWEET', 1),

('2023-03-25 13:50:33.961888', '/images/pancakes.jpg',
'• 30-40 гр. лешници\r\n• 6 фурми\r\n• 1 ч.л. лешников тахан\r\n• 2-3 капки ром/есенция ром\r\n• 1-2 ч.л. какао\r\n• щипка сол',
'2023-03-25 13:50:33.961888', 'сурови какаови бонбони',
'Смиламе лешниците на ситно.\r\nОтделно смиламе фурмите заедно с тахана, какаото, ромът, солта.\r\nДобавяме към лешниците и омесваме лепкава смес, от която оформяме топчета и овалваме в какао. \r\nСъхраняваме в хладилник',
'SWEET', 2),

('2023-03-30 20:01:32.836503', 'https://res.cloudinary.com/dh0d7odiu/image/upload/w_500,h_500,c_fill/v1679387387/d22c0594-eb8f-4c3f-90ef-52bd1a728a1a.jpg',
'• 500 гр (1 пакет) паста\r\n• 4-5 чушки\r\n• 10-15 маслини\r\n• 1 червен лук\r\n• 1 консерва риба тон\r\n• зехтин\r\n• сол\r\n• черен пипер\r\n• оцет/лимонов сок',
'2023-03-30 20:32:15.253347', 'салата с паста',
'Пастата се сварява, към нея се добавят нарязани чушките, маслините и лука, рибата тон и се овкусява с подправките.',
'SAVORY', 2),

('2023-03-14 12:40:06.782326', '/images/egg-toast.jpg',
'• 2 slices of bread\r\n• 2 eggs\r\n• 1 avocado\r\n• chili flakes\r\n• salt\r\n• pepper\r\n• lemon juice',
'2023-03-16 12:38:25.161268', 'egg toast with chili flakes',
'Toast the bread.\r\nPrepare the eggs on a pan with a little oil.\r\nMash the avocado with some salt, pepper and lemon juice.\r\nPut the avocado on both slices of bread, then put an egg on each. Sprinkle with chili flakes and enjoy!',
'SAVORY', 1),

('2023-03-14 13:33:48.035060', '/images/pancakes.jpg',
'• 200g flour\r\n• 300ml milk\r\n• 2 eggs\r\n• pinch sugar\r\n• pinch salt\r\n• honey/jam/peanut butter',
'2023-03-14 13:33:48.035060', 'pancakes',
'Sift flour, baking powder, sugar, and salt together in a large bowl. Make a well in the center and add milk, melted butter, and egg; mix until smooth.\r\nHeat a lightly oiled griddle or pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake; cook until bubbles form and the edges are dry, about 2 to 3 minutes. Flip and cook until browned on the other side. Repeat with remaining batter.',
'SWEET', 1),

('2023-03-15 10:15:17.642701', '/images/pancakes.jpg',
'• бишкоти\r\n• ~ 200мл кафе\r\n• 300г копринено тофу\r\n• твърдата част на 1 кен кокосова сметана\r\n• 3 с.л. кафява/кокосова захар\r\n• 2-3 с.л. кокосови стърготини (опционално)\r\n• ванилия\r\n• какао за поръсване',
'2023-03-15 10:15:17.642701', 'Протеиново тирамису без млечни',
'Тофуто, захарта, кокосовата сметана, стърготини и ванилията се смилат в блендер. \r\nБишкотите се нареждат на дъното на съд, поливат се с кафе и след това се покриват с получения крем. Това се повтаря докато крема и бишкотите свършат. Накрая се поръсва с какао и се прибира в хладилника да стегне. \r\nСъхранява се в хладилник за около 3-4 дни.',
'SWEET', 1),

('2023-03-16 10:44:54.824587', 'https://res.cloudinary.com/dh0d7odiu/image/upload/w_500,h_500,c_fill/v1679387387/a1b0a411-802e-4dbc-ae5a-2f3130f05fc6.jpg',
'• 1 пакет спагети\r\n• 300гр. кайма\r\n• 1-2 глави лук\r\n• 1-2 ск. чесън\r\n• 1 морков\r\n• 1 чушка\r\n• 1 ч.л. кимион\r\n• 1 ч.л. сол\r\n• 1 ч.л. риган\r\n• 1 ч.л. червен пипер\r\n• 1/2 ч.л. черен пипер\r\n• 1/2 ч.л. розмарин\r\n• 400г домати от консерва\r\n• 1 с.л. доматено пюре (незадължително)\r\n• 1 чаша бульон',
'2023-03-21 10:59:45.195643', 'спагети болонезе',
'Спагетите се сваряват в добре подсолена вода и се отцеждат.\r\nВ горещ тиган и малко мазнина, се запържват лука и чесъна за 3-4 мин. След това се добавят и зеленчуците за 3-4 мин. \r\nВсичко се изважда от тигана и в същия тиган се запържва каймата докато стане на трохи и е напълно сготвена. Зеленчуците се връщат в тигана и всичко се поръсва с кимиона, солта, пипера, ригана и розмарина. Задушават се още малко и се изсипват бутльона и доматите.\r\nОставя се да ври поне 10 мин или докато соса се сгъсти.\r\nПастата се смесва със соса и е готово за ядене.',
'SAVORY', 1),

('2023-03-27 10:47:24.736917', '/images/pancakes.jpg',
'• 4-5 ябълки\r\n• 2-3 яйца\r\n• 2/3 ч.ч. захар\r\n• 1 ч.ч. олио\r\n• 1 пак. канела\r\n• 2 ч.ч. брашно\r\n• 1 бакпулвер\r\n• орехи по желание',
'2023-03-30 15:50:53.771715', 'кекс с ябълки',
'Ябълките се нарязват на кубчета.\r\nРазбиват се яйцата и захарта. Прибавя се олиото и се разбърква. Добавят се брашното с канелата и бакпулвера и се разбърква. Става гъсто тесто. Накрая се прибавят ябълките и орехите по желание.\r\nИзсипва се в намазнен и набрашнен съд. Може да се сложат орехи и отгоре. Пече се на 200 градуса докато се надуе и после на 180 градуса до готовност.',
'SWEET', 2);