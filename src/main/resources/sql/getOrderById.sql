SELECT order_id, user_id, total_amount, created_date, last_modified_date
FROM mall.`order`
WHERE order_id = :order_id