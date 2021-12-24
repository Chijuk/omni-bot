
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
 *         &lt;element name="GetObjectListResult" type="{http://www.omninet.de/OtWebSvc/v1}GetObjectListResult" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getObjectListResult"
})
@XmlRootElement(name = "GetObjectListResponse")
public class GetObjectListResponse {

    @XmlElement(name = "GetObjectListResult")
    protected GetObjectListResult getObjectListResult;

    /**
     * Gets the value of the getObjectListResult property.
     *
     * @return possible object is
     * {@link GetObjectListResult }
     */
    public GetObjectListResult getGetObjectListResult() {
        return getObjectListResult;
    }

    /**
     * Sets the value of the getObjectListResult property.
     *
     * @param value allowed object is
     *              {@link GetObjectListResult }
     */
    public void setGetObjectListResult(GetObjectListResult value) {
        this.getObjectListResult = value;
    }

}
