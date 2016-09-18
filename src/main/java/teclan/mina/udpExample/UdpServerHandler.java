package teclan.mina.udpExample;

import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import teclan.utils.GsonUtils;

public class UdpServerHandler extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.closeNow();
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        if (message instanceof IoBuffer) {
            IoBuffer buffer = (IoBuffer) message;

            SocketAddress remoteAddress = session.getRemoteAddress();

            Man man = GsonUtils.fromJson(buffer.getObject().toString(),
                    Man.class);

            System.out.println("From : " + remoteAddress.toString() + " : "
                    + man.toString());
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("session close :" + session.getRemoteAddress());
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("session create :" + session.getRemoteAddress());

    };

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("session open :" + session.getRemoteAddress());
    }

}
