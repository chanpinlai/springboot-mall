SELECT user_id,
       email,
       password,
       created_date,
       last_modified_date
FROM mall.user
WHERE 1 = 1
  AND user_id = :user_id