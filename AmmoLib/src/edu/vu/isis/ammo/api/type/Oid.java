/*Copyright (C) 2010-2012 Institute for Software Integrated Systems (ISIS)
This software was developed by the Institute for Software Integrated
Systems (ISIS) at Vanderbilt University, Tennessee, USA for the 
Transformative Apps program under DARPA, Contract # HR011-10-C-0175.
The United States Government has unlimited rights to this software. 
The US government has the right to use, modify, reproduce, release, 
perform, display, or disclose computer software or computer software 
documentation in whole or in part, in any manner and for any 
purpose whatsoever, and to have or authorize others to do so.
 */
package edu.vu.isis.ammo.api.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.os.Parcel;
import android.os.Parcelable;

public class Oid extends AmmoType implements List<Integer> {

    private final List<Integer> backing;

    public static final Oid EMPTY = new Oid();

    // *********************************
    // Parcelable Support
    // *********************************

    public static final Parcelable.Creator<Oid> CREATOR = 
            new Parcelable.Creator<Oid>() {

        @Override
        public Oid createFromParcel(Parcel source) {
            return new Oid(source);
        }

        @Override
        public Oid[] newArray(int size) {
            return new Oid[size];
        }
    };
    public Oid readFromParcel(Parcel source) {
        if (AmmoType.isNull(source)) return null;
        return new Oid(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        plogger.trace("marshall oid {}", this);
        final int[] array = new int[this.backing.size()];
        int ix=0;
        for (Integer item : this.backing) array[ix++] = item.intValue();
        dest.writeIntArray(array);
    }

    private Oid(Parcel in) {
        final int[] array = in.createIntArray();
        this.backing = new ArrayList<Integer>(array.length);
        for (int item : array) this.backing.add(item);
        plogger.trace("unmarshall oid {}", this);
    }

    // *********************************
    // Standard Methods
    // *********************************
    @Override
    public String toString() {
        return this.backing.toString();
    }
    
    /**
     * check that the two objects are logically equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.backing == null) return false;
        if (!(obj instanceof Oid)) return false;
        final Oid that = (Oid) obj;
        if (this.backing == that.backing) return true;
        if (this.backing == null) return false;
        return (this.backing.equals(that.backing));
    }

    // *********************************
    // IAmmoRequest Support
    // *********************************

    private Oid() {
        this.backing = new ArrayList<Integer>();
    }

    @Override
    public boolean add(Integer arg0) {
        return this.backing.add(arg0);
    }

    @Override
    public void add(int location, Integer object) {
        this.backing.add(location, object);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> arg0) {
        return this.backing.addAll(arg0);
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends Integer> arg1) {
        return this.backing.addAll(arg1);
    }

    @Override
    public void clear() {
        this.backing.clear();
    }

    @Override
    public boolean contains(Object object) {
        return this.backing.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return this.backing.containsAll(arg0);
    }

    @Override
    public Integer get(int location) {
        return this.backing.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return this.backing.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return this.backing.isEmpty();
    }

    @Override
    public Iterator<Integer> iterator() {
        return this.backing.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.backing.lastIndexOf(object);
    }

    @Override
    public ListIterator<Integer> listIterator() {
        return this.backing.listIterator();
    }

    @Override
    public ListIterator<Integer> listIterator(int location) {
        return this.backing.listIterator(location);
    }

    @Override
    public Integer remove(int location) {
        return this.backing.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        return this.backing.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        return this.backing.removeAll(arg0);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        return this.backing.retainAll(arg0);
    }

    @Override
    public Integer set(int location, Integer object) {
        return this.backing.set(location, object);
    }

    @Override
    public int size() {
        return this.backing.size();
    }

    @Override
    public List<Integer> subList(int start, int end) {
        return this.backing.subList(start, end);
    }

    @Override
    public Object[] toArray() {
        return this.backing.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return this.backing.toArray(array);
    }


}
