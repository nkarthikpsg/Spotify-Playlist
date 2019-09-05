package com.nk.sp.dto;

import java.util.ArrayList;
import java.util.List;

public class Tracks
{
    private List<TrackInfo> trackInfoList = null;

    public List<TrackInfo> getTrackInfoList()
    {
        return trackInfoList;
    }

    public void setTrackInfoList(List<TrackInfo> trackInfoList)
    {
        this.trackInfoList = trackInfoList;
    }

    public void addTrackInfo(TrackInfo trackInfo)
    {
        if (this.trackInfoList == null)
            this.trackInfoList = new ArrayList<>();
        this.trackInfoList.add(trackInfo);
    }
}

