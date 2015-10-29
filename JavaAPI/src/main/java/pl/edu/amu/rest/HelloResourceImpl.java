package pl.edu.amu.rest;

import pl.edu.amu.api.HelloResource;

public class HelloResourceImpl implements HelloResource {

    @Override
	public String getMsg(String name) {

		return "hello: " + name;
	}
}
