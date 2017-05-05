package org.ovirt.engine.ui.webadmin.section.main.presenter;

import java.util.HashMap;
import java.util.Map;
import org.ovirt.engine.core.common.action.ActivCodeParameters;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.action.VdcReturnValueBase;
import org.ovirt.engine.core.common.queries.ConfigurationValues;
import org.ovirt.engine.ui.common.presenter.AbstractPopupPresenterWidget;
import org.ovirt.engine.ui.common.widget.editor.generic.StringEntityModelTextBox;
import org.ovirt.engine.ui.frontend.AsyncQuery;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.frontend.INewAsyncCallback;
import org.ovirt.engine.ui.uicommonweb.dataprovider.AsyncDataProvider;
import org.ovirt.engine.ui.uicompat.FrontendActionAsyncResult;
import org.ovirt.engine.ui.uicompat.IFrontendActionAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * Implements the WebAdmin about dialog.
 */
public class AboutPopupPresenterWidget extends AbstractPopupPresenterWidget<AboutPopupPresenterWidget.ViewDef> {

    public interface ViewDef extends AbstractPopupPresenterWidget.ViewDef {

        void setVersion(String version);

        void setPollCode(String pollCode);

        void setLicenseNotice(String notice);

        HasClickHandlers getActivatButton();

        StringEntityModelTextBox getActiveCode();
    }

    @Inject
    public AboutPopupPresenterWidget(EventBus eventBus, ViewDef view) {
        super(eventBus, view);
    }

    /*
    * LicenseNotice:
    * 00: 基础版
    * 01: 注册成功
    * 11: 已经注册
    * -10: 试用期已过
    * 1-1: 试用期内
    * -1-1: 非管理员用户
    * */

    @Override
    protected void onReveal() {
        super.onReveal();

        String vsersion=(String) AsyncDataProvider.getInstance().getConfigValuePreConverted(ConfigurationValues.EayunOSVersion);

        AsyncQuery _asyncQuery = new AsyncQuery();
        _asyncQuery.setModel(this);
        _asyncQuery.asyncCallback = new INewAsyncCallback() {
            @Override
            public void onSuccess(Object model, Object result) {
                String version = (String) result;

                getView().setVersion(version);
                getView().setLicenseNotice("00"); //$NON-NLS-1$ // 基础版

            }
        };
        AsyncDataProvider.getInstance().getRpmVersion(_asyncQuery);

        if("Enterprise".equalsIgnoreCase(vsersion)){//$NON-NLS-1$
            AsyncQuery _asyncQuery2 = new AsyncQuery();
            _asyncQuery2.setModel(this);
            _asyncQuery2.asyncCallback = new INewAsyncCallback() {
                @Override
                public void onSuccess(Object model, Object result) {
                    String data= result.toString();
                    data=data.replace(" ", "");//$NON-NLS-1$ //$NON-NLS-2$
                    data=data.substring(1, data.length()-1);
                    Map<String, String> map = new HashMap<String, String>();
                    String[] d=data.split(",");//$NON-NLS-1$
                    for (String s : d) {
                        String[] m=s.split("=");//$NON-NLS-1$
                        map.put(m[0], m[1]);
                    }

                    String isActive=map.get("isActive");//$NON-NLS-1$
                    String timeOut=map.get("timeOut");//$NON-NLS-1$
                    String code=map.get("code");//$NON-NLS-1$
                    String isSuper=map.get("isSuper");//$NON-NLS-1$
                    if("true".equals(isActive)){//$NON-NLS-1$
                        getView().setLicenseNotice("11");  //$NON-NLS-1$// 已成功
                    } else {
                        if("false".equals(isSuper)){//$NON-NLS-1$
                            getView().setLicenseNotice("-1-1");//$NON-NLS-1$
                        } else if("true".equals(timeOut)){//$NON-NLS-1$
                            getView().setLicenseNotice("-10"); //$NON-NLS-1$ // 试用过期
                        } else {
                            getView().setLicenseNotice("1-1");//$NON-NLS-1$
                        }
                    }
                    getView().setPollCode(code);

                }
            };
            AsyncDataProvider.getInstance().getUuid(_asyncQuery2);

        }

    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getActivatButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                ActivCodeParameters parameters=new ActivCodeParameters(getView().getActiveCode().getValue());

                Frontend.getInstance().runAction(VdcActionType.ActivCode,
                        parameters,
                        new IFrontendActionAsyncCallback() {
                            @Override
                            public void executed(FrontendActionAsyncResult result) {
                                result.getState();
                                VdcReturnValueBase vrvb = result.getReturnValue();
                                if (vrvb.getSucceeded()) {
                                    getView().setLicenseNotice("01"); //$NON-NLS-1$   // 注册成功
                                }
                            }
                        },
                        this);
            }

        }));
    }

}
