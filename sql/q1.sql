-- Set rank to 0 for initializate 
SET @rank = 0;
-- Select data, ordering by votes desc and adding up rank for each row
SELECT @rank:=@rank + 1 as Ranking, name as Name, votes as Total_votes 
FROM votes
ORDER BY votes desc;