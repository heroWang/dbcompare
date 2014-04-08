package com.sohu.assist.dbcompare.util;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.ValidationEventCollector;

/**
 * @author hawkinswang
 */
public class JAXBValidator extends ValidationEventCollector {
    /*
     * 当XML验证异常时调用
     */
    @Override
    public boolean handleEvent(ValidationEvent event) {
        if (event.getSeverity() == ValidationEvent.ERROR || event.getSeverity() == ValidationEvent.FATAL_ERROR) {
            ValidationEventLocator locator = event.getLocator();
            // change RuntimeException to something more appropriate
            throw new RuntimeException("XML验证失败:" + event.getMessage() + " at row: "
                    + locator.getLineNumber() + " column: " + locator.getColumnNumber());
        }

        return true;
    }
}
