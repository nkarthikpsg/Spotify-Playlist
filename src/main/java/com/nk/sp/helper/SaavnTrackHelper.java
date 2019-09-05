package com.nk.sp.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.nk.sp.dto.TrackInfo;
import com.nk.sp.dto.Tracks;

public class SaavnTrackHelper
{
    public static SaavnTrackHelper instance()
    {
        return new SaavnTrackHelper();
    }

    public Tracks getTracks(String saavnPlayList)
    {
        Document document = getDocument(saavnPlayList);
        Tracks tracks = new Tracks();
        Elements contents = document.select("div[class=details content-list]");
        contents.forEach(con -> {
            TrackInfo trackInfo = new TrackInfo();
            trackInfo.setTrackName(con.select("p[class=song-name ellip]").first().textNodes().get(0).text());
            trackInfo.setAlbumName(con.select("p[class=album-name ellip]").first().textNodes().get(0).text());
            tracks.addTrackInfo(trackInfo);
        });

        return tracks;
    }

    private Document getDocument(String saavnPlayList)
    {
        Document doc;
        try
        {
            doc = Jsoup.connect(saavnPlayList).get();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return doc;
    }
}
