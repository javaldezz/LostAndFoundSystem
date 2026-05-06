package app.components;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
//import java.util.UUID;
import org.springframework.stereotype.Component;

import app.dto.TwilioReply;
import app.dto.TwilioRequests;

import java.util.Base64;


import retrofit2.Call;
import retrofit2.Response;

@Component
public class TwilioComponent {

	// Credentials from application.properties
    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;
    
    @Value("${twilio.msg_sid}")
    private String msgSid;

    private final TwilioRequests twilioApi;

    public TwilioComponent(TwilioRequests twilioApi) {
        this.twilioApi = twilioApi;
    }

    // Body is the Message and To is the phoneNumber
    public TwilioReply sendSMS(String to, String body) throws IOException {
        
    	// Create auth header
        String creds = accountSid + ":" + authToken;
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(creds.getBytes());

        // Make call
        Call<TwilioReply> call = twilioApi.sendSMS(accountSid, to, msgSid, body, authHeader);
        Response<TwilioReply> resp = call.execute();

        TwilioReply reply = resp.body();
        
        
        // Error handling 
        if (!resp.isSuccessful()) {
            String errorBody = resp.errorBody() != null ? resp.errorBody().string() : "No error body";
            throw new IOException("Failed to send SMS. Status: " + resp.code() + ", Body: " + errorBody);
        }
        
        System.out.println("--- TWILIO API ---");
        System.out.println("Status: " + reply.getStatus()); 
        System.out.println("SID: " + reply.getSid()); 
        
        return reply;
    }
}
