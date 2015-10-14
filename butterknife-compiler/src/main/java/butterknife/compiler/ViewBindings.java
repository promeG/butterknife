package butterknife.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;

final class ViewBindings {
  private final int id;
  private final String resName;
  private final Set<FieldViewBinding> fieldBindings = new LinkedHashSet<>();
  private final LinkedHashMap<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>>
      methodBindings = new LinkedHashMap<>();

  ViewBindings(int id) {
    this.id = id;
    this.resName = "";
  }

  ViewBindings(String resName) {
    this.id = -1;
    this.resName = resName;
  }

  public String getResName() {
    return resName;
  }

  public int getId() {
    return id;
  }

  public Collection<FieldViewBinding> getFieldBindings() {
    return fieldBindings;
  }

  public Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> getMethodBindings() {
    return methodBindings;
  }

  public boolean hasMethodBinding(ListenerClass listener, ListenerMethod method) {
    Map<ListenerMethod, Set<MethodViewBinding>> methods = methodBindings.get(listener);
    return methods != null && methods.containsKey(method);
  }

  public void addMethodBinding(ListenerClass listener, ListenerMethod method,
      MethodViewBinding binding) {
    Map<ListenerMethod, Set<MethodViewBinding>> methods = methodBindings.get(listener);
    Set<MethodViewBinding> set = null;
    if (methods == null) {
      methods = new LinkedHashMap<>();
      methodBindings.put(listener, methods);
    } else {
      set = methods.get(method);
    }
    if (set == null) {
      set = new LinkedHashSet<>();
      methods.put(method, set);
    }
    set.add(binding);
  }

  public void addFieldBinding(FieldViewBinding fieldBinding) {
    fieldBindings.add(fieldBinding);
  }

  public List<ViewBinding> getRequiredBindings() {
    List<ViewBinding> requiredViewBindings = new ArrayList<>();
    for (FieldViewBinding fieldBinding : fieldBindings) {
      if (fieldBinding.isRequired()) {
        requiredViewBindings.add(fieldBinding);
      }
    }
    for (Map<ListenerMethod, Set<MethodViewBinding>> methodBinding : methodBindings.values()) {
      for (Set<MethodViewBinding> set : methodBinding.values()) {
        for (MethodViewBinding binding : set) {
          if (binding.isRequired()) {
            requiredViewBindings.add(binding);
          }
        }
      }
    }
    return requiredViewBindings;
  }
}
