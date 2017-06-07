CREATE DEFINER=`root`@`localhost` FUNCTION `initcap`(input VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
BEGIN
	-- iterator
	DECLARE i INT;
	-- input length
    DECLARE len INT;

	-- transform all to lower case
	SET input = LCASE(input);
    SET len = LENGTH(input);
	SET i = 0;

	WHILE (i < len) DO
		-- Check if the next character ( i + 1 ) should be capital letter with checkSeparator function
		-- The first character always should be capital letter ( i = 0)
		IF (checkDelimiter(MID(input,i,1)) OR i = 0) THEN
			IF (i < len) THEN
				-- Change only one character to capital letter
				-- 		From begining to i -> not change
				-- 		i + 1 --> transform into capital letter
				-- 		From i + 2 to end --> not change
				SET input = CONCAT(
					LEFT(input,i),
					UCASE(MID(input,i + 1,1)),
					RIGHT(input,len - i - 1)
				);
			END IF;
		END IF;
		SET i = i + 1;
	END WHILE;

	RETURN input;
END

CREATE DEFINER=`root`@`localhost` FUNCTION `checkDelimiter`(letter VARCHAR(1)) RETURNS tinyint(1)
BEGIN
	-- Array with characters for define when apply capital letter 
    -- Other possible solution could be to pass it as an argument
	DECLARE wordSeparators VARCHAR(10) DEFAULT ' ';
	-- Check if wordSeparators contains letter
    IF (LOCATE(letter, wordSeparators) > 0) THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END
