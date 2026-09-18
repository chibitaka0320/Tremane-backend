package com.chibitaka.tremane_backend.common.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    public GoogleCredentials firebaseCredentials() throws IOException {
        try (FileInputStream serviceAccount = new FileInputStream("/etc/secrets/serviceAccountKey.json")) {
            return GoogleCredentials.fromStream(serviceAccount);
        }
    }

    @Bean
    public FirebaseApp firebaseApp(GoogleCredentials firebaseCredentials,
            @Value("${firebase.storage-bucket}") String storageBucket) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(firebaseCredentials)
                .setStorageBucket(storageBucket)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }
}
