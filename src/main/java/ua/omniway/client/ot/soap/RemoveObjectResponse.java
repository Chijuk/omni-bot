
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
 *         &lt;element name="RemoveObjectResult" type="{http://www.omninet.de/OtWebSvc/v1}RemoveObjectResult" minOccurs="0"/&gt;
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
    "removeObjectResult"
})
@XmlRootElement(name = "RemoveObjectResponse")
public class RemoveObjectResponse {

    @XmlElement(name = "RemoveObjectResult")
    protected RemoveObjectResult removeObjectResult;

    /**
     * Gets the value of the removeObjectResult property.
     * 
     * @return
     *     possible object is
     *     {@link RemoveObjectResult }
     *     
     */
    public RemoveObjectResult getRemoveObjectResult() {
        return removeObjectResult;
    }

    /**
     * Sets the value of the removeObjectResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoveObjectResult }
     *     
     */
    public void setRemoveObjectResult(RemoveObjectResult value) {
        this.removeObjectResult = value;
    }

}
