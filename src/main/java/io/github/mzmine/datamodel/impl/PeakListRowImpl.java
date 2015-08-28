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

import java.util.List;
import java.util.Optional;

import io.github.mzmine.datamodel.PeakList;
import io.github.mzmine.datamodel.PeakListColumn;
import io.github.mzmine.datamodel.PeakListRow;

/**
 * Implementation of PeakListRow
 */
public class PeakListRowImpl implements PeakListRow {

    @Override
    public PeakList getParentPeakList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getNumberOfColumns() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <DataType> List<PeakListColumn<DataType>> getColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override

    public <DataType> Optional<DataType> getData(
            PeakListColumn<DataType> column) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

}
