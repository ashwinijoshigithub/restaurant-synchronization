public class Cook implements Runnable{

    //class variable declarations
    public int cook_id;
    Diner dinerServed;

    //constructor
    public Cook(){
        //Empty constructor if required
    }

    //Cook is using machines to make orders
    public void makeOrder() throws InterruptedException{
        Order ord = dinerServed.order;
        int total_items = ord.burgers_count + ord.coke_count + ord.fries_count + ord.sundae_count;
        while(total_items > 0){
            if(ord.coke_count > 0){
                synchronized(Restaurant.cokeMachine){
                    if(Restaurant.isCokeFree == true){
                        System.out.println("HH:MM - " + getFunction()+": Cook "+cook_id+" uses the coke machine.");
                        Thread.sleep(2000);
                        --ord.coke_count;
                        if(ord.coke_count == 0)
                        {
                            Restaurant.isCokeFree = true;
                            Restaurant.cokeMachine.notify();
                        }
                    }else{
                        Restaurant.cokeMachine.wait();
                    }

                }
            }

            if(ord.fries_count > 0){
                synchronized(Restaurant.friesMachine){
                    if(Restaurant.isFriesFree == true)
                    {
                        System.out.println("HH:MM - " + getFunction()+": Cook "+cook_id+" uses the fries machine.");
                        Thread.sleep(3000);
                        --ord.fries_count;
                        if(ord.fries_count == 0)
                        {
                            Restaurant.isFriesFree = true;
                            Restaurant.friesMachine.notify();
                        }
                    }
                    else
                    {
                        Restaurant.friesMachine.wait();
                    }
                }
            }

            if(ord.burgers_count > 0){
                synchronized(Restaurant.burgerMachine){
                    if(Restaurant.isBurgerFree == true){
                        System.out.println("HH:MM - " + getFunction()+": Cook "+cook_id+" uses the burger machine.");
                        Thread.sleep(5000);
                        --ord.burgers_count;
                        if(ord.burgers_count == 0)
                        {
                            Restaurant.isBurgerFree = true;
                            Restaurant.burgerMachine.notify();
                        }
                    }else{
                        Restaurant.burgerMachine.wait();

                    }
                }
            }

            if(ord.sundae_count > 0){
                synchronized(Restaurant.sundaeMachine){
                    if(Restaurant.isSundaeFree == true){
                        System.out.println("HH:MM - " + getFunction()+": Cook "+cook_id+" uses the sundae machine.");
                        Thread.sleep(1000);
                        --ord.sundae_count;
                        if(ord.sundae_count == 0)
                        {
                            Restaurant.isSundaeFree = true;
                            Restaurant.sundaeMachine.notify();
                        }
                    }else{
                        Restaurant.sundaeMachine.wait();
                    }
                }
            }

            total_items = ord.burgers_count + ord.coke_count + ord.fries_count + ord.sundae_count;

            if(total_items == 0){
                synchronized (dinerServed) {
                    dinerServed.notify();
                }
            }

        }
    }

    //Cooke serves diner
    public void giveDiner() throws Exception{
        synchronized(Restaurant.hungryDiners){
            while(Restaurant.hungryDiners.isEmpty())
            {
                try {
                    Restaurant.hungryDiners.wait();
                } catch (InterruptedException e) {
                    throw new Exception(e.getMessage());
                }
            }

            dinerServed = Restaurant.hungryDiners.remove(0);
            System.out.println("HH:MM - " + getFunction() + ": Cook " + cook_id + " is making food for Diner with Diner " + dinerServed.diner_id);
        }
    }

    //Run method is overwritten
    @Override
    public void run(){
        while(true){
            try {
                giveDiner();
                makeOrder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //Function to get time
    public String getFunction(){
        long currentTime = System.currentTimeMillis();
        currentTime = currentTime / 1000;
        long TimeSpent = currentTime - Restaurant.startTime;
        String answer;
        long hour = 0, min = 0;

        if(TimeSpent >= 60){
            hour = TimeSpent / 60;
            min = TimeSpent % 60;
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
