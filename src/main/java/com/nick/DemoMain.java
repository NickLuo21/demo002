package com.nick;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Nick on 2017/3/12.
 */
public class DemoMain {

    private static final Logger logger = LoggerFactory.getLogger(DemoMain.class);
    private static Properties properties = new Properties();

    static {
        try {
            URL resource = DemoMain.class.getResource("/conf.properties");
            if (resource != null) {
                properties.load(resource.openStream());
                logger.info("initialize properties from {}", resource);
            }
        } catch (IOException e) {
            throw new RuntimeException("initialize configuration failed", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = properties.getProperty("server.port");
        logger.info("server.port={}", webPort);
        tomcat.setPort(Integer.valueOf(webPort));

        //This is to compensate for the different paths that the appassembler-program and jsw daemon use
        String basedir = properties.getProperty("server.webPath");
        String webAppLocation = new File(basedir).getAbsolutePath();
        logger.info("configuring app with basedir {}", webAppLocation);
        String contextPath = properties.getProperty("server.contextPath");
        tomcat.addWebapp(contextPath, webAppLocation);

        tomcat.start();
        tomcat.getServer().await();
    }
}
