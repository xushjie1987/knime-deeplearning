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
 */
package org.knime.dl.keras.core;

import org.knime.core.util.Version;
import org.knime.dl.core.DLInvalidSourceException;
import org.knime.dl.core.DLNetworkLocation;
import org.knime.dl.python.core.DLPythonNetworkSpec;
import org.knime.dl.util.DLUtils;

/**
 * @author Marcel Wiedenmann, KNIME GmbH, Konstanz, Germany
 * @author Christian Dietz, KNIME GmbH, Konstanz, Germany
 */
public interface DLKerasNetworkSpec extends DLPythonNetworkSpec {

    /**
     * Must only be called by implementing classes that are in the same bundle as {@link DLKerasAbstractNetworkSpec}.
     *
     * @return the version of the KNIME Deep Learning Keras bundle
     */
    public static Version getKerasBundleVersion() {
        return DLUtils.Misc.getVersionOfSameBundle(DLKerasNetworkSpec.class);
    }

    /**
     * Returns the version of the Keras library this network has been created with. May be <code>null</code> if the
     * version is unknown. Please note that the version should only be used for version management (e.g. resolving
     * backward compatibility issues). As such, it is not considered by {@link #hashCode()} and {@link #equals(Object)}.
     *
     * @return the Keras version, may be <code>null</code>
     * @since 3.6 - This getter will always return <code>null</code> if this instance is the result of deserializing an
     *        older version of this spec class.
     */
    Version getKerasVersion();

    /**
     * Creates a network whose {@link DLKerasNetwork#getSpec()} returns this spec and whose
     * {@link DLKerasNetwork#getSource()} returns the given network location.
     *
     * @param source the network location to use as source for the network to create
     * @param validateSource {@code true} if the network location shall be validated, {@code false} otherwise
     * @return the created network
     * @throws DLInvalidSourceException if {@code validateSource} is true and the given network location is invalid
     * @since 3.6.1
     */
    DLKerasNetwork create(DLNetworkLocation source, boolean validateSource) throws DLInvalidSourceException;

    /**
     * Equivalent to {@code create(source, true)}.
     *
     * @param source the network location to use as source for the network to create
     * @return the created network
     * @throws DLInvalidSourceException if the given network location is invalid
     */
    default DLKerasNetwork create(final DLNetworkLocation source) throws DLInvalidSourceException {
        return create(source, true);
    }
}
