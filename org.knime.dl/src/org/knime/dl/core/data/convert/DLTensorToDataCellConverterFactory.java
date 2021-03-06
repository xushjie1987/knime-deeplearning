/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ---------------------------------------------------------------------
 *
 * History
 *   Jun 30, 2017 (marcel): created
 */
package org.knime.dl.core.data.convert;

import java.util.OptionalLong;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataType;
import org.knime.dl.core.DLTensor;
import org.knime.dl.core.DLTensorSpec;
import org.knime.dl.core.data.DLReadableBuffer;

/**
 * Root interface for deep learning output converter factories that create converters which allow conversion of
 * {@link DLTensor tensor} types into {@link DataCell data cells}.
 *
 * @param <I> the input {@link DLReadableBuffer buffer type}
 * @param <O> the output {@link DataType data type}
 * @see DLDataValueToTensorConverterFactory
 * @author Marcel Wiedenmann, KNIME GmbH, Konstanz, Germany
 * @author Christian Dietz, KNIME GmbH, Konstanz, Germany
 */
public interface DLTensorToDataCellConverterFactory<I extends DLReadableBuffer, O extends DataCell> {

	/**
	 * Returns the unique identifier of the converter factory.
	 *
	 * @return the factory's identifier
	 */
	default String getIdentifier() {
		return getClass().getName();
	}

	/**
	 * Returns the friendly name of the converter factory. The name can be presented to the user and allows distinct
	 * recognition.
	 *
	 * @return the factory's name
	 */
	String getName();

	/**
	 * Returns the input {@link DLReadableBuffer buffer type} that is supported by converters created by this factory.
	 *
	 * @return the input buffer type.
	 */
	Class<I> getBufferType();

	/**
	 * Returns the output {@link DataType data type} that is supported by converters created by this factory.
	 * <P>
	 * <code>{@link DataType#getCellClass()} == Class&lt;0&gt;</code> must hold for the returned data type.
	 *
	 * @return the output data type
	 */
	DataType getDestType();

	/**
	 * Returns the number of elements that will make up the output of converters created by this factory given an input
	 * spec.
	 * <P>
	 * The output of this method is interpreted on a per-row basis, i.e. the {@link DLTensorSpec#getBatchSize() batch
	 * size} of the input must not be taken into account.
	 *
	 * @param spec the input spec
	 * @return the number of output elements
	 */
	OptionalLong getDestCount(DLTensorSpec spec);

	/**
	 * Creates a new converter instance.
	 *
	 * @return a new converter instance
	 */
	DLTensorToDataCellConverter<I, O> createConverter();
}
