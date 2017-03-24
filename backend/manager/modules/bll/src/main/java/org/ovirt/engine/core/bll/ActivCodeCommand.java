package org.ovirt.engine.core.bll;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.profiles.CpuProfileHelper;
import org.ovirt.engine.core.common.action.ActivCodeParameters;
import org.ovirt.engine.core.common.businessentities.VdcOption;
import org.ovirt.engine.core.common.errors.EngineMessage;
import org.ovirt.engine.core.common.utils.SecretKey;
import org.ovirt.engine.core.common.utils.VerifyLicenseStatus;
import org.ovirt.engine.core.utils.transaction.TransactionMethod;
import org.ovirt.engine.core.utils.transaction.TransactionSupport;

public class ActivCodeCommand<T extends ActivCodeParameters> extends VmCommand<T> {

    private boolean dedicatedHostWasCleared;

    @Inject
    private CpuProfileHelper cpuProfileHelper;

    public ActivCodeCommand(T params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {


        String activCode= getParameters().getActivCode();
        if (activCode==null||"".equals(activCode)){
            return failCanDoAction(EngineMessage.ACTION_ACTIVCODE_FAILED);
        }
        try {
            activCode=SecretKey.decode(activCode);
            activCode=SecretKey.to16String(activCode);
            activCode=activCode.replace("-", "");
            String UUID = VerifyLicenseStatus.getInstance().getUuid();
            UUID = UUID.replace("-", "");
            log.info(UUID);
            log.info(activCode);

            if (UUID.equals(activCode)) {
                return true;
            } else {
                return failCanDoAction(EngineMessage.ACTION_ACTIVCODE_FAILED);
            }

        } catch (Exception e) {
            return failCanDoAction(EngineMessage.ACTION_ACTIVCODE_FAILED);
        }



    }

    @Override
    protected void executeCommand() {

                TransactionSupport.executeInNewTransaction(new TransactionMethod<Void>() {
                    @Override
                    public Void runInTransaction() {
                        saveActivCode();
                        setSucceeded(true);
                        return null;
                    }
                });
    }

    protected void saveActivCode() {
        VdcOption vdcOption=new VdcOption();

       VdcOption option=getDbFacade().getVdcOptionDao().getByNameAndVersion("ActivCode", "general");

        if(option==null){
            vdcOption.setoption_value(getParameters().getActivCode());
            vdcOption.setoption_name("ActivCode");
            vdcOption.setversion("general");
            getDbFacade().getVdcOptionDao().save(vdcOption);
        }else{
            option.setoption_value(getParameters().getActivCode());
            getDbFacade().getVdcOptionDao().update(vdcOption);
        }
    }


}
