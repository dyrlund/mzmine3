/*
 * Copyright 2006-2015 The MZmine 3 Development Team
 * 
 * This file is part of MZmine 3.
 * 
 * MZmine 3 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 3 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 3; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.modules.plots.msspectrum;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import io.github.msdk.datamodel.msspectra.MsSpectrum;
import javafx.scene.layout.BorderPane;

/**
 * MS spectrum plot window
 */
public class MsSpectrumPlotWindow extends BorderPane {

    private final ChartNode chart;

    MsSpectrumPlotWindow(PlottingLibrary library) {
        switch (library) {
        case JFREECHART:
            ChartNodeJFreeChart jfreeNode = new ChartNodeJFreeChart();
            this.chart = jfreeNode;
            setCenter(jfreeNode);
            break;
        case WATERLOOFX:
            ChartNodeWaterlooFX waterlooNode = new ChartNodeWaterlooFX();
            this.chart = waterlooNode;
            setCenter(waterlooNode);
            break;
        default:
        case JAVAFX_CHARTS:
            ChartNodeJavaFX jfxNode = new ChartNodeJavaFX();
            this.chart = jfxNode;
            setCenter(jfxNode);
            break;
        }
    }

    /**
     * Add a new spectrum to the plot.
     * 
     * @param spectrum
     */
    public void addSpectrum(@Nonnull MsSpectrum spectrum) {
        Preconditions.checkNotNull(spectrum);
        chart.addSpectrum(spectrum);
    }

}