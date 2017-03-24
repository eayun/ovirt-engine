package org.ovirt.engine.core.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ovirt.engine.core.common.businessentities.VdcOption;
import org.ovirt.engine.core.common.config.Config;
import org.ovirt.engine.core.common.config.ConfigValues;
import org.ovirt.engine.core.dal.dbbroker.DbFacade;
import org.slf4j.LoggerFactory;

public class VerifyLicenseStatus {
    private static VerifyLicenseStatus instance;

    public static VerifyLicenseStatus getInstance() {
        if (instance == null) {
            instance = new VerifyLicenseStatus();
        }
        return instance;
    }
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(VerifyLicenseStatus.class);
    //验证激活状态
    public boolean getVerifyActiveState(){
        VdcOption vdcOption=DbFacade.getInstance().getVdcOptionDao().getByNameAndVersion("ActivCode", "general");
        boolean isAcive=false;
        if(vdcOption!=null&&vdcOption.getoption_value()!=null &&!"".equals(vdcOption.getoption_value())){
            String activCode= vdcOption.getoption_value();
            activCode=SecretKey.decode(activCode);
            activCode=SecretKey.to16String(activCode);
            activCode=activCode.replace("-", "");
            String UUID = VerifyLicenseStatus.getInstance().getUuid();
            UUID = UUID.replace("-", "");

            isAcive = UUID.equals(activCode);

        }
        return isAcive;

    }
    //验证过期状态
    public boolean getVerifyExpiredState(){
        String installationTime = Config.<String> getValue(ConfigValues.InstallationTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        Date currentDate=new Date();
        boolean isTimeout=false;
        try {
            date = sdf.parse(installationTime);
            int addDay = 30;//加30天
            date.setDate(date.getDate()+addDay);
            isTimeout=currentDate.after(date);
        } catch (ParseException e) {
            log.info("Time conversion failed!");
        }
        return isTimeout;
    }
    //获得UUID
    public String getUuid(){
        String UUID = null;
        File file = new File("/sys/class/dmi/id/product_uuid");
        try {
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                UUID = bufferedReader.readLine();
            } else {
                log.info("Cannot find the specified file");
            }
        } catch (Exception e) {
            log.info("An exception occurred when UUID was obtained");
        }
        return UUID;
    }
}
