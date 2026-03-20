package com.br.hubsellerappbackend.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MensagemService {
	
	@Value("${phone.number.whatsapp}")
    private String phoneNumberWhatsApp;
	
	@Value("${api.key.whatsapp}")
    private String apiKeyWhatsApp;
	
	public void enviarMensagemWhatsApp(String mesagem) {
		try {
            
            // Codifica a mensagem para URL
            String encodedMessage = URLEncoder.encode(mesagem, "UTF-8");
            
            
            // Monta a URL da API CallMeBot
            String apiUrl = "https://api.callmebot.com/whatsapp.php?phone=" + phoneNumberWhatsApp + "&text=" + encodedMessage + "&apikey=" + apiKeyWhatsApp;
            
            // Cria conexão HTTP
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Lê a resposta da API
            int responseCode = conn.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                System.out.println("Resposta da API: " + response);
            } else {
                System.out.println("Erro ao enviar a mensagem.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
