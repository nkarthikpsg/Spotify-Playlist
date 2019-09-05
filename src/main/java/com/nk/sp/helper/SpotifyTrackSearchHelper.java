package com.nk.sp.helper;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.sp.dto.TrackInfo;
import com.nk.sp.dto.Tracks;

public class SpotifyTrackSearchHelper
{
    private static ObjectMapper mapper = new ObjectMapper();

    public static SpotifyTrackSearchHelper instance()
    {
        return new SpotifyTrackSearchHelper();
    }

    public List<String> getSpotifyTracks(Tracks tracks, SpotifyMatchStats stats, String refreshToken)
    {
        List<String> spotifyTracks = new ArrayList<>();
        tracks.getTrackInfoList().forEach(track -> {
            Map<String, Object> stringObjectMap = searchTrack(track, refreshToken);
            String spotifyURI = getSpotifyURI(stringObjectMap, track, stats);
            if (spotifyURI != null)
            {
                spotifyTracks.add("\"" + spotifyURI + "\"");
                System.out.println("Track:" + spotifyURI);
            }
        });

        return spotifyTracks;
    }

    private String getSpotifyURI(Map<String, Object> stringObjectMap, TrackInfo track, SpotifyMatchStats stats)
    {
        String spotifyUri = null;
        Map<String, Object> tracks = (Map<String, Object>) stringObjectMap.get("tracks");
        Integer total = (Integer) tracks.get("total");
        if (total >= 1)
        {
            List<Map<String, Object>> items = (List<Map<String, Object>>) tracks.get("items");

            if (total > 1)
            {
                addMultiItemStats(items, track, stats);
            }

            spotifyUri = (String) items.get(0).get("uri");
        }
        else
        {
            stats.addNotFoundTracks(track);
        }

        return spotifyUri;
    }

    private void addMultiItemStats(List<Map<String, Object>> items, TrackInfo track, SpotifyMatchStats stats)
    {
        items.forEach(item -> {
            TrackInfo foundTrack = new TrackInfo();
            foundTrack.setAlbumName(track.getAlbumName());
            foundTrack.setTrackName((String) item.get("name"));
        });
    }

    public Map<String, Object> searchTrack(TrackInfo trackInfo, String refreshToken)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        try
        {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost("api.spotify.com").setPath("/v1/search")
                    .setParameter("q", getSearchString(trackInfo)).setParameter("type", "track");
            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);

                        SpotifyHelper.instance()
                                .addBearerAuthorizationHeader(httpGet, refreshToken);
//                        SpotifyHelper.instance()
//                                .addBearerAuthorizationHeader(httpGet, SpotifyRefreshTokenHelper.instance().getRefreshToken());
//            SpotifyHelper.instance().addBearerAuthorizationHeader(httpGet,
//                    "BQBI5M-pA6hInp-QXifQKabmTYcQ8pu0j8t7lwLJN-p_6emju6ZxunEixEyp4eKny1TNQ9QLbOLxMqPXuSeO738v4xU76rxXogxGUoU4AXrpiet218vpF-j9NRXFOKYAVN1ePHdD0VuQGAWoTc9XR08qwL75imEXh3PBO0QeYjqeLSMpYE-BxbNtPS2gn1JE760CSgEAP7Dfd-szvt3RVQNUfg");

            CloseableHttpResponse response = client.execute(httpGet);

            if (200 != response.getStatusLine().getStatusCode())
            {
                throw new RuntimeException("Refresh Token response not success");
            }

            String result = SpotifyHelper.instance().getResponseString(response);

            Map<String, Object> map = mapper.readValue(result.toString(), Map.class);

            return map;
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

    private String getSearchString(TrackInfo trackInfo)
    {
        StringBuffer query = new StringBuffer();

        query.append("album:");
        query.append(trackInfo.getAlbumName());
        query.append(" ");
        query.append("track:");
        query.append(trackInfo.getTrackName());

        return query.toString();
    }
}
