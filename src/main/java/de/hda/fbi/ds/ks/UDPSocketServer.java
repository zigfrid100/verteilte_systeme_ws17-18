package de.hda.fbi.ds.ks;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.util.*;

/**
 * Created by zigfrid on 13.11.17.
 */
public class UDPSocketServer {


    /** The UDP port the server listens to. */
    private static int PORT = 8181;

    /** The UDP socket used to receive data. */
    private DatagramSocket udpSocket;
    /** States the server running. */
    private boolean running = true;
    /** A buffer array to store the datagram information. */
    private byte[] buf = new byte[1024];
    /** SensorData List (all Data) */
    private List<SensorData> sensorDatas = new ArrayList<SensorData>();
    /** SensorData List (all Data) */
    private static List<SensorData> actualSensorDatas = new ArrayList<SensorData>();

    /**
     * Default constructor that creates, i.e., opens
     * the socket.
     *
     * @throws IOException In case the socket cannot be created.
     */
    public UDPSocketServer() throws IOException {
        udpSocket = new DatagramSocket( PORT );
        System.out.println("Started the UDP socket server at port " + PORT);
    }

    /**
     * Continuously running method that receives the data
     * from the UDP socket and logs the datagram information.
     */
    public void run() throws InterruptedException {
        while(running) {
            DatagramPacket udpPacket = new DatagramPacket(buf, buf.length);
            try {
                // Receive message
                udpSocket.receive(udpPacket);
                //Print some packet data.
                savePaketdata(udpPacket);
                printActualSensorData();
            } catch (IOException e) {
                System.out.println("Could not receive datagram.\n" + e.getLocalizedMessage());
            }
        }
    }

    /**
     * Extracts some data of a given datagram packet
     * and save.
     *
     * @param udpPacket The datagram packet to extract and print and save.
     */
    private void savePaketdata(DatagramPacket udpPacket)throws IOException{
        // Get IP address and port.
        InetAddress address = udpPacket.getAddress();
        int port = udpPacket.getPort();
        // Get packet length
        int length = udpPacket.getLength();
        // Get the payload and print.
        ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(udpPacket.getData()));
        try {
            Product product = (Product)iStream.readObject();
            saveSensorData(address,port,product,length);
            iStream.close();
            //printPacketData(address,port,length,product);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Prints to standard out.
     * @param  address ,  port ,  length ,  product to print.
     */
    private void printPacketData(InetAddress address , int port , int length , Product product) throws IOException{

        System.out.println("Received a packet: IP:Port: " + address
                + ":" + port
                + ", length: " + length
                + ", payload: " + product.toString());
    }

    /** Prints actual SensorData to standard out.*/
    private void printActualSensorData() throws IOException , InterruptedException{

        for (SensorData sensorData : actualSensorDatas) {
            System.out.println("Received a packet: IP:Port: " + sensorData.printSensorData());
        }
        Thread.sleep(2000);
        System.out.print("\033[H\033[2J");
    }

    /**
     * Save all PacketData in List of SensorData
     * */
    private void saveSensorData(InetAddress address, int port, Product product, int length){
        sensorDatas.add(new SensorData(product,port,address,length));

        if(isHere(port,address,product.getNameOfProduct())){
            update(port,address,product);
        }else{
            actualSensorDatas.add(new SensorData(product,port,address,length));
        }
    }


    /**
     * Chack SensorData in actualSensorData List.
     * @param portNummber
     * @param address
     * @param nameOfProduct
     * @return
     */
    private static boolean isHere(int portNummber,InetAddress address, String nameOfProduct){

        for(SensorData sensorData : actualSensorDatas){
            if((sensorData.getPortNummber() == portNummber)
                    || (sensorData.getAddress() == address)
                    || (sensorData.getProduct().getNameOfProduct() == nameOfProduct) ){
                return true;
            }
        }
        return false;
    }

    /**
     *  Update actualSensorData List with actual valueOfProduct.
     * @param portNummber
     * @param address
     * @param product
     */
    private static void update(int portNummber, InetAddress address, Product product){

        for(SensorData sensorData : actualSensorDatas){
            if((sensorData.getPortNummber() == portNummber)
                    || (sensorData.getAddress() == address)
                    || (sensorData.getProduct().getNameOfProduct() == product.getNameOfProduct()) ){
                sensorData.setProduct(product) ;
            }
        }
    }


}
