
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
 *         &lt;element name="InvokeScriptResult" type="{http://www.omninet.de/OtWebSvc/v1}InvokeScriptResult" minOccurs="0"/&gt;
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
    "invokeScriptResult"
})
@XmlRootElement(name = "InvokeScriptResponse")
public class InvokeScriptResponse {

    @XmlElement(name = "InvokeScriptResult")
    protected InvokeScriptResult invokeScriptResult;

    /**
     * Gets the value of the invokeScriptResult property.
     * 
     * @return
     *     possible object is
     *     {@link InvokeScriptResult }
     *     
     */
    public InvokeScriptResult getInvokeScriptResult() {
        return invokeScriptResult;
    }

    /**
     * Sets the value of the invokeScriptResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvokeScriptResult }
     *     
     */
    public void setInvokeScriptResult(InvokeScriptResult value) {
        this.invokeScriptResult = value;
    }

}
