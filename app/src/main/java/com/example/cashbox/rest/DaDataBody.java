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

import com.example.cashbox.test.Bound;

import org.json.JSONObject;

/**
 * @author Savelii Zagurskii
 */
public class DaDataBody {
    private String query, from_bound, to_bound;
    private int count;
    private String temp;
    private Bound from;
    private Bound to;

    public DaDataBody(String query, int count, Bound from, Bound to) {
        this.query = query;
        this.count = count;
        this.from = from;
        this.to = to;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Bound getFromV() {
        return from;
    }

    public void setFromV(Bound from) {
        this.from = from;
    }

    public Bound getToV() {
        return to;
    }

    public void setToV(Bound to) {
        this.to = to;
    }
}