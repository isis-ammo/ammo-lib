package edu.vu.isis.ammo.api.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;
import edu.vu.isis.ammo.api.IAmmoRequest;

/**
 * The form is used to convey some selection of objects.
 */

public class Form extends AmmoType implements Map<String, String>, IAmmoRequest.Form {
	    
    private final Map<String,String> backing;
    
    // *********************************
    // Parcelable Support
    // *********************************
   
    public static final Parcelable.Creator<Form> CREATOR = 
        new Parcelable.Creator<Form>() {

        @Override
        public Form createFromParcel(Parcel source) {
            return new Form(source);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };
    
    public static Form readFromParcel(Parcel source) {
    	if (AmmoType.isNull(source)) return null;
        return new Form(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	plogger.trace("marshall form {}", this);
        // dest.writeMap(this);
    }

    private Form(Parcel in) {
    	this.backing = new HashMap<String,String>();
        // in.readMap(this, loader)
    	plogger.trace("unmarshall form {}", this);
    }
	// *********************************
	// Standard Methods
	// *********************************
	@Override
	public String toString() {
		return this.backing.toString();
	}

    // *********************************
    // IAmmoReques Support
    // *********************************

    public Form() {
    	this.backing = new HashMap<String,String>();
    }

    // *********************************
    // Map Methods
    // *********************************
    
	@Override
	public void clear() {
		this.backing.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.backing.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.backing.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return this.backing.entrySet();
	}

	@Override
	public String get(Object key) {
		return this.backing.get(key);
	}

	@Override
	public boolean isEmpty() {
		return this.backing.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return this.backing.keySet();
	}

	@Override
	public String put(String key, String value) {
		return this.backing.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> arg0) {
		 this.backing.putAll(arg0);
	}

	@Override
	public String remove(Object key) {
		return this.backing.remove(key);
	}

	@Override
	public int size() {
		return this.backing.size();
	}

	@Override
	public Collection<String> values() {
		return this.backing.values();
	}
	
}
