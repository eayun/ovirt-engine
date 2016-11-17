package org.ovirt.engine.ui.uicommonweb.models.hosts;

import java.util.HashMap;
import java.util.Map;

import org.ovirt.engine.ui.uicommonweb.models.ListModel;
import org.ovirt.engine.ui.uicompat.CommonApplicationConstantsWithLookup;
import org.ovirt.engine.ui.uicompat.CommonApplicationConstantsWithLookupManager;

/**
 * Created by wxt on 11/11/16.
 */
public class FenceProxyUtils {

    static {
        inintProxyMap();
    }

    private static CommonApplicationConstantsWithLookup constantsWithLookup =
            CommonApplicationConstantsWithLookupManager.getInstance().getCommonApplicationConstantsWithLookup();

    private static Map<String, String> proxyMap;

    private static String getProxyValue(String key) {
        if (proxyMap != null && proxyMap.containsKey(key)) {
            return proxyMap.get(key);
        }
        return key;
    }

    private static String getConstantsValue(String key) {
        if (key == null || "".equals(key)) {
            return "";
        } else {
            try {
                return constantsWithLookup.getString(key);
            } catch (Exception e) {
                java.util.logging.Logger.getLogger(HostModel.class.getName())
                        .warning("Can't find " + constantsWithLookup.getClass().getName() + "." + key);//$NON-NLS-1$//$NON-NLS-2$
                return key;
            }
        }
    }

    public static void localeFenceProxy(ListModel<FenceProxyModel> fenceProxyModels) {

        if (fenceProxyModels != null && fenceProxyModels.getItems() != null) {
            for (FenceProxyModel model : fenceProxyModels.getItems()) {
                String value = model.getEntity().getValue();
                model.getEntity().setValue(getConstantsValue(value)); // 国际化
            }
        }
    }

    public static void setRealFenceProxy(FenceProxyModel model) {
        if (proxyMap.containsKey(model.getEntity().getValue())) {
            model.getEntity().setValue(proxyMap.get(model.getEntity().getValue()));// 国际化字符串 -> 实际值
        }
    }

    // The proxyMap must include CommonApplicationConstantsWithLookup and CommonApplicationConstantsWithLookup_zh_CN
    private static void inintProxyMap() {
        proxyMap = new HashMap<>();
        proxyMap.put("cluster", "cluster");//$NON-NLS-1$//$NON-NLS-2$
        proxyMap.put("同一集群", "cluster");//$NON-NLS-1$//$NON-NLS-2$
        proxyMap.put("dc", "dc");//$NON-NLS-1$//$NON-NLS-2$
        proxyMap.put("同一数据中心", "dc");//$NON-NLS-1$//$NON-NLS-2$
        proxyMap.put("other_dc", "other_dc");//$NON-NLS-1$//$NON-NLS-2$
        proxyMap.put("其他数据中心", "other_dc");//$NON-NLS-1$//$NON-NLS-2$
    }
}
