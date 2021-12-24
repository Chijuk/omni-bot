
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
 *         &lt;element name="ObjectID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="UseTrashbin" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IgnoreReferences" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    "objectID",
    "useTrashbin",
    "ignoreReferences"
})
@XmlRootElement(name = "RemoveObject")
public class RemoveObject {

    @XmlElement(name = "ObjectID")
    protected int objectID;
    @XmlElement(name = "UseTrashbin")
    protected boolean useTrashbin;
    @XmlElement(name = "IgnoreReferences")
    protected boolean ignoreReferences;

    /**
     * Gets the value of the objectID property.
     * 
     */
    public int getObjectID() {
        return objectID;
    }

    /**
     * Sets the value of the objectID property.
     * 
     */
    public void setObjectID(int value) {
        this.objectID = value;
    }

    /**
     * Gets the value of the useTrashbin property.
     * 
     */
    public boolean isUseTrashbin() {
        return useTrashbin;
    }

    /**
     * Sets the value of the useTrashbin property.
     * 
     */
    public void setUseTrashbin(boolean value) {
        this.useTrashbin = value;
    }

    /**
     * Gets the value of the ignoreReferences property.
     * 
     */
    public boolean isIgnoreReferences() {
        return ignoreReferences;
    }

    /**
     * Sets the value of the ignoreReferences property.
     * 
     */
    public void setIgnoreReferences(boolean value) {
        this.ignoreReferences = value;
    }

}
