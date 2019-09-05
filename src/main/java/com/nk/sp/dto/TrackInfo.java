package com.nk.sp.dto;

public class TrackInfo
{
    private String trackName;

    private String albumName;

    public String getTrackName()
    {
        return trackName;
    }

    public String getTrackNameHex()
    {
        return trackName.replaceAll(" ", "+");
    }

    public void setTrackName(String trackName)
    {
        this.trackName = trackName;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public String getAlbumNameHex()
    {
        return albumName.replaceAll(" ", "+");
    }

    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof TrackInfo))
            return false;

        TrackInfo trackInfo = (TrackInfo) o;

        if (!getTrackName().equals(trackInfo.getTrackName()))
            return false;
        return getAlbumName().equals(trackInfo.getAlbumName());
    }

    @Override
    public int hashCode()
    {
        int result = getTrackName().hashCode();
        result = 31 * result + getAlbumName().hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "TrackInfo{" + "trackName='" + trackName + '\'' + ", albumName='" + albumName + '\'' + '}';
    }
}
