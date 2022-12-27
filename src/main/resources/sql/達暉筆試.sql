-- 查詢條件：平均考券分數大於 60分
-- 查訊內容：員工姓名、平均考券分數
select staff.Name, avg(Score.Score)
from staff
         join Score on staff.ID = Score.StudentID
where Score.Score > 60
group by staff.ID;


# 查詢條件：無
# 查訊內容：員工編號、員工姓名、所測驗的數量、總考券分數
select staff.ID, staff.Name, count(*), sum(Score.Score)
from mall.staff
         join Score on staff.ID = Score.StudentID
GROUP BY staff.id;