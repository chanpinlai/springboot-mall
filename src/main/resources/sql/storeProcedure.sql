CREATE PROCEDURE productProcedure()
begin
    select * from mall.product;
end;
call productProcedure();

-- IN
CREATE PROCEDURE productProcedureIN(productId int)
begin
    select * from mall.product where product_id = productId;
end;

call productProcedureIN(1);

-- IN/OUT
CREATE PROCEDURE productProcedureOUT(in productId int, out productName varchar(128))
begin
    select product_name into productName from mall.product where product_id = productId;
end;
set @productName = '';
call productProcedureOUT(3, @productName);
select @productName as productName;

-- INOUT
CREATE PROCEDURE productProcedureINOUT(inout productId int, inout productName varchar(128))
begin
    select product_id, product_name into productId,productName from mall.product where product_id = productId;
end;
set @productId = 4;
set @productName = '';
call productProcedureINOUT(@productId, @productName);
select @productId, @productName as product;

-- if odd=name even=id
create procedure productProcedureIF(in productId int)
begin
    if (productId % 2 = 0)
    then
        select product_id from mall.product where product_id = productId;
    else
        select product_name from mall.product where product_id = productId;
    end if;
end;

-- while
create procedure testProcedureWhile(in count int)
begin
    declare i int default 0;
    while (i < count)
        do
            begin
                set i = i + 1;
                insert into test_procedure(id) values (i);
            end;
        end while;
end;

call testProcedureWhile(3);
select *
from mall.test_procedure;
truncate table mall.test_procedure;

-- repeat
create procedure testProcedureRepeat(in count int)
begin
    declare i int default 0;
    repeat
        set i = i + 1;
        insert into test_procedure(id) values (i);
    until i >= count end repeat;
end;

call testProcedureRepeat(10);
select *
from mall.test_procedure;
truncate table mall.test_procedure;

DROP PROCEDURE productProcedure;
DROP PROCEDURE productProcedureIN;
DROP PROCEDURE productProcedureOUT;
DROP PROCEDURE productProcedureINOUT;
DROP PROCEDURE productProcedureIF;
DROP PROCEDURE testProcedureWhile;
DROP PROCEDURE testProcedureRepeat;