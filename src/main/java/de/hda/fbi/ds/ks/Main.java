package de.hda.fbi.ds.ks;

import java.io.IOException;

/**
 * Created by zigfrid on 13.11.17.
 *
 * main method that starts Menu to choose server or client.
 */

/**
 * 1 paket = 43.0 ms
 * 10 pakets = 46.0 ms
 * 1 000 pakets = 181.0 ms
 * 1 000 000 pakets  = 25023.0 ms
 */


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException{

        int choice;
        String packetsToSend;
        /**
         * for test -> true
         * for normal -> false
         **/
        boolean helpForTest = false;

        // Display menu graphics
        System.out.println("============================");
        System.out.println("|     MENU SMART FRIDGE    |");
        System.out.println("============================");
        System.out.println("| Options:                 |");
        System.out.println("|        1. Start Server   |");
        System.out.println("|        2. Start Client   |");
        System.out.println("|        3. Exit           |");
        System.out.println("============================");
        choice = Keyin.inInt(" Select option: ");

        // Switch construct
        switch (choice) {
            case 1:
                System.out.println("Start Server selected");
                    /** The UDP socket server. */
                    UDPSocketServer udpSocketServer = null;

                    try {
                        udpSocketServer = new UDPSocketServer();
                        /** for test */
                        if(helpForTest) {
                            udpSocketServer.helpForTest = helpForTest;
                        }
                    } catch (IOException e) {
                        System.out.println("Could not start UDP Socket Server.\n" + e.getLocalizedMessage());
                        System.exit(1);
                    }

                    // Run the UDP socket server.
                    udpSocketServer.run();
                break;
            case 2:
                System.out.println("Start Client selected");

                    /** Timer for Test*/
                    Timer timer = new Timer();

                    /** The UDP socket client. */
                    UDPSocketClient udpSocketClient = null;
                    /** The Product Object. */
                    Product myProduct = new Product();
                    /** for test */
                    if(helpForTest){
                        myProduct.setValueOfProduct(1000000);
                        packetsToSend = "" + myProduct.getValueOfProduct();
                    }else{
                        packetsToSend = "" + myProduct.getValueOfProduct();
                    }

                    /** allowed chars */
                    String pattern = "^[a-zA-ZäüöÄÜÖ0-9 ]*$";
                    // Create a Pattern object
                    java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
                    // Now create matcher object.
                    java.util.regex.Matcher m = r.matcher(myProduct.getNameOfProduct());
                    // if chars authorized send UDP packet to Server
                    if (m.find()) {

                        try {
                            udpSocketClient = new UDPSocketClient();
                        } catch (IOException e) {
                            System.out.println("Could not start UDP Socket Client.\n" + e.getLocalizedMessage());
                            System.exit(1);
                        }

                        // Send the message.
                        for (int i = 0 ; i <= myProduct.getValueOfProduct();){
                            udpSocketClient.sendMsg(myProduct);
                            myProduct.reduce();
                            /** for test */
                            if(!helpForTest){
                                Thread.sleep(2000);
                            }
                        }
                        //Vrgleich res and req
                        System.out.println("Sendet Packets " + packetsToSend  + " and" + udpSocketClient.getAnswerFromServer() + " Receive confirmation packets from the server");
                        timer.getEndTime();
                        System.out.println("Time is " + timer.counting()  + " ms");
                    }else{
                        System.out.println("NOT ALLOWED SIGNS AVAILABLE");
                    }
                break;
            case 3:
                System.out.println("Exit selected");
                break;
            default:
                System.out.println("Invalid selection");
                break; // This break is not really necessary
        }


    }
}
