SELECT (
    SELECT DISTINCT salary
    FROM Employee
    ORDER BY salary DESC
    LIMIT 1 OFFSET 1
) AS SecondHighestSalary;

SELECT MAX(salary) AS SecondHighestSalary
FROM Employee
WHERE salary < (SELECT MAX(salary) FROM Employee);

# Write your MySQL query statement below
SELECT(
    SELECT DISTINCT salary
    FROM (
        SELECT salary, 
        DENSE_RANK() OVER ( ORDER BY salary DESC) as rnk
        FROM Employee
    ) AS RankedSalary
    WHERE rnk = 2
) AS SecondHighestSalary;

CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  RETURN (
      # Write your MySQL query statement below.
    SELECT salary FROM (SELECT salary, 
       DENSE_RANK() OVER( ORDER BY salary DESC ) rnk
       FROM Employee) AS highestSalary
    WHERE rnk = n
    LIMIT 1
  );
END

# 187 rank score
# Write your MySQL query statement below
SELECT t.score, t.rnk as 'rank' 
FROM(
    SELECT 
    score, 
    DENSE_RANK() OVER( ORDER BY score DESC ) as rnk 
    FROM Scores
) AS t


#180 consecutive nums
# Write your MySQL query statement below
SELECT stat.num as ConsecutiveNums
FROM ( 
    SELECT
    num, COUNT(num) as ap
    FROM Logs 
    Group by num ) stat
    WHERE stat.ap > 3


select distinct Num as ConsecutiveNums
from Logs
where (Id + 1, Num) in (select * from Logs) and (Id + 2, Num) in (select * from Logs)


# 181
# Write your MySQL query statement below
SELECT e.name as 'Employee'
FROM Employee e
INNER JOIN Employee e2 
WHERE e.managerId = e2.id
AND e.salary > e2.salary

SELECT EMP.name AS Employee 
FROM Employee EMP,Employee MGR
WHERE EMP.managerId=MGR.id AND EMP.salary>MGR.salary


#182 count duplicate email
# Write your MySQL query statement below
SELECT t.email as 'Email'
FROM (
    SELECT email, COUNT( email) as c 
    FROM Person
    GROUP By email
) t
WHERE t.c > 1