/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Savelii Zagurskii
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.cashbox.rest;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.example.cashbox.utils.PropLocations;
import com.example.cashbox.utils.Property;
import com.google.gson.annotations.SerializedName;


public class DaDataBody {

    @SerializedName("from_bound")
    private Property fromBound;

    @SerializedName("query")
    private String query;

    @SerializedName("count")
    private int count;

    @SerializedName("to_bound")
    private Property toBound;

    @SerializedName("restrict_value")
    private boolean restrictValue;

    @SerializedName("locations")
    private PropLocations[] locations;


    public void setFromBound(Property fromBound) {
        this.fromBound = fromBound;
    }

    public Property getFromBound() {
        return fromBound;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setToBound(Property toBound) {
        this.toBound = toBound;
    }

    public Property getToBound() {
        return toBound;
    }

    public boolean getRestrictValue() {
        return restrictValue;
    }

    public void setRestrictValue(boolean restrictValue) {
        this.restrictValue = restrictValue;
    }

    public PropLocations[] getLocations() {
        return locations;
    }

    public void setLocations(PropLocations[] locations) {
        this.locations = locations;
    }
}