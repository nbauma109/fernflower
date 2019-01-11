package org.jetbrains.java.decompiler.struct;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MethodBean implements Comparable<MethodBean> {
	
	private String name;
	private List<String> paramTypes;

	public MethodBean(String name) {
		this.name = name;
		paramTypes = new ArrayList<>();
	}

	public MethodBean(Method method) {
		this(method.getName());
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (Class<?> paramType : parameterTypes) {
			paramTypes.add(paramType.getTypeName());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((paramTypes == null) ? 0 : paramTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodBean other = (MethodBean) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (paramTypes == null) {
			if (other.paramTypes != null)
				return false;
		} else if (!paramTypes.equals(other.paramTypes))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes = paramTypes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		Iterator<String> it = paramTypes.iterator();
		if (!it.hasNext()) {
			return sb.append("()").toString();
		}
		sb.append('(');
		for (;;) {
			sb.append(it.next());
			if (!it.hasNext())
				return sb.append(')').toString();
			sb.append(',').append(' ');
		}
	}

	@Override
	public int compareTo(MethodBean methodBean) {
		return toString().compareTo(methodBean.toString());
	}
}
