Table: Person

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| email       | varchar |
+-------------+---------+
id is the primary key (column with unique values) for this table.
Each row of this table contains an email. The emails will not contain uppercase letters.
 

Write a solution to report all the duplicate emails. Note that its guaranteed that the email field is not NULL.

Return the result table in any order.

The result format is in the following example.

 

Example 1:

Input: 
Person table:
+----+---------+
| id | email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+
Output: 
+---------+
| Email   |
+---------+
| a@b.com |
+---------+
Explanation: a@b.com is repeated two times.


Solution:

SELECT EMAIL FROM PERSON GROUP BY EMAIL HAVING COUNT(EMAIL) > 1;

SELECT DISINCT P1.EMAIL FROM PERSON P1, PERSON P2 WHERE P1.EMAIL = P2.EMAIL
AND P1.ID <> P2.ID;

SELECT DISINCT P1.EMAIL FROM PERSON P1 JOIN PERSON P2 ON P1.EMAIL = P2.EMAIL
AND P1.ID <> P2.ID;

SELECT DISTINCT(P1.EMAIL) FROM PERSON P1, PERSON P2
WHERE P1.EMAIL = P2.EMAIL AND P1.ID <> P2.ID;

SELECT DISTINCT(P1.EMAIL) FROM PERSON P1 JOIN PERSON P2
ON P1.EMAIL = P2.EMAIL AND P1.ID <> P2.ID;