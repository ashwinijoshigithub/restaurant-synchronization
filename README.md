# restaurant-synchronization
Restaurant simulation using Java synchronization primitives. (OSU CSE 6431 Project)

### Problem Statement

Restaurant is open. A restaurant requires careful coordination of resources, which are the tables in the restaurant, the cooks available to cook the order, and the machines available to cook the food (There is no contention on other possible resources, like servers to take order and serve the orders).

Eaters in the restaurant place their orders when they get seated. These orders are then handled by available cooks. Each cook handles one order at a time. A cook handles an order by using machines to cook the food items. There are only four types of food served by the restaurant - a Buckeye Burger, Brutus Fries, Coke, and a TBDBITL Sundae. Each person entering the restaurant occupies one table and orders one or more burgers, zero or more orders of fries, zero or one glass of coke, and zero or one order of the dessert. The cook needs to use the burger machine for 5 minutes to prepare each burger, fries machine for 3 minutes for one order of fries, the soda machine for 2 minutes to fill a glass with coke, and the ice-cream machine for 1 minute to prepare the sundae. The cook can use at most one of these four machines at any given time. There is only one machine of each type in the restaurant (so, for example, over a 5 minute duration, only one burger can be prepared). Once the food (all items at the same time) are brought to a diner's table, they take exactly 30 minutes to finish eating them, and then leave the restaurant, making the table available for another diner (immediately).

### Input

Diners arrive at the restaurant over a 120 minute duration, which is taken as input by the simulation. If there are N diners arriving, the input has N+3 lines, specifying, in order: number of diners (N), number of tables, number of cooks, and N other lines each with the following five numbers: a number between 0 and 120 stating when the diner arrived (this number is increasing across lines), the number of burgers ordered (1 or higher), number of order of fries (0 or higher), whether or not coke was ordered (0 or 1), and whether or not the sundae was ordered (0 or 1). 

### Output

Simulation outputs when each diner was seated, which table they were seated in, which cook processed their order, when each of the machines was used for their orders, and when the food was brought to their table. Finally, you should state the time when the last diner leaves the restaurant.

### Execution
1. In terminal, go inside the directory where the code resides.
2. Run javac *.java to compile all java files
3. Then type: java Restaurant *input-file* > *output-file* (For example, java Restaurant input1.txt > output1.txt)

### Assumptions

1. If either of the following is zero, it is considered invalid input - number of diners, number of tables, number of cooks.
2. Also, if the number of diners does not match with number of lines, it is invalid input.
3. For any negative count of food(burger, coke, fries, sundae), it is assumed to be zero. Along with it, if the count is zero, it means that food item has not been ordered.
4. If arrival time for any diner is greater than 120, then it is ignored since restaurant is open during zero to 120 minutes.
5. It is assumed that the number of burgers ordered (1 or higher), number of order of fries (0 or higher), whether or not coke was ordered (0 or 1), and whether or not the sundae was ordered (0 or 1).
6. Rather than randomly serving diners, priority queue is used to implement FCFS scheme depending upon arrival time of diners.
