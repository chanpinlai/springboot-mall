# 分頁
SELECT * FROM mall.product LIMIT 2 OFFSET 0;
#總筆數
SELECT count(*) FROM mall.product WHERE 1=1;

#LEFT JOIN
SELECT * FROM mall.order_item o LEFT JOIN mall.product p on o.order_id = p.product_id;