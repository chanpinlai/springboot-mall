SELECT oi.order_item_id,
       oi.order_id,
       oi.product_id,
       oi.quantity,
       oi.amount,
       p.product_name,
       p.image_url
FROM mall.order_item oi
         LEFT JOIN mall.product p on oi.product_id = p.product_id
WHERE order_id = :order_id