package com.mojang.minecraft.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.HashSet;
import java.util.Set;

public final class LevelObjectInputStream extends ObjectInputStream {

	private Set<String> classes = new HashSet<String>();

	public LevelObjectInputStream(InputStream var1) throws IOException {
		super(var1);
		classes.add("Player$1");
		classes.add("Creeper$1");
		classes.add("Skeleton$1");
	}

	@Override
	protected final ObjectStreamClass readClassDescriptor() {
		try {
			ObjectStreamClass var1 = super.readClassDescriptor();
			return classes.contains(var1.getName()) ? ObjectStreamClass.lookup(Class.forName(var1
					.getName())) : var1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
