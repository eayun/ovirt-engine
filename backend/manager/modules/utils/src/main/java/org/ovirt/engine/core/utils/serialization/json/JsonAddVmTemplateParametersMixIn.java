package org.ovirt.engine.core.utils.serialization.json;

import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.ovirt.engine.core.common.action.AddVmTemplateParameters;
import org.ovirt.engine.core.common.businessentities.VM;
import org.ovirt.engine.core.common.businessentities.storage.DiskImage;
import org.ovirt.engine.core.compat.Guid;

@SuppressWarnings("serial")
@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY)
public abstract class JsonAddVmTemplateParametersMixIn extends AddVmTemplateParameters {

    @JsonIgnore
    @Override
    public abstract HashMap<Guid, DiskImage> getDiskInfoDestinationMap();

    @JsonIgnore
    @Override
    public abstract VM getVm();

}
