package teclan.mina.timeExample;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TimeClient {

    private static final String HOST            = "localhost";// 127.0.0.1
    private static final int    PORT            = 8082;
    private static final long   CONNECT_TIMEOUT = 30 * 1000L; // 30 seconds

    public static void main(String[] args) {

        NioSocketConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.setHandler(new TimeClientHandler());

        IoSession session;

        ConnectFuture future = connector
                .connect(new InetSocketAddress(HOST, PORT));

        future.awaitUninterruptibly();

        session = future.getSession();

        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();

    }

}
