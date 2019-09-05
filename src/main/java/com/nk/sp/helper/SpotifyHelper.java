package com.nk.sp.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;

public class SpotifyHelper
{
    public static SpotifyHelper instance()
    {
        return new SpotifyHelper();
    }

    public void addBasicAuthorizationHeader(HttpEntityEnclosingRequestBase http)
    {
        http.addHeader("Authorization",
                "Basic MWUzMTAzYjAyNzMxNDAxMzg3YTU2NjNiMGJjYzg5NzI6ODg2MmZjMmEyYTlhNGFiOTk3Y2VhY2M0NmY2OGFjYzI=");
    }

    public void addBearerAuthorizationHeader(HttpRequestBase http, String accessToken)
    {
        http.addHeader("Authorization", "Bearer " + accessToken);
    }

    public String getResponseString(CloseableHttpResponse response)
    {
        StringBuffer result = new StringBuffer();
        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null)
            {
                result.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result.toString();
    }
}
