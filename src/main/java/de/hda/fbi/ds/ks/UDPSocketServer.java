package de.hda.fbi.ds.ks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;

import de.hda.fbi.ds.myShopService.ShopService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


//import myShopService.*;
/**
 * Created by zigfrid on 13.11.17.
 */
public class UDPSocketServer {


    /** The UDP port the server listens to. */
    private static int SERVER_PORT = 8181;
    /** The UDP socket used to receive data. */
    private DatagramSocket udpSocket;
    /** States the server running. */
    private boolean running = true;
    /** A buffer array to store the datagram information. */
    private byte[] buf = new byte[1024];
    private byte[] data = new byte[1024];
    /** SensorData List (all Data) */
    private List<SensorData> sensorDatas = new ArrayList<SensorData>();
    /** SensorData List (all Data) */
    private static List<SensorData> actualSensorDatas = new ArrayList<SensorData>();
    /** counter for received packets */
    private int receivedPacketsCounter = 0;
    /** TCP Socket for HTTP */
    private ServerSocket serverSocket;
    /** The UDP port the server listens to. */
    private static int PORT_WEB = 8282;
    /** Socket for Web */
    private Socket socket;
    /** The port the client connects to. */
    public static final int PORT_THRIFT = 9090;
    /** The host the client connects to. */
    public static final String HOST_THRIFT = "localhost";

    /**
     * Default constructor that creates, i.e., opens
     * the socket.
     *
     * @throws IOException In case the socket cannot be created.
     */
    public UDPSocketServer() throws IOException {
        udpSocket = new DatagramSocket( SERVER_PORT );
        serverSocket = new ServerSocket( PORT_WEB);
        System.out.println("Started the UDP socket server at port " + SERVER_PORT);
        System.out.println("Started the WebServer at port " + PORT_WEB);

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

                //connection accept TCP
                socket = serverSocket.accept();

                //Print some packet data.
                savePaketdata(udpPacket);

                //print by server console
                printActualSensorData();

                //start Web Server
                showWeb();

                //test thrift
                makeOrder();

            } catch (IOException e) {
                System.out.println("Could not receive datagram.\n" + e.getLocalizedMessage());
            }
        }
    }

    // Wahrscheinlich muss Rückgabewert als Product sein
    private void makeOrder(){
        try (TTransport transport = new TSocket(HOST_THRIFT, PORT_THRIFT)){
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            //ShopSecvice.Client client = new ShopSecvice.Client(protocol);
            ShopService.Client client = new ShopService.Client(protocol);
            System.out.println("add result:" + client.hello("TEST"));
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    /**
     * generete Page for WebClient
     *
     * @throws IOException
     */
    private void showWeb() throws IOException{

        InputStream inputStream;
        BufferedReader bufferedReader;
        PrintWriter out;
        String request;
        String response;
        String[] requestParam;
        String path;
        String message = "Don't have a Products";
        String historyURL = "/history";
        String listURL = "/list";
        String testBadURL = "/testError";
        String pathForRefresh = "";
        String topic ="";
        //For test Status
        String httpStatus = "";

        try{
            inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            request = bufferedReader.readLine();
            if(!request.isEmpty()){
                requestParam = request.split(" ");
                path = requestParam[1];
                System.out.println(path);
                if(path.equals(historyURL)){
                    message = makeOneStringHistory();
                    httpStatus = "HTTP/1.1 200";
                    topic = "History of products : ";
                    pathForRefresh = historyURL;
                }
                if(path.equals(listURL)){
                    message = makeOneStringList();
                    httpStatus = "HTTP/1.1 200";
                    topic = "List of products : ";
                    pathForRefresh = listURL;
                    /** compare Data from actualSensorData with response Data */
                    //testShowList(message);
                }
                /** if path not equals /list or /history will be BAD REQUEST with status 400 */
                if(!path.equals(listURL) && !path.equals(historyURL)){
                    message = "<h1 style='color:red'> BAD REQUEST, ALLOWED ONLY HTTP GET /list or /history REQUESTS </h1>";
                    httpStatus = "HTTP/1.1 400";
                    topic = "<h1 style='color:red'> ERROR </h1>";
                    pathForRefresh = testBadURL;
                }

            }

            /** Color test */
            //testColor(message,httpStatus);

            out = new PrintWriter(socket.getOutputStream());
            out.println(httpStatus);
            out.println("Content-type: text/html");
            out.println("Server-name: myServer");
            response = "<html>" + "<head>"
                    + "<meta http-equiv='refresh' content='2'; url=localhost:8282" + pathForRefresh + ">"
                    + "<meta charset='utf-8'>" // Umlauts
                    + "<link rel=\"icon\" href=\"data:;base64,iVBORw0KGgo=\">" // How to prevent favicon.ico requests?
                    + "<title>My Web Server</title></head>"
                    + "<h1>Your request: " + request + "</h3>"
                    + "<table width=\"200\">"
                    + "<td><h3><a href=\"/list \">list</a></h3></td>"
                    + "<td><h3><a href=\"/history \">history</a></h3></td>"
                    + "<td><h3><a href=\"/testError \">testError</a></h3></td>"
                    + "</table>"
                    + "<h1>" + topic + "</h1>"
                    + "<table width=\"200\">"
                    + "<h3>" + message + "</h3>"
                    + "</table>"
                    + "</html>";
            out.println("Content-length: " + response.length());
            out.println("");
            out.println(response);
            out.flush();
            //out.close();
            //bufferedReader.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed respond to client request: " + e.getMessage());
        }

    }

    /**
     * compare Data from actualSensorData with response Data
     * */
    void testShowList(String message){

        if(message.length() != 0){
            String[] chekingString = message.split(";");
            for (int i = 0 ; i < actualSensorDatas.size();i++){
                String[] chekingInString = chekingString[i].split(" ");
                if(actualSensorDatas.get(i).getProduct().getNameOfProduct().equals(chekingInString[3])
                        && actualSensorDatas.get(i).getProduct().getValueOfProduct() == Integer.parseInt(chekingInString[chekingInString.length-1])){
                    System.out.println("Product name from actualData: >"
                            + actualSensorDatas.get(i).getProduct().getNameOfProduct()
                            + "< is same with Product name from request messege >" + chekingInString[3]
                            + "< and Product value from actualData: >" + actualSensorDatas.get(i).getProduct().getValueOfProduct()
                            + "< is same with Product value from request message >" + chekingInString[chekingInString.length-1] + "<");
                }else{
                    System.out.println("Product name from actualData: >"
                            + actualSensorDatas.get(i).getProduct().getNameOfProduct()
                            + "< is NOT same with Product name from request messege >" + chekingInString[3]
                            + "< and Product value from actualData: >" + actualSensorDatas.get(i).getProduct().getValueOfProduct()
                            + "< is NOT same with Product value from request message >" + chekingInString[chekingInString.length-1] + "<");
                }
            }
        }
    }

    /**
     *  Color test
     *  Search style color red/blue and value of Product and then compare >0 or ==0
     * */
    void testColor(String message,String httpStatus){
        String styleColorRed = "style='color:red'";
        String styleColorBlue = "style='color:blue'";

        if(httpStatus.equals("HTTP/1.1 200") && message.length() != 0){
            String[] chekingString = message.split(";");
            if(chekingString.length > 0){
                for(int i = 0 ; i < chekingString.length - 1; i++ ){
                    System.out.println(chekingString[i]);
                    String[] chekingInString = chekingString[i].split(" ");
                    /** Search style color red/blue and value of Product and then compare >0 or ==0 */
                    if(chekingString[i].contains(styleColorBlue) && Integer.parseInt(chekingInString[chekingInString.length-1]) > 0){
                        System.out.println("By value "  + chekingInString[chekingInString.length-1] + " is " + styleColorBlue );
                    }
                    if(chekingString[i].contains(styleColorRed) && Integer.parseInt(chekingInString[chekingInString.length-1]) == 0){
                        System.out.println("By value "  + chekingInString[chekingInString.length-1] + " is " + styleColorRed );
                    }
                }
            }
        }
    }

    /**
     * generate String for webServer
     * for path /list
     * */
    private String makeOneStringList(){
        String result = "";

        for(int i = 0 ; i < actualSensorDatas.size() ; i++ ){
            if(actualSensorDatas.get(i).getProduct().getValueOfProduct() == 0){
                result = result + "<tr><td><h3 style='color:red' > " + actualSensorDatas.get(i).getProduct().getNameOfProduct() + " </h3></td><td><h3 style='color:red'> " + actualSensorDatas.get(i).getProduct().getValueOfProduct() + " ;</h3></td></tr>";
            }else{
            result = result + "<tr><td><h3 style='color:blue' > " + actualSensorDatas.get(i).getProduct().getNameOfProduct() + " </h3></td><td><h3 style='color:blue' > " + actualSensorDatas.get(i).getProduct().getValueOfProduct() + " ;</h3></td></tr>";
            }
        }
        return result;
    }
    /**
     * generate String for webServer
     * for path /history
     * */
    private String makeOneStringHistory(){
        String result = "";

        for(int i = 0 ; i < sensorDatas.size() ; i++ ){
            if(sensorDatas.get(i).getProduct().getValueOfProduct() == 0){
                result = result + "<tr><td><h3 style='color:red' > " +sensorDatas.get(i).getProduct().getNameOfProduct() + " </h3></td><td><h3 style='color:red' > " + sensorDatas.get(i).getProduct().getValueOfProduct()  + " ;</h3></td></tr>";

            }else{
            result = result + " <tr><td><h3 style='color:blue' > " +sensorDatas.get(i).getProduct().getNameOfProduct() + " </h3></td><td><h3 style='color:blue' > " + sensorDatas.get(i).getProduct().getValueOfProduct()  + " ;</h3></td></tr>";
            }
        }
        return result;
    }

    private void sendAnswer(SensorData sensorData) throws IOException{

        /** The IP address and port from client . */
        InetAddress address = sensorData.getAddress();
        int port = sensorData.getPortNummber();
        //increment for check
        String message = " " + receivedPacketsCounter;
        data = message.getBytes();
        DatagramPacket sendPacket  = new DatagramPacket(data, data.length, address, port);
        udpSocket.send(sendPacket);
        ++receivedPacketsCounter;

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
            sendAnswer(new SensorData(product,port,address,length));
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

        System.out.println("*----------------------------");
        for (SensorData sensorData : actualSensorDatas) {
            System.out.println("Received a packet: IP:Port: " + sensorData.printSensorData());
        }
            //Thread.sleep(10000);
            //System.out.print("\033[H\033[2J");
        System.out.println("----------------------------*");

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
