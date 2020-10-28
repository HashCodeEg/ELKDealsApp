package com.elkdeals.mobile.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQAnswer implements Parcelable {
    String answer;

    public FAQAnswer(String answer) {
        this.answer = answer;
    }

    protected FAQAnswer(Parcel in) {
        answer = in.readString();
    }

    public static final Creator<FAQAnswer> CREATOR = new Creator<FAQAnswer>() {
        @Override
        public FAQAnswer createFromParcel(Parcel in) {
            return new FAQAnswer(in);
        }

        @Override
        public FAQAnswer[] newArray(int size) {
            return new FAQAnswer[size];
        }
    };

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
    }
}
