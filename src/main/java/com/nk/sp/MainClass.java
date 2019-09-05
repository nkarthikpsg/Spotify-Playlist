package com.nk.sp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.nk.sp.dto.TrackInfo;
import com.nk.sp.dto.Tracks;
import com.nk.sp.helper.SpotifyMatchStats;
import com.nk.sp.helper.SpotifyRefreshTokenHelper;
import com.nk.sp.helper.SpotifyTrackSearchHelper;

public class MainClass
{
    public static void main(String args[])
    {
        SpotifyMatchStats stats = new SpotifyMatchStats();

        //        Tracks tracks = SaavnTrackHelper.instance().getTracks("https://www.saavn.com/s/featured/tamil/Weekly_Top_15");

        Tracks tracks = new Tracks();
        TrackInfo trackInfo = new TrackInfo();
        trackInfo.setAlbumName("Bigil");
        trackInfo.setTrackName("Singappenney");

        tracks.setTrackInfoList(Arrays.asList(trackInfo));

        String refreshToken = SpotifyRefreshTokenHelper.instance().getRefreshToken();
        List<String> spotifyTracks = SpotifyTrackSearchHelper.instance().getSpotifyTracks(tracks, stats, refreshToken);

        System.out.println(spotifyTracks);
        System.out.println(stats);
    }

    public static void main1(String args[])
    {
        Document doc = null;
        try
        {
            doc = Jsoup.connect("https://www.saavn.com/s/featured/tamil/Highway_Hits/foVAPRKAlmw").get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Elements contents = doc.select("div[class=details content-list");

        contents.forEach(con -> {
            System.out.println(con.select("p[class=song-name ellip]").first().textNodes().get(0).text());
            System.out.println(con.select("p[class=album-name ellip]").first().textNodes().get(0).text());
        });

        //        System.out.println(
        //                new LuceneLevenshteinDistance().getDistance("Thaana Serndha Kottam", "Thaana Serndha Koottam"));
        //        OutputStream outputStream = null;
        //        try
        //        {
        //            URL url = new URL("http://tamilfreemp3s.net/down.php?file=Tamil%202017%20Songs/Thaanaa%20Serndha%20Koottam/64/Sodakku.mp3");
        //
        //            URLConnection conn = url.openConnection();
        //            conn.addRequestProperty("User-Agent",
        //                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        //            InputStream is = conn.getInputStream();
        //
        //            outputStream = new FileOutputStream(new File("/A/temp/file.mp3"));
        //        byte[] buffer = new byte[4096];
        //        int len;
        //        while ((len = is.read(buffer)) > 0) {
        //            outputStream.write(buffer, 0, len);
        //        }
        //        }
        //        catch (Exception e)
        //        {
        //            e.printStackTrace();
        //        }
        //        finally
        //        {
        //            if(outputStream != null)
        //                try
        //                {
        //                    outputStream.close();
        //                }
        //                catch (IOException e)
        //                {
        //                    e.printStackTrace();
        //                }
        //        }
    }
}
