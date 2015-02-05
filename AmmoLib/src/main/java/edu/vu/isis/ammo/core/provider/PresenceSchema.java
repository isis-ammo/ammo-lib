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



package edu.vu.isis.ammo.core.provider;

import java.util.ArrayList;

import android.provider.BaseColumns;
import edu.vu.isis.ammo.util.EnumUtils;

public enum PresenceSchema implements RelationSchema {
    /** This is a locally unique identifier for the request */
    ID(BaseColumns._ID, "TEXT"),

    /** This is a universally unique identifier for the request */
    UUID("TEXT"),

    /** Device originating the request */
    ORIGIN("TEXT"),

    /** Who last modified the request */
    OPERATOR("TEXT"),

    /**
     * Use the PresenceState enum class to make these values useful. e.g.
     * 
     * @code{ final EnumSet<PresenceState> set =
     *        Presence.decode(cursor.getLong(cursor.getColumnIndex(ix))); } You
     *        can set the value in a similar fashion.
     * @code{ final long encodedValue =
     *        Presence.encode(EnumSet.of(PresenceState.RARE));
     *        cv.put(PresenceSchema.STATE.field, encodedValue); } The encoded
     *        value is placed in the state field.
     */
    STATE("INTEGER"),

    /**
     * The time when first observed (millisec); indicates the first time the
     * peer was observed.
     */
    FIRST("INTEGER"),

    /**
     * when last observed (millisec); When the operator was last seen "speaking"
     * on the channel. The latest field indicates the last time the peer was
     * observed.
     */
    LATEST("INTEGER"),

    /**
     * how many times seen since first. How many times the peer has been seen
     * since FIRST. Each time LATEST is changed this COUNT should be incremented
     */
    COUNT("INTEGER"),

    /**
     * The time when no longer relevant (millisec); the request becomes stale
     * and may be discarded.
     */
    EXPIRATION("INTEGER");

    /**
     * Selection predicates
     * 
     */
    /** '*' or null */
    static public final String WHERE_ALL = "where-all";
    /** "operator"=? */
    static public final String WHERE_OPERATOR_IS = "where-operator-is";

    /** textual field name */
    final public String field;

    /** type */
    final public String type;

    private PresenceSchema(String type) {
        this.field = this.name();
        this.type = type;
    }

    private PresenceSchema(String field, String type) {
        this.field = field;
        this.type = type;
    }

    /**
     * an array of all field names
     */
    public static final String[] FIELD_NAMES = EnumUtils.buildFieldNames(PresenceSchema.class);

    /**
     * map an array of field names to fields.
     * 
     * @param names an array of field names
     * @return an array of fields
     */
    public static ArrayList<PresenceSchema> mapFields(final String[] names) {
        return EnumUtils.getFields(PresenceSchema.class, names);
    }

    @Override
    public String toString() {
        return this.field;
    }

    @Override
    public String getField() {
        return this.field;
    }
}
