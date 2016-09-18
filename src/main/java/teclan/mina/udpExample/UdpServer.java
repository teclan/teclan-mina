package teclan.mina.udpExample;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class UdpServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();

        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

        acceptor.setHandler(new UdpServerHandler());

        chain.addLast("logger", new LoggingFilter());

        DatagramSessionConfig dcfg = acceptor.getSessionConfig();
        dcfg.setReuseAddress(true);

        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("UDPServer listening on port " + PORT);

    }

}
