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
package org.knime.dl.python.core;

import static com.google.common.base.Preconditions.checkState;
import static org.knime.dl.util.DLUtils.Preconditions.checkNotNullOrEmpty;

import org.knime.python2.extensions.serializationlibrary.interfaces.Row;
import org.knime.python2.extensions.serializationlibrary.interfaces.TableCreator;
import org.knime.python2.extensions.serializationlibrary.interfaces.TableCreatorFactory;
import org.knime.python2.extensions.serializationlibrary.interfaces.TableSpec;
import org.knime.python2.extensions.serializationlibrary.interfaces.Type;

/**
 * @author Marcel Wiedenmann, KNIME GmbH, Konstanz, Germany
 * @author Christian Dietz, KNIME GmbH, Konstanz, Germany
 */
public class DLPythonNetworkHandleTableCreatorFactory implements TableCreatorFactory {

    private static final int HANDLE_IDX = 0;

    @Override
    public TableCreator<?> createTableCreator(final TableSpec spec, final int tableSize) {
        return new DLPythonNetworkHandleTableCreator(spec, tableSize);
    }

    public static class DLPythonNetworkHandleTableCreator implements TableCreator<DLPythonNetworkHandle> {

        private final TableSpec m_tableSpec;

        private DLPythonNetworkHandle m_networkHandle;

        private static boolean checkTableSpec(final TableSpec spec, final int tableSize) {
            return tableSize == 1 //
                && spec.getNumberColumns() == 1 //
                && spec.getColumnNames()[HANDLE_IDX].equals(DLPythonAbstractCommands.CURRENT_NETWORK_NAME)
                && spec.getColumnTypes()[HANDLE_IDX].equals(Type.STRING);
        }

        public DLPythonNetworkHandleTableCreator(final TableSpec spec, final int tableSize) {
            if (!checkTableSpec(spec, tableSize)) {
                throw new IllegalStateException("Python side sent an invalid network handle table.");
            }
            m_tableSpec = spec;
        }

        @Override
        public void addRow(final Row row) {
            m_networkHandle = new DLPythonNetworkHandle(checkNotNullOrEmpty(row.getCell(HANDLE_IDX).getStringValue(),
                "Python sent a null or empty network handle."));
        }

        @Override
        public TableSpec getTableSpec() {
            return m_tableSpec;
        }

        @Override
        public DLPythonNetworkHandle getTable() {
            checkState(m_networkHandle != null, "Python did not send a network handle.");
            return m_networkHandle;
        }
    }
}
