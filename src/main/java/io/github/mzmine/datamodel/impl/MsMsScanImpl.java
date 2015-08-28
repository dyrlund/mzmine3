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
 * MZmine 2; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.datamodel.impl;

import javax.annotation.Nullable;

import com.google.common.collect.Range;

import io.github.mzmine.datamodel.ChromatographyData;
import io.github.mzmine.datamodel.MassSpectrumType;
import io.github.mzmine.datamodel.MsMsScan;
import io.github.mzmine.datamodel.MsScan;
import io.github.mzmine.datamodel.RawDataFile;

/**
 * Simple implementation of the Scan interface.
 */
public class MsMsScanImpl extends MsScanImpl implements MsMsScan {

    private MsScan parentScan;
    private Double precursorMZ;
    private Integer precursorCharge;
    private Double activationEnergy;

    public MsMsScanImpl(RawDataFile dataFile) {
        super(dataFile);
    }

    @Override
    public int getPrecursorCharge() {
        return precursorCharge;
    }

    @Override
    public void setPrecursorCharge(int precursorCharge) {
        this.precursorCharge = precursorCharge;
    }

    @Override
    public MsScan getParentScan() {
        return parentScan;
    }

    @Override
    public void setParentScan(@Nullable MsScan parentScan) {
        this.parentScan = parentScan;
    }

    @Override
    public double getActivationEnergy() {
        return activationEnergy;
    }

    @Override
    public void setActivationEnergy(double activationEnergy) {
        this.activationEnergy = activationEnergy;
    }

    @Override
    public ChromatographyData getChromatographyData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MassSpectrumType getSpectrumType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getPrecursorMz() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Range<Double> getIsolationWidth() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrecursorMz(Double precursorMZ) {
        // TODO Auto-generated method stub

    }

}
