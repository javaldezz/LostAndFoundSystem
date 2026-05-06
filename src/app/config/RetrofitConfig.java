package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.dto.TwilioRequests; 
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/") 
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
    @Bean
    public TwilioRequests twilioRequests(Retrofit retrofit) {
        return retrofit.create(TwilioRequests.class);
    }
}