package ua.goit.projectmanagementsystem.config;

import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private Properties properties = null;

    public PropertiesUtil(ServletContext context) {
        loadWithContext(context);
    }

    public String getHostname() {
        return properties.getProperty("database.hostname");
    }

    public String getSchema() {
        return properties.getProperty("database.schema");
    }

    public Integer getPort() {
        return Integer.parseInt(properties.getProperty("database.port"));
    }

    public String getUser() {
        return properties.getProperty("database.user");
    }

    public String getPassword() {
        return properties.getProperty("database.password");
    }

    public String getJdbcDriver() {return  properties.getProperty("jdbc.driver");}

    private void load() {
        this.properties = new Properties();
        getClass().getClassLoader();
        Thread.currentThread().getContextClassLoader();
        try(InputStream is = ClassLoader
                .getSystemResourceAsStream("application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWithContext(ServletContext context) {
        this.properties = new Properties();
        try(InputStream is = context.getResourceAsStream("/WEB-INF/resources/application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
