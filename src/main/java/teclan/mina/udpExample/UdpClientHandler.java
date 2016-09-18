package teclan.mina.udpExample;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

import teclan.utils.GsonUtils;

public class UdpClientHandler extends IoHandlerAdapter {

    private static final String HOST = "localhost";
    private static final int    PORT = 8080;

    private IoSession   session;
    private IoConnector connector;

    public UdpClientHandler() {

        connector = new NioDatagramConnector();
        connector.setHandler(this);

        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));

        ConnectFuture connectFuture = connector
                .connect(new InetSocketAddress(HOST, PORT));

        connectFuture.awaitUninterruptibly();

        connectFuture.addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                if (future.isConnected()) {
                    session = future.getSession();
                }

                sendDate();

            }

        });

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        System.out.println("Receive :" + message);
    }

    public void messageSent(IoSession session) throws Exception {
        System.out.println("message sent ...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("session close ...");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("session create ...");
    }

    public void sessionIdle(IoSession session) throws Exception {
        System.out.println("session idle ...");
    }

    private void sendDate() {

        String json = GsonUtils.toJson(new Man(10086, "中国移动"));
        IoBuffer buffer;
        try {
            buffer = IoBuffer.allocate(2048);
            buffer.putObject(json);
            buffer.flip();
            session.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
