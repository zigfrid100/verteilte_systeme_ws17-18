package de.hda.fbi.ds.ks;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;

/**
 * Created by zigfrid on 09.12.17.
 */
public class ShopServer {
/*
    public static void StartsimpleServer(ShopService.Processor<ShopServiceHandler> processor) {

        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(
                    new Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        StartsimpleServer(new ShopService.Processor<ShopServiceHandler>(new ShopServiceHandler()));
    }
*/
}
