package com.angeljedi.myreps;

import android.os.Parcel;
import android.os.Parcelable;

public class Rep implements Parcelable{

    private String name;
    private String party;
    private String state;
    private String district;
    private String phone;
    private String office;
    private String website;

    public Rep() {
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(party);
        dest.writeString(state);
        dest.writeString(district);
        dest.writeString(phone);
        dest.writeString(office);
        dest.writeString(website);
    }

    public static final Parcelable.Creator<Rep> CREATOR = new Parcelable.Creator<Rep> () {
        public Rep createFromParcel(Parcel parcel) {
            return new Rep(parcel);
        }
        public Rep[] newArray(int size) {
            return new Rep[size];
        }
    };

    public Rep(Parcel parcel) {
        name = parcel.readString();
        party = parcel.readString();
        state = parcel.readString();
        district = parcel.readString();
        phone = parcel.readString();
        office = parcel.readString();
        website = parcel.readString();
    }
}
