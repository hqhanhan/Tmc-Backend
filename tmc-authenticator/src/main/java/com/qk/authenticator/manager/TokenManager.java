package com.qk.authenticator.manager;

import java.io.IOException;
import javax.json.JsonObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Description：
 * Created by hqhan on 2017/5/25 0025.
 */

public interface TokenManager {

    <T> String issueToken(T payload);

    <T> String issueToken(T payload, long validPeriodMillis);

    JsonObject retrieveToken(String token);

    String retrieveTokenAndGetUserId(String authorizationHeader);

    String decryptTokenAndGetTokenTime( String token,int n )  throws JsonParseException, JsonMappingException, IOException;
}
