package org.dasein.cloud.azurepack.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vmunthiu on 1/7/2016.
 */
@XmlRootElement(name="Error", namespace ="http://schemas.microsoft.com/windowsazure")
@XmlAccessorType(XmlAccessType.FIELD)
public class WAPErrorModel {
    @XmlElement(name="Code", namespace ="http://schemas.microsoft.com/windowsazure")
    private String code;
    @XmlElement(name="Message", namespace ="http://schemas.microsoft.com/windowsazure")
    private String message;
    @XmlElement(name="Severity", namespace ="http://schemas.microsoft.com/windowsazure")
    private String severity;
    @XmlElement(name="State", namespace ="http://schemas.microsoft.com/windowsazure")
    private String state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
