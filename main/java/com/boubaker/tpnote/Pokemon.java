package com.boubaker.tpnote;

import android.os.Parcel;
import android.os.Parcelable;

public class Pokemon implements Parcelable {
    private int id;
    private String name;
    private String type;
    private String imageUrl;
    private float strength;

    public Pokemon() {
        // Required empty constructor for JSON deserialization
    }

    public Pokemon(int id, String name, String type, String imageUrl, float strength) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.strength = strength;
    }

    // Parcelable implementation
    protected Pokemon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        imageUrl = in.readString();
        strength = in.readFloat();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(imageUrl);
        dest.writeFloat(strength);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}