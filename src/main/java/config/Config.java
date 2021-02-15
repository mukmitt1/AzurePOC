package config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

public class Config {

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
        secretClient.setSecret(new KeyVaultSecret(key, value));
    }

    public String getSecret(String key) {
        KeyVaultSecret retrievedSecret = secretClient.getSecret(key);

        return retrievedSecret.getValue();
    }
}

