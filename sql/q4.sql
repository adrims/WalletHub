CREATE DEFINER=`root`@`localhost` PROCEDURE `search_bugs`(startDate DATE, endDate DATE)
BEGIN
	DECLARE output VARCHAR(750);
	DECLARE dateToCheck DATE;

	SET @output = ''; -- Avoid to concat with null
	SET dateToCheck = startDate; -- Initialize date to check with start date

	-- starting loop until reach end date
	WHILE dateToCheck <= endDate DO

		-- Concat one select for each day with UNION
		SET @output = CONCAT(@output, ' SELECT DATE(\'',dateToCheck,'\') as DAY, b.id from bugs b WHERE open_date <= DATE(\'',dateToCheck,'\') AND (close_date >= DATE(\'',dateToCheck,'\') OR close_date IS NULL) UNION ALL');
		SELECT ADDDATE(dateToCheck, INTERVAL 1 DAY) INTO dateToCheck; -- Move to the next day to check

	END WHILE; 
	
	SET @output = LEFT(@output, LENGTH(@output)-LENGTH('UNION ALL')); -- Remove last UNION ALL
	SET @output = CONCAT('SELECT t.DAY,COUNT(t.id) AS OPEN_BUGS FROM (',@output); -- New select for make COUNT
	SET @output = CONCAT(@output,') as t GROUP BY t.DAY'); -- Grouping by day
    
	PREPARE  statement FROM @output; 
	EXECUTE statement; 
   
END