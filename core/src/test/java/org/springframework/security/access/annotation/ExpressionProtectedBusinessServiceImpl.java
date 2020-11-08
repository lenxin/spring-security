package org.springframework.security.access.annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;

public class ExpressionProtectedBusinessServiceImpl implements BusinessService {

	@Override
	public void someAdminMethod() {
	}

	@Override
	public int someOther(String s) {
		return 0;
	}

	@Override
	public int someOther(int input) {
		return 0;
	}

	@Override
	public void someUserAndAdminMethod() {
	}

	@Override
	public void someUserMethod1() {
	}

	@Override
	public void someUserMethod2() {
	}

	@Override
	@PreFilter(filterTarget = "someList", value = "filterObject == authentication.name or filterObject == 'sam'")
	@PostFilter("filterObject == 'bob'")
	public List<?> methodReturningAList(List<?> someList) {
		return someList;
	}

	@Override
	public List<Object> methodReturningAList(String userName, String arg2) {
		return new ArrayList<>();
	}

	@Override
	@PostFilter("filterObject == 'bob'")
	public Object[] methodReturningAnArray(Object[] someArray) {
		return someArray;
	}

	@PreAuthorize("#x == 'x' and @number.intValue() == 1294 ")
	public void methodWithBeanNamePropertyAccessExpression(String x) {
	}

	@Override
	public void rolesAllowedUser() {
	}

}
