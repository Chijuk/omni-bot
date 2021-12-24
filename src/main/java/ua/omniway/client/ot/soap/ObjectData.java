
package ua.omniway.client.ot.soap;

import ua.omniway.client.ot.soap.types.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ObjectData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectData"&gt;
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
 *           &lt;element name="AttachmentsVal" type="{http://www.omninet.de/OtWebSvc/v1}AttachmentsVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceListVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceListVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceToUserVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceToUserVal" minOccurs="0"/&gt;
 *           &lt;element name="TimeStampedMemoVal" type="{http://www.omninet.de/OtWebSvc/v1}TimeStampedMemoVal" minOccurs="0"/&gt;
 *           &lt;element name="HistoryVal" type="{http://www.omninet.de/OtWebSvc/v1}HistoryVal" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectData", propOrder = {
        "otValues"
})
public class ObjectData {

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
        @XmlElement(name = "AttachmentsVal", type = AttachmentsVal.class),
        @XmlElement(name = "ReferenceVal", type = ReferenceVal.class),
        @XmlElement(name = "ReferenceListVal", type = ReferenceListVal.class),
        @XmlElement(name = "ReferenceToUserVal", type = ReferenceToUserVal.class),
        @XmlElement(name = "TimeStampedMemoVal", type = TimeStampedMemoVal.class),
        @XmlElement(name = "HistoryVal", type = HistoryVal.class)
    })
    protected List<Object> otValues;
    @XmlAttribute(name = "id", required = true)
    protected long id;

    /**
     * Gets the value of the nullValOrBoolValOrByteVal property.
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
     *    getOtValues().add(newItem);
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
     * {@link AttachmentsVal }
     * {@link ReferenceVal }
     * {@link ReferenceListVal }
     * {@link ReferenceToUserVal }
     * {@link TimeStampedMemoVal }
     * {@link HistoryVal }
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
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

}
