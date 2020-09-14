package tech.jmcs.fhm.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jmcs.fhm.jsf.security.LogoutBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class SearchBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(SearchBean.class);
    private static final long serialVersionUID = 1L;

    private String searchInputText;

    public SearchBean() {
    }

    public String search() {
        LOG.debug("Do search on string: {}", searchInputText);
        return "";
    }

    public String getSearchInputText() {
        return searchInputText;
    }

    public void setSearchInputText(String searchInputText) {
        this.searchInputText = searchInputText;
    }
}
