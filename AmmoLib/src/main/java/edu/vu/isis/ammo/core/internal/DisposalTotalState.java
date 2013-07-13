
package edu.vu.isis.ammo.core.internal;

/**
 * The states of a request. The DISTRIBUTE state indicates that the total state
 * is an aggregate of the distribution of the request across the relevant
 * channels. see the ChannelDisposal
 */
public enum DisposalTotalState {
    /** either all pending or none */
    NEW(0x01, "new"),
    /** the request is being actively processed */
    DISTRIBUTE(0x02, "distribute"),
    /** the expiration time of the request has arrived */
    EXPIRED(0x04, "expired"),
    /** the distribution rule has been fulfilled */
    COMPLETE(0x08, "complete"),
    /** the distribution rule cannot be completely fulfilled */
    INCOMPLETE(0x10, "incomplete"),
    /** the request failed */
    FAILED(0x20, "failed");

    final public int o;
    final public String t;

    private DisposalTotalState(int ordinal, String title) {
        this.o = ordinal;
        this.t = title;
    }

    public String q() {
        return new StringBuilder().append("'").append(this.o).append("'").toString();
    }

    public String cv() {
        return String.valueOf(this.o);
    }

    static public DisposalTotalState getInstance(String ordinal) {
        return DisposalTotalState.values()[Integer.parseInt(ordinal)];
    }

    static public DisposalTotalState getInstanceById(int o) {
        for (DisposalTotalState candidate : DisposalTotalState.values()) {
            if (candidate.o == o)
                return candidate;
        }
        return null;
    }

};
