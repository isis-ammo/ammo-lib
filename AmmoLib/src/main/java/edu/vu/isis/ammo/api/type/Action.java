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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import edu.vu.isis.ammo.api.IncompleteRequest;

/**
 * deprecated:
 * DIRECTED_POSTAL, DIRECTED_SUBSCRIBE, PUBLISH
 * 
 */
public enum Action {
	NONE(-1),
	POSTAL(0), DIRECTED_POSTAL(1), 
	UNPOSTAL(6),
	PUBLISH(2), 
	RETRIEVAL(3), 
	UNRETRIEVAL(7), 
	SUBSCRIBE(4), DIRECTED_SUBSCRIBE(5),
	UNSUBSCRIBE(8);

	public static final Logger logger = LoggerFactory.getLogger("class.Action");
	public static final Logger IPC_REQ_IN = LoggerFactory.getLogger( "ipc.request.inbound" );

	public int o;

	private Action(int ordinal) {
		this.o = ordinal;
	}

	@Override
	public String toString() {
		switch (this) {
		case NONE: return "NONE";
		case POSTAL: return "POSTAL";
		case UNPOSTAL: return "UNPOSTAL";
		case RETRIEVAL: return "RETRIEVAL";
		case UNRETRIEVAL: return "CANCEL RETRIEVAL";
		case SUBSCRIBE: return "SUBSCRIBE";
		case UNSUBSCRIBE: return "UNSUBSCRIBE";
		default: 
			return null;
		}
	}

	static public void writeToParcel(final Parcel dest, final Action action) {
		logger.trace("index: {}", action.o);
		dest.writeInt(action.o);
	}

	static public Action getInstance(Parcel in) throws IncompleteRequest { 
		final int ordinal = in.readInt();
		try {
			if (ordinal == NONE.o) return NONE;
			if (ordinal == POSTAL.o) return POSTAL;
			if (ordinal == UNPOSTAL.o) return UNPOSTAL;
			
			if (ordinal == RETRIEVAL.o) return RETRIEVAL;
			if (ordinal == UNRETRIEVAL.o) return UNRETRIEVAL;
			
			if (ordinal == SUBSCRIBE.o) return SUBSCRIBE;
			if (ordinal == UNSUBSCRIBE.o) return UNSUBSCRIBE;
			
			IncompleteRequest.logger.error("bad action index {}", ordinal);
			throw new IncompleteRequest("bad action");

		} catch (Exception ex) {
			IncompleteRequest.logger.error("bad action index {}", ordinal);
			throw new IncompleteRequest(ex);
		}
	}
}