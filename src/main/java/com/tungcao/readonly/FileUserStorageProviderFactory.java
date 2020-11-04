package com.tungcao.readonly;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUserStorageProviderFactory implements UserStorageProviderFactory<FileUserStorageProvider> {

    private static final Logger logger = Logger.getLogger(FileUserStorageProviderFactory.class);

    public static final String PROVIDER_NAME = "readonly";

    protected Properties properties = new Properties();

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }


    @Override
    public void init(Config.Scope config) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("/users.properties");

        if (is == null) {
            logger.warn("Could not find users.properties in classpath");
        } else {
            try {
                properties.load(is);
            } catch (IOException ex) {
                logger.error("Failed to load users.properties file", ex);
            }
        }
    }

    @Override
    public FileUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new FileUserStorageProvider(session, model, properties);
    }

}
