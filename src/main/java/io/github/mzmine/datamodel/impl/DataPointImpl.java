/*
 * Copyright 2006-2015 The MZmine 3 Development Team
 * 
 * This file is part of MZmine 2.
 * 
 * MZmine 2 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 2; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.datamodel.impl;

import java.text.Format;

import javax.annotation.concurrent.Immutable;

import io.github.mzmine.datamodel.DataPoint;
import io.github.mzmine.main.MZmineCore;

/**
 * This class represents one data point of a spectrum (m/z and intensity pair).
 * Data point is immutable once created, so we can pass data points by
 * reference.
 */
@Immutable
class DataPointImpl implements DataPoint {

    private double mz, intensity;

    /**
     * @param mz
     * @param intensity
     */
    DataPointImpl(double mz, double intensity) {
        this.mz = mz;
        this.intensity = intensity;
    }

    @Override
    public double getIntensity() {
        return intensity;
    }

    @Override
    public double getMz() {
        return mz;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DataPoint))
            return false;
        DataPoint dp = (DataPoint) obj;
        return (this.mz == dp.getMz()) && (this.intensity == dp.getIntensity());
    }

    @Override
    public int hashCode() {
        return (int) (this.mz + this.intensity);
    }

    @Override
    public String toString() {
        Format mzFormat = MZmineCore.getConfiguration().getMZFormat();
        Format intensityFormat = MZmineCore.getConfiguration()
                .getIntensityFormat();
        String str = "m/z: " + mzFormat.format(mz) + ", intensity: "
                + intensityFormat.format(intensity);
        return str;
    }

}
