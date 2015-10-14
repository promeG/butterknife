package butterknife.compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * Created by guyacong on 15/10/14.
 */
final class Validator {

    private final Elements mElements;

    private final Map<Integer, String> idQualifiedNamesByIdValues = new HashMap<>();
    private final Set<String> idQualifiedNames = new HashSet<>();

    public Validator(Elements elementUtils, String resPkgName) {
        mElements = elementUtils;
        String rClassName = resPkgName + ".R";
        String rIdClassName = rClassName + ".id";
        TypeElement typeElement = mElements.getTypeElement(rIdClassName);

        List<? extends Element> idEnclosedElements = typeElement.getEnclosedElements();

        List<VariableElement> idFields = ElementFilter.fieldsIn(idEnclosedElements);

        for (VariableElement idField : idFields) {

            TypeKind fieldType = idField.asType().getKind();
            if (fieldType.isPrimitive() && fieldType.equals(TypeKind.INT)) {
                String idQualifiedName = idField.getSimpleName().toString();
                idQualifiedNames.add(idQualifiedName);
                Integer idFieldId = (Integer) idField.getConstantValue();
                if (idFieldId != null) {
                    idQualifiedNamesByIdValues.put(idFieldId, idQualifiedName);
                }
            }
        }
    }

    public boolean containsIdValue(  String resName) {
        return idQualifiedNames.contains(resName);
    }

}
