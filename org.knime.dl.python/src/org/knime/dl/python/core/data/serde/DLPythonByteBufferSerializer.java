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
package org.knime.dl.python.core.data.serde;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.knime.dl.core.data.DLAbstractByteBuffer;
import org.knime.dl.python.core.data.DLPythonAbstractByteBuffer;
import org.knime.python.typeextension.Serializer;

/**
 * @author Benjamin Wilhelm, KNIME GmbH, Konstanz, Germany
 * @author Marcel Wiedenmann, KNIME GmbH, Konstanz, Germany
 * @author Christian Dietz, KNIME GmbH, Konstanz, Germany
 */
public class DLPythonByteBufferSerializer<B extends DLPythonAbstractByteBuffer<? extends DLAbstractByteBuffer>> implements Serializer<B> {

    @Override
    public byte[] serialize(final B value) throws IOException {
        // TODO: we serialize to flat buffers for now
        // final int numDimensions = value.getNumDimensions();
        // final long[] shape = value.getShape();
        final long size = value.size() - value.getNextReadPosition();
        final long numBytes = /*
                               * Integer.BYTES + numDimensions * Long.BYTES +
                               */ size * Byte.BYTES;
        if (numBytes > Integer.MAX_VALUE) {
            throw new IOException(
                "Transmitting data to Python failed. Buffer size exceeds the limit of 2^31-1 bytes.");
        }
        final byte[] tensorStorage = value.getStorageForReading(value.getNextReadPosition(), size);
        final ByteBuffer buffer = ByteBuffer.allocate((int)numBytes);
        buffer.put(tensorStorage, (int)value.getNextReadPosition(), (int)size);
        // TODO: we serialize to flat buffers for now
        // buffer.putInt(numDimensions);
        // for (final long dim : shape) {
        // buffer.putLong(dim);
        // }
        return buffer.array();
    }
}
