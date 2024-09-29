183. Customers Who Never Order

able: Customers

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
+-------------+---------+
id is the primary key (column with unique values) for this table.
Each row of this table indicates the ID and name of a customer.
 

Table: Orders

+-------------+------+
| Column Name | Type |
+-------------+------+
| id          | int  |
| customerId  | int  |
+-------------+------+
id is the primary key (column with unique values) for this table.
customerId is a foreign key (reference columns) of the ID from the Customers table.
Each row of this table indicates the ID of an order and the ID of the customer who ordered it.
 

Write a solution to find all customers who never order anything.

Return the result table in any order.

The result format is in the following example.

 

Example 1:

Input: 
Customers table:
+----+-------+
| id | name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+
Orders table:
+----+------------+
| id | customerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+
Output: 
+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+


Solution:

SELECT NAME AS CUSTOMERS FROM CUSTOMERS
WHERE ID NOT IN (SELECT CUSTOMERID ID FROM ORDERS);


-- To know the names of customers who ordered a specific number(1,2,3,..) of order
-- Can use nested subqueries in where clause with different table
-- Can pass outer reference inside nested subquery
SELECT NAME AS Customers FROM CUSTOMERS
WHERE (SELECT COUNT(*) FROM ORDERS WHERE CUSTOMERID = CUSTOMERS.ID) < 1

-- Can't mention O.ID = null otherwise empty result is output
SELECT NAME AS Customers FROM CUSTOMERS C
LEFT JOIN ORDERS O ON C.ID = O.CUSTOMERID
WHERE O.ID IS NULL;

-- can use O.CUSTOMERID is null
SELECT NAME AS Customers FROM CUSTOMERS c
LEFT JOIN ORDERS o ON C.ID = O.CUSTOMERID
WHERE O.CUSTOMERID IS NULL;










