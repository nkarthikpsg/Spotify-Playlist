package com.nk.sp.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SpotifyRefreshTokenHelper
{
    private static ObjectMapper mapper = new ObjectMapper();

    public static SpotifyRefreshTokenHelper instance()
    {
        return new SpotifyRefreshTokenHelper();
    }

    public String getRefreshToken()
    {
        CloseableHttpClient client = HttpClients.createDefault();
        try
        {
            HttpPost httpPost = new HttpPost("https://accounts.spotify.com/api/token");

            SpotifyHelper.instance().addBasicAuthorizationHeader(httpPost);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("refresh_token",
                    "AQDOJZSY48CFMo3t0O9ccbgd3aW8r_iwpcxlNjYms7J65EIWyLO_4dM_TPhmJGC5TI8FRxx_DGZgSA5WfZ21R5qBBKEcAnvUXdZb1sx6uIBKFplDCB-ny8gySXW1FbbkTww"));

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            CloseableHttpResponse response = client.execute(httpPost);

            if (200 != response.getStatusLine().getStatusCode())
            {
                throw new RuntimeException("Refresh Token response not success");
            }

            String result = SpotifyHelper.instance().getResponseString(response);

            Map<String, String> map = mapper.readValue(result.toString(), Map.class);

            String access_token = map.get("access_token");
            System.out.println("ACCESS TOKEN **** : ***** : " +  access_token);
            return access_token;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (client != null)
            {
                try
                {
                    client.close();
                }
                catch (IOException e)
                {
                }
            }
        }

        return null;
    }
}
