/* 
 * (C) Copyright 2015 by MSDK Development Team
 *
 * This software is dual-licensed under either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */

package io.github.msdk.datamodel.msspectra;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Range;

/**
 * This interface provides a convenient data structure for storing large amount
 * of MS data points in memory. Internally, it is implemented by two arrays, one
 * for m/z values (double[]) and one for intensity values (float[]). The arrays
 * are resized dynamically, as in ArrayList.
 * 
 * DataPointList provides all List methods, therefore it supports iteration.
 * Furthermore, it provides direct access to the underlying buffer arrays, for
 * more efficient data handling operations. The access through these arrays is
 * always preferred, as iteration via the List interface has to create a new
 * DataPoint instance for each visited data point.
 * 
 * DataPointList methods always keep the data points sorted in the m/z order,
 * and this requirement must be maintained when the internal m/z and intensity
 * arrays are modified directly.
 * 
 * The equals() method compares the contents of the two data point lists, and
 * ignores their internal array sizes (capacities).
 * 
 * This data structure is not thread-safe.
 */
public interface MsSpectrumDataPointList {

    /**
     * Returns the current m/z buffer array. The size of the array might be
     * larger than the actual size of this DataPointList, therefore data
     * operations should always use the size returned by the size() method and
     * not the length of the returned array. The returned array reflects only
     * the current state of this list - if more data points are added, the
     * internal buffer might be replaced with a larger array.
     * 
     * @return current m/z buffer
     */
    @Nonnull
    double[] getMzBuffer();

    /**
     * Returns the current intensity buffer array. The size of the array might
     * be larger than the actual size of this DataPointList, therefore data
     * operations should always use the size returned by the size() method and
     * not the length of the returned array. The returned array reflects only
     * the current state of this list - if more data points are added, the
     * internal buffer might be replaced with a larger array.
     * 
     * @return current intensity buffer
     */
    @Nonnull
    float[] getIntensityBuffer();

    /**
     * 
     * @return
     */
    int getSize();

    /**
     * 
     * @param newSize
     */
    void setSize(int newSize);

    /**
     * Ensures the size of the internal arrays is at least newSize
     */
    void allocate(int newSize);

    /**
     * 
     */
    void clear();

    /**
     * Sets the internal buffers to given arrays. The arrays will be referenced
     * directly without cloning. The m/z buffer contents must be sorted in
     * ascending order.
     * 
     * @param mzBuffer
     *            new m/z buffer
     * @param intensityBuffer
     *            new intensity buffer
     * @param newSize
     *            number of data point items in the buffers; this might be
     *            smaller than the actual length of the buffer arrays
     * @throws IllegalArgumentException
     *             if the size is larger than the length of the m/z array
     * @throws IllegalStateException
     *             if the m/z array is not sorted in ascending order
     */
    void setBuffers(@Nonnull double[] mzBuffer,
            @Nonnull float[] intensityBuffer, int newSize);

    /**
     * Add a new data point into the right position of the list, maintaining the
     * m/z order. If the internal arrays are full, they are replaced with new
     * arrays of twice the length.
     * 
     * @param newMz
     *            m/z value of the new data point
     * @param newIntensity
     *            intensity value of the new data point
     */
    void add(double newMz, float newIntensity);

    /**
     * Copies the contents of another data point list into this list. The
     * capacity of this list might stay the same or it might change, depending
     * on needs.
     * 
     * @param list
     *            source list to copy from.
     */
    void copyFrom(@Nonnull MsSpectrumDataPointList list);

    /**
     * Creates a new DataPointList that contains only those data points that fit
     * within given m/z and intensity boundaries.
     * 
     * @param mzRange
     *            m/z range to select
     * @param intensityRange
     *            intensity range to select
     * @return new DataPointList
     */
    @Nonnull
    MsSpectrumDataPointList selectDataPoints(@Nonnull Range<Double> mzRange,
            @Nonnull Range<Float> intensityRange);

    /**
     * Returns the range of m/z values in this DataPointList, or null if the
     * list is empty.
     * 
     * @return range of m/z values in this DataPointList, or null
     */
    @Nullable
    Range<Double> getMzRange();

    /**
     * Returns the sum of intensities of all data points (total ion current or
     * TIC). Returns 0 if there are no data points.
     * 
     * @return total ion current
     */
    @Nonnull
    Float getTIC();

}