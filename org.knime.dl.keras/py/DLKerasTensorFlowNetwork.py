# -*- coding: utf-8 -*-

# ------------------------------------------------------------------------
#  Copyright by KNIME AG, Zurich, Switzerland
#  Website: http://www.knime.com; Email: contact@knime.com
#
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License, Version 3, as
#  published by the Free Software Foundation.
#
#  This program is distributed in the hope that it will be useful, but
#  WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, see <http://www.gnu.org/licenses>.
#
#  Additional permission under GNU GPL version 3 section 7:
#
#  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
#  Hence, KNIME and ECLIPSE are both independent programs and are not
#  derived from each other. Should, however, the interpretation of the
#  GNU GPL Version 3 ("License") under any applicable laws result in
#  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
#  you the additional permission to use and propagate KNIME together with
#  ECLIPSE with only the license terms in place for ECLIPSE applying to
#  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
#  license terms of ECLIPSE themselves allow for the respective use and
#  propagation of ECLIPSE together with KNIME.
#
#  Additional permission relating to nodes for KNIME that extend the Node
#  Extension (and in particular that are based on subclasses of NodeModel,
#  NodeDialog, and NodeView) and that only interoperate with KNIME through
#  standard APIs ("Nodes"):
#  Nodes are deemed to be separate and independent programs and to not be
#  covered works.  Notwithstanding anything to the contrary in the
#  License, the License does not apply to Nodes, you are not required to
#  license Nodes under the License, and you are granted a license to
#  prepare and propagate Nodes, in each case even if such Nodes are
#  propagated with or for interoperation with KNIME.  The owner of a Node
#  may freely choose the license terms applicable to such Node, including
#  when such Node is propagated with or for interoperation with KNIME.
# ------------------------------------------------------------------------

'''
@author Marcel Wiedenmann, KNIME GmbH, Konstanz, Germany
@author Christian Dietz, KNIME GmbH, Konstanz, Germany
'''

from DLKerasNetworkSpecExtractor import DLKerasNetworkSpecExtractor
from DLKerasNetwork import DLKerasNetwork
from DLKerasNetwork import DLKerasNetworkReader
from DLKerasNetwork import DLKerasNetworkSpec


class DLKerasTensorFlowNetworkReader(DLKerasNetworkReader):

    def read(self, path, compile=True, compatibility_mode=False):
        model = self._read_internal(path, compile)
        return DLKerasTensorFlowNetwork(model, compatibility_mode)

    def read_from_json(self, path, compatibility_mode=False):
        model = self._read_from_json_internal(path)
        return DLKerasTensorFlowNetwork(model, compatibility_mode)

    def read_from_yaml(self, path, compatibility_mode=False):
        model = self._read_from_yaml_internal(path)
        return DLKerasTensorFlowNetwork(model, compatibility_mode)


class DLKerasTensorFlowNetwork(DLKerasNetwork):

    def __init__(self, model, compatibility_mode=False):
        super().__init__(model, compatibility_mode)

    #def train(self):
        # TODO: support TensorBoard?
        #config = self._spec.training_config
        #from keras.callbacks import TensorBoard
        #if config and not any(isinstance(cb, TensorBoard) for cb in config.callbacks):
        #    tb = TensorBoard(...) # TODO
        #    config.callbacks.append(tb)
    #    super().train()

    def _extract_model_spec(self):
        return DLKerasTensorFlowNetworkSpecExtractor(self._model).extract_spec()


class DLKerasTensorFlowNetworkSpec(DLKerasNetworkSpec):

    def __init__(self, input_specs, intermediate_output_specs, output_specs):
        super().__init__(input_specs, intermediate_output_specs, output_specs)

    @property
    def network_type(self):
        from DLKerasTensorFlowNetworkType import instance as TensorFlow
        return TensorFlow()


class DLKerasTensorFlowNetworkSpecExtractor(DLKerasNetworkSpecExtractor):

    def __init__(self, model):
        super().__init__(model)

    def _get_tensor_element_type(self, tensor):
        return tensor.dtype.name  # TensorFlow returns a TF dtype
