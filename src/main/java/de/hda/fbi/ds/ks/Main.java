package de.hda.fbi.ds.ks;

import java.io.IOException;

/**
 * Created by zigfrid on 13.11.17.
 *
 * main method that starts Menu to choose server or client.
 */

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException{

        int choice;

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
                    } catch (IOException e) {
                        System.out.println("Could not start UDP Socket Server.\n" + e.getLocalizedMessage());
                        System.exit(1);
                    }

                    // Run the UDP socket server.
                    udpSocketServer.run();
                break;
            case 2:
                System.out.println("Start Client selected");

                    /** The UDP socket client. */
                    UDPSocketClient udpSocketClient = null;
                    // The Product Object
                    Product myProduct = new Product();

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
                        Thread.sleep(2000);
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
