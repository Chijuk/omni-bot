
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
 *         &lt;element name="Object" type="{http://www.omninet.de/OtWebSvc/v1}ModifyObjectData" minOccurs="0"/&gt;
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
    "object"
})
@XmlRootElement(name = "ModifyObject")
public class ModifyObject {

    @XmlElement(name = "Object")
    protected ModifyObjectData object;

    /**
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     {@link ModifyObjectData }
     *     
     */
    public ModifyObjectData getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyObjectData }
     *     
     */
    public void setObject(ModifyObjectData value) {
        this.object = value;
    }

}
