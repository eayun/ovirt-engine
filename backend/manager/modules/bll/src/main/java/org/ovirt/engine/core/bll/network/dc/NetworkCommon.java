package org.ovirt.engine.core.bll.network.dc;

import java.util.Collections;
import java.util.List;

import org.ovirt.engine.core.bll.CommandBase;
import org.ovirt.engine.core.bll.ValidationResult;
import org.ovirt.engine.core.bll.utils.PermissionSubject;
import org.ovirt.engine.core.common.FeatureSupported;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.AddNetworkStoragePoolParameters;
import org.ovirt.engine.core.common.businessentities.Network;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.VdcBllMessages;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.CustomLogField;
import org.ovirt.engine.core.dal.dbbroker.auditloghandling.CustomLogFields;

@SuppressWarnings("serial")
@CustomLogFields({ @CustomLogField("NetworkName") })
public abstract class NetworkCommon<T extends AddNetworkStoragePoolParameters> extends CommandBase<T> {
    public NetworkCommon(T parameters) {
        super(parameters);
        this.setStoragePoolId(getNetwork().getstorage_pool_id());
    }

    protected Network getNetwork() {
        return getParameters().getNetwork();
    }

    public String getNetworkName() {
        return getNetwork().getname();
    }

    @Override
    protected void setActionMessageParameters() {
        addCanDoActionMessage(VdcBllMessages.VAR__TYPE__NETWORK);
    }

    protected ValidationResult vmNetworkSetCorrectly() {
        return getNetwork().isVmNetwork() || FeatureSupported.nonVmNetwork(getStoragePool().getcompatibility_version())
                ? ValidationResult.VALID
                : new ValidationResult(VdcBllMessages.NON_VM_NETWORK_NOT_SUPPORTED_FOR_POOL_LEVEL);
    }

    protected ValidationResult stpForVmNetworkOnly() {
        return getNetwork().isVmNetwork() || !getNetwork().getstp()
                ? ValidationResult.VALID
                : new ValidationResult(VdcBllMessages.NON_VM_NETWORK_CANNOT_SUPPORT_STP);
    }

    protected ValidationResult mtuValid() {
        return getNetwork().getMtu() == 0
                || FeatureSupported.mtuSpecification(getStoragePool().getcompatibility_version())
                ? ValidationResult.VALID
                : new ValidationResult(VdcBllMessages.NETWORK_MTU_OVERRIDE_NOT_SUPPORTED);
    }

    protected ValidationResult vlanIsFree(List<Network> networks) {
        if (getNetwork().getvlan_id() != null) {
            for (Network network : networks) {
                if (network.getvlan_id() != null
                        && network.getvlan_id().equals(getNetwork().getvlan_id())
                        && network.getstorage_pool_id().equals(getNetwork().getstorage_pool_id())
                        && !network.getId().equals(getNetwork().getId())) {
                    return new ValidationResult(VdcBllMessages.NETWORK_VLAN_IN_USE,
                            String.format("$vlanId %d", getNetwork().getvlan_id()));
                }
            }
        }

        return ValidationResult.VALID;
    }

    protected ValidationResult networkNotAttachedToCluster(final Network network) {
        return getNetworkClusterDAO().getAllForNetwork(network.getId()).isEmpty()
                ? ValidationResult.VALID
                : new ValidationResult(VdcBllMessages.NETWORK_CLUSTER_NETWORK_IN_USE);
    }

    @Override
    public List<PermissionSubject> getPermissionCheckSubjects() {
        Network network = getNetwork();
        Guid networkId = network == null ? null : network.getId();

        return Collections.singletonList(new PermissionSubject(networkId,
                VdcObjectType.Network,
                getActionType().getActionGroup()));
    }
}
