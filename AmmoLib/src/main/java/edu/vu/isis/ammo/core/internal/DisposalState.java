
package edu.vu.isis.ammo.core.internal;

/**
 * The states of a request over a particular channel. The DISTRIBUTE
 * RequestDisposal indicates that the total state is an aggregate of the
 * distribution of the request across the relevant channels.
 */
public enum DisposalState {
    /** an initial transient state */
    NEW(0x0001, "new"),
    /** channel is temporarily rejecting req (probably down) */
    REJECTED(0x0002, "rejected"),
    /** message is problematic, don't try again */
    BAD(0x0080, "bad"),
    /** cannot send, channel unavailable, but not because the message is bad */
    PENDING(0x0004, "pending"),
    /** message in channel queue */
    QUEUED(0x0008, "queued"),
    /** channel queue was busy (full channel queue) */
    BUSY(0x0100, "full"),
    /** message has been sent synchronously */
    SENT(0x0010, "sent"),
    /**
     * message sent asynchronously, with an expectation of an acknowledgment
     */
    TOLD(0x0020, "told"),
    /** async (told) message acknowledged */
    DELIVERED(0x0040, "delivered"), ;

    final public int o;
    final public String t;

    private DisposalState(int ordinal, String title) {
        this.o = ordinal;
        this.t = title;
    }

    @Override
    public String toString() {
        return this.t;
    }

    public String q() {
        return new StringBuilder().append("'").append(this.o).append("'").toString();
    }

    public String cv() {
        return String.valueOf(this.o);
    }

    static public DisposalState getInstance(String ordinal) {
        return DisposalState.values()[Integer.parseInt(ordinal)];
    }

    /**
     * This method indicates if the goal has been met. Note that false does not
     * mean the goal will not be reachable it only means that it has not yet
     * been reached.
     */
    public boolean goalReached(boolean goalCondition) {
        switch (this) {
            case QUEUED:
            case SENT:
            case DELIVERED:
                if (goalCondition == true)
                    return true;
                break;
            case PENDING:
            case REJECTED:
            case BUSY:
            case BAD:
                if (goalCondition == false)
                    return true;
                break;
            case NEW:
            case TOLD:
            default:
                break;
        }
        return false;
    }

    public DisposalState and(boolean clauseSuccess) {
        if (clauseSuccess)
            return this;
        return DisposalState.DELIVERED;
    }

    public int checkAggregate(final int aggregate) {
        return this.o & aggregate;
    }

    public int aggregate(final int aggregate) {
        return this.o | aggregate;
    }

    static public DisposalState getInstanceById(int o) {
        for (DisposalState candidate : DisposalState.values()) {
            if (candidate.o == o)
                return candidate;
        }
        return null;
    }
};
