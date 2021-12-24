
package ua.omniway.client.ot.soap;

import ua.omniway.client.ot.soap.enums.ScriptExecutionSite;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for InvokeScriptParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvokeScriptParameters"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Parameters" type="{http://www.omninet.de/OtWebSvc/v1}ScriptParameters" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="runAt" use="required" type="{http://www.omninet.de/OtWebSvc/v1}ScriptExecutionSite" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvokeScriptParameters", propOrder = {
    "parameters"
})
public class InvokeScriptParameters {

    @XmlElement(name = "Parameters")
    protected ScriptParameters parameters;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "runAt", required = true)
    protected ScriptExecutionSite runAt;

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link ScriptParameters }
     *     
     */
    public ScriptParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptParameters }
     *     
     */
    public void setParameters(ScriptParameters value) {
        this.parameters = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the runAt property.
     * 
     * @return
     *     possible object is
     *     {@link ScriptExecutionSite }
     *     
     */
    public ScriptExecutionSite getRunAt() {
        return runAt;
    }

    /**
     * Sets the value of the runAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScriptExecutionSite }
     *     
     */
    public void setRunAt(ScriptExecutionSite value) {
        this.runAt = value;
    }

}
