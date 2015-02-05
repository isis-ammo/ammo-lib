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



package edu.vu.isis.ammo.util;

/**
 * A interval is a contiguous subset. The idea is that two values define in
 * bounded (usually finite) subset of an unlimited space "S". The set "S" must
 * be transitive. Two cuts are made, a single cut divides the space in two, two
 * cuts typically bound a finite space.
 */
public class Interval<T extends Comparable<T>> {

    private final T lowCut;
    private final boolean lowOpen;

    private final T highCut;
    private final boolean highOpen;

    /**
     * Creates a ranger having a set of bounds. This constructor presumes the
     * range includes the end points. The order of the end points is important,
     * the lower bound must be supplied first.
     * 
     * @param lowCut The lowest end of the range {@code null} indicates
     *            unlimited
     * @param highCut The highest end of the range {@code null} indicates
     *            unlimited
     * @throws IllegalArgumentException if lower end is not lower than upper end
     */
    public Interval(final T lowCut, final T highCut) throws IllegalArgumentException {
        if (lowCut != null && highCut != null && lowCut.compareTo(highCut) > 0) {
            throw new IllegalArgumentException("inconsistent ends");
        }
        this.lowOpen = true;
        this.lowCut = lowCut;
        this.highOpen = true;
        this.highCut = highCut;
    }

    public Interval(final T lowCut, final boolean lowOpen, final T highCut,
            final boolean highOpen)
            throws IllegalArgumentException {
        if (lowCut != null && highCut != null && lowCut.compareTo(highCut) > 0) {
            throw new IllegalArgumentException("inconsistent ends");
        }
        this.lowOpen = lowOpen;
        this.lowCut = lowCut;
        this.highOpen = highOpen;
        this.highCut = highCut;
    }

    /**
     * Checks whether the object falls between the end points. As there is some
     * ambiguity regarding a null (is it low or high) a null is considered an
     * error.
     * 
     * @param object The object to be checked. Must not be {@code null}.
     * @return {@code false} if {@code object} is lower than the lower bound or
     *         greater than the upper bound; otherwise {@code true}.
     * @throws IllegalArgumentException if {@code object} is {@code null}.
     */
    public final boolean between(final T obj) throws IllegalArgumentException {
        if (obj == null)
            throw new IllegalArgumentException("object is null");

        if (this.lowCut != null) {
            final int side = obj.compareTo(this.lowCut);
            if (side < 0) {
                return false;
            } else if (side == 0 && this.lowOpen) {
                return false;
            }
        }

        if (this.highCut != null) {
            final int side = obj.compareTo(this.lowCut);
            if (0 < side) {
                return false;
            } else if (side == 0 && this.highOpen) {
                return false;
            }
        }

        return true;
    }

    public enum IntersectionType {
        /** fully contained */
        CONTAINED,
        /** overlap to the left */
        OVERLAP_LEFT,
        /** overlap to the right */
        OVERLAP_RIGHT,
        /** disjoint to the left */
        DISJOINT_LEFT,
        /** disjoint to the right */
        DISJOINT_RIGHT,
        /** target contains the container */
        SURROUNDS;
    }

    /**
     * Checks if a ranger contains a range,
     * 
     * @param target intersect , {@code null} is disallowed.
     * @return intersection type
     * @throws IllegalArgumentException if {@code ranger} is {@code null}.
     */
    public final IntersectionType contact(final Interval<T> that)
            throws IllegalArgumentException {
        if (that == null)
            throw new IllegalArgumentException("null is not a valid ranger");

        final boolean outsideLeft =
                insideCheck(this.lowCut, this.lowOpen, that.highCut, that.highOpen);
        if (outsideLeft)
            return IntersectionType.DISJOINT_LEFT;
        final boolean outsideRight =
                insideCheck(that.lowCut, that.lowOpen, this.highCut, this.highOpen);
        if (outsideRight)
            return IntersectionType.DISJOINT_RIGHT;

        final boolean insideLeft =
                insideCheck(this.lowCut, this.lowOpen, that.lowCut, that.lowOpen);
        final boolean insideRight =
                insideCheck(that.highCut, that.highOpen, this.highCut, this.highOpen);
        if (insideLeft && insideRight)
            return IntersectionType.CONTAINED;
        if (!insideLeft && !insideRight)
            return IntersectionType.SURROUNDS;

        if (!insideLeft && insideRight)
            return IntersectionType.OVERLAP_LEFT;
        return IntersectionType.OVERLAP_RIGHT;
    }

    /**
     * Given two values determine which one is "inside" the other.
     * 
     * @param first
     * @param firstOpen
     * @param second
     * @param secondOpen
     * @return
     */
    static private <T extends Comparable<T>> boolean insideCheck(T first, boolean firstOpen,
            T second, boolean secondOpen) {
        if (first == null)
            return true;
        if (second == null)
            return false;
        final int comparison = first.compareTo(second);
        if (comparison == 0) {
            if (firstOpen && secondOpen)
                return true;
            if (firstOpen && !secondOpen)
                return false;
            if (!firstOpen && secondOpen)
                return true;
            else
                return false;
        }
        if (comparison < 0)
            return true;
        return false;
    }

    static final private int LOW_HASHCODE = new Integer(Integer.MIN_VALUE).hashCode();
    static final private int HIGH_HASHCODE = new Integer(Integer.MAX_VALUE).hashCode();
    private volatile int hashCode;

    @Override
    public final int hashCode() {
        int result = this.hashCode;
        if (result != 0)
            return result;
        result = 17;
        result *= 31;
        result += this.lowCut == null ? LOW_HASHCODE : this.lowCut.hashCode();
        result *= 31;
        result += this.highCut == null ? HIGH_HASHCODE : this.highCut.hashCode();
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof Interval)) {
            return false;
        }
        final Interval<?> that = (Interval<?>) obj;
        return equals(this.lowCut, that.lowCut) && equals(this.highCut, that.highCut);
    }

    private static final boolean equals(final Object xo, final Object yo) {
        if (xo == null && yo == null) {
            return true;
        }
        if (xo != null && yo != null) {
            return xo.equals(yo);
        }

        return false;
    }

    /**
     * Use the notation from elementary school. [] indicate a closed interval ()
     * indicates an open interval
     */
    @Override
    public final String toString() {
        return new StringBuilder("Ranger ")
                .append((this.lowOpen ? "(" : "[")).append(this.lowCut)
                .append(",")
                .append(this.highCut).append((this.highOpen ? ")" : "]"))
                .toString();
    }

}
