package com.tinqin.restexport;

import com.helger.jcodemodel.*;
import com.helger.jcodemodel.writer.JCMWriter;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RestExportGenerator {
    private final List<RequestMappingData> methodList;
    private final String targetPath;
    private final String targetPackage;
    private final String fileName;

    public RestExportGenerator(List<RequestMappingData> methodList, String targetPath, String targetPackage, String fileName) {
        this.methodList = methodList;
        this.targetPath = targetPath;
        this.targetPackage = targetPackage;
        this.fileName = fileName;
    }

    public void generate() throws IOException, JCodeModelException {
        this.process(this.methodList);
    }

    private void process(List<RequestMappingData> restExportAnnotatedMethods) throws JCodeModelException, IOException {
        JCodeModel jcm = new JCodeModel();

        JDefinedClass clazz = jcm._class(this.targetPackage + "." + this.fileName, EClassType.INTERFACE);
        clazz.annotate(feign.Headers.class).paramArray("value", "Content-Type: application/json"); //hardcoded

        restExportAnnotatedMethods.forEach(e -> this.addMethod(clazz, e));


        JCMWriter writer = new JCMWriter(jcm);
        writer.build(new File(this.targetPath));
    }

    private void addMethod(JDefinedClass c, RequestMappingData mappingData) {
        RequestLineBuilder requestLineBuilder = new RequestLineBuilder(mappingData);
        requestLineBuilder.getRequestLine();

        JMethod method = c.method(JMod.NONE, mappingData.getReturnType(), mappingData.getMethodName());

        method.annotate(RequestLine.class).param("value", requestLineBuilder.getRequestLine());


        mappingData.getMirrorParameters()
                .stream()
                .filter(e -> e.getAnnotation().annotationType().equals(PathVariable.class))
                .forEach(e -> this.addPathVariableParameterToMethod(method, e));

        mappingData.getMirrorParameters()
                .stream()
                .filter(e -> e.getAnnotation().annotationType().equals(RequestParam.class))
                .forEach(e -> this.addRequestParamParameterToMethod(method, e));

        mappingData.getMirrorParameters()
                .stream()
                .filter(e -> e.getAnnotation().annotationType().equals(RequestBody.class))
                .forEach(e -> this.addRequestBodyParameterToMethod(method, e));
    }

    private void addPathVariableParameterToMethod(JMethod method, MirrorParameter mirrorParameter) {
        String parameterName = ((PathVariable) mirrorParameter.getAnnotation()).name().isEmpty()
                ? mirrorParameter.getName()
                : ((PathVariable) mirrorParameter.getAnnotation()).name();


        if (!mirrorParameter.getGenericType().equals(Object.class)) {
            AbstractJType parameterType = new JCodeModel().ref(mirrorParameter.getParameterType()).narrow(mirrorParameter.getGenericType());

            method.param(parameterType, parameterName)
                    .annotate(Param.class)
                    .param("value", parameterName);
            return;
        }

        method.param(mirrorParameter.getParameterType(), parameterName)
                .annotate(Param.class)
                .param("value", parameterName);
    }


    private void addRequestParamParameterToMethod(JMethod method, MirrorParameter mirrorParameter) {
        String parameterName = ((RequestParam) mirrorParameter.getAnnotation()).name().isEmpty()
                ? mirrorParameter.getName()
                : ((RequestParam) mirrorParameter.getAnnotation()).name();

        if (!mirrorParameter.getGenericType().equals(Object.class)) {
            AbstractJType parameterType = new JCodeModel().ref(mirrorParameter.getParameterType()).narrow(mirrorParameter.getGenericType());

            method.param(parameterType, parameterName)
                    .annotate(Param.class)
                    .param("value", parameterName);
            return;
        }

        method.param(mirrorParameter.getParameterType(), mirrorParameter.getName())
                .annotate(Param.class)
                .param("value", parameterName);
    }


    private void addRequestBodyParameterToMethod(JMethod method, MirrorParameter mirrorParameter) {
        method.param(mirrorParameter.getParameterType(), mirrorParameter.getName())
                .annotate(Param.class);
    }
}
