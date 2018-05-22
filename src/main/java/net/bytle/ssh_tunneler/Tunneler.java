package net.bytle.ssh_tunneler;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.logging.Logger;

public class Tunneler
{
    private Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
    private Session session;
    private Properties properties;

    public static void main(String[] args) throws Exception
    {
        new Tunneler();
    }

    @SuppressWarnings("EmptyCatchBlock")
    public Tunneler() throws Exception
    {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());



        properties = new Properties()
        {{
                load(getClass().getResourceAsStream("/tunneler.properties"));
            }};

        long sleep = Long.valueOf(properties.getProperty("sleep", "30000"));

        while (true)
        {
            if (!isConnected())
            {
                logger.info("connection is down");
                try
                {
                    logger.info("connecting to " + properties.getProperty("host"));
                    connect();
                } catch (Exception ex)
                {
                    logger.severe(ex.getMessage());
                    Thread.sleep(sleep);
                }
            } else
            {
                session.sendKeepAliveMsg();
                Thread.sleep(sleep);
            }
        }
    }

    private void setup() throws JSchException, IOException, GeneralSecurityException
    {
        session = new JSch().getSession(
                properties.getProperty("username"),
                properties.getProperty("host"),
                Integer.valueOf(properties.getProperty("port"))
        );

        session.setConfig(properties);

        session.setPassword(properties.getProperty("password"));
    }

    private void connect() throws JSchException, IOException, GeneralSecurityException
    {
        setup();

        disconnect();

        session.connect();

        String[] forwardInfo;

        if ((forwardInfo = getForwardInfo("rforwarding", ":")) != null)
            session.setPortForwardingR(
                    Integer.valueOf(forwardInfo[0]),
                    forwardInfo[1],
                    Integer.valueOf(forwardInfo[2])
            );

        if ((forwardInfo = getForwardInfo("lforwarding", ":")) != null)
            session.setPortForwardingL(
                    Integer.valueOf(forwardInfo[0]),
                    forwardInfo[1],
                    Integer.valueOf(forwardInfo[2])
            );

        logger.info("connected");

    }

    private boolean isConnected()
    {
        return session != null && session.isConnected();
    }

    private void disconnect()
    {
        if (isConnected())
        {
            logger.info("disconnecting");
            session.disconnect();
        }
    }

    private String[] getForwardInfo(String key, String split)
    {
        String value = properties.getProperty(key, "").trim();
        if (!"".equals(value))
            return value.split(split);
        else
            return null;
    }

    class ShutdownHook extends Thread
    {
        public void run()
        {
            disconnect();
        }
    }
}
