package com.github.hexosse.bloodmoon.util;

import java.util.List;
import java.util.Random;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class RandomUtils
{
	private static final Random rand = new Random();

	/**
	 * Gets a random entry from the list.
	 *
	 * @param items		The list to use.
	 * @param <T>		The object being conversed with
	 * @return			A random entry from the list.
	 */
	public static <T> T getRandom(List<T> items){
		return items.get(rand.nextInt(items.size()));
	}
}
