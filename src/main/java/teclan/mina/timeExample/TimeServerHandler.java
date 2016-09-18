package teclan.mina.timeExample;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {

        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        String str = message.toString();
        System.out.println("Message receive : " + str);

        if ("关闭连接".equals(str)) {
            System.out.println("即将关闭连接...");
            // TO DO
        } else {
            Date date = new Date();
            session.write(date.toString());
        }

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        System.out.println("IDLE " + session.getIdleCount(status));
    }
}
