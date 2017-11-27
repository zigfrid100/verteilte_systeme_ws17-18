package de.hda.fbi.ds.ks;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.*;

/**
 * Created by zigfrid on 13.11.17.
 */
public class UDPSocketClient {

    /** The UDP port the client connects to. */
    private static int SERVER_PORT = 8181;

    /** The UDP socket used to send data. */
    private DatagramSocket udpSocket;
    /** The IP address the client connects to. */
    private InetAddress address;
    /** A buffer array to store the datagram information. */
    private byte[] buf = new byte[1024];
    private byte[] incomingData = new byte[1024];
    /** InetAdress String */
    private String ipAddresString = "141.100.42.159";
    private String ipAddresLocalhostString = "localhost";
    private String answerFromServer;

    /**
     * Default constructor that creates, i.e., opens
     * the socket.
     *
     * @throws IOException In case the socket cannot be created.
     */
    public UDPSocketClient() throws IOException {
        address = InetAddress.getByName(ipAddresLocalhostString);
        udpSocket = new DatagramSocket();
        System.out.println("Started the UDP socket that connects to " + address.getHostAddress());
    }

    /**
     * Method that transmits a String message via the UDP socket.
     */
    public void sendMsg(Product product)  throws IOException,InterruptedException {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);

        oo.writeObject(product);
        oo.close();

        // Convert the message into a byte-array.
        buf = bStream.toByteArray();
        // Create a new UDP packet with the byte-array as payload.
        DatagramPacket sendPacket  = new DatagramPacket(buf, buf.length, address, SERVER_PORT);
        DatagramPacket incomingPacket = new DatagramPacket(incomingData,incomingData.length);
        // Send the data.
        try {
            //send the packet on server
            udpSocket.send(sendPacket);

            //check if server available
           // udpSocket.setSoTimeout(2000);

            udpSocket.receive(incomingPacket);
            //write answer counter
            this.answerFromServer = new String(incomingPacket.getData());

            System.out.println("Message sent with payload: " + product.toPrint());
        }  catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not send data.\n" + e.getLocalizedMessage());
        }
    }

    public String getAnswerFromServer(){
        return answerFromServer;
    }

}
