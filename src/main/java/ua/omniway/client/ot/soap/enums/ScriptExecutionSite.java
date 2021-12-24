
package ua.omniway.client.ot.soap.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScriptExecutionSite.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ScriptExecutionSite"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Default"/&gt;
 *     &lt;enumeration value="Server"/&gt;
 *     &lt;enumeration value="Client"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ScriptExecutionSite")
@XmlEnum
public enum ScriptExecutionSite {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Server")
    SERVER("Server"),
    @XmlEnumValue("Client")
    CLIENT("Client");
    private final String value;

    ScriptExecutionSite(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ScriptExecutionSite fromValue(String v) {
        for (ScriptExecutionSite c: ScriptExecutionSite.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
