package com.tinqin.restexport;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

public class ConvertRequestMapping {

    public static RequestMapping from(Annotation requestMappingAlias) {

        switch (requestMappingAlias.annotationType().getName()) {
            case "org.springframework.web.bind.annotation.RequestMapping" -> {
                return (RequestMapping) requestMappingAlias;
            }
            case "org.springframework.web.bind.annotation.GetMapping" -> {
                return ConvertRequestMapping.fromGetMapping((GetMapping) requestMappingAlias);
            }
            case "org.springframework.web.bind.annotation.PostMapping" -> {
                return ConvertRequestMapping.fromPostMapping((PostMapping) requestMappingAlias);
            }
            case "org.springframework.web.bind.annotation.PutMapping" -> {
                return ConvertRequestMapping.fromPutMapping((PutMapping) requestMappingAlias);
            }
            case "org.springframework.web.bind.annotation.DeleteMapping" -> {
                return ConvertRequestMapping.fromDeleteMapping((DeleteMapping) requestMappingAlias);
            }
            case "org.springframework.web.bind.annotation.PatchMapping" -> {
                return ConvertRequestMapping.fromPatchMapping((PatchMapping) requestMappingAlias);
            }

            default -> throw new RuntimeException("Not a RequestMapping alias!");
        }
    }

    public static RequestMapping from(Element element) {
        List<Class<? extends Annotation>> requestMappings = List.of(
                GetMapping.class,
                PostMapping.class,
                PutMapping.class,
                PatchMapping.class,
                DeleteMapping.class,
                RequestMapping.class
        );

        Annotation annotation = requestMappings.stream()
                .map(element::getAnnotation)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No annotation of type RequestMapping or its aliases is present."));

        return ConvertRequestMapping.from(annotation);
    }

    private static RequestMapping fromGetMapping(GetMapping getMapping) {
        return new RequestMapping() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            @NonNull
            public String name() {
                return getMapping.name();
            }

            @Override
            @NonNull
            public String[] value() {
                return getMapping.value();
            }

            @Override
            @NonNull
            public String[] path() {
                return getMapping.path();
            }

            @Override
            @NonNull
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.GET};
            }

            @Override
            @NonNull
            public String[] params() {
                return getMapping.params();
            }

            @Override
            @NonNull
            public String[] headers() {
                return getMapping.headers();
            }

            @Override
            @NonNull
            public String[] consumes() {
                return getMapping.consumes();
            }

            @Override
            @NonNull
            public String[] produces() {
                return getMapping.produces();
            }
        };
    }

    private static RequestMapping fromPostMapping(PostMapping postMapping) {
        return new RequestMapping() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            @NonNull
            public String name() {
                return postMapping.name();
            }

            @Override
            @NonNull
            public String[] value() {
                return postMapping.value();
            }

            @Override
            @NonNull
            public String[] path() {
                return postMapping.path();
            }

            @Override
            @NonNull
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.POST};
            }

            @Override
            @NonNull
            public String[] params() {
                return postMapping.params();
            }

            @Override
            @NonNull
            public String[] headers() {
                return postMapping.headers();
            }

            @Override
            @NonNull
            public String[] consumes() {
                return postMapping.consumes();
            }

            @Override
            @NonNull
            public String[] produces() {
                return postMapping.produces();
            }
        };
    }

    private static RequestMapping fromPutMapping(PutMapping putMapping) {
        return new RequestMapping() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            @NonNull
            public String name() {
                return putMapping.name();
            }

            @Override
            @NonNull
            public String[] value() {
                return putMapping.value();
            }

            @Override
            @NonNull
            public String[] path() {
                return putMapping.path();
            }

            @Override
            @NonNull
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.PUT};
            }

            @Override
            @NonNull
            public String[] params() {
                return putMapping.params();
            }

            @Override
            @NonNull
            public String[] headers() {
                return putMapping.headers();
            }

            @Override
            @NonNull
            public String[] consumes() {
                return putMapping.consumes();
            }

            @Override
            @NonNull
            public String[] produces() {
                return putMapping.produces();
            }
        };
    }

    private static RequestMapping fromDeleteMapping(DeleteMapping deleteMapping) {
        return new RequestMapping() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            @NonNull
            public String name() {
                return deleteMapping.name();
            }

            @Override
            @NonNull
            public String[] value() {
                return deleteMapping.value();
            }

            @Override
            @NonNull
            public String[] path() {
                return deleteMapping.path();
            }

            @Override
            @NonNull
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.DELETE};
            }

            @Override
            @NonNull
            public String[] params() {
                return deleteMapping.params();
            }

            @Override
            @NonNull
            public String[] headers() {
                return deleteMapping.headers();
            }

            @Override
            @NonNull
            public String[] consumes() {
                return deleteMapping.consumes();
            }

            @Override
            @NonNull
            public String[] produces() {
                return deleteMapping.produces();
            }
        };
    }

    private static RequestMapping fromPatchMapping(PatchMapping patchMapping) {
        return new RequestMapping() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return RequestMapping.class;
            }

            @Override
            @NonNull
            public String name() {
                return patchMapping.name();
            }

            @Override
            @NonNull
            public String[] value() {
                return patchMapping.value();
            }

            @Override
            @NonNull
            public String[] path() {
                return patchMapping.path();
            }

            @Override
            @NonNull
            public RequestMethod[] method() {
                return new RequestMethod[]{RequestMethod.PATCH};
            }

            @Override
            @NonNull
            public String[] params() {
                return patchMapping.params();
            }

            @Override
            @NonNull
            public String[] headers() {
                return patchMapping.headers();
            }

            @Override
            @NonNull
            public String[] consumes() {
                return patchMapping.consumes();
            }

            @Override
            @NonNull
            public String[] produces() {
                return patchMapping.produces();
            }
        };
    }
}
