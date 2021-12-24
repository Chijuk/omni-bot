
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
 *         &lt;element name="AddObjectResult" type="{http://www.omninet.de/OtWebSvc/v1}AddObjectResult" minOccurs="0"/&gt;
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
    "addObjectResult"
})
@XmlRootElement(name = "AddObjectResponse")
public class AddObjectResponse {

    @XmlElement(name = "AddObjectResult")
    protected AddObjectResult addObjectResult;

    /**
     * Gets the value of the addObjectResult property.
     * 
     * @return
     *     possible object is
     *     {@link AddObjectResult }
     *     
     */
    public AddObjectResult getAddObjectResult() {
        return addObjectResult;
    }

    /**
     * Sets the value of the addObjectResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddObjectResult }
     *     
     */
    public void setAddObjectResult(AddObjectResult value) {
        this.addObjectResult = value;
    }

}
