package config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MessagingController;

public class Config {

    private static final Logger LOGGER= LoggerFactory.getLogger(Config.class);

    String keyVaultName = "umbcskeyvault";
    String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";
    SecretClient secretClient;

    public Config() {
        secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

    public void setSecretKeyValue(String key, String value) {
        LOGGER.info("Setting Secret! key: " + key + " value: " + value);
        secretClient.setSecret(new KeyVaultSecret(key, value));
    }

    public String getSecret(String key) {
        KeyVaultSecret retrievedSecret = secretClient.getSecret(key);

        return retrievedSecret.getValue();
    }
}

