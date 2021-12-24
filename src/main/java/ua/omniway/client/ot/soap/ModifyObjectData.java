
package ua.omniway.client.ot.soap;

import ua.omniway.client.ot.soap.types.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ModifyObjectData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModifyObjectData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="NullVal" type="{http://www.omninet.de/OtWebSvc/v1}NullVal" minOccurs="0"/&gt;
 *           &lt;element name="BoolVal" type="{http://www.omninet.de/OtWebSvc/v1}BoolVal" minOccurs="0"/&gt;
 *           &lt;element name="ByteVal" type="{http://www.omninet.de/OtWebSvc/v1}ByteVal" minOccurs="0"/&gt;
 *           &lt;element name="ShortIntVal" type="{http://www.omninet.de/OtWebSvc/v1}ShortIntVal" minOccurs="0"/&gt;
 *           &lt;element name="LongIntVal" type="{http://www.omninet.de/OtWebSvc/v1}LongIntVal" minOccurs="0"/&gt;
 *           &lt;element name="SingleVal" type="{http://www.omninet.de/OtWebSvc/v1}SingleVal" minOccurs="0"/&gt;
 *           &lt;element name="DoubleVal" type="{http://www.omninet.de/OtWebSvc/v1}DoubleVal" minOccurs="0"/&gt;
 *           &lt;element name="DateTimeVal" type="{http://www.omninet.de/OtWebSvc/v1}DateTimeVal" minOccurs="0"/&gt;
 *           &lt;element name="StringVal" type="{http://www.omninet.de/OtWebSvc/v1}StringVal" minOccurs="0"/&gt;
 *           &lt;element name="CurrencyVal" type="{http://www.omninet.de/OtWebSvc/v1}CurrencyVal" minOccurs="0"/&gt;
 *           &lt;element name="AttachmentModificationsVal" type="{http://www.omninet.de/OtWebSvc/v1}AttachmentModificationsVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceListModificationsVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceListModificationsVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceToUserVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceToUserVal" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="objectId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="saveExFlags" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="username" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="password" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModifyObjectData", propOrder = {
        "otValues"
})
public class ModifyObjectData {

    @XmlElements({
        @XmlElement(name = "NullVal", type = NullVal.class),
        @XmlElement(name = "BoolVal", type = BoolVal.class),
        @XmlElement(name = "ByteVal", type = ByteVal.class),
        @XmlElement(name = "ShortIntVal", type = ShortIntVal.class),
        @XmlElement(name = "LongIntVal", type = LongIntVal.class),
        @XmlElement(name = "SingleVal", type = SingleVal.class),
        @XmlElement(name = "DoubleVal", type = DoubleVal.class),
        @XmlElement(name = "DateTimeVal", type = DateTimeVal.class),
        @XmlElement(name = "StringVal", type = StringVal.class),
        @XmlElement(name = "CurrencyVal", type = CurrencyVal.class),
        @XmlElement(name = "AttachmentModificationsVal", type = AttachmentModificationsVal.class),
        @XmlElement(name = "ReferenceVal", type = ReferenceVal.class),
        @XmlElement(name = "ReferenceListModificationsVal", type = ReferenceListModificationsVal.class),
        @XmlElement(name = "ReferenceToUserVal", type = ReferenceToUserVal.class)
    })
    protected List<Object> otValues;
    @XmlAttribute(name = "objectId", required = true)
    protected long objectId;
    @XmlAttribute(name = "saveExFlags", required = true)
    protected int saveExFlags;
    @XmlAttribute(name = "username")
    protected String username;
    @XmlAttribute(name = "password")
    protected String password;

    /**
     * Gets the value of the otValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nullValOrBoolValOrByteVal property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNullValOrBoolValOrByteVal().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NullVal }
     * {@link BoolVal }
     * {@link ByteVal }
     * {@link ShortIntVal }
     * {@link LongIntVal }
     * {@link SingleVal }
     * {@link DoubleVal }
     * {@link DateTimeVal }
     * {@link StringVal }
     * {@link CurrencyVal }
     * {@link AttachmentModificationsVal }
     * {@link ReferenceVal }
     * {@link ReferenceListModificationsVal }
     * {@link ReferenceToUserVal }
     * 
     * 
     */
    public List<Object> getOtValues() {
        if (otValues == null) {
            otValues = new ArrayList<>();
        }
        return this.otValues;
    }

    /**
     * Gets the value of the objectId property.
     * 
     */
    public long getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     */
    public void setObjectId(long value) {
        this.objectId = value;
    }

    /**
     * Gets the value of the saveExFlags property.
     * 
     */
    public int getSaveExFlags() {
        return saveExFlags;
    }

    /**
     * Sets the value of the saveExFlags property.
     * 
     */
    public void setSaveExFlags(int value) {
        this.saveExFlags = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
