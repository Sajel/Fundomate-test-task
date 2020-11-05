###### Task 1:
**a)** Examples of hierarchy are in the CompensationCalculatorTest

**b)** Class for calculation is CompensationCalculator

**c)** Time complexity is O(n) because we are visiting every node of tree once

Notes to task A:
- CompensationCalculator class uses recursion to traverse tree. It can lead to stackoverflow exception. It can be replaced with loop using stack.

###### Task 2:
**a)** Class for calculation of negative days is NegativeDaysCalculator, example are in NegativeDaysCalculatorTest

**b)** SQL script for database scheme is in account.sql file

**c)** Example of scheme is in account-json.sql

Relationship database might be an issue  when there are a lot of transactions, and we need to do join tables. Then search in transactions table by account_id can take a lot of time. We can create an index for that column and it can solve problem but indexes are not the best solution if we are doing a lot of inserts|updates|deletes.
In general structure of database depends on how data will be retrieved/modified. Depends on usage patterns. For example if we retrieve only all transactions per account and never retrieve single account we can store transactions in a column with type json. If we query transactions per month only then we can group them by months and store them right in DB with this structure.
Relationship database gives big flexibility of building queries but can have bad perfomance. NoSQL databases can give us better perfomance but we have to think about usage patterns before. (Relational databases also gives strict scheme => validation).

###### Task 3:
Class for calculation is GlassBallsCalculator. Test with all combinations is GlassBallsCalculatorTest#full_test.
Possible solution for 3b is in "task3b" branch.