public class Diner implements Runnable, Comparable<Diner>{
    //Class variable declarations
    public int diner_id;
    public int arrivalTime;
    public Order order;
    public int tableAssigned;

    //constructor
    public Diner(){
        //Empty constructor if required
    }

    //Diner has arrived
    public void arrive()
    {
        System.out.println("HH:MM - " + getFunction() + ": Diner " + diner_id + " has arrived.");
    }

    //Order is taken for diner
    public void takeOrder() throws InterruptedException{
        synchronized(Restaurant.hungryDiners){
            Restaurant.hungryDiners.add(this);
            Restaurant.hungryDiners.notify();
        }
    }

    //Diner is leaving
    public void leave(){
        System.out.println("HH:MM - " + getFunction()+": Diner with Diner " + diner_id + " is done and leaves the restaurant.");
        synchronized(Restaurant.freeTables){
            Restaurant.freeTables.add(tableAssigned);
            Restaurant.freeTables.notify();
        }
    }

    //Diner books table
    public void book() throws InterruptedException{
        synchronized(Restaurant.freeTables){
            if(Restaurant.freeTables.isEmpty() == false){
                tableAssigned = Restaurant.freeTables.remove(0);
                System.out.println("HH:MM - " + getFunction()+": Diner " + diner_id + " seats at Table " + tableAssigned + " .");
            }
            else
            {
                Restaurant.freeTables.wait();
            }
        }
    }

    //Last diner leaves
    public void lastDinerLeaves(){
        synchronized(Restaurant.diners_left_count){
            Restaurant.diners_left_count++;

            if(Restaurant.diners_left_count != Restaurant.diners_count){
                //Do nothing, add if required
            }
            else{
                System.out.println("HH:MM - " + getFunction()+": Last diner has left the restaurant");
                System.exit(0);
            }
        }
    }

    //Diner eats
    public void eat() throws InterruptedException{
        System.out.println("HH:MM - " + getFunction() + ": Order for diner with diner " + diner_id + " is ready and starts eating.");
        Thread.sleep(30000);
    }


    //Diner is waiting on order
    public void waitingOnOrder() throws InterruptedException{
        synchronized (this) {
            this.wait();
        }
    }

    //Compareto wrapper
    public int compareTo(Diner o) {
        if (arrivalTime > o.arrivalTime)
            return 1;
        else if (arrivalTime < o.arrivalTime)
            return -1;
        return 0;
    }

    @Override
    public void run(){
        arrive();
        try{
            book();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            takeOrder();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try{
            waitingOnOrder();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        try {
            eat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        leave();
        lastDinerLeaves();
    }

    //Function to get time
    public String getFunction(){
        String answer;
        long currentTime = System.currentTimeMillis();
        currentTime = currentTime / 1000;
        long TimeSpent = currentTime - Restaurant.startTime;

        if(TimeSpent >= 60){
            long hour = TimeSpent / 60;
            long min = TimeSpent % 60;
            String hour_str = String.format("%02d", hour);
            String min_str = String.format("%02d", min);
            answer = hour_str + ":" + min_str;

        }else{
            answer = (TimeSpent < 10 ? new String("00:0"): new String("00:"));
            answer = answer + TimeSpent;
        }

        return answer;
    }

    }
