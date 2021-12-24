
package ua.omniway.client.ot.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for GetObjectListResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetObjectListResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Object" type="{http://www.omninet.de/OtWebSvc/v1}ObjectData" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="success" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="errorMsg" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="totalNumberResults" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetObjectListResult", propOrder = {
    "object"
})
public class GetObjectListResult {

    @XmlElement(name = "Object")
    protected List<ObjectData> object;
    @XmlAttribute(name = "success", required = true)
    protected boolean success;
    @XmlAttribute(name = "errorMsg")
    protected String errorMsg;
    @XmlAttribute(name = "totalNumberResults", required = true)
    protected int totalNumberResults;

    /**
     * Gets the value of the object property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the object property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectData }
     * 
     * 
     */
    public List<ObjectData> getObject() {
        if (object == null) {
            object = new ArrayList<>();
        }
        return this.object;
    }

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the errorMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets the value of the errorMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMsg(String value) {
        this.errorMsg = value;
    }

    /**
     * Gets the value of the totalNumberResults property.
     * 
     */
    public int getTotalNumberResults() {
        return totalNumberResults;
    }

    /**
     * Sets the value of the totalNumberResults property.
     * 
     */
    public void setTotalNumberResults(int value) {
        this.totalNumberResults = value;
    }

}
