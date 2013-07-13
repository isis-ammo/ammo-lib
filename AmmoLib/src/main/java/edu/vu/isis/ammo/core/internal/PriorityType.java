
package edu.vu.isis.ammo.core.internal;

/**
 * Indicates message priority.
 */
public enum PriorityType {
    /** Gets sent immediately */
    FLASH(0x80, "FLASH"),
    /** Time critical */
    URGENT(0x40, "URGENT"),
    /** Should be sent but not timing critical */
    IMPORTANT(0x20, "IMPORTANT"),
    /** Not particularly time critical, but may be of interest generally */
    NORMAL(0x10, "NORMAL"),
    /**
     * Large and should not be allowed to interfere, processed in background
     */
    BACKGROUND(0x08, "BACKGROUND");

    final public int o;
    final public String t;

    private PriorityType(int ordinal, String title) {
        this.o = ordinal;
        this.t = title;
    }

    /**
     * Produce string of the form... '<field-ordinal-value>';
     */
    public String quote() {
        return new StringBuilder().append("'").append(this.o).append("'").toString();
    }

    public String cv() {
        return String.valueOf(this.o);
    }

    static public PriorityType getInstance(String ordinal) {
        return PriorityType.values()[Integer.parseInt(ordinal)];
    }

    static public PriorityType getInstanceById(int o) {
        for (PriorityType candidate : PriorityType.values()) {
            final int lower = candidate.o;
            final int upper = lower << 1;
            if (upper > o && lower >= o)
                return candidate;
        }
        return null;
    }

    public CharSequence toString(int priorityId) {
        final StringBuilder sb = new StringBuilder().append(this.o);
        if (priorityId > this.o) {
            sb.append("+").append(priorityId - this.o);
        }
        return sb.toString();
    }
};
