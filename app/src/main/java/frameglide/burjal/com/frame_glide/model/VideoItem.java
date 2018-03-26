package frameglide.burjal.com.frame_glide.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by burjal on 2018/3/10.
 */

public class VideoItem implements Serializable, Parcelable {

    public long id;
    public String name;
    public String videoPath;
    public Uri videoUrl;
    public String mineType;
    public long size;
    public long duration;//ms
    public int width;
    public int height;
    public int rotation;

    public VideoItem() {
    }

    protected VideoItem(Parcel in) {
        id = in.readLong();
        name = in.readString();
        videoPath = in.readString();
        videoUrl = in.readParcelable(Uri.class.getClassLoader());
        mineType = in.readString();
        size = in.readLong();
        duration = in.readLong();
        width = in.readInt();
        height = in.readInt();
        rotation = in.readInt();
    }

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(videoPath);
        dest.writeParcelable(videoUrl, flags);
        dest.writeString(mineType);
        dest.writeLong(size);
        dest.writeLong(duration);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(rotation);
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", videoPath='" + videoPath + '\'' +
                ", videoUrl=" + videoUrl +
                ", mineType='" + mineType + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                '}';
    }
}
