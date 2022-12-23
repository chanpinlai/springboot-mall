drop procedure open_lottery;
create procedure open_lottery()
begin
    insert into mall.lottery(num1,num2,num3,ctime)
    select FLOOR(rand()*9)+1,FLOOR(rand()*9)+1,FLOOR(rand()*9)+1,now();
end;
drop event lottery_event;
create event if not exists lottery_event -- 建立一個事件
    on schedule every  30 second  -- on schedule 什麼時候來執行
    on completion preserve
    do call open_lottery;


# 在2019年2月1日4點執行一次
# on schedule at '2019-02-01 04:00:00'
# 2. 重複計劃執行
# on schedule every 1 second 每秒執行一次
# on schedule every 1 minute 每分鐘執行一次
# on schedule every 1 day 沒天執行一次
# 3.指定時間範圍的重複計劃任務
# 每天在20:00:00執行一次
# on schedule every 1 day starts '2019-02-01 20:00:00'

select * from lottery;
-- 啟動
alter event lottery_event enable;
-- 停止
alter event lottery_event disable;