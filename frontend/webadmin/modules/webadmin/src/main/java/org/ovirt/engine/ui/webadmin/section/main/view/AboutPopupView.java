package org.ovirt.engine.ui.webadmin.section.main.view;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Strong;
import org.gwtbootstrap3.client.ui.html.UnorderedList;
import org.ovirt.engine.ui.common.view.AbstractPopupView;
import org.ovirt.engine.ui.common.widget.PatternflyUiCommandButton;
import org.ovirt.engine.ui.common.widget.dialog.PopupNativeKeyPressHandler;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogButton;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogPanel;
import org.ovirt.engine.ui.common.widget.editor.generic.StringEntityModelTextBox;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationDynamicMessages;
import org.ovirt.engine.ui.webadmin.gin.AssetProvider;
import org.ovirt.engine.ui.webadmin.section.main.presenter.AboutPopupPresenterWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class AboutPopupView extends AbstractPopupView<SimpleDialogPanel> implements AboutPopupPresenterWidget.ViewDef {

    interface ViewUiBinder extends UiBinder<SimpleDialogPanel, AboutPopupView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    @UiField
    Label titleLabel;

    @UiField
    InlineLabel versionLabel;

    @UiField
    StringEntityModelTextBox activeCode;

    @UiField
    SimpleDialogButton closeButton;

    @UiField
    PatternflyUiCommandButton activatButton;

    @UiField
    HTMLPanel mainContent;

    @UiField
    HTMLPanel elseContent;

    @UiField
    InlineLabel pollCode;

    @UiField
    HTMLPanel mailContent;

    @UiField
    HTMLPanel probtStatus;

    private final ApplicationDynamicMessages dynamicMessages;

    private final static ApplicationConstants constants = AssetProvider.getConstants();


    @Inject
    public AboutPopupView(EventBus eventBus, ApplicationDynamicMessages dynamicMessages) {
        super(eventBus);
        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        this.dynamicMessages = dynamicMessages;
        initAboutMainContent();
        mainContent.setVisible(false);
        elseContent.setVisible(false);
        localize();
    }

    void localize() {
        closeButton.setText(constants.closeButtonLabel());
        titleLabel.setText(constants.aboutPopupCaption());
        activeCode.getElement().setPropertyString("placeholder", "请输入您的激活码");//$NON-NLS-1$//$NON-NLS-2$
    }

    private void initAboutMainContent() {
        mailContent.add(fetchRegCode());
    }

    @Override
    public void setVersion(String text) {
        versionLabel.setText("Fusionstack 超融合管理平台：企业版");//$NON-NLS-1$
    }

    @Override
    public HasClickHandlers getCloseButton() {
        return closeButton;
    }

    @Override
    public HasClickHandlers getCloseIconButton() {
        return asWidget().getCloseIconButton();
    }

    @Override
    public void setPopupKeyPressHandler(PopupNativeKeyPressHandler handler) {
        asWidget().setKeyPressHandler(handler);
    }

    @Override
    public void setPollCode(String text) {
        pollCode.setText(text);
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
    public void setLicenseNotice(String text) {
        if ("01".equals(text)) {//$NON-NLS-1$
            releaseElseContent(regSuccess(), stylePanel(serviceInfoPanel(), "pull-right", "abt-ser-right"));//$NON-NLS-1$//$NON-NLS-2$
        } else if ("11".equals(text)) {//$NON-NLS-1$
            releaseElseContent(doneReg(), stylePanel(serviceInfoPanel(), "pull-right", "abt-ser-done"));//$NON-NLS-1$//$NON-NLS-2$
        } else if ("1-1".equals(text)) {//$NON-NLS-1$
            probtStatus.clear();
            probtStatus.add(probationStatus(true));
            releaseMainContent();
        } else if ("-10".equals(text)) {//$NON-NLS-1$
            probtStatus.clear();
            probtStatus.add(probationStatus(false));
            releaseMainContent();
        } else if ("-1-1".equals(text)) {//$NON-NLS-1$
            releaseElseContent(nonAdminInfo(), stylePanel(serviceInfoPanel(), "pull-right", "abt-ser-right"));//$NON-NLS-1$//$NON-NLS-2$
        } else if ("00".equals(text)) {//$NON-NLS-1$
            releaseElseContent(basicInfo());
        } else {
            // done nothing
        }
    }


    /* regMainContent, regSuccessContent, doneRegContent, basicContent, nonAdminContent   */
    @Override
    public HasClickHandlers getActivatButton() {
        return activatButton;
    }

    @Override
    public StringEntityModelTextBox getActiveCode() {
        return activeCode;
    }

    private Div serviceInfoPanel() {
        Div panel = new Div();
        panel.addStyleName("abt-ser-font");//$NON-NLS-1$
        Paragraph p1 = new Paragraph();
        p1.setText("遇到问题？我们的技术人员为您服务");//$NON-NLS-1$
        p1.setMarginBottom(10);
        panel.add(p1);
        Paragraph p2 = new Paragraph();
        p2.setText("电话： 400-6066-396");//$NON-NLS-1$
        panel.add(p2);
        Paragraph p3 = new Paragraph();
        p3.setText("邮箱： service@eayun.com");//$NON-NLS-1$
        panel.add(p3);
        Paragraph p4 = new Paragraph();
        p4.setText("易云捷讯科技（北京）股份有限公司");//$NON-NLS-1$
        panel.add(p4);
        return panel;
    }

    private Div stylePanel(Div div, String... style) {
        if (style != null && !"".equals(style)) {
            for (String s : style) {
                div.addStyleName(s);
            }
        }
        return div;
    }

    private Div doneReg() {
        Div panel = new Div();
        panel.addStyleName("abt-done-suc");//$NON-NLS-1$
        Heading h1 = new Heading(HeadingSize.H2);
        h1.setText("欢迎使用！");//$NON-NLS-1$
        panel.add(h1);
        Paragraph p1 = new Paragraph();
        p1.setText("Fusionstack超融合管理平台");//$NON-NLS-1$
        p1.setMarginBottom(30);
        panel.add(p1);
        Heading h2 = new Heading(HeadingSize.H3);
        h2.setText("您已经成功激活可以正常使用此产品。");//$NON-NLS-1$
        panel.add(h2);
        return panel;
    }

    private Div basicInfo() {
        Div panel = new Div();
        panel.addStyleName("abt-basic-info");//$NON-NLS-1$
        Heading h1 = new Heading(HeadingSize.H2);
        h1.setText("欢迎使用！");//$NON-NLS-1$
        panel.add(h1);
        Paragraph p1 = new Paragraph();
        p1.setText("Fusionstack超融合管理平台");//$NON-NLS-1$
        p1.setMarginBottom(30);
        panel.add(p1);
        return panel;
    }

    private Div nonAdminInfo() {
        Div panel = new Div();
        panel.addStyleName("abt-non-admin");//$NON-NLS-1$
        StringBuffer buf = new StringBuffer();
        buf.append("非");//$NON-NLS-1$
        buf.append("<span style=\"color: #BB2D1D;\">SuperUser</span>");//$NON-NLS-1$
        buf.append("角色用户！");//$NON-NLS-1$
        Strong b1 = new Strong();
        b1.setHTML(buf.toString());
        panel.add(b1);
        Paragraph p2 = new Paragraph();
        p2.setText("不能激活本系统，请使用 admin 登录或联系系统管理员！");//$NON-NLS-1$
        panel.add(p2);
        return panel;
    }

    private Div regSuccess() {
        Div panel = new Div();
        panel.addStyleName("abt-reg-suc"); //$NON-NLS-1$
        Heading h1 = new Heading(HeadingSize.H2);
        h1.setText("恭喜激活成功！");//$NON-NLS-1$
        panel.add(h1);
        Paragraph p1 = new Paragraph();
        p1.setText("您已成功激活可以正常使用此产品。");//$NON-NLS-1$
        p1.setMarginTop(30);
        panel.add(p1);
        return panel;
    }

    private Div fetchRegCode(){
        Div panel = new Div();
        panel.setMarginTop(40);
        Heading h1 = new Heading(HeadingSize.H2);
        h1.setText("如何获取激活码？");//$NON-NLS-1$
        panel.add(h1);
        Paragraph p1 = new Paragraph();
        p1.setText("请您将下列信息，通过电子邮件发送至华云网际");//$NON-NLS-1$
        panel.add(p1);
        UnorderedList list = new UnorderedList();
        list.setMarginTop(20);
        ListItem li1 = new ListItem();
        li1.setMarginBottom(5);
        li1.setText("本系统License注册码");//$NON-NLS-1$
        list.add(li1);

        ListItem li2 = new ListItem();
        li2.setMarginBottom(5);
        li2.setText("最终客户公司名称");//$NON-NLS-1$
        list.add(li2);
        ListItem li3 = new ListItem();

        li3.setMarginBottom(5);
        li3.setText("申请联系人姓名");//$NON-NLS-1$
        list.add(li3);

        ListItem li4 = new ListItem();
        li4.setMarginBottom(5);
        li4.setText("申请联系人邮箱");//$NON-NLS-1$
        list.add(li4);

        ListItem li5 = new ListItem();
        li5.setMarginBottom(5);
        li5.setText("购买的CPU个数");//$NON-NLS-1$
        list.add(li5);
        panel.add(list);

        Paragraph p2 = new Paragraph();
        p2.setText("我们收到您的邮件后1个工作日内，");//$NON-NLS-1$
        panel.add(p2);

        Paragraph p3 = new Paragraph();
        p3.setText("将激活码通过邮件发送给您！");//$NON-NLS-1$
        panel.add(p3);
        return panel;
    }

    private Strong probationStatus(boolean isDur) {
        StringBuffer buf = new StringBuffer();
        buf.append("30天免费试用期： ");//$NON-NLS-1$
        if (isDur) {
            buf.append("<span style=\"color: #0c9073;\">使用中</span>");//$NON-NLS-1$
        } else {
            buf.append("<span style=\"color: #BB2D1D;\">已过期</span>");//$NON-NLS-1$
        }
        Strong s1 = new Strong();
        s1.setHTML(buf.toString());
        return s1;
    }

    private void releaseElseContent(Div... div) {
        mainContent.setVisible(false);
        if (div != null && div.length > 0) {
            elseContent.setVisible(false);
            elseContent.clear();
            for (Div w : div) {
                elseContent.add(w);
            }
            elseContent.setVisible(true);
        }
    }

    public void releaseMainContent() {
        elseContent.setVisible(false);
        mainContent.setVisible(true);
        elseContent.clear();
    }
}
