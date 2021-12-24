package ua.omniway.client.ot.soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.omniway.client.ot.soap.InvokeScriptParameters;
import ua.omniway.client.ot.soap.InvokeScriptResult;
import ua.omniway.client.ot.soap.ScriptParameters;
import ua.omniway.client.ot.soap.enums.ScriptExecutionSite;
import ua.omniway.client.ot.soap.operations.SoapOperations;
import ua.omniway.client.ot.soap.types.BoolVal;
import ua.omniway.client.ot.soap.types.DateTimeVal;
import ua.omniway.client.ot.soap.types.LongIntVal;
import ua.omniway.client.ot.soap.types.StringVal;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.models.converters.ConvertUtils;

import java.util.List;

@Component
public class CommonApiService {
    private final SoapOperations soapOperations;

    @Autowired
    public CommonApiService(SoapOperations soapOperations) {
        this.soapOperations = soapOperations;
    }

    public Object execute(String script, String method) throws OmnitrackerException {
        return this.execute(script, method, null);
    }

    public Object execute(String script, String method, List<Object> params) throws OmnitrackerException {
        InvokeScriptParameters invokeParameters = new InvokeScriptParameters();
        invokeParameters.setRunAt(ScriptExecutionSite.DEFAULT);
        invokeParameters.setName(script);
        ScriptParameters scriptParameters = new ScriptParameters();
        scriptParameters.getOtValues().add(new StringVal("method", method));
        if (params != null) {
            scriptParameters.getOtValues().addAll(params);
        }
        invokeParameters.setParameters(scriptParameters);

        InvokeScriptResult scriptResult = soapOperations.invokeScript(invokeParameters);
        if (!scriptResult.isSuccess()) {
            throw new OmnitrackerException(scriptResult.getErrorMsg());
        }
        if (scriptResult.getParameters().getOtValues().isEmpty()){
            return null;
        } else {
            return this.parse(scriptResult.getParameters().getOtValues());
        }
    }

    private Object parse(List<Object> results) {
        if (results.size() != 1) {
            throw new IllegalArgumentException("Incorrect results size: " + results.size());
        }
        final Object result = results.get(0);
        if (result instanceof StringVal) {
            return (((StringVal) result).getValue());
        }
        if (result instanceof LongIntVal) {
            return (((LongIntVal) result).getValue());
        }
        if (result instanceof DateTimeVal) {
            return ConvertUtils.convertToLocalDateTime((((DateTimeVal) result).getValue()));
        }
        if (result instanceof BoolVal) {
            return (((BoolVal) result).getValue());
        }
        return null;
    }
}
