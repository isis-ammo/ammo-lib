/* Copyright (c) 2010-2015 Vanderbilt University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.vu.isis.ammo.api.type;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * When the application wishes to get a broadcast intent
 * this parameter is used.
 *
 */
public class BroadIntent extends AmmoType {

    final private Intent intent;

    public BroadIntent(Intent val) {
        this.intent = val;
    }

    public String cv() {
        throw new UnsupportedOperationException();
        // return ""; // intent.cv();
    }

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<BroadIntent> CREATOR =
            new Parcelable.Creator<BroadIntent>() {

                @Override
                public BroadIntent createFromParcel(Parcel source) {
                    return new BroadIntent(source);
                }

                @Override
                public BroadIntent[] newArray(int size) {
                    return new BroadIntent[size];
                }
            };

    public static BroadIntent readFromParcel(Parcel source) {
        if (AmmoType.isNull(source))
            return null;
        return new BroadIntent(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall provider {}", this);
                dest.writeParcelable(this, flags);
    }

    public BroadIntent(Parcel in) {
        this.intent = in.readParcelable(null);
        plogger.trace("unmarshall provider {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************

    @Override
    public String toString() {
       return "";
           //     return this.uri.toString();
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************


    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BroadIntent))
            return false;
        final BroadIntent that = (BroadIntent) obj;
        return this.intent.equals(that.intent);
    }

    @Override
    public synchronized int hashCode() {
        if (!this.dirtyHashcode.getAndSet(false))
            return this.hashcode;
        this.hashcode = this.intent.hashCode();
        return this.hashcode;
    }

    public String asString() {
       
        return null;
    }

}
