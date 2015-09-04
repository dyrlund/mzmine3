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

package io.github.msdk.datamodel.ionannotations;

import io.github.msdk.datamodel.rawdata.PolarityType;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Annotation of ionization type
 */
@Immutable
public interface IonType {

    /**
     * @return Name of ionization type, such as [M+2H]2+.
     */
    @Nonnull
    String getName();

    /**
     * @return Polarity of ionization. See #PolarityType.java for values.
     */
    @Nonnull
    PolarityType getPolarity();

    /**
     * @return Number of molecules in this ion.
     */
    int getNumberOfMolecules();

    /**
     * @return  Chemical formula for adduct.
     */
    @Nonnull
    String getAdductFormula();

    /**
     * @return  Charge of ion.
     */
    int getCharge();

}