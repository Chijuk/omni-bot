
package ua.omniway.client.ot.soap.types;

import ua.omniway.client.ot.soap.ArrayOfAddedAttachment;
import ua.omniway.client.ot.soap.ArrayOfID;
import ua.omniway.client.ot.soap.ArrayOfModifiedAttachment;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AttachmentModificationsVal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachmentModificationsVal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AddedAttachments" type="{http://www.omninet.de/OtWebSvc/v1}ArrayOfAddedAttachment" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedAttachments" type="{http://www.omninet.de/OtWebSvc/v1}ArrayOfModifiedAttachment" minOccurs="0"/&gt;
 *         &lt;element name="DeletedAttachments" type="{http://www.omninet.de/OtWebSvc/v1}ArrayOfID" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachmentModificationsVal", propOrder = {
    "addedAttachments",
    "modifiedAttachments",
    "deletedAttachments"
})
public class AttachmentModificationsVal {

    @XmlElement(name = "AddedAttachments")
    protected ArrayOfAddedAttachment addedAttachments;
    @XmlElement(name = "ModifiedAttachments")
    protected ArrayOfModifiedAttachment modifiedAttachments;
    @XmlElement(name = "DeletedAttachments")
    protected ArrayOfID deletedAttachments;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the addedAttachments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAddedAttachment }
     *     
     */
    public ArrayOfAddedAttachment getAddedAttachments() {
        return addedAttachments;
    }

    /**
     * Sets the value of the addedAttachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAddedAttachment }
     *     
     */
    public void setAddedAttachments(ArrayOfAddedAttachment value) {
        this.addedAttachments = value;
    }

    /**
     * Gets the value of the modifiedAttachments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfModifiedAttachment }
     *     
     */
    public ArrayOfModifiedAttachment getModifiedAttachments() {
        return modifiedAttachments;
    }

    /**
     * Sets the value of the modifiedAttachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfModifiedAttachment }
     *     
     */
    public void setModifiedAttachments(ArrayOfModifiedAttachment value) {
        this.modifiedAttachments = value;
    }

    /**
     * Gets the value of the deletedAttachments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfID }
     *     
     */
    public ArrayOfID getDeletedAttachments() {
        return deletedAttachments;
    }

    /**
     * Sets the value of the deletedAttachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfID }
     *     
     */
    public void setDeletedAttachments(ArrayOfID value) {
        this.deletedAttachments = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
