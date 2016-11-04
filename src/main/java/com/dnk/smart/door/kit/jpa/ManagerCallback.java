package com.dnk.smart.door.kit.jpa;

import javax.persistence.EntityManager;

public interface ManagerCallback<T> {

	T doExecute(EntityManager manager);
}
