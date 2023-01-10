use bookshop;

select b.title, a.first_name, a.last_name from books b
join authors a on a.id = b.author_id
where a.first_name = 'George' and a.last_name = 'Powell'
order by b.release_date desc, b.title;

SELECT SUM(b.copies) AS copies_sum FROM books b 
RIGHT JOIN authors a ON b.author_id = a.id
WHERE a.first_name = 'Randy' AND a.last_name = 'Graham';

SELECT COUNT(b.id) FROM books b
JOIN authors a ON a.id = b.author_id
WHERE a.first_name = 'Randy' AND a.last_name = 'Graham';

DELIMITER $$
CREATE PROCEDURE usp_authors_books_count(first VARCHAR(255), last VARCHAR(255), OUT book_count INT)
BEGIN
	SELECT COUNT(b.id) into book_count FROM books b
    JOIN authors a ON a.id = b.author_id
    WHERE a.first_name = first AND a.last_name = last;
END$$

DELIMITER ;

use product_shop;

SELECT * FROM product_shop.categories ORDER BY RAND() LIMIT 1;

SELECT u.first_name, u.last_name, ps.name, ps.buyer_id FROM users u 
JOIN products ps ON ps.seller_id = u.id
WHERE ps.buyer_id IS NOT NULL AND (
	SELECT COUNT(*) FROM products p 
    WHERE p.seller_id = u.id AND p.buyer_id IS NOT NULL) > 0
ORDER BY u.last_name, u.first_name;


SELECT u.first_name, u.last_name, COUNT(ps.id) AS count_of_all_selling_items FROM users u 
JOIN products ps ON ps.seller_id = u.id
WHERE (
	SELECT COUNT(*) FROM products p 
    WHERE p.seller_id = u.id AND p.buyer_id IS NOT NULL) > 0
GROUP BY u.id
ORDER BY u.last_name, u.first_name;


SELECT u.first_name, u.last_name, COUNT(ps.id) AS count_of_sold_items FROM users u 
JOIN products ps ON ps.seller_id = u.id
WHERE ps.buyer_id IS NOT NULL AND (
	SELECT COUNT(*) FROM products p 
    WHERE p.seller_id = u.id AND p.buyer_id IS NOT NULL) > 0
GROUP BY u.id
ORDER BY u.last_name, u.first_name;
