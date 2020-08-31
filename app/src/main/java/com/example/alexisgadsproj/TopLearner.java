package com.example.alexisgadsproj;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class TopLearner implements Parcelable {
    public String name;
    public  Integer hours;
    public String country;
    public String badgeUrl;

    public TopLearner(String name, Integer hours, String country, String badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    protected TopLearner(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            hours = null;
        } else {
            hours = in.readInt();
        }
        country = in.readString();
        badgeUrl = in.readString();
    }

    public static final Creator<TopLearner> CREATOR = new Creator<TopLearner>() {
        @Override
        public TopLearner createFromParcel(Parcel in) {
            return new TopLearner(in);
        }

        @Override
        public TopLearner[] newArray(int size) {
            return new TopLearner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (hours == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(hours);
        }
        dest.writeString(country);
        dest.writeString(badgeUrl);
    }

    @BindingAdapter({"android:badgeUrl"})
    public static void loadImage (ImageView view, String badgeUrl){
        Picasso.with(view.getContext())
                .load(badgeUrl)
                .into(view);
    }
}
