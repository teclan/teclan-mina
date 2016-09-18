package teclan.mina.timeExample;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter {

    @Override
    public void sessionOpened(IoSession session) {
        for (int i = 0; i < 10; i++) {
            session.write(i);

            // try {
            // Thread.sleep(1000);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
        }

        session.write("关闭连接");
    }

    @Override
    public void messageReceived(IoSession session, Object message) {

        System.out.println((String) message);
    }

    @Override
    public void sessionClosed(IoSession session) {
        // TO DO
        session.closeNow();
    }

}
