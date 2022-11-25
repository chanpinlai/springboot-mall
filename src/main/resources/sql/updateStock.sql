UPDATE mall.product
SET stock=:stock,
    last_modified_date=:last_modified_date
WHERE product_id = :product_id