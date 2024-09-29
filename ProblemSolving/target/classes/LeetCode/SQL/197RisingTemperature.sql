197. Rising Temperature

Table: Weather

+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| recordDate    | date    |
| temperature   | int     |
+---------------+---------+
id is the column with unique values for this table.
There are no different rows with the same recordDate.
This table contains information about the temperature on a certain day.
 

Write a solution to find all dates id with higher temperatures compared to its previous dates (yesterday).

Return the result table in any order.

The result format is in the following example.

 

Example 1:

Input: 
Weather table:
+----+------------+-------------+
| id | recordDate | temperature |
+----+------------+-------------+
| 1  | 2015-01-01 | 10          |
| 2  | 2015-01-02 | 25          |
| 3  | 2015-01-03 | 20          |
| 4  | 2015-01-04 | 30          |
+----+------------+-------------+
Output: 
+----+
| id |
+----+
| 2  |
| 4  |
+----+
Explanation: 
In 2015-01-02, the temperature was higher than the previous day (10 -> 25).
In 2015-01-04, the temperature was higher than the previous day (20 -> 30).


Solution:

-- Simple Join
-- MySQL

SELECT W1.ID FROM WEATHER W1 JOIN WEATHER W2
ON W1.RECORDDATE = DATE_ADD(W2.RECORDDATE, INTERVAL 1 DAY)
WHERE W1.TEMPERATURE > W2.TEMPERATURE;

-- SQL Server
DATE_ADD -> DATEADD(DAY, 1, W2.RECORDDATE)


-- Subquery Slow

SELECT ID FROM WEATHER W1 
WHERE TEMPERATURE > 
(
	SELECT W2.TEMPERATURE FROM WEATHER W2 
	WHERE W2.RECORDDATE = DATE_SUB(W1.RECORDDATE, INTERVAL 1 DAY)
);


-- May not work if table is not sorted based on record date
SELECT ID FROM WEATHER W1
WHERE TEMPERATURE >
(
	SELECT TEMPERATURE FROM WEATHERE W2
	WHERE W2.ID = W1.ID - 1
);


-- DATE DIFF and Cartesian Product where order of date in date diff is important
SELECT W1.ID FROM WEATHER W1, WEATHER W2
WHERE DATEDIFF(W1.RECORDDATE, W2.RECORDDATE) = 1
AND W1.TEMPERATURE > W2.TEMPERATURE;


-- CTE with LAG function
WITH TEMP AS(
	SELECT *, 
	LAG(TEMPERATURE) OVER (ORDER BY RECORDDATE) AS PREVTEMPERATURE,
	LAG(RECORDDATE) OVER (ORDER BY RECORDDATE) AS PREVDATE
	FROM WEATHER
)

SELECT ID FROM TEMP
WHERE TEMPERATURE > PREVTEMPERATURE
AND DATEDIFF(RECORDDATE, PREVDATE) = 1;


-- CTE with LEAD function
WITH TEMP AS(
	SELECT *,
	LEAD(TEMPERATURE) OVER(ORDER BY RECORDDATE DESC) AS PREVTEMP,
	LEAD(RECORDDATE) OVER(ORDER BY RECORDDATE DESC) AS PREVDATE
	FROM WEATHER
)

SELECT ID FROM TEMP
WHERE TEMPERATURE > PREVTEMP
AND DATEDIFF(RECORDDATE, PREVDATE) = 1;
