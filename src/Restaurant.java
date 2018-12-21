import java.io.*;
import java.util.*;

public class Restaurant {

    //Variables for 4 types of machines
    public static boolean isBurgerFree, isFriesFree, isCokeFree, isSundaeFree;
    public static Object burgerMachine, friesMachine, cokeMachine, sundaeMachine;

    //Data structures for diner
    public static ArrayList<Integer> freeTables;
    public static PriorityQueue<Diner> diners;
    public static ArrayList<Diner>hungryDiners;

    //Variables for class counts
    public static int diners_count, tables_count, cooks_count;
    public static Integer diners_left_count = 0;
    public static long startTime;

    public static void main(String args[]) throws NumberFormatException, IOException, InterruptedException
    {

        //Variable declarations
        String diner_info;
        int diner_id, arrivalTime;
        int burgers_count, fries_count, coke_count, sundae_count;

        //Initialisation for Diner Information
        isBurgerFree = true;
        isFriesFree = true;
        isCokeFree = true;
        isSundaeFree = true;

        burgerMachine = new Object();
        friesMachine= new Object();
        cokeMachine = new Object();
        sundaeMachine = new Object();

        String input = args[0];
        FileReader fRead = new FileReader(input);
        BufferedReader bRead = new BufferedReader(fRead);

        //scanning the input
        diners_count = Integer.parseInt(bRead.readLine().trim());
        tables_count =Integer.parseInt(bRead.readLine().trim());
        cooks_count = Integer.parseInt(bRead.readLine().trim());

        //check if any of the count is zero for invalid input
        if(diners_count == 0 || tables_count == 0 || cooks_count == 0){
            System.out.println("Invalid Input");
            System.exit(0);
        }

        //Initialising diner data structures
        diners = new PriorityQueue<>();
        hungryDiners = new ArrayList<Diner>(diners_count);

        //start the timer
        startTime = System.currentTimeMillis() / 1000;
        int prevArrival = 0, waitTime;

        //Add free tables equal to the number of tables
        freeTables = new ArrayList<Integer>(tables_count);
        int i = 1;
        while(i <= tables_count)
        {
            freeTables.add(i);
            i = i + 1;
        }

        //Variables for the loop
        int temp_diner_count = diners_count;
        int din = 0;
        for(din = 0; din < temp_diner_count; din++)
        {
            String line = bRead.readLine();
            if (line == null)
                break;
            diner_info = line;
            String[] info = diner_info.split("\\s+");
            diner_id = din + 1;
            arrivalTime = Integer.parseInt(info[0]);

            if(arrivalTime <= 120){
                //do nothing since the input is valid
            }
            else{
                din++;
                continue;
            }

            //parsing food items count from info
            int parsed[] = new int[4];
            for(int j = 0; j < 4; j++){
                parsed[j] = Integer.parseInt(info[j+1]);
            }

            burgers_count = parsed[0];
            fries_count = parsed[1];
            coke_count = parsed[2];
            sundae_count = parsed[3];

            //creating the order
            Order ord = new Order();

            //setting count to 0 if negative
            ord.burgers_count = (burgers_count < 0 ? 0 : burgers_count);
            ord.fries_count = (fries_count < 0 ? 0 : fries_count);
            ord.coke_count = (coke_count < 0 ? 0 : coke_count);
            ord.sundae_count = (sundae_count < 0 ? 0 : sundae_count);

            //Creating Diner
            Diner dinerObject = new Diner();
            dinerObject.diner_id = diner_id;
            dinerObject.arrivalTime = arrivalTime;
            dinerObject.order = ord;

            //Adding diner to the list of diners
            diners.add(dinerObject);

        }

        //check for invalid inputs
        diners_count = diners.size();
        if(temp_diner_count != din){
            System.out.println("Invalid input");
            System.exit(0);
        }

        //Thread for cook
        i = 0;
        while(i < cooks_count)
        {
            Cook ck = new Cook();
            ck.cook_id = i + 1;
            Thread cookThread = new Thread(ck);
            cookThread.start();
            i = i + 1;
        }

        //Thread for diner
        i = 0;
        while(i < diners_count)
        {
            Diner temp_diner = diners.poll();
            int inMillis = temp_diner.arrivalTime - prevArrival;
            waitTime =  inMillis * 1000;
            Thread dinerThread = new Thread(temp_diner);
            Thread.sleep(waitTime);
            dinerThread.start();
            prevArrival = temp_diner.arrivalTime;
            i = i + 1;
        }
        bRead.close();
        fRead.close();
    }
    }

