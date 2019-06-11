package com.yodlee.iae.bugr.services.synthetic.base;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author KChandrarajan
 *
 */
public interface ServiceIO<T, V> extends Consumer<T>, Supplier<V> {

	public default V process(T req) {
		this.accept(req);
		this.mapInput();
		this.validate();
		this.executeImpl();
		this.mapOutput();
		return this.get();
	}

	public void mapInput();

	public void validate();

	public void executeImpl();

	public void mapOutput();

}
