196. Delete Duplicate Emails

Table: Person

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| email       | varchar |
+-------------+---------+
id is the primary key (column with unique values) for this table.
Each row of this table contains an email. The emails will not contain uppercase letters.
 

Write a solution to delete all duplicate emails, keeping only one unique email with the smallest id.

For SQL users, please note that you are supposed to write a DELETE statement and not a SELECT one.

For Pandas users, please note that you are supposed to modify Person in place.

After running your script, the answer shown is the Person table. The driver will first compile and run your piece of code and then show the Person table. The final order of the Person table does not matter.

The result format is in the following example.

 

Example 1:

Input: 
Person table:
+----+------------------+
| id | email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+
Output: 
+----+------------------+
| id | email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+
Explanation: john@example.com is repeated two times. We keep the row with the smallest Id = 1.


Solution:

DELETE P1 FROM PERSON P1, PERSON P2
WHERE P1.EMAIL = P2.EMAIL AND P1.ID > P2.ID;

The above uses Cartesian product
FROM PERSON P1, PERSON P2 means:

1|john@example.com|1|john@example.com
1|john@example.com|2|bob@example.com 
1|john@example.com|3|john@example.com

2|bob@example.com|1|john@example.com
2|bob@example.com|2|bob@example.com
2|bob@example.com|3|john@example.com

3|john@example.com|1|john@example.com
3|john@example.com|2|bob@example.com
3|john@example.com|3|john@example.com

FROM PERSON P1, PERSON P2
WHERE P1.EMAIL = P2.EMAIL means
1|john@example.com|1|john@example.com
1|john@example.com|3|john@example.com

2|bob@example.com|2|bob@example.com

3|john@example.com|1|john@example.com
3|john@example.com|3|john@example.com

FROM PERSON P1, PERSON P2
WHERE P1.EMAIL = P2.EMAIL AND P1.ID > P2.ID means
3|john@example.com|1|john@example.com


DELETE P1 FROM PERSON P1, PERSON P2
WHERE P1.EMAIL = P2.EMAIL AND P1.ID > P2.ID

DELETE P1 only deletes the corresponding row in P1 table which is last row in this case.
delete this rows matching row in p1 using p1:  delete p1



DELETE P1 FROM PERSON P1
CROSS JOIN PERSON P2
WHERE P1.EMAIL=P2.EMAIL AND P1.ID > P2.ID;


-- Fastest
-- CTE - Common table expression can be used with SELECT, INSERT, UPDATE, DELETE
WITH NEWPERSON AS(
	SELECT MIN(ID) AS MINID FROM PERSON
	GROUP BY EMAIL
)

DELETE FROM PERSON
WHERE ID NOT IN (SELECT MINID FROM NEWPERSON);


-- Fast
DELETE P FROM PERSON P
LEFT JOIN(SELECT MIN(ID) AS MINID FROM PERSON GROUP BY EMAIL) A
ON P.ID = A.MINID
WHERE A.MINID IS NULL;


-- Very slow
DELETE FROM PERSON 
WHERE ID IN (
    SELECT A.ID 
    FROM (
        SELECT ID, EMAIL, ROW_NUMBER() OVER (PARTITION BY EMAIL ORDER BY ID) AS RN 
        FROM PERSON
        ) A
    WHERE A.RN > 1
);






