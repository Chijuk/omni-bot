
package ua.omniway.client.ot.soap;

import ua.omniway.client.ot.soap.types.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ua.omniway.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FilterNullVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "NullVal");
    private final static QName _FilterBoolVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "BoolVal");
    private final static QName _FilterByteVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "ByteVal");
    private final static QName _FilterShortIntVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "ShortIntVal");
    private final static QName _FilterLongIntVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "LongIntVal");
    private final static QName _FilterSingleVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "SingleVal");
    private final static QName _FilterDoubleVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "DoubleVal");
    private final static QName _FilterDateTimeVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "DateTimeVal");
    private final static QName _FilterStringVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "StringVal");
    private final static QName _FilterCurrencyVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "CurrencyVal");
    private final static QName _FilterReferenceVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "ReferenceVal");
    private final static QName _FilterReferenceToUserVal_QNAME = new QName("http://www.omninet.de/OtWebSvc/v1", "ReferenceToUserVal");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ua.omniway.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddObject }
     * 
     */
    public AddObject createAddObject() {
        return new AddObject();
    }

    /**
     * Create an instance of {@link AddObjectData }
     * 
     */
    public AddObjectData createAddObjectData() {
        return new AddObjectData();
    }

    /**
     * Create an instance of {@link AddObjectResponse }
     * 
     */
    public AddObjectResponse createAddObjectResponse() {
        return new AddObjectResponse();
    }

    /**
     * Create an instance of {@link AddObjectResult }
     * 
     */
    public AddObjectResult createAddObjectResult() {
        return new AddObjectResult();
    }

    /**
     * Create an instance of {@link ModifyObject }
     * 
     */
    public ModifyObject createModifyObject() {
        return new ModifyObject();
    }

    /**
     * Create an instance of {@link ModifyObjectData }
     * 
     */
    public ModifyObjectData createModifyObjectData() {
        return new ModifyObjectData();
    }

    /**
     * Create an instance of {@link ModifyObjectResponse }
     * 
     */
    public ModifyObjectResponse createModifyObjectResponse() {
        return new ModifyObjectResponse();
    }

    /**
     * Create an instance of {@link ModifyObjectResult }
     * 
     */
    public ModifyObjectResult createModifyObjectResult() {
        return new ModifyObjectResult();
    }

    /**
     * Create an instance of {@link RemoveObject }
     * 
     */
    public RemoveObject createRemoveObject() {
        return new RemoveObject();
    }

    /**
     * Create an instance of {@link RemoveObjectResponse }
     * 
     */
    public RemoveObjectResponse createRemoveObjectResponse() {
        return new RemoveObjectResponse();
    }

    /**
     * Create an instance of {@link RemoveObjectResult }
     * 
     */
    public RemoveObjectResult createRemoveObjectResult() {
        return new RemoveObjectResult();
    }

    /**
     * Create an instance of {@link InvokeScript }
     * 
     */
    public InvokeScript createInvokeScript() {
        return new InvokeScript();
    }

    /**
     * Create an instance of {@link InvokeScriptParameters }
     * 
     */
    public InvokeScriptParameters createInvokeScriptParameters() {
        return new InvokeScriptParameters();
    }

    /**
     * Create an instance of {@link InvokeScriptResponse }
     * 
     */
    public InvokeScriptResponse createInvokeScriptResponse() {
        return new InvokeScriptResponse();
    }

    /**
     * Create an instance of {@link InvokeScriptResult }
     * 
     */
    public InvokeScriptResult createInvokeScriptResult() {
        return new InvokeScriptResult();
    }

    /**
     * Create an instance of {@link GetObjectList }
     * 
     */
    public GetObjectList createGetObjectList() {
        return new GetObjectList();
    }

    /**
     * Create an instance of {@link GetObjectListData }
     * 
     */
    public GetObjectListData createGetObjectListData() {
        return new GetObjectListData();
    }

    /**
     * Create an instance of {@link GetObjectListResponse }
     * 
     */
    public GetObjectListResponse createGetObjectListResponse() {
        return new GetObjectListResponse();
    }

    /**
     * Create an instance of {@link GetObjectListResult }
     * 
     */
    public GetObjectListResult createGetObjectListResult() {
        return new GetObjectListResult();
    }

    /**
     * Create an instance of {@link NullVal }
     * 
     */
    public NullVal createNullVal() {
        return new NullVal();
    }

    /**
     * Create an instance of {@link BoolVal }
     * 
     */
    public BoolVal createBoolVal() {
        return new BoolVal();
    }

    /**
     * Create an instance of {@link ByteVal }
     * 
     */
    public ByteVal createByteVal() {
        return new ByteVal();
    }

    /**
     * Create an instance of {@link ShortIntVal }
     * 
     */
    public ShortIntVal createShortIntVal() {
        return new ShortIntVal();
    }

    /**
     * Create an instance of {@link LongIntVal }
     * 
     */
    public LongIntVal createLongIntVal() {
        return new LongIntVal();
    }

    /**
     * Create an instance of {@link SingleVal }
     * 
     */
    public SingleVal createSingleVal() {
        return new SingleVal();
    }

    /**
     * Create an instance of {@link DoubleVal }
     * 
     */
    public DoubleVal createDoubleVal() {
        return new DoubleVal();
    }

    /**
     * Create an instance of {@link DateTimeVal }
     * 
     */
    public DateTimeVal createDateTimeVal() {
        return new DateTimeVal();
    }

    /**
     * Create an instance of {@link StringVal }
     * 
     */
    public StringVal createStringVal() {
        return new StringVal();
    }

    /**
     * Create an instance of {@link CurrencyVal }
     * 
     */
    public CurrencyVal createCurrencyVal() {
        return new CurrencyVal();
    }

    /**
     * Create an instance of {@link AddedAttachmentsVal }
     * 
     */
    public AddedAttachmentsVal createAddedAttachmentsVal() {
        return new AddedAttachmentsVal();
    }

    /**
     * Create an instance of {@link ArrayOfAddedAttachment }
     * 
     */
    public ArrayOfAddedAttachment createArrayOfAddedAttachment() {
        return new ArrayOfAddedAttachment();
    }

    /**
     * Create an instance of {@link AddedAttachment }
     * 
     */
    public AddedAttachment createAddedAttachment() {
        return new AddedAttachment();
    }

    /**
     * Create an instance of {@link ReferenceVal }
     * 
     */
    public ReferenceVal createReferenceVal() {
        return new ReferenceVal();
    }

    /**
     * Create an instance of {@link ReferenceListVal }
     * 
     */
    public ReferenceListVal createReferenceListVal() {
        return new ReferenceListVal();
    }

    /**
     * Create an instance of {@link ReferenceToUserVal }
     * 
     */
    public ReferenceToUserVal createReferenceToUserVal() {
        return new ReferenceToUserVal();
    }

    /**
     * Create an instance of {@link AttachmentModificationsVal }
     * 
     */
    public AttachmentModificationsVal createAttachmentModificationsVal() {
        return new AttachmentModificationsVal();
    }

    /**
     * Create an instance of {@link ArrayOfModifiedAttachment }
     * 
     */
    public ArrayOfModifiedAttachment createArrayOfModifiedAttachment() {
        return new ArrayOfModifiedAttachment();
    }

    /**
     * Create an instance of {@link ModifiedAttachment }
     * 
     */
    public ModifiedAttachment createModifiedAttachment() {
        return new ModifiedAttachment();
    }

    /**
     * Create an instance of {@link ArrayOfID }
     * 
     */
    public ArrayOfID createArrayOfID() {
        return new ArrayOfID();
    }

    /**
     * Create an instance of {@link ID }
     * 
     */
    public ID createID() {
        return new ID();
    }

    /**
     * Create an instance of {@link ReferenceListModificationsVal }
     * 
     */
    public ReferenceListModificationsVal createReferenceListModificationsVal() {
        return new ReferenceListModificationsVal();
    }

    /**
     * Create an instance of {@link ScriptParameters }
     * 
     */
    public ScriptParameters createScriptParameters() {
        return new ScriptParameters();
    }

    /**
     * Create an instance of {@link Filter }
     * 
     */
    public Filter createFilter() {
        return new Filter();
    }

    /**
     * Create an instance of {@link ObjectIDs }
     * 
     */
    public ObjectIDs createObjectIDs() {
        return new ObjectIDs();
    }

    /**
     * Create an instance of {@link RequiredField }
     * 
     */
    public RequiredField createRequiredField() {
        return new RequiredField();
    }

    /**
     * Create an instance of {@link ObjectData }
     * 
     */
    public ObjectData createObjectData() {
        return new ObjectData();
    }

    /**
     * Create an instance of {@link AttachmentsVal }
     * 
     */
    public AttachmentsVal createAttachmentsVal() {
        return new AttachmentsVal();
    }

    /**
     * Create an instance of {@link ArrayOfAttachment }
     * 
     */
    public ArrayOfAttachment createArrayOfAttachment() {
        return new ArrayOfAttachment();
    }

    /**
     * Create an instance of {@link Attachment }
     * 
     */
    public Attachment createAttachment() {
        return new Attachment();
    }

    /**
     * Create an instance of {@link TimeStampedMemoVal }
     * 
     */
    public TimeStampedMemoVal createTimeStampedMemoVal() {
        return new TimeStampedMemoVal();
    }

    /**
     * Create an instance of {@link TimeStampedMemoSection }
     * 
     */
    public TimeStampedMemoSection createTimeStampedMemoSection() {
        return new TimeStampedMemoSection();
    }

    /**
     * Create an instance of {@link HistoryVal }
     * 
     */
    public HistoryVal createHistoryVal() {
        return new HistoryVal();
    }

    /**
     * Create an instance of {@link HistoryItem }
     * 
     */
    public HistoryItem createHistoryItem() {
        return new HistoryItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NullVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NullVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "NullVal", scope = Filter.class)
    public JAXBElement<NullVal> createFilterNullVal(NullVal value) {
        return new JAXBElement<NullVal>(_FilterNullVal_QNAME, NullVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BoolVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BoolVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "BoolVal", scope = Filter.class)
    public JAXBElement<BoolVal> createFilterBoolVal(BoolVal value) {
        return new JAXBElement<BoolVal>(_FilterBoolVal_QNAME, BoolVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ByteVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ByteVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "ByteVal", scope = Filter.class)
    public JAXBElement<ByteVal> createFilterByteVal(ByteVal value) {
        return new JAXBElement<ByteVal>(_FilterByteVal_QNAME, ByteVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShortIntVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ShortIntVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "ShortIntVal", scope = Filter.class)
    public JAXBElement<ShortIntVal> createFilterShortIntVal(ShortIntVal value) {
        return new JAXBElement<ShortIntVal>(_FilterShortIntVal_QNAME, ShortIntVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LongIntVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LongIntVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "LongIntVal", scope = Filter.class)
    public JAXBElement<LongIntVal> createFilterLongIntVal(LongIntVal value) {
        return new JAXBElement<LongIntVal>(_FilterLongIntVal_QNAME, LongIntVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SingleVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SingleVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "SingleVal", scope = Filter.class)
    public JAXBElement<SingleVal> createFilterSingleVal(SingleVal value) {
        return new JAXBElement<SingleVal>(_FilterSingleVal_QNAME, SingleVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoubleVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DoubleVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "DoubleVal", scope = Filter.class)
    public JAXBElement<DoubleVal> createFilterDoubleVal(DoubleVal value) {
        return new JAXBElement<DoubleVal>(_FilterDoubleVal_QNAME, DoubleVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DateTimeVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "DateTimeVal", scope = Filter.class)
    public JAXBElement<DateTimeVal> createFilterDateTimeVal(DateTimeVal value) {
        return new JAXBElement<DateTimeVal>(_FilterDateTimeVal_QNAME, DateTimeVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link StringVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "StringVal", scope = Filter.class)
    public JAXBElement<StringVal> createFilterStringVal(StringVal value) {
        return new JAXBElement<StringVal>(_FilterStringVal_QNAME, StringVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CurrencyVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "CurrencyVal", scope = Filter.class)
    public JAXBElement<CurrencyVal> createFilterCurrencyVal(CurrencyVal value) {
        return new JAXBElement<CurrencyVal>(_FilterCurrencyVal_QNAME, CurrencyVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReferenceVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "ReferenceVal", scope = Filter.class)
    public JAXBElement<ReferenceVal> createFilterReferenceVal(ReferenceVal value) {
        return new JAXBElement<ReferenceVal>(_FilterReferenceVal_QNAME, ReferenceVal.class, Filter.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceToUserVal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ReferenceToUserVal }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.omninet.de/OtWebSvc/v1", name = "ReferenceToUserVal", scope = Filter.class)
    public JAXBElement<ReferenceToUserVal> createFilterReferenceToUserVal(ReferenceToUserVal value) {
        return new JAXBElement<ReferenceToUserVal>(_FilterReferenceToUserVal_QNAME, ReferenceToUserVal.class, Filter.class, value);
    }

}
