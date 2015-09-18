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
 * MZmine 3; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.modules.featuretable;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import io.github.msdk.datamodel.featuretables.ColumnName;
import io.github.msdk.datamodel.featuretables.FeatureTable;
import io.github.msdk.datamodel.featuretables.FeatureTableColumn;
import io.github.msdk.datamodel.featuretables.FeatureTableRow;
import io.github.msdk.datamodel.featuretables.Sample;
import io.github.mzmine.modules.MZmineProcessingModule;
import io.github.mzmine.modules.MZmineRunnableModule;
import io.github.mzmine.util.TableUtils;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

public class Table implements MZmineRunnableModule {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Nonnull
    private static final String MODULE_DESCRIPTION = "This module creates a TableView of a feature table.";

    private static Map<Integer, TableColumn<Map,Object>> columnMap = new HashMap<Integer, TableColumn<Map,Object>>();

    @Override
    public @Nonnull String getDescription() {
        return MODULE_DESCRIPTION;
    }

    
    public static TableView getFeatureTable(FeatureTable featureTable) {

        char nextChar = 'A';
        String mapChar;
        int totalColumns = 0;
        final List<FeatureTableColumn<?>> columns = featureTable.getColumns();
        TableColumn<Map, Object> tableColumn = null;
        TableColumn sampleColumn = null;
        Sample prevSample = null, currentSample = null;

        // New Table
        TableView table = new TableView();

        // Common columns
        for (FeatureTableColumn<?> col : columns) {
            currentSample = col.getSample();
            Class dataType = col.getDataTypeClass();
            if (currentSample == null) {
                mapChar = String.valueOf(nextChar++);
                tableColumn = new TableColumn<>(col.getName());
                tableColumn.setCellValueFactory(
                        new MapValueFactory<Object>(mapChar));
                tableColumn
                        .setCellFactory(new CellFactoryCallback(col.getName()));
                if (col.getName().equals(ColumnName.ID.getName())
                        || col.getName().equals(ColumnName.MZ.getName())
                        || col.getName().equals(ColumnName.CHARGE.getName())) {
                    tableColumn.setStyle("-fx-alignment: CENTER;");
                }
                table.getColumns().add(tableColumn);
                columnMap.put(totalColumns, tableColumn);
                totalColumns++;
            }
        }

        // Sample columns
        for (FeatureTableColumn<?> col : columns) {
            currentSample = col.getSample();

            if (currentSample != null) {
                // Create sample header
                if (prevSample == null || !prevSample.equals(currentSample)) {
                    sampleColumn = new TableColumn(currentSample.getName());
                    table.getColumns().add(sampleColumn);
                    prevSample = currentSample;
                }

                // Creates sample columns
                mapChar = String.valueOf(nextChar++);
                tableColumn = new TableColumn<>(col.getName());
                tableColumn.setCellValueFactory(
                        new MapValueFactory<Object>(mapChar));
                tableColumn
                        .setCellFactory(new CellFactoryCallback(col.getName()));
                tableColumn.setStyle("-fx-alignment: CENTER;");
                sampleColumn.getColumns().add(tableColumn);
                columnMap.put(totalColumns, tableColumn);
                totalColumns++;
            }

        }

        // Add rows
        final List<FeatureTableRow> rows = featureTable.getRows();
        int rowNr = 0;
        ObservableList<Map> allData = table.getItems();
        for (FeatureTableRow row : rows) {
            rowNr++;
            int columnNr = 0;
            Map<String, Object> dataRow = new HashMap<>();

            for (FeatureTableColumn<?> column : columns) {
                String mapKey = Character.toString((char) ('A' + columnNr));
                if (row.getData(column) != null) {
                    Object value1 = row.getData(column);
                    dataRow.put(mapKey, value1);
                } else {
                    dataRow.put(mapKey, null);
                }

                columnNr++;
            }

            allData.add(dataRow);
        }

        // Table preferences
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().setCellSelectionEnabled(true);

        // Enable copy
        TableUtils.addCopyHandler(table);
        return table;

    }

    private Map getColumnMap() {
        return columnMap;
    }

}
