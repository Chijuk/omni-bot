
package ua.omniway.client.ot.soap;

import ua.omniway.client.ot.soap.types.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Filter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Filter"&gt;
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
 *           &lt;element name="ReferenceVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceVal" minOccurs="0"/&gt;
 *           &lt;element name="ReferenceToUserVal" type="{http://www.omninet.de/OtWebSvc/v1}ReferenceToUserVal" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
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
@XmlType(name = "Filter", propOrder = {
    "content"
})
public class Filter {

    @XmlElementRefs({
        @XmlElementRef(name = "NullVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "BoolVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ByteVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ShortIntVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "LongIntVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "SingleVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "DoubleVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "DateTimeVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "StringVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "CurrencyVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ReferenceVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ReferenceToUserVal", namespace = "http://www.omninet.de/OtWebSvc/v1", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link NullVal }{@code >}
     * {@link JAXBElement }{@code <}{@link BoolVal }{@code >}
     * {@link JAXBElement }{@code <}{@link ByteVal }{@code >}
     * {@link JAXBElement }{@code <}{@link ShortIntVal }{@code >}
     * {@link JAXBElement }{@code <}{@link LongIntVal }{@code >}
     * {@link JAXBElement }{@code <}{@link SingleVal }{@code >}
     * {@link JAXBElement }{@code <}{@link DoubleVal }{@code >}
     * {@link JAXBElement }{@code <}{@link DateTimeVal }{@code >}
     * {@link JAXBElement }{@code <}{@link StringVal }{@code >}
     * {@link JAXBElement }{@code <}{@link CurrencyVal }{@code >}
     * {@link JAXBElement }{@code <}{@link ReferenceVal }{@code >}
     * {@link JAXBElement }{@code <}{@link ReferenceToUserVal }{@code >}
     * {@link String }
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return this.content;
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
