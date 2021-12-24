
package ua.omniway.client.ot.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfModifiedAttachment complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfModifiedAttachment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ModifiedAttachment" type="{http://www.omninet.de/OtWebSvc/v1}ModifiedAttachment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfModifiedAttachment", propOrder = {
        "modifiedAttachment"
})
public class ArrayOfModifiedAttachment {

    @XmlElement(name = "ModifiedAttachment", nillable = true)
    protected List<ModifiedAttachment> modifiedAttachment;

    /**
     * Gets the value of the modifiedAttachment property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modifiedAttachment property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModifiedAttachment().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModifiedAttachment }
     */
    public List<ModifiedAttachment> getModifiedAttachment() {
        if (modifiedAttachment == null) {
            modifiedAttachment = new ArrayList<>();
        }
        return this.modifiedAttachment;
    }

}
