
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
 *         &lt;element name="ModifyObjectResult" type="{http://www.omninet.de/OtWebSvc/v1}ModifyObjectResult" minOccurs="0"/&gt;
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
    "modifyObjectResult"
})
@XmlRootElement(name = "ModifyObjectResponse")
public class ModifyObjectResponse {

    @XmlElement(name = "ModifyObjectResult")
    protected ModifyObjectResult modifyObjectResult;

    /**
     * Gets the value of the modifyObjectResult property.
     * 
     * @return
     *     possible object is
     *     {@link ModifyObjectResult }
     *     
     */
    public ModifyObjectResult getModifyObjectResult() {
        return modifyObjectResult;
    }

    /**
     * Sets the value of the modifyObjectResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyObjectResult }
     *     
     */
    public void setModifyObjectResult(ModifyObjectResult value) {
        this.modifyObjectResult = value;
    }

}
