
package ua.omniway.client.ot.soap;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Script" type="{http://www.omninet.de/OtWebSvc/v1}InvokeScriptParameters" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "script"
})
@XmlRootElement(name = "InvokeScript")
public class InvokeScript {

    @XmlElement(name = "Script")
    protected InvokeScriptParameters script;

    /**
     * Gets the value of the script property.
     * 
     * @return
     *     possible object is
     *     {@link InvokeScriptParameters }
     *     
     */
    public InvokeScriptParameters getScript() {
        return script;
    }

    /**
     * Sets the value of the script property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvokeScriptParameters }
     *     
     */
    public void setScript(InvokeScriptParameters value) {
        this.script = value;
    }

}
