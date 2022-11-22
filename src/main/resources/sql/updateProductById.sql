UPDATE mall.product
SET product_name=:productName,
    category=:category,
    image_url=:imageUrl,
    price=:price,
    stock=:stock,
    description=:description,
    last_modified_date=:lastModifiedDate
WHERE product_id = :productId