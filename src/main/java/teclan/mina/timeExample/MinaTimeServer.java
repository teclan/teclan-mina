package teclan.mina.timeExample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer {
    private static final int PORT = 8082;

    public static void main(String[] args) {
        IoAcceptor acceptor = new NioSocketAcceptor();
        // server 和 client 的 FilterChain 要对应
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new TimeServerHandler());
        // The buffer size will be specified in order to tell the underlying
        // operating system how much room to allocate for incoming data
        acceptor.getSessionConfig().setReadBufferSize(2048);
        // the first parameter defines what actions to check for when
        // determining if a session is idle, the second parameter defines the
        // length of time in seconds that must occur before a session is deemed
        // to be idle.
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        try {
            acceptor.bind(new InetSocketAddress(PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
