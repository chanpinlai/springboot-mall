-- function用法
DROP FUNCTION getProductNameFunction;
create function getProductNameFunction(productId int) returns varchar(128)
    reads sql data  -- 從資料庫中讀取資料，但不修改資料
begin
    declare productName varchar(128) default '';
    select product_name into productName from mall.product where product_id=productId;
    return productName;
end;
select getProductNameFunction(3);


-- 清空
truncate table user_trigger;
truncate table oplog;
drop trigger tr_users_insert;
drop trigger tr_users_delete;
-- trigger insert
create trigger tr_users_insert after insert on user_trigger
    for each row
begin
    insert into oplog(userid,username,action,optime)
    values(NEW.id,NEW.name,'insert',now());
end;
-- trigger delete
create trigger tr_users_delete before delete on user_trigger
    for each row
begin
    insert into oplog(userid,username,action,optime)
    values(OLD.id,OLD.name,'delete',now());
end;
-- 新增
insert into user_trigger(id,name,age,status,score,accontid)
    values(6,'小周',23,1,'60','10001');
-- 確認
select * from user_trigger;
select * from oplog;
-- 刪除
delete from user_trigger where id=6;
-- 確認
select * from user_trigger;
select * from oplog;

-- procedure case 用法
create procedure updateStatusToScoreProcedure(userid int)
begin
    declare my_status int default 0;
    select status into my_status from user_trigger where id=userid;

    case my_status
        when 1 then update user_trigger set score=10 where id=userid;
        when 2 then update user_trigger set score=20 where id=userid;
        when 3 then update user_trigger set score=30 where id=userid;
        else update user_trigger set score=40 where id=userid;
        end case;
end;

call updateStatusToScoreProcedure(6);