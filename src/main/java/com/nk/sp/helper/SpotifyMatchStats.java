package com.nk.sp.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nk.sp.dto.TrackInfo;

public class SpotifyMatchStats
{
    private List<TrackInfo> notFoundTracks = null;

    private Map<TrackInfo, List<TrackInfo>> multipleTracks = null;

    public List<TrackInfo> getNotFoundTracks()
    {
        return notFoundTracks;
    }

    public void setNotFoundTracks(List<TrackInfo> notFoundTracks)
    {
        this.notFoundTracks = notFoundTracks;
    }

    public void addNotFoundTracks(TrackInfo notFoundTrack)
    {
        if(this.notFoundTracks == null)
            this.notFoundTracks = new ArrayList<>();

        this.notFoundTracks.add(notFoundTrack);
    }

    public Map<TrackInfo, List<TrackInfo>> getMultipleTracks()
    {
        return multipleTracks;
    }

    public void setMultipleTracks(Map<TrackInfo, List<TrackInfo>> multipleTracks)
    {
        this.multipleTracks = multipleTracks;
    }

    public void addMultipleTracks(TrackInfo originTrack, TrackInfo foundTrack)
    {
        List<TrackInfo> trackInfos = multipleTracks.get(originTrack);
        if (trackInfos == null)
        {
            trackInfos = new ArrayList<>();
            multipleTracks.put(originTrack, trackInfos);
        }

        trackInfos.add(foundTrack);
    }

    @Override
    public String toString()
    {
        return "SpotifyMatchStats{" + "notFoundTracks=" + notFoundTracks + ", multipleTracks=" + multipleTracks + '}';
    }
}
