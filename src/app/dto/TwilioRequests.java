package app.dto;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;


public interface TwilioRequests {	
	
    @FormUrlEncoded
    @POST("Accounts/{acctId}/Messages.json")
    public Call<TwilioReply> sendSMS(
            @Path("acctId") String accountSid,
            @Field("To") String to,
            @Field("MessagingServiceSid") String msgSid,
            @Field("Body") String body,
            @Header("Authorization") String authHeader
    );
}
