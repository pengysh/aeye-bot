package com.a.eye.bot.common.message.actor;

public class ActorPath {
	
//本地调用地址："aeye://my-sys/user/service-a/worker1"
//远程调用地址："aeye.tcp://my-sys@host.example.com:5678/user/service-a/worker1"

	private String address;

	private String path;

	private String name;

	public ActorPath(String name, String path) {
		this.name = name;
		this.path = path;
		this.address = path;
	}

	public String getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}
}
