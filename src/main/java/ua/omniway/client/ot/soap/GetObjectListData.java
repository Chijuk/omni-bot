
package ua.omniway.client.ot.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for GetObjectListData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetObjectListData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="Filter" type="{http://www.omninet.de/OtWebSvc/v1}Filter" minOccurs="0"/&gt;
 *           &lt;element name="ObjectIDs" type="{http://www.omninet.de/OtWebSvc/v1}ObjectIDs" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="RequiredField" type="{http://www.omninet.de/OtWebSvc/v1}RequiredField" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="folderPath" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="recursive" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="getHistory" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="getNoFields" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="minimumIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="maximumRecords" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetObjectListData", propOrder = {
    "filter",
    "objectIDs",
    "requiredField"
})
public class GetObjectListData {

    @XmlElement(name = "Filter")
    protected Filter filter;
    @XmlElement(name = "ObjectIDs")
    protected ObjectIDs objectIDs;
    @XmlElement(name = "RequiredField")
    protected List<RequiredField> requiredField;
    @XmlAttribute(name = "folderPath")
    protected String folderPath;
    @XmlAttribute(name = "recursive", required = true)
    protected boolean recursive;
    @XmlAttribute(name = "getHistory", required = true)
    protected boolean getHistory;
    @XmlAttribute(name = "getNoFields", required = true)
    protected boolean getNoFields;
    @XmlAttribute(name = "minimumIndex", required = true)
    protected int minimumIndex;
    @XmlAttribute(name = "maximumRecords", required = true)
    protected int maximumRecords;

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link Filter }
     *     
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter }
     *     
     */
    public void setFilter(Filter value) {
        this.filter = value;
    }

    /**
     * Gets the value of the objectIDs property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIDs }
     *     
     */
    public ObjectIDs getObjectIDs() {
        return objectIDs;
    }

    /**
     * Sets the value of the objectIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIDs }
     *     
     */
    public void setObjectIDs(ObjectIDs value) {
        this.objectIDs = value;
    }

    /**
     * Gets the value of the requiredField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requiredField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequiredField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequiredField }
     * 
     * 
     */
    public List<RequiredField> getRequiredField() {
        if (requiredField == null) {
            requiredField = new ArrayList<>();
        }
        return this.requiredField;
    }

    /**
     * Gets the value of the folderPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * Sets the value of the folderPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolderPath(String value) {
        this.folderPath = value;
    }

    /**
     * Gets the value of the recursive property.
     * 
     */
    public boolean isRecursive() {
        return recursive;
    }

    /**
     * Sets the value of the recursive property.
     * 
     */
    public void setRecursive(boolean value) {
        this.recursive = value;
    }

    /**
     * Gets the value of the getHistory property.
     * 
     */
    public boolean isGetHistory() {
        return getHistory;
    }

    /**
     * Sets the value of the getHistory property.
     * 
     */
    public void setGetHistory(boolean value) {
        this.getHistory = value;
    }

    /**
     * Gets the value of the getNoFields property.
     * 
     */
    public boolean isGetNoFields() {
        return getNoFields;
    }

    /**
     * Sets the value of the getNoFields property.
     * 
     */
    public void setGetNoFields(boolean value) {
        this.getNoFields = value;
    }

    /**
     * Gets the value of the minimumIndex property.
     * 
     */
    public int getMinimumIndex() {
        return minimumIndex;
    }

    /**
     * Sets the value of the minimumIndex property.
     * 
     */
    public void setMinimumIndex(int value) {
        this.minimumIndex = value;
    }

    /**
     * Gets the value of the maximumRecords property.
     * 
     */
    public int getMaximumRecords() {
        return maximumRecords;
    }

    /**
     * Sets the value of the maximumRecords property.
     * 
     */
    public void setMaximumRecords(int value) {
        this.maximumRecords = value;
    }

}
