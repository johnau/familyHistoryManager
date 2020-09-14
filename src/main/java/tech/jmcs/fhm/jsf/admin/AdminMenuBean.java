package tech.jmcs.fhm.jsf.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class AdminMenuBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(AdminMenuBean.class);
    private static final long serialVersionUID = 1L;

    @Inject
    private ExternalContext externalContext;

    public String newFamilyMember() {
        LOG.debug("New Family Member Action");
        return "";
    }

    public String chooseFamilyMemberForEdit() {
        LOG.debug("Choose Family Member For Edit Action");
        return "";
    }

    public String chooseFamilyMemberForDelete() {
        LOG.debug("Choose Family Member For Delete Action");
        return "";
    }

    public String viewFamilyMembers() {
        LOG.debug("View Family Members Action");
        return "";
    }

    public String newArticle() {
        LOG.debug("New Article Action");
        return "";
    }

    public String editArticle() {
        LOG.debug("Edit Article Action");
        return "";
    }

    public String deleteArticle() {
        LOG.debug("Delete Article Action");
        return "";
    }

    public String viewArticles() {
        LOG.debug("Create Article Link Action");
        return "";
    }



//
//    private void buildFamilyMemberMenuItems() {
//        DefaultMenuItem fm_new = new DefaultMenuItem("New");
//        fm_new.setAjax(false);
//        fm_new.setCommand("admin/create-family-member");
//        fm_new.setIcon("pi pi-user-plus");
//        familyMemberMenuItems.add(fm_new);
//
//        DefaultMenuItem fm_edit = new DefaultMenuItem("Edit");
//        fm_edit.setAjax(false);
//        fm_edit.setCommand("#{adminMenuBean.familyMemberEdit}");
//        fm_edit.setAjax(true);
//        fm_edit.setIcon("pi pi-user-edit");
//
//    }
//
//    private void buildArticleMenuItems() {
//
//    }
//
//    public String familyMemberEdit() {
//        String appContextPath = externalContext.getApplicationContextPath();
//
//        Map<String, String> requestParams = externalContext.getRequestParameterMap();
//
//        LOG.info("App Context Path is: {} with {} params", appContextPath, requestParams.size());
//    }

}
