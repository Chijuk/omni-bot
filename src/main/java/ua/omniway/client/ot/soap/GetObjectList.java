
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
 *         &lt;element name="Get" type="{http://www.omninet.de/OtWebSvc/v1}GetObjectListData" minOccurs="0"/&gt;
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
    "get"
})
@XmlRootElement(name = "GetObjectList")
public class GetObjectList {

    @XmlElement(name = "Get")
    protected GetObjectListData get;

    /**
     * Gets the value of the get property.
     * 
     * @return
     *     possible object is
     *     {@link GetObjectListData }
     *     
     */
    public GetObjectListData getGet() {
        return get;
    }

    /**
     * Sets the value of the get property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetObjectListData }
     *     
     */
    public void setGet(GetObjectListData value) {
        this.get = value;
    }

}
